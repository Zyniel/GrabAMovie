package grabamovie.core;

import static grabamovie.core.Order.MaxTries;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
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
public final class Order {

    private static final Logger LOG = Logger.getLogger(Order.class.getName());
    /**
     * Maximum number of tries allowed to process an Order
     */
    protected static final int MaxTries = 1;
    @XmlAttribute(name = "id")
    /**
     * Order technical identifier
     */
    private String id;
    @XmlElement(name = "owner")
    /**
     * Order optional owner name
     */
    private String owner;
    @XmlElement(name = "movie")
    @XmlElementWrapper(name = "movies")
    private List<Movie> movieList;
    
    private List<Movie> processedMovieList;
    private List<Movie> unprocessedMovieList;
    /**
     * Current order completion status
     */
    private OrderStatus status;
    /**
     * Number of retries done for the current order
     */
    private int tries = 0;
    /**
     * Number of errors during current try
     */
    private int errors = 0;

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

    /**
     * Empty constructor for JAXB
     */
    public Order() {
        this.status = OrderStatus.UNHANDLED;
        this.movieList = new ArrayList<Movie>();
    }

    /**
     * Main constructor
     *
     * @param id Compulsory technical identifier
     * @param owner Order optional owner name
     * @param movieList List movies contained in the Order
     * @throws Exception Exceptions regarding Id
     */
    public Order(String id, String owner, List<Movie> movieList) throws Exception {
        this();
        // Checks on ownername
        setId(id);

        // No checks on ownername (Can be empty)
        this.owner = owner;
        // No checks on movie list (Can be empty)
        this.movieList = movieList;
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

    public OrderStatus getStatus() {
        return status;
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

    /**
     * Parses and XML source into an Order instance
     *
     * @param source Source of the XML
     * @return Order instance
     * @throws JAXBException
     */
    public static Order parseXML(Source source) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Order order = (Order) jaxbUnmarshaller.unmarshal(source);
        return order;
    }

    public void process(IMovieProcessor movieProcessor) throws OrderMaxTriesReachedException, OrderProcessingException {
        boolean done = false;
        do {
            // Check Order Status
            if (this.status == OrderStatus.CANCELLED) 
                throw new OrderProcessingException("Cannot process order. Order has been cancelled.");
            else if (this.status == OrderStatus.PREPARED || this.status == OrderStatus.PREPARED_PARTIAL) 
                throw new OrderProcessingException("Cannot process order. Order has already been processed.");
            
            // Check Retries Status
            if (++this.tries > MaxTries)
                throw new OrderMaxTriesReachedException();
            
            errors = 0;
            // Loop through all movies from the currentOrder
            Iterator<Movie> iteMovies = movieList.iterator();
            while (iteMovies.hasNext()) {
                Movie currentMovie = (Movie) iteMovies.next();
                try {
                    movieProcessor.process(currentMovie, this);
                    processedMovieList.add(currentMovie);
                } catch (Exception e) {
                    LOG.log(Level.SEVERE, "Error processing movie: " + currentMovie.getName(), e);
                    unprocessedMovieList.add(currentMovie);
                    errors++;
                }
            }
            if (errors > 0) {
                this.status = OrderStatus.PREPARED_PARTIAL;
            } else {
                this.status = OrderStatus.PREPARED;
            }
        } while (this.tries <= MaxTries);
    }

    public boolean isCancelled() {
        return (this.status == OrderStatus.CANCELLED);
    }

    public boolean isOnPrepatation() {
        return (this.status == OrderStatus.PROCESSING);
    }

    public boolean isUnhandled() {
        return (this.status == OrderStatus.UNHANDLED);
    }

    public boolean isRetriable() {
        return (this.status == OrderStatus.RETRIABLE);
    }

    public boolean isPrepared() {
        return (this.status == OrderStatus.PREPARED || this.status == OrderStatus.PREPARED_PARTIAL);
    }
    
    
}
