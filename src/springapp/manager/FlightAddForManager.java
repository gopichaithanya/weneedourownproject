package springapp.manager;

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
public class FlightAddForManager {
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

   private int returnMonth;
   private int returnDay;
   private int returnYear;
   private int returnHour = -1;

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
}
