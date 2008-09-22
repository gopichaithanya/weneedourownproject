package database.hibernate;

public class Schedule {

	/**
	 * The schedule ID
	 */
	private String scheduleID;
	
	/**
	 * The departure location
	 */
	private String departureLocation;
	
	/**
	 * The day of departure
	 */
	private String departureDay;
	
	/**
	 * The time of departure
	 */
	private String departureTime;
	
	/**
	 * The arrival location
	 */
	private String arrivalLocation;
	
	/**
	 * The day of arrival
	 */
	private String arrivalDay;
	
	/**
	 * The time of arrival
	 */
	private String arrivalTime;
	
	/**
	 * Default constructor
	 */
	public Schedule() {}

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
	 * Returns the day of departure
	 * @return departureDay - the day of departure
	 */
	public String getDepartureDay() {
		return departureDay;
	}

	/**
	 * Sets the day of departure
	 * @param departureDay - the day of departure
	 */
	public void setDepartureDay(String departureDay) {
		this.departureDay = departureDay;
	}

	/**
	 * Returns the time of departure
	 * @return departureTime - the time of departure
	 */
	public String getDepartureTime() {
		return departureTime;
	}

	/**
	 * Sets the time of departure
	 * @param departureTime - the time of departure
	 */
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
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
	 * Returns the day of arrival
	 * @return arrivalDay - the day of arrival
	 */
	public String getArrivalDay() {
		return arrivalDay;
	}

	/**
	 * Sets the day of arrival
	 * @param arrivalDay - the day of arrival
	 */
	public void setArrivalDay(String arrivalDay) {
		this.arrivalDay = arrivalDay;
	}

	/**
	 * Returns the time of arrival
	 * @return arrivalTime - the time of arrival
	 */
	public String getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Sets the time of arrival
	 * @param arrivalTime - the time of arrival
	 */
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	
}