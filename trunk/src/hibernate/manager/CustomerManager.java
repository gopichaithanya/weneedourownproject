package hibernate.manager;

import java.util.List;

import hibernate.Customer;
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
	
	public List<?> listCustomers() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<?> result = session.createQuery("from Customer").list();
		session.getTransaction().commit();

	    return result;
	}
	
	public static void main (String[] args){
		CustomerManager mgr = new CustomerManager();
		
		List<?> customers = mgr.listCustomers();
	    for (int i = 0; i < customers.size(); i++) {
	    	Customer customer = (Customer) customers.get(i);
	        System.out.println("Customer: " + customer.getUsername());
	    }

		HibernateUtil.getSessionFactory().close();
		
	}
}