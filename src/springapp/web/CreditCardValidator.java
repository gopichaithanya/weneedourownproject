package springapp.web;

import hibernate.Customer;
import hibernate.Itinerary;

import java.net.*;
import java.io.*;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

/**
 * This class represents an HTTP client class that sends credit card information
 * to a remote server for validation.  The validation is done by interpreting both
 * the credit card number and the experation date as integer numbers and take the modulo
 * operation on exp date from card number.  If the modulo is zero, then the card is valid.
 * Otherwise, the card is false.
 *
 * @author Nick Duan
 */
public class CreditCardValidator implements Validator {

   /**
    * Returns true if this Validator can validate instances of the supplied aClass, false otherwise
    * @return true if class can be validated, false otherwise
    */
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
         return;
      } else if (((Long) customer.getCcNo()).toString().length() != 16) {
         errors.rejectValue("ccNo", "error.invalid.ccNo", "Invalid Credit card number");
         return;
      }

      if (customer.getExpiration() == null) {
         errors.rejectValue("expiration", "error.invalid.expiration", "Expiration date is blank");
         return;
      } else if (((Integer) customer.getExpiration()).toString().length() != 4) {
         errors.rejectValue("expiration", "error.invalid.expiration", "Invalid expiration date");
         return;
      }

      String urlString = "http://localhost:8080/validation/ValidateCC";
      long cardNumber = customer.getCcNo();
      //long cardNumber = Long.parseLong("1111111111111111");
      int expDate = customer.getExpiration();
      //int expDate = Integer.parseInt("2222");
      String temp = "tomcat:tomcat";

      String encodedPassword = (new sun.misc.BASE64Encoder()).encode(temp.getBytes());

      try {
         URL url = new URL(urlString);
         HttpURLConnection connection = (HttpURLConnection) url.openConnection();

         connection.setRequestProperty("Authorization", "Basic " + encodedPassword);

         connection.setDoInput(true);
         connection.setDoOutput(true);
         connection.setRequestMethod("POST");

         System.out.println("Send card number and exp date for validation..\n");
         DataOutputStream dataOutStream = new DataOutputStream(connection.getOutputStream());
         dataOutStream.writeLong(cardNumber);
         dataOutStream.writeInt(expDate);
         dataOutStream.close();

         BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection
               .getInputStream()));
         System.out.println("Getting HTTP response..\n");
         String line = responseReader.readLine();

         System.out.println("Response received: " + line);

         if (line != null && line.equals("VALID"))
            System.out.println("Valid card number " + cardNumber + " with exp date " + expDate);
         else {
            System.out.println("Invalid card number " + cardNumber + " with exp date " + expDate);
            errors.rejectValue("ccNo", "error.invalid.ccNo",
                  "Invalid credit card or expiration date");
         }

         responseReader.close();
         connection.disconnect();
      } catch (java.net.MalformedURLException e) {
         System.err.println("java.net.MalformedURLException caught: " + e.getMessage());
         e.printStackTrace();
      } catch (java.io.IOException e) {
         System.err.println("java.io.IOException caught: " + e.getMessage());
         e.printStackTrace();
      }

   }
}
