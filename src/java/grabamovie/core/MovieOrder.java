package grabamovie.core;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

/**
 * Class representing a movie Order, identified by a technical Id. It is
 * optionally labelled by a owner name and contains a possibly empty Movie list.
 * Each movie is meant to be processed in the order at further step.
 *
 * @author OCanada
 */
@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.NONE)
public class MovieOrder extends Order {  
    /**
     * Empty constructor for JAXB
     */
    public MovieOrder() {
        super();
    }

    /**
     * Main constructor
     *
     * @param id Compulsory technical identifier
     * @param owner Order optional owner name
     * @param movieList List movies contained in the Order
     * @throws Exception Exceptions regarding Id
     */
    public MovieOrder(String id, String owner, List<IOrderable> itemList) throws Exception {
        super(id, owner, itemList);
    }

    @Override
    public int compareTo(Object o) {
        return super.compareTo(o);
    }    
    
}
