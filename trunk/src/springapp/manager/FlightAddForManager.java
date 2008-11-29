package springapp.manager;

import java.util.Date;

import hibernate.Airline;
import hibernate.Airport;

public class FlightAddForManager 
{
	private int flightNo;
	private Airline airline;
	private Airport airportByArrivalLocation;
	private Airport airportByDepartureLocation;
	private Integer economySeats;
	private Float economyPrice;
	private Integer businessSeats;
	private Float businessPrice;
	
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
	public Airline getAirline() {
		return airline;
	}
	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	public Airport getAirportByArrivalLocation() {
		return airportByArrivalLocation;
	}
	public void setAirportByArrivalLocation(Airport airportByArrivalLocation) {
		this.airportByArrivalLocation = airportByArrivalLocation;
	}
	public Airport getAirportByDepartureLocation() {
		return airportByDepartureLocation;
	}
	public void setAirportByDepartureLocation(Airport airportByDepartureLocation) {
		this.airportByDepartureLocation = airportByDepartureLocation;
	}
	public Integer getEconomySeats() {
		return economySeats;
	}
	public void setEconomySeats(Integer economySeats) {
		this.economySeats = economySeats;
	}
	public Float getEconomyPrice() {
		return economyPrice;
	}
	public void setEconomyPrice(Float economyPrice) {
		this.economyPrice = economyPrice;
	}
	public Integer getBusinessSeats() {
		return businessSeats;
	}
	public void setBusinessSeats(Integer businessSeats) {
		this.businessSeats = businessSeats;
	}
	public Float getBusinessPrice() {
		return businessPrice;
	}
	public void setBusinessPrice(Float businessPrice) {
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
