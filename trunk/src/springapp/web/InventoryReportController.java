package springapp.web;

import hibernate.Flight;
import hibernate.manager.FlightManager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * InventoryReportController is a Spring controller for the inventory page. It retrieves the
 * necessary inventory information to be displayed
 */
public class InventoryReportController extends SimpleFormController {

   public static final String URL = "inventory.spring";

   /**
    * Process the request and return a ModelAndView object which the DispatcherServlet will render.
    * @return the ModelAndView object for the inventory page
    */
   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
         throws Exception {

      final HttpSession session = request.getSession();
      final Object newFlight = session.getAttribute(SessionConstants.ADDED_NEW_FLIGHT);
      session.removeAttribute(SessionConstants.ADDED_NEW_FLIGHT);
      final Object canceledFlightNo = session.getAttribute(SessionConstants.CANCELED_FLIGHT_NO);
      session.removeAttribute(SessionConstants.CANCELED_FLIGHT_NO);

      int emptyEconomySeats = 0;
      int emptyBusinessSeats = 0;

      final List<Flight> flight = FlightManager.listFlights();
      for (int i = 0; i < flight.size(); i++) {
         emptyEconomySeats += flight.get(i).getEconomySeats();
         emptyBusinessSeats += flight.get(i).getBusinessSeats();
      }

      final ModelAndView mv = super.handleRequest(request, response);
      mv.addObject("flightList", flight);
      mv.addObject("emptyEconomySeats", emptyEconomySeats);
      mv.addObject("emptyBusinessSeats", emptyBusinessSeats);
      if (null != newFlight)
         mv.addObject("newFlight", newFlight);
      if (null != canceledFlightNo)
         mv.addObject("canceledFlightNo", canceledFlightNo);
      return mv;
   }
}