package springapp.web;

import hibernate.Flight;
import hibernate.manager.AirportManager;
import hibernate.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

@SuppressWarnings("unchecked")
public class FlightSearchForCustomerFormController extends SimpleFormController {

   public final static String KEYWORD_oneWayTrip = "oneWayTrip";
   public final static String KEYWORD_roundTrip = "roundTrip";

   private final static SimpleDateFormat df_ = new SimpleDateFormat("EEE dd-MMM yyyy HH:mm aaa");

   public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
         throws Exception {

      final ModelAndView mv = super.handleRequest(req, resp);

      final List<String[]> airports = AirportManager.getAirportCodeAndName();
      mv.addObject("airports", airports);

      return mv;
   }

   protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final ModelAndView mv = new ModelAndView(this.getFormView());
      mv.addObject(this.getCommandName(), command);

      final FlightSearchForCustomer cmd = ((FlightSearchForCustomer) command);
      final List searchedFlights = getFlightList(null);//cmd); // TODO remove comment here.
      mv.addObject("searchedFlights", searchedFlights);

      return mv;
   }

   public static List getFlightList(FlightSearchForCustomer cmd) {

      final List<FlightSearchForCustomerResult> result = new ArrayList<FlightSearchForCustomerResult>();

      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();

      Query q = null;
      if (null == cmd) {
         q = session.createQuery("FROM Flight");
      } else {
         final Date[] departureTimeRange = getTimeRange(cmd.getDepartYear(), cmd.getDepartMonth(),
               cmd.getDepartDay(), cmd.getDepartHour(), cmd.getSearchingHourRange());

         q = session.createQuery(
               "FROM Flight " + "WHERE DEPARTURE_LOCATION = ? " + "AND ARRIVAL_LOCATION = ? "
                     + "AND DEPARTURE_TIME > ? " + "AND DEPARTURE_TIME < ? ").setString(0,
               cmd.getDepartLocation()).setString(1, cmd.getArrivalLocation()).setTimestamp(2,
               departureTimeRange[0]).setTimestamp(3, departureTimeRange[1]);
      }
      final Iterator<Flight> iter = q.list().iterator();

      while (iter.hasNext()) {
         final Flight f = iter.next();
         final FlightSearchForCustomerResult flight = new FlightSearchForCustomerResult();

         flight.setFlightNo(String.valueOf(f.getFlightNo()));
         flight.setAirline_code(f.getAirline().getCode());
         flight.setAirline_name(f.getAirline().getName());
         flight.setAirportByArrivalLocation_code(f.getAirportByArrivalLocation().getCode());
         flight.setAirportByArrivalLocation_name(f.getAirportByArrivalLocation().getName());
         flight.setAirportByDepartureLocation_code(f.getAirportByDepartureLocation().getCode());
         flight.setAirportByDepartureLocation_name(f.getAirportByDepartureLocation().getName());
         flight.setBusinessPrice(f.getBusinessPrice().toString());
         flight.setEconomyPrice(f.getEconomyPrice().toString());
         flight.setBusinessSeats(f.getBusinessSeats().toString());
         flight.setEconomySeats(f.getEconomySeats().toString());
         flight.setArrivalTime(df_.format(f.getArrivalTime()));
         flight.setDepartureTime(df_.format(f.getDepartureTime()));

         final Calendar departTime = Calendar.getInstance();
         final Calendar arrivalTime = Calendar.getInstance();
         departTime.setTime(f.getDepartureTime());
         arrivalTime.setTime(f.getArrivalTime());
         final long durationHours = Math.round(Math
               .ceil((arrivalTime.getTimeInMillis() - departTime.getTimeInMillis())
                     / (1000 * 60 * 60)));
         flight.setDurationHours(String.valueOf(durationHours));

         result.add(flight);
      }

      session.close();
      return result;
   }

   public static Date[] getTimeRange(String year, String month, String day, String hour,
         String searchingHourRange) {
      final int y = Integer.valueOf(year);
      final int m = Integer.valueOf(month) - 1; // Month is 0-based in Calendar class
      final int d = Integer.valueOf(day);

      boolean bAnytime = false;
      int h = 0;
      int searchHour = 0;
      try {
         h = Integer.valueOf(hour);
         searchHour = Integer.valueOf(searchingHourRange);
         if (h < 0 || h > 23)
            bAnytime = true;
         if (searchHour < 0)
            bAnytime = true;
      } catch (NumberFormatException e) {
         bAnytime = true;
      }

      final Calendar startCalendar = Calendar.getInstance();
      final Calendar endCalendar = Calendar.getInstance();
      if (bAnytime) {
         startCalendar.set(y, m, d, 0, 0, 0);
         endCalendar.set(y, m, d, 23, 59, 0);
      } else {
         startCalendar.set(y, m, d, h, 0, 0);
         startCalendar.add(Calendar.HOUR, -searchHour);
         endCalendar.set(y, m, d, h, 0, 0);
         endCalendar.add(Calendar.HOUR, searchHour);
      }

      final Date startDate = startCalendar.getTime();
      final Date endDate = endCalendar.getTime();
      final Date[] range = new Date[] { startDate, endDate };
      return range;
   }

}
