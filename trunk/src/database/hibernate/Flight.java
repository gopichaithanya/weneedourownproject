package database.hibernate;

import java.sql.Timestamp;

public class Flight {
	
	/**
	 * The three-digit flight number
	 */
	private int flightNumber;
	
	/**
	 * The two-letter airline code
	 */
	private String airlineCode;
	
	/**
	 * The departure location
	 */
	private String departureLocation;
	
	/**
	 * The day and time of departure
	 */
	private Timestamp departureTime;
	
	/**
	 * The day and time of arrival
	 */
	private Timestamp arrivalTime;
	
	/**
	 * The arrival location
	 */
	private String arrivalLocation;
	
	/**
	 * The number of available seats in economy
	 */
	private int economySeats;
	
	/**
	 * The price of economy ticket
	 */
	private double economyPrice;
	
	/**
	 * The number of available seats in business
	 */
	private int businessSeats;
	
	/**
	 * The price of business ticket
	 */
	private int businessPrice;
	
	/**
	 * Default constructor
	 */
	public Flight() {}

	/**
	 * Returns the flight number
	 * @return flightNumber - the flight number
	 */
	public int getFlightNumber() {
		return flightNumber;
	}

	/**
	 * Sets the flight number
	 * @param flightNumber - the flight number
	 */
	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * Returns the airline code
	 * @return airlineCode - the airline code
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * Sets the airline code
	 * @param airlineCode - the airline code
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * Returns the departure location
	 * @return departureLocation - the departure location
	 */
	public String getDepartureLocation() {
		return departureLocation;
	}

	/**
	 * Sets the departure location
	 * @param departureLocation - the departure location
	 */
	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation;
	}
	
	/**
	 * Sets the day and time of departure
	 * @param departureTime - the day and time of departure
	 */
	public void setDepartureTime(Timestamp departureTime) {
		this.departureTime = departureTime;
	}
	
	/**
	 * Returns the day and time of departure
	 * @return departureTime - the day and time of departure
	 */
	public Timestamp getDepartureTime() {
		return departureTime;
	}
	
	/**
	 * Returns the arrival location
	 * @return arrivalLocation - the arrival location
	 */
	public String getArrivalLocation() {
		return arrivalLocation;
	}

	/**
	 * Sets the arrival location
	 * @param arrivalLocation - the arrival location
	 */
	public void setArrivalLocation(String arrivalLocation) {
		this.arrivalLocation = arrivalLocation;
	}
	
	/**
	 * Sets the day and time of arrival
	 * @param arrivalTime - the day and time of arrival
	 */
	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	/**
	 * Returns the day and time of arrival
	 * @return arrivalTime - the day and time of arrival
	 */
	public Timestamp getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Returns the number of available seats in economy class
	 * @return economySeats - the number of available seats in economy class
	 */
	public int getEconomySeats() {
		return economySeats;
	}

	/**
	 * Sets the number of available seats in economy class
	 * @param economySeats - the number of available seats in economy class
	 */
	public void setEconomySeats(int economySeats) {
		this.economySeats = economySeats;
	}

	/**
	 * Returns the price of economy class ticket
	 * @return economyPrice - the price of economy class ticket
	 */
	public double getEconomyPrice() {
		return economyPrice;
	}

	/**
	 * Sets the price of economy class ticket
	 * @param economyPrice - the price of economy class ticket
	 */
	public void setEconomyPrice(double economyPrice) {
		this.economyPrice = economyPrice;
	}

	/**
	 * Returns the number of available seats in business class
	 * @return businessSeats - the number of available seats in business class
	 */
	public int getBusinessSeats() {
		return businessSeats;
	}

	/**
	 * Sets the number of available seats in business class
	 * @param businessSeats - the number of available seats in business class
	 */
	public void setBusinessSeats(int businessSeats) {
		this.businessSeats = businessSeats;
	}

	/**
	 * Returns the price of business class ticket
	 * @return businessPrice - the price of business class ticket
	 */
	public int getBusinessPrice() {
		return businessPrice;
	}

	/**
	 * Sets the price of business class ticket
	 * @param businessPrice - the price of business class ticket
	 */
	public void setBusinessPrice(int businessPrice) {
		this.businessPrice = businessPrice;
	}
}