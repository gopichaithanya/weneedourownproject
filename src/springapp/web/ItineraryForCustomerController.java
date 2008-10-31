package springapp.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ItineraryForCustomerController implements Controller {
   
   public static final String URL = "itineraryForCustomer.spring";

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      final ModelAndView mv = new ModelAndView("itineraryForCustomer");
      return mv;
   }
}
