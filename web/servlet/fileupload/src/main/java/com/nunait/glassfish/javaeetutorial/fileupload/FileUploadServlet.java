/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.fileupload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 7 de mayo de 2017 22:25:20 ART
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    private static final long serialVersionUID = -6849505815956384590L;
    private static final Logger LOGGER = 
            Logger.getLogger(FileUploadServlet.class.getCanonicalName());
    
    /**
     * Procesa solicitudes para métodos
     * <code>GET</code> y <code>POST</code> de HTTP
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si un error específico del servlet ocurre
     * @throws IOException  si un error de I/O ocurre
     */
    protected void processRequest(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Crea los componentes path para guardar el archivo
        final String path = request.getParameter("destination");
        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);
        
        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();
        
        try {
            out = new FileOutputStream(new File(
                    path + File.separator + fileName));
            filecontent = filePart.getInputStream();
            
            int read;
            final byte[] bytes = new byte[1024];
            
            while ((read = filecontent.read(bytes)) != -1) {                
                out.write(bytes, 0, read);
            }
            writer.println("Nuevo archivo " + fileName + " creado en " + path);
            LOGGER.log(Level.INFO, "El archivo {0} ha sido cargado en {1}",
                    new Object[]{fileName, path});
            
        } catch(FileNotFoundException fne) {
            writer.println("Ud. no ha especificado un archivo para cargar o "
                + "está tratando de cargar un archivo en una ubicación "
                + "protegida o inexistente.");
            writer.println("<br> ERROR: " + fne.getMessage());
            LOGGER.log(Level.SEVERE, 
                    "Problemas durante la carga del archivo. Error: {0}",
                    new Object[]{fne.getMessage()});
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }
    
    /**
     * Maneja el método <code>GET</code> de HTTP
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si un error específico del servlet ocurre
     * @throws IOException  si un error de I/O ocurre
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Maneja el método <code>POST</code> de HTTP
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si un error específico del servlet ocurre
     * @throws IOException  si un error de I/O ocurre
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Retorna una breve descripción del Servlet
     * @return un String que contiene breve descripción
     */
    @Override
    public String getServletInfo() {
        return "Servlet que carga un archivo hacia el "
                + "destino especicado por el usuario";
    }

    
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        LOGGER.log(Level.INFO, "[servlet-fileupload] Part Header: {0}",
                partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 1).
                        trim().replace("\"", "");
            }
        }
        return null;
    }
            
}
