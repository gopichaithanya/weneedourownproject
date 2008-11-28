package hibernate.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import hibernate.Customer;
import hibernate.Flight;
import hibernate.Itinerary;
import hibernate.ItineraryId;
import hibernate.Itinerary.ESeatClass;
import hibernate.Itinerary.EStatus;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * ItineraryManager contains functions to access and maintain itinerary objects
 */
@SuppressWarnings("unchecked")
public class ItineraryManager {

   private final static Logger log = Logger.getLogger(ItineraryManager.class);

   /**
    * Every reservations that is not paied in time will be canceled.
    * The amount of time should be specified by this variable.
    */
   public static final int reservationTimeOutSec = 2 * 60; // seconds 

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

      final Calendar calendar = Calendar.getInstance();
      final Date curDate = calendar.getTime();

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
               newIt.setReservedTime(curDate);
               session.save(newIt);

            } else if (false == it.getStatus().equals(EStatus.RESERVED.toString())) {
               it.setCustomer(customer);
               it.setFlight(flight);
               it.setSeatClass(seatClass);
               it.setNumOfSeats(passenger);
               it.setStatus(EStatus.RESERVED);
               it.setReservedTime(curDate);
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
      final Boolean[] bRst = new Boolean[] { false };
      final ItineraryId pKey = new ItineraryId(flightNo, userName);

      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {
            final Itinerary it = (Itinerary) session.get(Itinerary.class, pKey);
            if (null != it && it.getStatus().equals(EStatus.RESERVED.toString())) {
               bRst[0] = true;
               it.setStatus(EStatus.CANCELED);
               session.update(it);
            }
         }
      });
      return bRst[0];
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
   private static List<Itinerary> getItinerary(final String userName, final EStatus status) {
      checkExpiredReservation();

      final Object[] rst = new Object[] { null };
      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {
            final Query q = session.createQuery("FROM Itinerary WHERE USERNAME = ? AND STATUS = ?")
                  .setString(0, userName).setString(1, status.toString());
            final List<Itinerary> its = q.list();
            rst[0] = its;
         }
      });
      return (List<Itinerary>) rst[0];
   }

   /**
    * create the ticket number
    */
   public static String getTicketNum(Flight f, Customer c) {
      final Random rand = new Random();
      final String ticketNo = f.getAirline().getCode() + "-" + f.getFlightNo() + "-"
            + c.getUsername().toUpperCase() + "-" + (rand.nextInt(900) + 100);
      return ticketNo;
   }

   /**
    * Cancel expired reservation.
    */
   public static void checkExpiredReservation() {
      final Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.SECOND, -reservationTimeOutSec);
      final Date expDate = calendar.getTime();

      final List<Itinerary> its = new ArrayList();
      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {

            final Query q = session.createQuery("FROM Itinerary");
            final List<Itinerary> all = q.list();
            for (final Itinerary i : all) {
               if (EStatus.get(i.getStatus()) != EStatus.RESERVED)
                  continue;
               if (i.getReservedTime().after(expDate))
                  continue;
               its.add(i);
            }
         }
      });

      for (final Itinerary it : its) {
         log.info("ExpiredReservations: " + it.getCustomer().getUsername() + ", "
               + it.getFlight().getFlightNo());
         cancelReserved(it.getCustomer().getUsername(), it.getFlight().getFlightNo());
      }
   }
}
