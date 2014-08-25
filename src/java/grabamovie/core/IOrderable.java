/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.core;

/**
 *
 * @author OCanada
 */
public interface IOrderable {
    
    public String getName();
    
    public OrderableProcessStatus getStatus();
    
    public void setStatus(OrderableProcessStatus status);
    
}
