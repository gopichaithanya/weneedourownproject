package springapp.manager;

import hibernate.manager.AirlineManager;
import hibernate.manager.AirportManager;
import hibernate.manager.FlightManager;

import java.util.Calendar;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * A Spring Framework Validator for AddFlightController
 */
public class AddFlightValidator implements Validator {

   /**
    * The flight searching maximum years from today
    */
   public static final int MAX_YEAR = 10;

   /**
    * supporting classes for validation.
    * @see org.springframework.validation.Validator#supports(java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   public boolean supports(Class aClass) {
      return aClass.equals(AddFlightCommand.class);
   }

   /**
    * validate the command object that will be given by Spring Framework.
    * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
    */
   @SuppressWarnings("unused")
   public void validate(Object cmdObj, Errors err) {
      final AddFlightCommand cmd = (AddFlightCommand) cmdObj;
      if (null == cmd) {
         err.reject("error.addFlight.command.nullPointer",
               "Null value as command object is not allowed.");
         return;
      }

      // Checking: flight number
      final int flightNo = cmd.getFlightNo();
      if (flightNo < 0 || flightNo > 999)
         err.rejectValue("flightNo", "error.addFlight.flightNo.not3Digits",
               "Flight number is not 3 digits.");
      if (null != FlightManager.getFlight(flightNo))
         err.rejectValue("flightNo", "error.addFlight.flightNo.alreadyExist",
               "Flight number is already exist");

      final List<String> airlineCodes = AirlineManager.getAirlineCode();

      // Checking: airline code
      if (!airlineCodes.contains(cmd.getAirline()))
         err.rejectValue("airline", "error.addFlight.airline.notExist", "Airline code, "
               + cmd.getAirline() + ", does not exist.");

      final List<String> airportCodes = AirportManager.getAirportCode();

      // Checking: departLocation
      if (!airportCodes.contains(cmd.getAirportByDepartureLocation()))
         err.rejectValue("departLocation", "error.addFlight.departureLocation.notExist",
               "Airport code, " + cmd.getAirportByDepartureLocation() + ", does not exist.");

      // Checking: arrivalLocation
      if (!airportCodes.contains(cmd.getAirportByArrivalLocation()))
         err.rejectValue("arrivalLocation", "error.addFlight.arrivalLocation.notExist",
               "Airport code, " + cmd.getAirportByArrivalLocation() + ", does not exist.");
      if (cmd.getAirportByArrivalLocation().equals(cmd.getAirportByDepartureLocation()))
         err.rejectValue("arrivalLocation",
               "error.FlightSearchForCustomer.arrivalLocation.sameWithDeparture",
               "Destination is same with departure.");

      // Checking: economySeats
      try {
         final int ecoSeats = Integer.valueOf(cmd.getEconomySeats());
         if (ecoSeats < 1)
            err.rejectValue("economySeats", "error.addFlight.economySeats.tooLess",
                  "The number of economy seats is too less.");
      } catch (NumberFormatException e) {
         err.rejectValue("economySeats", "error.addFlight.economySeats.notInteger",
               "The number of economy seats is not integer.");
      }

      // Checking: businessSeats
      try {
         final int busiSeats = Integer.valueOf(cmd.getBusinessSeats());
         if (busiSeats < 1)
            err.rejectValue("businessSeats", "error.addFlight.businessSeats.tooLess",
                  "The number of business seats is too less.");
      } catch (NumberFormatException e) {
         err.rejectValue("businessSeats", "error.addFlight.businessSeats.notInteger",
               "The number of business seats is not integer.");
      }

      // Checking: price of economy
      try {
         final float ecoPrice = Float.valueOf(cmd.getEconomyPrice());
         if (ecoPrice < 1)
            err.rejectValue("economyPrice", "error.addFlight.economyPrice.tooLess",
                  "The price of economy seats is too less.");
      } catch (NumberFormatException e) {
         err.rejectValue("economyPrice", "error.addFlight.economyPrice.notInteger",
               "The price of economy seats is not float type.");
      }

      // Checking: price of business
      try {
         final float busiPrice = Float.valueOf(cmd.getBusinessPrice());
         if (busiPrice < 1)
            err.rejectValue("businessPrice", "error.addFlight.businessPrice.tooLess",
                  "The price of business seats is too less.");
      } catch (NumberFormatException e) {
         err.rejectValue("businessPrice", "error.addFlight.businessPrice.notInteger",
               "The price of business seats is not float type.");
      }

      final Calendar calendar = Calendar.getInstance();
      final int curMonth = calendar.get(Calendar.MONTH) + 1; // 0-based.
      final int curYear = calendar.get(Calendar.YEAR);
      final int curDay = calendar.get(Calendar.DAY_OF_MONTH);
//      final int curHour = calendar.get(Calendar.HOUR);
//      final int curMin = calendar.get(Calendar.MINUTE);

      // Checking: departYear
      int dYear;
      try {
         dYear = Integer.valueOf(cmd.getDepartYear());
      } catch (NumberFormatException e) {
         err.rejectValue("departYear", "error.addFlight.departYear.notInteger",
               "Departing year value is not integer.");
         return;
      }
      if (dYear < curYear)
         err.rejectValue("departYear", "error.addFlight.departYear.tooLess",
               "Departing year is already past.");
      else if (dYear >= curYear + MAX_YEAR)
         err.rejectValue("departYear", "error.addFlight.departYear.tooMuch",
               "Departing year is too much future.");

      // Checking: departMonth
      int dMonth;
      try {
         dMonth = Integer.valueOf(cmd.getDepartMonth());
      } catch (NumberFormatException e) {
         err.rejectValue("departMonth", "error.addFlight.departMonth.notInteger",
               "Departing month value is not integer.");
         return;
      }
      final boolean bSameDepartYear = (curYear == dYear);
      if (dMonth < 1)
         err.rejectValue("departMonth", "error.addFlight.departMonth.tooLess",
               "Departing month is too less.");
      else if (dMonth > 12)
         err.rejectValue("departMonth", "error.addFlight.departMonth.tooMuch",
               "Departing month is too much.");
      else if (bSameDepartYear && dMonth < curMonth)
         err.rejectValue("departMonth", "error.addFlight.departMonth.pastMonth",
               "Already past month.");

      // Checking: departDay
      int dDay;
      try {
         dDay = Integer.valueOf(cmd.getDepartDay());
      } catch (NumberFormatException e) {
         err.rejectValue("departDay", "error.addFlight.departDay.notInteger",
               "Departing day value is not integer.");
         return;
      }
      final boolean bSameDepartMonth = (dMonth == curMonth);
      {
         final Calendar c = Calendar.getInstance();
         c.set(dYear, dMonth - 1, 1);
         final int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
         if (dDay < 1)
            err.rejectValue("departDay", "error.addFlight.departDay.tooLess",
                  "Departing day is too less.");
         else if (dDay > maxDay)
            err.rejectValue("departDay", "error.addFlight.departDay.tooMuch",
                  "Departing day is too much.");
         else if (bSameDepartYear && bSameDepartMonth && dDay < curDay)
            err.rejectValue("departDay", "error.addFlight.departDay.pastDay", "Already past day.");
      }

      // Checking: departHour
      int dHour;
      try {
         dHour = Integer.valueOf(cmd.getDepartHour());
      } catch (NumberFormatException e) {
         err.rejectValue("departHour", "error.addFlight.departHour.notInteger",
               "Departing hour value is not integer.");
         return;
      }
//      final boolean bSameDepartDay = (curDay == dDay);
//      if (dHour < 0)
//         err.rejectValue("departHour", "error.addFlight.departHour.tooLess",
//               "Departing hour is too less.");
//      else if (dHour > 24)
//         err.rejectValue("departHour", "error.addFlight.departHour.tooMuch",
//               "Departing hour is too much.");
//      else if (bSameDepartYear && bSameDepartMonth && bSameDepartDay && dHour < curHour)
//         err.rejectValue("departHour", "error.addFlight.departHour.pastHour", "Already past hour.");

      // Checking: departMin
      int dMin;
      try {
         dMin = Integer.valueOf(cmd.getDepartMin());
      } catch (NumberFormatException e) {
         err.rejectValue("departMin", "error.addFlight.departMin.notInteger",
               "Departing minute value is not integer.");
         return;
      }
//      final boolean bSameDepartHour = (curHour == dHour);
//      if (dMin < 0)
//         err.rejectValue("departMin", "error.addFlight.departMin.tooLess",
//               "Departing minute is too less.");
//      else if (dMin > 60)
//         err.rejectValue("departMin", "error.addFlight.departMin.tooMuch",
//               "Departing minute is too much.");
//      else if (bSameDepartYear && bSameDepartMonth && bSameDepartDay && bSameDepartHour
//            && dMin < curMin)
//         err.rejectValue("departMin", "error.addFlight.departHour.pastMin", "Already past minute.");

      // Checking: returnYear
      int rYear;
      try {
         rYear = Integer.valueOf(cmd.getReturnYear());
      } catch (NumberFormatException e) {
         err.rejectValue("returnYear", "error.addFlight.returnYear.notInteger",
               "Returning year value is not integer.");
         return;
      }
      if (rYear < dYear)
         err.rejectValue("returnYear", "error.addFlight.returnYear.earlierThanDepart",
               "Returning year should be later than departing year.");
      else if (rYear >= curYear + MAX_YEAR)
         err.rejectValue("returnYear", "error.addFlight.returnYear.tooMuch",
               "Returning year is too much future.");

      // Checking: returnMonth
      int rMonth;
      try {
         rMonth = Integer.valueOf(cmd.getReturnMonth());
      } catch (NumberFormatException e) {
         err.rejectValue("returnMonth", "error.addFlight.returnMonth.notInteger",
               "Returning month value is not integer.");
         return;
      }
      final boolean bSameReturnYear = (dYear == rYear);
      if (rMonth < 1)
         err.rejectValue("returnMonth", "error.addFlight.returnMonth.tooLess",
               "Returning month is too less.");
      else if (rMonth > 12)
         err.rejectValue("returnMonth", "error.addFlight.returnMonth.tooMuch",
               "Returning month is too much.");
      else if (bSameReturnYear && rMonth < dMonth)
         err.rejectValue("returnMonth", "error.addFlight.returnMonth.earlierThanDepart",
               "Returning month should be later than departing month.");

      // Checking: returnDay
      int rDay;
      try {
         rDay = Integer.valueOf(cmd.getReturnDay());
      } catch (NumberFormatException e) {
         err.rejectValue("returnDay", "error.addFlight.returnDay.notInteger",
               "Returning day value is not integer.");
         return;
      }
      {
         final boolean bSameReturnMonth = (rMonth == dMonth);
         final Calendar c = Calendar.getInstance();
         c.set(rYear, rMonth - 1, 1);
         final int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
         if (rDay < 1)
            err.rejectValue("returnDay", "error.addFlight.returnDay.tooLess",
                  "Returning day is too less.");
         else if (rDay > maxDay)
            err.rejectValue("returnDay", "error.addFlight.returnDay.tooMuch",
                  "Returning day is too much.");
         else if (bSameReturnYear && bSameReturnMonth && rDay < dDay)
            err.rejectValue("returnDay", "error.addFlight.returnDay.earlierThanDepart",
                  "Returning day should be later than departing day.");
      }
   }
}
