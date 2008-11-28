package hibernate.manager;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hibernate.Customer;
import hibernate.Flight;
import hibernate.Itinerary;
import hibernate.ItineraryId;
import hibernate.Itinerary.ESeatClass;
import hibernate.Itinerary.EStatus;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ItineraryManagerTest {

   private ItineraryId id = null;
   private Itinerary it = null;
   private Calendar calendar = null;

   @Before
   public void before() {
      calendar = Calendar.getInstance();
      id = new ItineraryId(157, "jjohnson");
      it = createItinerary(id);
      it.setSeatClass(ESeatClass.ECONOMY);
      it.setNumOfSeats(1);
      deleteItinerary(id);
   }

   @After
   public void after() {
      deleteItinerary(id);
   }

   @Test
   public void testReserveFlight() {
      final Date curDate = calendar.getTime();
      final boolean bReserve = ItineraryManager.reserve(id.getUsername(), id.getFlightNo(),
            ESeatClass.get(it.getSeatClass()), it.getNumOfSeats());
      assertTrue(bReserve);

      final List<Itinerary> reserved = ItineraryManager.getReserved(id.getUsername());
      assertNotNull(reserved);

      boolean bFound = false;
      for (final Itinerary i : reserved) {
         assertEquals(EStatus.RESERVED.toString(), i.getStatus());
         if (false == i.getId().equals(id))
            continue;

         assertEquals(it.getNumOfSeats(), i.getNumOfSeats());
         assertEquals(it.getSeatClass(), i.getSeatClass());
         final Date reservedDate = i.getReservedTime();
         assertEquals(curDate.getTime(), reservedDate.getTime(), 2000); // 2 sec
         bFound = true;
      }
      assertTrue(bFound);
   }

   @Test
   public void testCancelReserveFlight() {
      ItineraryManager.reserve(id.getUsername(), id.getFlightNo(), ESeatClass
            .get(it.getSeatClass()), it.getNumOfSeats());
      final boolean bCancel = ItineraryManager.cancelReserved(id.getUsername(), id.getFlightNo());
      assertTrue(bCancel);

      final List<Itinerary> canceled = ItineraryManager.getCanceled(id.getUsername());
      assertNotNull(canceled);

      boolean bFound = false;
      for (final Itinerary it : canceled) {
         assertEquals(EStatus.CANCELED.toString(), it.getStatus());
         if (false == it.getId().equals(id))
            continue;
         bFound = true;
      }
      assertTrue(bFound);
   }

   @Test
   public void testBook() {
      final boolean bReserve = ItineraryManager.reserve(id.getUsername(), id.getFlightNo(),
            ESeatClass.get(it.getSeatClass()), it.getNumOfSeats());
      assertTrue(bReserve);

      final String ticketNo = "XX-FF-YYYYYY-ZZZ";
      final boolean bBook = ItineraryManager.book(id.getUsername(), id.getFlightNo(), ticketNo);
      assertTrue(bBook);

      final List<Itinerary> booked = ItineraryManager.getBooked(id.getUsername());
      assertNotNull(booked);

      boolean bFound = false;
      for (final Itinerary it : booked) {
         assertEquals(EStatus.BOOKED.toString(), it.getStatus());
         if (false == it.getId().equals(id))
            continue;

         final String t = it.getTicketNo();
         assertNotNull(t);
         assertEquals(t, ticketNo);
         bFound = true;
      }
      assertTrue(bFound);
   }

   @Test
   public void testGetReserved() {
      final boolean bReserve = ItineraryManager.reserve(id.getUsername(), id.getFlightNo(),
            ESeatClass.get(it.getSeatClass()), it.getNumOfSeats());
      assertTrue(bReserve);

      final List<Itinerary> flights = ItineraryManager.getReserved(id.getUsername());
      assertNotNull(flights);
      assertTrue(flights.size() > 0);

      boolean bFound = false;
      for (final Itinerary it : flights) {
         assertEquals(EStatus.RESERVED.toString(), it.getStatus());
         if (it.getId().equals(id))
            bFound = true;
      }
      assertTrue(bFound);
   }

   @Test
   public void testGetCanceled() {
      ItineraryManager.reserve(id.getUsername(), id.getFlightNo(), ESeatClass
            .get(it.getSeatClass()), it.getNumOfSeats());
      final boolean bCancel = ItineraryManager.cancelReserved(id.getUsername(), id.getFlightNo());
      assertTrue(bCancel);

      final List<Itinerary> flights = ItineraryManager.getCanceled(id.getUsername());
      assertNotNull(flights);
      assertTrue(flights.size() > 0);

      boolean bFound = false;
      for (final Itinerary it : flights) {
         assertEquals(EStatus.CANCELED.toString(), it.getStatus());
         if (it.getId().equals(id))
            bFound = true;
      }
      assertTrue(bFound);
   }

   @Test
   public void testGetBooked() {
      final boolean bReserve = ItineraryManager.reserve(id.getUsername(), id.getFlightNo(),
            ESeatClass.get(it.getSeatClass()), it.getNumOfSeats());
      assertTrue(bReserve);

      final String ticketNo = "XX-FF-YYYYYY-ZZZ";
      final boolean bBooked = ItineraryManager.book(id.getUsername(), id.getFlightNo(), ticketNo);
      assertTrue(bBooked);

      final List<Itinerary> flights = ItineraryManager.getBooked(id.getUsername());
      assertNotNull(flights);
      assertTrue(flights.size() > 0);

      boolean bFound = false;
      for (final Itinerary it : flights) {
         assertEquals(EStatus.BOOKED.toString(), it.getStatus());
         if (it.getId().equals(id))
            bFound = true;
      }
      assertTrue(bFound);
   }

   @Test
   public void testCheckExpiredReservationKept() {
      assertEquals(EStatus.RESERVED, checkExpReserv(-ItineraryManager.reservationTimeOutSec + 4));
   }

   @Test
   public void testCheckExpiredReservationCanceled() {
      assertEquals(EStatus.CANCELED, checkExpReserv(-ItineraryManager.reservationTimeOutSec - 4));
   }

   private EStatus checkExpReserv(int diffSec) {
      calendar.add(Calendar.SECOND, diffSec);
      final Date reservDate = calendar.getTime();

      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {
            it.setSeatClass(ESeatClass.ECONOMY);
            it.setNumOfSeats(1);
            it.setStatus(EStatus.RESERVED);
            it.setReservedTime(reservDate);
            session.save(it);
         }
      });

      ItineraryManager.checkExpiredReservation();

      final EStatus[] status = new EStatus[] { null };
      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {

            final Itinerary it = (Itinerary) session.get(Itinerary.class, id);
            assertNotNull(it);
            status[0] = EStatus.get(it.getStatus());
         }
      });
      return status[0];
   }

   public static boolean deleteItinerary(final ItineraryId id) {
      final Boolean[] bRst = new Boolean[] { false };
      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {

            final Itinerary it = (Itinerary) session.get(Itinerary.class, id);
            if (null == it)
               return;

            session.delete(it);
            bRst[0] = true;
         }
      });
      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {
            assertNull(session.get(Itinerary.class, id));
         }
      });
      return bRst[0];
   }

   private static Itinerary createItinerary(final ItineraryId id) {
      final Itinerary[] it = new Itinerary[] { new Itinerary() };
      it[0].setId(id);

      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {
            final Flight f = (Flight) session.get(Flight.class, id.getFlightNo());
            final Customer c = (Customer) session.get(Customer.class, id.getUsername());
            it[0].setFlight(f);
            it[0].setCustomer(c);
         }
      });

      return it[0];
   }

}
