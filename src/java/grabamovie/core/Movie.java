package grabamovie.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OCanada
 */
@XmlRootElement(name = "movie")
@XmlAccessorType (XmlAccessType.NONE)
public class Movie extends Orderable{
    @XmlElement (name="location")
    private String location;

    /** Empty constructor for JAXB */
    public Movie() {
        super();
    }
    
    public Movie(String id, String name, String location) {
        super(id, name);
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
