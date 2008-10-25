package hibernate.manager;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class AirportManagerTest {

   @Test
   public void testGetAirportCodeAndName() {
      final List<String[]> airports = AirportManager.getAirportCodeAndName();
      assertNotNull(airports);
      assertEquals(567, airports.size());
      assertEquals(2, ((Object[]) airports.get(0)).length);
   }

   @Test
   public void testGetAirportCode() {
      final List<String[]> airports = AirportManager.getAirportCode();
      assertNotNull(airports);
      assertEquals(567, airports.size());
      assertTrue(airports.get(0) instanceof Object);
   }
}
