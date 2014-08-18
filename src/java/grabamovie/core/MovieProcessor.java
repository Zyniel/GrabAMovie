/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
    public void process(Movie movie) throws Exception {
        try{
            preProcess(movie);
            internalProcess(movie);
            postProcess(movie);            
        } catch (Exception e) {
            errorProcess(movie, e);
        }
    }
    
    /**
     * Main processing routine. Every action to handle movie should be done here.
     * @param movie Currently processed movie.
     */
    protected abstract void internalProcess (Movie movie) throws Exception;
    
    /**
     * Main post-processing routine. 
     * @param movie Currently processed movie.
     */    
    protected abstract void postProcess (Movie movie) throws Exception;
    
    /**
     * Main post-processing routine.
     * @param movie Currently processed movie.
     */    
    protected abstract void preProcess (Movie movie) throws Exception;
    
    /**
     * Main error routine. Every action on error/exception should be done here.
     * @param movie Currently processed movie.
     */    
    protected abstract void errorProcess (Movie movie, Exception e) throws Exception;

}
