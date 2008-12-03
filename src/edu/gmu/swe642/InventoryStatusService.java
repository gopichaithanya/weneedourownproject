package edu.gmu.swe642;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * Inventory Status on number of available seats on flights
 */
public class InventoryStatusService {

   @SuppressWarnings("unused")
   private static Logger log = Logger.getLogger(InventoryStatusService.class);

   public InventoryStatusService() {

   }

   private static String findCatalinaBase() {
      final String classPath = System.getProperty("java.class.path", ".");
      final String sep = File.pathSeparator;

      final String[] classPaths = Pattern.compile(String.valueOf(sep)).split(classPath);
      for (final String cp : classPaths) { // cp will be
         log.info("classPath: " + cp);
         // "catalina.base/webapps/proj4398/WEB-INF/classes
         final File fileBin = new File(cp).getParentFile();
         if (null == fileBin)
            continue;
         if (false == fileBin.getName().equals("bin"))
            continue;

         final String catalinaBase = fileBin.getParentFile().getAbsolutePath();
         final File webapps = new File(catalinaBase + File.separator + "webapps");
         if (false == webapps.exists())
            continue;
         
         return catalinaBase;
      }
      return null;
   }

   /**
    * Return the number of empty seats on today's flights
    */
   public int getNumOfEmptySeats(Date date) {

      int flightDate = 42;
      int totalSeats = 0;

      try {

         // Configuration cfg = new Configuration().configure();
         // final String catalBase = findCatalinaBase();
         // log.info(catalBase);
         // create an instance of properties class

//			Properties props = new Properties();
//			props.load(new FileInputStream("build.properties"));
//			String tomcatpath = props.getProperty("appserver.home");
//			log.info(tomcatpath);
//			log.info(date.toString());
         //final String curPath = System.getProperty("user.dir", ".");
         final String catalina = findCatalinaBase();
         log.info("Catalina: " + catalina);

         final String fileDB = catalina + "/webapps/proj4398/WEB-INF/data/mydb";
         if (false == new File(fileDB + ".script").exists())
            throw new IllegalStateException("Database folder is not found.");
         
         final String url = "jdbc:hsqldb:file:" + fileDB;
         //final String url = "jdbc:hsqldb:file:/Users/wrice/workspace3/apache-tomcat-6.0.18/webapps/proj4398/WEB-INF/data/mydb";
         log.info("URL: " + url);
         final String username = "sa";
         final String password = "";
         //Date date = getDate(testdate);

         final DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy/hh");
         final String s = formatter.format(date);

         log.info("Today is " + s);

         final String[] dateParts = s.split("/");

         int month = Integer.parseInt(dateParts[0]);
         int day = Integer.parseInt(dateParts[1]);
         int year = Integer.parseInt(dateParts[2]);
         //int hour = Integer.parseInt(dateParts[3]);
         log.info(month + "" + year + "");

         Class.forName("org.hsqldb.jdbcDriver");

         log.info(url);

         Connection conn = null;
         //Date tdate = new Date();
         //long time = tdate.getTime();
         PreparedStatement pstmt = null;
         // Timestamp stamptime = new Timestamp(time);
         // log.info(stamptime.toString());

         conn = DriverManager.getConnection(url, // filenames
               username, // username
               password);

         // Statement stmt = conn.createStatement();
         pstmt = conn.prepareStatement("select Business_Seats, Economy_Seats from Flight where "
               + "  DEPARTURE_TIME >= ?  and  DEPARTURE_TIME <= ?");
         final String sql = "select Business_Seats, Economy_Seats from Flight DEPARTURE_TIME >"
               + date;
         log.info("The sql is" + sql);
         // pstmt.setDate(0, (java.sql.Date) tdate);
         final Date[] dates = getTimeRange(year, month, day, 12, 12);

         final Timestamp time1 = new Timestamp(dates[0].getTime());
         final Timestamp time2 = new Timestamp(dates[1].getTime());
         log.info(time1);
         log.info(time2);

         pstmt.setTimestamp(1, time1);

         pstmt.setTimestamp(2, time2);

         log.info("The sql is" + pstmt.toString());

         ResultSet rs = pstmt.executeQuery();

         while (rs.next()) {

            totalSeats += rs.getInt("Business_Seats");
            totalSeats += rs.getInt("Economy_Seats");

         }
         
         pstmt.close();
         conn.close();

         //date = date;
      } catch (NullPointerException e) {
         log.error(e.getMessage());
         throw new IllegalStateException(e);
         //return -1;

      } catch (Exception e) {
         log.error(e.getMessage());
         throw new IllegalStateException(e);
         //return -3;
      }
      return totalSeats;
   }

   private static Date[] getTimeRange(int year, int month, int day, int hour, int searchingHourRange) {
      if (month <= 0 || month > 12)
         return null;
      if (day <= 0 || day > 31)
         return null;

      final int y = Integer.valueOf(year);
      final int m = Integer.valueOf(month) - 1; // Month is 0-based in
      // Calendar class
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

   /**
    * Return the number of empty seats for the date range btw today and a
    * future date
    */
   public int getNumOfEmptySeatsForDateRange(Date date) {
      int totalSeats = 0; // Total seats for flights on given date
      try {
         boolean PM = false; // Check whether system time is AM or PM
         Calendar calendar = new GregorianCalendar();
         if (calendar.get(Calendar.AM_PM) == 0)
            PM = false;
         else
            PM = true;

         Date currDate = new Date();

         log.info("Current date: " + currDate);
         log.info("Input date: " + date);

         // Converting Date to Calendar type in order to increment date
         DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy/hh/mm/ss");
         String currStr = formatter.format(currDate);
         String[] currParts = currStr.split("/");
         int currMonth = Integer.parseInt(currParts[0]) - 1;
         int currDay = Integer.parseInt(currParts[1]);
         int currYear = Integer.parseInt(currParts[2]);
         int currHrs = Integer.parseInt(currParts[3]);
         if (PM)
            currHrs = currHrs + 12;
         int currMins = Integer.parseInt(currParts[4]);
         int currSecs = Integer.parseInt(currParts[5]);

         Calendar currCal = Calendar.getInstance();
         currCal.set(currYear, currMonth, currDay, currHrs, currMins, currSecs);
         currDate = currCal.getTime();
         log.info("Today's date using Calendar: " + currDate);

         // Fetch the total seats for date range ---> today, date input by user
         while (currDate.before(date)) {
            totalSeats = totalSeats + getNumOfEmptySeats(currDate);
            log.info("Total seats inside while loop for date " + totalSeats + " " + currDate);
            currCal.add(Calendar.DATE, 1);
            currDate = currCal.getTime();
         }
         if (PM)
            totalSeats = totalSeats + getNumOfEmptySeats(date);
      } catch (Exception e) {
         throw new IllegalStateException(e);
         //return -1;
      }
      return totalSeats;
   }

}
