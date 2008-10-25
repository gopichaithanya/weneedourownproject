package hibernate.manager;

import hibernate.util.HibernateUtil;

import java.util.List;

import org.hibernate.Session;

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

}
