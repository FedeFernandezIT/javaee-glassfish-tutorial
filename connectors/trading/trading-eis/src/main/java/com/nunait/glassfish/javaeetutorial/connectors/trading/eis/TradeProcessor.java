/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.trading.eis;

import java.util.Random;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 11:30:29 ART
 */
public class TradeProcessor {

    Random random;

    public TradeProcessor() {
        random = new Random();
    }
               
    public String getGreeting() {
        return "WELCOME MegaTrade Plataforma de Ejecucion.";
    }
    
    public String getReady() {
        return "READY Aceptando pedidos de comercializacion para ejecucion.";
    }
    
    public String processCommand(String command) {
        String ret;
        String[] words = command.split(" ");
        
        switch (words[0]) {
            case "EXIT":
                ret = "BYE Cerrando conexion.";
                break;
            case "BUY":
            case "SELL":
                int nshares = Integer.parseInt(words[1]);
                String ticker = words[2].toUpperCase();
                String type = words[3].toUpperCase();
                if (type.compareTo("MARKET") == 0) {
                    double price = getPrice(ticker);
                    if (price != -1) {
                        double total = nshares * price;
                        double fee = 0.005 * total;
                        int orderNumber = random.nextInt(10000);
                        ret = String.format("EXECUTED #%d TOTAL %.2f FEE %.2f",
                                     orderNumber, total, fee);
                    } else 
                        ret = "ERROR No se puede recuperar precio para " + ticker;                                        
                } else
                    ret = "ERROR Solo pedidos MARKET son soportados.";
                break;
            default:
                ret = "ERROR Comando desconocido.";
        }
        return ret;
    }

    private double getPrice(String ticker) {
        return 100.0 + 0.01 * (random.nextInt(5000) - 2500);
    }

}
