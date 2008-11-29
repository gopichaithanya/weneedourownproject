package springapp.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutController implements Controller {

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      final HttpSession session = request.getSession();

      session.invalidate();
      return new ModelAndView(new RedirectView(FlightSearchForCustomerFormController.URL));

   }

}
