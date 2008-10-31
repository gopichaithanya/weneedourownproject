package springapp.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.web.servlet.mvc.SimpleFormController;

import hibernate.*;
import hibernate.manager.*;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class CreditCardController extends SimpleFormController {
	protected void doSubmitAction(Object o) throws ServletException, IOException
	{
		System.out.println("CreditCardController");
		
		Customer customer = (Customer)o;
		System.out.println("CCNo: " + customer.getCcNo());
		System.out.println("Expiration: " + customer.getExpiration());
		//CustomerManager.register(customer);	
	}
}
