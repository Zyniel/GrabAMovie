/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.core;

import java.util.List;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.Source;

/**
 * Class representing a movie Order, identified by a technical Id. It is
 * optionally labelled by a owner name and contains a possibly empty Movie list.
 * Each movie is meant to be processed in the order at further step.
 *
 * @author OCanada
 */
@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.NONE)
public class Order {
    @XmlAttribute(name="id")
    private String id;
    @XmlElement(name="owner")
    private String owner;

    @XmlElement(name = "movie")
    @XmlElementWrapper( name="movies" )
    private List<Movie> movieList = null;
    private Boolean isProcessed;
    private static final Logger LOG = Logger.getLogger(Order.class.getName());

    /**
     * Empty constructor for JAXB
     */
    public Order() {
        this.isProcessed = false;
    }

    public Order(String id, String owner, List<Movie> movieList) throws Exception {
        // Checks on ownername
        setId(id);

        // No checks on ownername (Can be empty)
        this.owner = owner;
        // No checks on movie list (Can be empty)
        this.movieList = movieList;
        this.isProcessed = false;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public Boolean getIsProcessed() {
        return isProcessed;
    }

    public void setId(String id) throws Exception {
        if (id != null) {
            this.id = id;
        } else {
            throw new Exception("Order Id is empty");
        }
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public static Order parseXML(Source source) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Order order = (Order) jaxbUnmarshaller.unmarshal(source);
        return order;
    }
}
