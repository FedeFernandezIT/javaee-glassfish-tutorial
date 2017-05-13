/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.cdi.producermethods;

/** 
 * Implementación de Coder que no realiza ningún porcesamiento
 * sobre la cadena de entrada pero muestra por pantalla los
 * argumentos.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 13 de mayo de 2017 16:06:59 ART
 */
public class TestCoderImpl implements Coder {

    /**
     * Retorna una cadena con los valores de los argumentos.
     * 
     * @param s     cadena de entrada
     * @param tval  cantidad de caracteres a desplazar
     * @return      cadena expresando los valores de argumentos
     */
    @Override
    public String codeString(String s, int tval) {
        return "Cadena de entrada " + s + ", desplazamiento " + tval;
    }

}
