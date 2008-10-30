package hibernate.util;

import java.io.File;
import java.util.regex.Pattern;

import org.hibernate.*;
import org.hibernate.cfg.*;

public class HibernateUtil {

   private static final SessionFactory sessionFactory;

   static {
      final String catalBase = findCatalinaBase();

      try {
         // Create the SessionFactory from hibernate.cfg.xml
         final Configuration cfg = new Configuration().configure();
         final String url = cfg.getProperty("connection.url");
         if (null != catalBase && null != url) {
            final String url2 = url.replaceFirst("${catalina.base}", catalBase);
            cfg.setProperty("connection.url", url2);
         }

         sessionFactory = cfg.buildSessionFactory();
      } catch (Throwable ex) {
         // Make sure you log the exception, as it might be swallowed
         System.err.println("Initial SessionFactory creation failed." + ex);
         throw new ExceptionInInitializerError(ex);
      }
   }

   private static String findCatalinaBase() {
      final String classPath = System.getProperty("java.class.path", ".");
      final String sep = String.valueOf(File.pathSeparatorChar);

      final String[] classPaths = Pattern.compile(String.valueOf(sep)).split(classPath);
      for (final String cp : classPaths) { // cp will be "catalina.base/webapps/proj4398/WEB-INF/classes
         final File fileWebInf = new File(cp).getParentFile();
         if (null == fileWebInf)
            continue;
         if (false == fileWebInf.getName().equals("WEB-INF"))
            continue;
         return fileWebInf.getParentFile().getParentFile().getParentFile().getAbsolutePath();
      }
      return null;
   }

   public static SessionFactory getSessionFactory() {
      return sessionFactory;
   }
}