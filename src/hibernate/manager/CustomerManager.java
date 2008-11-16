package hibernate.manager;

import java.util.List;

import hibernate.Customer;

import org.hibernate.Session;
import org.hibernate.HibernateException;

public class CustomerManager {
	
	/**
	 * Constructora a new CustomerManager object
	 */
	public CustomerManager() {}
	
	/**
	 * Adds a customer to the database
	 * @param customer - the customer to register
	 */
	static public void register(Customer customer) {
		
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(customer);
			session.getTransaction().commit();
		}
		catch (HibernateException e) {
			e.printStackTrace();
		}
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
	
	/**
	 * 
	 * @param username - the customer username
	 * @return the customer with the given username
	 */
	public static Customer getCustomer(String username) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Customer customer = (Customer) session.get(Customer.class, new String(username));
		session.getTransaction().commit();
		return customer;
	}
	
	/**
	 * Updates a customer's info with the new ccNo and expiration date
	 */
	public static Customer updateCcNoAndExpiration(String username, Long ccNo, Integer expiration) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Customer customer = (Customer) session.get(Customer.class, new String(username));
		customer.setCcNo(ccNo);
		customer.setExpiration(expiration);
		session.getTransaction().commit();
		return customer;
	}
}