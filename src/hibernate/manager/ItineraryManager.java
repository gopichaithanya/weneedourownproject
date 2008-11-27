package hibernate.manager;

import java.util.List;
import java.util.Random;

import hibernate.Customer;
import hibernate.Flight;
import hibernate.Itinerary;
import hibernate.ItineraryId;
import hibernate.Itinerary.ESeatClass;
import hibernate.Itinerary.EStatus;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * ItineraryManager contains functions to access and maintain itinerary objects
 */
@SuppressWarnings("unchecked")
public class ItineraryManager {

   /**
    * Returns true if the flight was successfully reserved for the customer, false otherwise
    * @param userName - the customer's username
    * @param flightNo - the flight number
    * @param seatClass TODO
    * @param passenger TODO
    * @return true if the flight was successfully reserved for the customer, false otherwise
    */
   public static boolean reserve(final String userName, final int flightNo,
         final ESeatClass seatClass, final int passenger) {
      if (null == seatClass)
         return false;

      final Boolean[] bRst = new Boolean[] { new Boolean(false) };
      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {

            final Customer customer = (Customer) session.get(Customer.class, userName);
            final Flight flight = (Flight) session.get(Flight.class, flightNo);
            if (null == customer || null == flight)
               return;

            final ItineraryId pKey = new ItineraryId(flightNo, userName);
            final Itinerary it = (Itinerary) session.get(Itinerary.class, pKey);
            if (null == it) {
               final Itinerary newIt = new Itinerary(pKey, customer, flight, seatClass, passenger);
               newIt.setStatus(EStatus.RESERVED);
               session.save(newIt);

            } else if (false == it.getStatus().equals(EStatus.RESERVED.toString())) {
               it.setCustomer(customer);
               it.setFlight(flight);
               it.setSeatClass(seatClass);
               it.setNumOfSeats(passenger);
               it.setStatus(EStatus.RESERVED);
            } else
               return;

            bRst[0] = true;
         }
      });
      return bRst[0];
   }

   /**
    * Returns true if the flight was successfully booked for the customer, false otherwise
    * @param userName - the customer's user name
    * @param flightNo - the flight number
    * @param ticketNo TODO
    * @return true if the flight was successfully booked for the customer, false otherwise
    */
   public static boolean book(String userName, final int flightNo, final String ticketNo) {
      final ItineraryId pKey = new ItineraryId(flightNo, userName);

      final Boolean[] bRst1 = new Boolean[1];
      final Itinerary[] its = new Itinerary[1];
      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {

            its[0] = (Itinerary) session.get(Itinerary.class, pKey);
            its[0].setStatus(EStatus.BOOKED);
            its[0].setTicketNo(ticketNo);
            bRst1[0] = true;
         }
      });

      final boolean bRst2 = FlightManager.decreaseSeats(flightNo, its[0].getNumOfSeats(),
            ESeatClass.get(its[0].getSeatClass()));
      return bRst1[0] && bRst2;
   }

   /**
    * Returns true if the flight was canceled from the customer's itinerary, false otherwise
    * @param userName - the customer's username
    * @param flightNo - the flight number
    * @return true if the flight was canceled from the customer's itinerary, false otherwise
    */
   public static boolean cancelReserved(String userName, int flightNo) {
      boolean bRst = false;
      {
         final ItineraryId pKey = new ItineraryId(flightNo, userName);

         final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
         session.beginTransaction();
         final Itinerary it = (Itinerary) session.get(Itinerary.class, pKey);
         if (null != it && it.getStatus().equals(EStatus.RESERVED.toString())) {
            bRst = true;
            it.setStatus(EStatus.CANCELED);
            session.update(it);
         }
         session.getTransaction().commit();
      }
      return bRst;
   }

   /**
    * Return the list of reserved flights for the given customer
    * @param userName - the customer's username
    * @return the list of reserved flights for the given customer
    */
   public static List<Itinerary> getReserved(String userName) {
      return getItinerary(userName, EStatus.RESERVED);
   }

   /**
    * Return the list of booked flights for the given customer
    * @param userName - the customer's username
    * @return the list of booked flights for the given customer
    */
   public static List<Itinerary> getBooked(String userName) {
      return getItinerary(userName, EStatus.BOOKED);
   }

   /**
    * Return the list of canceled flights for the given customer
    * @param userName - the customer's username
    * @return the list of canceled flights for a given customer
    */
   public static List<Itinerary> getCanceled(String userName) {
      return getItinerary(userName, EStatus.CANCELED);
   }

   /**
    * Return the list of itineraries for a given customer with the given status parameter
    * @param userName - the customer's username
    * @param status - the status of flights to retrieve 
    * @return the list of itineraries
    */
   private static List<Itinerary> getItinerary(String userName, EStatus status) {
      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      final Query q = session.createQuery("FROM Itinerary WHERE USERNAME = ? AND STATUS = ?")
            .setString(0, userName).setString(1, status.toString());
      final List<Itinerary> its = q.list();
      session.close();
      return its;
   }

   public static String getTicketNum(Flight f, Customer c) {
      //create the ticket object
      final Random rand = new Random();
      final String ticketNo = f.getAirline().getCode() + "-" + f.getFlightNo() + "-"
            + c.getUsername().toUpperCase() + "-" + (rand.nextInt(900) + 100);
      return ticketNo;
   }
}
