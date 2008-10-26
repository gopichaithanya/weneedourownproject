package springapp.web;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

public class FlightSearchForCustomerValidatorTest {

   private final static FlightSearchForCustomerValidator v = new FlightSearchForCustomerValidator();

   @Before
   public void before() {
      FlightSearchForCustomerValidator.bFlagFlightNoIsForcedToBe3Digits = true;
   }

   private static FlightSearchForCustomer createOneWay1() {
      final FlightSearchForCustomer cmdOneWay1 = new FlightSearchForCustomer();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      cmdOneWay1.setDepartLocation("DCA");
      cmdOneWay1.setArrivalLocation("SFO");
      cmdOneWay1.setNumPassengers("1");
      cmdOneWay1.setDepartYear("2008");
      cmdOneWay1.setDepartMonth("12");
      cmdOneWay1.setDepartDay("20");
      cmdOneWay1.setTripType(FlightSearchForCustomer.KEYWORD_oneWayTrip);
      return cmdOneWay1;
   }

   private static FlightSearchForCustomer createOneWay2() {
      final FlightSearchForCustomer cmdOneWay2 = createOneWay1();
      cmdOneWay2.setDepartFlightNo("157");
      return cmdOneWay2;
   }

   private static FlightSearchForCustomer createRound1() {
      final FlightSearchForCustomer cmdRound1 = new FlightSearchForCustomer();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      cmdRound1.setDepartLocation("DCA");
      cmdRound1.setArrivalLocation("SFO");
      cmdRound1.setNumPassengers("1");
      cmdRound1.setDepartYear("2008"); // 04-Jan 2009 10:50 AM
      cmdRound1.setDepartMonth("12");
      cmdRound1.setDepartDay("20");
      cmdRound1.setTripType(FlightSearchForCustomer.KEYWORD_roundTrip);
      cmdRound1.setReturnYear("2008");
      cmdRound1.setReturnMonth("12");
      cmdRound1.setReturnDay("21");
      return cmdRound1;
   }

   private static FlightSearchForCustomer createRound2() {
      final FlightSearchForCustomer cmdRound2 = createRound1();
      cmdRound2.setDepartFlightNo("157");
      return cmdRound2;
   }

   private static FlightSearchForCustomer createRound3() {
      final FlightSearchForCustomer cmdRound3 = createRound2();
      cmdRound3.setReturnFlightNo("157");
      return cmdRound3;
   }

   private static Errors createErrors() {
      return new MapBindingResult(new HashMap<Object, Object>(), "foo");
   }

   @Test
   public void testSupports() {
      assertTrue(v.supports(springapp.web.FlightSearchForCustomer.class));
   }

   @Test
   public void testValidateNull() {
      final Errors errors = createErrors();
      v.validate(null, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateOneWayTrip1() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      v.validate(dummy, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void testValidateOneWayTrip2() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay2();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      v.validate(dummy, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void testValidateRoundTrip1() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      v.validate(dummy, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void testValidateRoundTrip2() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound2();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      v.validate(dummy, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void testValidateRoundTrip3() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound3();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      v.validate(dummy, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartLocation1() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartLocation("XXX");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartLocation2() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartLocation("");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartLocation3() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartLocation(null);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadArrivalLocation1() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setArrivalLocation("XXX");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadArrivalLocation2() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setArrivalLocation("");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadArrivalLocation3() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setArrivalLocation(null);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadNumPassengers1() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setNumPassengers("XXXX");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadNumPassengers2() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setNumPassengers("0");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadNumPassengers3() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setNumPassengers(null);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartYear1() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartYear("2007");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartYear2() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartYear("XX");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartYear3() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartYear(null);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartMonth1() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartMonth("0");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartMonth2() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartMonth("A");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartMonth3() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartMonth(null);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartDay1() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartYear("2009");
      dummy.setDepartMonth("2");
      dummy.setDepartDay("29"); // No exist
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartDay2() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartDay("A");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartDay3() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartDay(null);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartDay4() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartDay("-1");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadTripType1() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setTripType("");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadTripType2() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setTripType("XX");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadTripType3() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setTripType(null);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnYear1() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnYear("2007");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnYear2() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartYear("2009");
      dummy.setReturnYear("2008");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnYear3() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnYear(null);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnYear4() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnYear("XXX");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnYear5() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnYear("");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnMonth1() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnMonth("11");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnMonth2() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnMonth("");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnMonth3() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnMonth(null);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnMonth4() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnMonth("14");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnDay1() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnDay("19");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnDay2() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnDay("XX");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnDay3() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnDay("");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnDay4() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnDay(null);
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnDay5() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound1();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnYear("2009");
      dummy.setReturnMonth("2");
      dummy.setReturnDay("29"); // No exist
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartFlightNo1() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay2();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartFlightNo("99");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartFlightNo2() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay2();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartFlightNo("1001");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartFlightNo3() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay2();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartFlightNo("XXX");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadDepartFlightNo4() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay2();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartFlightNo("");
      v.validate(dummy, errors);
      assertFalse(errors.hasErrors()); // this is not assertTrue
   }

   @Test
   public void testValidateBadDepartFlightNo5() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createOneWay2();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setDepartFlightNo(null);
      v.validate(dummy, errors);
      assertFalse(errors.hasErrors()); // this is not assertTrue
   }

   @Test
   public void testValidateBadReturnFlightNo1() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound2();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnFlightNo("99");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnFlightNo2() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound2();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnFlightNo("1000");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnFlightNo3() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound2();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnFlightNo("XXX");
      v.validate(dummy, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void testValidateBadReturnFlightNo4() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound2();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnFlightNo("");
      v.validate(dummy, errors);
      assertFalse(errors.hasErrors()); // this is not assertTrue
   }

   @Test
   public void testValidateBadReturnFlightNo5() {
      final Errors errors = createErrors();
      final FlightSearchForCustomer dummy = createRound2();
      // 157,AA,DCA,2008-12-20 07:45:00.000000000,SFO,2008-12-20 13:34:00.000000000,30,479.0,10,1357.0
      dummy.setReturnFlightNo(null);
      v.validate(dummy, errors);
      assertFalse(errors.hasErrors()); // this is not assertTrue
   }
}
