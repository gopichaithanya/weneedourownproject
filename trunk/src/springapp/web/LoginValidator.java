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
		Customer customer = (Customer)o;
		
		String username = customer.getUsername();
		String password = customer.getPassword();
		boolean test = CustomerManager.login(username, password);
		
		// well, let's do nothing with the bean for now and return
        //return super.onSubmit(command);
		if(test == false)
		{
			errors.rejectValue("username", "error.invalid.username", "User Name is invalid");
			errors.rejectValue("password", "error.invalid.password", "Pass is invalid");
		}
			
	}

}
