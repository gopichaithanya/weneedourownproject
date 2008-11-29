package springapp.web;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.*;
import org.springframework.mock.web.*;

import springapp.manager.MockServletContextWebContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = MockServletContextWebContextLoader.class, locations = { "/xml/springapp-servlet.xml" })
public class ItineraryForCustomerControllerTest extends AbstractJUnit4SpringContextTests {

   private String beanName = null;
   private MockHttpServletRequest request = null;
   private MockHttpServletResponse response = null;
   private MockHttpSession session = null;
   private ItineraryForCustomerController ctrl = null;
   private String loginViewName = null;

   @Before
   public void before() throws Exception {
      beanName = applicationContext.getBeanNamesForType(ItineraryForCustomerController.class)[0];
      assertEquals("/" + ItineraryForCustomerController.URL, beanName);

      request = new MockHttpServletRequest("POST", beanName);
      assertNotNull(request);

      response = new MockHttpServletResponse();
      assertNotNull(response);

      session = (MockHttpSession) request.getSession();
      assertNotNull(session);

      ctrl = (ItineraryForCustomerController) this.applicationContext.getBean(beanName);
      assertNotNull(ctrl);

      loginViewName = applicationContext.getBeanNamesForType(LoginController.class)[0];
   }

   @Test
   public void testHandleRequest() throws ServletException, IOException {
      session.setAttribute(SessionConstants.LOGIN_USERNAME, "jjohnson");
      final ModelAndView mv = ctrl.handleRequest(request, response);
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "reservedItinerary");
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "bookedItinerary");
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "canceledItinerary");
   }

   @Test
   public void testWithoutLogin() throws Exception {
      final ModelAndView mv = ctrl.handleRequest(request, response);
      assertEquals("/" + ((RedirectView) mv.getView()).getUrl(), loginViewName);
   }
}
