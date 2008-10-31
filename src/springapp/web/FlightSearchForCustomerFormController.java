package springapp.web;

import hibernate.Flight;
import hibernate.manager.AirportManager;
import hibernate.manager.FlightManager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

@SuppressWarnings("unchecked")
public class FlightSearchForCustomerFormController extends SimpleFormController {

   public static final String URL = "flightSearchForCustomerForm.spring";

   /**
    * This is for debugging purpose.
    * If this is false, all of flights will be shown; this is for debugging.
    * If this is true, flight searching will work.
    */
   static boolean bFlagSearch = false;

   public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
         throws Exception {

      final ModelAndView mv = super.handleRequest(req, resp);

      final List<String[]> airports = AirportManager.getAirportCodeAndName();
      mv.addObject("airports", airports);
      mv.addObject("tripTypes", FlightSearchForCustomer.ETripType.values());

      return mv;
   }

   protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      if (null == command)
         return new ModelAndView(this.getFormView());

      ModelAndView mv = null;
      {
         final FlightSearchForCustomer cmd = ((FlightSearchForCustomer) command);
         if (false == cmd.isNullDepartFlightNo()
               && (cmd.isOneWayTrip() || false == cmd.isNullReturnFlightNo())) {
            // after step 3 with round trip or after step 2 with one way trip
            mv = onSubmit3(request, response, command, errors);
         } else if (false == cmd.isNullDepartFlightNo()) {
            // after step 2
            mv = onSubmit2(request, response, command, errors);
         } else {
            // after step 1
            mv = onSubmit1(request, response, command, errors);
         }
      }
      mv.addObject(this.getCommandName(), command);
      return mv;
   }

   private ModelAndView onSubmit1(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final FlightSearchForCustomer cmd = ((FlightSearchForCustomer) command);
      List flights;
      if (bFlagSearch)
         flights = FlightManager.getFlightList(cmd.getDepartLocation(), cmd.getArrivalLocation(),
               cmd.getDepartYear(), cmd.getDepartMonth(), cmd.getDepartDay(), cmd.getDepartHour(),
               cmd.getSearchingHourRange());
      else
         flights = FlightManager.getFlightList(null, null, 0, 0, 0, 0, 0);

      final ModelAndView mv = new ModelAndView(this.getFormView());
      mv.addObject("searchedDepartFlights", flights);
      mv.addObject("isOneWayTrip", cmd.isOneWayTrip());
      mv.addObject("isRoundTrip", cmd.isRoundTrip());
      return mv;
   }

   private ModelAndView onSubmit2(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final FlightSearchForCustomer cmd = ((FlightSearchForCustomer) command);
      List flights;
      if (bFlagSearch)
         flights = FlightManager.getFlightList(cmd.getArrivalLocation(), cmd.getDepartLocation(),
               cmd.getReturnYear(), cmd.getReturnMonth(), cmd.getReturnDay(), cmd.getReturnHour(),
               cmd.getSearchingHourRange());
      else
         flights = FlightManager.getFlightList(null, null, 0, 0, 0, 0, 0);

      final ModelAndView mv = new ModelAndView(this.getFormView());
      mv.addObject("searchedReturnFlights", flights);
      final Flight departFlight = FlightManager.getFlight(cmd.getDepartFlightNo());
      mv.addObject("selectedDepartFlight", departFlight);
      return mv;
   }

   private ModelAndView onSubmit3(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final HttpSession session = request.getSession();
      final FlightSearchForCustomer cmd = ((FlightSearchForCustomer) command);

      Integer[] flights = null;
      if (cmd.isOneWayTrip())
         flights = new Integer[] { cmd.getDepartFlightNo() };
      else if (cmd.isRoundTrip())
         flights = new Integer[] { cmd.getDepartFlightNo(), cmd.getReturnFlightNo() };

      session.setAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER, flights);

      final ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));
      return mv;
   }

}
