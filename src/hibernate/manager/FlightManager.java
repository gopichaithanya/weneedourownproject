package hibernate.manager;

import hibernate.*;
import hibernate.util.*;
import org.hibernate.Session;

public class FlightManager {
	
	private void createAndStoreFlight(int flightNo, String code) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Flight flight = new Flight();
		//flight.setFlightNo(flightNo);
		//flight.setAirlineCode(code);
		session.save(flight);
		session.getTransaction().commit();
	}
	
	public static void main (String[] args){
		FlightManager mgr = new FlightManager();
		mgr.createAndStoreFlight(924, "FL");
		HibernateUtil.getSessionFactory().close();
	}
}