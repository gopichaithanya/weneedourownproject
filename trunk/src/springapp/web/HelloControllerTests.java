package springapp.web;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.web.servlet.ModelAndView;

import springapp.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = MockServletContextWebContextLoader.class, locations = { "/xml/springapp-servlet.xml" })
public class HelloControllerTests extends AbstractJUnit4SpringContextTests {

   @Test
   public void testHandleRequestView() throws Exception {
      final HelloController controller = new HelloController();
      final ModelAndView mav = controller.handleRequest(null, null);
      ModelAndViewAssert.assertViewName(mav, "hello");
      ModelAndViewAssert.assertModelAttributeAvailable(mav, "now");
   }
}
