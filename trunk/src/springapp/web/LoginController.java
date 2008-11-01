package springapp.web;

import java.lang.*;
import java.util.List;
import hibernate.Customer;
import hibernate.manager.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class LoginController extends SimpleFormController {

   public static final String URL = "login.spring";

   protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final Customer customer = (Customer) command;
      final HttpSession session = request.getSession();

      final String afterLogin = (String) session
            .getAttribute(SessionConstants.LOGIN_REDIRECT_AFTER_LOGIN);
      session.removeAttribute(SessionConstants.LOGIN_REDIRECT_AFTER_LOGIN);

//      final String successView = (null == afterLogin || afterLogin.length() <= 0) ? getSuccessView()
//            : afterLogin;
      final ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));

      session.setAttribute(SessionConstants.USERNAME, customer.getUsername());
      return mv;
   }

   static public String getUserName(HttpSession session) {
      final String userId = (String) session.getAttribute(SessionConstants.USERNAME);
      if (null == userId || userId.length() <= 0)
         return null;
      return userId;
   }

   static public ModelAndView redirectToLogin(HttpSession session, String urlAfterLogin) {
      session.setAttribute(SessionConstants.LOGIN_REDIRECT_AFTER_LOGIN, urlAfterLogin);
      return new ModelAndView(new RedirectView(LoginController.URL));
   }

}
