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
	
	 protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
	         Object command, BindException errors) throws Exception {
		 Customer customer = (Customer)command;
		 	
	      final ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));
	      final List<String[]> airports = AirportManager.getAirportCodeAndName();
	      mv.addObject("airports", airports);
			Cookie c = new Cookie("username", customer.getUsername());
			response.addCookie(c);
	      //HttpSession session = request.getSession(true);
	      //session.setAttribute("username", customer.getUsername());
	      return mv;

	   }

}
