package hibernate.manager;

import static org.junit.Assert.*;

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
}
