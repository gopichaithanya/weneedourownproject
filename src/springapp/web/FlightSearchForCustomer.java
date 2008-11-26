package springapp.web;

import hibernate.Itinerary.ESeatClass;

import java.util.Calendar;

/**
 * This class is used for command object on flight search page.
 * This class will contain these information:
 * <LI> depart location; airport code
 * <LI> arrival location; airport code
 * <LI> departure Month from 1 to 12
 * <LI> departure Year from 2008.
 * <LI> departure Hour from 0 to 23.
 * <LI> Trip type: round trip or one way trip.
 * <LI> number of passengers
 * <LI> return month from 1 to 12
 * <LI> return year from 2008.
 * <LI> return hour from 0 to 23
 * <LI> seatClass
 * <LI> return flight number (optional)
 * <LI> departing flight number (optional)
 */
public class FlightSearchForCustomer {

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

   private int departMonth;
   private int departDay;
   private int departYear;
   private int departHour = -1;

   private int searchingHourRange = 3;
   private int numPassengers = 1;
   private ETripType tripType = ETripType.ROUND_TRIP;

   private int returnMonth;
   private int returnDay;
   private int returnYear;
   private int returnHour = -1;

   private ESeatClass seatClass = ESeatClass.ECONOMY;

   private Integer departFlightNo = null;
   private Integer returnFlightNo = null;

   private static final Calendar calendar = Calendar.getInstance();

   public FlightSearchForCustomer() {
      final int curMonth = calendar.get(Calendar.MONTH) + 1; // 0-based.
      final int curDay = calendar.get(Calendar.DAY_OF_MONTH);
      final int curYear = calendar.get(Calendar.YEAR);

      returnMonth = departMonth = curMonth;
      returnDay = departDay = curDay;
      returnYear = departYear = curYear;
   }

   public boolean isOneWayTrip() {
      return getTripType() == ETripType.ONEWAY_TRIP;
   }

   public boolean isRoundTrip() {
      return getTripType() == ETripType.ROUND_TRIP;
   }

   public boolean isNullDepartFlightNo() {
      return (null == getDepartFlightNo());
   }

   public boolean isNullReturnFlightNo() {
      return (null == getReturnFlightNo());
   }

   public boolean isValidDepartFlightNo() {
      if (null == getDepartFlightNo())
         return true;
      final int no = getDepartFlightNo();
      return (no >= 100 && no <= 999);
   }

   public boolean isValidReturnFlightNo() {
      if (null == getReturnFlightNo())
         return true;
      final int no = getReturnFlightNo();
      return (no >= 100 && no <= 999);
   }

   public void setDepartMonth(int departMonth) {
      this.departMonth = departMonth;
   }

   public int getDepartMonth() {
      return departMonth;
   }

   public void setDepartDay(int departDay) {
      this.departDay = departDay;
   }

   public int getDepartDay() {
      return departDay;
   }

   public void setDepartYear(int departYear) {
      this.departYear = departYear;
   }

   public int getDepartYear() {
      return departYear;
   }

   public void setDepartHour(int departHour) {
      this.departHour = departHour;
   }

   public int getDepartHour() {
      return departHour;
   }

   public void setDepartLocation(String departLocation) {
      this.departLocation = departLocation;
   }

   public String getDepartLocation() {
      return departLocation;
   }

   public void setReturnMonth(int arrivalMonth) {
      this.returnMonth = arrivalMonth;
   }

   public int getReturnMonth() {
      return returnMonth;
   }

   public void setReturnDay(int arrivalDay) {
      this.returnDay = arrivalDay;
   }

   public int getReturnDay() {
      return returnDay;
   }

   public void setReturnYear(int arrivalYear) {
      this.returnYear = arrivalYear;
   }

   public int getReturnYear() {
      return returnYear;
   }

   public void setReturnHour(int arrivalHour) {
      this.returnHour = arrivalHour;
   }

   public int getReturnHour() {
      return returnHour;
   }

   public void setArrivalLocation(String arrivalLocation) {
      this.arrivalLocation = arrivalLocation;
   }

   public String getArrivalLocation() {
      return arrivalLocation;
   }

   public void setNumPassengers(int nPassengers) {
      this.numPassengers = nPassengers;
   }

   public int getNumPassengers() {
      return numPassengers;
   }

   public void setTripType(ETripType tripType) {
      this.tripType = tripType;
   }

   public ETripType getTripType() {
      return tripType;
   }

   public void setSearchingHourRange(int searchingTimeRange) {
      this.searchingHourRange = searchingTimeRange;
   }

   public int getSearchingHourRange() {
      return searchingHourRange;
   }

   public void setDepartFlightNo(Integer departFlightNo) {
      this.departFlightNo = departFlightNo;
   }

   public Integer getDepartFlightNo() {
      return departFlightNo;
   }

   public void setReturnFlightNo(Integer returnFlightNo) {
      this.returnFlightNo = returnFlightNo;
   }

   public Integer getReturnFlightNo() {
      return returnFlightNo;
   }

   public void setSeatClass(ESeatClass seatClass) {
      this.seatClass = seatClass;
   }

   public ESeatClass getSeatClass() {
      return seatClass;
   }

   public boolean isBusinessSeat() {
      return seatClass == ESeatClass.BUSINESS;
   }

   public boolean isEconomySeat() {
      return seatClass == ESeatClass.ECONOMY;
   }
}
