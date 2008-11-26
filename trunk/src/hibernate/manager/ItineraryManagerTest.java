package hibernate.manager;

import static org.junit.Assert.*;

import java.util.List;

import hibernate.Itinerary;
import hibernate.Itinerary.ESeatClass;

import org.junit.Test;

public class ItineraryManagerTest {

   @Test
   public void testReserveFlight() {
      ItineraryManager.cancelReserved("jjohnson", 157);
      final boolean bReserve = ItineraryManager.reserve("jjohnson", 157, ESeatClass.ECONOMY, 1);
      assertTrue(bReserve);
   }

   @Test
   public void testCancelReserveFlight() {
      ItineraryManager.reserve("jjohnson", 157, ESeatClass.ECONOMY, 1);
      final boolean bCancel = ItineraryManager.cancelReserved("jjohnson", 157);
      assertTrue(bCancel);
   }

   @Test
   public void testGetReserved() {
      ItineraryManager.cancelReserved("jjohnson", 157);
      final boolean bReserve = ItineraryManager.reserve("jjohnson", 157, ESeatClass.ECONOMY, 1);
      assertTrue(bReserve);
      final List<Itinerary> flights = ItineraryManager.getReserved("jjohnson");
      assertTrue(flights.size() > 0);
   }
}
