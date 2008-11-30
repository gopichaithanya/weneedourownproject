package springapp.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import springapp.web.FlightSearchForCustomerCommand;
import springapp.web.FlightSearchForCustomerFormController;
import springapp.web.MockServletContextWebContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = MockServletContextWebContextLoader.class, locations = { "/xml/springapp-servlet.xml" })
public class FlightSearchForManagerFormControllerTest extends AbstractJUnit4SpringContextTests {

   private String beanName = null;
   private MockHttpServletRequest request = null;
   private MockHttpServletResponse response = null;
   private MockHttpSession session = null;
   private FlightSearchForManagerFormController ctrl = null;
   private FlightSearchForManagerCommand cmd = null;

   @Before
   public void before() throws Exception {
      beanName = applicationContext.getBeanNamesForType(FlightSearchForManagerFormController.class)[0];
      assertEquals("/" + FlightSearchForManagerFormController.URL, beanName);

      request = new MockHttpServletRequest("POST", beanName);
      assertNotNull(request);

      response = new MockHttpServletResponse();
      assertNotNull(response);

      session = (MockHttpSession) request.getSession();
      assertNotNull(session);

      ctrl = (FlightSearchForManagerFormController) this.applicationContext.getBean(beanName);
      assertNotNull(ctrl);

      cmd = (FlightSearchForManagerCommand) ctrl.getCommandClass().getConstructor(new Class[] {})
            .newInstance(new Object[] {});
      assertNotNull(cmd);

   }

   @Test
   public void testHandleRequest() throws Exception {
      final ModelAndView mv = ctrl.handleRequest(request, response);
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "airlines");
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "airports");
   }
}
