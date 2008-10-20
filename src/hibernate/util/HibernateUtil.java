package hibernate.util;

import java.io.File;

import org.hibernate.*;
import org.hibernate.cfg.*;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    private final static String attrCatalinaBase = "catalina.base";
    public final static String configFilenameForTest = "xml/hibernate.ant.cfg.xml";

    static {
//		System.out.println(System.getProperty("user.dir")); // working directory
//		System.out.println(System.getProperty("java.class.path", "."));

        try {
            final File cfgFile = new File(configFilenameForTest);
            final boolean bFile = cfgFile.exists();

            Configuration cfg = null;

            if (bFile && false == isThisForTomcat())
                cfg = new Configuration().configure(cfgFile);
            else
                // Create the SessionFactory from hibernate.cfg.xml
                cfg = new Configuration().configure();

            sessionFactory = cfg.buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static boolean isThisForTomcat() {
        final String catalinaBase = System.getProperty(attrCatalinaBase, ".");
        final boolean bTomcat = !(".".equals(catalinaBase));
        return bTomcat;
    }
}