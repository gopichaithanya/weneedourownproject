package springapp.web;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.HashMap;

import hibernate.Customer;
import hibernate.manager.CustomerManagerTest;

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
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = MockServletContextWebContextLoader.class, locations = { "/xml/springapp-servlet.xml" })
public class RegisterControllerTest extends AbstractJUnit4SpringContextTests {

   private String beanName = null;
   private MockHttpServletRequest request = null;
   private MockHttpServletResponse response = null;
   private MockHttpSession session = null;
   private RegisterController ctrl = null;
   private Method formBackingObject = null;
   private Errors err = null;
   private String viewSuccess = null;
   private String viewForm = null;

   @Before
   public void before() throws Exception {
      beanName = applicationContext.getBeanNamesForType(RegisterController.class)[0];
      assertEquals("/" + RegisterController.URL, beanName);

      request = new MockHttpServletRequest("POST", beanName);
      assertNotNull(request);

      response = new MockHttpServletResponse();
      assertNotNull(response);

      session = (MockHttpSession) request.getSession();
      assertNotNull(session);

      ctrl = (RegisterController) this.applicationContext.getBean(beanName);
      assertNotNull(ctrl);

      formBackingObject = RegisterController.class.getDeclaredMethod("formBackingObject",
            new Class[] { HttpServletRequest.class });
      assertNotNull(formBackingObject);
      formBackingObject.setAccessible(true);

      err = new MapBindingResult(new HashMap<Object, Object>(), "foo");

      viewSuccess = ctrl.getSuccessView();
      viewForm = ctrl.getFormView();
   }

   @Test
   public void testRegister() throws Exception {
      final Customer cmd = getValidCmdObj();

      CustomerManagerTest.deleteUser(cmd.getUsername());
      {
         assertNull(session.getAttribute(SessionConstants.LOGIN_USERNAME));
         {
            final ModelAndView mv = ctrl.onSubmit(request, response, cmd, null);
            assertEquals(RedirectView.class, mv.getView().getClass());
            assertEquals(viewSuccess, ((RedirectView) mv.getView()).getUrl());
         }
         assertNotNull(session.getAttribute(SessionConstants.LOGIN_USERNAME));
      }
      CustomerManagerTest.deleteUser(cmd.getUsername());
   }

   @Test
   public void testRedundant() throws Exception {
      final Customer cmd = getValidCmdObj();

      CustomerManagerTest.deleteUser(cmd.getUsername());
      {
         final ModelAndView mv1 = ctrl.onSubmit(request, response, cmd, null);
         assertEquals(RedirectView.class, mv1.getView().getClass());
         assertEquals(viewSuccess, ((RedirectView) mv1.getView()).getUrl());

         final ModelAndView mv2 = ctrl.onSubmit(request, response, cmd, null);
         assertEquals(RedirectView.class, mv2.getView().getClass());
         assertEquals(viewForm + ".spring", ((RedirectView) mv2.getView()).getUrl());
      }
      CustomerManagerTest.deleteUser(cmd.getUsername());
   }

   private Customer getValidCmdObj() throws Exception {
      final Customer cmd = (Customer) formBackingObject.invoke(ctrl, new Object[] { request });
      cmd.setUsername("testingUser");
      cmd.setFirstName("firstName");
      cmd.setLastName("lastName");
      cmd.setStreet("street");
      cmd.setCity("city");
      cmd.setState("VA");
      cmd.setPassword("password");

      final RegisterValidator v = new RegisterValidator();
      v.validate(cmd, err);
      assertFalse(err.hasErrors());
      return cmd;
   }
}
