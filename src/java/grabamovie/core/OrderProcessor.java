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

    private IOrder order;
    private IOrderableProcessor itemProcessor;
    private static final Logger LOG = LogFormatter.getLogger(OrderProcessor.class.getName());

    public OrderProcessor(IOrderableProcessor itemProcessor, IOrder order) {
        this.order = order;
        this.itemProcessor = itemProcessor;
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
            try {
                if (order != null) {
                    LOG.log(Level.INFO, "Processing order: {0}", order.getId());
                    order.process(itemProcessor);
                    LOG.log(Level.INFO, "Ordered status: {0}", order.getStatus().getDescription());
                }
            } catch (OrderProcessingException ex) {
                LOG.log(Level.SEVERE, "An error occured while processing orders.", ex);
                LOG.log(Level.INFO, "Ordered status: {0}", order.getStatus().getDescription());
            }
        }
    }
}
