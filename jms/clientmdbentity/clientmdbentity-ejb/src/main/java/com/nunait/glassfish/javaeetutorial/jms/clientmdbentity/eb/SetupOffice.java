/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.clientmdbentity.eb;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.Id;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 1 de junio de 2017 18:56:31 ART
 */
@Entity
public class SetupOffice implements Serializable {
    private static final long serialVersionUID = 6170662506085600065L;
    private static final Logger logger = Logger.getLogger("SetupOffice");

    public SetupOffice() {
    }
        
    public SetupOffice(String newHireID, String name) {
        this.id = newHireID;
        this.name = name;
        this.equip = null;
        this.officeNum = -1;
    }

    /*
     * There should be a list of replies for each message being
     * joined.  This example is joining the work of separate
     * departments on the same original request, so it is all
     * right to have only one reply destination.  In theory, this
     * should be a set of destinations, with one reply for each
     * unique destination.
     */
    private String id;
    private String name;
    private int officeNum;
    private String equip;
    
    @Id
    public String getEmployeeId() {
        return id;
    }

    public void setEmployeeId(String id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return name;
    }

    public void setEmployeeName(String name) {
        this.name = name;
    }

    public int getOfficeNumber() {
        return officeNum;
    }

    public void setOfficeNumber(int officeNum) {
        this.officeNum = officeNum;
    }

    public String getEquipmentList() {
        return equip;
    }

    public void setEquipmentList(String equip) {
        this.equip = equip;
    }
    
    
    /**
     * Almacena el equipamiento en la base de datos, y determina si el
     * setup está completo.
     * 
     * @param list equipo asignado
     * @return true si el setup está completo
     */
    public boolean doEquipmentList(String list) {

        boolean done;

        setEquipmentList(list);
        logger.log(Level.INFO, "SetupOffice.doEquipmentList: Equipamiento para "
                + "employeeId {0} es {1} (oficina número {2})",
                new Object[]{getEmployeeId(), getEquipmentList(),
                    getOfficeNumber()});
        done = checkIfSetupComplete();
        return done;
    }
    
    /**
     * Almacena el núemro de oficina en la base de datos, y determina si el
     * setup está completo.
     * 
     * @param officeNumber
     * @return true si el setup está completo
     */
    public boolean doOfficeNumber(int officeNumber) {
        boolean done;
        
        setOfficeNumber(officeNumber);
        logger.log(Level.INFO, "SetupOffice.doOfficeNumber: Número de oficina para "
                + "employeeId {0} es {1} (equipamiento {2})",
                new Object[]{getEmployeeId(), getOfficeNumber(),
                    getEquipmentList()});
        done = checkIfSetupComplete();
        return done;
    }

    /**
     * Determina si ambos número de oficina y equipamiento han sido asignados
     * @return true si ambos están asignado
     */
    private boolean checkIfSetupComplete() {
        boolean allDone = false;
        
        if ((getEquipmentList() != null) && (getOfficeNumber() != -1)) {
            logger.log(Level.INFO, "SetupOffice.checkIfSetupComplete:"
                    + " PROGRAMA employeeId={0}, Name={1} configurado para"
                    + " oficina #{2} con {3}",
                    new Object[]{getEmployeeId(), getEmployeeName(),
                        getOfficeNumber(), getEquipmentList()});

            allDone = true;
        }
        return allDone;
    }

}
