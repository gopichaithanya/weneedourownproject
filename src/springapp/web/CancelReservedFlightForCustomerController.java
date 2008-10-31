package springapp.web;

import hibernate.manager.ItineraryManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

public class CancelReservedFlightForCustomerController implements Controller {

   public static final String PARAM_FLIGHT_NO = "flightNo";
   public static final String URL = "cancelReservedFlightForCustomer.spring";

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
