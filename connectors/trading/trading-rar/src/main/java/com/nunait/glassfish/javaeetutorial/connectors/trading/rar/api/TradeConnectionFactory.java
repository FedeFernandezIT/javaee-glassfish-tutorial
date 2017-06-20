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
 * @created 11 de junio de 2017 12:55:14 ART
 */
public interface TradeConnectionFactory {
    
    /* Applications call this method, which delegates on the container's
     * connection manager to obtain a connection instance through
     * TradeManagedConnectionFactory */
    public TradeConnection getConnection() throws ResourceException;
}
