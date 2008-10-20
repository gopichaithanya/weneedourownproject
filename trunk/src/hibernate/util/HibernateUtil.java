package hibernate.util;

import org.hibernate.*;
import org.hibernate.cfg.*;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
//		System.out.println(System.getProperty("user.dir")); // working directory
//		System.out.println(System.getProperty("java.class.path", "."));

        try {
            // Create the SessionFactory from hibernate.cfg.xml
            final Configuration cfg = new Configuration().configure();

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
}