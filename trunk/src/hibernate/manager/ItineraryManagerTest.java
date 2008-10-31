package hibernate.manager;

import static org.junit.Assert.*;

import java.util.List;

import hibernate.Flight;

import org.junit.Test;

public class ItineraryManagerTest {

   @Test
   public void testReserveFlight() {
      ItineraryManager.cancelReserve("jjohnson", 157);
      final boolean bReserve = ItineraryManager.reserve("jjohnson", 157);
      assertTrue(bReserve);
   }

   @Test
   public void testCancelReserveFlight() {
      ItineraryManager.reserve("jjohnson", 157);
      final boolean bCancel = ItineraryManager.cancelReserve("jjohnson", 157);
      assertTrue(bCancel);
   }

   @Test
   public void testGetReserved() {
      ItineraryManager.cancelReserve("jjohnson", 157);
      final boolean bReserve = ItineraryManager.reserve("jjohnson", 157);
      assertTrue(bReserve);
      final List<Flight> flights = ItineraryManager.getReserved("jjohnson");
      assertTrue(flights.size() > 0);
   }
}
