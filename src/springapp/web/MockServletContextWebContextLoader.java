package springapp.web;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.support.AbstractContextLoader;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * This Servlet context loader is only for testing.
 * This loader loads up the XML setting file of Spring Framework without Web containers.
 */
@SuppressWarnings("unchecked")
public class MockServletContextWebContextLoader extends AbstractContextLoader {

   @Override
   protected String getResourceSuffix() {
      return "-servlet.xml";
   }

   public final ConfigurableApplicationContext loadContext(String... locations) throws Exception {
      //System.out.println(System.getProperty("java.class.path"));
      //System.out.println(System.getProperty("user.dir"));
      final ConfigurableWebApplicationContext context = new XmlWebApplicationContext();
      context.setServletContext(new MockServletContext(new FileSystemResourceLoader()));
      context.setConfigLocations(locations);
      context.refresh();
      AnnotationConfigUtils.registerAnnotationConfigProcessors((BeanDefinitionRegistry) context
            .getBeanFactory());
      context.registerShutdownHook();
      return context;
   }

   @Override
   protected String[] modifyLocations(final Class clazz, final String... locations) {
      return locations;
   }
}
