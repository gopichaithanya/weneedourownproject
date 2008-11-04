package springapp.web;

import org.hibernate.Session;
import hibernate.manager.*;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hibernate.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;

public class CustomerController extends SimpleFormController {
	 protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
	         Object command, BindException errors) throws Exception 
	{
		 final ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));
		Customer customer = (Customer)command;
		CustomerManager.register(customer);
		
		 final HttpSession session = request.getSession();

	      final String afterLogin = (String) session
	            .getAttribute(SessionConstants.LOGIN_REDIRECT_AFTER_LOGIN);
	      session.removeAttribute(SessionConstants.LOGIN_REDIRECT_AFTER_LOGIN);
		//Cookie c = new Cookie("username", customer.getUsername());
		//response.addCookie(c);
	      session.setAttribute(SessionConstants.USERNAME, customer.getUsername());
		//HttpSession session = request.getSession(true);
		//session.setAttribute("username", customer.getUsername());
		
		return mv;
		
		
	}
	 
	   static public String getUserName(HttpSession session) {
		      final String userId = (String) session.getAttribute(SessionConstants.USERNAME);
		      if (null == userId || userId.length() <= 0)
		         return null;
		      return userId;
		   }
	
	
}