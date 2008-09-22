package database.hibernate;

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
	 * The schedule ID
	 */
	private String scheduleID;
	
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
	 * Returns the schedule ID
	 * @return scheduleID - the schedule ID
	 */
	public String getScheduleID() {
		return scheduleID;
	}

	/**
	 * Sets the schedule ID
	 * @param scheduleID - the schedule ID
	 */
	public void setScheduleID(String scheduleID) {
		this.scheduleID = scheduleID;
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