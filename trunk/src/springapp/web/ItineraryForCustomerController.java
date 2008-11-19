package springapp.web;

import hibernate.Flight;
import hibernate.manager.ItineraryManager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * A Spring controller for itinerary page.
 * This controller retrieves reserved flights, booked flights, and canceled flights from database.
 */
public class ItineraryForCustomerController implements Controller {

   public static final String URL = "itineraryForCustomer.spring";

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      // Check login
      final HttpSession session = request.getSession();
      String userName = LoginController.getUserName(session);
      if (null == userName)
         userName = "jjohnson"; // TODO
         //return LoginController.redirectToLogin(session, URL);

      final List<Flight> reserved = ItineraryManager.getReserved(userName);
      final List<Flight> booked = ItineraryManager.getBooked(userName);
      final List<Flight> canceled = ItineraryManager.getCanceled(userName);

      final ModelAndView mv = new ModelAndView("itineraryForCustomer");
      mv.addObject("reservedFlights", reserved);
      mv.addObject("bookedFlights", booked);
      mv.addObject("canceledFlights", canceled);
      return mv;
   }
}
