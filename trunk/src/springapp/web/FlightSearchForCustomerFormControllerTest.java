package springapp.web;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class FlightSearchForCustomerFormControllerTest {

   @Test
   public void testGetAirportList() {
      final List<String[]> airports = FlightSearchForCustomerFormController
            .getAirportList();
      assertNotNull(airports);
      assertEquals(567, airports.size());
   }

   @Test
   public void testHandleRequestView() throws Exception {
      final FlightSearchForCustomerFormController controller = new FlightSearchForCustomerFormController();
      ModelAndView modelAndView = controller.handleRequest(null, null);
      assertEquals("flightSearchForCustomerForm", modelAndView.getViewName());
      assertNotNull(modelAndView.getModel());
      
      final List airports = (List) modelAndView.getModel().get("airports");
      assertNotNull(airports);
   }
}
