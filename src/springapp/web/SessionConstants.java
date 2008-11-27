package springapp.web;

/**
 * SessionConstants class has variable names for session.
 * The variable name that will be stored in session use be stores here.
 * This makes compile possible to check variable name at compile time.
 */
public abstract class SessionConstants {
   public static final String USERNAME = "username";
   public static final String CREDIT_TICKET = "ticket";
   public static final String LOGIN_REDIRECT_AFTER_LOGIN = "login.redirectAfterLogin";
   public static final String RESERVE_FLIGHTS_FOR_CUSTOMER = "reserve.flights.for.customer";
   public static final String RESERVE_SEATCLASS_FOR_CUSTOMER = "reserve.seat.type.for.customer";
   public static final String RESERVE_NUM_PASSENGERS_FOR_CUSTOMER = "reserve.num.passengers.for.customer";
   public static final String CREDIT_FLIGHT_NO = "credit.flight_no";
}
