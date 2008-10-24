package springapp.web;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@SuppressWarnings("unchecked")
public class FlightSearchForCustomerFormControllerTest {

   @Test
   public void testGetAirportList() {
      final List<String[]> airports = FlightSearchForCustomerFormController.getAirportList();
      assertNotNull(airports);
      assertEquals(567, airports.size());
   }

   @Test(expected = NullPointerException.class)
   public void testHandleRequestView() throws Exception {
      final FlightSearchForCustomerFormController controller = new FlightSearchForCustomerFormController();
      final ModelAndView modelAndView = controller.handleRequest(null, null);
   }

   @Test
   public void testListFlights() {
      final List flightList = FlightSearchForCustomerFormController.getFlightList();
      assertTrue(flightList.size() > 0);
   }

   @Test
   public void testOnSubmit() {
//      final FlightSearchForCustomerFormController submit = new FlightSearchForCustomerFormController();
//      final ModelAndView view = submit.onSubmit(new FlightSearchForCustomer());
//      assertTrue(view.getView() instanceof RedirectView);
   }
}
