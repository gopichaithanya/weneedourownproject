package database.hibernate;

public class Itinerary {

	/**
	 * The flight number
	 */
	private int flightNumber;
	
	/**
	 * The user to whom the itinerary belongs to
	 */
	private String username;
	
	/**
	 * The status of the flight; reserved, booked or canceled
	 */
	private String status;
	/**
	 * Default constructor
	 */
	public Itinerary() {}
	
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
	 * Returns the username of the user associated with this itinerary
	 * @return username - the user associated with this itinerary
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the username of the user associated with this itinerary
	 * @param username - the user associated with this itinerary
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Returns the status of the flight
	 * @return status - the status of the flight
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the status of the flight
	 * @param status - the status of the flight
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}