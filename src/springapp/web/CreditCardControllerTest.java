package springapp.web;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Hashtable;

import hibernate.Itinerary;
import hibernate.Itinerary.ESeatClass;
import hibernate.Itinerary.EStatus;
import hibernate.manager.ItineraryManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import org.junit.Before;
import org.junit.Test;

public class CreditCardControllerTest {

   private Method formBackingObject = null;

   @Before
   public void before() throws SecurityException, NoSuchMethodException {
      formBackingObject = CreditCardController.class.getDeclaredMethod("formBackingObject",
            new Class[] { HttpServletRequest.class });
      formBackingObject.setAccessible(true);
   }

   @Test
   public void testDefaultObj() throws Exception {
      ItineraryManager.reserve("jjohnson", 157, ESeatClass.ECONOMY, 1);

      final Hashtable<String, String> param = new Hashtable<String, String>();
      param.put("flightNo", String.valueOf(157));
      final HttpServletRequest req = new NullHttpServletRequest(param);
      req.getSession().setAttribute(SessionConstants.USERNAME, "jjohnson");

      final CreditCardController form = new CreditCardController();
      final Itinerary cmd = (Itinerary) formBackingObject.invoke(form, new Object[] { req });
      assertNotNull(cmd);
      assertEquals("jjohnson", cmd.getCustomer().getUsername());
      assertNotNull(cmd.getFlight());
      assertEquals(157, cmd.getFlight().getFlightNo());
      assertEquals(EStatus.RESERVED.toString(), cmd.getStatus());
      assertEquals(ESeatClass.ECONOMY.toString(), cmd.getSeatClass());
      assertEquals(1, (int) cmd.getNumOfSeats());

      ItineraryManager.cancelReserved("jjohnson", 157);
   }

   @Test
   public void testWithoutLogin() throws Exception {
      final HttpServletRequest req = new NullHttpServletRequest();
      final HttpServletResponse res = new NullHttpServletResponse();
      final CreditCardController form = new CreditCardController();
      final ModelAndView mv = form.handleRequest(req, res);
      assertNotNull(mv);
      assertEquals(RedirectView.class, mv.getView().getClass());
   }
}
