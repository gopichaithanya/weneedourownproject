package springapp.web;

import hibernate.manager.ItineraryManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

/**
 * A Spring controller for reserve flight. This is for customer not for manager.
 */
public class ReserveFlightForCustomerController implements Controller {

   public static final String URL = "reserveFlightForCustomer.spring";

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
         throws Exception {

      // Check login
      final HttpSession session = request.getSession();
      final String userName = LoginController.getUserName(session);
      if (null == userName)
         return LoginController.redirectToLogin(session, URL);

      final ModelAndView mv = new ModelAndView(new RedirectView(ItineraryForCustomerController.URL));
      {
         boolean bRst = false;
         Integer[] flights = null;
         try {
            flights = (Integer[]) session
                  .getAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER);
         } catch (ClassCastException e) {
         }

         if (null != flights && flights.length > 0) {
            bRst = true;
            for (final Object flight : flights)
               bRst &= ItineraryManager.reserve(userName, (Integer) flight);
         }
         mv.addObject("result", bRst);
      }
      return mv;
   }
}
