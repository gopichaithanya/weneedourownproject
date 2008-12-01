package springapp.manager;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import springapp.web.MockServletContextWebContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = MockServletContextWebContextLoader.class, locations = { "/xml/springapp-servlet.xml" })
public class FlightSearchForManagerValidatorTest extends AbstractJUnit4SpringContextTests {

   public static boolean checkErrorField(Errors e, String[] fields) {
      if (null == fields)
         fields = new String[] {};

      boolean bRst = true;
      for (final String f : fields) {
         final FieldError fe = e.getFieldError(f);
         final boolean bExist = (null != fe);
         if (false == bExist)
            bRst = false;
      }

      final boolean bEqualSize = (fields.length == e.getErrorCount());
      if (false == bEqualSize)
         bRst = false;

      return bRst;
   }

   @SuppressWarnings("unchecked")
   public static boolean checkErrorGlobal(Errors e, String[] global) {
      if (null == global)
         global = new String[] {};

      final List<ObjectError> es = e.getGlobalErrors();

      boolean bRst = true;
      for (final String g : global) {

         boolean bFound = false;
         for (final ObjectError o : es) {
            for (final String c : o.getCodes()) {
               final boolean bExist = c.equals(g);
               if (bExist)
                  bFound = true;
            }
         }
         if(false == bFound)
            bRst = false;
      }

      final boolean bEqualSize = (global.length == e.getGlobalErrorCount());
      if (false == bEqualSize)
         bRst = false;

      return bRst;
   }

   private String beanName = null;
   private MockHttpServletRequest request = null;
   private FlightSearchForManagerFormController ctrl = null;
   private FlightSearchForManagerValidator v = null;
   private Errors err = null;
   private FlightSearchForManagerCommand cmd = null;
   private Method formBackingObject = null;

   @Before
   public void before() throws Exception {
      beanName = applicationContext.getBeanNamesForType(FlightSearchForManagerFormController.class)[0];
      assertEquals("/" + FlightSearchForManagerFormController.URL, beanName);

      request = new MockHttpServletRequest("POST", beanName);
      assertNotNull(request);

      ctrl = (FlightSearchForManagerFormController) this.applicationContext.getBean(beanName);
      assertNotNull(ctrl);

      v = new FlightSearchForManagerValidator();
      assertNotNull(v);

      err = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      assertNotNull(err);

      formBackingObject = FlightSearchForManagerFormController.class.getDeclaredMethod(
            "formBackingObject", new Class[] { HttpServletRequest.class });
      assertNotNull(formBackingObject);
      formBackingObject.setAccessible(true);

      cmd = (FlightSearchForManagerCommand) formBackingObject
            .invoke(ctrl, new Object[] { request });
      assertTrue(v.supports(cmd.getClass()));
   }

   @Test
   public void testNormal() {
      v.validate(cmd, err);
      final boolean bRst = checkErrorField(err, null);
      assertTrue(bRst);
   }

   @Test
   public void testNullCommand() {
      v.validate(null, err);
      final boolean bRst = checkErrorGlobal(err, new String[] { "nullpointer" });
      assertTrue(bRst);
   }

   @Test
   public void testFlightNoNormal() {
      cmd.setFlightNo(123);
      v.validate(cmd, err);
      final boolean bRst = checkErrorField(err, null);
      assertTrue(bRst);
   }

   @Test
   public void testFlightNoShort() {
      cmd.setFlightNo(1);
      v.validate(cmd, err);
      final boolean bRst = checkErrorField(err, null);
      assertTrue(bRst);
   }

   @Test
   public void testFlightNoBad1() {
      cmd.setFlightNo(-1);
      v.validate(cmd, err);
      final boolean bRst = checkErrorField(err, new String[] { "flightNo" });
      assertTrue(bRst);
   }

   @Test
   public void testFlightNoBad2() {
      cmd.setFlightNo(1000);
      v.validate(cmd, err);
      final boolean bRst = checkErrorField(err, new String[] { "flightNo" });
      assertTrue(bRst);
   }

   @Test
   public void testAirline1() {
      cmd.setAirline("AP");
      v.validate(cmd, err);
      final boolean bRst = checkErrorField(err, null);
      assertTrue(bRst);
   }

   @Test
   public void testAirline2() {
      assertEquals(null, cmd.getAirline());
      cmd.setAirline("");
      v.validate(cmd, err);
      final boolean bRst = checkErrorField(err, null);
      assertTrue(bRst);
   }

   @Test
   public void testAirlineBad1() {
      cmd.setAirline("ZZ");
      v.validate(cmd, err);
      final boolean bRst = checkErrorField(err, new String[] { "airline" });
      assertTrue(bRst);
   }

   @Test
   public void testDepart1() {
      cmd.setDepartLocation("ACV");
      v.validate(cmd, err);
      final boolean bRst = checkErrorField(err, null);
      assertTrue(bRst);
   }

   @Test
   public void testDepart2() {
      assertEquals(null, cmd.getDepartLocation());
      cmd.setDepartLocation("");
      v.validate(cmd, err);
      final boolean bRst = checkErrorField(err, null);
      assertTrue(bRst);
   }

   @Test
   public void testDepartBad1() {
      cmd.setDepartLocation("AVV");
      v.validate(cmd, err);
      final boolean bRst = checkErrorField(err, new String[] { "departLocation" });
      assertTrue(bRst);
   }

   @Test
   public void testArrive1() {
      cmd.setArrivalLocation("ACV");
      v.validate(cmd, err);
      final boolean bRst = checkErrorField(err, null);
      assertTrue(bRst);
   }

   @Test
   public void testArrive2() {
      assertEquals(null, cmd.getArrivalLocation());
      cmd.setArrivalLocation("");
      v.validate(cmd, err);
      final boolean bRst = checkErrorField(err, null);
      assertTrue(bRst);
   }

   @Test
   public void testArriveBad1() {
      cmd.setArrivalLocation("AVV");
      v.validate(cmd, err);
      final boolean bRst = checkErrorField(err, new String[] { "arrivalLocation" });
      assertTrue(bRst);
   }
}
