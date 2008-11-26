package springapp.web;

import static org.junit.Assert.*;
import hibernate.Itinerary;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

@SuppressWarnings("unchecked")
public class ItineraryForCustomerControllerTest {

   @Test
   public void testHandleRequest() throws ServletException, IOException {
      final HttpServletRequest req = new NullHttpServletRequest();
      final HttpServletResponse resp = new NullHttpServletResponse();
      req.getSession().setAttribute(SessionConstants.USERNAME, "jjohnson");

      final ItineraryForCustomerController ctl = new ItineraryForCustomerController();
      final ModelAndView mv = ctl.handleRequest(req, resp);

      final List<Itinerary> reserved = (List<Itinerary>) mv.getModel().get("reservedItinerary");
      assertNotNull(reserved);
      assertFalse(reserved.isEmpty());

      final List<Itinerary> booked = (List<Itinerary>) mv.getModel().get("bookedItinerary");
      assertNotNull(booked);
      assertFalse(booked.isEmpty());

      final List<Itinerary> canceled = (List<Itinerary>) mv.getModel().get("canceledItinerary");
      assertNotNull(canceled);
      assertFalse(canceled.isEmpty());
   }
   
   @Test
   public void testHandleRequestNoLogin() {
   }
}
