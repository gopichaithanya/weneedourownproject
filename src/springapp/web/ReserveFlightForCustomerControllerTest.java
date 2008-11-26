package springapp.web;

import static org.junit.Assert.*;
import hibernate.Itinerary.ESeatClass;
import hibernate.manager.ItineraryManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ReserveFlightForCustomerControllerTest {

   @Test
   public void testReserveFlight1() throws Exception {
      final String userName = "jjohnson";
      final boolean bRst = testReserveFlight(userName, new Integer[] { 182 }, ESeatClass.BUSINESS, 1);
      assertTrue(bRst);
      ItineraryManager.cancelReserved(userName, 182);
   }

   @Test
   public void testReserveFlight2() throws Exception {
      final String userName = "jjohnson";
      final boolean bRst = testReserveFlight(userName, new Integer[] { 182, 378 },
            ESeatClass.ECONOMY, 1);
      assertTrue(bRst);
      ItineraryManager.cancelReserved(userName, 182);
      ItineraryManager.cancelReserved(userName, 378);
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
      final HttpServletRequest req = new NullHttpServletRequest();
      final HttpServletResponse resp = new NullHttpServletResponse();
      final HttpSession session = req.getSession();
      session.setAttribute(SessionConstants.USERNAME, userName);
      final Controller ctl = new ReserveFlightForCustomerController();

      session.setAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER, argReserveFlight);
      session.setAttribute(SessionConstants.RESERVE_SEATCLASS_FOR_CUSTOMER, argSeatType);
      session.setAttribute(SessionConstants.RESERVE_NUM_PASSENGERS_FOR_CUSTOMER, argNumPassengers);

      final ModelAndView mv = ctl.handleRequest(req, resp);
      assertNotNull(mv);
      final boolean bRst = (Boolean) mv.getModel().get("result");
      return bRst;
   }
}
