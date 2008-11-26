package springapp.web;

import hibernate.Flight;
import hibernate.Itinerary.ESeatClass;
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

/**
 * A spring controller for flight searching.
 * This searching functionality is for customer not for manager.
 */
@SuppressWarnings("unchecked")
public class FlightSearchForCustomerFormController extends SimpleFormController {

   public static final String URL = "flightSearchForCustomerForm.spring";

   /**
    * This is for debugging purpose.
    * If this is false, all of flights will be shown; this is for debugging.
    * If this is true, flight searching will work.
    */
   static boolean bFlagSearch = true;

   /**
    * Handle request for web browser.
    * This handler retrieves airport list from database. 
    */
   public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
         throws Exception {

      final ModelAndView mv = super.handleRequest(req, resp);

      final List<String[]> airports = AirportManager.getAirportCodeAndName();
      mv.addObject("airports", airports);
      mv.addObject("tripTypes", FlightSearchForCustomer.ETripType.values());
      mv.addObject("seatClass", ESeatClass.values());

      return mv;
   }

   /**
    * <UL>This method reacts in 3 steps:
    * <LI>1. selecting airports.
    * <LI>2. selecting departing ticket.
    * <LI>3. selecting arrival ticket.</UL>
    */
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

   /**
    * This method reacts for the first step, searching for airports. Validity wouldn't be checked here.
    * @param request Spring framework passes request object.
    * @param response Spring framework passes response object.
    * @param command Spring framework passes command object.
    * @param errors Error object for Spring framework
    * @return ModelAndView object of Spring framework
    * @throws Exception
    * @see FlightSearchForCustomerValidator
    * @see FlightSearchForCustomerFormController#onSubmit(HttpServletRequest, HttpServletResponse, Object, BindException)
    */
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

      final List<Float> departLatLng = AirportManager.getAirportLatLong(cmd.getDepartLocation());
      final List<Float> arrivalLatLng = AirportManager.getAirportLatLong(cmd.getArrivalLocation());
      mv.addObject("departAirportLat", departLatLng.get(0));
      mv.addObject("departAirportLng", departLatLng.get(1));
      mv.addObject("arrivalAirportLat", arrivalLatLng.get(0));
      mv.addObject("arrivalAirportLng", arrivalLatLng.get(1));
      return mv;
   }

   /**
    * This method reacts for the second step, searching for airports. Validity wouldn't be checked here.
    * @param request Spring framework passes request object.
    * @param response Spring framework passes response object.
    * @param command Spring framework passes command object.
    * @param errors Error object for Spring framework
    * @return ModelAndView object of Spring framework
    * @throws Exception
    * @see FlightSearchForCustomerValidator
    * @see FlightSearchForCustomerFormController#onSubmit(HttpServletRequest, HttpServletResponse, Object, BindException)
    */
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

      final List<Float> departLatLng = AirportManager.getAirportLatLong(cmd.getDepartLocation());
      final List<Float> arrivalLatLng = AirportManager.getAirportLatLong(cmd.getArrivalLocation());
      mv.addObject("departAirportLat", departLatLng.get(0));
      mv.addObject("departAirportLng", departLatLng.get(1));
      mv.addObject("arrivalAirportLat", arrivalLatLng.get(0));
      mv.addObject("arrivalAirportLng", arrivalLatLng.get(1));
      return mv;
   }

   /**
    * This method reacts for the third step, searching for airports. Validity wouldn't be checked here.
    * @param request Spring framework passes request object.
    * @param response Spring framework passes response object.
    * @param command Spring framework passes command object.
    * @param errors Error object for Spring framework
    * @return ModelAndView object of Spring framework
    * @throws Exception
    * @see FlightSearchForCustomerValidator
    * @see FlightSearchForCustomerFormController#onSubmit(HttpServletRequest, HttpServletResponse, Object, BindException)
    */
   private ModelAndView onSubmit3(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final HttpSession session = request.getSession();
      final FlightSearchForCustomer cmd = ((FlightSearchForCustomer) command);

      Integer[] flights = null;
      if (cmd.isOneWayTrip())
         flights = new Integer[] { cmd.getDepartFlightNo() };
      else if (cmd.isRoundTrip())
         flights = new Integer[] { cmd.getDepartFlightNo(), cmd.getReturnFlightNo() };
      
      final ESeatClass seatClass = cmd.getSeatClass();
      final int numPassengers = cmd.getNumPassengers();

      session.setAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER, flights);
      session.setAttribute(SessionConstants.RESERVE_SEATCLASS_FOR_CUSTOMER, seatClass);
      session.setAttribute(SessionConstants.RESERVE_NUM_PASSENGERS_FOR_CUSTOMER, numPassengers);

      final ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));
      return mv;
   }

}
