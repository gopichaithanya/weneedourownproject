package springapp.manager;

import org.springframework.web.servlet.mvc.SimpleFormController;

import hibernate.Flight;

public class AddFlightController  extends SimpleFormController 
{
	protected void doSubmitAction(Object o) throws Exception
	{ 
		Flight loFlight = (Flight)o;
		
		//final Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		//session.beginTransaction();

		//session.save(loFlight);
	}


}
