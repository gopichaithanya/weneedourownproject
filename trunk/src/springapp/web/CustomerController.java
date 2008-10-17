package springapp.web;

import org.hibernate.Session;
import util.HibernateUtil;


import org.springframework.web.servlet.mvc.SimpleFormController;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import database.hibernate.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class CustomerController extends SimpleFormController {
	protected void doSubmitAction(Object o) throws Exception
	{
		Customer customer = (Customer)o;
		//final Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		//session.beginTransaction();

		//session.save(customer);
		
		
	}
	
	
}
