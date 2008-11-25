package hibernate;

import java.util.EnumSet;
import java.util.HashMap;

// Generated Oct 15, 2008 10:13:30 PM by Hibernate Tools 3.2.2.GA

/**
 * Itinerary generated by hbm2java
 */
@SuppressWarnings("serial")
public class Itinerary implements java.io.Serializable {

   public enum EStatus {
      CANCELED("canceled"), BOOKED("booked"), RESERVED("reserved");

      private static final HashMap<String, EStatus> reverseMap = new HashMap<String, EStatus>();

      static {
         for (EStatus s : EnumSet.allOf(EStatus.class))
            reverseMap.put(s.toString(), s);
      }

      private String description;

      private EStatus(String description) {
         this.description = description;
      }

      @Override
      public String toString() {
         return this.description;
      }

      public static EStatus get(String code) {
         return reverseMap.get(code);
      }
   }

   /**
    * The itinerary id
    */
   private ItineraryId id;
   
   /**
    * The customer to whom the itinerary belongs to
    */
   private Customer customer;
   
   /**
    * The flight in the itinerary
    */
   private Flight flight;
   
   /**
    * The status of the flight whether its booked, reserved or canceled
    */
   private EStatus status;

   /**
    * The ticket number
    */
   private String ticketNo;
   
   /**
    * Constructs a new itinerary object
    */
   public Itinerary() {
   }

   /**
    * Constructs a new itinerary object with the given parameters
    * @param id
    * @param customer - the customer 
    * @param flight - the flight 
    */
   public Itinerary(ItineraryId id, Customer customer, Flight flight) {
      this.id = id;
      this.customer = customer;
      this.flight = flight;
   }

   /**
    * Constructs a new itinerary object 
    * @param id - the itinerary id
    * @param customer - the customer to whom the itinerary belongs
    * @param flight - the flight associated with the itinerary
    * @param status - the status of the flight
    */
   public Itinerary(ItineraryId id, Customer customer, Flight flight, String status) {
      this.id = id;
      this.customer = customer;
      this.flight = flight;
      this.status = EStatus.get(status);
   }

   /**
    * Constructs a new itinerary object 
    * @param id - the itinerary id
    * @param customer - the customer to whom the itinerary belongs
    * @param flight - the flight associated with the itinerary
    * @param status - the status of the flight
    * @param ticketNo - the ticketNo
    */
   public Itinerary(ItineraryId id, Customer customer, Flight flight, String status, String ticketNo) {
	   this.id = id;
	   this.customer = customer;
	   this.flight = flight;
	   this.status = EStatus.get(status);
	   this.ticketNo = ticketNo;
   }
   
   /**
    * Returns the itinerary id
    * @return the itinerary id
    */
   public ItineraryId getId() {
      return this.id;
   }

   /**
    * Sets the itinerary id
    * @param id - the itinerary id
    */
   public void setId(ItineraryId id) {
      this.id = id;
   }

   /**
    * Returns the customer to whom the itinerary belongs
    * @return the customer to whom the itinerary belongs
    */
   public Customer getCustomer() {
      return this.customer;
   }

   /**
    * Sets the customer to whom the itinerary belongs
    * @param customer - the customer to whom the itinerary belongs
    */
   public void setCustomer(Customer customer) {
      this.customer = customer;
   }

   /**
    * Returns the flight associated with the itinerary
    * @return the flight associated with the itinerary
    */
   public Flight getFlight() {
      return this.flight;
   }

   /**
    * Sets the flight associated with the itinerary
    * @param flight - the flight associated with the itinerary
    */
   public void setFlight(Flight flight) {
      this.flight = flight;
   }

   /**
    * Returns the status of the flight
    * @return the status of the flight
    */
   public String getStatus() {
      return this.status.toString();
   }

   /**
    * Sets the status of the flight
    * @param status - the status of the flight
    */
   public void setStatus(String status) {
      this.status = EStatus.get(status);
   }

   /**
    * Returns the ticket number
    * @return the ticket number
    */
   public String getTicketNo() {
	   return ticketNo;
   }

   /**
    * Sets the ticket number
    * @param ticketNo - th ticket number
    */
   public void setTicketNo(String ticketNo) {
	   this.ticketNo = ticketNo;
   }
}
