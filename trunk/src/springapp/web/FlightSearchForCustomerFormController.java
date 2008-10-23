package springapp.web;

import hibernate.*;
import hibernate.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class FlightSearchForCustomerFormController extends SimpleFormController {

//   public ModelAndView handleRequest(HttpServletRequest req,
//         HttpServletResponse resp) throws Exception {
//      final List<String[]> airports = getAirportList();
//      return new ModelAndView("flightSearchForCustomerForm", "airports",
//            airports);
//   }

   public ModelAndView onSubmit(Object command) {
//    int increase = ((FlightSearchForCustomer) command);

      return new ModelAndView(new RedirectView(getSuccessView()));
   }

   protected Object formBackingObject(HttpServletRequest request)
         throws ServletException {
      final FlightSearchForCustomer defaultObj = new FlightSearchForCustomer();
      return defaultObj;
   }

   @SuppressWarnings("unchecked")
   public static List<String[]> getAirportList() {
      final Session session = HibernateUtil.getSessionFactory()
            .getCurrentSession();
      session.beginTransaction();

      final List<String[]> airports = new ArrayList<String[]>();
      final Iterator iterAirport = session.createQuery(
            "SELECT code, name FROM Airport as airport").list().iterator();
      while (iterAirport.hasNext()) {
         final Object[] rsts = (Object[]) iterAirport.next();
         final String code = (String) rsts[0];
         final String name = (String) rsts[1];
         airports.add(new String[] { code, name });
      }
      session.close();
      return airports;
   }

}
