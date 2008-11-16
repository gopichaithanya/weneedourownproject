package springapp.web;

import hibernate.*;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.lang.*;
import hibernate.Customer;


public class CustomerValidator  implements Validator
{

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
		if (customer.getStreet().length()  == 0)
			errors.rejectValue("street", "error.invalid.street", "Street Name is blank");
		if (customer.getCity().length()  == 0)
			errors.rejectValue("city", "error.invalid.city", "City is blank");
		if (customer.getState().length()  == 0)
			errors.rejectValue("state", "error.invalid.state", "State is blank");
		if (customer.getPassword().toString().length() < 6)
			errors.rejectValue("password", "error.invalid.password", "Password must be at least 6 characters");
	}
}
