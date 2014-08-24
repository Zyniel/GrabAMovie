package grabamovie.core;

import grabamovie.utils.LogFormatter;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OCanada
 */
public class OrderProcessor implements Runnable {

    private LinkedBlockingQueue<IOrder> remainingOrders;
    private IOrderableProcessor itemProcessor;
    private boolean doRun = false;
    private Thread t;
    private static final Logger LOG = LogFormatter.getLogger(OrderProcessor.class.getName());

    public OrderProcessor() {
        remainingOrders = new LinkedBlockingQueue<IOrder>();
    }

    public IOrderableProcessor getItemProcessor() {
        return itemProcessor;
    }

    public void setItemProcessor(IOrderableProcessor itemProcessor) {
        this.itemProcessor = itemProcessor;
    }
    
    public void addOrder(IOrder order) {
        if (!remainingOrders.contains(order)) {
            remainingOrders.add(order);
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
    
    @Override
    public void run() {
        if (itemProcessor == null || "".equals(itemProcessor.getName())) {
            LOG.log(Level.SEVERE, "Movie Processor has not been properly initialized.");
            return;
        }
        while (doRun) {
            IOrder currentOrder = null;
            try {
                currentOrder = remainingOrders.poll();
                if (currentOrder != null) {
                    LOG.log(Level.INFO, "Processing order: {0}", currentOrder.getId());
                    currentOrder.process(itemProcessor);
                }
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, "An critical error occured in program.", ex);
            } catch (OrderProcessingException ex) {
                LOG.log(Level.SEVERE, "An error occured while processing orders.", ex);
            }
        }
    }
}
