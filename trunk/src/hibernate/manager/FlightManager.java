package hibernate.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import hibernate.*;
import hibernate.Flight.EFlightStatus;
import hibernate.Itinerary.ESeatClass;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.HibernateException;

/**
 * FlightManager contains functions to access and maintain flight objects
 */
@SuppressWarnings("unchecked")
public class FlightManager {

   /**
    * Week enumeration type
    */
   public enum EWeek {
      SUNDAY("Sunday", Calendar.SUNDAY), MONDAY("Monday", Calendar.MONDAY), TUESDAY("Tuesday",
            Calendar.TUESDAY), WEDNESDAY("Wednesday", Calendar.WEDNESDAY), THURSDAY("Thursday",
            Calendar.THURSDAY), FRIDAY("Friday", Calendar.FRIDAY), SATURDAY("Saturday",
            Calendar.SATURDAY);

      private String description;
      private int calendarWeek;

      EWeek(String desc, int week) {
         this.description = desc;
         this.calendarWeek = week;
      }

      public String getDescription() {
         return this.description;
      }

      public int getCalendarWeek() {
         return this.calendarWeek;
      }
   }

   private static final Integer defaultHourRange = 3;
   private static Logger log = Logger.getLogger(FlightManager.class);

   /**
    * This is not designed to be an instance.
    */
   private FlightManager() {
   }

   /**
    * Adds a flight to the database
    * @param flight - the flight to be added
    */
   public static boolean addFlight(Flight flight) {

      boolean bRst = false;
      try {
         Session session = HibernateUtil.getSessionFactory().getCurrentSession();
         session.beginTransaction();
         final boolean bAlreadyExist = (null != session.get(Flight.class, flight.getFlightNo()));
         if (false == bAlreadyExist) {
            session.save(flight);
            bRst = true;
         }
         session.getTransaction().commit();
      } catch (HibernateException e) {
         e.printStackTrace();
      }
      return bRst;
   }

   /**
    * Removes a flight from the database
    * @param flight - the flight to be removed
    */
   public static void removeFlight(Flight flight) {

      try {
         Session session = HibernateUtil.getSessionFactory().getCurrentSession();
         session.beginTransaction();
         session.delete(flight);
         session.getTransaction().commit();
      } catch (HibernateException e) {
         e.printStackTrace();
      }
   }

   /**
    * Search for flight(s)
    * @param flight
    * @return list of flights that match the specified criteria
    */
   public static List<?> searchFlight(Flight flight) {
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();

      // search database for flights that match the values in the flight object
      List<?> result = session.createQuery("").list();

      session.getTransaction().commit();

      // return list of flights
      return result;
   }

   /**
    * Returns a list of all flights in the database
    * @return a list of all flights
    */
   public static List<Flight> listFlights() {
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      List<Flight> result = session.createQuery("from Flight").list();
      session.getTransaction().commit();

      return result;
   }

   /**
    * Returns the flight with the given flightNo
    * @param flightNo - the flight number
    * @return the flight object with the given flightNo, null if there is no such flight
    */
   public static Flight getFlight(int flightNo) {
      final int fno = flightNo;
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      final Flight flight = (Flight) session.get(Flight.class, fno);
      session.close();
      return flight;
   }

   /**
    * Returns a list of flights that satisfy the given parameter constraints 
    * @param airline airline code name
    * @param departLoc - the flight departure location
    * @param arriveLoc - the flight arrival location
    * @param departYear - depart year
    * @param departMonth - depart month
    * @param departDay - depart day
    * @param departHour - depart hour
    * @param departHourRange - the hour range
    * @param departWeek depart week
    * @param arriveYear arrive year
    * @param arriveMonth arrive month
    * @param arriveDay arrive day
    * @param arriveHour arrive hour
    * @param arriveHourRange arrive hour range
    * @param arriveWeek arrive wekk
    * @return the list of flights
    */
   public static List<Flight> getFlightList(String airline, String departLoc, String arriveLoc,
         Integer departYear, Integer departMonth, Integer departDay, Integer departHour,
         Integer departHourRange, EWeek departWeek, Integer arriveYear, Integer arriveMonth,
         Integer arriveDay, Integer arriveHour, Integer arriveHourRange, EWeek arriveWeek) {

      if (null == departHourRange)
         departHourRange = defaultHourRange;
      if (null == arriveHourRange)
         arriveHourRange = defaultHourRange;
      if (null == departHour) {
         departHour = 12;
         departHourRange = 12;
      }
      if (null == arriveHour) {
         arriveHour = 12;
         arriveHourRange = 12;
      }

      Date[] departTimeRange = null;
      if (null == departWeek) {
         if (null != departYear && null != departMonth && null != departDay && null != departHour)
            departTimeRange = getTimeRange(departYear, departMonth, departDay, departHour,
                  departHourRange);
      }

      Date[] arriveTimeRange = null;
      if (null == arriveWeek) {
         if (null != arriveYear && null != arriveMonth && null != arriveDay && null != arriveHour)
            arriveTimeRange = getTimeRange(arriveYear, arriveMonth, arriveDay, arriveHour,
                  arriveHourRange);
      }

      final List<String> whereClause = new ArrayList();
      if (null != airline)
         whereClause.add("AIRLINE_CODE = ?");
      if (null != departLoc)
         whereClause.add("DEPARTURE_LOCATION = ?");
      if (null != arriveLoc)
         whereClause.add("ARRIVAL_LOCATION = ?");
      if (null != departTimeRange) {
         whereClause.add("DEPARTURE_TIME >= ?");
         whereClause.add("DEPARTURE_TIME <= ?");
      }
      if (null != arriveTimeRange) {
         whereClause.add("ARRIVAL_TIME >= ?");
         whereClause.add("ARRIVAL_TIME <= ?");
      }

      String query = "FROM Flight";
      for (int i = 0; i < whereClause.size(); ++i) {
         query += ((0 == i) ? " WHERE " : " AND ");
         query += " " + whereClause.get(i) + " ";
      }
      log.info(query);

      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();

      Query q = session.createQuery(query);
      for (int i = 0; i < whereClause.size(); ++i) {
         if (whereClause.get(i).startsWith("AIRLINE_CODE"))
            q = q.setString(i, airline);
         else if (whereClause.get(i).startsWith("DEPARTURE_LOCATION"))
            q = q.setString(i, departLoc);
         else if (whereClause.get(i).startsWith("ARRIVAL_LOCATION"))
            q = q.setString(i, arriveLoc);
         else if (whereClause.get(i).startsWith("DEPARTURE_TIME >"))
            q = q.setTimestamp(i, departTimeRange[0]);
         else if (whereClause.get(i).startsWith("DEPARTURE_TIME <"))
            q = q.setTimestamp(i, departTimeRange[1]);
         else if (whereClause.get(i).startsWith("ARRIVAL_TIME >"))
            q = q.setTimestamp(i, arriveTimeRange[0]);
         else if (whereClause.get(i).startsWith("ARRIVAL_TIME <"))
            q = q.setTimestamp(i, arriveTimeRange[1]);
         else
            throw new UnsupportedOperationException();
      }

      final List<Flight> flights = q.list();
      session.close();

      if (null != departWeek) {
         for (final Iterator<Flight> iter = flights.iterator(); iter.hasNext();) {
            final Flight f = iter.next();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(f.getDepartureTime());
            final int week = calendar.get(Calendar.DAY_OF_WEEK);

            if (departWeek.getCalendarWeek() == week)
               continue;
            iter.remove();
         }
      }
      if (null != arriveWeek) {
         for (final Iterator<Flight> iter = flights.iterator(); iter.hasNext();) {
            final Flight f = iter.next();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(f.getArrivalTime());
            final int week = calendar.get(Calendar.DAY_OF_WEEK);

            if (arriveWeek.getCalendarWeek() == week)
               continue;
            iter.remove();
         }
      }

      return flights;
   }

   /**
    * Updates the number of seats in a flight by decrementing the numOfSeats from the available
    * seats in the requested seat class (business/economy)
    * @param flightNo - the flight number
    * @param numOfSeats - the number of seats
    */
   public static boolean decreaseSeats(int flightNo, int numOfSeats, ESeatClass seatClass) {
      boolean rst = false;
      final Flight flight = getFlight(flightNo);

      try {
         if (ESeatClass.BUSINESS.equals(seatClass)) {
            final int seat = getFlight(flightNo).getBusinessSeats();
            final int newSeat = seat - numOfSeats;
            if (newSeat < 0)
               throw new IllegalArgumentException();
            flight.setBusinessSeats(newSeat);
            rst = true;
         } else if (ESeatClass.ECONOMY.equals(seatClass)) {
            final int seat = getFlight(flightNo).getEconomySeats();
            final int newSeat = seat - numOfSeats;
            if (newSeat < 0)
               throw new IllegalArgumentException();
            flight.setEconomySeats(newSeat);
            rst = true;
         }

         final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
         session.beginTransaction();
         session.update(flight);
         session.getTransaction().commit();
      } catch (IllegalArgumentException e) {
         rst = false;
      }

      return rst;
   }

   /**
    * Returns a date object with the time range
    * @param year - the search year
    * @param month - the search month
    * @param day - the search day
    * @param hour - the search hour
    * @param searchingHourRange - the hour range
    * @return the time range
    */
   public static Date[] getTimeRange(int year, int month, int day, int hour, int searchingHourRange) {
      if (month <= 0 || month > 12)
         return null;
      if (day <= 0 || day > 31)
         return null;

      final int y = Integer.valueOf(year);
      final int m = Integer.valueOf(month) - 1; // Month is 0-based in Calendar class
      final int d = Integer.valueOf(day);

      boolean bAnytime = false;
      int h = 0;
      int searchHour = 0;
      try {
         h = Integer.valueOf(hour);
         searchHour = Integer.valueOf(searchingHourRange);
         if (h < 0 || h > 23)
            bAnytime = true;
         if (searchHour < 0)
            bAnytime = true;
      } catch (NumberFormatException e) {
         bAnytime = true;
      }

      final Calendar startCalendar = Calendar.getInstance();
      final Calendar endCalendar = Calendar.getInstance();
      if (bAnytime) {
         startCalendar.set(y, m, d, 0, 0, 0);
         endCalendar.set(y, m, d, 23, 59, 0);
      } else {
         startCalendar.set(y, m, d, h, 0, 0);
         startCalendar.add(Calendar.HOUR_OF_DAY, -searchHour);
         endCalendar.set(y, m, d, h, 0, 0);
         endCalendar.add(Calendar.HOUR_OF_DAY, searchHour);
      }

      final Date startDate = startCalendar.getTime();
      final Date endDate = endCalendar.getTime();
      final Date[] range = new Date[] { startDate, endDate };
      return range;
   }

   /**
    * returns the number of left seats for each seat class.
    * @param flightNo flightNo
    * @return # of left economy seats, # of total economy seats,
    *  # of left business seats, # of total business seats.
    */
   public static Integer[] getLeftSeats(final int flightNo) {
      if (flightNo < 0 || flightNo > 999)
         throw new IllegalArgumentException();

      final Integer[] rst = new Integer[] { null, null, null, null };
      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {

            final Flight f = (Flight) session.get(Flight.class, flightNo);
            rst[1] = f.getEconomySeats();
            rst[3] = f.getBusinessSeats();

            int leftEconomySeats = rst[1];
            int leftBusinessSeats = rst[3];
            final Query q = session.createQuery("FROM Itinerary WHERE flight_no = ?").setInteger(0,
                  flightNo);
            final List<Itinerary> its = q.list();
            for (final Itinerary it : its) {
               final Integer n = it.getNumOfSeats();
               if (null == n)
                  continue;

               if (it.getSeatClass().equals(ESeatClass.BUSINESS.toString()))
                  leftBusinessSeats -= n;
               else
                  leftEconomySeats -= n;
            }

            rst[0] = leftEconomySeats;
            rst[2] = leftBusinessSeats;
         }
      });

      return rst;
   }

   /**
    * Cancels flight by flight number
    * @param flightNo flight number
    * @return whether it succeed or not
    */
   public static boolean cancelFlight(final int flightNo) {
      final Boolean[] bRst = new Boolean[] { false };
      HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) {

            final Flight f = (Flight) session.get(Flight.class, flightNo);
            if (null == f)
               return;
            if (f.getStatus().equals(EFlightStatus.CANCELED.toString()))
               return;

            f.setStatus(EFlightStatus.CANCELED.toString());
            bRst[0] = true;

         }
      });

      ItineraryManager.cancelByFlightCancel(flightNo);
      return bRst[0];
   }

   public static int getNumOfEmptySeats(final Date date) {
      final Calendar c1 = Calendar.getInstance();
      c1.setTime(date);
      c1.set(Calendar.HOUR_OF_DAY, 0);
      c1.set(Calendar.MINUTE, 0);
      final Date date1 = c1.getTime();

      final Calendar c2 = Calendar.getInstance();
      c2.setTime(date);
      c2.set(Calendar.HOUR_OF_DAY, 23);
      c2.set(Calendar.MINUTE, 59);
      final Date date2 = c2.getTime();

      final Integer[] rst = new Integer[] { 0 };
      final SQLException transaction = HibernateUtil.doTransaction(new IHibernateTransaction() {
         public void transaction(Session session) throws SQLException {
            final Query q = session.createQuery(
                  "SELECT SUM(BUSINESS_SEATS), SUM(ECONOMY_SEATS) FROM Flight "
                        + " WHERE DEPARTURE_TIME >= ? AND DEPARTURE_TIME <= ?").setDate(0, date1)
                  .setDate(1, date2);
            final Object[] rsts = (Object[]) q.uniqueResult();
            if (null == q)
               throw new SQLException();

            rst[0] = ((Integer) (rsts[0])) + ((Integer) rsts[1]);
         }
      });
      if (null != transaction)
         throw new IllegalStateException(transaction);

      return rst[0];

   }
}