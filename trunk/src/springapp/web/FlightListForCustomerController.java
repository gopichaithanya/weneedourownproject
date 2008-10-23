package springapp.web;


import org.hibernate.Session;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import hibernate.util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
public class FlightListForCustomerController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		final List flights = listFlight();
		return new ModelAndView("flightList", "flights", flights);
	}

	private List listFlight() {

		final Session session = HibernateUtil.getSessionFactory()
				.getCurrentSession();

		session.beginTransaction();

		final List result = session.createQuery("from Flight").list();

		session.getTransaction().commit();

		return result;
	}
}
