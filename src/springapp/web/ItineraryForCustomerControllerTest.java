package springapp.web;

import static org.junit.Assert.*;
import hibernate.Flight;

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
      final List<Flight> reserved = (List<Flight>) mv.getModel().get("reservedFlights");
      assertNotNull(reserved);
      final List<Flight> booked = (List<Flight>) mv.getModel().get("bookedFlights");
      assertNotNull(booked);
      final List<Flight> canceled = (List<Flight>) mv.getModel().get("canceledFlights");
      assertNotNull(canceled);
   }
}
