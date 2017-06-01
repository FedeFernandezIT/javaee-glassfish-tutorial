/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.clientsessionmdb.sb;

import javax.ejb.Remote;

/** 
 * Iterfaz remota para ejb Publisher. Declara un sólo método de negocio.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 1 de junio de 2017 11:16:55 ART
 */
@Remote
public interface PublisherRemote {
    public void publisherNews();
}
