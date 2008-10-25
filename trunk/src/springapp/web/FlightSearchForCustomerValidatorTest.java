package springapp.web;

import java.util.HashMap;
import java.util.Iterator;

import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

public class FlightSearchForCustomerValidatorTest {
   @Test
   public void testSupports() {
      final FlightSearchForCustomerValidator v = new FlightSearchForCustomerValidator();
      assertTrue(v.supports(springapp.web.FlightSearchForCustomer.class));
   }

   final FlightSearchForCustomerValidator v = new FlightSearchForCustomerValidator();

   @Test
   public void testValidateNull() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      v.validate(null, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateOneWayTrip() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_oneWayTrip);
      v.validate(dummy, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void testValidateRoundTrip() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_roundTrip);
      dummy.setReturnYear("2009");
      dummy.setReturnMonth("1");
      dummy.setReturnDay("5");
      v.validate(dummy, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void testValidateOneWayTripNoDepartLocation() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
//      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_oneWayTrip);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateOneWayTripNoArrivalLocation() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
//      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_oneWayTrip);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateOneWayTripBadNumPassengers1() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("XXXX");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_oneWayTrip);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateOneWayTripBadNumPassengers2() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("0");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_oneWayTrip);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateOneWayTripBadDepartYear1() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2007"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_oneWayTrip);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateOneWayTripBadDepartYear2() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("XX"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_oneWayTrip);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateOneWayTripBadDepartMonth1() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("0");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_oneWayTrip);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateOneWayTripBadDepartMonth2() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("A");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_oneWayTrip);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateOneWayTripBadDepartDay1() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("2");
      dummy.setDepartDay("29"); // No exist
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_oneWayTrip);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateOneWayTripBadDepartDay2() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("2");
      dummy.setDepartDay("A");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_oneWayTrip);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateOneWayTripNoTripType() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
//      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_oneWayTrip);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateOneWayTripBadTripType() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
      dummy.setTripType("AAA");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateRoundTripBadReturnYear1() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_roundTrip);
      dummy.setReturnYear("2008");
      dummy.setReturnMonth("1");
      dummy.setReturnDay("5");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateRoundTripBadReturnYear2() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_roundTrip);
      dummy.setReturnYear("AA");
      dummy.setReturnMonth("1");
      dummy.setReturnDay("5");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateRoundTripBadReturnMonth1() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("2");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_roundTrip);
      dummy.setReturnYear("2009");
      dummy.setReturnMonth("1");
      dummy.setReturnDay("5");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateRoundTripBadReturnMonth2() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("2");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_roundTrip);
      dummy.setReturnYear("2009");
      dummy.setReturnMonth("A");
      dummy.setReturnDay("5");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateRoundTripBadReturnDay1() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_roundTrip);
      dummy.setReturnYear("2009");
      dummy.setReturnMonth("1");
      dummy.setReturnDay("3");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateRoundTripBadReturnDay2() {
      final Errors errors = new MapBindingResult(new HashMap<Object, Object>(), "foo");
      final FlightSearchForCustomer dummy = new FlightSearchForCustomer();
      dummy.setDepartLocation("DEN");
      dummy.setArrivalLocation("BWI");
      dummy.setNumPassengers("1");
      dummy.setDepartYear("2009"); // 04-Jan 2009 10:50 AM
      dummy.setDepartMonth("1");
      dummy.setDepartDay("4");
      dummy.setTripType(FlightSearchForCustomerFormController.KEYWORD_roundTrip);
      dummy.setReturnYear("2009");
      dummy.setReturnMonth("1");
      dummy.setReturnDay("A");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }
}
