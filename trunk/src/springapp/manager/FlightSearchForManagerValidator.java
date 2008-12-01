package springapp.manager;

import java.util.List;

import hibernate.manager.AirlineManager;
import hibernate.manager.AirportManager;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * A Spring Framework Validator for flight manager search command object
 */
public class FlightSearchForManagerValidator implements Validator {

   /**
    * indicates whether a class is supported for this validator or not.
    * @see org.springframework.validation.Validator#supports(java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   public boolean supports(Class aClass) {
      return FlightSearchForManagerCommand.class == aClass;
   }

   /**
    * validate the command object
    * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
    */
   public void validate(Object cmdObj, Errors e) {
      final FlightSearchForManagerCommand cmd = (FlightSearchForManagerCommand) cmdObj;

      if (null == cmd) {
         e.reject("nullpointer", "Command object is null.");
         return;
      }

      final Integer flightNo = cmd.getFlightNo();
      if (null != flightNo) {
         if (flightNo < 0)
            e.rejectValue("flightNo", "flightSearchForManager.flightNo.negative",
                  "Flight number is negative. It should be positive 3digit numbers.");
         if (flightNo >= 1000)
            e.rejectValue("flightNo", "flightSearchForManager.flightNo.tooBig",
                  "Flight number is too big. It should be positive 3digit numbers.");
      }

      final List<String> airlines = AirlineManager.getAirlineCode();
      final String airline = cmd.getAirline();
      if (null != airline && false == airline.equals("")) {
         if (false == airlines.contains(airline))
            e.rejectValue("airline", "flightSearchForManager.airline.notExist", "The airline, "
                  + airline + ", does not exist.");
      }

      final List<String> airports = AirportManager.getAirportCode();
      final String departLoc = cmd.getDepartLocation();
      if (null != departLoc && false == departLoc.equals("")) {
         if (false == airports.contains(departLoc))
            e.rejectValue("departLocation", "flightSearchForManager.departLocation.notExist",
                  "The airport code, " + departLoc + ", does not exist.");
      }
      final String arrivalLoc = cmd.getArrivalLocation();
      if (null != arrivalLoc && false == arrivalLoc.equals("")) {
         if (false == airports.contains(arrivalLoc))
            e.rejectValue("arrivalLocation", "flightSearchForManager.arrivalLocation.notExist",
                  "The airport code, " + arrivalLoc + ", does not exist.");
      }
   }
}
