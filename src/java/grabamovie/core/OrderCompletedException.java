/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.core;

/**
 *
 * @author OCanada
 */
public class OrderCompletedException extends Exception {
    public OrderCompletedException() {
        super("Order has been cancelled.");
    }
    public OrderCompletedException(String message) {
        super(message);
    }
}