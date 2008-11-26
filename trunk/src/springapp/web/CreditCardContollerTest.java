package springapp.web;

import static org.junit.Assert.*;

import java.util.Hashtable;

import hibernate.Itinerary;
import hibernate.Itinerary.ESeatClass;
import hibernate.Itinerary.EStatus;
import hibernate.manager.ItineraryManager;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

public class CreditCardContollerTest {

   private static class CreditCardControllerForTest extends CreditCardController {
      public Object formBackingObjectPublic(HttpServletRequest req) throws Exception{
         return super.formBackingObject(req);
      }
   }
   @Test
   public void testDefaultObj() throws Exception {
      ItineraryManager.reserve("jjohnson", 157, ESeatClass.ECONOMY, 1);

      final Hashtable<String, String> param = new Hashtable<String, String>();
      param.put("flightNo", String.valueOf(157));
      final HttpServletRequest req = new NullHttpServletRequest(param);
      req.getSession().setAttribute(SessionConstants.USERNAME, "jjohnson");

      final CreditCardControllerForTest form = new CreditCardControllerForTest();
      final Itinerary cmd = (Itinerary) form.formBackingObjectPublic(req);
      assertNotNull(cmd);
      assertEquals(cmd.getCustomer().getUsername(), "jjohnson");
      assertEquals(cmd.getFlight().getFlightNo(), 157);
      assertEquals(cmd.getStatus(), EStatus.RESERVED.toString());
      assertEquals(cmd.getSeatClass(), ESeatClass.ECONOMY.toString());
      assertEquals(cmd.getNumOfSeats(), 1);
      
   }
}