package springapp.web;

import hibernate.Itinerary;
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

   /**
    * parameter name for flight number.
    */
   public static final String URL = "itineraryForCustomer.spring";

   /**
    * handle the requests from web browsers
    * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      final HttpSession session = request.getSession();
      String userName = LoginController.getUserName(session);
      if (null == userName)
         return LoginController.redirectToLogin(session, URL);

      final Boolean bReserve = (Boolean) session.getAttribute(SessionConstants.RESERVATION_RESULT);
      session.removeAttribute(SessionConstants.RESERVATION_RESULT);

      final List<Itinerary> reserved = ItineraryManager.getReserved(userName);
      final List<Itinerary> booked = ItineraryManager.getBooked(userName);
      final List<Itinerary> canceled = ItineraryManager.getCanceled(userName);

      final ModelAndView mv = new ModelAndView("itineraryForCustomer");
      mv.addObject("reservedItinerary", reserved);
      mv.addObject("bookedItinerary", booked);
      mv.addObject("canceledItinerary", canceled);
      mv.addObject("reservationResult", bReserve);
      return mv;
   }
}
