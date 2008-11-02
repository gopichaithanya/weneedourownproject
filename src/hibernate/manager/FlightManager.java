package hibernate.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import hibernate.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.HibernateException;

/**
 * Hibernate class that implements the functions dealing with flight information used
 * by Spring
 * 
 * @author Israa Taha
 * @version 
 */
@SuppressWarnings("unchecked")
public class FlightManager {

   /**
    * Default Constructor
    */
   public FlightManager() {
   }

   /**
    * Adds a flight to the database
    * @param flight - the flight to be added
    */
   public static void addFlight(Flight flight) {
      
      try {
    	 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
         session.beginTransaction();
         session.save(flight);
         session.getTransaction().commit();
      } catch (HibernateException e) {
         e.printStackTrace();
      }
   }

   /**
    * Removes a flight from the database
    * @param flight - the flight to be removed
    */
   public void removeFlight(Flight flight) {
      
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
   public List<?> searchFlight(Flight flight) {
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();

      // search database for flights that match the values in the flight object
      List<?> result = session.createQuery("").list();

      session.getTransaction().commit();

      // return list of flights
      return result;
   }

   public List<?> listFlights() {
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      List<?> result = session.createQuery("from Flight").list();
      session.getTransaction().commit();

      return result;
   }

   public static Flight getFlight(int flightNo) {
      final int fno = Integer.valueOf(flightNo);
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      final Flight flight = (Flight) session.get(Flight.class, fno);
      session.close();
      return flight;
   }

   public static List<Flight> getFlightList(String departLoc, String arrivalLoc, int year,
         int month, int day, int hour, int hourRange) {

      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();

      Query q = null;
      {
         final Date[] departureTimeRange = getTimeRange(year, month, day, hour, hourRange);
         if (null == departLoc || null == arrivalLoc || null == departureTimeRange) {
            q = session.createQuery("FROM Flight");
         } else {
            q = session.createQuery(
                  "FROM Flight " + "WHERE DEPARTURE_LOCATION = ? " + "AND ARRIVAL_LOCATION = ? "
                        + "AND DEPARTURE_TIME > ? " + "AND DEPARTURE_TIME < ? ").setString(0,
                  departLoc).setString(1, arrivalLoc).setTimestamp(2, departureTimeRange[0])
                  .setTimestamp(3, departureTimeRange[1]);
         }
      }

      final List<Flight> flights = q.list();
      session.close();
      return flights;
   }

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
         startCalendar.add(Calendar.HOUR, -searchHour);
         endCalendar.set(y, m, d, h, 0, 0);
         endCalendar.add(Calendar.HOUR, searchHour);
      }

      final Date startDate = startCalendar.getTime();
      final Date endDate = endCalendar.getTime();
      final Date[] range = new Date[] { startDate, endDate };
      return range;
   }

   public static void main(String[] args) {
      FlightManager mgr = new FlightManager();

      List<?> flights = mgr.listFlights();
      for (int i = 0; i < flights.size(); i++) {
         Flight flight = (Flight) flights.get(i);
         System.out.println("Flight: " + flight.getFlightNo());
      }

      HibernateUtil.getSessionFactory().close();
   }
}