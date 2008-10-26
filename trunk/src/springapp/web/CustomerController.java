package springapp.web;

import org.hibernate.Session;
import hibernate.util.HibernateUtil;
import hibernate.manager.*;

import org.springframework.web.servlet.mvc.SimpleFormController;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hibernate.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;

public class CustomerController extends SimpleFormController {
	protected void doSubmitAction(Object o) throws ServletException, IOException
	{
		Customer customer = (Customer)o;
		CustomerManager.register(customer);

		
	}
	
	
}
