package springapp.manager;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FlightSearchForManagerValidator implements Validator {

   public boolean supports(Class aClass) {
      return aClass == FlightSearchForManagerCommand.class;
   }

   public void validate(Object arg0, Errors arg1) {
      // TODO Auto-generated method stub
      
   }

}
