package springapp.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * TicketController is a Spring controller for the ticket page. It handles the creation of a ticket
 * to be displayed to the user as a confirmation of booking and payment 
 * 
 * @author Israa Taha
 */
public class TicketController extends SimpleFormController {
	
	/**
	 * Process the request and return a ModelAndView object which the DispatcherServlet will render.
	 * @return the ModelAndView object for the ticket page
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		final HttpSession session = request.getSession();
		
		ModelAndView mv = new ModelAndView("ticket");
		String ticket = (String)session.getAttribute(SessionConstants.CREDIT_TICKET);
		//String ticket = "US-292-jjohnson-ZZZ";
		mv.addObject("ticket", ticket);
		return mv;
	}
}