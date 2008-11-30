package hibernate.manager;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

/**
 * AirportManager contains functions to access and maintain airport objects
 */
@SuppressWarnings("unchecked")
public class AirportManager {

   /**
    * This is not designed to be an instance.
    */
   private AirportManager() {
   }

   /**
    * Returns a list of airport codes from the airport table
    * @return a list of airport codes
    */
   public static List<String> getAirportCode() {
      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      final List<String> airports = session.createQuery("SELECT code FROM Airport as airport")
            .list();
      session.close();
      return airports;
   }

   /**
    * Returns a list of airport codes and names from the airport table
    * @return a list of airport codes and names
    */
   public static List<String[]> getAirportCodeAndName() {
      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      final List<String[]> airports = session.createQuery(
            "SELECT code, name FROM Airport as airport").list();
      session.close();
      return airports;
   }

   /**
    * Returns the longitude and latitude values for the airport with the given code
    * @param code - the airport code
    * @return the longitude and latitude values
    */
   public static List<Float> getAirportLatLong(String code) {
      final List<Float> rst = new ArrayList<Float>();
      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      final Object[] latLng = (Object[]) session.createQuery(
            "SELECT latitude1, longitude1 FROM Airport WHERE code = ?").setString(0, code)
            .uniqueResult();
      session.close();
      for (final Object val : latLng) {
         final String v = val.toString();
         int begin = 0;
         int end = v.indexOf('-');
         final float degree = Float.valueOf(v.substring(begin, end));
         begin = end + 1;
         end = v.indexOf('-', begin);
         final float min = Float.valueOf(v.substring(begin, end));
         begin = end + 1;
         end = v.indexOf('.', begin);
         final float sec = Float.valueOf(v.substring(begin, end));
         float absNum = degree + (min + (sec / 60)) / 60;
         final char lastChar = v.charAt(v.length() - 1);
         if (lastChar == 'W' || lastChar == 'S')
            absNum *= -1;
         rst.add(absNum);
      }
      return rst;
   }
}
