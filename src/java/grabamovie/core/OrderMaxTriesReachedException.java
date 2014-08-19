/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.core;

/**
 *
 * @author OCanada
 */
public class OrderMaxTriesReachedException extends Exception {
    public OrderMaxTriesReachedException() {
        super("Max number of tries reached.");
    }
    public OrderMaxTriesReachedException(String message) {
        super(message);
    }
}