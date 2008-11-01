package hibernate.manager;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

@SuppressWarnings("unchecked")
public class AirportManager {

   public static List<String[]> getAirportCode() {
      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      final List<String[]> airports = session.createQuery("SELECT code FROM Airport as airport")
            .list();
      session.close();
      return airports;
   }

   public static List<String[]> getAirportCodeAndName() {
      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      final List<String[]> airports = session.createQuery(
            "SELECT code, name FROM Airport as airport").list();
      session.close();
      return airports;
   }

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
         final float degree = Float.valueOf(v.substring(0, 2));
         final float min = Float.valueOf(v.substring(3, 5));
         final float sec = Float.valueOf(v.substring(6, 8));
         float absNum = degree + (min + (sec / 60)) / 60;
         final char lastChar = v.charAt(v.length() - 1);
         if (lastChar == 'W' || lastChar == 'S')
            absNum *= -1;
         rst.add(absNum);
      }
      return rst;
   }
}
