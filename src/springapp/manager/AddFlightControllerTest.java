package springapp.manager;

import static org.junit.Assert.*;

import hibernate.manager.FlightManagerTest;

import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractFormController;

import springapp.web.MockServletContextWebContextLoader;
import springapp.web.SessionConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = MockServletContextWebContextLoader.class, locations = { "/xml/springapp-servlet.xml" })
public class AddFlightControllerTest extends AbstractJUnit4SpringContextTests {

   private String beanName = null;
   private MockHttpServletRequest request = null;
   private MockHttpServletResponse response = null;
   private MockHttpSession session = null;
   private AddFlightController ctrl = null;
   private AddFlightValidator validator = null;
   private Errors err = null;
   private Method formBackingObject = null;
   private Method getFormSessionAttributeName = null;
   private AddFlightCommand cmd = null;
   private String formSessionAttributeName = null;
   private String formView = null;

   @Before
   public void before() throws Exception {
      beanName = applicationContext.getBeanNamesForType(AddFlightController.class)[0];
      assertEquals("/" + AddFlightController.URL, beanName);

      validator = new AddFlightValidator();
      err = new MapBindingResult(new HashMap<Object, Object>(), beanName);
      request = new MockHttpServletRequest("POST", beanName);
      response = new MockHttpServletResponse();
      session = (MockHttpSession) request.getSession();
      assertNotNull(session);

      ctrl = (AddFlightController) this.applicationContext.getBean(beanName);
      assertNotNull(ctrl);

      formBackingObject = AddFlightController.class.getDeclaredMethod("formBackingObject",
            new Class[] { HttpServletRequest.class });
      assertNotNull(formBackingObject);
      formBackingObject.setAccessible(true);

      getFormSessionAttributeName = AbstractFormController.class.getDeclaredMethod(
            "getFormSessionAttributeName", new Class[] {});
      assertNotNull(getFormSessionAttributeName);
      getFormSessionAttributeName.setAccessible(true);

      cmd = (AddFlightCommand) formBackingObject.invoke(ctrl, new Object[] { request });
      assertTrue(validator.supports(cmd.getClass()));

      formSessionAttributeName = (String) getFormSessionAttributeName.invoke(ctrl, new Object[] {});
      assertNotNull(formSessionAttributeName);

      formView = ctrl.getFormView();
      assertNotNull(formView);
   }

   @Test
   public void testNormalCase() throws Exception {
      final ModelAndView mv = ctrl.handleRequest(request, response);
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "airlines");
      ModelAndViewAssert.assertModelAttributeAvailable(mv, "airports");
   }

   @Test
   public void testSubmitting() throws Exception {
      final int flightNo = 911;
      FlightManagerTest.deleteFlight(flightNo);
      cmd.setFlightNo(flightNo);
      cmd.setAirline("AA");
      cmd.setAirportByDepartureLocation("IAD");
      cmd.setAirportByArrivalLocation("JFK");
      session.setAttribute(formSessionAttributeName, cmd);
      validator.validate(cmd, err);
      assertFalse(err.hasErrors());

      assertNull(session.getAttribute(SessionConstants.ADDED_NEW_FLIGHT));
      final ModelAndView mv = ctrl.handleRequest(request, response);
      assertNotNull(mv);
      assertNotNull(session.getAttribute(SessionConstants.ADDED_NEW_FLIGHT));
      FlightManagerTest.deleteFlight(flightNo);
   }
}
