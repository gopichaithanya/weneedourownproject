package hibernate.manager;

import java.util.List;

import hibernate.*;
import hibernate.util.*;
import org.hibernate.Session;

public class FlightManager {
	
	public void createAndStoreFlight(int flightNo, String code) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Flight flight = new Flight();
		flight.setFlightNo(flightNo);
		
		// to retrieve an airline object
		/*Airline airline = new Airline();
		session.load(airline, new String(code));
		
		flight.setAirline(airline);*/
		
		session.save(flight);
		session.getTransaction().commit();
	}
	
	/**
	 * Search for a flight by flight number
	 * @param flightNumber flight number
	 * @return flight
	 */
	public Flight searchFlight(int flightNumber) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Flight flight = (Flight) session.get(Flight.class, flightNumber);
		
		if (flight == null)
			;//flight does not exist
			
		session.getTransaction().commit();
		
		return flight;
	}
	
	public List listFlights() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from Flight").list();
		session.getTransaction().commit();

	    return result;
	}
	
	public void login(String username, String password) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		// retrieve customer record from the databse
		Customer customer = (Customer) session.get(Customer.class, new String(username));
		
		if (customer == null) {
			// customer record does not exist in the database
		} 
		else {
			//compare entered password to stored password
			if (password.compareTo(customer.getPassword()) == 0) {
				// login successful
			}
			else {
				// login error: incorrect password
			}
		}
		session.getTransaction().commit();
	}
	
	public static void main (String[] args){
		FlightManager mgr = new FlightManager();
		mgr.createAndStoreFlight(924, "FL");
		
		List flights = mgr.listFlights();
	    for (int i = 0; i < flights.size(); i++) {
	    	Flight flight = (Flight) flights.get(i);
	        System.out.println("Flight: " + flight.getFlightNo());
	    }

		HibernateUtil.getSessionFactory().close();
	}
}