package hibernate.manager;


import java.util.List;

import org.hibernate.Session;

public class AirlineManager 
{
	public static List<String[]> getAirlineCodeAndName() 
	{
	      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	      session.beginTransaction();
	      final List<String[]> airlines = session.createQuery(
	            "SELECT code, name FROM Airline as airline").list();
	      session.close();
	      return airlines;
   }
}
