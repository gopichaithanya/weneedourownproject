package edu.gmu.swe642;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

		int flightDate = 42;
		// Date date = getDate(testdate);

		// try {
		//
		// DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy/hh");
		// String s = formatter.format(date);
		// System.out.println("Today is " + s);
		//
		// String[] dateParts = s.split("/");
		//
		// int month = Integer.parseInt(dateParts[0]);
		// int day = Integer.parseInt(dateParts[1]);
		// int year = Integer.parseInt(dateParts[2]);
		// int hour = Integer.parseInt(dateParts[3]);
		//
		// final List flightList = FlightManager.getFlightList("IAD", "LAX",
		// year, month, day, hour, 2);
		//
		// if (flightList.size() > 0) {
		// Flight flightTest = (Flight) flightList.get(0);
		// // testDate = flightTest.getDepartureTime();
		// flightDate = flightTest.getBusinessSeats()
		// + flightTest.getEconomySeats();
		//
		// }
		// } catch (Exception e) {
		// e.toString();
		// }

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
		int e = test.getNumOfEmptySeats("12/26/2008/10");

		// System.out.println(e);
		Date today = new Date();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy/hh");
		// System.out.println(formatter.format(today));

	}
}
