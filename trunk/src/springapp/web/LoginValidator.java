package springapp.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import hibernate.Customer;
import hibernate.manager.*;

/**
 * A Spring frame work validator for login page.
 */
@SuppressWarnings("unchecked")
public class LoginValidator implements Validator {
	
   /**
    * Supporting classes for this validator
    * @param aClass the class that will be tested
    * @return whether the class is supported by this validator
    * @see org.springframework.validation.Validator#supports(java.lang.Class)
    */
   public boolean supports(Class aClass)
	{
		return aClass.equals(Customer.class);
	}

	/**
	 * Validating for login page
	 * @param o command object
	 * @param errors a Spring Framework error object
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
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
