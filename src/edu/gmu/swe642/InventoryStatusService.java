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
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Inventory Status on number of available seats on flights
 */
public class InventoryStatusService {

	public InventoryStatusService() {

	}

	private static String findCatalinaBase() {
		final String classPath = System.getProperty("java.class.path", ".");
		final String sep = String.valueOf(File.pathSeparatorChar);

		final String[] classPaths = Pattern.compile(String.valueOf(sep)).split(
				classPath);
		for (final String cp : classPaths) { // cp will be
			// "catalina.base/webapps/proj4398/WEB-INF/classes
			final File fileWebInf = new File(cp).getParentFile();
			if (null == fileWebInf)
				continue;
			if (false == fileWebInf.getName().equals("WEB-INF"))
				continue;
			return fileWebInf.getParentFile().getParentFile().getParentFile()
					.getAbsolutePath();
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
			// System.out.println(catalBase);
			// create an instance of properties class

			Properties props = new Properties();
			props.load(new FileInputStream("build.properties"));
			String tomcatpath = props.getProperty("appserver.home");
			System.out.println(tomcatpath);
			System.out.println(date.toString());
			final String url = "jdbc:hsqldb:file:build/mydb";
			final String username = "sa";
			final String password = "";
			//Date date = getDate(testdate);

			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy/hh");
			String s = formatter.format(date);

			System.out.println("Today is " + s);

			String[] dateParts = s.split("/");

			int month = Integer.parseInt(dateParts[0]);
			int day = Integer.parseInt(dateParts[1]);
			int year = Integer.parseInt(dateParts[2]);
			//int hour = Integer.parseInt(dateParts[3]);
			System.out.print(month + "" + year + "");

			Class.forName("org.hsqldb.jdbcDriver");

			System.out.println(url);

			Connection conn = null;
			//Date tdate = new Date();
			//long time = tdate.getTime();
			PreparedStatement pstmt = null;
			// Timestamp stamptime = new Timestamp(time);
			// System.out.println(stamptime.toString());

			conn = DriverManager.getConnection(url, // filenames
					username, // username
					password);

			// Statement stmt = conn.createStatement();
			pstmt = conn
					.prepareStatement("select Business_Seats, Economy_Seats from Flight where "
							+ "  DEPARTURE_TIME > ?  and  DEPARTURE_TIME < ?");
			String sql = "select Business_Seats, Economy_Seats from Flight DEPARTURE_TIME >"
					+ date;
			System.out.println("The sql is" + sql);
			// pstmt.setDate(0, (java.sql.Date) tdate);
			Date[] dates = getTimeRange(year, month, day, 12, 12);

			Timestamp time1 = new Timestamp(dates[0].getTime());
			Timestamp time2 = new Timestamp(dates[1].getTime());
			System.out.println(time1);
			System.out.println(time2);

			pstmt.setTimestamp(1, time1);

			pstmt.setTimestamp(2, time2);

			System.out.println("The sql is" + pstmt.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				totalSeats += rs.getInt("Business_Seats");
				totalSeats += rs.getInt("Economy_Seats");

			}

			date = date;
		} catch (NullPointerException e) {
			return -1;

		} catch (Exception e) {
			return -3;
		}
		return totalSeats;
	}

	public static Date[] getTimeRange(int year, int month, int day, int hour,
			int searchingHourRange) {
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
    public int getNumOfEmptySeatsForDateRange(Date date) 
    {
        int totalSeats = 0;
        //System.out.println("Date entered by user in function() " + date.toString());
        try
        {
            Date currDate = new Date();
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy/hh/mm/ss");
            String currStr = formatter.format(currDate);
            String dateStr = formatter.format(date);
            
            String[] currParts = currStr.split("/");
            int currMonth = Integer.parseInt(currParts[0]) - 1;
            int currDay = Integer.parseInt(currParts[1]);
            int currYear = Integer.parseInt(currParts[2]);
            int currHrs = Integer.parseInt(currParts[3]) - 12;
            int currMins = Integer.parseInt(currParts[4]);
            int currSecs = Integer.parseInt(currParts[5]);
            
            String[] dateParts = dateStr.split("/");
            int month = Integer.parseInt(dateParts[0]);
            int day = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            int hrs = Integer.parseInt(dateParts[3]);
            int mins = Integer.parseInt(dateParts[4]);
            int secs = Integer.parseInt(dateParts[5]);
            
            //System.out.println("Current date: " + currDate);
            //System.out.println("Input date: " + date);
            Calendar currCal = Calendar.getInstance();
            currCal.set(currYear, currMonth, currDay, currHrs, currMins, currSecs);
            
            Calendar inputCal = Calendar.getInstance();
            inputCal.set(year, month, day, hrs, mins, secs);
            
            //System.out.println("Current cal: " + currCal.toString());
            //System.out.println("Input cal: " + inputCal.toString());
            if(date.before(currDate))
                return 0;
            else 
            {
                //System.out.println("Today's date part 1: " + currDate);
                totalSeats = totalSeats + getNumOfEmptySeats(currDate);
                currCal.add(Calendar.DATE,2);
                currDate = currCal.getTime();   
                
                while(date.after(currDate))
                {
                    //System.out.println("Today's date part 2: " + currDate);
                    totalSeats = totalSeats + getNumOfEmptySeats(currDate);
                    currCal.add(Calendar.DATE,1);
                    currDate = currCal.getTime();                 
                }
                //System.out.println("Today's date part 3: " + currDate + " " + totalSeats);
                totalSeats = totalSeats + getNumOfEmptySeats(currDate);
                //System.out.println("Today's date part 4: " + currDate + " " + totalSeats);
            }
       }
       catch(Exception E)
       {
           return -1;
       }
       return totalSeats;
   }

	/**
	 * 
	 * @param test
	 * @return
	 */
	static public Date getDate(String test) {

		Date date = new Date();

		try {
			DateFormat formatter = new SimpleDateFormat();
			test = test.replaceAll("-", "/");
			test = test.replaceAll(" ", "/");

			// String[] dateParts = test.split("/");
			formatter = new SimpleDateFormat("MM/dd/yyyy/hh");
			date = (Date) formatter.parse(test);

		} catch (ParseException e) {
			return date;
		}

		return date;
	}

	public static void main(String[] args) {
		InventoryStatusService test = new InventoryStatusService();
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		
		try{
		Date today = (Date)formatter.parse("2008-12-26T08:10:00-12:00");
		System.out.println(today.toString());
		int e = test.getNumOfEmptySeats(today);
		System.out.println(e);
		}catch(ParseException p)
		{
			System.out.println(p.toString());
		}
		// System.out.println(formatter.format(today));

	}
}
