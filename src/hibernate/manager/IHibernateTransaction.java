package hibernate.manager;

import org.hibernate.Session;

public interface IHibernateTransaction {
   public void transaction(Session session);
}
