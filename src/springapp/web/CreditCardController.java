package springapp.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import hibernate.*;
import hibernate.manager.*;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreditCardController extends SimpleFormController {

//   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
//         throws Exception {
//
//      ModelAndView mv = null;
//      final Object command = super.getCommand(request);
//      if (null != command) {
//         mv = super.handleRequest(request, response);
//      } else {
//         mv = new ModelAndView(this.getFormView());
//      }
//
//      return mv;
//
//   }

   protected void doSubmitAction(Object command) {
      Customer customer = (Customer) command;
      //return new ModelAndView(new RedirectView(getSuccessView()));

   }

//   protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
//         Object command, BindException errors) throws Exception {
//
//      System.out.println("CreditCardController");
//
//      Customer customer = (Customer) command;
//      System.out.println("CCNo: " + customer.getCcNo());
//      System.out.println("Expiration: " + customer.getExpiration());
//      //CustomerManager.register(customer);
//
//      return new ModelAndView(new RedirectView(getSuccessView()));
//   }

   @Override
   protected Object formBackingObject(HttpServletRequest request) throws Exception {

      final HttpSession session = request.getSession();
      final String userName = LoginController.getUserName(session);
//   Customer customer = CustomerManager.getCustomer(userName);
      Customer customer = new Customer();
      customer.setCcNo((long) 1111);
      customer.setExpiration(111111);
      return customer;
   }
}
