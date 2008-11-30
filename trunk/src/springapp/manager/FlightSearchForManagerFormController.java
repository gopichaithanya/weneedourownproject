package springapp.manager;

import hibernate.manager.AirlineManager;
import hibernate.manager.AirportManager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class FlightSearchForManagerFormController extends SimpleFormController {

   public static final String URL = "flightSearchForManagerForm.spring";

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
}
