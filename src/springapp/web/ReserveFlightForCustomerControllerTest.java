package springapp.web;

import static org.junit.Assert.*;
import hibernate.ItineraryId;
import hibernate.Itinerary.ESeatClass;
import hibernate.manager.ItineraryManagerTest;

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
import org.springframework.web.servlet.view.RedirectView;

import springapp.manager.MockServletContextWebContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = MockServletContextWebContextLoader.class, locations = { "/xml/springapp-servlet.xml" })
public class ReserveFlightForCustomerControllerTest extends AbstractJUnit4SpringContextTests {

   private String beanName = null;
   private MockHttpServletRequest request = null;
   private MockHttpServletResponse response = null;
   private MockHttpSession session = null;
   private ReserveFlightForCustomerController ctrl = null;
   private String loginViewName = null;

   @Before
   public void before() throws Exception {
      beanName = applicationContext.getBeanNamesForType(ReserveFlightForCustomerController.class)[0];
      assertEquals("/reserveFlightForCustomer.spring", beanName);

      request = new MockHttpServletRequest("POST", beanName);
      assertNotNull(request);

      response = new MockHttpServletResponse();
      assertNotNull(response);

      session = (MockHttpSession) request.getSession();
      assertNotNull(session);

      ctrl = (ReserveFlightForCustomerController) this.applicationContext.getBean(beanName);
      assertNotNull(ctrl);

      loginViewName = applicationContext.getBeanNamesForType(LoginController.class)[0];
   }

   @Test
   public void testReserveFlight1() throws Exception {
      final String userName = "jjohnson";
      final boolean bRst = testReserveFlight(userName, new Integer[] { 182 }, ESeatClass.BUSINESS,
            1);
      assertTrue(bRst);
      ItineraryManagerTest.deleteItinerary(new ItineraryId(182, userName));
   }

   @Test
   public void testReserveFlight2() throws Exception {
      final String userName = "jjohnson";
      final boolean bRst = testReserveFlight(userName, new Integer[] { 182, 378 },
            ESeatClass.ECONOMY, 1);
      assertTrue(bRst);
      ItineraryManagerTest.deleteItinerary(new ItineraryId(182, userName));
      ItineraryManagerTest.deleteItinerary(new ItineraryId(378, userName));
   }

   @Test
   public void testReserveFlightNull() throws Exception {
      final String userName = "jjohnson";
      final boolean bRst = testReserveFlight(userName, null, ESeatClass.BUSINESS, 1);
      assertFalse(bRst);
   }

   @Test
   public void testReserveFlightBad1() throws Exception {
      final String userName = "jjohnson";
      try {
         final boolean bRst = testReserveFlight(userName, "XXX", ESeatClass.BUSINESS, 1);
         assertFalse(bRst);
      } catch (ClassCastException e) {
      }
   }

   @Test
   public void testReserveFlightBad2() throws Exception {
      final String userName = "jjohnson";
      try {
         final boolean bRst = testReserveFlight(userName, "", ESeatClass.BUSINESS, 1);
         assertFalse(bRst);
      } catch (ClassCastException e) {
      }
   }

   @Test
   public void testReserveFlightBad3() throws Exception {
      final String userName = "jjohnson";
      try {
         final boolean bRst = testReserveFlight(userName, new String[] { "182" },
               ESeatClass.BUSINESS, 1);
         assertFalse(bRst);
      } catch (ClassCastException e) {
      }
   }

   @Test
   public void testReserveSeatTypeBad() throws Exception {
      final String userName = "jjohnson";
      final boolean bRst = testReserveFlight(userName, new Integer[] { 182 }, null, 1);
      assertFalse(bRst);
   }

   @Test
   public void testReserveNumPassengersBad() throws Exception {
      final String userName = "jjohnson";
      final boolean bRst = testReserveFlight(userName, new Integer[] { 182 }, ESeatClass.ECONOMY, 0);
      assertFalse(bRst);
   }

   public boolean testReserveFlight(String userName, Object argReserveFlight,
         ESeatClass argSeatType, int argNumPassengers) throws Exception {

      session.setAttribute(SessionConstants.USERNAME, userName);
      session.setAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER, argReserveFlight);
      session.setAttribute(SessionConstants.RESERVE_SEATCLASS_FOR_CUSTOMER, argSeatType);
      session.setAttribute(SessionConstants.RESERVE_NUM_PASSENGERS_FOR_CUSTOMER, argNumPassengers);

      final ModelAndView mv = ctrl.handleRequest(request, response);
      final boolean bRst = (Boolean) ModelAndViewAssert.assertAndReturnModelAttributeOfType(mv,
            "result", Boolean.class);
      return bRst;
   }

   @Test
   public void testWithoutLogin() throws Exception {
      final ModelAndView mv = ctrl.handleRequest(request, response);
      assertEquals("/" + ((RedirectView) mv.getView()).getUrl(), loginViewName);
   }
}
