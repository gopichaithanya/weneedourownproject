package springapp.web;

import hibernate.Flight;
import hibernate.manager.AirportManager;
import hibernate.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

@SuppressWarnings("unchecked")
public class FlightSearchForCustomerFormController extends SimpleFormController {

   public static final String URL = "flightSearchForCustomerForm.spring";

   /**
    * This is for debugging purpose.
    * If this is false, all of flights will be shown; this is for debugging.
    * If this is true, flight searching will work.
    */
   static boolean bFlagSearch = false;

   public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
         throws Exception {

      final ModelAndView mv = super.handleRequest(req, resp);

      final List<String[]> airports = AirportManager.getAirportCodeAndName();
      mv.addObject("airports", airports);
      mv.addObject("tripTypes", FlightSearchForCustomer.ETripType.values());

      return mv;
   }

   protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      if (null == command)
         return new ModelAndView(this.getFormView());

      ModelAndView mv = null;
      {
         final FlightSearchForCustomer cmd = ((FlightSearchForCustomer) command);
         if (false == cmd.isNullDepartFlightNo()
               && (cmd.isOneWayTrip() || false == cmd.isNullReturnFlightNo())) {
            // after step 3 with round trip or after step 2 with one way trip
            mv = onSubmit3(request, response, command, errors);
         } else if (false == cmd.isNullDepartFlightNo()) {
            // after step 2
            mv = onSubmit2(request, response, command, errors);
         } else {
            // after step 1
            mv = onSubmit1(request, response, command, errors);
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
         flights = getFlightList(null, null, 0, 0, 0, 0, 0);

      final ModelAndView mv = new ModelAndView(this.getFormView());
      mv.addObject("searchedDepartFlights", flights);
      mv.addObject("isOneWayTrip", cmd.isOneWayTrip());
      mv.addObject("isRoundTrip", cmd.isRoundTrip());
      return mv;
   }

   private ModelAndView onSubmit2(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final FlightSearchForCustomer cmd = ((FlightSearchForCustomer) command);
      List flights;
      if (bFlagSearch)
         flights = getFlightList(cmd.getArrivalLocation(), cmd.getDepartLocation(), cmd
               .getReturnYear(), cmd.getReturnMonth(), cmd.getReturnDay(), cmd.getReturnHour(), cmd
               .getSearchingHourRange());
      else
         flights = getFlightList(null, null, 0, 0, 0, 0, 0);

      final ModelAndView mv = new ModelAndView(this.getFormView());
      mv.addObject("searchedReturnFlights", flights);
      final FlightSearchForCustomerResult departFlight = getFlight(cmd.getDepartFlightNo());
      mv.addObject("selectedDepartFlight", departFlight);
      return mv;
   }

   private ModelAndView onSubmit3(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      final HttpSession session = request.getSession();
      final FlightSearchForCustomer cmd = ((FlightSearchForCustomer) command);

      int[] flights = null;
      if (cmd.isOneWayTrip())
         flights = new int[] { cmd.getDepartFlightNo() };
      else if (cmd.isRoundTrip())
         flights = new int[] { cmd.getDepartFlightNo(), cmd.getReturnFlightNo() };

      session.setAttribute(SessionConstants.RESERVE_FLIGHTS_FOR_CUSTOMER, flights);

      final ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));
      return mv;
   }

   public static FlightSearchForCustomerResult getFlight(int flightNo) {
      final int fno = Integer.valueOf(flightNo);
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      final Flight flight = (Flight) session.load(Flight.class, fno);
      final FlightSearchForCustomerResult result = new FlightSearchForCustomerResult(flight);
      session.close();
      return result;
   }

   public static List<FlightSearchForCustomerResult> getFlightList(String departLocation,
         String arrivalLocation, int year, int month, int day, int hour, int hourRange) {

      final List<FlightSearchForCustomerResult> result = new ArrayList<FlightSearchForCustomerResult>();

      final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();

      Query q = null;
      {
         final Date[] departureTimeRange = getTimeRange(year, month, day, hour, hourRange);
         if (null == departLocation || null == arrivalLocation || null == departureTimeRange) {
            q = session.createQuery("FROM Flight");
         } else {
            q = session.createQuery(
                  "FROM Flight " + "WHERE DEPARTURE_LOCATION = ? " + "AND ARRIVAL_LOCATION = ? "
                        + "AND DEPARTURE_TIME > ? " + "AND DEPARTURE_TIME < ? ").setString(0,
                  departLocation).setString(1, arrivalLocation).setTimestamp(2,
                  departureTimeRange[0]).setTimestamp(3, departureTimeRange[1]);
         }
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

   public static Date[] getTimeRange(int year, int month, int day, int hour, int searchingHourRange) {
      if (month <= 0 || month > 12)
         return null;
      if (day <= 0 || day > 31)
         return null;

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
