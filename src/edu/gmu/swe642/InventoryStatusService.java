package edu.gmu.swe642;

import hibernate.Flight;
import hibernate.manager.FlightManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Inventory Status on number of available seats on flights
 */
public class InventoryStatusService {

	public InventoryStatusService() {

	}

	/**
	 * Return the number of empty seats on today's flights
	 */

	// yyyy.MMMMM.dd
	public int getNumOfEmptySeats(String testdate) {

		int flightDate = 0;
		Date date = getDate(testdate);

		System.out.println(date);

		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy/hh");
		String s = formatter.format(date);
		System.out.println("Today is " + s);

		String[] dateParts = s.split("/");

		System.out.println("The Month is " + dateParts[0]);
		System.out.println("The date is " + dateParts[1]);
		System.out.println("The Year is " + dateParts[2]);
		System.out.println("The hour is " + dateParts[3]);

		int month = Integer.parseInt(dateParts[0]);
		int day = Integer.parseInt(dateParts[1]);
		int year = Integer.parseInt(dateParts[2]);
		int hour = Integer.parseInt(dateParts[3]);

		final List flightList = FlightManager.getFlightList("IAD", "LAX", year,
				month, day, hour, 2);

		if (flightList.size() > 0) {
			Flight flightTest = (Flight) flightList.get(0);
			// testDate = flightTest.getDepartureTime();
			System.out.println(flightTest.getBusinessSeats()
					+ flightTest.getEconomySeats());
			flightDate = flightTest.getBusinessSeats()
					+ flightTest.getEconomySeats();

		}

		return flightDate;
	}

	/**
	 * Return the number of empty seats for the date range btw today and a
	 * future date
	 */
	public int getNumOfEmptySeatsForDateRange(Date date) {
		return 55;
	}

	/**
	 * 
	 * @param test
	 * @return
	 */
	static public Date getDate(String test) {
		DateFormat formatter = new SimpleDateFormat();
		Date date = new Date();

		try {

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
		int e = test.getNumOfEmptySeats("12-26-2008 10");

		System.out.println(e);
		Date today = new Date();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy/hh");
		System.out.println(formatter.format(today));

	}
}
