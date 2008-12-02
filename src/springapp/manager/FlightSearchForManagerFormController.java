package springapp.manager;

import hibernate.Flight;
import hibernate.manager.AirlineManager;
import hibernate.manager.AirportManager;
import hibernate.manager.FlightManager;
import hibernate.manager.FlightManager.EWeek;

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
      mv.addObject("weeks", EWeek.values());
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

      cmd.setOptArriveDate(true);
      cmd.setOptDepartDate(true);
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
         final Boolean dAnyday = cmd.getOptDepartDate();
         final EWeek dw = cmd.getDepartWeek();
         final Integer dy = (dAnyday || null != dw ? null : cmd.getDepartYear());
         final Integer dm = (dAnyday || null != dw ? null : cmd.getDepartMonth());
         final Integer dd = (dAnyday || null != dw ? null : cmd.getDepartDay());
         final Integer dh = (dAnyday ? null : cmd.getDepartHour());
         final Integer dr = (dAnyday ? null : cmd.getDepartHourRange());

         final String alTmp = cmd.getArrivalLocation();
         final String al = (null == alTmp || 0 == alTmp.length() ? null : alTmp);
         final Boolean aAnyday = cmd.getOptArriveDate();
         final EWeek aw = cmd.getArriveWeek();
         final Integer ay = (aAnyday || null != aw ? null : cmd.getArriveYear());
         final Integer am = (aAnyday || null != aw ? null : cmd.getArriveMonth());
         final Integer ad = (aAnyday || null != aw ? null : cmd.getArriveDay());
         final Integer ah = (aAnyday ? null : cmd.getArriveHour());
         final Integer ar = (aAnyday ? null : cmd.getArriveHourRange());

         flights.addAll(FlightManager.getFlightList(airline, dl, al, dy, dm, dd, dh, dr, dw, ay,
               am, ad, ah, ar, aw));
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
