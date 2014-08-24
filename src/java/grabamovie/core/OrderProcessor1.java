//package grabamovie.core;
//
//import grabamovie.utils.LogFormatter;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author OCanada
// */
//public class OrderProcessor1 implements Runnable {
//
//    private LinkedBlockingQueue<IOrder> remainingOrders;
//    private IOrderableProcessor itemProcessor;
//    private static final Logger LOG = LogFormatter.getLogger(OrderProcessor1.class.getName());
//
//    public OrderProcessor1() {
//        remainingOrders = new LinkedBlockingQueue<IOrder>();
//    }
//    
//    public OrderProcessor1 (IOrderableProcessor itemProcessor) {
//        this();
//        this.itemProcessor = itemProcessor;
//    }
//
//    public IOrderableProcessor getItemProcessor() {
//        return itemProcessor;
//    }
//
//    public void setItemProcessor(IOrderableProcessor itemProcessor) {
//        this.itemProcessor = itemProcessor;
//    }
//
//    public void addOrder(IOrder order) {
//        if (!remainingOrders.contains(order)) {
//            remainingOrders.add(order);
//        }
//    }
//
//    @Override
//    public void run() {
//        LOG.log(Level.INFO, "RUN !");
//        if (itemProcessor == null || "".equals(itemProcessor.getName())) {
//            LOG.log(Level.SEVERE, "Item Processor has not been properly initialized.");
//            return;
//        }
//        IOrder currentOrder = null;
//        try {
//            currentOrder = remainingOrders.poll();
//            if (currentOrder != null) {
//                LOG.log(Level.INFO, "Processing order: {0}", currentOrder.getId());
//                currentOrder.process(itemProcessor);
//                LOG.log(Level.INFO, "Ordered status: {0}", currentOrder.getStatus().getDescription());
//            }
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//            LOG.log(Level.SEVERE, "An critical error occured in program.", ex);
//        } catch (OrderProcessingException ex) {
//            LOG.log(Level.SEVERE, "An error occured while processing orders.", ex);
//            LOG.log(Level.INFO, "Ordered status: {0}", currentOrder.getStatus().getDescription());
//        }
//    }
//}
