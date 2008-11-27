package springapp.web;

import static org.junit.Assert.*;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
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
   private FlightSearchForCustomerFormController ctrl = null;
   private FlightSearchForCustomer cmd = null;

   @Before
   public void before() throws Exception {
      FlightSearchForCustomerFormController.bFlagSearch = true;

      beanName = applicationContext
            .getBeanNamesForType(FlightSearchForCustomerFormController.class)[0];
      assertEquals("/flightSearchForCustomerForm.spring", beanName);

      request = new MockHttpServletRequest("POST", beanName);
      response = new MockHttpServletResponse();
      assertNotNull(request);
      assertNotNull(response);

      ctrl = (FlightSearchForCustomerFormController) this.applicationContext.getBean(beanName);
      assertNotNull(ctrl);

      cmd = (FlightSearchForCustomer) ctrl.getCommandClass().getConstructor(new Class[] {})
            .newInstance(new Object[] {});
      assertNotNull(cmd);

   }

   @Test
   public void testHandleRequest() throws Exception {
      final ModelAndView mv = ctrl.handleRequest(request, response);
      assertNotNull(mv);
      ModelAndViewAssert.assertViewName(mv, ctrl.getFormView());
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "airports");
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "tripTypes");
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "seatClass");
   }

   @Test
   public void testOnSubmitNull() throws Exception {
      final ModelAndView mv = ctrl.onSubmit(null, null, null, null);
      assertNotNull(mv);
      ModelAndViewAssert.assertViewName(mv, ctrl.getFormView());
      assertTrue(mv.getModel().isEmpty());
   }

   @Test
   public void testOnSubmitAfterStepFirst() throws Exception {
      cmd.setTripType(FlightSearchForCustomer.ETripType.ROUND_TRIP);
      cmd.setDepartLocation("YNG");
      cmd.setArrivalLocation("SGH");

      final ModelAndView mv = ctrl.onSubmit(null, null, cmd, null);
      assertNotNull(mv);
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
      assertNotNull(mv);
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "selectedDepartFlight");
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "searchedReturnFlights");
   }

   @Test
   public void testOnSubmitAfterStepSecondWithOneWayTrip() throws Exception {
      cmd.setTripType(FlightSearchForCustomer.ETripType.ONEWAY_TRIP);
      cmd.setDepartFlightNo(157);

      final ModelAndView mv = ctrl.onSubmit(request, null, cmd, null);
      assertNotNull(mv);

      final HttpSession session = request.getSession();
      assertNull(session.getAttribute("Nothing is in"));
      assertNotNull(session.getAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER));
   }

   @Test
   public void testOnSubmitAfterStepThird() throws Exception {
      cmd.setTripType(FlightSearchForCustomer.ETripType.ROUND_TRIP);
      cmd.setDepartFlightNo(157);
      cmd.setReturnFlightNo(157);

      final ModelAndView mv = ctrl.onSubmit(request, null, cmd, null);
      assertNotNull(mv);

      final HttpSession session = request.getSession();
      assertNull(session.getAttribute("Nothing is in"));
      assertNotNull(session.getAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER));
   }
}
