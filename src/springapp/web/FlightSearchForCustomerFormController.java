package springapp.web;

import hibernate.Flight;
import hibernate.manager.AirportManager;
import hibernate.manager.FlightManager;
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

   /**
    * This is for debugging purpose.
    * If this is false, all of flights will be shown; this is for debugging.
    * If this is true, flight searching will work.
    */
   public static boolean bFlagSearch = false;

   public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
         throws Exception {

      final ModelAndView mv = super.handleRequest(req, resp);

      final List<String[]> airports = AirportManager.getAirportCodeAndName();
      mv.addObject("airports", airports);

      return mv;
   }

   protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      if (null == command)
         return new ModelAndView(this.getFormView());

      ModelAndView mv = null;
      {
         final FlightSearchForCustomer cmd = ((FlightSearchForCustomer) command);
         if (cmd.getDepartFlightNo().length() > 0
               && (cmd.getTripType().equalsIgnoreCase(FlightSearchForCustomer.KEYWORD_oneWayTrip) || cmd
                     .getReturnFlightNo().length() > 0)) {
            // after step 3 with round trip or after step 2 with one way trip
            mv = onSubmit3(request, response, command, errors);
         } else if (cmd.getDepartFlightNo().length() == 0) {
            // after step 1
            mv = onSubmit1(request, response, command, errors);
         } else {
            // after step 2
            mv = onSubmit2(request, response, command, errors);
         }
      }
      mv.addObject(this.getCommandName(), command);
      return mv;
   }

   private ModelAndView onSubmit1(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final FlightSearchForCustomer cmd = ((FlightSearchForCustomer) command);
      List flights;
      if (bFlagSearch)
         flights = getFlightList(cmd.getDepartLocation(), cmd.getArrivalLocation(), cmd
               .getDepartYear(), cmd.getDepartMonth(), cmd.getDepartDay(), cmd.getDepartHour(), cmd
               .getSearchingHourRange());
      else
         flights = getFlightList(null, null, null, null, null, null, null);

      final ModelAndView mv = new ModelAndView(this.getFormView());
      mv.addObject("searchedDepartFlights", flights);
      mv.addObject("isOneWayTrip", cmd.getTripType().equalsIgnoreCase(
            FlightSearchForCustomer.KEYWORD_oneWayTrip));
      mv.addObject("isRoundTrip", cmd.getTripType().equalsIgnoreCase(
            FlightSearchForCustomer.KEYWORD_roundTrip));
      return mv;
   }

   private ModelAndView onSubmit2(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final FlightSearchForCustomer cmd = ((FlightSearchForCustomer) command);
      List flights;
      if (bFlagSearch)
         flights = getFlightList(cmd.getDepartLocation(), cmd.getArrivalLocation(), cmd
               .getReturnYear(), cmd.getReturnMonth(), cmd.getReturnDay(), cmd.getReturnHour(), cmd
               .getSearchingHourRange());
      else
         flights = getFlightList(null, null, null, null, null, null, null);

      final ModelAndView mv = new ModelAndView(this.getFormView());
      mv.addObject("searchedReturnFlights", flights);
      final FlightSearchForCustomerResult departFlight = getFlight(cmd.getDepartFlightNo());
      mv.addObject("selectedDepartFlight", departFlight);
      return mv;
   }

   private ModelAndView onSubmit3(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final ModelAndView mv = super.onSubmit(request, response, command, errors);
      return mv;
   }

   public static FlightSearchForCustomerResult getFlight(String flightNo) {
      final int fno = Integer.valueOf(flightNo);
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      final Flight flight = (Flight) session.load(Flight.class, fno);
      final FlightSearchForCustomerResult result = new FlightSearchForCustomerResult(flight);
      session.close();
      return result;
   }

   public static List<FlightSearchForCustomerResult> getFlightList(String departLocation,
         String arrivalLocation, String year, String month, String day, String hour,
         String hourRange) {

      final List<FlightSearchForCustomerResult> result = new ArrayList<FlightSearchForCustomerResult>();

      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();

      Query q = null;
      try {
         final Date[] departureTimeRange = getTimeRange(year, month, day, hour, hourRange);
         q = session.createQuery(
               "FROM Flight " + "WHERE DEPARTURE_LOCATION = ? " + "AND ARRIVAL_LOCATION = ? "
                     + "AND DEPARTURE_TIME > ? " + "AND DEPARTURE_TIME < ? ").setString(0,
               departLocation).setString(1, arrivalLocation).setTimestamp(2, departureTimeRange[0])
               .setTimestamp(3, departureTimeRange[1]);
      } catch (NumberFormatException e) {
         q = session.createQuery("FROM Flight");
      }
      final Iterator<Flight> iter = q.list().iterator();

      while (iter.hasNext()) {
         final Flight f = iter.next();
         final FlightSearchForCustomerResult flight = new FlightSearchForCustomerResult(f);
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
