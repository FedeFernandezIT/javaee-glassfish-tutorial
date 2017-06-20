/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api;

/** 
 * Indicates that the trade order could not be processed
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 13:01:08 ART
 */
public class TradeProcessingException extends Exception {

    public TradeProcessingException(String message) {
        super(message);
    }        
}
