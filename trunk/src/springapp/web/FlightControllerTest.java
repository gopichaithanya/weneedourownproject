package springapp.web;

import static org.junit.Assert.*;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import springapp.web.HelloController;

import org.junit.*;

public class FlightControllerTest {

	@Test
	public void testHandleRequestView() throws Exception {
		final FlightController controller = new FlightController();
		ModelAndView modelAndView = controller.handleRequest(null, null);
		assertEquals("flightList", modelAndView.getViewName());
		assertNotNull(modelAndView.getModel());
		final List flights = (List) modelAndView.getModel().get("flights");
		assertNotNull(flights);
	}
}