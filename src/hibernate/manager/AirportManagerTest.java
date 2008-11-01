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

   @Test
   public void testGetAirportLatLong() {
      final List<Float> latLng = AirportManager.getAirportLatLong("YNG");
      assertEquals(41.260555f, (float) latLng.get(0), 0.01);
      assertEquals(-7.9322224f, (float) latLng.get(1), 0.01);
   }
}
