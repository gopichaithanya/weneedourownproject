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

   public static boolean reserve(String userName, int flightNo) {
      boolean bRst = false;
      {
         final ItineraryId pKey = new ItineraryId(flightNo, userName);

         final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
         session.beginTransaction();
         final Itinerary it = (Itinerary) session.get(Itinerary.class, pKey);
         if (null == it) {
            final Customer customer = new Customer(userName);
            final Flight flight = new Flight(flightNo);
            final Itinerary newIt = new Itinerary(pKey, customer, flight);
            newIt.setStatus(Itinerary.EStatus.RESERVED.toString());
            session.save(newIt);
            bRst = true;
         } else {
            it.setStatus(Itinerary.EStatus.RESERVED.toString());
            session.update(it);
            bRst = true;
         }
         session.getTransaction().commit();
      }
      return bRst;
   }

   public static boolean book(String userName, int flightNo, String ticketNo) {
	  boolean bRst = false;
	  {
		 final ItineraryId pKey = new ItineraryId(flightNo, userName);

	     final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	     session.beginTransaction();
	     final Itinerary it = (Itinerary) session.get(Itinerary.class, pKey);
	     if (null == it) {
	        final Customer customer = new Customer(userName);
	        final Flight flight = new Flight(flightNo);
	        final Itinerary newIt = new Itinerary(pKey, customer, flight);
	        newIt.setStatus(Itinerary.EStatus.BOOKED.toString());
	        newIt.setTicketNo(ticketNo);
	        session.save(newIt);
	        bRst = true;
	     } else {
	    	it.setStatus(Itinerary.EStatus.BOOKED.toString());
	    	it.setTicketNo(ticketNo);
	        session.update(it);
	        bRst = true;
	     }
	     session.getTransaction().commit();
	  }
	  return bRst;
   }

   public static boolean cancelReserved(String userName, int flightNo) {
      boolean bRst = false;
      {
         final ItineraryId pKey = new ItineraryId(flightNo, userName);

         final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
         session.beginTransaction();
         final Itinerary it = (Itinerary) session.get(Itinerary.class, pKey);
         if (null != it && it.getStatus().equals(Itinerary.EStatus.RESERVED.toString())) {
            bRst = true;
            it.setStatus(Itinerary.EStatus.CANCELED.toString());
            session.update(it);
         }
         session.getTransaction().commit();
      }
      return bRst;
   }

   public static List<Flight> getReserved(String userName) {
      return getItinerary(userName, Itinerary.EStatus.RESERVED.toString());
   }

   public static List<Flight> getBooked(String userName) {
      return getItinerary(userName, Itinerary.EStatus.BOOKED.toString());
   }

   public static List<Flight> getCanceled(String userName) {
      return getItinerary(userName, Itinerary.EStatus.CANCELED.toString());
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
