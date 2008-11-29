package springapp.web;

import hibernate.*;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import hibernate.Customer;
import hibernate.manager.CustomerManager;

public class RegisterValidator implements Validator {

   @SuppressWarnings("unchecked")
   public boolean supports(Class aClass) {
      return aClass.equals(Customer.class);
   }

   public void validate(Object o, Errors errors) {
      Customer customer = (Customer) o;

      final String userName = customer.getUsername();
      if (null == userName || userName.length() == 0)
         errors.rejectValue("username", "error.invalid.username", "User Name is blank");

      final String firstName = customer.getFirstName();
      if (null == firstName || firstName.length() == 0)
         errors.rejectValue("firstName", "error.invalid.firstName", "First Name is blank");

      final String lastName = customer.getLastName();
      if (null == lastName || lastName.length() == 0)
         errors.rejectValue("lastName", "error.invalid.lastName", "LastName is blank");

      final String street = customer.getStreet();
      if (null == street || street.length() == 0)
         errors.rejectValue("street", "error.invalid.street", "Street Name is blank");

      final String city = customer.getCity();
      if (null == city || city.length() == 0)
         errors.rejectValue("city", "error.invalid.city", "City is blank");

      final String state = customer.getState();
      if (null == state || state.length() == 0)
         errors.rejectValue("state", "error.invalid.state", "State is blank");

      final String password = customer.getPassword();
      if (null == password || password.toString().length() < 6)
         errors.rejectValue("password", "error.invalid.password",
               "Password must be at least 6 characters");

      final boolean bAlreadyExist = (null != CustomerManager.getCustomer(userName));
      if (bAlreadyExist)
         errors.rejectValue("username", "error.redundant.username", "User name is already exist.");

      final Long cc = customer.getCcNo();
      final Integer exp = customer.getExpiration();
      if (null != cc && null != exp) {
         final Itinerary it = new Itinerary();
         it.setCustomer(customer);
         final CreditCardValidator v = new CreditCardValidator();
         v.validate(it, errors);
      }
   }
}
