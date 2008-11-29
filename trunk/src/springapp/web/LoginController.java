package springapp.web;

import hibernate.Customer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class LoginController extends SimpleFormController {

   public static final String URL = "login.spring";
   private static Logger logger = Logger.getLogger(LoginController.class.getName());

   protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final Customer customer = (Customer) command;
      final HttpSession session = request.getSession();

      final String afterLogin = (String) session
            .getAttribute(SessionConstants.LOGIN_REDIRECT_AFTER_LOGIN);
      session.removeAttribute(SessionConstants.LOGIN_REDIRECT_AFTER_LOGIN);

      final String successView = (null == afterLogin || afterLogin.length() <= 0) ? getSuccessView()
            : afterLogin;
      final String username = customer.getUsername();

      ModelAndView mv = new ModelAndView(new RedirectView(successView));
      session.setAttribute(SessionConstants.LOGIN_USERNAME, username);
      return mv;
   }

   static public String getUserName(HttpSession session) {
      final String userId = (String) session.getAttribute(SessionConstants.LOGIN_USERNAME);
      if (null == userId || userId.length() <= 0)
         return null;
      return userId;
   }

   static public ModelAndView redirectToLogin(HttpSession session, String urlAfterLogin) {
      logger.info("redirectToLogin: " + urlAfterLogin);
      session.setAttribute(SessionConstants.LOGIN_REDIRECT_AFTER_LOGIN, urlAfterLogin);
      return new ModelAndView(new RedirectView(LoginController.URL));
   }

   @Override
   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      final Customer c = (Customer) super.formBackingObject(request);
      c.setUsername("tomcat");
      c.setPassword("tomcat");
      return c;
   }

}
