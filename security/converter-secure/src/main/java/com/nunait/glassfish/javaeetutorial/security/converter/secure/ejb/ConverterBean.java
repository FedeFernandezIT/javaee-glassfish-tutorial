/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.security.converter.secure.ejb;

import java.math.BigDecimal;
import java.security.Principal;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

/** 
 * Esta es la clase para ConverterBean Enterprise Bean.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 19 de mayo de 2017 9:41:48 ART
 */
@Stateless
@DeclareRoles({"TutorialUser"})
public class ConverterBean {
    
    @Resource SessionContext ctx;
    private final BigDecimal yenRate = new BigDecimal("111.47");
    private final BigDecimal euroRate = new BigDecimal("0.008");
    
    @RolesAllowed({"TutorialUser"})
    public BigDecimal dollarToYen(BigDecimal dollars) {
        BigDecimal result = new BigDecimal("0.0");
        Principal callerPrincipal = ctx.getCallerPrincipal();
        if (ctx.isCallerInRole("TutorialUser")) {
            result = dollars.multiply(yenRate);
            return result.setScale(2, BigDecimal.ROUND_UP);
        }
        return result.setScale(2, BigDecimal.ROUND_UP);        
    }
    
    @RolesAllowed({"TutorialUser"})
    public BigDecimal yenToEuro(BigDecimal yen) {
        BigDecimal result = new BigDecimal("0.0");
        Principal callerPrincipal = ctx.getCallerPrincipal();
        if (ctx.isCallerInRole("TutorialUser")) {
            result = yen.multiply(euroRate);
            return result.setScale(2, BigDecimal.ROUND_UP);
        }
        return result.setScale(2, BigDecimal.ROUND_UP);
    }
}
