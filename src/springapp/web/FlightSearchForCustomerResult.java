package springapp.web;

public class FlightSearchForCustomerResult {
   private String airline_code;
   private String airline_name;
   private String flightNo;
   private String airportByDepartureLocation_code;
   private String airportByDepartureLocation_name;
   private String departureTime;
   private String airportByArrivalLocation_code;
   private String airportByArrivalLocation_name;
   private String arrivalTime;
   private String businessPrice;
   private String economyPrice;
   private String businessSeats;
   private String economySeats;
   private String durationHours;

   public void setAirline_code(String airline_code) {
      this.airline_code = airline_code;
   }

   public String getAirline_code() {
      return airline_code;
   }

   public void setFlightNo(String flightNo) {
      this.flightNo = flightNo;
   }

   public String getFlightNo() {
      return flightNo;
   }

   public void setAirportByDepartureLocation_code(String airportByDepartureLocation_code) {
      this.airportByDepartureLocation_code = airportByDepartureLocation_code;
   }

   public String getAirportByDepartureLocation_code() {
      return airportByDepartureLocation_code;
   }

   public void setDepartureTime(String departureTime) {
      this.departureTime = departureTime;
   }

   public String getDepartureTime() {
      return departureTime;
   }

   public void setAirportByArrivalLocation_code(String airportByArrivalLocation_code) {
      this.airportByArrivalLocation_code = airportByArrivalLocation_code;
   }

   public String getAirportByArrivalLocation_code() {
      return airportByArrivalLocation_code;
   }

   public void setBusinessPrice(String businessPrice) {
      this.businessPrice = businessPrice;
   }

   public String getBusinessPrice() {
      return businessPrice;
   }

   public void setEconomyPrice(String economyPrice) {
      this.economyPrice = economyPrice;
   }

   public String getEconomyPrice() {
      return economyPrice;
   }

   public void setArrivalTime(String arrivalTime) {
      this.arrivalTime = arrivalTime;
   }

   public String getArrivalTime() {
      return arrivalTime;
   }

   public void setBusinessSeats(String businessSeats) {
      this.businessSeats = businessSeats;
   }

   public String getBusinessSeats() {
      return businessSeats;
   }

   public void setEconomySeats(String economySeats) {
      this.economySeats = economySeats;
   }

   public String getEconomySeats() {
      return economySeats;
   }

   public void setAirline_name(String airline_name) {
      this.airline_name = airline_name;
   }

   public String getAirline_name() {
      return airline_name;
   }

   public void setAirportByDepartureLocation_name(String airportByDepartureLocation_name) {
      this.airportByDepartureLocation_name = airportByDepartureLocation_name;
   }

   public String getAirportByDepartureLocation_name() {
      return airportByDepartureLocation_name;
   }

   public void setAirportByArrivalLocation_name(String airportByArrivalLocation_name) {
      this.airportByArrivalLocation_name = airportByArrivalLocation_name;
   }

   public String getAirportByArrivalLocation_name() {
      return airportByArrivalLocation_name;
   }

   public void setDurationHours(String durationTime) {
      this.durationHours = durationTime;
   }

   public String getDurationHours() {
      return durationHours;
   }
}
