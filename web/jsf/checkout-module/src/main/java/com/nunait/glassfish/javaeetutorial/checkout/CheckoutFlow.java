/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.checkout;

import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 4 de mayo de 2017 12:57:29 ART
 */
public class CheckoutFlow implements Serializable {

    private static final long serialVersionUID = 4957298883595652465L;
    
    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
        
        String flowId = "checkoutFlow";
        flowBuilder.id("", flowId);
        flowBuilder.viewNode(flowId,
                "/" + flowId + "/" + flowId + ".xhtml").
                markAsStartNode();
        
        flowBuilder.returnNode("returnFromCheckoutFlow").
                fromOutcome("#{checkoutFlowBean.returnValue}");
        
        flowBuilder.inboundParameter("param1FromJoinFlow",
                "#{flowScope.param1Value}");
        flowBuilder.inboundParameter("param2FromJoinFlow",
                "#{flowScope.param2Value}");
                
        flowBuilder.flowCallNode("calljoin").flowReference("", "joinFlow").
                outboundParameter("param1FromCheckoutFlow",
                    "#{checkoutFlowBean.name}").
                outboundParameter("param2FromCheckoutFlow",
                    "#{checkoutFlowBean.city}");
        
        return flowBuilder.getFlow();
    }

}
