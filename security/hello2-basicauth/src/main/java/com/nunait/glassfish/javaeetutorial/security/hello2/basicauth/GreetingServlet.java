/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.security.hello2.basicauth;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * HTTP Servlet que responde a un método GET del protocolo HTTP.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 23 de abril de 2017 23:58:36 ART
 */
@WebServlet(name = "GreetingServlet", urlPatterns = {"/greeting"})
@ServletSecurity(
        @HttpConstraint(transportGuarantee = ServletSecurity.TransportGuarantee.CONFIDENTIAL,
                        rolesAllowed = {"TutorialUser"})
)
public class GreetingServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setBufferSize(8192);
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>"
                    + "NunaIT - Servlet Saludo de bienvenida"
                    + "</title></head>");
            
            // ahora se escriben los datos de la respuesta
            out.println("<body bgcolor=\"#ffffff\">"
                    + "<img src=\"resources/images/nuna-logo-1.png\" "
                    + "alt=\"NunaIT logo\">"
                    + "<form method=\"get\">"
                    + "<h2>Bienvenido, ingrese su nombre</h2>"
                    + "<input title=\"Mi nombre es:\" type=\"text\" "
                    + "name=\"username\" size=\"25\"/>"
                    + "<p></p>"
                    + "<input type=\"submit\" value=\"Enviar\"/>"
                    + "<input type=\"reset\" value=\"Restablecer\"/>"
                    + "</form>");
            
            String username = request.getParameter("username");
            if (username != null && username.length() > 0) {
                RequestDispatcher dispatcher =
                        getServletContext().getRequestDispatcher("/response");
                if (dispatcher != null) {
                    dispatcher.include(request, response);
                }
            }
            out.println("</body></html>");
        }
    }

    @Override
    public String getServletInfo() {
        return "Greeting servlet da un saludo de bienvenida.";
    }
        
}
