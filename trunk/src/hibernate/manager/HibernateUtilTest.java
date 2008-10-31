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

      final Method methodFindDatabase = HibernateUtil.class.getDeclaredMethod("findCatalinaBase");
      assertNotNull(methodFindDatabase);
      methodFindDatabase.setAccessible(true);
      final String catalBase = (String) methodFindDatabase.invoke(null);
      assertNotNull(catalBase);

      assertEquals(expectedResult, catalBase);
   }
}
