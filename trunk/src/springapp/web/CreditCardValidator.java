package springapp.web;

import hibernate.Customer;
import hibernate.Itinerary;

import java.net.*;
import java.util.Calendar;
import java.io.*;

import org.apache.log4j.Logger;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

/**
 * This class represents an HTTP client class that sends credit card information
 * to a remote server for validation.  The validation is done by interpreting both
 * the credit card number and the experation date as integer numbers and take the modulo
 * operation on exp date from card number.  If the modulo is zero, then the card is valid.
 * Otherwise, the card is false.
 */
public class CreditCardValidator implements Validator {

   /**
    * URL of Credit Card Validator.
    * This is given by professor.
    */
   public static final String URL_VALIDATOR = "http://localhost:8080/validation/ValidateCC";

   /**
    * User name for the validator
    */
   public static final String username = "tomcat";

   /**
    * Password for the validator
    */
   public static final String password = "tomcat";

   private final Logger log = Logger.getLogger(getClass());

   /**
    * Returns true if this Validator can validate instances of the supplied aClass, false otherwise
    * @return true if class can be validated, false otherwise
    */
   @SuppressWarnings("unchecked")
   public boolean supports(Class aClass) {
      return aClass.equals(Itinerary.class);
   }

   /**
    * Execute this method using the following arguments: <br>
    * <ul>
    *   <li>args[0]: url of the validation server</li>
    *	 <li>args[1]: credit card number in the format of xxxxxxxxxxxxxxxx</li>
    *   <li>args[2]: credit card exp date in the format of xxxx</li>
    *   <li>args[3]: username</li>
    *	 <li>args[4]: password</li>
    * </ul>
    */
   public void validate(Object obj, Errors errors) {

      final Itinerary it = (Itinerary) obj;
      final Customer customer = it.getCustomer();

      if (customer.getCcNo() == null) {
         errors.rejectValue("ccNo", "error.invalid.ccNo", "Credit card number is blank");
      } else {
         final long cc = customer.getCcNo();
         if (cc <= 0 || ((Long) cc).toString().length() != 16)
            errors.rejectValue("ccNo", "error.invalid.ccNo", "Invalid Credit card number");
      }

      if (customer.getExpiration() == null) {
         errors.rejectValue("expiration", "error.invalid.expiration", "Expiration date is blank");
      } else {
         final int exp = customer.getExpiration();
         final String str = ((Integer) exp).toString();
         final int len = str.length();
         if (exp <= 0 || (len != 4 && len != 3))
            errors.rejectValue("expiration", "error.invalid.expiration", "Invalid expiration date");
         else {
            final Calendar calendar = Calendar.getInstance();
            final int curYear = calendar.get(Calendar.YEAR) % 100;
            final int curMonth = calendar.get(Calendar.MONTH) + 1;

            try {
               final int month = Integer.valueOf(str.substring(0, len - 2));
               final int year = Integer.valueOf(str.substring(len - 2, len));
               if (month <= 0 || month > 12)
                  errors.rejectValue("expiration", "error.outofrange.expiration.month",
                        "Month of expiration date is out of range.");
               if (year >= 50)
                  errors.rejectValue("expiration", "error.outofrange.expiration.year",
                        "Year of expiration date is too big.");
               if (year < curYear)
                  errors.rejectValue("expiration", "error.past.expiration.year",
                        "Year of expiration date has already past.");
               if (year == curYear && month < curMonth)
                  errors.rejectValue("expiration", "error.past.expiration.month",
                        "Month of expiration date has already past.");
            } catch (NumberFormatException e) {
               errors.rejectValue("expiration", "error.invalid.expiration",
                     "Only decimal format is allowed.");
            }
         }
      }

      if (errors.hasErrors())
         return;

      final long cardNumber = customer.getCcNo();
      final int expDate = customer.getExpiration();
      final String temp = username + ":" + password;

      String encodedPassword = (new sun.misc.BASE64Encoder()).encode(temp.getBytes());

      try {
         URL url = new URL(URL_VALIDATOR);
         HttpURLConnection connection = (HttpURLConnection) url.openConnection();

         connection.setRequestProperty("Authorization", "Basic " + encodedPassword);

         connection.setDoInput(true);
         connection.setDoOutput(true);
         connection.setRequestMethod("POST");

         log.info("Send card number and exp date for validation..");
         DataOutputStream dataOutStream = new DataOutputStream(connection.getOutputStream());
         dataOutStream.writeLong(cardNumber);
         dataOutStream.writeInt(expDate);
         dataOutStream.close();

         BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection
               .getInputStream()));
         log.info("Getting HTTP response..");
         String line = responseReader.readLine();

         log.info("Response received: " + line);

         if (line != null && line.equals("VALID"))
            log.info("Valid card number " + cardNumber + " with exp date " + expDate);
         else {
            log.info("Invalid card number " + cardNumber + " with exp date " + expDate);
            errors.rejectValue("INVALID", "error.invalid.ccNo",
                  "Invalid credit card or expiration date");
         }

         responseReader.close();
         connection.disconnect();
      } catch (java.net.MalformedURLException e) {
         log.error("java.net.MalformedURLException caught: " + e.getMessage());
         e.printStackTrace();
      } catch (java.io.IOException e) {
         log.error("java.io.IOException caught: " + e.getMessage());
         e.printStackTrace();
      }

   }
}
