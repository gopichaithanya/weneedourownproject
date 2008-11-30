package springapp.manager;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FlightSearchForManagerValidator implements Validator {

   @SuppressWarnings("unchecked")
   public boolean supports(Class aClass) {
      return aClass == FlightSearchForManagerCommand.class;
   }

   public void validate(Object cmdObj, Errors e) {
      // TODO Auto-generated method stub
      
   }

}
