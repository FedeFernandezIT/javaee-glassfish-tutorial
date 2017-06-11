/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.security.hello2.basicauth;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * HTTP Servlet que responde a un método GET del protocolo HTTP.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 24 de abril de 2017 0:42:39 ART
 */
@WebServlet(name = "ResponseServlet", urlPatterns = {"/response"})
public class ResponseServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response) 
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()){
            String username = request.getParameter("username");
            if (username != null && username.length() > 0) {
                out.println("<h2>Hola, " + username + "!</h2>");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Response servlet saluda \"Hola ,<username>!\"";
    }
        
}
