package grabamovie.core;

import grabamovie.utils.LogFormatter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.transform.Source;
import org.eclipse.persistence.jaxb.MarshallerProperties;

/**
 * Class representing a movie Order, identified by a technical Id. It is
 * optionally labelled by a owner name and contains a possibly empty Movie list.
 * Each movie is meant to be processed in the order at further step.
 *
 * @author OCanada
 */
@XmlTransient
public abstract class Order implements IOrder, Comparable {

    protected static final Logger LOG = LogFormatter.getLogger(Order.class.getName());
    /**
     * Order technical identifier
     */
    @XmlAttribute(name = "id")
    protected String id;
    /**
     * Order optional owner name
     */
    @XmlElement(name = "owner")
    protected String owner;
    
    @XmlElement(name = "item")
    @XmlElementWrapper(name = "items")
    @XmlAnyElement(lax=true)   
    protected List<IOrderable> itemList;
    
    protected List<IOrderable> processedItemList;
    protected List<IOrderable> unprocessedItemList;
    /**
     * Current order completion status
     */
    protected OrderStatus status;
    /**
     * Number of errors during current try
     */
    private int errors = 0;

    /**
     * Empty constructor for JAXB
     */
    public Order() {
        this.status = OrderStatus.UNHANDLED;
        this.processedItemList = new ArrayList<IOrderable>();
        this.unprocessedItemList = new ArrayList<IOrderable>();
    }

    /**
     * Main constructor
     *
     * @param id Compulsory technical identifier
     * @param owner Order optional owner name
     * @param movieList List movies contained in the Order
     * @throws Exception Exceptions regarding Id
     */
    public Order(String id, String owner, List<IOrderable> itemList) throws Exception {
        this();
        // Checks on ownername
        setId(id);

        // No checks on ownername (Can be empty)
        this.owner = owner;
        // No checks on movie list (Can be empty)
        this.itemList = itemList;
    }

    public String getOwner() {
        return this.owner;
    }

    @Override
    public List<IOrderable> getItems() {
        return this.itemList;
    }

    @Override
    public OrderStatus getStatus() {
        return this.status;
    }
    
    @Override 
    public String getId() {
        return this.id;
    }

    public final void setId(String id) throws Exception {
        //if (id != null) {
            this.id = id;
        ////} else {
        ////    throw new Exception("Order Id is empty");
        ////}
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setItemList(List<IOrderable> itemList) {
        this.itemList = itemList;
    }

    /**
     * Parses and XML source into an Order instance
     *
     * @param source Source of the XML
     * @return Order instance
     * @throws JAXBException
     */
    public static IOrder parseXML(Source source, Class c) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Order.class, MovieOrder.class, Orderable.class, Movie.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        IOrder order = (IOrder) jaxbUnmarshaller.unmarshal(source);
        return order;
    }

    /**
     * Parses and XML source into an Order instance
     *
     * @param source Source of the XML
     * @return Order instance
     * @throws JAXBException
     */
    public static IOrder parseJSON(Source source, Class c) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Order.class, c);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbUnmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
        jaxbUnmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
        IOrder order = (IOrder) jaxbUnmarshaller.unmarshal(source);
        return order;
    }

    public String toXML(Class c) throws JAXBException {
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(c);
        Marshaller jaxbmarshaller = jaxbContext.createMarshaller();
        jaxbmarshaller.marshal(this, sw);
        return sw.toString();
    }    
    
    public String toJSON(Class c) throws JAXBException {
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(c);
        Marshaller jaxbmarshaller = jaxbContext.createMarshaller();
        jaxbmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
        jaxbmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
        jaxbmarshaller.marshal(this, sw);
        return (sw.toString());
    }

    @Override
    public void process(IOrderableProcessor movieProcessor) throws OrderProcessingException {

        // Check Order Status
        if (this.status == OrderStatus.CANCELLED) {
            throw new OrderProcessingException("Cannot process order. Order has been cancelled.");
        } else if (this.status == OrderStatus.PREPARED || this.status == OrderStatus.PREPARED_PARTIAL) {
            throw new OrderProcessingException("Cannot process order. Order has already been processed.");
        }

        errors = 0;
        // Loop through all movies from the currentOrder
        Iterator<IOrderable> iteItems = itemList.iterator();
        while (iteItems.hasNext()) {
            IOrderable item = (IOrderable) iteItems.next();
            try {
                movieProcessor.process(item, this);
                processedItemList.add(item);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Error processing item: " + item.getName(), e);
                unprocessedItemList.add(item);
                errors++;
            }
        }
        if (errors > 0) {
            this.status = OrderStatus.PREPARED_PARTIAL;
        } else {
            this.status = OrderStatus.PREPARED;
        }
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

    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
