package hibernate.manager;

import java.sql.SQLException;

import org.hibernate.Session;

/**
 * Hibernate trasaction interface.
 * @see HibernateUtil#doTransaction(IHibernateTransaction)
 */
interface IHibernateTransaction {
   
   /**
    * interface for a transaction
    * @param session Hibernate session object for the transaction
    * @throws SQLException 
    */
   public void transaction(Session session) throws SQLException;
}
