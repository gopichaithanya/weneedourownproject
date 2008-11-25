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
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * CreditCardController is a Spring controller for the credit card page.
 * 
 * @author Israa Taha
 */
@SuppressWarnings("unused")
public class CreditCardController extends SimpleFormController {
	
	/**
	 * The flight number of the flight to be booked
	 */
	private Integer flightNo = null;
	
	/**
	 * The customer booking the flight
	 */
	private Customer c = null;
	
	/**
	 * Submit callback with all parameters.
	 * @param request - current servlet request
     * @param response - current servlet response
     * @param command - form object with request parameters bound onto it
     * @param errors - Errors instance without errors (subclass can add errors if it wants to) 
	 * @return the ModelAndView object
	 */
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
	         Object command, BindException errors) throws Exception {
		
		final HttpSession session = request.getSession();
		
		//update customer's credit card information
		Customer customer = (Customer) command;
		c = CustomerManager.updateCcNoAndExpiration(customer.getUsername(), customer.getCcNo(), customer.getExpiration());
		
		System.out.println(c.getUsername());
		System.out.println(c.getCcNo());
		System.out.println(c.getExpiration());
		
		//create the ticket object
		String ticket;
		
		Random rand = new Random();
		Flight f = FlightManager.getFlight(flightNo);
		ticket = f.getAirline().getCode()+"-"+flightNo+"-"+c.getUsername().toUpperCase()+"-"+(rand.nextInt(900)+100);
		System.out.println(f.getAirline().getCode()+"-"+flightNo+"-"+c.getUsername()+"-"+(rand.nextInt(900)+100));
		
		//book the flight once credit card is validated and a ticket is generated
		ItineraryManager.book(c.getUsername(), flightNo, ticket);
		
		session.setAttribute(SessionConstants.TICKET, ticket);
		ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView())); 
		//mv.addObject("ticket", ticket);
		return mv;
	}

	
	/**
	 * Retrieve a backing object for the current form from the given request
	 * @param request - current HTTP request
	 * @return the backing object 
	 */
	@Override			
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		final HttpSession session = request.getSession();
		final String userName = LoginController.getUserName(session);
		
		//get the customer object with the username from the database
		Customer customer = CustomerManager.getCustomer(userName);
		c = customer;
		
		//get the flight number of flight to be booked
		try {
			flightNo = Integer.valueOf(request.getParameter("flightNo"));
			System.out.println(flightNo);
		} catch (NumberFormatException e) {
			
		}
		
		return customer;
	}
}
