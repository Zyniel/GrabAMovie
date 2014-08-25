package grabamovie.core;

import grabamovie.utils.LogFormatter;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OCanada
 */
public class OrderProcessor implements Runnable {

    private BlockingQueue<IOrder> orders;
    private IOrderableProcessor itemProcessor;
    private static final Logger LOG = LogFormatter.getLogger(OrderProcessor.class.getName());

    public OrderProcessor(IOrderableProcessor itemProcessor) {
        this.orders = new LinkedBlockingQueue<IOrder>();
        this.itemProcessor = itemProcessor;
    }

    public OrderProcessor(IOrderableProcessor itemProcessor, BlockingQueue<IOrder> orders) {
        this(itemProcessor);
        if (orders != null) {
            this.orders = orders;
        }
    }

    public void addOrder(IOrder order) {
        if (order == null) {
        } else if (!this.orders.add(order)) {
            LOG.log(Level.SEVERE, "Could not add order to process queue.");
        } else {
            LOG.log(Level.INFO, "Order added successfully to processor.");
        }
    }

    public IOrderableProcessor getItemProcessor() {
        return itemProcessor;
    }

    public void setItemProcessor(IOrderableProcessor itemProcessor) {
        this.itemProcessor = itemProcessor;
    }

    @Override
    public void run() {
        LOG.log(Level.INFO, "RUN !");
        if (itemProcessor == null || "".equals(itemProcessor.getName())) {
            LOG.log(Level.SEVERE, "Item Processor has not been properly initialized.");
        } else {
            IOrder order;
            while ((order = this.orders.poll()) != null) {
                try {
                    LOG.log(Level.INFO, "Processing order: {0}", order.getId());
                    order.process(itemProcessor);
                    LOG.log(Level.INFO, "Ordered status: {0}", order.getStatus().getDescription());
                } catch (OrderProcessingException ex) {
                    LOG.log(Level.SEVERE, "An error occured while processing orders.", ex);
                    LOG.log(Level.INFO, "Ordered status: {0}", order.getStatus().getDescription());
                }
            }

        }
    }
}
