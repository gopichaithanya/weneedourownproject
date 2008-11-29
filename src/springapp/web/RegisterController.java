package springapp.web;

import hibernate.manager.*;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hibernate.*;

/**
 * A Spring framework controller for Customer Registration
 */
public class RegisterController extends SimpleFormController {

   /**
    * bean name
    */
   public static final String URL = "register.spring";
   
   /**
    * handles the submitting for user registration
    * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
    */
   protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final Customer customer = (Customer) command;
      final boolean bRst = CustomerManager.register(customer);
      if (false == bRst)
         return new ModelAndView(new RedirectView(URL));

      final String username = customer.getUsername();
      final HttpSession session = request.getSession();
      session.setAttribute(SessionConstants.LOGIN_USERNAME, username);

      final ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));
      return mv;
   }

   /**
    * generates and returns default command object that will be used for Spring Framework
    * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
    */
   @Override
   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      return super.formBackingObject(request);
   }
}
