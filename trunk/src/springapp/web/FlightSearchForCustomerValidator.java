package springapp.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@SuppressWarnings("unchecked")
public class FlightSearchForCustomerValidator implements Validator {

   public boolean supports(Class clazz) {
      return springapp.web.FlightSearchForCustomer.class.equals(clazz);
   }

   public void validate(Object obj, Errors errors) {
      // TODO Auto-generated method stub
      
   }

}
