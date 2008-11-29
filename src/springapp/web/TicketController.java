package springapp.web;

import hibernate.Itinerary;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * TicketController is a Spring controller for the ticket page. It handles the creation of a ticket
 * to be displayed to the user as a confirmation of booking and payment 
 * 
 * @author Israa Taha
 */
public class TicketController extends SimpleFormController {

   /**
    * Process the request and return a ModelAndView object which the DispatcherServlet will render.
    * @return the ModelAndView object for the ticket page
    */
   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      // Check login
      final HttpSession session = request.getSession();
      final String userName = LoginController.getUserName(session);
      if (null == userName)
         return LoginController.redirectToLogin(session, ItineraryForCustomerController.URL);

      final Itinerary it = (Itinerary) session.getAttribute(SessionConstants.BOOKED_ITINERARY);
      session.removeAttribute(SessionConstants.BOOKED_ITINERARY);

      final ModelAndView mv = new ModelAndView("ticket");
      mv.addObject("itinerary", it);
      return mv;
   }
}