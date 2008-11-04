package hibernate.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import hibernate.Flight;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class FlightManagerTest {

   @Test
   public void testGetFlightWithNoLazy() {
      final Flight f = FlightManager.getFlight(157);
      f.getAirline().getName();
   }

   @Test
   public void testListFlightsNull() {
      final List flightList = FlightManager.getFlightList(null, null, 0, 0, 0, 0, 0);
      assertNotNull(flightList);
      assertTrue(flightList.size() > 0);
   }

   @Test
   public void testListFlightsAnytime() {
      final List flightList = FlightManager.getFlightList("DEN", "BWI", 2009, 1, 4, -99, -99);
      assertNotNull(flightList);
      assertTrue(flightList.size() > 0);
   }

   @Test
   public void testListFlightsCertainTime() {
      final List flightList = FlightManager.getFlightList("DEN", "BWI", 2009, 1, 4, 10, 2);
      assertNotNull(flightList);
      assertTrue(flightList.size() > 0);
   }

   @Test
   public void testGetTimeRange1() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightManager.getTimeRange(2008, 1, 1, -99, -99);
      calendar.set(2008, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2008, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRange2() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightManager.getTimeRange(2008, 1, 1, 1, 2);
      calendar.set(2007, 11, 31, 23, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2008, 0, 1, 3, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRange3() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightManager.getTimeRange(2008, 1, 1, 1, -99);
      calendar.set(2008, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2008, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRangeBadMonth() {
      assertNull(FlightManager.getTimeRange(2009, -9, 1, 1, -99));
   }

   @Test
   public void testGetTimeRangeBadDay() {
      assertNull(FlightManager.getTimeRange(2009, 1, -9, 1, -99));
   }

   @Test
   public void testGetTimeRangeBadHour() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightManager.getTimeRange(2009, 1, 1, -99, -99);
      calendar.set(2009, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2009, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRangeBadRange1() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightManager.getTimeRange(2009, 1, 1, 2, -99);
      calendar.set(2009, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2009, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRangeBadRange2() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightManager.getTimeRange(2009, 1, 1, 2, -1);
      calendar.set(2009, 0, 1, 0, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2009, 0, 1, 23, 59, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

   @Test
   public void testGetTimeRangeBadRange3() {
      final Calendar calendar = Calendar.getInstance();

      final Date[] dates = FlightManager.getTimeRange(2009, 1, 1, 2, 0);
      calendar.set(2009, 0, 1, 2, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[0].toString());
      calendar.set(2009, 0, 1, 2, 0, 0);
      assertEquals(calendar.getTime().toString(), dates[1].toString());
   }

}