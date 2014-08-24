package grabamovie.server;

import grabamovie.core.OrderProcessor;
import grabamovie.core.FileCopyProcessor;
import grabamovie.core.IOrderableProcessor;
import grabamovie.utils.LogFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    private ExecutorService service;
    private IOrderableProcessor defaultProcessor;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        
        LOG.log(Level.INFO,"GAM Servlet Initialized");
        try {
            service = Executors.newSingleThreadScheduledExecutor();
            defaultProcessor = new FileCopyProcessor("D:\\tmp3");
            
            // Register 
            ctx.setAttribute("ExecutorService", service);
            ctx.setAttribute("DefaultProcessor", defaultProcessor);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Could not initialize GAMEngine.", ex);
        }
   }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        ctx.removeAttribute("ExecutorService");
        ctx.removeAttribute("DefaultProcessor");
        
        LOG.log(Level.INFO, "Initializing shutdown...");
        service.shutdown();
        service.shutdownNow();
        LOG.log(Level.INFO,"GAM Servlet Destroyed");
    } 
}
