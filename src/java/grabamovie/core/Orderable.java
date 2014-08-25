/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author OCanada
 */
public abstract class Orderable implements IOrderable {
    @XmlAttribute (name="id")
    private String id;
    @XmlElement (name="name")
    private String name;
    
    private OrderableProcessStatus status;
    
    public Orderable () {
        this.status = OrderableProcessStatus.PENDING;
    }
    
    public Orderable (String id, String name) {
        this();
        this.id = id;
        this.name = name;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }    
    
    @Override
    public String getName(){
        return this.name;
    }    
    
    @Override
    public OrderableProcessStatus getStatus(){
        return this.status;
    }
    
    @Override
    public void setStatus(OrderableProcessStatus status){
        this.status = status;
    } 
    
}
