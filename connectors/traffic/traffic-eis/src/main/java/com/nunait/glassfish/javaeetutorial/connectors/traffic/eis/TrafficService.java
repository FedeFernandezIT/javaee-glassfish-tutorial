/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.traffic.eis;

import java.io.StringWriter;
import java.util.Random;
import javax.json.Json;
import javax.json.stream.JsonGenerator;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 21:17:35 ART
 */
public class TrafficService {
    
    private String[] cities = {
        "City1", "City2", "City3", "City4", "City5"
    };
    private String[] accessRoutes = {
        "AccessA", "AccessB", "AccessC", "AccessD", "AccessE"
    };
    private String[] statuses = {
        "GOOD", "SLOW", "CONGESTED"
    };
    private Random random;

    public TrafficService() {
        this.random = new Random();
    }
    
    /* Return a line with a JSON report like this:
     * {"report":[ {"city":"city_i","access":"access_j","status":"status_k"}, ... ]} */
    public String getReport() {
        
        StringWriter sw = new StringWriter();
        try (JsonGenerator gen = Json.createGenerator(sw)) {
            gen.writeStartObject();
            gen.writeStartArray("report");
            for (String city : cities) {
                for (String accessRoute : accessRoutes) {
                    int i = random.nextInt(statuses.length);
                    gen.writeStartObject();
                    gen.write("city", city);
                    gen.write("access", accessRoute);
                    gen.write("status", statuses[i]);
                    gen.writeEnd();
                }
                
            }
            gen.writeEnd();
            gen.writeEnd();
        }
        return sw.toString();
    }
    
    
}
