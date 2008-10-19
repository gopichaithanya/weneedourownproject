package hibernate.manager;

import java.util.List;

import hibernate.*;
import hibernate.util.*;
import org.hibernate.Session;
import org.hibernate.HibernateException;

/**
 * Hibernate class that implements the functions dealing with flight information used
 * by Spring
 * 
 * @author Israa Taha
 * @version 
 */
public class FlightManager {
	
	/**
	 * Default Constructor
	 */
	public FlightManager() {}
	
	/**
	 * Adds a flight to the database
	 * @param flight - the flight to be added
	 */
	public void addFlight(Flight flight) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			session.save(flight);
		}
		catch(HibernateException e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
	}
	
	/**
	 * Removes a flight from the database
	 * @param flight - the flight to be removed
	 */
	public void removeFlight(Flight flight) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		try {
			session.delete(flight);
		}
		catch(HibernateException e) {
			e.printStackTrace();
		}
		
		session.getTransaction().commit();
	}
	
	/**
	 * Search for flight(s)
	 * @param flight
	 * @return list of flights that match the specified criteria
	 */
	public List<?> searchFlight(Flight flight) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		// search database for flights that match the values in the flight object
		List<?> result = session.createQuery("").list();
		
		session.getTransaction().commit();
		
		// return list of flights
		return result;
	}
	
	public List<?> listFlights() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<?> result = session.createQuery("from Flight").list();
		session.getTransaction().commit();

	    return result;
	}
	
	public static void main (String[] args){
		FlightManager mgr = new FlightManager();
		
		List<?> flights = mgr.listFlights();
	    for (int i = 0; i < flights.size(); i++) {
	    	Flight flight = (Flight) flights.get(i);
	        System.out.println("Flight: " + flight.getFlightNo());
	    }

		HibernateUtil.getSessionFactory().close();
	}
}