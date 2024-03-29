package hibernate;
// Generated Oct 15, 2008 10:13:30 PM by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Airport generated by hbm2java
 */
@SuppressWarnings("serial")
public class Airport  implements java.io.Serializable {

	/**
	 * The airport code
	 */
	private String code;
	
	/**
	 * The airport name
	 */
    private String name;
    
    /**
     * The airport city
     */
    private String city;
    
    /**
     * The airport county
     */
    private String county;
    
    /**
     * The airport state
     */
    private String state;
    
    /**
     * The first latitude value of the airport location
     */
    private String latitude1;
    
    /**
     * The second latitude value of the airport location
     */
    private String latitude2;
    
    /**
     * The first longitude value of the airport location
     */
    private String longitude1;
    
    /**
     * The second longitude value of the airport location
     */
    private String longitude2;
    
    /**
     * The airport elevation
     */
    private String elevation;
    
    /**
     * The airport telephone number
     */
    private String telephone;
    
    /**
     * The list of flights that depart from the specified departure location
     */
    private Set<Flight> flightsForDepartureLocation = new HashSet<Flight>(0);
    
    /**
     * The list of flights that arrive at a specified arrival location
     */
    private Set<Flight> flightsForArrivalLocation = new HashSet<Flight>(0);

    /**
     * Constructs a new airport object
     */
    public Airport() {
    }

	/**
	 * Constructs a new airport object with the give airport code
	 * @param code - the airport code
	 */
    public Airport(String code) {
        this.code = code;
    }
    
    /**
     * Constructs a new airport object with the give parameters
     * @param code - the airport code
     * @param name - the airport name
     * @param city - the airport city
     * @param county - the airport county
     * @param state - the airport state
     * @param latitude1 - the first latitude value of the airport location
     * @param latitude2 - the second latitude value of the airport location
     * @param longitude1 - the first longitude value of the airport location
     * @param longitude2 - the second longitude value of the airport location
     * @param elevation - the airport elevation
     * @param telephone - the airport telephone number
     * @param flightsForDepartureLocation - set of flights for given departure location
     * @param flightsForArrivalLocation - set of flights for given arrival location
     */
    public Airport(String code, String name, String city, String county, String state, String latitude1, String latitude2, String longitude1, String longitude2, String elevation, String telephone, Set<Flight> flightsForDepartureLocation, Set<Flight> flightsForArrivalLocation) {
       this.code = code;
       this.name = name;
       this.city = city;
       this.county = county;
       this.state = state;
       this.latitude1 = latitude1;
       this.latitude2 = latitude2;
       this.longitude1 = longitude1;
       this.longitude2 = longitude2;
       this.elevation = elevation;
       this.telephone = telephone;
       this.flightsForDepartureLocation = flightsForDepartureLocation;
       this.flightsForArrivalLocation = flightsForArrivalLocation;
    }
   
    /**
     * Returns the airport code
     * @return the airport code
     */
    public String getCode() {
        return this.code;
    }
    
    /**
     * Sets the airport code
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * Returns the airport name
     * @return the airport name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Sets the airport name
     * @param name - the airport name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns the city in which the airport is located
     * @return - the airport city 
     */
    public String getCity() {
        return this.city;
    }
    
    /**
     * Sets the city in which the airport is located
     * @param city - the airport city
     */
    public void setCity(String city) {
        this.city = city;
    }
    
    /**
     * Returns the county in which the airport is located
     * @return the airport county
     */
    public String getCounty() {
        return this.county;
    }
    
    /**
     * Sets the county in which the airport is located
     * @param county - the airport county
     */
    public void setCounty(String county) {
        this.county = county;
    }
    
    /**
     * Returns the state in which the airport is located
     * @return the airport state
     */
    public String getState() {
        return this.state;
    }
    
    /**
     * Sets the state in which the airport is located
     * @param state - the airport state
     */
    public void setState(String state) {
        this.state = state;
    }
    
    /**
     * Return the first latitude value of the airport location
     * @return the first latitude value
     */
    public String getLatitude1() {
        return this.latitude1;
    }
    
    /**
     * Sets the first latitude value of the airport location
     * @param latitude1 - the first latitude value
     */
    public void setLatitude1(String latitude1) {
        this.latitude1 = latitude1;
    }
    
    /**
     * Return the second latitude value of the airport location
     * @return the second latitude value
     */
    public String getLatitude2() {
        return this.latitude2;
    }
    
    /**
     * Sets the second latitude value of airport location
     * @param latitude2 - the second latitude value
     */
    public void setLatitude2(String latitude2) {
        this.latitude2 = latitude2;
    }
    
    /**
     * Returns the first longitude value of the airport location
     * @return the first longitude value
     */
    public String getLongitude1() {
        return this.longitude1;
    }
    
    /**
     * Sets the first longitude value of the airport location
     * @param longitude1 - the first longitude value
     */
    public void setLongitude1(String longitude1) {
        this.longitude1 = longitude1;
    }

    /**
     * Returns the second longitude value of the airport location
     * @return the second longitude value
     */
    public String getLongitude2() {
        return this.longitude2;
    }
    
    /**
     * Sets the second longitude value of the airport location
     * @param longitude2 - the second longitude value
     */
    public void setLongitude2(String longitude2) {
        this.longitude2 = longitude2;
    }
    
    /**
     * Returns the airport's elevation
     * @return the airport elevation
     */
    public String getElevation() {
        return this.elevation;
    }
    
    /**
     * Sets the airport's elevation
     * @param elevation - the airport elevation
     */
    public void setElevation(String elevation) {
        this.elevation = elevation;
    }
    
    /**
     * Returns the airport telephone number
     * @return the airport telephone number
     */
    public String getTelephone() {
        return this.telephone;
    }
    
    /**
     * Sets the airport telephone number
     * @param telephone - the airport telephone number
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    /**
     * Returns the list of flights that depart from a specified departure location
     * @return the list of flights
     */
    public Set<Flight> getFlightsForDepartureLocation() {
        return this.flightsForDepartureLocation;
    }
    
    /**
     * Sets the list of flights that depart from a specified departure location
     * @param flightsForDepartureLocation - the list of flights
     */
    public void setFlightsForDepartureLocation(Set<Flight> flightsForDepartureLocation) {
        this.flightsForDepartureLocation = flightsForDepartureLocation;
    }
    
    /**
     * Returns the list of flights that arrive at a specified arrival location
     * @return the list of flights
     */
    public Set<Flight> getFlightsForArrivalLocation() {
        return this.flightsForArrivalLocation;
    }
    
    /**
     * Sets the list of flights that arrive at a specified arrival location
     * @param flightsForArrivalLocation - the list of flights
     */
    public void setFlightsForArrivalLocation(Set<Flight> flightsForArrivalLocation) {
        this.flightsForArrivalLocation = flightsForArrivalLocation;
    }
}


