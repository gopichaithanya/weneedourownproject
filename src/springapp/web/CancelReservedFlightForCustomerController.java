package springapp.web;

import hibernate.manager.ItineraryManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

/**
 * A Spring Controller for cancel reserved flight. This functionality is for customer not for manager.
 */
public class CancelReservedFlightForCustomerController implements Controller {

   /**
    * parameter name for flight number.
    */
   public static final String PARAM_FLIGHT_NO = "flightNo";
   public static final String URL = "cancelReservedFlightForCustomer.spring";

   /**
    * This method cancels a reserved flight of a customer who is logged in.
    * The flight number should be passes as parameter.
    * And then the page will be redirected to itinerary page.
    */
   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
         throws Exception {
      // Check login
      final HttpSession session = request.getSession();
      final String userName = LoginController.getUserName(session);
      if (null == userName)
         return LoginController.redirectToLogin(session, URL);

      final ModelAndView mv = new ModelAndView(new RedirectView(ItineraryForCustomerController.URL));
      {
         Integer flightNo = null;
         try {
            flightNo = Integer.valueOf(request.getParameter(PARAM_FLIGHT_NO));
         } catch (NumberFormatException e) {
         }

         boolean bRst = false;
         if (null != flightNo)
            bRst = ItineraryManager.cancelReserved(userName, flightNo);
         mv.addObject("result", bRst);
      }
      return mv;
   }
}
