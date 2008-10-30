package springapp.web;

import java.net.*;
import java.io.*;

/**
 * This class represents an HTTP client class that sends credit card information
 * to a remote server for validation.  The validation is done by interpreting both
 * the credit card number and the experation date as integer numbers and take the modulo
 * operation on exp date from card number.  If the modulo is zero, then the card is valid.
 * Otherwise, the card is false.
 *
 * @author Nick Duan
 */
public class CreditCardValidator {


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
    public static void main(String[] args)    {

		/*if(args.length < 5) {
			System.out.println("Usage: java ValidateCC url cardnumber expdate username password");
			return;
		}*/

		/*String urlString = args[0];
    	long cardNumber = Long.parseLong(args[1]);
		int expDate = Integer.parseInt(args[2]);
		String temp = args[3] + ":" + args[4];
		*/
		String urlString = "http://localhost:8080/validation/ValidateCC";
		long cardNumber = Long.parseLong("1111111111111111");
		int expDate = Integer.parseInt("2222");
		String temp = "tomcat:tomcat";

		String encodedPassword = (new sun.misc.BASE64Encoder()).encode(temp.getBytes());


        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestProperty("Authorization", "Basic " + encodedPassword);

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            System.out.println("Send card number and exp date for validation..\n");
            DataOutputStream dataOutStream = new DataOutputStream(connection.getOutputStream());
            dataOutStream.writeLong(cardNumber);
            dataOutStream.writeInt(expDate);
            dataOutStream.close();

            BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println("Getting HTTP response..\n");
            String line = responseReader.readLine();

            System.out.println("Response received: " + line);

            if(line != null && line.equals("VALID"))
                System.out.println("Valid card number " + cardNumber + " with exp date " + expDate);
            else
                System.out.println("Invalid card number " + cardNumber + " with exp date " + expDate);


            responseReader.close();
            connection.disconnect();
        }
        catch (java.net.MalformedURLException e) {
            System.err.println("java.net.MalformedURLException caught: " + e.getMessage());
            e.printStackTrace();
        }
        catch (java.io.IOException e) {
            System.err.println("java.io.IOException caught: " + e.getMessage());
            e.printStackTrace();
        }


    }
}