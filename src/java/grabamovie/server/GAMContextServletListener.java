package grabamovie.server;

import grabamovie.core.OrderProcessor;
import grabamovie.core.FileCopyProcessor;
import grabamovie.utils.LogFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
    private final Logger LOG = LogFormatter.getLogger(GAMContextServletListener.class.getName());
    private ScheduledExecutorService service;
    private OrderProcessor gam;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        
        LOG.log(Level.INFO,"GAM Servlet Initialized");
        
        // Démarrer le processus
        FileCopyProcessor hddprocessor;
        try {
            service = Executors.newSingleThreadScheduledExecutor();
            //TODO: Insert loading processor and configuration from file
            gam = new OrderProcessor(service);
            
            // Register OrderProcessor as Context attribute
            service.scheduleAtFixedRate(gam, 0, 1000, TimeUnit.MILLISECONDS);
             
            hddprocessor = new FileCopyProcessor("D:\\tmp3");
            gam.setItemProcessor(hddprocessor);
            //gam.start();    
            
            // Register o
            ctx.setAttribute("GAMEngine", gam);   
            ctx.setAttribute("Service", service);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Could not initialize GAMEngine.", ex);
        }
   }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        ctx.removeAttribute("GAMEngine");
        ctx.removeAttribute("Service");
        
        gam.stop();
        service.shutdownNow();

        LOG.log(Level.INFO,"GAM Servlet Destroyed");
    } 
}
