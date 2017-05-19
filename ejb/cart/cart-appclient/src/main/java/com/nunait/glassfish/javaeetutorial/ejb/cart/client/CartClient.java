/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.cart.client;

import com.nunait.glassfish.javaeetutorial.ejb.cart.ejb.Cart;
import com.nunait.glassfish.javaeetutorial.ejb.cart.util.BookException;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;

/** 
 * Clase client para el ejemplo CartBean. Client agrega libros al
 * carrito. Imprime el contenido del carrito, y luego quita un libro
 * que no ha sido agregado aún al carrito, causando una BookException.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 19 de mayo de 2017 16:19:04 ART
 */
public class CartClient {
    
    @EJB
    private static Cart cart;

    public CartClient(String[] args) {
    }        
    
    /**     
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        CartClient client = new CartClient(args);
        client.toTest();
    }

    public void toTest() {
        try {
            cart.initialize("Federico Fernández", "123");
            cart.addBook("Java for Dummy");
            cart.addBook("C# for Dummy");
            cart.addBook("SQL for Dummy");
            
            List<String> bookList = cart.getContents();
            Iterator<String> iterator = bookList.iterator();
            while (iterator.hasNext()) {
                String title = iterator.next();
                System.out.println("Recuperando título de libro en el carrito: " + title);
            }
            
            System.out.println("Removiendo \"Enciclopedia mundial\" desde el carrito.");
            cart.removeBook("Enciclopedia mundial");
            cart.remove();
            
            System.exit(0);
        } catch(BookException e) {
            System.err.println("Capturando una BookException: " + e.getMessage());
            System.exit(0);
        }
    }
}
