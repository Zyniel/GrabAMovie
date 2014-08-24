package grabamovie.core;

/**
 *
 * @author OCanada
 */
public abstract class OrderableProcessor implements IOrderableProcessor{
    protected String processorName;

    public OrderableProcessor() {
        processorName = this.getClass().getSimpleName();
    }

    @Override
    /**
     * Processing routine with pre-processing, processing, post-processing and 
     * processing-error handling.
     */
    public void process(IOrderable item, IOrder order) throws Exception {
        try{
            preProcess(item, order);
            internalProcess(item, order);
            postProcess(item, order);            
        } catch (Exception e) {
            errorProcess(item, order, e);
        }
    }
    
    public String getName() {
        return processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }    
    
    /**
     * Main processing routine. Every action to handle movie should be done here.
     * @param movie Currently processed movie.
     */
    protected abstract void internalProcess (IOrderable item, IOrder order) throws Exception;
    
    /**
     * Main post-processing routine. 
     * @param movie Currently processed movie.
     */    
    protected abstract void postProcess (IOrderable item, IOrder order) throws Exception;
    
    /**
     * Main post-processing routine.
     * @param movie Currently processed movie.
     */    
    protected abstract void preProcess (IOrderable item, IOrder order) throws Exception;
    
    /**
     * Main error routine. Every action on error/exception should be done here.
     * @param movie Currently processed movie.
     */    
    protected abstract void errorProcess (IOrderable item, IOrder order, Exception e) throws Exception;

}
