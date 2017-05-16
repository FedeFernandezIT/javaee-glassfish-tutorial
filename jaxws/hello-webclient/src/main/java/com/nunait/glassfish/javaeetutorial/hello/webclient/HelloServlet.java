/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.hello.webclient;

import com.nunait.glassfish.javaeetutorial.jaxws.helloservice.endpoint.Hello;
import com.nunait.glassfish.javaeetutorial.jaxws.helloservice.endpoint.HelloService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 15 de mayo de 2017 22:14:34 ART
 */
@WebServlet(name = "HelloServlet", urlPatterns = {"/HelloServlet"})
public class HelloServlet extends HttpServlet {
    
    @WebServiceRef(wsdlLocation = 
            "http://localhost:8080/helloservice/HelloService?wsdl")
    private HelloService service;
    
    
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<html lang=\"es\">");
            out.println("<head>");
            out.println("<title>NunaIT - Servlet HelloServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HelloServlet en " + 
                    request.getContextPath() + "</h1>");
            out.println("<p>" + sayHello("mundo") + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    public String getServletInfo() {
        return "Breve descripción";
    }
    
    
    
    private String sayHello(String arg0) {
        Hello port = service.getHelloPort();
        return port.sayHello(arg0);
    }

}
