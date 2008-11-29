package springapp.web;

import hibernate.*;
import hibernate.manager.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * CreditCardController is a Spring controller for the credit card page.
 */
public class CreditCardController extends SimpleFormController {

   /**
    * parameter name for flight number.
    */
   public static final String PARAM_FLIGHT_NO = "flightNo";

   /**
    * URL for bean
    */
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
      /*
       * The credit card information has to be validated first before booking.
       * The validation is done via a validation service, which will be provided by the instructor.
       */

      final HttpSession session = request.getSession();
      final Integer flightNo = (Integer) session.getAttribute(SessionConstants.CREDIT_FLIGHT_NO);
      if (null == flightNo)
         return new ModelAndView(new RedirectView(ItineraryForCustomerController.URL));
      session.removeAttribute(SessionConstants.CREDIT_FLIGHT_NO);
      logger.info("flightNo: " + flightNo);

      final Itinerary it = (Itinerary) command;
      final Flight f = FlightManager.getFlight(flightNo);
      logger.info("Flight: " + f);
      logger.info("Itinerary: " + it);

      //update customer's credit card information
      final Customer c = CustomerManager.updateCcNoAndExpiration(it.getCustomer().getUsername(), it
            .getCustomer().getCcNo(), it.getCustomer().getExpiration());
      logger.info("Customer with new Credit info: " + c);

      //book the flight once credit card is validated and a ticket is generated
      final String ticketNo = ItineraryManager.getTicketNum(f, c.getUsername());
      logger.info("Ticket no: " + ticketNo);
      final boolean bRst = ItineraryManager.book(c.getUsername(), flightNo, ticketNo);
      logger.info("Booking result: " + bRst);

      final String userName = LoginController.getUserName(session);
      final List<Itinerary> bookedIts = ItineraryManager.getBooked(userName);
      for (final Itinerary bookedIt : bookedIts)
         if (bookedIt.getFlight().getFlightNo() == flightNo)
            session.setAttribute(SessionConstants.BOOKED_ITINERARY, bookedIt);

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
      final Customer dummyCustomer = new Customer();
      final Flight dummyFlight = new Flight();
      defaultCommandObj.setCustomer(dummyCustomer);
      defaultCommandObj.setFlight(dummyFlight);

      final HttpSession session = request.getSession();
      final String userName = LoginController.getUserName(session);
      final List<Itinerary> its = ItineraryManager.getReserved(userName);

      final Integer flightNo = getFlightNoFromParam(request);
      if (null == flightNo)
         logger.info("No flightNo is found.");

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

   /**
    * get the flight number of flight to be booked
    * @param request Http servlet request object
    * @return flight number
    */
   private static Integer getFlightNoFromParam(HttpServletRequest request) {
      final HttpSession session = request.getSession();
      final String param = request.getParameter("flightNo");

      Integer flightNo = null;
      if (null == param) {
         flightNo = (Integer) session.getAttribute(SessionConstants.CREDIT_FLIGHT_NO);
      } else {
         try {
            flightNo = Integer.valueOf(param);
         } catch (NumberFormatException e) {
         }
         if (null != flightNo)
            session.setAttribute(SessionConstants.CREDIT_FLIGHT_NO, flightNo);
      }
      return flightNo;
   }

   /**
    * Handle the requests from clients.
    * @see org.springframework.web.servlet.mvc.AbstractController#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
   @Override
   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
         throws Exception {

      // Check login
      final HttpSession session = request.getSession();
      final String userName = LoginController.getUserName(session);
      if (null == userName)
         return LoginController.redirectToLogin(session, URL);

      final Integer flightNo = getFlightNoFromParam(request);
      if (null == flightNo)
         return new ModelAndView(new RedirectView(ItineraryForCustomerController.URL));

      final ModelAndView mv = super.handleRequest(request, response);
      mv.addObject("flight", FlightManager.getFlight(flightNo));
      return mv;
   }
}
