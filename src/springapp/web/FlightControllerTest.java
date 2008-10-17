package springapp.web;

import static org.junit.Assert.*;

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
		String nowValue = (String) modelAndView.getModel().get("flights");
		assertNotNull(nowValue);
	}
}