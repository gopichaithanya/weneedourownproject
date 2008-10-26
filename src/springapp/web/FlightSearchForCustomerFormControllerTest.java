package springapp.web;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

@SuppressWarnings("unchecked")
public class FlightSearchForCustomerFormControllerTest {

   @Before
   public void before() {
      FlightSearchForCustomerFormController.bFlagSearch = true;
   }

   @Test(expected = NullPointerException.class)
   public void testHandleRequestView() throws Exception {
      final FlightSearchForCustomerFormController controller = new FlightSearchForCustomerFormController();
      final ModelAndView mv = controller.handleRequest(null, null);
      assertNotNull(mv);
      fail();
   }

   @Test
   public void testListFlightsNull() {
      final List flightList = FlightSearchForCustomerFormController.getFlightList(null, null, null,
            null, null, null, null);
      assertNotNull(flightList);
      assertTrue(flightList.size() > 0);
   }

   @Test
   public void testListFlightsAnytime() {
      final List flightList = FlightSearchForCustomerFormController.getFlightList("DEN", "BWI",
            "2009", "1", "4", "BUG", "XXX");
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

      final List flightList = FlightSearchForCustomerFormController.getFlightList("DEN", "BWI",
            "2009", "1", "4", "10", "2");
      assertNotNull(flightList);
      assertTrue(flightList.size() > 0);
   }

   @Test
   public void testGetTimeRange1() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightSearchForCustomerFormController.getTimeRange("2008", "1", "1",
            "XXX", "XXX");
      calendar.set(2008, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2008, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRange2() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightSearchForCustomerFormController.getTimeRange("2008", "1", "1",
            "1", "2");
      calendar.set(2007, 11, 31, 23, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2008, 0, 1, 3, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRange3() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightSearchForCustomerFormController.getTimeRange("2008", "1", "1",
            "1", "XXX");
      calendar.set(2008, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2008, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test(expected = NumberFormatException.class)
   public void testGetTimeRangeBadYear() {
      FlightSearchForCustomerFormController.getTimeRange("XXX", "1", "1", "1", "XXX");
      fail();
   }

   @Test(expected = NumberFormatException.class)
   public void testGetTimeRangeBadMonth() {
      FlightSearchForCustomerFormController.getTimeRange("2009", "X", "1", "1", "XXX");
      fail();
   }

   @Test(expected = NumberFormatException.class)
   public void testGetTimeRangeBadDay() {
      FlightSearchForCustomerFormController.getTimeRange("2009", "1", "X", "1", "XXX");
      fail();
   }

   @Test
   public void testGetTimeRangeBadHour() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightSearchForCustomerFormController.getTimeRange("2009", "1", "1",
            "X", "XXX");
      calendar.set(2009, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2009, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRangeBadRange1() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightSearchForCustomerFormController.getTimeRange("2009", "1", "1",
            "2", "XXX");
      calendar.set(2009, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2009, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRangeBadRange2() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightSearchForCustomerFormController.getTimeRange("2009", "1", "1",
            "2", "-1");
      calendar.set(2009, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2009, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRangeBadRange3() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightSearchForCustomerFormController.getTimeRange("2009", "1", "1",
            "2", "0");
      calendar.set(2009, 0, 1, 2, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2009, 0, 1, 2, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testOnSubmitNull() throws Exception {
      final FlightSearchForCustomerFormController submit = new FlightSearchForCustomerFormController();
      final ModelAndView mv = submit.onSubmit(null, null, null, null);
      assertNotNull(mv);
      assertTrue(mv.getModel().isEmpty());
   }

   @Test
   public void testOnSubmitAfterStepFirst() throws Exception {
      final FlightSearchForCustomer cmd = new FlightSearchForCustomer();
      cmd.setTripType(FlightSearchForCustomer.KEYWORD_roundTrip);
      final FlightSearchForCustomerFormController submit = new FlightSearchForCustomerFormController();
      final ModelAndView mv = submit.onSubmit(null, null, cmd, null);
      assertNotNull(mv);
      assertTrue(mv.getModel().containsKey("searchedDepartFlights"));
   }

   @Test
   public void testOnSubmitAfterStepSecondWithRoundTrip() throws Exception {
      final FlightSearchForCustomer cmd = new FlightSearchForCustomer();
      cmd.setDepartFlightNo("157");
      cmd.setTripType(FlightSearchForCustomer.KEYWORD_roundTrip);
      final FlightSearchForCustomerFormController submit = new FlightSearchForCustomerFormController();
      final ModelAndView mv = submit.onSubmit(null, null, cmd, null);
      assertNotNull(mv);
      assertTrue(mv.getModel().containsKey("selectedDepartFlight"));
      assertTrue(mv.getModel().containsKey("searchedReturnFlights"));
   }

   @Test
   public void testOnSubmitAfterStepSecondWithOneWayTrip() throws Exception {
      final FlightSearchForCustomer cmd = new FlightSearchForCustomer();
      cmd.setTripType(FlightSearchForCustomer.KEYWORD_oneWayTrip);
      cmd.setDepartFlightNo("157");
      final FlightSearchForCustomerFormController submit = new FlightSearchForCustomerFormController();
      try {
         final ModelAndView mv = submit.onSubmit(null, null, cmd, null);
         assertNotNull(mv);
         fail();
      } catch (ServletException e) {
         assertEquals("successView isn't set", e.getMessage());
      }
   }

   @Test
   public void testOnSubmitAfterStepThird() throws Exception {
      final FlightSearchForCustomer cmd = new FlightSearchForCustomer();
      cmd.setTripType(FlightSearchForCustomer.KEYWORD_roundTrip);
      cmd.setDepartFlightNo("157");
      cmd.setReturnFlightNo("157");
      final FlightSearchForCustomerFormController submit = new FlightSearchForCustomerFormController();
      try {
         final ModelAndView mv = submit.onSubmit(null, null, cmd, null);
         assertNotNull(mv);
         fail();
      } catch (ServletException e) {
         assertEquals("successView isn't set", e.getMessage());
      }
   }
}
