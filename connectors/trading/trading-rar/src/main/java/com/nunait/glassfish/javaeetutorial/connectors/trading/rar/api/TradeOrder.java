/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api;

/** 
 * Represents a trade order for the EIS
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 13:01:42 ART
 */
public class TradeOrder {
    
    public enum OrderType {BUY, SELL};
    public enum OrderClass {MARKET};
    public enum Ticker {WWW, YYYY, ZZZ};
    
    private OrderType orderType;
    private int nShares;
    private Ticker ticker;
    private OrderClass orderClass;

    public TradeOrder() {
        orderType = OrderType.BUY;
        nShares = 100;
        ticker = Ticker.YYYY;
        orderClass = OrderClass.MARKET;
    }

    @Override
    public String toString() {
        return String.format("%s %d %s %s", orderType, nShares, ticker, orderClass);
    }
    
    /* Getters and setters */
    public OrderType getOrderType() {
        return orderType;
    }
    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
    public int getNShares() {
        return nShares;
    }
    public void setNShares(int nShares) {
        this.nShares = nShares;
    }
    public Ticker getTicker() {
        return ticker;
    }
    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }
    public OrderClass getOrderClass() {
        return orderClass;
    }
    public void setOrderClass(OrderClass orderClass) {
        this.orderClass = orderClass;
    }    
}
