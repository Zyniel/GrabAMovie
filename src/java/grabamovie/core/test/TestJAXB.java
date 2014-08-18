/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.core.test;

import grabamovie.core.Movie;
import grabamovie.core.Order;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author OCanada
 */
public class TestJAXB {

    public static void main(String[] args) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Movie.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        Movie movie1 = new Movie("000001", "Movie 1", "D:\\");
        Movie movie2 = new Movie("000002", "Movie 2", "D:\\");
        Movie movie3 = new Movie("000003", "Movie 3", "D:\\");

        //Marshal the employees list in file
        jaxbMarshaller.marshal(movie1, new File("d:\\tmp3\\movies.xml"));

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
    }
}
