package springapp.web;

import java.util.Date;

public class FlightSearchForCustomer {

   private String departLocation;
   private String arrivalLocation;

   private String departMonth = "1";
   private String departDay = "1";
   private String departYear = "2008";
   private String departHour = "anytime";

   private String searchingHourRange = "3";
   private String numPassengers = "1";
   private String tripType = "roundTrip";

   private String returnMonth = "1";
   private String returnDay = "1";
   private String returnYear;
   private String returnHour;

   public void setDepartMonth(String departMonth) {
      this.departMonth = departMonth;
   }

   public String getDepartMonth() {
      return departMonth;
   }

   public void setDepartDay(String departDay) {
      this.departDay = departDay;
   }

   public String getDepartDay() {
      return departDay;
   }

   public void setDepartYear(String departYear) {
      this.departYear = departYear;
   }

   public String getDepartYear() {
      return departYear;
   }

   public void setDepartHour(String departHour) {
      this.departHour = departHour;
   }

   public String getDepartHour() {
      return departHour;
   }

   public void setDepartLocation(String departLocation) {
      this.departLocation = departLocation;
   }

   public String getDepartLocation() {
      return departLocation;
   }

   public void setReturnMonth(String arrivalMonth) {
      this.returnMonth = arrivalMonth;
   }

   public String getReturnMonth() {
      return returnMonth;
   }

   public void setReturnDay(String arrivalDay) {
      this.returnDay = arrivalDay;
   }

   public String getReturnDay() {
      return returnDay;
   }

   public void setReturnYear(String arrivalYear) {
      this.returnYear = arrivalYear;
   }

   public String getReturnYear() {
      return returnYear;
   }

   public void setReturnHour(String arrivalHour) {
      this.returnHour = arrivalHour;
   }

   public String getReturnHour() {
      return returnHour;
   }

   public void setArrivalLocation(String arrivalLocation) {
      this.arrivalLocation = arrivalLocation;
   }

   public String getArrivalLocation() {
      return arrivalLocation;
   }

   public void setNumPassengers(String nPassengers) {
      this.numPassengers = nPassengers;
   }

   public String getNumPassengers() {
      return numPassengers;
   }

   public void setTripType(String tripType) {
      this.tripType = tripType;
   }

   public String getTripType() {
      return tripType;
   }

   public void setSearchingHourRange(String searchingTimeRange) {
      this.searchingHourRange = searchingTimeRange;
   }

   public String getSearchingHourRange() {
      return searchingHourRange;
   }
}
