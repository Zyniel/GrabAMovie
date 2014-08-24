/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.core;

/**
 * States the progression of the order's preparation
 */
public enum OrderStatus {
    PREPARED,
    PREPARED_PARTIAL,
    CANCELLED,
    RETRIABLE,
    PROCESSING,
    UNHANDLED
}