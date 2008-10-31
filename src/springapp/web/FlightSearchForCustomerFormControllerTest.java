package springapp.web;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@SuppressWarnings("unchecked")
public class FlightSearchForCustomerFormControllerTest {

   @Before
   public void before() {
      FlightSearchForCustomerFormController.bFlagSearch = true;
   }

   @Test
   public void testHandleRequest() throws Exception {
      final FlightSearchForCustomerFormController controller = new FlightSearchForCustomerFormController();
      final HttpServletRequest req = new NullHttpServletRequest();
      final HttpServletResponse resp = new NullHttpServletResponse();
      //TODO
//      final ModelAndView mv = controller.handleRequest(req, resp);
//      assertNotNull(mv);
   }

   @Test
   public void testListFlightsNull() {
      final List flightList = FlightSearchForCustomerFormController.getFlightList(null, null, 0, 0,
            0, 0, 0);
      assertNotNull(flightList);
      assertTrue(flightList.size() > 0);
   }

   @Test
   public void testListFlightsAnytime() {
      final List flightList = FlightSearchForCustomerFormController.getFlightList("DEN", "BWI",
            2009, 1, 4, -99, -99);
      assertNotNull(flightList);
      assertTrue(flightList.size() > 0);
   }

   @Test
   public void testListFlightsCertainTime() {
      final List flightList = FlightSearchForCustomerFormController.getFlightList("DEN", "BWI",
            2009, 1, 4, 10, 2);
      assertNotNull(flightList);
      assertTrue(flightList.size() > 0);
   }

   @Test
   public void testGetTimeRange1() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightSearchForCustomerFormController.getTimeRange(2008, 1, 1, -99, -99);
      calendar.set(2008, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2008, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRange2() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightSearchForCustomerFormController.getTimeRange(2008, 1, 1, 1, 2);
      calendar.set(2007, 11, 31, 23, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2008, 0, 1, 3, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRange3() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightSearchForCustomerFormController.getTimeRange(2008, 1, 1, 1, -99);
      calendar.set(2008, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2008, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRangeBadMonth() {
      assertNull(FlightSearchForCustomerFormController.getTimeRange(2009, -9, 1, 1, -99));
   }

   @Test
   public void testGetTimeRangeBadDay() {
      assertNull(FlightSearchForCustomerFormController.getTimeRange(2009, 1, -9, 1, -99));
   }

   @Test
   public void testGetTimeRangeBadHour() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightSearchForCustomerFormController.getTimeRange(2009, 1, 1, -99, -99);
      calendar.set(2009, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2009, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRangeBadRange1() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightSearchForCustomerFormController.getTimeRange(2009, 1, 1, 2, -99);
      calendar.set(2009, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2009, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRangeBadRange2() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightSearchForCustomerFormController.getTimeRange(2009, 1, 1, 2, -1);
      calendar.set(2009, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2009, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRangeBadRange3() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightSearchForCustomerFormController.getTimeRange(2009, 1, 1, 2, 0);
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
      cmd.setTripType(FlightSearchForCustomer.ETripType.ROUND_TRIP);
      final FlightSearchForCustomerFormController submit = new FlightSearchForCustomerFormController();
      final ModelAndView mv = submit.onSubmit(null, null, cmd, null);
      assertNotNull(mv);
      assertTrue(mv.getModel().containsKey("searchedDepartFlights"));
   }

   @Test
   public void testOnSubmitAfterStepSecondWithRoundTrip() throws Exception {
      final FlightSearchForCustomer cmd = new FlightSearchForCustomer();
      cmd.setDepartFlightNo(157);
      cmd.setTripType(FlightSearchForCustomer.ETripType.ROUND_TRIP);
      final FlightSearchForCustomerFormController submit = new FlightSearchForCustomerFormController();
      final ModelAndView mv = submit.onSubmit(null, null, cmd, null);
      assertNotNull(mv);
      assertTrue(mv.getModel().containsKey("selectedDepartFlight"));
      assertTrue(mv.getModel().containsKey("searchedReturnFlights"));
   }

   @Test
   public void testOnSubmitAfterStepSecondWithOneWayTrip() throws Exception {
      final FlightSearchForCustomer cmd = new FlightSearchForCustomer();
      cmd.setTripType(FlightSearchForCustomer.ETripType.ONEWAY_TRIP);
      cmd.setDepartFlightNo(157);
      final FlightSearchForCustomerFormController submit = new FlightSearchForCustomerFormController();
      try {
         final HttpServletRequest request = new NullHttpServletRequest();
         final ModelAndView mv = submit.onSubmit(request, null, cmd, null);
         assertNotNull(mv);
         final HttpSession session = request.getSession();
         assertNull(session.getAttribute("Nothing is in"));
         assertNotNull(session.getAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER));
      } catch (ServletException e) {
         fail();
      }
   }

   @Test
   public void testOnSubmitAfterStepThird() throws Exception {
      final FlightSearchForCustomer cmd = new FlightSearchForCustomer();
      cmd.setTripType(FlightSearchForCustomer.ETripType.ROUND_TRIP);
      cmd.setDepartFlightNo(157);
      cmd.setReturnFlightNo(157);
      final FlightSearchForCustomerFormController submit = new FlightSearchForCustomerFormController();
      try {
         final HttpServletRequest request = new NullHttpServletRequest();
         final ModelAndView mv = submit.onSubmit(request, null, cmd, null);
         assertNotNull(mv);
         final HttpSession session = request.getSession();
         assertNull(session.getAttribute("Nothing is in"));
         assertNotNull(session.getAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER));
      } catch (ServletException e) {
         fail();
      }
   }
}
