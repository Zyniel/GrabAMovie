package grabamovie.core;

/**
 *
 * @author OCanada
 */
public abstract class MovieProcessor implements IMovieProcessor{
    protected String processorName;
    
    @Override
    /**
     * Processing routine with pre-processing, processing, post-processing and 
     * processing-error handling.
     */
    public void process(Movie movie, Order order) throws Exception {
        try{
            preProcess(movie, order);
            internalProcess(movie, order);
            postProcess(movie, order);            
        } catch (Exception e) {
            errorProcess(movie, order, e);
        }
    }
    
    public String getProcessorName() {
        return processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }    
    
    /**
     * Main processing routine. Every action to handle movie should be done here.
     * @param movie Currently processed movie.
     */
    protected abstract void internalProcess (Movie movie, Order order) throws Exception;
    
    /**
     * Main post-processing routine. 
     * @param movie Currently processed movie.
     */    
    protected abstract void postProcess (Movie movie, Order order) throws Exception;
    
    /**
     * Main post-processing routine.
     * @param movie Currently processed movie.
     */    
    protected abstract void preProcess (Movie movie, Order order) throws Exception;
    
    /**
     * Main error routine. Every action on error/exception should be done here.
     * @param movie Currently processed movie.
     */    
    protected abstract void errorProcess (Movie movie, Order order, Exception e) throws Exception;

}
