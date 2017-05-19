/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.converter.web;

import com.nunait.glassfish.javaeetutorial.ejb.converter.ejb.ConverterBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 19 de mayo de 2017 9:56:28 ART
 */
@WebServlet(urlPatterns = "/")
public class ConverterServlet extends HttpServlet {
    private static final long serialVersionUID = 8224775579401267131L;
    
    @EJB
    ConverterBean converter;
    
    /**
     * Procesa las solicitudes para ambos métodos HTTP <code>GET</code> y
     * <code>POST</code>.
     * @param request solicitud al servlet
     * @param response respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de I/O
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        // Salida de los resultados
        out.println("<html lang=\"es\">");
        out.println("<head>");
        out.println("<title>NunaIT - Servlet ConverterServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet ConverterServlet en " +
                request.getContextPath() + "</h1>");
        try {
            String amount = request.getParameter("amount");
            if (amount != null && amount.length() > 0) {
                // convertir el parámetro amount de la solicitud a un BigDecimal
                BigDecimal d = new BigDecimal(amount);
                // invocar el método ConverterBean.dollarToYen() para obtener un
                // monto en Yen
                BigDecimal yenAmount = converter.dollarToYen(d);
                
                // invocar el método ConverterBean.yenToEuro() para obtener un
                // monto en Euro
                BigDecimal euroAmount = converter.yenToEuro(yenAmount);
                out.println("<p>" + amount + "dólares son " +
                        yenAmount.toPlainString() + " yen.</>");
                out.println("<p>" + yenAmount.toPlainString() + " yen son " +
                        euroAmount.toPlainString() + " Euro.</p>");
            } else {
                out.println("<p>Ingrese un monto en dólares para convertir:</p>");
                out.println("<form method=\"get\">");
                out.println("<p>$ <input title=\"Monto\" type=\"text\" name=\"amount\" size=\"25\"></>");
                out.println("<br/>");
                out.println("<input type=\"submit\" value=\"Enviar\">" +
                        "<input type=\"reset\" value=\"Restablecer\">");
                out.println("</form>");
            }
        } finally {
            out.println("</body>");
            out.println("</html>");
            out.close();
        }        
    }

    // <editor-fold defaultstate="collapsed" desc="Servlet methods.">
    /**
     * Manaja un método HTTP <code>POST</code>.
     * @param request 
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Manaja un método HTTP <code>GET</code>.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Retorna una breve descripción del servlet
     * @return un String
     */
    @Override
    public String getServletInfo() {
        return "Breve descripción";
    }
    // </editor-fold>
        
}
