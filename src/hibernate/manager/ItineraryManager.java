package hibernate.manager;

import hibernate.Customer;
import hibernate.Flight;
import hibernate.Itinerary;
import hibernate.ItineraryId;
import hibernate.util.HibernateUtil;

import org.hibernate.Session;

public class ItineraryManager {

   public static final String RESERVED = "reserved";

   public static boolean reserve(String userName, int flightNo) {
      boolean bRst = false;
      final ItineraryId pKey = new ItineraryId(flightNo, userName);

      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      final Itinerary it = (Itinerary) session.get(Itinerary.class, pKey);
      if (null == it) {
         bRst = true;
         final Customer customer = new Customer(userName);
         final Flight flight = new Flight(flightNo);
         final Itinerary newIt = new Itinerary(pKey, customer, flight);
         newIt.setStatus("RESERVED");
         session.save(newIt);
      }
      session.getTransaction().commit();
      return bRst;
   }

   public static boolean cancelReserve(String userName, int flightNo) {
      boolean bRst = false;
      final ItineraryId pKey = new ItineraryId(flightNo, userName);

      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      final Itinerary it = (Itinerary) session.get(Itinerary.class, pKey);
      if (null != it) {
         bRst = true;
         session.delete(it);
      }
      session.getTransaction().commit();
      return bRst;
   }
}
