/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.persistence.order.entity;

import java.io.Serializable;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 22 de mayo de 2017 20:08:11 ART
 */
public final class PartKey implements Serializable {
    private static final long serialVersionUID = -3652055001720198858L;
    
    private String partNumber;
    private int revision;

    @Override
    public int hashCode() {
        return 
            (this.getPartNumber() == null ? 0 : this.getPartNumber().hashCode())
            ^this.getRevision();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof PartKey)) return false;
        
        PartKey other = (PartKey) object;
        return (
            this.getPartNumber() == null
                ? other.getPartNumber() == null
                : this.getPartNumber().equals(other.getPartNumber())
            &&
            this.getRevision() == other.getRevision()
        );        
    }

    @Override
    public String toString() {
        return getPartNumber() + " rev " + getRevision();
    }        

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }
        
}
