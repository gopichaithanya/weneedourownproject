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

public class InventoryReportController extends SimpleFormController {
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		final ModelAndView mv = new ModelAndView("inventory");
		
		List<Flight> flight = FlightManager.listFlights();
		mv.addObject("flightList", flight);
		
		return mv;
	}
}