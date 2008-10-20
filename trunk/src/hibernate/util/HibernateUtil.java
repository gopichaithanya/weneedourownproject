package hibernate.util;

import java.io.File;

import org.hibernate.*;
import org.hibernate.cfg.*;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	final static String attrHibernateURL = "hibernate.connection.url";
	final static String attrCatalinaBase = "catlina.base";
	final static String configFilenameForTest = "xml/hibernate.ant.cfg.xml";

	static {
		//		System.out.println(System.getProperty("user.dir"));
		//		System.out.println(System.getProperty("java.class.path", "."));

		try {
			// Create the SessionFactory from hibernate.cfg.xml
			final File cfgFile = new File(configFilenameForTest);
			final boolean bFile = cfgFile.exists();
			final Configuration cfgObj = new Configuration();
			final Configuration cfg = (bFile ? cfgObj.configure(cfgFile)
					: cfgObj.configure());

			final String url = cfg.getProperty(attrHibernateURL);
			final String parsedUrl = getParsedUrl(url);
			cfg.setProperty(attrHibernateURL, parsedUrl);

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

	private static String getParsedUrl(String url) {
		final String catalinaBase = System.getProperty(attrCatalinaBase, ".");
		final int idxCatalinaBase = url.indexOf("${" + attrCatalinaBase + "}");

		if (idxCatalinaBase >= 0)
			url = url.substring(0, idxCatalinaBase)
					+ catalinaBase
					+ url.substring(idxCatalinaBase + 3
							+ attrCatalinaBase.length());

		return url;
	}
}