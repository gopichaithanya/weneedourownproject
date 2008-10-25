package springapp.web;

import org.junit.Test;
import static org.junit.Assert.*;

public class FlightSearchForCustomerValidatorTest {
   @Test
   public void testSupports() {
      final FlightSearchForCustomerValidator v = new FlightSearchForCustomerValidator();
      assertTrue(v.supports(springapp.web.FlightSearchForCustomer.class));
   }

   @Test
   public void testValidate() {
      // TODO
      final FlightSearchForCustomerValidator v = new FlightSearchForCustomerValidator();
   }
}
