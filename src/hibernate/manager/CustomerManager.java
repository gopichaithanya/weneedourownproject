package hibernate.manager;

import hibernate.Customer;

import org.hibernate.Session;

/**
 * CustomerManager contains functions to access and maintain customer objects
 */
public class CustomerManager {

   /**
    * This is not designed to be an instance.
    */
   private CustomerManager() {
   }

   /**
    * Adds a customer to the database
    * @param customer - the customer to register
    * @return whether or not it succeed.
    */
   static public boolean register(final Customer customer) {
      if (null == customer)
         return false;
      final String userName = customer.getUsername();
      if (null == userName)
         return false;
      final char firstChar = userName.charAt(0);
      final boolean bCapital = (firstChar >= 'A' && firstChar <= 'Z');
      final boolean bLowerCase = (firstChar >= 'a' && firstChar <= 'z');
      if (false == bCapital && false == bLowerCase)
         return false;
      if (userName.indexOf(' ') >= 0)
         return false;

      final Boolean[] bRst = new Boolean[] { false };
      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {

            final Object tmp = session.get(Customer.class, customer.getUsername());
            final boolean bAlreadyExist = (null != tmp);
            if (bAlreadyExist)
               return;

            session.save(customer);
            bRst[0] = true;
         }
      });
      return bRst[0];
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
         truth = false;
      } else {
         //compare entered password to stored password
         if (password.compareTo(customer.getPassword()) == 0) {
            truth = true;
         } else {
            truth = false;
         }
      }
      session.getTransaction().commit();
      return truth;
   }

   /**
    * Returns the customer object with the given username
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
      session.update(customer);
      return customer;
   }
}