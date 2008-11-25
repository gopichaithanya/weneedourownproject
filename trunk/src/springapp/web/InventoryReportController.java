package springapp.web;

import hibernate.Flight;
import hibernate.manager.FlightManager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * InventoryReportController is a Spring controller for the inventory page. It retrieves the
 * necessary inventory information to be displayed
 * 
 * @author Israa Taha
 */
public class InventoryReportController extends SimpleFormController {
	
	/**
	 * Process the request and return a ModelAndView object which the DispatcherServlet will render.
	 * @return the ModelAndView object for the inventory page
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		final ModelAndView mv = new ModelAndView("inventory");
		
		List<Flight> flight = FlightManager.listFlights();
		mv.addObject("flightList", flight);
		
		return mv;
	}
}