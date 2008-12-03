package springapp.web;

/**
 * SessionConstants class has variable names for session.
 * The variable name that will be stored in session use be stores here.
 * This makes compile possible to check variable name at compile time.
 */
public abstract class SessionConstants {
   /**
    * user login name
    */
   public static final String LOGIN_USERNAME = "username";

   /**
    * the URL that will be shown after logging in 
    */
   public static final String LOGIN_REDIRECT_AFTER_LOGIN = "login.redirectAfterLogin";

   /**
    * the flight numbers that will be reserved on reservation controller. 
    */
   public static final String RESERVE_FLIGHTS_FOR_CUSTOMER = "reserve.flights.for.customer";

   /**
    * the seat class that will be reserved on reservation controller. 
    */
   public static final String RESERVE_SEATCLASS_FOR_CUSTOMER = "reserve.seat.type.for.customer";

   /**
    * the number of passengers that will be reserved on reservation controller. 
    */
   public static final String RESERVE_NUM_PASSENGERS_FOR_CUSTOMER = "reserve.num.passengers.for.customer";

   /**
    * the flight number that was passed to credit card page by parameter.
    * Once the parameter is parsed, then it is stored on session.
    */
   public static final String CREDIT_FLIGHT_NO = "credit.flight_no";

   /**
    * the itinerary data that is just booked now.
    * this data will be used for result page.
    */
   public static final String BOOKED_ITINERARY = "credit.booked.itinerary";

   /**
    * the newly added flight
    */
   public static final String ADDED_NEW_FLIGHT = "addFlight.new.flight";

   /**
    * canceled flight number
    */
   public static final String CANCELED_FLIGHT_NO = "canceled.flightNo";

   /**
    * the result of ticket booking.
    */
   public static final String RESERVATION_RESULT = "reserve.customer.result";
}
