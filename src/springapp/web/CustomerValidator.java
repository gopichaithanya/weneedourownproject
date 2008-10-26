package springapp.web;

import hibernate.*;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.lang.*;
import hibernate.Customer;


public class CustomerValidator  implements Validator
{
	private String username;
	private String firstName;
	private String lastName;
	private String street;
	private String city;
	private String state;
	private Integer zip;
	private String password;
	private Integer ccNo;
	private String expiration;
	public boolean supports(Class aClass)
	{
		return aClass.equals(Customer.class);
	}

	public void validate(Object o, Errors errors)
	{
		Customer customer = (Customer)o;
		
		
		if (customer.getUsername().length()  == 0)
			errors.rejectValue("username", "error.invalid.username", "User Name is blank");
		if (customer.getFirstName().length()  == 0)
			errors.rejectValue("firstName", "error.invalid.firstName", "First Name is blank");
		if (customer.getLastName().length()  == 0)
			errors.rejectValue("lastName", "error.invalid.lastName", "LastName is blank");
		if (customer.getStreet() == null)
			errors.rejectValue("street", "error.invalid.street", "Street Name is blank");
		if (customer.getCity().length()  == 0)
			errors.rejectValue("city", "error.invalid.city", "City is blank");
		if (customer.getState().length()  == 0)
			errors.rejectValue("state", "error.invalid.state", "State is blank");
		if (customer.getZip().toString().length() != 5)
			errors.rejectValue("apr", "error.invalid.apr", "Zip is not ");
		if (customer.getCcNo().toString().length()  != 16)
			errors.rejectValue("ccNo", "error.invalid.ccNo", "Credit Card Number is not correct");
		if (customer.getPassword().toString().length() < 6)
			errors.rejectValue("password", "error.invalid.password", "Password must be at least 6 characters");
		if (customer.getExpiration().length()  != 4)
			errors.rejectValue("expiration", "error.invalid.expiration", "State is blank");
	}
}
