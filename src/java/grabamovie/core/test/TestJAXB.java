/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.core.test;

import grabamovie.core.Movie;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.*;
import org.eclipse.persistence.jaxb.JAXBContextProperties;



/**
 *
 * @author OCanada
 */
public class TestJAXB {

    public static void main(String[] args) throws JAXBException {
        // JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
        JAXBContext jc = JAXBContext.newInstance(Movie.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty("eclipselink.media-type", "application/json");
        marshaller.setProperty("eclipselink.json.include-root", false);

        Movie movie1 = new Movie("000001", "Movie 1", "D:\\");
        Movie movie2 = new Movie("000002", "Movie 2", "D:\\");
        Movie movie3 = new Movie("000003", "Movie 3", "D:\\");

        //Marshal the employees list in file
        marshaller.marshal(movie1, new File("d:\\tmp3\\movies.xml"));
        /*
        jaxbContext = JAXBContext.newInstance(Order.class);
        jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        List<Movie> listMovie = new ArrayList<Movie>();
        listMovie.add(movie1);
        listMovie.add(movie2);
        listMovie.add(movie3);

        try {
            Order order = new Order("0000001", "FCABANNES", listMovie);
            //Marshal the employees list in file
            
            jaxbMarshaller.marshal(order, new File("d:\\tmp3\\order.xml"));
        } catch (Exception ex) {
            Logger.getLogger(TestJAXB.class.getName()).log(Level.SEVERE, null, ex);
        }
        * */
    }
}
