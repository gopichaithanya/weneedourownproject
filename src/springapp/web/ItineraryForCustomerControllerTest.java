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
import org.springframework.test.web.*;
import org.springframework.mock.web.*;

@SuppressWarnings("unchecked")
public class ItineraryForCustomerControllerTest {

   @Test
   public void testHandleRequest() throws ServletException, IOException {
      final HttpServletRequest req = new MockHttpServletRequest();
      final HttpServletResponse resp = new MockHttpServletResponse();
      req.getSession().setAttribute(SessionConstants.USERNAME, "jjohnson");

      final ItineraryForCustomerController ctl = new ItineraryForCustomerController();
      final ModelAndView mv = ctl.handleRequest(req, resp);
      assertNotNull(mv);

      final List<Itinerary> reserved = (List<Itinerary>) ModelAndViewAssert
            .assertAndReturnModelAttributeOfType(mv, "reservedItinerary", List.class);
      assertNotNull(reserved);
      assertFalse(reserved.isEmpty());

      final List<Itinerary> booked = (List<Itinerary>) ModelAndViewAssert
            .assertAndReturnModelAttributeOfType(mv, "bookedItinerary", List.class);
      assertNotNull(booked);
      assertFalse(booked.isEmpty());

      final List<Itinerary> canceled = (List<Itinerary>) ModelAndViewAssert
            .assertAndReturnModelAttributeOfType(mv, "canceledItinerary", List.class);
      assertNotNull(canceled);
      assertFalse(canceled.isEmpty());
   }

   @Test
   public void testHandleRequestNoLogin() {
   }
}
