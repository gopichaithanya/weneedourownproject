package springapp.web;

import hibernate.Itinerary.ESeatClass;
import hibernate.manager.FlightManager.EWeek;

import java.util.Calendar;

/**
 * This class is used for command object on flight search page.
 * This class will contain these information:
 * <UL><LI> depart location; airport code</LI>
 * <LI> arrival location; airport code</LI>
 * <LI> departure Month from 1 to 12</LI>
 * <LI> departure Year from 2008.</LI>
 * <LI> departure Hour from 0 to 23.</LI>
 * <LI> Trip type: round trip or one way trip.</LI>
 * <LI> number of passengers</LI>
 * <LI> return month from 1 to 12</LI>
 * <LI> return year from 2008.</LI>
 * <LI> return hour from 0 to 23</LI>
 * <LI> seatClass</LI>
 * <LI> return flight number (optional)</LI>
 * <LI> departing flight number (optional)</LI></UL>
 */
public class FlightSearchForCustomerCommand {

   /**
    * Trip type:
    * <UL><LI>One way trip</LI>
    * <LI>Round trip</LI></UL>
    */
   public enum ETripType {
      ROUND_TRIP("Round trip"), ONEWAY_TRIP("One way trip");

      private String description;

      ETripType(String description) {
         this.description = description;
      }

      public String getDescription() {
         return description;
      }
   };

   private String departLocation = "IAD";
   private String arrivalLocation = "JFK";

   private Integer departMonth;
   private Integer departDay;
   private Integer departYear;
   private Integer departHour = -1;
   private Integer departHourRange = 3;
   private EWeek departWeek;

   private Integer numPassengers = 1;
   private ETripType tripType = ETripType.ONEWAY_TRIP;

   private Integer returnMonth;
   private Integer returnDay;
   private Integer returnYear;
   private Integer returnHour = -1;
   private Integer returnHourRange = 3;
   private EWeek returnWeek;

   private ESeatClass seatClass = ESeatClass.ECONOMY;

   private Integer departFlightNo = null;
   private Integer returnFlightNo = null;

   /**
    * Constructs default object with date of today.
    */
   public FlightSearchForCustomerCommand() {
      final Calendar calendar = Calendar.getInstance();
      final int curMonth = calendar.get(Calendar.MONTH) + 1; // 0-based.
      final int curDay = calendar.get(Calendar.DAY_OF_MONTH);
      final int curYear = calendar.get(Calendar.YEAR);

      returnMonth = departMonth = curMonth;
      returnDay = departDay = curDay;
      returnYear = departYear = curYear;
   }

   /**
    * Observation method of trip type
    * @return whether this is one way trip or not
    */
   public boolean isOneWayTrip() {
      return getTripType() == ETripType.ONEWAY_TRIP;
   }

   /**
    * Observation method of trip type
    * @return whether this is round trip or not
    */
   public boolean isRoundTrip() {
      return getTripType() == ETripType.ROUND_TRIP;
   }

   /**
    * Observation method of departure flight number
    * @return whether the departure flight number is null or not
    */
   public boolean isNullDepartFlightNo() {
      return (null == getDepartFlightNo());
   }

   /**
    * Observation method for return flight number
    * @return whether the return flight number is null or not
    */
   public boolean isNullReturnFlightNo() {
      return (null == getReturnFlightNo());
   }

   /**
    * Observation method for departure flight number
    * @return whether the departure flight number is valid
    */
   public boolean isValidDepartFlightNo() {
      if (null == getDepartFlightNo())
         return true;
      final Integer no = getDepartFlightNo();
      return (null != no && no >= 0 && no <= 999);
   }

   /**
    * Observation method for return flight number
    * @return whether the return flight number is valid
    */
   public boolean isValidReturnFlightNo() {
      if (null == getReturnFlightNo())
         return true;
      final Integer no = getReturnFlightNo();
      return (null != no && no >= 0 && no <= 999);
   }

   /**
    * sets departing month
    * @param departMonth departing month from 1 to 12
    */
   public void setDepartMonth(Integer departMonth) {
      this.departMonth = departMonth;
   }

   /**
    * returns departing month
    * @return departing month from 1 to 12
    */
   public Integer getDepartMonth() {
      return departMonth;
   }

   /**
    * sets departing day
    * @param departDay departing day from 1 to 31; the last day is depending on the month
    */
   public void setDepartDay(Integer departDay) {
      this.departDay = departDay;
   }

   /**
    * returns departing day
    * @return departing day from 1 to 31; the last day is depending on the month
    */
   public Integer getDepartDay() {
      return departDay;
   }

   /**
    * sets departing year
    * @param departYear departing year after 2008
    */
   public void setDepartYear(Integer departYear) {
      this.departYear = departYear;
   }

   /**
    * returns departing year
    * @return departing year after 2008
    */
   public Integer getDepartYear() {
      return departYear;
   }

   /**
    * sets departing hour
    * @param departHour departing hour from 1 to 23. The other values will be interpreted as "anytime"
    */
   public void setDepartHour(Integer departHour) {
      this.departHour = departHour;
   }

   /**
    * returns departing hour
    * @return departing hour from 1 to 23. The other values mean "anytime"
    */
   public Integer getDepartHour() {
      return departHour;
   }

   /**
    * sets departing airport code
    * @param departLocation departing airport code
    */
   public void setDepartLocation(String departLocation) {
      this.departLocation = departLocation;
   }

   /**
    * returns departing airport code
    * @return departing airport code
    */
   public String getDepartLocation() {
      return departLocation;
   }

   /**
    * sets returning month
    * @param returnMonth returning month from 1 to 12
    */
   public void setReturnMonth(Integer returnMonth) {
      this.returnMonth = returnMonth;
   }

   /**
    * returns returning month
    * @return returning month from 1 to 12
    */
   public Integer getReturnMonth() {
      return returnMonth;
   }

   /**
    * sets returning day
    * @param returnDay returning day from 1 to 31; the last day is depending on the month
    */
   public void setReturnDay(Integer returnDay) {
      this.returnDay = returnDay;
   }

   /**
    * returns returning day
    * @return rturning day from 1 to 31; the last day is depending on the month
    */
   public Integer getReturnDay() {
      return returnDay;
   }

   /**
    * sets returning year
    * @param returnYear after 2008
    */
   public void setReturnYear(Integer returnYear) {
      this.returnYear = returnYear;
   }

   /**
    * returns returning year
    * @return returning year after 2008
    */
   public Integer getReturnYear() {
      return returnYear;
   }

   /**
    * sets returning hour
    * @param returnHour returning hour from 1 to 23; the other values will be interpreted as "anytime"
    */
   public void setReturnHour(Integer returnHour) {
      this.returnHour = returnHour;
   }

   /**
    * returns returning hour
    * @return returning hour from 1 to 23; the other values mean "anytime"
    */
   public Integer getReturnHour() {
      return returnHour;
   }

   /**
    * sets arrival airport code
    * @param arrivalLocation arrival airport code
    */
   public void setArrivalLocation(String arrivalLocation) {
      this.arrivalLocation = arrivalLocation;
   }

   /**
    * returns arrival airport code
    * @return arrival airport code
    */
   public String getArrivalLocation() {
      return arrivalLocation;
   }

   /**
    * sets number of passengers
    * @param numPassengers number of passengers
    */
   public void setNumPassengers(Integer numPassengers) {
      this.numPassengers = numPassengers;
   }

   /**
    * returns number of passengers
    * @return number of passengers; bigger than 0
    */
   public Integer getNumPassengers() {
      return numPassengers;
   }

   /**
    * sets trip type
    * @param tripType trip type
    */
   public void setTripType(ETripType tripType) {
      this.tripType = tripType;
   }

   /**
    * returns trip type
    * @return trip type
    */
   public ETripType getTripType() {
      return tripType;
   }

   /**
    * sets the searching hour range
    * @param departHourRange searching hour range
    */
   public void setDepartHourRange(Integer departHourRange) {
      this.departHourRange = departHourRange;
   }

   /**
    * returns searching hour range
    * @return searching hour range
    */
   public Integer getDepartHourRange() {
      return departHourRange;
   }

   /**
    * sets departing flight number
    * @param departFlightNo departing flight number
    */
   public void setDepartFlightNo(Integer departFlightNo) {
      this.departFlightNo = departFlightNo;
   }

   /**
    * returns departing flight number
    * @return departing flight number
    */
   public Integer getDepartFlightNo() {
      return departFlightNo;
   }

   /**
    * sets return flight number
    * @param returnFlightNo return flight number
    */
   public void setReturnFlightNo(Integer returnFlightNo) {
      this.returnFlightNo = returnFlightNo;
   }

   /**
    * returns return flight number
    * @return return flight number
    */
   public Integer getReturnFlightNo() {
      return returnFlightNo;
   }

   /**
    * sets seat class. ex) BUSINESS class / ECONOMY class
    * @param seatClass seat class
    */
   public void setSeatClass(ESeatClass seatClass) {
      this.seatClass = seatClass;
   }

   /**
    * returns seat class. ex) BUSINESS class / ECONOMY class
    * @return seat class
    */
   public ESeatClass getSeatClass() {
      return seatClass;
   }

   /**
    * observation method for seat class
    * @return whether it is business class
    */
   public boolean isBusinessSeat() {
      return seatClass == ESeatClass.BUSINESS;
   }

   /**
    * observation method for seat class
    * @return whether it is economy class
    */
   public boolean isEconomySeat() {
      return seatClass == ESeatClass.ECONOMY;
   }

   /**
    * @param departWeek the departWeek to set
    */
   public void setDepartWeek(EWeek departWeek) {
      this.departWeek = departWeek;
   }

   /**
    * @return the departWeek
    */
   public EWeek getDepartWeek() {
      return departWeek;
   }

   /**
    * @param returnWeek the returnWeek to set
    */
   public void setReturnWeek(EWeek returnWeek) {
      this.returnWeek = returnWeek;
   }

   /**
    * @return the returnWeek
    */
   public EWeek getReturnWeek() {
      return returnWeek;
   }

   /**
    * @param returnHourRange the returnHourRange to set
    */
   public void setReturnHourRange(Integer returnHourRange) {
      this.returnHourRange = returnHourRange;
   }

   /**
    * @return the returnHourRange
    */
   public Integer getReturnHourRange() {
      return returnHourRange;
   }
}