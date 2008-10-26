package hibernate.manager;

import hibernate.*;
import hibernate.util.*;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import hibernate.Customer;

public class CustomerManager {
	
	/**
	 * Default Constructor
	 */
	public CustomerManager() {}
	
	/**
	 * Adds a customer to the database
	 * @param customer - the customer to register
	 */
	static public void register(Customer customer) {
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
	static public boolean login(String username, String password) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		boolean truth = false;
		// retrieve customer record from the databse
		Customer customer = (Customer) session.get(Customer.class, new String(username));
		
		if (customer == null) {
			truth =  false;
		} 
		else {
			//compare entered password to stored password
			if (password.compareTo(customer.getPassword()) == 0) {
				truth = true;
			}
			else {
				truth = false;
			}
		}
		session.getTransaction().commit();
		return truth;
	}
}