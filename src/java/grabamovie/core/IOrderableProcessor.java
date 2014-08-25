package grabamovie.core;

import grabamovie.utils.Subject;

/**
 *
 * @author OCanada
 */
public interface IOrderableProcessor extends Subject{
    
    public String getName();
    
    public void process (IOrderable movie, IOrder order) throws Exception;
}
