package grabamovie.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OCanada
 */
public class GAMEngine implements Runnable {

    private List<Order> remainingOrders;
    private MovieProcessor movieProcessor;
    private Boolean doRun = false;
    private Thread t;
    private static final Logger LOG = Logger.getLogger(GAMEngine.class.getName());

    public GAMEngine(MovieProcessor movieProcessor) {
        try {
            remainingOrders = new ArrayList<Order>();
            this.movieProcessor = movieProcessor;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }

    public MovieProcessor getMovieProcessor() {
        return movieProcessor;
    }

    public void setMovieProcessor(MovieProcessor movieProcessor) {
        this.movieProcessor = movieProcessor;
    }
    
    @Override
    public void run() {
        if (movieProcessor == null || "".equals(movieProcessor.getProcessorName()) ) {
            LOG.log(Level.SEVERE, "Movie Processor has not been properly initialized.");
            return;
        }
        while (doRun) {
            try {
                if (remainingOrders.size() > 0) {
                    Order currentOrder = remainingOrders.get(0);
                    LOG.log(Level.INFO, "Processing order: {0}", currentOrder.getId());
                    
                    // Loop through all movies from the currentOrder
                    Iterator<Movie> iteMovies = currentOrder.getMovieList().iterator();
                    while (iteMovies.hasNext()) {
                        Movie currentMovie = (Movie) iteMovies.next();
                        try {
                            movieProcessor.process(currentMovie, currentOrder);
                        } catch (Exception e) {
                            LOG.log(Level.SEVERE, "Error processing movie: " + currentMovie.getName(), e);
                        }
                    }
                    remainingOrders.remove(0);
                }
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, "An error occured while processing orders.", ex);
            }
        }
    }

    public void stop() {
        if (doRun) {
            doRun = false;
        }
    }

    public void start() {
        if (!doRun) {
            doRun = true;
            t = new Thread(this);
            t.start();
        }
    }
    
    public void addOrder(Order order) {
        if (!remainingOrders.contains(order))
            remainingOrders.add(order);
    }
}
