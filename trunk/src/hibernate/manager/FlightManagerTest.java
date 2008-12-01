package hibernate.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import hibernate.Flight;
import hibernate.Itinerary.ESeatClass;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
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
      final List flightList = FlightManager.getFlightList(null, null, null, 0, 0, 0, 0, 0, null,
            null, null, null, null);
      assertNotNull(flightList);
      assertTrue(flightList.size() > 0);
   }

   @Test
   public void testListFlightsAnytime() {
      final List flightList = FlightManager.getFlightList(null, "DEN", "BWI", 2009, 1, 4, -99, -99,
            null, null, null, null, null);
      assertNotNull(flightList);
      assertTrue(flightList.size() > 0);
   }

   @Test
   public void testListFlightsCertainTime() {
      final List flightList = FlightManager.getFlightList(null, "DEN", "BWI", 2009, 1, 4, 10, 2,
            null, null, null, null, null);
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

   @Test
   public void testDecreaseSeat() {
      final int orgSeat = FlightManager.getFlight(157).getEconomySeats();
      assertTrue(orgSeat > 0);

      final boolean bRst1 = FlightManager.decreaseSeats(157, 1, ESeatClass.ECONOMY);
      assertTrue(bRst1);
      final int newSeat1 = FlightManager.getFlight(157).getEconomySeats();
      assertEquals(orgSeat - 1, newSeat1);

      final boolean bRst2 = FlightManager.decreaseSeats(157, -1, ESeatClass.ECONOMY);
      assertTrue(bRst2);
      final int newSeat2 = FlightManager.getFlight(157).getEconomySeats();
      assertEquals(orgSeat, newSeat2);
   }

   public static boolean deleteFlight(final int flightNo) {
      final Boolean[] bRst = new Boolean[] { false };
      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {

            final Flight f = (Flight) session.get(Flight.class, flightNo);
            final boolean bNotExist = (null == f);
            if (bNotExist)
               return;

            session.delete(f);
            bRst[0] = true;
         }
      });
      return bRst[0];
   }

}
