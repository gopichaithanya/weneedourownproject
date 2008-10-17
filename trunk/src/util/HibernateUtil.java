package util;

import org.hibernate.*;
import org.hibernate.cfg.*;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		final String attrHibernateURL = "hibernate.connection.url";
		final String attrCatalinaBase = "catlina.base";
		final String catalinaBase = System.getProperty(attrCatalinaBase);

		try {
			// Create the SessionFactory from hibernate.cfg.xml
			final Configuration cfg = new Configuration().configure();

			final String url = cfg.getProperty(attrHibernateURL);
			final int idxCatalinaBase = url.indexOf("${" + attrCatalinaBase
					+ "}");
			if (idxCatalinaBase >= 0) {
				final String newURL = url.substring(0, idxCatalinaBase)
						+ catalinaBase
						+ url.substring(idxCatalinaBase + 3
								+ attrCatalinaBase.length());
				cfg.setProperty(attrHibernateURL, newURL);
			}

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