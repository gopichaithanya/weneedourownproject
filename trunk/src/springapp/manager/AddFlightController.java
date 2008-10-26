package springapp.manager;

import hibernate.manager.AirlineManager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class AddFlightController  extends SimpleFormController 
{
	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
    throws Exception 
    {
		final ModelAndView mv = super.handleRequest(req, resp);
		
		final List<String[]> airlines = AirlineManager.getAirlineCodeAndName();
		mv.addObject("airlines", airlines);
	
		return mv;
	}
}
