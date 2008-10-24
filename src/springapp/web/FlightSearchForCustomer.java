package springapp.web;

import java.util.Date;

public class FlightSearchForCustomer {

   // TODO change from String to Ineger
   private String departMonth = "1";
   private String departDay = "1";
   private String departYear;
   private String departHour;
   private String departMin;
   private String departLocation;
   
   private String arrivalMonth = "1";
   private String arrivalDay = "1";
   private String arrivalYear;
   private String arrivalHour;
   private String arrivalMin;
   private String arrivalLocation;
   
   private String numPassengers = "1";
   
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
   public void setDepartMin(String departMin) {
      this.departMin = departMin;
   }
   public String getDepartMin() {
      return departMin;
   }
   public void setDepartLocation(String departLocation) {
      this.departLocation = departLocation;
   }
   public String getDepartLocation() {
      return departLocation;
   }
   public void setArrivalMonth(String arrivalMonth) {
      this.arrivalMonth = arrivalMonth;
   }
   public String getArrivalMonth() {
      return arrivalMonth;
   }
   public void setArrivalDay(String arrivalDay) {
      this.arrivalDay = arrivalDay;
   }
   public String getArrivalDay() {
      return arrivalDay;
   }
   public void setArrivalYear(String arrivalYear) {
      this.arrivalYear = arrivalYear;
   }
   public String getArrivalYear() {
      return arrivalYear;
   }
   public void setArrivalHour(String arrivalHour) {
      this.arrivalHour = arrivalHour;
   }
   public String getArrivalHour() {
      return arrivalHour;
   }
   public void setArrivalMin(String arrivalMin) {
      this.arrivalMin = arrivalMin;
   }
   public String getArrivalMin() {
      return arrivalMin;
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
}
