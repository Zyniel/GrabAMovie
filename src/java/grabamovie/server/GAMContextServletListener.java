package grabamovie.server;

import grabamovie.core.OrderProcessor;
import grabamovie.core.FileCopyProcessor;
import grabamovie.utils.LogFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author OCanada
 */
@WebListener()
public class GAMContextServletListener implements ServletContextListener {
    private static final Logger LOG = LogFormatter.getLogger(GAMContextServletListener.class.getName());
    private OrderProcessor gam; 
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        
        LOG.log(Level.INFO,"GAM Servlet Initialized");
        
        // Démarrer le processus
        FileCopyProcessor hddprocessor;
        try {
            //TODO: Insert loading processor and configuration from file
            gam = new OrderProcessor();
            hddprocessor = new FileCopyProcessor("D:\\tmp3");
            gam.setItemProcessor(hddprocessor);
            gam.start();    
            
            // Register OrderProcessor as Context attribute
            ctx.setAttribute("GAMEngine", gam);   
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Could not initialize GAMEngine.", ex);
        }
   }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        LOG.log(Level.INFO,"GAM Servlet Destroyed");
        gam.stop();
    }
    
    
    
}
