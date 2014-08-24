/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.core;

import java.util.List;

/**
 *
 * @author OCanada
 */
public interface IOrder {
    
    public String getId();
    
    public OrderStatus getStatus();
    
    public List<IOrderable> getItems();
    
    public void process(IOrderableProcessor movieProcessor) throws OrderProcessingException;
            

    
}
