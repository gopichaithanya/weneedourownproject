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

      final HttpSession session = request.getSession();

      //update customer's credit card information
      final Itinerary it = (Itinerary) command;
      final int flightNo = it.getFlight().getFlightNo();
      final Customer c = CustomerManager.updateCcNoAndExpiration(it.getCustomer().getUsername(), it
            .getCustomer().getCcNo(), it.getCustomer().getExpiration());
      final ESeatClass seatClass = ESeatClass.get(it.getSeatClass());
      final Integer numOfSeats = it.getNumOfSeats();

      //create the ticket object
      String ticket;

      final Random rand = new Random();
      Flight f = FlightManager.getFlight(flightNo);
      ticket = f.getAirline().getCode() + "-" + flightNo + "-" + c.getUsername().toUpperCase()
            + "-" + (rand.nextInt(900) + 100);
      System.out.println(f.getAirline().getCode() + "-" + flightNo + "-" + c.getUsername() + "-"
            + (rand.nextInt(900) + 100));

      //book the flight once credit card is validated and a ticket is generated
      ItineraryManager.book(c.getUsername(), flightNo, seatClass, numOfSeats, ticket);

      session.setAttribute(SessionConstants.TICKET, ticket);
      ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));
      //mv.addObject("ticket", ticket);
      return mv;
   }

   /**
    * Retrieve a backing object for the current form from the given request
    * @param request - current HTTP request
    * @return the backing object 
    */
   @Override
   protected Object formBackingObject(HttpServletRequest request) throws Exception {
      Itinerary defaultCommandObj = new Itinerary();
      final HttpSession session = request.getSession();
      final String userName = LoginController.getUserName(session);
      final List<Itinerary> its = ItineraryManager.getReserved(userName);

      //get the flight number of flight to be booked
      Integer flightNo = null;
      try {
         flightNo = Integer.valueOf(request.getParameter("flightNo"));
      } catch (NumberFormatException e) {
      }

      if (null != flightNo)
         for (final Itinerary it : its) {
            if (it.getFlight().getFlightNo() == flightNo) {
               defaultCommandObj = it;
               break;
            }
         }

      return defaultCommandObj;
   }
}
