/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nunait.glassfish.javaeetutorial.ejb.standalone.ejb;

import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Federico Fernandez
 */
public class StandaloneBeanTest {
    
    private EJBContainer container;
    private Context ctx;
    private static final Logger logger =
            Logger.getLogger("standalone.ejb");
    
    public StandaloneBeanTest() {
    }
    
    @Before
    public void setUp() {
        container = EJBContainer.createEJBContainer();
        ctx = container.getContext();
    }
    
    @After
    public void tearDown() {
        if (container != null) {
            container.close();
        }
    }
    
    @Test
    public void testReturnMessage() throws Exception {
        logger.info("Probando standalone.ejb.StandaloneBean.returnMessage()");
        StandaloneBean instance = (StandaloneBean)
                ctx.lookup("java:global/classes/StandaloneBean");
        assertEquals("Bienvenidos!", instance.returnMessage());
    }
}
