package hibernate.manager;

import hibernate.Airline;

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
    * Returns a list of airport codes from the airport table
    * @return a list of airport codes
    */
   public static List<String> getAirlineCode() {
      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      final List<String> airports = session.createQuery("SELECT code FROM Airline as airline")
            .list();
      session.close();
      return airports;
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

   public static Airline getAirline(final String code) {
      final Airline[] rst = new Airline[] { null };
      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {

            rst[0] = (Airline) session.get(Airline.class, code);
         }
      });
      return rst[0];
   }
}
