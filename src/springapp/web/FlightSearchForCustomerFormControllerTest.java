package springapp.web;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.web.servlet.ModelAndView;

import springapp.manager.MockServletContextWebContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = MockServletContextWebContextLoader.class, locations = { "/xml/springapp-servlet.xml" })
public class FlightSearchForCustomerFormControllerTest extends AbstractJUnit4SpringContextTests {

   private String beanName = null;
   private MockHttpServletRequest request = null;
   private MockHttpServletResponse response = null;
   private MockHttpSession session = null;
   private FlightSearchForCustomerFormController ctrl = null;
   private FlightSearchForCustomer cmd = null;

   @Before
   public void before() throws Exception {
      FlightSearchForCustomerFormController.bFlagSearch = true;

      beanName = applicationContext
            .getBeanNamesForType(FlightSearchForCustomerFormController.class)[0];
      assertEquals("/" + FlightSearchForCustomerFormController.URL, beanName);

      request = new MockHttpServletRequest("POST", beanName);
      assertNotNull(request);

      response = new MockHttpServletResponse();
      assertNotNull(response);

      session = (MockHttpSession) request.getSession();
      assertNotNull(session);

      ctrl = (FlightSearchForCustomerFormController) this.applicationContext.getBean(beanName);
      assertNotNull(ctrl);

      cmd = (FlightSearchForCustomer) ctrl.getCommandClass().getConstructor(new Class[] {})
            .newInstance(new Object[] {});
      assertNotNull(cmd);

   }

   @Test
   public void testHandleRequest() throws Exception {
      final ModelAndView mv = ctrl.handleRequest(request, response);
      ModelAndViewAssert.assertViewName(mv, ctrl.getFormView());
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "airports");
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "tripTypes");
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "seatClass");
   }

   @Test
   public void testOnSubmitNull() throws Exception {
      final ModelAndView mv = ctrl.onSubmit(null, null, null, null);
      ModelAndViewAssert.assertViewName(mv, ctrl.getFormView());
      assertTrue(mv.getModel().isEmpty());
   }

   @Test
   public void testOnSubmitAfterStepFirst() throws Exception {
      cmd.setTripType(FlightSearchForCustomer.ETripType.ROUND_TRIP);
      cmd.setDepartLocation("YNG");
      cmd.setArrivalLocation("SGH");

      final ModelAndView mv = ctrl.onSubmit(null, null, cmd, null);
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "searchedDepartFlights");
      assertEquals(41.260555f, (Float) mv.getModel().get("departAirportLat"), 0.01);
      assertEquals(-80.678889f, (Float) mv.getModel().get("departAirportLng"), 0.01);
      assertEquals(39.84028f, (Float) mv.getModel().get("arrivalAirportLat"), 0.01);
      assertEquals(-83.8399f, (Float) mv.getModel().get("arrivalAirportLng"), 0.01);
   }

   @Test
   public void testOnSubmitAfterStepSecondWithRoundTrip() throws Exception {
      cmd.setDepartFlightNo(157);
      cmd.setTripType(FlightSearchForCustomer.ETripType.ROUND_TRIP);

      final ModelAndView mv = ctrl.onSubmit(null, null, cmd, null);
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "selectedDepartFlight");
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "searchedReturnFlights");
   }

   @Test
   public void testOnSubmitAfterStepSecondWithOneWayTrip() throws Exception {
      cmd.setTripType(FlightSearchForCustomer.ETripType.ONEWAY_TRIP);
      cmd.setDepartFlightNo(157);

      assertNull(session.getAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER));
      ctrl.onSubmit(request, null, cmd, null);
      assertNotNull(session.getAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER));
   }

   @Test
   public void testOnSubmitAfterStepThird() throws Exception {
      cmd.setTripType(FlightSearchForCustomer.ETripType.ROUND_TRIP);
      cmd.setDepartFlightNo(157);
      cmd.setReturnFlightNo(157);

      assertNull(session.getAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER));
      ctrl.onSubmit(request, null, cmd, null);
      assertNotNull(session.getAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER));
   }
}
