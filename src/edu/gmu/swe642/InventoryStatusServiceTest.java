package edu.gmu.swe642;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

@SuppressWarnings("unused")
public class InventoryStatusServiceTest {

   /**
    * 
    * @param test
    * @return
    */
   static private Date getDate(String test) {

      Date date = new Date();

      try {
         DateFormat formatter = new SimpleDateFormat();
         test = test.replaceAll("-", "/");
         test = test.replaceAll(" ", "/");

         // String[] dateParts = test.split("/");
         formatter = new SimpleDateFormat("MM/dd/yyyy/hh");
         date = (Date) formatter.parse(test);

      } catch (ParseException e) {
         return date;
      }

      return date;
   }

   @Test
   public void testNothing() {
   }

   @Test(expected = IllegalStateException.class)
   public void test() {
//   public static void main(String[] args) {
      InventoryStatusService test = new InventoryStatusService();

      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

      try {
         Date today = (Date) formatter.parse("2008-12-26T08:10:00-12:00");
         System.out.println(today.toString());
         int e = test.getNumOfEmptySeats(today);
         System.out.println(e);
      } catch (ParseException p) {
         System.out.println(p.toString());
      }
      // System.out.println(formatter.format(today));

   }
}
