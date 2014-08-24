package grabamovie.core;

/**
 *
 * @author OCanada
 */
public interface IOrderableProcessor {
    
    public String getName();
    
    public void process (IOrderable movie, IOrder order) throws Exception;
}
