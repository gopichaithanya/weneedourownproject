package springapp.manager;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AddFlightValidator implements Validator {

   @SuppressWarnings("unchecked")
   public boolean supports(Class aClass) {
      return aClass.equals(FlightAddForManager.class);
   }

   public void validate(Object cmd, Errors err) {
   }

}
