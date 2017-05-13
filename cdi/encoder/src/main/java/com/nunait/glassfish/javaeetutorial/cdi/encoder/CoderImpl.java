/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.cdi.encoder;

/** 
 * Implementación de la interfaz Coder, cambia las letras de una cadena.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 13 de mayo de 2017 15:45:00 ART
 */
public class CoderImpl implements Coder{

    /**
     * Desplaza las letras de una cadena de entrada por el
     * número de caratcteres del segundo argumento.
     * 
     * @param s     cadena de entrada
     * @param tval  cantidad de caracteres a desplazar
     * @return      cadena transformada
     */
    @Override
    public String codeString(String s, int tval) {
        final int SPACE_CHAR = 32;
        final int CAPITAL_A = 65;
        final int CAPITAL_Z = 90;
        final int SMALL_A = 97;
        final int SMALL_Z = 122;
        
        StringBuilder sb = new StringBuilder(s);
       
        for (int i = 0; i < sb.length(); i++) {
            
            int cp = sb.codePointAt(i);
            int cplus = cp + tval;
            if (cp == SPACE_CHAR) {
                cplus = SPACE_CHAR;
            }
            if (CAPITAL_A <= cp && cp <= CAPITAL_Z) {
                if (cplus > CAPITAL_Z) {
                    cplus -= 26;
                }
            } else if (SMALL_A <= cp && cp <= SMALL_Z) {
                if (cplus > SMALL_Z) {
                    cplus -= 26;
                }
            } else {
                cplus = cp;
            }
            char c = (char) cplus;
            sb.setCharAt(i, c);
        }
        return sb.toString();
    }

}
