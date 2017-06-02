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
 * @created 1 de junio de 2017 18:16:48 ART
 */
@MessageDriven(mappedName="java:app/jms/HRTopic", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability",
            propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "clientId",
            propertyValue = "EquipmentMDB"),
    @ActivationConfigProperty(propertyName = "subscriptionName",
            propertyValue = "EquipmentMDB")
})
public class EquipmentMDB implements MessageListener {
    private static final Logger logger = Logger.getLogger("EquipmentMDB");
    private final Random processingTime = new Random();
    
    @Resource
    public MessageDrivenContext mdc;
    @Inject
    private JMSContext context;
    @PersistenceContext
    EntityManager em;

    public EquipmentMDB() {
    }
        
    
    /**
     * Castea el mensaje entrante a un MapMessage, recupera su contenido y asigna
     * al nuevo contratado el equipamiento necesario según su posición. Invoca 
     * al método compose para almacenar la información en la entidad persistente,
     * si el trabajo está completo, envía una mensaje de replica al cliente.
     * 
     * @param inMessage mensaje entrante
     */
    @Override        
    public void onMessage(Message inMessage) {
        MapMessage msg;
        String key;
        String name;
        String position;
        String equipmentList;
        
        try {
            if (inMessage instanceof MapMessage) {
                msg = (MapMessage) inMessage;
                key = msg.getString("HireID");
                name = msg.getString("Name");
                position = msg.getString("Position");
                logger.log(Level.INFO, "EquipmentMDB.onMessage: "
                        + "Mensaje recibido para employeeId {0}", key);
                switch (position) {
                    case "Programador":
                        equipmentList = "Desktop System";
                        break;
                    case "Programador Senior":
                        equipmentList = "Laptop";
                        break;
                    case "Gerente":
                        equipmentList = "Tablet";
                        break;
                    case "Director":
                        equipmentList = "Mobile Phone";
                        break;
                    default:
                        equipmentList = "Baton";
                        break;
                }
                
                // Simula el tiempo de proceso de 1 a 10 seg.
                Thread.sleep(processingTime.nextInt(10) * 1000);
                compose(key, name, equipmentList, msg);
            } else {
                
            }
        } catch (Exception e) {
        }
    }

    void compose(String key, String name, String equipmentList, MapMessage msg) {
        SetupOffice so = null;
        MapMessage replyMsg;
        Destination replyDest;
        String replyCorrelationMsgId;
        boolean done = false;
        
        try {
            so = em.find(SetupOffice.class, key);
            if (so != null) {
                logger.log(Level.INFO, "EquipmentMDB.compose: "
                        + "Entidad de unión econtrada para employeeId {0}", key);
            }
        } catch(IllegalArgumentException e) {
            logger.log(Level.WARNING,
                    "EquipmentMDB.compose: No se encontró entidad de unión: {0}",
                    e.toString());
        } catch (Exception e) {
            logger.log(Level.SEVERE,
                    "Equipment.compose: em.find falló sin"
                    + " throwing IllegalArgumentException: {0}", e.toString());
        }
        
        // no se encontró entidad, entonces crear una.
        if (so == null) {
            try {
                logger.log(Level.INFO, "EquipmentMDB.compose: "
                        + "Creando entidad de unión para employeeId {0}", key);
                so = new SetupOffice(key, name);
                em.persist(so);
            } catch (Exception e) {
                logger.log(Level.WARNING,
                        "EquipmentMDB.compose: No se pudo crear entidad de unión: {0}",
                        e.toString());
                mdc.setRollbackOnly();
            }
        }
        
        // entidad creada o encontrada, entonces agregar oficina.
        if (so != null) {
            try {
                done = so.doEquipmentList(equipmentList);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "EquipmentMDB.compose: "
                        + "No se pudo asignar equipamiento para employeeId {0}", key);
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
                logger.log(Level.INFO, "EquipmentMDB.compose: "
                        + "Envía mensaje de replica para employeeId {0}",
                        so.getEmployeeId());
            } catch (JMSException e) {
                logger.log(Level.SEVERE, "EquipmentMDB.compose: "
                        + "JMSException: {0}", e.toString());                
            }
            
            if (so != null) {
                logger.log(Level.INFO, "EquipmentMDB.compose: "
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
