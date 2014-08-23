package grabamovie.server;

import grabamovie.core.GAMEngine;
import grabamovie.core.HDDMovieProcessor;
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
    private static final Logger LOG = Logger.getLogger(GAMContextServletListener.class.getName());
    private GAMEngine gam; 
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        
        LOG.log(Level.INFO,"GAM Servlet Initialized");
        
        // Démarrer le processus
        HDDMovieProcessor hddprocessor;
        try {
            //TODO: Insert loading processor and configuration from file
            gam = new GAMEngine();
            hddprocessor = new HDDMovieProcessor("D:\\tmp3");
            gam.start();    
            
            // Register GAMEngine as Context attribute
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
