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

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class AddFlightController extends SimpleFormController {
   private Logger log = Logger.getLogger(getClass());

   public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
         throws Exception {

      final List<String[]> airlines = AirlineManager.getAirlineCodeAndName();
      final List<String[]> airports = AirportManager.getAirportCodeAndName();

      final ModelAndView mv = super.handleRequest(req, resp);
      mv.addObject("airlines", airlines);
      mv.addObject("airports", airports);
      return mv;
   }

   protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      log.info("start onSubmit");
      final FlightAddForManager f = (FlightAddForManager) command;

      final int departYear = f.getDepartYear();
      final int departMonth = f.getDepartMonth();
      final int departDay = f.getDepartDay();
      final int departHour = f.getDepartHour();
      final Calendar departCalendar = Calendar.getInstance();
      departCalendar.set(departYear, departMonth, departDay, departHour, 0);
      final Date departDate = departCalendar.getTime();

      final int arrivalYear = f.getReturnYear();
      final int arrivalMonth = f.getReturnMonth();
      final int arrivalDay = f.getReturnDay();
      final int arrivalHour = f.getReturnHour();
      final Calendar arrivalCalendar = Calendar.getInstance();
      arrivalCalendar.set(arrivalYear, arrivalMonth, arrivalDay, arrivalHour, 0);
      final Date arrivalDate = arrivalCalendar.getTime();

      final Airline airline = AirlineManager.getAirline(f.getAirline());
      final Airport arrival = AirportManager.getAirport(f.getAirportByArrivalLocation());
      final Airport departure = AirportManager.getAirport(f.getAirportByDepartureLocation());

      final Flight newFlight = new Flight(f.getFlightNo(), airline, arrival, departure, departDate,
            arrivalDate, f.getEconomySeats(), f.getEconomyPrice(), f.getBusinessSeats(), f
                  .getBusinessPrice(), null);
      final boolean bRst = FlightManager.addFlight(newFlight);
      log.info("end onSubmit: " + bRst);

      final ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));
      return mv;
   }

   @Override
   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      final FlightAddForManager cmd = (FlightAddForManager) super.formBackingObject(request);
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
