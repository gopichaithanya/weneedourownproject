package springapp.manager;

import hibernate.Flight;
import hibernate.manager.AirlineManager;
import hibernate.manager.AirportManager;
import hibernate.manager.FlightManager;

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
      final ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));
      FlightAddForManager flight = (FlightAddForManager) command;

      int departYear = flight.getDepartYear();
      int departMonth = flight.getDepartMonth();
      int departDay = flight.getDepartDay();
      int departHour = flight.getDepartHour();

      Date[] departDate = FlightManager.getTimeRange(departYear, departMonth, departDay,
            departHour, 0);

      int arrivalYear = flight.getReturnYear();
      int arrivalMonth = flight.getReturnMonth();
      int arrivalDay = flight.getReturnDay();
      int arrivalHour = flight.getReturnHour();

      Date[] returnDate = FlightManager.getTimeRange(arrivalYear, arrivalMonth, arrivalDay,
            arrivalHour, 0);

//      Flight loFlight = new Flight(flight.getFlightNo(), flight.getAirline(), flight
//            .getAirportByArrivalLocation(), flight.getAirportByDepartureLocation(), departDate[0],
//            returnDate[0], flight.getEconomySeats(), flight.getEconomyPrice(), flight
//                  .getBusinessSeats(), flight.getBusinessPrice(), null);

//      FlightManager.addFlight(loFlight);

      return mv;
   }

   @Override
   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      final FlightAddForManager cmd = (FlightAddForManager) super.formBackingObject(request);
      cmd.setEconomySeats(30);
      cmd.setBusinessSeats(10);
      cmd.setEconomyPrice(300f);
      cmd.setBusinessPrice(1500f);
      return cmd;
   }
   
}
