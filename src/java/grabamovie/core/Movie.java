/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OCanada
 */
@XmlRootElement(name = "movie")
@XmlAccessorType (XmlAccessType.NONE)
public class Movie {
    @XmlAttribute (name="id")
    private String id;
    @XmlElement (name="name")
    private String name;
    @XmlElement (name="location")
    private String location;

    /** Empty constructor for JAXB */
    public Movie() {}
    
    public Movie(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
