package springapp.manager;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * This class is used for command object on flight add page.
 * This class will contain these information:
 * <UL>
 * <LI> flight number</LI>
 * <LI> airline; airline code</LI>
 * <LI> depart location; airport code</LI>
 * <LI> arrival location; airport code</LI>
 * <LI> departure Month from 1 to 12</LI>
 * <LI> departure Year from 2008.</LI>
 * <LI> departure Hour from 0 to 23.</LI>
 * <LI> Trip type: round trip or one way trip.</LI>
 * <LI> number of passengers</LI>
 * <LI> return month from 1 to 12</LI>
 * <LI> return year from 2008.</LI>
 * <LI> return hour from 0 to 23</LI>
 * <LI> the number of economy seats</LI>
 * <LI> price of each economy seat</LI>
 * <LI> the number of business seats</LI>
 * <LI> price of each business seat</LI>
 */
public class AddFlightCommand {
   private int flightNo;
   private String airline;
   private String airportByArrivalLocation;
   private String airportByDepartureLocation;
   private int economySeats;
   private float economyPrice;
   private int businessSeats;
   private float businessPrice;

   private int departMonth;
   private int departDay;
   private int departYear;
   private int departHour = -1;
   private Integer departMin;

   private int returnMonth;
   private int returnDay;
   private int returnYear;
   private int returnHour = -1;
   private Integer returnMin;

   public AddFlightCommand() {
      final Calendar calendar = Calendar.getInstance();
      final int curMonth = calendar.get(Calendar.MONTH) + 1; // 0-based.
      final int curDay = calendar.get(Calendar.DAY_OF_MONTH);
      final int curYear = calendar.get(Calendar.YEAR);

      returnMonth = departMonth = curMonth;
      returnDay = departDay = curDay;
      returnYear = departYear = curYear;
   }

   public int getFlightNo() {
      return flightNo;
   }

   public void setFlightNo(int flightNo) {
      this.flightNo = flightNo;
   }

   public String getAirline() {
      return airline;
   }

   public void setAirline(String airline) {
      this.airline = airline;
   }

   public String getAirportByArrivalLocation() {
      return airportByArrivalLocation;
   }

   public void setAirportByArrivalLocation(String airportByArrivalLocation) {
      this.airportByArrivalLocation = airportByArrivalLocation;
   }

   public String getAirportByDepartureLocation() {
      return airportByDepartureLocation;
   }

   public void setAirportByDepartureLocation(String airportByDepartureLocation) {
      this.airportByDepartureLocation = airportByDepartureLocation;
   }

   public int getEconomySeats() {
      return economySeats;
   }

   public void setEconomySeats(int economySeats) {
      this.economySeats = economySeats;
   }

   public float getEconomyPrice() {
      return economyPrice;
   }

   public void setEconomyPrice(float economyPrice) {
      this.economyPrice = economyPrice;
   }

   public String getEconomyPriceFormatted() {
      final DecimalFormat df = new DecimalFormat("############0.00");
      return df.format(getEconomyPrice());
   }

   public void setEconomyPriceFormatted(String economyPrice) {
      setEconomyPrice(Float.valueOf(economyPrice));
   }

   public int getBusinessSeats() {
      return businessSeats;
   }

   public void setBusinessSeats(int businessSeats) {
      this.businessSeats = businessSeats;
   }

   public float getBusinessPrice() {
      return businessPrice;
   }

   public void setBusinessPrice(float businessPrice) {
      this.businessPrice = businessPrice;
   }

   public String getBusinessPriceFormatted() {
      final DecimalFormat df = new DecimalFormat("############0.00");
      return df.format(getBusinessPrice());
   }

   public void setBusinessPriceFormatted(String businessPrice) {
      setBusinessPrice(Float.valueOf(businessPrice));
   }

   public int getDepartMonth() {
      return departMonth;
   }

   public void setDepartMonth(int departMonth) {
      this.departMonth = departMonth;
   }

   public int getDepartDay() {
      return departDay;
   }

   public void setDepartDay(int departDay) {
      this.departDay = departDay;
   }

   public int getDepartYear() {
      return departYear;
   }

   public void setDepartYear(int departYear) {
      this.departYear = departYear;
   }

   public int getDepartHour() {
      return departHour;
   }

   public void setDepartHour(int departHour) {
      this.departHour = departHour;
   }

   public int getReturnMonth() {
      return returnMonth;
   }

   public void setReturnMonth(int returnMonth) {
      this.returnMonth = returnMonth;
   }

   public int getReturnDay() {
      return returnDay;
   }

   public void setReturnDay(int returnDay) {
      this.returnDay = returnDay;
   }

   public int getReturnYear() {
      return returnYear;
   }

   public void setReturnYear(int returnYear) {
      this.returnYear = returnYear;
   }

   public int getReturnHour() {
      return returnHour;
   }

   public void setReturnHour(int returnHour) {
      this.returnHour = returnHour;
   }

   /**
    * @param returnMin the returnMin to set
    */
   public void setReturnMin(Integer returnMin) {
      this.returnMin = returnMin;
   }

   /**
    * @return the returnMin
    */
   public Integer getReturnMin() {
      return returnMin;
   }

   /**
    * @param departMin the departMin to set
    */
   public void setDepartMin(Integer departMin) {
      this.departMin = departMin;
   }

   /**
    * @return the departMin
    */
   public Integer getDepartMin() {
      return departMin;
   }
}
