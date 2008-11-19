package springapp.web;

import hibernate.manager.AirportManager;

import java.util.Calendar;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * A validator for flight search. This validator is from Spring framework.
 * This validator checkes whether or not the command object is valid.
 * @see FlightSearchForCustomer
 */
@SuppressWarnings("unchecked")
public class FlightSearchForCustomerValidator implements Validator {

   public static final int MAX_YEAR = 10;
   /**
    * Currently flight number does not seems 3 digits.
    * But the description our professor gave us said it should be 3 digits.
    * If this value is false, then this validator does not check the number of digits of flightNo.
    * If this value is true, then this validator checks the number of digits of flightNo. 
    */
   static boolean bFlagFlightNoIsForcedToBe3Digits = false;

   public boolean supports(Class clazz) {
      return springapp.web.FlightSearchForCustomer.class.equals(clazz);
   }

   public void validate(Object obj, Errors errors) {
      final FlightSearchForCustomer o = (FlightSearchForCustomer) obj;

      // Checking: departYear
      if (null == o) {
         errors.rejectValue("departLocation",
               "error.FlightSearchForCustomer.departureLocation.not-specified",
               "Departing airport code is not specified.");
         errors.rejectValue("arrivalLocation",
               "error.FlightSearchForCustomer.arrivalLocation.not-specified",
               "Arrival airport code is not specified.");

         errors.rejectValue("departYear", "error.FlightSearchForCustomer.departYear.not-specified",
               "Departing year value is not specified.");
         errors.rejectValue("departMonth",
               "error.FlightSearchForCustomer.departMonth.not-specified",
               "Departing month value is not specified.");
         errors.rejectValue("departDay", "error.FlightSearchForCustomer.departDay.not-specified",
               "Departing day value is not specified.");

         errors.rejectValue("returnYear", "error.FlightSearchForCustomer.returnYear.not-specified",
               "Returning year value is not specified.");
         errors.rejectValue("returnMonth",
               "error.FlightSearchForCustomer.returnMonth.not-specified",
               "Returning month value is not specified.");
         errors.rejectValue("returnDay", "error.FlightSearchForCustomer.returnDay.not-specified",
               "Returning day value is not specified.");

         errors.rejectValue("tripType", "error.FlightSearchForCustomer.tripType.not-specified",
               "Trip type is not specified. It can be either "
                     + FlightSearchForCustomer.ETripType.ONEWAY_TRIP.name() + " or "
                     + FlightSearchForCustomer.ETripType.ROUND_TRIP.name() + ".");
         errors.rejectValue("numPassengers",
               "error.FlightSearchForCustomer.numPassengers.not-specified",
               "The number of passengers is not specified.");
         return;
      }

      final List airportCodes = AirportManager.getAirportCode();

      // Checking: departLocation
      if (!airportCodes.contains(o.getDepartLocation()))
         errors.rejectValue("departLocation",
               "error.FlightSearchForCustomer.departureLocation.notExist", "Airport code, "
                     + o.getDepartLocation() + ", does not exist.");

      // Checking: arrivalLocation
      if (!airportCodes.contains(o.getArrivalLocation()))
         errors.rejectValue("arrivalLocation",
               "error.FlightSearchForCustomer.arrivalLocation.notExist", "Airport code, "
                     + o.getArrivalLocation() + ", does not exist.");

      // Checking: tripType
      final boolean bOneWayTrip = o.isOneWayTrip();
      final boolean bRoundTrip = o.isRoundTrip();
      if (!bOneWayTrip && !bRoundTrip)
         errors.rejectValue("tripType", "error.FlightSearchForCustomer.tripType.outOfBound",
               "Trip type must be either " + FlightSearchForCustomer.ETripType.ROUND_TRIP.name()
                     + " or " + FlightSearchForCustomer.ETripType.ONEWAY_TRIP.name() + ".");

      // Checking: numPassengers
      try {
         final int numPassengers = Integer.valueOf(o.getNumPassengers());
         if (numPassengers < 1)
            errors.rejectValue("numPassengers",
                  "error.FlightSearchForCustomer.numPassengers.tooLess",
                  "The number of passengers is too less.");
      } catch (NumberFormatException e) {
         errors.rejectValue("numPassengers",
               "error.FlightSearchForCustomer.numPassengers.notInteger",
               "The number of passengers is not integer.");
      }

      final Calendar calendar = Calendar.getInstance();
      final int curMonth = calendar.get(Calendar.MONTH) + 1; // 0-based.
      final int curYear = calendar.get(Calendar.YEAR);
      final int curDay = calendar.get(Calendar.DAY_OF_MONTH);

      // Checking: departYear
      int dYear;
      try {
         dYear = Integer.valueOf(o.getDepartYear());
      } catch (NumberFormatException e) {
         errors.rejectValue("departYear", "error.FlightSearchForCustomer.departYear.notInteger",
               "Departing year value is not integer.");
         return;
      }
      if (dYear < curYear)
         errors.rejectValue("departYear", "error.FlightSearchForCustomer.departYear.tooLess",
               "Departing year is already past.");
      else if (dYear >= curYear + MAX_YEAR)
         errors.rejectValue("departYear", "error.FlightSearchForCustomer.departYear.tooMuch",
               "Departing year is too much future.");

      // Checking: departMonth
      int dMonth;
      try {
         dMonth = Integer.valueOf(o.getDepartMonth());
      } catch (NumberFormatException e) {
         errors.rejectValue("departMonth", "error.FlightSearchForCustomer.departMonth.notInteger",
               "Departing month value is not integer.");
         return;
      }
      final boolean bSameDepartYear = (curYear == dYear);
      if (dMonth < 1)
         errors.rejectValue("departMonth", "error.FlightSearchForCustomer.departMonth.tooLess",
               "Departing month is too less.");
      else if (dMonth > 12)
         errors.rejectValue("departMonth", "error.FlightSearchForCustomer.departMonth.tooMuch",
               "Departing month is too much.");
      else if (bSameDepartYear && dMonth < curMonth)
         errors.rejectValue("departMonth", "error.FlightSearchForCustomer.departMonth.pastMonth",
               "Already past month.");

      // Checking: departDay
      int dDay;
      try {
         dDay = Integer.valueOf(o.getDepartDay());
      } catch (NumberFormatException e) {
         errors.rejectValue("departDay", "error.FlightSearchForCustomer.departDay.notInteger",
               "Departing day value is not integer.");
         return;
      }
      {
         final boolean bSameDepartMonth = (dMonth == curMonth);
         final Calendar c = Calendar.getInstance();
         c.set(dYear, dMonth - 1, 1);
         final int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
         if (dDay < 1)
            errors.rejectValue("departDay", "error.FlightSearchForCustomer.departDay.tooLess",
                  "Departing day is too less.");
         else if (dDay > maxDay)
            errors.rejectValue("departDay", "error.FlightSearchForCustomer.departDay.tooMuch",
                  "Departing day is too much.");
         else if (bSameDepartYear && bSameDepartMonth && dDay < curDay)
            errors.rejectValue("departDay", "error.FlightSearchForCustomer.departDay.pastDay",
                  "Already past day.");
      }

      // Checking: departFlightNo
      if (bFlagFlightNoIsForcedToBe3Digits && false == o.isValidDepartFlightNo())
         errors.rejectValue("departFlightNo",
               "error.FlightSearchForCustomer.departFlightNo.not-specified",
               "Flight number of departing flight should be 3 digits.");

      if (false == bRoundTrip)
         return;

      // Checking: returnYear
      int rYear;
      try {
         rYear = Integer.valueOf(o.getReturnYear());
      } catch (NumberFormatException e) {
         errors.rejectValue("returnYear", "error.FlightSearchForCustomer.returnYear.notInteger",
               "Returning year value is not integer.");
         return;
      }
      if (rYear < dYear)
         errors.rejectValue("returnYear",
               "error.FlightSearchForCustomer.returnYear.earlierThanDepart",
               "Returning year should be later than departing year.");
      else if (rYear >= curYear + MAX_YEAR)
         errors.rejectValue("returnYear", "error.FlightSearchForCustomer.returnYear.tooMuch",
               "Returning year is too much future.");

      // Checking: returnMonth
      int rMonth;
      try {
         rMonth = Integer.valueOf(o.getReturnMonth());
      } catch (NumberFormatException e) {
         errors.rejectValue("returnMonth", "error.FlightSearchForCustomer.returnMonth.notInteger",
               "Returning month value is not integer.");
         return;
      }
      final boolean bSameReturnYear = (dYear == rYear);
      if (rMonth < 1)
         errors.rejectValue("returnMonth", "error.FlightSearchForCustomer.returnMonth.tooLess",
               "Returning month is too less.");
      else if (rMonth > 12)
         errors.rejectValue("returnMonth", "error.FlightSearchForCustomer.returnMonth.tooMuch",
               "Returning month is too much.");
      else if (bSameReturnYear && rMonth < dMonth)
         errors.rejectValue("returnMonth",
               "error.FlightSearchForCustomer.returnMonth.earlierThanDepart",
               "Returning month should be later than departing month.");

      // Checking: returnDay
      int rDay;
      try {
         rDay = Integer.valueOf(o.getReturnDay());
      } catch (NumberFormatException e) {
         errors.rejectValue("returnDay", "error.FlightSearchForCustomer.returnDay.notInteger",
               "Returning day value is not integer.");
         return;
      }
      {
         final boolean bSameReturnMonth = (rMonth == dMonth);
         final Calendar c = Calendar.getInstance();
         c.set(rYear, rMonth - 1, 1);
         final int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
         if (rDay < 1)
            errors.rejectValue("returnDay", "error.FlightSearchForCustomer.returnDay.tooLess",
                  "Returning day is too less.");
         else if (rDay > maxDay)
            errors.rejectValue("returnDay", "error.FlightSearchForCustomer.returnDay.tooMuch",
                  "Returning day is too much.");
         else if (bSameReturnYear && bSameReturnMonth && rDay < dDay)
            errors.rejectValue("returnDay",
                  "error.FlightSearchForCustomer.returnDay.earlierThanDepart",
                  "Returning day should be later than departing day.");
      }

      // Checking: returnFlightNo
      if (bFlagFlightNoIsForcedToBe3Digits && false == o.isValidReturnFlightNo())
         errors.rejectValue("returnFlightNo",
               "error.FlightSearchForCustomer.returnFlightNo.not-specified",
               "Flight number of returning flight should be 3 digits.");
   }
}
