package springapp.web;

import static org.junit.Assert.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

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
      cmd.setDepartLocation("YNG");
      cmd.setArrivalLocation("SGH");
      final FlightSearchForCustomerFormController submit = new FlightSearchForCustomerFormController();

      final ModelAndView mv = submit.onSubmit(null, null, cmd, null);
      assertNotNull(mv);
      assertTrue(mv.getModel().containsKey("searchedDepartFlights"));
      assertEquals("148538.6480N", mv.getModel().get("departAirportLat"));
      assertEquals("290444.7480W", mv.getModel().get("departAirportLng"));
      assertEquals("143425.0450N", mv.getModel().get("arrivalAirportLat"));
      assertEquals("301824.5765W", mv.getModel().get("arrivalAirportLng"));
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
