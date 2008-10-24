package springapp.web;

import hibernate.Flight;
import hibernate.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

@SuppressWarnings("unchecked")
public class FlightSearchForCustomerFormController extends SimpleFormController {

   public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
         throws Exception {

      final ModelAndView mv = super.handleRequest(req, resp);

      final List<String[]> airports = getAirportList();
      mv.addObject("airports", airports);

      return mv;
   }

   protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final ModelAndView mv = new ModelAndView(this.getFormView());
      mv.addObject(this.getCommandName(), command);

      final FlightSearchForCustomer cmd = ((FlightSearchForCustomer) command);
      final List searchedFlights = getFlightList();
      mv.addObject("searchedFlights", searchedFlights);

      return mv;
   }

   public static List getFlightList() {

      final List<FlightSearchForCustomerResult> result = new ArrayList<FlightSearchForCustomerResult>();

      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();

      final Iterator<Flight> iter = session.createQuery("from Flight").list().iterator();
      while (iter.hasNext()) {
         final Flight f = iter.next();
         final FlightSearchForCustomerResult flight = new FlightSearchForCustomerResult();
         flight.setAirline_code(f.getAirline().getCode());
         flight.setAirportByArrivalLocation_code(f.getAirportByArrivalLocation().getCode());
         flight.setAirportByDepartureLocation_code(f.getAirportByDepartureLocation().getCode());
         flight.setArrivalTime(f.getArrivalTime().toString());
         flight.setBusinessPrice(f.getBusinessPrice().toString());
         flight.setBusinessSeats(f.getBusinessSeats().toString());
         flight.setDepartureTime(f.getDepartureTime().toString());
         flight.setEconomyPrice(f.getEconomyPrice().toString());
         flight.setEconomySeats(f.getEconomySeats().toString());
         flight.setFlightNo(String.valueOf(f.getFlightNo()));
         result.add(flight);
      }

      session.close();
      return result;
   }

   public static List<String[]> getAirportList() {
      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();

      final List<String[]> airports = new ArrayList<String[]>();
      final Iterator iterAirport = session.createQuery("SELECT code, name FROM Airport as airport")
            .list().iterator();
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
