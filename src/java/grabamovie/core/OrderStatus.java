/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.core;

/**
 * States the progression of the order's preparation
 */
public enum OrderStatus {

    PREPARED("Fully Prepared: All items are processed."),
    PREPARED_PARTIAL("Partially Prepared: Some items are missing."),
    CANCELLED("Cancelled"),
    PROCESSING("In Process..."),
    PENDING("Pending...");
    
    private final String desc;

    private OrderStatus(String desc) {
        this.desc = desc;
    }

    public String getDescription() {
        return desc;
    }
}