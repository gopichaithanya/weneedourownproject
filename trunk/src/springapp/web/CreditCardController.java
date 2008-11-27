package springapp.web;

import hibernate.*;
import hibernate.Itinerary.ESeatClass;
import hibernate.manager.*;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * CreditCardController is a Spring controller for the credit card page.
 * 
 * @author Israa Taha
 */
public class CreditCardController extends SimpleFormController {

   public static final String URL = "enterCreditCardInfo.spring";

   /**
    * Submit callback with all parameters.
    * @param request - current servlet request
     * @param response - current servlet response
     * @param command - form object with request parameters bound onto it
     * @param errors - Errors instance without errors (subclass can add errors if it wants to) 
    * @return the ModelAndView object
    */
   protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
         Object command, BindException errors) throws Exception {

      // Check login
      final HttpSession session = request.getSession();
      final String userName = LoginController.getUserName(session);
      if (null == userName)
         return LoginController.redirectToLogin(session, URL);

      final Integer flightNo = (Integer) session.getAttribute(SessionConstants.CREDIT_FLIGHT_NO);
      session.removeAttribute(SessionConstants.CREDIT_FLIGHT_NO);

      final Itinerary it = (Itinerary) command;
      final Flight f = FlightManager.getFlight(flightNo);
      final ESeatClass seatClass = ESeatClass.get(it.getSeatClass());
      final Integer numOfSeats = it.getNumOfSeats();
      logger.info("flightNo: " + flightNo);
      logger.info("Flight: " + f);
      logger.info("Itinerary: " + it);

      //update customer's credit card information
      final Customer c = CustomerManager.updateCcNoAndExpiration(it.getCustomer().getUsername(), it
            .getCustomer().getCcNo(), it.getCustomer().getExpiration());
      logger.info("Customer with new Credit info: " + c);

      //create the ticket object
      final Random rand = new Random();
      final String ticketNo = f.getAirline().getCode() + "-" + flightNo + "-"
            + c.getUsername().toUpperCase() + "-" + (rand.nextInt(900) + 100);
      logger.info("Ticket no: " + ticketNo);

      //book the flight once credit card is validated and a ticket is generated
      final boolean bRst = ItineraryManager.book(c.getUsername(), flightNo, seatClass, numOfSeats,
            ticketNo);
      logger.info("Booking result: " + bRst);

      session.setAttribute(SessionConstants.TICKET, ticketNo);

      final ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));
      return mv;
   }

   /**
    * Retrieve a backing object for the current form from the given request
    * @param request - current HTTP request
    * @return the backing object 
    */
   @Override
   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      Itinerary defaultCommandObj = (Itinerary) super.formBackingObject(request);

      final HttpSession session = request.getSession();
      final String userName = LoginController.getUserName(session);
      final List<Itinerary> its = ItineraryManager.getReserved(userName);

      //get the flight number of flight to be booked
      Integer flightNo = null;
      final String param = request.getParameter("flightNo");
      logger.info("Parameter for flightNo: " + param);

      if (null == param) {
         flightNo = (Integer) session.getAttribute(SessionConstants.CREDIT_FLIGHT_NO);
      } else {
         try {
            flightNo = Integer.valueOf(param);
         } catch (NumberFormatException e) {
            logger.info("No flightNo is found.");
         }
         session.setAttribute(SessionConstants.CREDIT_FLIGHT_NO, flightNo);
      }

      if (null != flightNo)
         for (final Itinerary it : its) {
            if (it.getFlight().getFlightNo() == flightNo) {
               defaultCommandObj = it;
               logger.info("Default object is found.");
               break;
            }
         }

      return defaultCommandObj;
   }

   @Override
   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
         throws Exception {

      // Check login
      final HttpSession session = request.getSession();
      final String userName = LoginController.getUserName(session);
      if (null == userName)
         return LoginController.redirectToLogin(session, URL);

      return super.handleRequest(request, response);
   }
}
