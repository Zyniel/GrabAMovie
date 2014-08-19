/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.core;

/**
 *
 * @author OCanada
 */
public class OrderCancelledException extends Exception {
    public OrderCancelledException() {
        super("Order has been cancelled.");
    }
    public OrderCancelledException(String message) {
        super(message);
    }
}