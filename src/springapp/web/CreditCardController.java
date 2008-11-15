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
	
	/*public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
    	throws Exception {
		
		final HttpSession session = request.getSession();
	    final String userName = LoginController.getUserName(session);
	    Customer customer = CustomerManager.getCustomer(userName);
	    
		//final ModelAndView mv = new ModelAndView(this.getFormView());
		final ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));
		
		mv.addObject("customer", customer);
		return mv;
		
	}*/

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
			 	         Object command, BindException errors) throws Exception {

		System.out.println("CreditCardController");
		
		Customer customer = (Customer)command;
		System.out.println("CCNo: " + customer.getCcNo());
		System.out.println("Expiration: " + customer.getExpiration());
		//CustomerManager.register(customer);
		
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
}
