package hibernate.manager;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class HibernateUtilTest {

   private final String expectedResult = System.getProperty("user.dir");

   @Before
   public void before() {
      final String sep = String.valueOf(File.pathSeparatorChar);
      final String delim = String.valueOf(File.separatorChar);

      // catalina.base/webapps/proj4398/WEB-INF/classes
      // .            /build  /war     /WEB-INF/classes
      final String classesPath = expectedResult + delim + "build" + delim + "war" + delim
            + "WEB-INF" + delim + "classes";
      final String dummyClassPaths = delim + sep + classesPath + sep
            + System.getProperty("java.class.path", ".");
      System.setProperty("java.class.path", dummyClassPaths);
   }

   @Test
   public void testFindDatabase() throws IOException, SecurityException, NoSuchMethodException,
         IllegalArgumentException, IllegalAccessException, InvocationTargetException {

      System
            .setProperty(
                  "java.class.path",
                  "/opt/local/share/java/apache-ant/lib/ant-stylebook.jar:/opt/local/share/java/apache-ant/lib/ant-swing.jar:/opt/local/share/java/apache-ant/lib/ant-testutil.jar:/opt/local/share/java/apache-ant/lib/ant-trax.jar:/opt/local/share/java/apache-ant/lib/ant-weblogic.jar:/opt/local/share/java/apache-ant/lib/ant.jar:/opt/local/share/java/apache-ant/lib/xercesImpl.jar:/opt/local/share/java/apache-ant/lib/xml-apis.jar:/someplace/tomcat/webapps/proj4398/WEB-INF/classes");

      final Method findCatalinaBase = HibernateUtil.class.getDeclaredMethod("findCatalinaBase");
      assertNotNull(findCatalinaBase);
      findCatalinaBase.setAccessible(true);
      @SuppressWarnings("unused")
      final String catalBase = (String) findCatalinaBase.invoke(null);
      //assertNotNull(catalBase);
      //assertEquals("/someplace/tomcat", catalBase);
   }
}
