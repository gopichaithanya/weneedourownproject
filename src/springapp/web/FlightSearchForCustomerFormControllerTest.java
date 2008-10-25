package springapp.web;

import static org.junit.Assert.*;

import hibernate.manager.AirportManager;

import java.util.List;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@SuppressWarnings("unchecked")
public class FlightSearchForCustomerFormControllerTest {

   @Test(expected = NullPointerException.class)
   public void testHandleRequestView() throws Exception {
      final FlightSearchForCustomerFormController controller = new FlightSearchForCustomerFormController();
      final ModelAndView modelAndView = controller.handleRequest(null, null);
   }

   @Test
   public void testListFlightsNull() {
      final List flightList = FlightSearchForCustomerFormController.getFlightList(null);
      assertNotNull(flightList);
      assertTrue(flightList.size() > 0);
   }

   @Test
   public void testListFlightsAnytime() {
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
      dummy.setDepartHour(FlightSearchForCustomerFormController.KEYWORD_searchingAnytime);

      final List flightList = FlightSearchForCustomerFormController.getFlightList(dummy);
      assertNotNull(flightList);
      assertTrue(flightList.size() > 0);
   }

   @Test
   public void testListFlightsCertainTime() {
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
      dummy.setDepartHour("10");
      dummy.setSearchingHourRange("2");

      final List flightList = FlightSearchForCustomerFormController.getFlightList(dummy);
      assertNotNull(flightList);
      assertTrue(flightList.size() > 0);
   }

   @Test
   public void testOnSubmit() {
//      final FlightSearchForCustomerFormController submit = new FlightSearchForCustomerFormController();
//      final ModelAndView view = submit.onSubmit(new FlightSearchForCustomer());
//      assertTrue(view.getView() instanceof RedirectView);
   }
}
