import java.util.Date;

public class User {
	
	/**
	 * The user's first name
	 */
	private String firstName;
	
	/**
	 * The user's last name
	 */
	private String lastName;
	
	/**
	 * The user's street address
	 */
	private String street;
	
	/**
	 * The user's city of residence
	 */
	private String city;
	
	/**
	 * The user's state of residence
	 */
	private String state;
	
	/**
	 * The user's zip code
	 */
	private int zip;
	
	/**
	 * The user's username for logging in
	 */
	private String username;
	
	/**
	 * The user's password for logging in
	 */
	private String password;
	
	/**
	 * The user's credit card number
	 */
	private int creditCardNumber;
	
	/**
	 * The user's credit card exipration date
	 */
	private Date expirationDate;
	/**
	 * Default constructor
	 */
	public User() {}
	
	/**
	 * Sets the user's first name
	 * @param firstName - the user's first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Returns the user's first name
	 * @return firstName - the user's first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the user's last name
	 * @param lastName - the user's last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Returns the user's last name
	 * @return lastName - the user's last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets the user's street address
	 * @param street - the user's street address
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	/**
	 * Returns the user's street address
	 * @return street - the user's street address
	 */
	public String getStreet() {
		return street;
	}
	
	/**
	 * Sets the user's city of residence
	 * @param city - the user's city of residence
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * Returns the user's city of residence
	 * @return city - the user's city of residence
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * Sets the user's state of residence
	 * @param state - the user's state of residence
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Returns the user's state of residence
	 * @return state - the user's state of residence
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Sets the user's zip code
	 * @param zip - the user's zip code
	 */
	public void setZip(int zip) {
		this.zip = zip;
	}
	
	/**
	 * Returns the user's zip code
	 * @return zip - the user's zip code
	 */
	public int getZip() {
		return zip;
	}
	
	/**
	 * Sets the user's username for login
	 * @param username - the user's username for login
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Returns the user's username login
	 * @return username - the user's username for login
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the user's password for login
	 * @param password - the user's password for login
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Returns the user's password for login
	 * @return password - the user's password for login
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the user's credit card number
	 * @param creditCardNumber - the user's credit card number
	 */
	public void setCreditCardNumber(int creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	
	/**
	 * Returns the user's credit card number
	 * @return creditCardNumber - the user's credit card number
	 */
	public int getCreditCardNumber() {
		return creditCardNumber;
	}
	
	/**
	 * Sets the user's credit card expiration date
	 * @param expirationDate - the credit card expiration date
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	/**
	 * Returns the user's credit card expiration date
	 * @return expirationDate - the credit card expiration date
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}
}