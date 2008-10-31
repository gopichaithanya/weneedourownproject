package hibernate;
// Generated Oct 15, 2008 10:13:30 PM by Hibernate Tools 3.2.2.GA


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Flight generated by hbm2java
 */
@SuppressWarnings("serial")
public class Flight implements java.io.Serializable {

	
	private int flightNo;
    private Airline airline;
    private Airport airportByArrivalLocation;
    private Airport airportByDepartureLocation;
    private Date departureTime;
    private Date arrivalTime;
    private Integer economySeats;
    private Float economyPrice;
    private Integer businessSeats;
    private Float businessPrice;
    private Set<Itinerary> itineraries = new HashSet<Itinerary>(0);

    private final static SimpleDateFormat df_ = new SimpleDateFormat("EEE dd-MMM yyyy HH:mm aaa");

    public Flight() {
    }

	
    public Flight(int flightNo) {
        this.flightNo = flightNo;
    }
    public Flight(int flightNo, Airline airline, Airport airportByArrivalLocation, Airport airportByDepartureLocation, Date departureTime, Date arrivalTime, Integer economySeats, Float economyPrice, Integer businessSeats, Float businessPrice, Set<Itinerary> itineraries) {
       this.flightNo = flightNo;
       this.airline = airline;
       this.airportByArrivalLocation = airportByArrivalLocation;
       this.airportByDepartureLocation = airportByDepartureLocation;
       this.departureTime = departureTime;
       this.arrivalTime = arrivalTime;
       this.economySeats = economySeats;
       this.economyPrice = economyPrice;
       this.businessSeats = businessSeats;
       this.businessPrice = businessPrice;
       this.itineraries = itineraries;
    }
   
    public int getFlightNo() {
        return this.flightNo;
    }
    
    public void setFlightNo(int flightNo) {
        this.flightNo = flightNo;
    }
    public Airline getAirline() {
        return this.airline;
    }
    
    public void setAirline(Airline airline) {
        this.airline = airline;
    }
    public Airport getAirportByArrivalLocation() {
        return this.airportByArrivalLocation;
    }
    
    public void setAirportByArrivalLocation(Airport airportByArrivalLocation) {
        this.airportByArrivalLocation = airportByArrivalLocation;
    }
    public Airport getAirportByDepartureLocation() {
        return this.airportByDepartureLocation;
    }
    
    public void setAirportByDepartureLocation(Airport airportByDepartureLocation) {
        this.airportByDepartureLocation = airportByDepartureLocation;
    }
    public Date getDepartureTime() {
        return this.departureTime;
    }
    
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }
    public Date getArrivalTime() {
        return this.arrivalTime;
    }
    
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public Integer getEconomySeats() {
        return this.economySeats;
    }
    
    public void setEconomySeats(Integer economySeats) {
        this.economySeats = economySeats;
    }
    public Float getEconomyPrice() {
        return this.economyPrice;
    }
    
    public void setEconomyPrice(Float economyPrice) {
        this.economyPrice = economyPrice;
    }
    public Integer getBusinessSeats() {
        return this.businessSeats;
    }
    
    public void setBusinessSeats(Integer businessSeats) {
        this.businessSeats = businessSeats;
    }
    public Float getBusinessPrice() {
        return this.businessPrice;
    }
    
    public void setBusinessPrice(Float businessPrice) {
        this.businessPrice = businessPrice;
    }
    public Set<Itinerary> getItineraries() {
        return this.itineraries;
    }
    
    public void setItineraries(Set<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    public long getDurationHours() {
       final Calendar departTime = Calendar.getInstance();
       final Calendar arrivalTime = Calendar.getInstance();
       departTime.setTime(getDepartureTime());
       arrivalTime.setTime(getArrivalTime());
       final long durationHours = Math.round(Math.ceil((arrivalTime.getTimeInMillis() - departTime
             .getTimeInMillis())
             / (1000 * 60 * 60)));
       return durationHours;
    }


}


