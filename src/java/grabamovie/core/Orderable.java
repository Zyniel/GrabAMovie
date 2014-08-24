/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author OCanada
 */
@XmlTransient
public abstract class Orderable implements IOrderable {
    @XmlAttribute (name="id")
    private String id;
    @XmlElement (name="name")
    private String name;
    
    public Orderable () {}
    
    public Orderable (String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }    
    
}
