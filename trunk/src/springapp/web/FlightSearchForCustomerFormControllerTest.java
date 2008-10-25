package springapp.web;

import static org.junit.Assert.*;

import hibernate.manager.AirportManager;

import java.util.Calendar;
import java.util.Date;
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
      dummy.setDepartHour("BUG"); // this means "anytime"

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
   public void testGetTimeRange() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates1 = FlightSearchForCustomerFormController.getTimeRange("2008", "1", "1",
            "XXX", "XXX");
      calendar.set(2008, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates1[0].toString());
      calendar.set(2008, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates1[1].toString());

      final Date[] dates2 = FlightSearchForCustomerFormController.getTimeRange("2008", "1", "1",
            "1", "2");
      calendar.set(2007, 11, 31, 23, 0, 0);
      assertEquals(calendar.getTime().toString(), dates2[0].toString());
      calendar.set(2008, 0, 1, 3, 0, 0);
      assertEquals(calendar.getTime().toString(), dates2[1].toString());

      final Date[] dates3 = FlightSearchForCustomerFormController.getTimeRange("2008", "1", "1",
            "1", "XXX");
      calendar.set(2008, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates1[0].toString());
      calendar.set(2008, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates1[1].toString());
   }

   @Test
   public void testOnSubmit() throws Exception {
      final FlightSearchForCustomerFormController submit = new FlightSearchForCustomerFormController();
      final ModelAndView view = submit.onSubmit(null, null, null, null);
      assertTrue(((List) view.getModel().get("searchedFlights")).size() > 0);
   }
}
