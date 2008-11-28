package springapp.web;

import static org.junit.Assert.*;
import hibernate.Itinerary.ESeatClass;
import hibernate.manager.ItineraryManager;

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
public class CancelReservedFlightForCustomerControllerTest extends AbstractJUnit4SpringContextTests {

   private String beanName = null;
   private MockHttpServletRequest request = null;
   private MockHttpServletResponse response = null;
   private MockHttpSession session = null;
   private CancelReservedFlightForCustomerController ctrl = null;

   @Before
   public void before() {
      beanName = applicationContext
            .getBeanNamesForType(CancelReservedFlightForCustomerController.class)[0];
      assertEquals("/cancelReservedFlightForCustomer.spring", beanName);

      request = new MockHttpServletRequest("POST", beanName);
      assertNotNull(request);

      response = new MockHttpServletResponse();
      assertNotNull(response);

      session = (MockHttpSession) request.getSession();
      assertNotNull(session);

      ctrl = (CancelReservedFlightForCustomerController) this.applicationContext.getBean(beanName);
      assertNotNull(ctrl);
   }

   private boolean testCancelReservedFlight(String userName, String flightNo) throws Exception {
      request.setParameter(CancelReservedFlightForCustomerController.PARAM_FLIGHT_NO, flightNo);
      session.setAttribute(SessionConstants.USERNAME, userName);

      final ModelAndView mv = ctrl.handleRequest(request, response);
      assertNotNull(mv);

      final boolean bRst = (Boolean) ModelAndViewAssert.assertAndReturnModelAttributeOfType(mv,
            "result", Boolean.class);
      return bRst;
   }

   @Test
   public void testHandleRequest() throws Exception {
      final String userName = "jjohnson";
      final String flightNo = "182";
      ItineraryManager.reserve(userName, Integer.valueOf(flightNo), ESeatClass.ECONOMY, 1);
      final boolean bRst = testCancelReservedFlight(userName, flightNo);
      assertTrue(bRst);
   }

   @Test
   public void testHandleRequestBad1() throws Exception {
      final String userName = "jjohnson";
      final String flightNo = "99";
      final boolean bRst = testCancelReservedFlight(userName, flightNo);
      assertFalse(bRst);
   }

   @Test
   public void testHandleRequestBad2() throws Exception {
      final String userName = "jjohnson";
      final String flightNo = "";
      final boolean bRst = testCancelReservedFlight(userName, flightNo);
      assertFalse(bRst);
   }

}
