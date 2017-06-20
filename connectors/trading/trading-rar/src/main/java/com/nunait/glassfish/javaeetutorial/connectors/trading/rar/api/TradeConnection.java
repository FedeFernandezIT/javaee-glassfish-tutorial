/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api;

import javax.resource.ResourceException;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 12:57:05 ART
 */
public interface TradeConnection {
    
    /* Submits a trade order to the EIS */
    public TradeResponse submitOrder(TradeOrder order) throws TradeProcessingException;
    
    /* Closes the connection handle */
    public void close() throws ResourceException;
}
