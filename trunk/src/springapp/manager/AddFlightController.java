package springapp.manager;

import hibernate.Airline;
import hibernate.Airport;
import hibernate.Flight;
import hibernate.manager.AirlineManager;
import hibernate.manager.AirportManager;
import hibernate.manager.FlightManager;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import springapp.web.SessionConstants;

/**
 * A Spring Framework Controller for add flight. This is for manager not for customers.
 */
public class AddFlightController extends SimpleFormController {
   /**
    * bean name
    */
   public static final String URL = "addflight.spring";

   @SuppressWarnings("unused")
   private Logger log = Logger.getLogger(getClass());

   /**
    * handles the requests from web browsers
    * @see org.springframework.web.servlet.mvc.AbstractController#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
   public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
         throws Exception {

      final List<String[]> airlines = AirlineManager.getAirlineCodeAndName();
      final List<String[]> airports = AirportManager.getAirportCodeAndName();

      final ModelAndView mv = super.handleRequest(req, resp);
      mv.addObject("airlines", airlines);
      mv.addObject("airports", airports);
      return mv;
   }

   /**
    * handles submitting action from web browsers.
    * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
    */
   protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final HttpSession session = request.getSession();
      final AddFlightCommand f = (AddFlightCommand) command;

      final int departYear = f.getDepartYear();
      final int departMonth = f.getDepartMonth();
      final int departDay = f.getDepartDay();
      final int departHour = f.getDepartHour();
      final Calendar departCalendar = Calendar.getInstance();
      departCalendar.set(departYear, departMonth - 1, departDay, departHour, 0);
      final Date departDate = departCalendar.getTime();

      final int arrivalYear = f.getReturnYear();
      final int arrivalMonth = f.getReturnMonth();
      final int arrivalDay = f.getReturnDay();
      final int arrivalHour = f.getReturnHour();
      final Calendar arrivalCalendar = Calendar.getInstance();
      arrivalCalendar.set(arrivalYear, arrivalMonth - 1, arrivalDay, arrivalHour, 0);
      final Date arrivalDate = arrivalCalendar.getTime();

      final Airline airline = AirlineManager.getAirline(f.getAirline());
      final Airport arrival = AirportManager.getAirport(f.getAirportByArrivalLocation());
      final Airport departure = AirportManager.getAirport(f.getAirportByDepartureLocation());

      final Flight newFlight = new Flight(f.getFlightNo(), airline, departure, arrival, departDate,
            arrivalDate, f.getEconomySeats(), f.getEconomyPrice(), f.getBusinessSeats(), f
                  .getBusinessPrice(), null);
      final boolean bRst = FlightManager.addFlight(newFlight);
      if (bRst)
         session.setAttribute(SessionConstants.ADDED_NEW_FLIGHT, newFlight);

      final ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));
      return mv;
   }

   /**
    * generates and returns a default command object
    * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
    */
   @Override
   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      final AddFlightCommand cmd = (AddFlightCommand) super.formBackingObject(request);
      cmd.setAirportByDepartureLocation("IAD");
      cmd.setAirportByArrivalLocation("JFK");
      cmd.setFlightNo(100);
      cmd.setEconomySeats(30);
      cmd.setBusinessSeats(10);
      cmd.setEconomyPrice(300f);
      cmd.setBusinessPrice(1500f);
      return cmd;
   }

}
