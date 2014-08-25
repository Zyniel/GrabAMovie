package grabamovie.server;

import grabamovie.core.IOrder;
import grabamovie.core.IOrderableProcessor;
import grabamovie.core.MovieOrder;
import grabamovie.core.OrderProcessor;
import grabamovie.core.Order;
import grabamovie.utils.LogFormatter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.concurrent.ExecutorService;
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

    private static final Logger LOG = LogFormatter.getLogger(GAMServlet.class.getName());
    private ExecutorService es;
    private IOrderableProcessor deftItemProcessor;

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
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (es == null) {
            es = (ExecutorService) getServletContext().getAttribute("ExecutorService");
        }
        if (deftItemProcessor == null) {
            deftItemProcessor = (IOrderableProcessor) getServletContext().getAttribute("DefaultProcessor");
        }

        try {
            // Get the order and try parsing
            String strOrder = request.getParameter("order");
            if (strOrder != null) {
                try {
                    // Prepare OrderProcessor
                    IOrder order = Order.parseXML(new StreamSource(new StringReader(strOrder)), MovieOrder.class);
                    OrderProcessor op = new OrderProcessor(deftItemProcessor);
                    op.addOrder(order);
                    
                    // Queue processing
                    es.execute(op);
                } catch (JAXBException ex) {
                    LOG.log(Level.SEVERE, "Could not parse order: {0}", ex.getLinkedException().getMessage());
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

    private enum ResponseStatus {

        SUCCESS,
        FAILURE,
        CRITICAL
    }

    private String formatResponse(ResponseStatus resp, String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("<response>");
        sb.append("<status>");
        switch (resp) {
            case SUCCESS:
                sb.append("0");
                ;
            case FAILURE:
                sb.append("1");
            case CRITICAL:
                sb.append("2");
            default:
                ;
        }
        sb.append("</status>");
        if (message == null || "".equals(message)) {
        } else {
            sb.append("<message>").append(message).append("</message>");
        }
        sb.append("</response>");
        return sb.toString();
    }
}
