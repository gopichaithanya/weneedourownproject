package springapp.manager;

import hibernate.Flight;
import hibernate.manager.AirlineManager;
import hibernate.manager.AirportManager;
import hibernate.manager.FlightManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * A Spring Framework Controller for flight manger search
 */
public class FlightSearchForManagerFormController extends SimpleFormController {

   /**
    * Bean name
    */
   public static final String URL = "flightSearchForManagerForm.spring";

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
    * Generates and returns a default command object.
    * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
    */
   @Override
   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      final FlightSearchForManagerCommand cmd = (FlightSearchForManagerCommand) super
            .formBackingObject(request);

      cmd.setOptArriveDate(false);
      cmd.setOptDepartDate(false);
      return cmd;
   }

   /**
    * handles the Spring Framework command object
    * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
    */
   @Override
   protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final List<Flight> flights = new ArrayList<Flight>();

      final FlightSearchForManagerCommand cmd = (FlightSearchForManagerCommand) command;
      final Integer fn = cmd.getFlightNo();
      if (null != fn) {
         final Flight f = FlightManager.getFlight(fn);
         if (null != f)
            flights.add(f);
      } else {
         final String airlineTmp = cmd.getAirline();
         final String airline = (null == airlineTmp || 0 == airlineTmp.length() ? null : airlineTmp);

         final String dlTmp = cmd.getDepartLocation();
         final String dl = (null == dlTmp || 0 == dlTmp.length() ? null : dlTmp);
         final Boolean dOpt = cmd.getOptDepartDate();
         final Integer dy = (dOpt ? cmd.getDepartYear() : null);
         final Integer dm = (dOpt ? cmd.getDepartMonth() : null);
         final Integer dd = (dOpt ? cmd.getDepartDay() : null);
         final Integer dh = (dOpt ? cmd.getDepartHour() : null);
         final Integer dr = (dOpt ? cmd.getDepartHourRange() : null);

         final String alTmp = cmd.getArrivalLocation();
         final String al = (null == alTmp || 0 == alTmp.length() ? null : alTmp);
         final Boolean aOpt = cmd.getOptArriveDate();
         final Integer ay = (aOpt ? cmd.getArriveYear() : null);
         final Integer am = (aOpt ? cmd.getArriveMonth() : null);
         final Integer ad = (aOpt ? cmd.getArriveDay() : null);
         final Integer ah = (aOpt ? cmd.getArriveHour() : null);
         final Integer ar = (dOpt ? cmd.getArriveHourRange() : null);

         flights.addAll(FlightManager.getFlightList(airline, dl, al, dy, dm, dd, dh, dr, ay, am,
               ad, ah, ar));
      }

      final Map<Integer, Integer[]> seats = new HashMap<Integer, Integer[]>();
      for (final Flight f : flights) {
         final int flightNo = f.getFlightNo();
         final Integer[] leftSeats = FlightManager.getLeftSeats(flightNo);
         seats.put(flightNo, leftSeats);
      }

      final ModelAndView mv = super.onSubmit(request, response, command, errors);
      mv.addObject("flights", flights);
      mv.addObject("seats", seats);
      return mv;
   }
}
