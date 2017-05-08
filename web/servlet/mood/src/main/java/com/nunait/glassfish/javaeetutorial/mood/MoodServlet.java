/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nunait.glassfish.javaeetutorial.mood;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 7 de mayo de 2017 18:27:09 ART
 */
@WebServlet("/report")
public class MoodServlet extends HttpServlet {

    private static final long serialVersionUID = -8728953101683730945L;
    
    /**
     * Procesa solicitudes para métodos <dode>GET</code> y <code>POST</code>
     * de HTTP.
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si un error específico del sevlet ocurre
     * @throws IOException si un error de I/O ocurre
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()) {
            out.println("<html lang=\"es\">");
            out.println("<head>");
            out.println("<title>Servlet MoodServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MoodServlet en "
                    + request.getContextPath() + "</h1>");
            String mood = (String) request.getAttribute("mood");
            out.println("<p>Nuna se siente: " + mood + "</p>");
            switch (mood) {
                case "sleepy":
                    out.println("<img src=\"resources/images/emo-sleepy.png\" alt=\"Emoticon durmiendo\"><br>");
                    break;
                case "alert":
                    out.println("<img src=\"resources/images/emo-alert.png\" alt=\"Símbolo de advertencia\"><br>");
                    break;
                case "hungry":
                    out.println("<img src=\"resources/images/emo-hungry.png\" alt=\"Emoticon hambriento\"><br>");
                    break;
                case "lethargic":
                    out.println("<img src=\"resources/images/emo-lethargic.png\" alt=\"Emoticon adolorido\"><br>");
                    break;
                case "thoughtful":
                    out.println("<img src=\"resources/images/emo-thoughtful.png\" alt=\"Emoticon pensante\"><br>");
                    break;
                default:
                    out.println("<img src=\"resources/images/emo-thumbsup.png\" alt=\"Emoticon dedos arriba\"><br>");
                    break;
            }
            out.println("</body>");
            out.println("</html>");
        }        
    }
    
    /**
     * Maneja el método <code>GET</code> HTTP
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si un error específico del sevlet ocurre
     * @throws IOException si un error de I/O ocurre
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        processRequest(request, response);
    }
        
    /**
     * Maneja el método <code>POST</code> HTTP
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si un error específico del sevlet ocurre
     * @throws IOException si un error de I/O ocurre
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {        
        processRequest(request, response);
    }

    /**
     * Retorna una breve descrición del servlet
     *
     * @return un String que conteiene una breve descripción
     */
    @Override
    public String getServletInfo() {
        return "Breve descripción";
    }    

}
