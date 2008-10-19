package hibernate.manager;

import hibernate.*;
import hibernate.util.*;

import org.hibernate.Session;
import org.hibernate.HibernateException;

public class CustomerManager {
	
	/**
	 * Default Constructor
	 */
	public CustomerManager() {}
	
	/**
	 * Adds a customer to the database
	 * @param customer - the customer to register
	 */
	public void register(Customer customer) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		try {
			session.save(customer);
		}
		catch (HibernateException e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
	}
	
	/**
	 * Verifies username and password for login
	 */
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
}