package springapp.web;

import hibernate.manager.ItineraryManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import hibernate.Itinerary.ESeatClass;

/**
 * A Spring controller for reserve flight. This is for customer not for manager.
 */
public class ReserveFlightForCustomerController implements Controller {

   public static final String URL = "reserveFlightForCustomer.spring";
   private Logger logger = Logger.getLogger(getClass().getName());

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
         throws Exception {

      // Check login
      final HttpSession session = request.getSession();
      final String userName = LoginController.getUserName(session);
      if (null == userName)
         return LoginController.redirectToLogin(session, URL);
      logger.info("Username: " + userName);

      final ModelAndView mv = new ModelAndView(new RedirectView(ItineraryForCustomerController.URL));
      {
         boolean bRst = false;
         final Integer[] flights = (Integer[]) session
               .getAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER);
         logger.info("Flights: " + flights);

         final ESeatClass seatClass = (ESeatClass) session
               .getAttribute(SessionConstants.RESERVE_SEATCLASS_FOR_CUSTOMER);
         logger.info("Seat class: " + seatClass);

         final int numPassengers = (Integer) session
               .getAttribute(SessionConstants.RESERVE_NUM_PASSENGERS_FOR_CUSTOMER);
         logger.info("Num passengers: " + numPassengers);

         if (null != flights && flights.length > 0 && null != seatClass && numPassengers > 0) {
            bRst = true;
            for (final Object flight : flights)
               bRst &= ItineraryManager.reserve(userName, (Integer) flight, seatClass,
                     numPassengers);
         }
         mv.addObject("result", bRst);
         logger.info("Reservation result: " + bRst);
      }
      return mv;
   }
}
