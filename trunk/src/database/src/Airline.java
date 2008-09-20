public class Airline {
	/**
	 * The two-letter airline identification code
	 */
	private String code;
	
	/**
	 * The airline name
	 */
	private String name;
	
	/**
	 * Default constructor
	 */
	public Airline() {}
	
	/**
	 * Sets the airline code
	 * @param code airline code
	 */
	public void setAirlineCode(String code) {
		this.code = code;
	}
	
	/**
	 * Returns the airline code
	 * @return code - airline code
	 */
	public String getAirlineCode() {
		return code;
	}
	
	/**
	 * Sets the airline name
	 * @param name airline name
	 */
	public void setAirlineName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the airline name
	 * @return name - airline name
	 */
	public String getAirlineName() {
		return name;
	}
}