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
      
      final Object[] sample = airports.get(0);
      assertEquals(2, sample.length);
      assertTrue(sample[0] instanceof String);
      assertTrue(sample[1] instanceof String);
      
      final String code = (String) sample[0];
      final String name = (String) sample[1];
      assertEquals("1G4", code);
      assertEquals("GRAND CANYON WEST", name);
   }

   @Test
   public void testGetAirportCode() {
      final List<String> airports = AirportManager.getAirportCode();
      assertNotNull(airports);
      assertEquals(567, airports.size());
      assertTrue(airports.get(0) instanceof String);
      assertEquals("1G4", airports.get(0));
   }

   @Test
   public void testGetAirportLatLong() {
      final List<Float> latLng = AirportManager.getAirportLatLong("YNG");
      assertEquals(41.260556f, (float) latLng.get(0), 0.01); // 41-15-38.6480N
      assertEquals(-80.678889f, (float) latLng.get(1), 0.01); // 080-40-44.7480W
   }
}
