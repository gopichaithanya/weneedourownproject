package hibernate.manager;

import java.util.List;

import org.hibernate.Session;

/**
 * AirlineManager contains functions to access and maintain airline objects
 */
@SuppressWarnings("unchecked")
public class AirlineManager {

   /**
    * This is not designed to be an instance.
    */
   private AirlineManager() {
   }

   /**
    * Returns a list of airline codes and names from the airline table
    * @return a list of airline codes and names
    */
   public static List<String[]> getAirlineCodeAndName() {
      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      final List<String[]> airlines = session.createQuery(
            "SELECT code, name FROM Airline as airline").list();
      session.close();
      return airlines;
   }
}
