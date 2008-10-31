package hibernate.manager;

import java.util.ArrayList;
import java.util.List;

import hibernate.Customer;
import hibernate.Flight;
import hibernate.Itinerary;
import hibernate.ItineraryId;

import org.hibernate.Query;
import org.hibernate.Session;

@SuppressWarnings("unchecked")
public class ItineraryManager {

   public static final String CANCELED = "canceled";
   public static final String BOOKED = "booked";
   public static final String RESERVED = "reserved";

   public static boolean reserve(String userName, int flightNo) {
      boolean bRst = false;
      {
         final ItineraryId pKey = new ItineraryId(flightNo, userName);

         final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
         session.beginTransaction();
         final Itinerary it = (Itinerary) session.get(Itinerary.class, pKey);
         if (null == it) {
            bRst = true;
            final Customer customer = new Customer(userName);
            final Flight flight = new Flight(flightNo);
            final Itinerary newIt = new Itinerary(pKey, customer, flight);
            newIt.setStatus(RESERVED);
            session.save(newIt);
         }
         session.getTransaction().commit();
      }
      return bRst;
   }

   public static boolean cancelReserve(String userName, int flightNo) {
      boolean bRst = false;
      {
         final ItineraryId pKey = new ItineraryId(flightNo, userName);

         final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
         session.beginTransaction();
         final Itinerary it = (Itinerary) session.get(Itinerary.class, pKey);
         if (null != it) {
            bRst = true;
            session.delete(it);
         }
         session.getTransaction().commit();
      }
      return bRst;
   }

   public static List<Flight> getReserved(String userName) {
      return getItinerary(userName, RESERVED);
   }

   public static List<Flight> getBooked(String userName) {
      return getItinerary(userName, BOOKED);
   }

   public static List<Flight> getCanceled(String userName) {
      return getItinerary(userName, CANCELED);
   }

   private static List<Flight> getItinerary(String userName, String status) {
      final List<Flight> rst = new ArrayList<Flight>();
      {
         final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
         session.beginTransaction();
         final Query q = session.createQuery("FROM Itinerary WHERE USERNAME = ? AND STATUS = ?")
               .setString(0, userName).setString(1, status);
         final List<Itinerary> its = q.list();
         for (final Itinerary it : its) {
            final int flightNo = it.getFlight().getFlightNo();
            rst.add((Flight) session.get(Flight.class, flightNo));
         }
         session.close();
      }
      return rst;

   }

}
