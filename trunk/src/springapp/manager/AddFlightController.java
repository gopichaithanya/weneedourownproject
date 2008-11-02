package springapp.manager;

import hibernate.Flight;
import hibernate.manager.AirlineManager;
import hibernate.manager.AirportManager;
import hibernate.manager.FlightManager;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class AddFlightController  extends SimpleFormController 
{
	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
    throws Exception 
    {
		final ModelAndView mv = super.handleRequest(req, resp);
		
		final List<String[]> airlines = AirlineManager.getAirlineCodeAndName();
		mv.addObject("airlines", airlines);
		
		final List<String[]> airports = AirportManager.getAirportCodeAndName();
		mv.addObject("airports",airports);
		
		return mv;
	}
	
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
	         Object command, BindException errors) throws Exception 
	{
		final ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));
		Flight flight = (Flight)command;
		FlightManager.addFlight(flight);		
		return mv;
	}
}
