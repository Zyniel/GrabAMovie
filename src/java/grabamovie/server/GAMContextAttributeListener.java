package grabamovie.server;

import grabamovie.utils.LogFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class GAMContextAttributeListener implements ServletContextAttributeListener {
    private static final Logger LOG = LogFormatter.getLogger(GAMContextServletListener.class.getName());
    
    @Override
    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {
    	LOG.log(Level.FINEST, "ServletContext attribute added::'{'{0},{1}'}'", new Object[]{servletContextAttributeEvent.getName(), servletContextAttributeEvent.getValue()});
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent) {
    	LOG.log(Level.FINEST, "ServletContext attribute replaced::'{'{0},{1}'}'", new Object[]{servletContextAttributeEvent.getName(), servletContextAttributeEvent.getValue()});
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent) {
    	LOG.log(Level.FINEST, "ServletContext attribute removed::'{'{0},{1}'}'", new Object[]{servletContextAttributeEvent.getName(), servletContextAttributeEvent.getValue()});
    }
	
}