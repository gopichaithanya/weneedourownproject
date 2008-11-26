package hibernate.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hibernate.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.HibernateException;

/**
 * FlightManager contains functions to access and maintain flight objects
 */
@SuppressWarnings("unchecked")
public class FlightManager {

   /**
    * Constructs a new FlightManager object
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
      final int fno = Integer.valueOf(flightNo);
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      final Flight flight = (Flight) session.get(Flight.class, fno);
      session.close();
      return flight;
   }

   /**
    * Returns a list of flights that satisfy the given parameter constraints 
    * @param departLoc - the flight departure location
    * @param arrivalLoc - the flight arrival location
    * @param year - the year
    * @param month - the month
    * @param day - the day
    * @param hour - the hour
    * @param hourRange - the hour range
    * @return the list of flights
    */
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

   /**
    * Updates the number of seats in a flight by decrementing the numOfSeats from the available
    * seats in the requested seat class (business/economy)
    * @param flightNo - the flight number
    * @param numOfSeats - the number of seats
    */
   public static void updateSeats(int flightNo, int numOfSeats, String seatClass) {
	   Flight flight = getFlight(flightNo); 
	   
	   Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	   session.beginTransaction();
	   
	   if (seatClass.compareTo("business") == 0) {
		   int businessSeats = getFlight(flightNo).getBusinessSeats();
		   flight.setBusinessSeats(businessSeats - numOfSeats);
		   session.update(flight);
	   }
	   else if (seatClass.compareTo("economy") == 0) {
		   int economySeats = getFlight(flightNo).getEconomySeats();
		   flight.setEconomySeats(economySeats - numOfSeats);
		   session.update(flight);
	   }
	   session.getTransaction().commit();
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
         startCalendar.add(Calendar.HOUR, -searchHour);
         endCalendar.set(y, m, d, h, 0, 0);
         endCalendar.add(Calendar.HOUR, searchHour);
      }

      final Date startDate = startCalendar.getTime();
      final Date endDate = endCalendar.getTime();
      final Date[] range = new Date[] { startDate, endDate };
      return range;
   }
}