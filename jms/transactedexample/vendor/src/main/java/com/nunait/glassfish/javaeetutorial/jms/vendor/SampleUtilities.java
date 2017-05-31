/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.vendor;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 30 de mayo de 2017 20:30:51 ART
 */
public class SampleUtilities {
    
    /**
     * Clase monitor para ejemplos asincrónicos. Producer señala el fin
     * del flujo de mensajes; listeners invoca a allDone() para notificar
     * a consumer que la señal ha arribado, mientras consumer invoca a 
     * waitTillDone() para esperar por esta notificación.
     */
    public static class DoneLatch {
        boolean done = false;
        
        /**
         * Espera hasta que done este establecido en true.
         */
        public void waitTillDone() {
            synchronized (this) {                
                while (!done) {                        
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                    } 
                }
            }
        }
        
        /**
         * Establece done a true.
         */
        public void allDone() {
            synchronized (this) {
                done = true;
                this.notify();
            }
        }        
    }

}
