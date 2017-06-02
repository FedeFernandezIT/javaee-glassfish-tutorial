/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.clientmdbentity.eb;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 1 de junio de 2017 18:17:23 ART
 */
@MessageDriven(mappedName="java:app/jms/HRTopic", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability",
            propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "clientId",
            propertyValue = "OfficeMDB"),
    @ActivationConfigProperty(propertyName = "subscriptionName",
            propertyValue = "OfficeMDB")
})
public class OfficeMDB implements MessageListener {
    private static final Logger logger = Logger.getLogger("OfficeMDB");
    private final Random processingTime = new Random();
    
    @Resource
    public MessageDrivenContext mdc;
    @Inject
    private JMSContext context;
    @PersistenceContext
    EntityManager em;

    public OfficeMDB() {
    }
    
    /**
     * Castea el mensaje entrante a un MapMessage, recupera su contenido y asigna
     * al nuevo contratado a una oficina. Invoca al método compose para almacenar
     * la información en la entidad persistente, si el trabajo está completo,
     * envía una mensaje de replica al cliente.
     * 
     * @param inMessage mensaje entrante
     */    
    @Override
    public void onMessage(Message inMessage) {        
        MapMessage msg;
        String key;
        String name;
        String position;
        int officeNumber;
        
        try {
            if (inMessage instanceof MapMessage) {
                msg = (MapMessage) inMessage;
                key = msg.getString("HireID");
                name = msg.getString("Name");                
                position = msg.getString("Position");
                logger.log(Level.INFO, "OfficeMDB.onMessage:"
                        + " Mensaje recibido para employeeId {0}", key);
                
                officeNumber = new Random().nextInt(300) + 1;
                
                // Simula el tiempo de proceso de 1 a 10 seg.
                Thread.sleep(processingTime.nextInt(10) * 1000);
                compose(key, name, officeNumber, msg);
            } else {
                logger.log(Level.WARNING,
                        "OfficeMDB.onMessage: Tipo de mensaje incorrecto: {0}",
                        inMessage.getClass().getName());                
            }
        } catch (JMSException | InterruptedException e) {
            logger.log(Level.SEVERE,
                    "OfficeMDB.onMessage: Excepción: {0}", e.toString());
            mdc.setRollbackOnly();
        }
    }

    
    /**
     * Método helper para onMessage.
     * Localiza la fila de la representación de base de datos por la clave principal
     * y agrega el número de oficina asignado al nuevo contratado.
     * 
     * @param key ID del empleado, clave principal
     * @param name nombre
     * @param officeNumber número de oficina
     * @param msg mensaje recibido
     */
    void compose(String key, String name, int officeNumber, MapMessage msg) {    
        SetupOffice so = null;
        MapMessage replyMsg;
        Destination replyDest;
        String replyCorrelationMsgId;
        boolean done = false;
        
        try {
            so = em.find(SetupOffice.class, key);
            if (so != null) {
                logger.log(Level.INFO, "OfficeMDB.compose: "
                        + "Entidad de unión econtrada para employeeId {0}", key);
            }
        } catch(IllegalArgumentException e) {
            logger.log(Level.WARNING,
                    "OfficeMDB.compose: No se encontró entidad de unión: {0}",
                    e.toString());
        } catch (Exception e) {
            logger.log(Level.SEVERE,
                    "OfficeMDB.compose: em.find falló sin"
                    + " throwing IllegalArgumentException: {0}", e.toString());
        }
        
        // no se encontró entidad, entonces crear una.
        if (so == null) {
            try {
                logger.log(Level.INFO, "OfficeMDB.compose: "
                        + "Creando entidad de unión para employeeId {0}", key);
                so = new SetupOffice(key, name);
                em.persist(so);
            } catch (Exception e) {
                logger.log(Level.WARNING,
                        "OfficeMDB.compose: No se pudo crear entidad de unión: {0}",
                        e.toString());
                mdc.setRollbackOnly();
            }
        }
        
        // entidad creada o encontrada, entonces agregar oficina.
        if (so != null) {
            try {
                done = so.doOfficeNumber(officeNumber);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "OfficeMDB.compose: "
                        + "No se pudo asignar oficina para employeeId {0}", key);
                mdc.setRollbackOnly();
            }
        }
        
        
        /* Cualquier bean que reciba la información que el setup está comleto
         * envía un mensaje de vuelta al cliente y remueve la entidad. El contexto
         * usa sessión transaccional por defecto.
         */
        if (done) {
            try {
                /** 
                 * Envía una replica de los mensajes agrupados por la entidad
                 * compuesta. Invoca createReplyMsg para construír la replica.
                 */
                replyDest = msg.getJMSReplyTo();
                replyCorrelationMsgId = msg.getJMSMessageID();
                replyMsg = createReplyMsg(so, context, replyCorrelationMsgId);
                context.createProducer().send(replyDest, replyMsg);
                logger.log(Level.INFO, "OfficeMDB.compose: "
                        + "Envía mensaje de replica para employeeId {0}",
                        so.getEmployeeId());
            } catch (JMSException e) {
                logger.log(Level.SEVERE, "OfficeMDB.compose: "
                        + "JMSException: {0}", e.toString());                
            }
            
            if (so != null) {
                logger.log(Level.INFO, "OfficeMDB.compose: "
                        + "REMOVIENDO entidad SetupOffice employeeId={0}, Name={1}",
                        new Object[]{so.getEmployeeId(), so.getEmployeeName()});
                em.remove(so);
            }
        }
    }

    
    /**
     * Compone el mensaje de replica con la información generada del nuevo
     * contratado.
     *      
     * @param context contexto del productor del mensaje
     * @param msgId ID del reply correlation message
     * @return un MapMessage con el mensaje de réplica
     */
    private MapMessage createReplyMsg(SetupOffice so, JMSContext context, String msgId) 
            throws JMSException {
        MapMessage replyMsg = context.createMapMessage();
        replyMsg.setString("employeeId", so.getEmployeeId());
        replyMsg.setString("employeeName", so.getEmployeeName());
        replyMsg.setString("equipmentList", so.getEquipmentList());
        replyMsg.setInt("officeNumber", so.getOfficeNumber());
        replyMsg.setJMSCorrelationID(msgId);
        
        return replyMsg;
    }
    
    
}
