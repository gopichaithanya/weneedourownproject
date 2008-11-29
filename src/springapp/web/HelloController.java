package springapp.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Date;

/**
 * A simple example controller that shows how Spring Framework works.
 */
public class HelloController implements Controller {

   private Logger logger = Logger.getLogger(getClass().getName());

   /**
    * handle the requests from web browsers
    * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      String now = (new Date()).toString();
      logger.info("Returning hello view with " + now);

      return new ModelAndView("hello", "now", now);
   }

}
