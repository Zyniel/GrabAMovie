/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.server;

import grabamovie.core.GAMEngine;
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
        gam = new GAMEngine();
        gam.start();
        
        // Register GAMEngine as Context attribute
        ctx.setAttribute("GAMEngine", gam);
   }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        LOG.log(Level.INFO,"GAM Servlet Destroyed");
        gam.stop();
    }
}
