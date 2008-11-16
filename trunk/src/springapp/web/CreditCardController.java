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
	
	private Customer c = null;
	
	protected void doSubmitAction(Object command) {
		Customer customer = (Customer) command;
		c = CustomerManager.updateCcNoAndExpiration(customer.getUsername(), customer.getCcNo(), customer.getExpiration());
		System.out.println(c.getUsername());
		System.out.println(c.getCcNo());
		System.out.println(c.getExpiration());
		
		//return new ModelAndView(new RedirectView(getSuccessView()));
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		final HttpSession session = request.getSession();
		final String userName = LoginController.getUserName(session);
		Customer customer = CustomerManager.getCustomer(userName);
		c = customer;
		return customer;
	}
}
