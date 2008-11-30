package springapp.web;

import static org.junit.Assert.*;

import java.util.HashMap;

import hibernate.Customer;
import hibernate.Itinerary;

import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

public class CreditCardValidatorTest {
   /**
    * including a 16-digit card number and a 4-digit expiration date.
    * As a convenience for this project,
    * a valid combination of credit card number and expiration date is defined
    * in such a way that the 4-digit expiration date is defined
    * with a valid mmdd format,
    * and as an integer is a denominator of the 16-digit card number.
    */

   private CreditCardValidator v = null;
   private Itinerary it = null;
   private Customer c = null;
   private Errors err = null;

   private void init() {
      v = new CreditCardValidator();
      it = new Itinerary();
      c = new Customer();
      it.setCustomer(c);
      err = new MapBindingResult(new HashMap<Object, Object>(), "foo");
   }

   private void validCC(Long cc, Integer exp) {
      init();
      c.setCcNo(cc);
      c.setExpiration(exp);
      v.validate(it, err);
      if (null != err.getFieldError("ccNo"))
         fail("Card number problem: " + err.getFieldError("ccNo").getDefaultMessage());
      if (null != err.getFieldError("expiration"))
         fail("Card expiration date problem: "
               + err.getFieldError("expiration").getDefaultMessage());
   }

   private void invalidCC(Long cc, Integer exp, String[] expectedErrorFields) {
      init();
      try {
         c.setCcNo(cc);
         c.setExpiration(exp);
      } catch (IllegalArgumentException e) {
         return;
      }

      v.validate(it, err);
      assertTrue(err.hasErrors());
      for (final String f : expectedErrorFields)
         assertNotNull("Problem is not detected. (Field:" + f + ")", err.getFieldError(f));
      assertEquals(expectedErrorFields.length, err.getAllErrors().size());
   }

   @Test
   public void testNormalCase() {
      validCC(1234567890123456L, 109);
      validCC(9234567890123456L, 1209);
   }

   @Test
   public void testEmptyCC() {
      invalidCC(0L, 131, new String[] { "customer.ccNo" });
      invalidCC(1234567890123456L, -131, new String[] { "customer.expiration" });
      invalidCC(123456789012345L, 0111, new String[] { "customer.ccNo", "customer.expiration" });
      invalidCC(10000000000000000L, 10, new String[] { "customer.ccNo", "customer.expiration" });
      invalidCC(-123456789012345L, 1309, new String[] { "customer.ccNo", "customer.expiration" });
      invalidCC(1234567890123456L, 12345, new String[] { "customer.expiration" });
      invalidCC(1234567890123456L, 0, new String[] { "customer.expiration" });
      invalidCC(null, null, new String[] { "customer.ccNo", "customer.expiration" });
   }

}
