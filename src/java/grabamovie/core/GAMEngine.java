package grabamovie.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OCanada
 */
public class GAMEngine implements Runnable {

    private LinkedBlockingQueue<Order> remainingOrders;
    private MovieProcessor movieProcessor;
    private boolean doRun = false;
    private Thread t;
    private static final Logger LOG = Logger.getLogger(GAMEngine.class.getName());

    public GAMEngine(MovieProcessor movieProcessor) {
        remainingOrders = new  LinkedBlockingQueue<Order>();
        this.movieProcessor = movieProcessor;
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
            Order currentOrder = null;
            try {
                currentOrder = remainingOrders.poll();
                if (currentOrder != null) {
                    LOG.log(Level.INFO, "Processing order: {0}", currentOrder.getId());
                    

                }
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, "An error occured while processing orders.", ex);
                // TODO: Implement max retries
                // Retry order processing later
                if (currentOrder != null) {
                    boolean setRetry = remainingOrders.offer(currentOrder);
                }
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
