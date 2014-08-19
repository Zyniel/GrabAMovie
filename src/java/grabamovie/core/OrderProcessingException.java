/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.core;

/**
 *
 * @author OCanada
 */
public class OrderProcessingException extends Exception {
    public OrderProcessingException() {
        super("Order has been cancelled.");
    }
    public OrderProcessingException(String message) {
        super(message);
    }
}