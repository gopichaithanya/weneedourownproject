package springapp.web;

import static org.junit.Assert.*;
import hibernate.manager.ItineraryManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.util.Hashtable;

@SuppressWarnings("unchecked")
public class CancelReservedFlightForCustomerControllerTest {

   @Test
   public void testHandleRequest() throws Exception {
      final String userName = "jjohnson";
      final String flightNo = "182";
      ItineraryManager.reserve(userName, Integer.valueOf(flightNo));
      final boolean bRst = testCancelReservedFlight(userName, flightNo);
      assertTrue(bRst);
   }

   @Test
   public void testHandleRequestNull() throws Exception {
//      final String userName = "jjohnson";
//      final String flightNo = null;
//      final boolean bRst = testCancelReservedFlight(userName, flightNo);
//      assertFalse(bRst);
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

   public boolean testCancelReservedFlight(String userName, String flightNo) throws Exception {
      final Hashtable<String, String> param = new Hashtable();
      param.put(CancelReservedFlightForCustomerController.PARAM_FLIGHT_NO, flightNo);
      final HttpServletRequest req = new NullHttpServletRequest(param);
      final HttpServletResponse resp = new NullHttpServletResponse();
      final HttpSession session = req.getSession();
      session.setAttribute(SessionConstants.USERNAME, userName);
      final Controller ctl = new CancelReservedFlightForCustomerController();

      final ModelAndView mv = ctl.handleRequest(req, resp);
      assertNotNull(mv);
      final boolean bRst = (Boolean) mv.getModel().get("result");
      return bRst;
   }
}
