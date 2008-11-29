package hibernate.manager;

import org.hibernate.Session;

/**
 * Hibernate trasaction interface.
 * @see HibernateUtil#doTransaction(IHibernateTransaction)
 */
interface IHibernateTransaction {
   
   /**
    * interface for a transaction
    * @param session Hibernate session object for the transaction
    */
   public void transaction(Session session);
}
