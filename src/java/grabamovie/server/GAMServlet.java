/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grabamovie.server;

import grabamovie.core.GAMEngine;
import grabamovie.core.Order;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author OCanada
 */
public class GAMServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(GAMServlet.class.getName());
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Order has been sent</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            
            // Get the order and try parsing
            String strOrder = request.getParameter("order");
            if (strOrder != null) {
                StringBuffer strB = new StringBuffer(strOrder);
                try {
                    Order order = Order.parseXML(new StreamSource( new StringReader( strB.toString() ) )); 
                    if (!order.getIsProcessed()) {
                        GAMEngine gam = (GAMEngine)getServletContext().getAttribute("GAMEngine");
                        gam.addOrder(order);
                    }
                        
                } catch (JAXBException ex) {
                    LOG.log(Level.SEVERE, null, "Could not parse request as XML Order.");
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
            
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "GrabAMovie Servlet. Handles order issued by the front-end.";
    }// </editor-fold>
}
