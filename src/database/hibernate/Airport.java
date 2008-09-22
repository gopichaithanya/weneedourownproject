package database.hibernate;
import java.util.Date;

public class Airport {
	
	/**
	 * Three-letter airport code
	 */
	private String code;
	
	/**
	 * The airport name
	 */
	private String name;
	
	/**
	 * The city which the airport is in
	 */
	private String city;
	
	/**
	 * The county which the airport is in
	 */
	private String county;
	
	/**
	 * The state which the airport is in
	 */
	private String state;
	
	/**
	 * The first longitude value 
	 */
	private String longitude1;
	
	/**
	 * The second longitude value
	 */
	private String longitude2;
	
	/**
	 * The first latitude value
	 */
	private String latitude1;
	
	/**
	 * The second latitude value
	 */
	private String latitude2;
	
	/**
	 * The airport elevation value
	 */
	private int elevation;
	
	/**
	 * The airport telephone number
	 */
	private String telephone;
	
	/**
	 * The date the airport certification was activated
	 */
	private Date activationDate;
	
	/**
	 * Default constructor
	 */
	public Airport(){}
	
	/**
	 * Sets the airport code
	 * @param code - the airport code
	 */
	public void setAirportCode(String code) {
		this.code = code;
	}
	
	/**
	 * Returns the airport code
	 * @return code - the airport code
	 */
	public String getAirlineCode() {
		return code;
	}
	
	/**
	 * Sets the airport name
	 * @param name - the airport name
	 */
	public void setAirportName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the airport name
	 * @return name - the airport name
	 */
	public String getAirlineName() {
		return name;
	}
	
	/**
	 * Sets the airport city
	 * @param city - the airport city
	 */
	public void setAirportCity(String city) {
		this.city = city;
	}
	
	/**
	 * Returns the airport city
	 * @return city - the airport city
	 */
	public String getAirlineCity() {
		return city;
	}
	
	/**
	 * Sets the airport county
	 * @param county - the airport county
	 */
	public void setAirportCounty(String county) {
		this.county = county;
	}
	
	/**
	 * Returns the airport county
	 * @return county - the airport county
	 */
	public String getAirlineCounty() {
		return county;
	}
	
	/**
	 * Sets the airport state
	 * @param state - the airport state
	 */
	public void setAirportState(String state) {
		this.state = state;
	}
	
	/**
	 * Returns the airport state
	 * @return state - the airport state
	 */
	public String getAirportState() {
		return state;
	}
	
	/**
	 * Sets the airport's first longitude value
	 * @param longitude - the airport longitude
	 */
	public void setAirportLongitude1(String longitude) {
		this.longitude1 = longitude;
	}
	
	/**
	 * Returns the airport's first longitude value
	 * @param longitude1 - the airport longitude
	 */
	public String getAirportLongitude1() {
		return longitude1;
	}
	
	/**
	 * Sets the airport's second longitude value
	 * @param longitude - the airport longitude
	 */
	public void setAirportLongitude2(String longitude) {
		this.longitude2 = longitude;
	}
	
	/**
	 * Returns the airport's second longitude value
	 * @param longitude2 - the airport longitude
	 */
	public String getAirportLongitude2() {
		return longitude2;
	}
	
	/**
	 * Sets the airport's first latitude value
	 * @param latitude - the airport latitude
	 */
	public void setAirportLatitude1(String latitude) {
		this.latitude1 = latitude;
	}
	
	/**
	 * Returns the airport's first latitude value
	 * @return latitude1 - the airport latitude
	 */
	public String getAirportLatitude1() {
		return latitude1;
	}
	
	/**
	 * Sets the airport's second latitude value
	 * @param latitude - the airport latitude
	 */
	public void setAirportLatitude2(String latitude) {
		this.latitude2 = latitude;
	}
	
	/**
	 * Returns the airport's second latitude value
	 * @return latitude2 - the airport latitude
	 */
	public String getAirportLatitude2() {
		return latitude2;
	}
	
	/**
	 * Sets the airport elevation
	 * @param elevation - the airport elevation
	 */
	public void setAirportElevation(int elevation) {
		this.elevation = elevation;
	}
	
	/**
	 * Returns the airport elevation
	 * @return elevation - the airport elevation
	 */
	public int getAirportElevation() {
		return elevation;
	}
	
	/**
	 * Sets the airport telephone number
	 * @param telephone - the airport telephone number
	 */
	public void setAirportTelephoneNumber(String telephone) {
		this.telephone = telephone;
	}
	
	/**
	 * Returns the airport telephone number
	 * @return telephone - the airport telephone number
	 */
	public String getAirportTelephoneNumber() {
		return telephone;
	}
	
	/**
	 * Sets the airport activationDate
	 * @param activationDate - the airport activation date
	 */
	public void setAirportActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}
	
	/**
	 * Returns the airport activationDate
	 * @return activationDate - the airport activation date
	 */
	public Date getAirportActivationDate() {
		return activationDate;
	}
}