package springapp.web;

import hibernate.Customer;
import hibernate.Flight;
import hibernate.Itinerary;
import hibernate.manager.ItineraryManager;
import hibernate.util.HibernateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

public class ReserveFlightForCustomerController implements Controller {

   public static final String URL = "reserveFlightForCustomer.spring";

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
         throws Exception {

      final HttpSession session = request.getSession();
      final String userName = LoginController.getUserName(session);
      if (null == userName)
         return LoginController.redirectToLogin(session, URL);

      int[] flights = (int[]) session.getAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER);
      for (int flight : flights)
         ItineraryManager.reserve(userName, flight);

      return new ModelAndView(new RedirectView(ItineraryForCustomerController.URL));
   }

}
