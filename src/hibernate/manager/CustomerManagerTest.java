package hibernate.manager;

import static org.junit.Assert.*;
import hibernate.Customer;

import org.hibernate.Session;
import org.junit.Test;

public class CustomerManagerTest {

   @Test
   public void testNormal() {
      final Customer c = new Customer();
      c.setUsername("testingUser");

      deleteUser(c.getUsername());
      final boolean bRst1 = CustomerManager.register(c);
      assertTrue(bRst1);
   }

   @Test
   public void testRedundantUsername() {
      final Customer c = new Customer();
      c.setUsername("testingUser");

      CustomerManager.register(c);
      final boolean bRst = CustomerManager.register(c);
      assertFalse(bRst);
      deleteUser(c.getUsername());
   }

   @Test
   public void testIllegalUsername1() {
      final Customer c = new Customer();
      c.setUsername("testing User");

      deleteUser(c.getUsername());
      final boolean bRst1 = CustomerManager.register(c);
      assertFalse(bRst1);
      deleteUser(c.getUsername());
   }

   @Test
   public void testIllegalUsername2() {
      final Customer c = new Customer();
      c.setUsername("1testingUser");

      deleteUser(c.getUsername());
      final boolean bRst1 = CustomerManager.register(c);
      assertFalse(bRst1);
      deleteUser(c.getUsername());
   }

   public static void deleteUser(final String userName) {
      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {
            final Customer c = (Customer) session.get(Customer.class, userName);
            if (null == c)
               return;
            session.delete(c);
         }
      });
   }
}
