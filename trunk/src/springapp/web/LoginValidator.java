package springapp.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import hibernate.Customer;
import hibernate.manager.*;

@SuppressWarnings("unchecked")
public class LoginValidator implements Validator {
	
   public boolean supports(Class aClass)
	{
		return aClass.equals(Customer.class);
	}

	public void validate(Object o, Errors errors)
	{
	   /*
	    * If the credit card number is invalid,
	    * a web page with an error message will be displayed to the user
	    * and ask the user to re-enter the credit card information.
	    */
		Customer customer = (Customer)o;
		
		String username = customer.getUsername();
		String password = customer.getPassword();
		
		boolean test = CustomerManager.login(username, password);
		
		// well, let's do nothing with the bean for now and return
        //return super.onSubmit(command);
		if(test == false)
		{
			errors.rejectValue("username", "error.invalid.username", "User Name or");
			errors.rejectValue("password", "error.invalid.password", "Password is invalid");
		}
			
	}

}
