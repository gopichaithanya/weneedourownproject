package springapp.web;

import static org.junit.Assert.*;
import java.util.List;
import org.springframework.web.servlet.ModelAndView;
import org.junit.*;

public class FlightListForCustomerControllerTest {

	@Test
	public void testHandleRequestView() throws Exception {
		final FlightListForCustomerController controller = new FlightListForCustomerController();
		ModelAndView modelAndView = controller.handleRequest(null, null);
		assertEquals("flightList", modelAndView.getViewName());
		assertNotNull(modelAndView.getModel());
		
		final List flights = (List) modelAndView.getModel().get("flights");
		assertNotNull(flights);
	}
}