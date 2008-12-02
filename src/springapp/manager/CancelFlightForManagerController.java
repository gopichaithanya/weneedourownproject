package springapp.manager;

import hibernate.manager.FlightManager;
import hibernate.manager.ItineraryManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import springapp.web.InventoryReportController;
import springapp.web.SessionConstants;

public class CancelFlightForManagerController implements Controller {

   public static String PARAM_FLIGHT_NO = "flightNo";

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
         throws Exception {

      Integer flightNo = null;
      try {
         flightNo = Integer.valueOf(request.getParameter(PARAM_FLIGHT_NO));
      } catch (NumberFormatException e) {
      }

      boolean bRst = false;
      if (null != flightNo)
         bRst = FlightManager.cancelFlight(flightNo);
      if(bRst) {
         final HttpSession session = request.getSession();
         session.setAttribute(SessionConstants.CANCELED_FLIGHT_NO, flightNo);
      }

      final ModelAndView mv = new ModelAndView(new RedirectView(InventoryReportController.URL));
      mv.addObject("result", bRst);
      return mv;
   }

}
