package springapp.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class TicketController extends SimpleFormController {
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		final HttpSession session = request.getSession();
		
		ModelAndView mv = new ModelAndView("ticket");
		String ticket = (String)session.getAttribute(SessionConstants.TICKET);
		//String ticket = "US-292-jjohnson-ZZZ";
		mv.addObject("ticket", ticket);
		return mv;
	}
}