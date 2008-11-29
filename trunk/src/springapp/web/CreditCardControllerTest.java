package springapp.web;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import hibernate.Itinerary;
import hibernate.ItineraryId;
import hibernate.Itinerary.ESeatClass;
import hibernate.Itinerary.EStatus;
import hibernate.manager.ItineraryManager;
import hibernate.manager.ItineraryManagerTest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = MockServletContextWebContextLoader.class, locations = { "/xml/springapp-servlet.xml" })
public class CreditCardControllerTest extends AbstractJUnit4SpringContextTests {

   private String beanName = null;
   private MockHttpServletRequest request = null;
   private MockHttpServletResponse response = null;
   private MockHttpSession session = null;
   private CreditCardController ctrl = null;
   private Method formBackingObject = null;
   private String loginViewName = null;

   @Before
   public void before() throws Exception {
      beanName = applicationContext.getBeanNamesForType(CreditCardController.class)[0];
      assertEquals("/" + CreditCardController.URL, beanName);

      request = new MockHttpServletRequest("POST", beanName);
      assertNotNull(request);

      response = new MockHttpServletResponse();
      assertNotNull(response);

      session = (MockHttpSession) request.getSession();
      assertNotNull(session);

      ctrl = (CreditCardController) this.applicationContext.getBean(beanName);
      assertNotNull(ctrl);

      formBackingObject = CreditCardController.class.getDeclaredMethod("formBackingObject",
            new Class[] { HttpServletRequest.class });
      assertNotNull(formBackingObject);
      formBackingObject.setAccessible(true);

      loginViewName = applicationContext.getBeanNamesForType(LoginController.class)[0];
   }

   @Test
   public void testDefaultObj() throws Exception {
      ItineraryManager.reserve("jjohnson", 157, ESeatClass.ECONOMY, 1);

      request.setParameter(CreditCardController.PARAM_FLIGHT_NO, String.valueOf(157));
      session.setAttribute(SessionConstants.LOGIN_USERNAME, "jjohnson");

      final Itinerary cmd = (Itinerary) formBackingObject.invoke(ctrl, new Object[] { request });
      assertEquals("jjohnson", cmd.getCustomer().getUsername());
      assertEquals(157, cmd.getFlight().getFlightNo());
      assertEquals(EStatus.RESERVED.toString(), cmd.getStatus());
      assertEquals(ESeatClass.ECONOMY.toString(), cmd.getSeatClass());
      assertEquals(1, (int) cmd.getNumOfSeats());

      ItineraryManagerTest.deleteItinerary(new ItineraryId(157, "jjohnson"));
   }

   @Test
   public void testWithoutLogin() throws Exception {
      final ModelAndView mv = ctrl.handleRequest(request, response);
      assertEquals("/" + ((RedirectView) mv.getView()).getUrl(), loginViewName);
   }
}
