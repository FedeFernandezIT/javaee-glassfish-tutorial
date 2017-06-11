/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.converter.ejb;

import java.math.BigDecimal;
import javax.ejb.Stateless;

/** 
 * Esta es la clase para ConverterBean Enterprise Bean.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 19 de mayo de 2017 9:41:48 ART
 */
@Stateless
public class ConverterBean {
    private final BigDecimal yenRate = new BigDecimal("111.47");
    private final BigDecimal euroRate = new BigDecimal("0.008");
        
    public BigDecimal dollarToYen(BigDecimal dollars) {
        BigDecimal result = dollars.multiply(yenRate);
        return result.setScale(2, BigDecimal.ROUND_UP);
    }
        
    public BigDecimal yenToEuro(BigDecimal yen) {
        BigDecimal result = yen.multiply(euroRate);
        return result.setScale(2, BigDecimal.ROUND_UP);
    }
}
