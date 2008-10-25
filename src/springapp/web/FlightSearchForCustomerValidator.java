package springapp.web;

import hibernate.manager.AirportManager;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@SuppressWarnings("unchecked")
public class FlightSearchForCustomerValidator implements Validator {

   public boolean supports(Class clazz) {
      return springapp.web.FlightSearchForCustomer.class.equals(clazz);
   }

   public void validate(Object obj, Errors errors) {
      final FlightSearchForCustomer o = (FlightSearchForCustomer) obj;
      final List airportCodes = AirportManager.getAirportCode();
      if (null == o || !airportCodes.contains(o.getDepartLocation()))
         errors.rejectValue("departLocation", "error.FlightSearchForCustomer.departureLocation",
               "Invalid value.");

      if (null == o || !airportCodes.contains(o.getArrivalLocation()))
         errors.rejectValue("arrivalLocation", "error.FlightSearchForCustomer.arrivalLocation",
               "Invalid value.");

      // TODO
   }

}
