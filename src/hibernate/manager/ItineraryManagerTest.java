package hibernate.manager;

import static org.junit.Assert.*;

import java.util.List;

import hibernate.Itinerary;
import hibernate.Itinerary.ESeatClass;
import hibernate.Itinerary.EStatus;

import org.junit.Test;

public class ItineraryManagerTest {

   @Test
   public void testReserveFlight() {
      ItineraryManager.cancelReserved("jjohnson", 157);
      final boolean bReserve = ItineraryManager.reserve("jjohnson", 157, ESeatClass.ECONOMY, 1);
      assertTrue(bReserve);

      final List<Itinerary> reserved = ItineraryManager.getReserved("jjohnson");
      assertNotNull(reserved);
      boolean bFound = false;
      for (final Itinerary it : reserved) {
         if (false == it.getCustomer().getUsername().equals("jjohnson"))
            continue;
         if (157 != it.getFlight().getFlightNo())
            continue;
         bFound = true;
         assertEquals(1, (int) it.getNumOfSeats());
         assertEquals(ESeatClass.ECONOMY.toString(), it.getSeatClass());
      }
      assertTrue(bFound);
   }

   @Test
   public void testCancelReserveFlight() {
      ItineraryManager.reserve("jjohnson", 157, ESeatClass.ECONOMY, 1);
      final boolean bCancel = ItineraryManager.cancelReserved("jjohnson", 157);
      assertTrue(bCancel);

      final List<Itinerary> reserved = ItineraryManager.getReserved("jjohnson");
      assertNotNull(reserved);
      boolean bFound = false;
      for (final Itinerary it : reserved) {
         if (false == it.getCustomer().getUsername().equals("jjohnson"))
            continue;
         if (157 != it.getFlight().getFlightNo())
            continue;
         bFound = true;
      }
      assertFalse(bFound);
   }

   @Test
   public void testGetReserved() {
      ItineraryManager.cancelReserved("jjohnson", 157);
      final boolean bReserve = ItineraryManager.reserve("jjohnson", 157, ESeatClass.ECONOMY, 1);
      assertTrue(bReserve);

      final List<Itinerary> flights = ItineraryManager.getReserved("jjohnson");
      assertNotNull(flights);
      assertTrue(flights.size() > 0);
      for (final Itinerary it : flights)
         assertEquals(EStatus.RESERVED.toString(), it.getStatus());

      ItineraryManager.cancelReserved("jjohnson", 157);
   }

   @Test
   public void testGetCanceled() {
      ItineraryManager.reserve("jjohnson", 157, ESeatClass.ECONOMY, 1);
      final boolean bCancel = ItineraryManager.cancelReserved("jjohnson", 157);
      assertTrue(bCancel);

      final List<Itinerary> flights = ItineraryManager.getCanceled("jjohnson");
      assertNotNull(flights);
      assertTrue(flights.size() > 0);
      for (final Itinerary it : flights)
         assertEquals(EStatus.CANCELED.toString(), it.getStatus());
   }

   @Test
   public void testGetBooked() {
      ItineraryManager.cancelReserved("jjohnson", 157);
      final boolean bReserve = ItineraryManager.reserve("jjohnson", 157, ESeatClass.ECONOMY, 1);
      assertTrue(bReserve);

      final String ticketNo = "XX-FF-YYYYYY-ZZZ";
      final boolean bBooked = ItineraryManager.book("jjohnson", 157, ticketNo);
      assertTrue(bBooked);

      final List<Itinerary> flights = ItineraryManager.getBooked("jjohnson");
      assertNotNull(flights);
      assertTrue(flights.size() > 0);
      for (final Itinerary it : flights)
         assertEquals(EStatus.BOOKED.toString(), it.getStatus());

      ItineraryManager.cancelReserved("jjohnson", 157);
   }

   @Test
   public void testBook() {
      ItineraryManager.cancelReserved("jjohnson", 157);
      final boolean bReserve = ItineraryManager.reserve("jjohnson", 157, ESeatClass.ECONOMY, 1);
      assertTrue(bReserve);

      final String ticketNo = "XX-FF-YYYYYY-ZZZ";
      final boolean bBook = ItineraryManager.book("jjohnson", 157, ticketNo);
      assertTrue(bBook);

      final List<Itinerary> booked = ItineraryManager.getBooked("jjohnson");
      assertNotNull(booked);
      boolean bFound = false;
      for (final Itinerary it : booked) {
         if (false == it.getCustomer().getUsername().equals("jjohnson"))
            continue;
         if (157 != it.getFlight().getFlightNo())
            continue;
         if (false == it.getTicketNo().equals(ticketNo))
            continue;
         bFound = true;
      }
      assertFalse(bFound);

      ItineraryManager.cancelReserved("jjohnson", 157);
   }
}
