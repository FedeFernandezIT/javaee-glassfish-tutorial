/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jsonpstreaming;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterReader;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

/** 
 * Esta clase gestiona los datos del formulario JSF, crea un modelo
 * de objeto JSON a partir de este, y aniliza los datos JSON.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de mayo de 2017 23:24:41 ART
 */
@Named
@SessionScoped
public class StreamingBean implements Serializable {

    private static final long serialVersionUID = 4444712617948599816L;
    private static final Logger log = Logger.getLogger("StreamingBean");
    
    /* Propiedades de formulario */
    protected static final String PHONE_TYPE_HOME = "Hogar";
    protected static final String PHONE_TYPE_MOBILE = "Móvil";
    protected String firstName = "NunaIT";
    protected String lastName = "Software";
    protected int age = 21;
    protected String streetAddress = "El Carnaval 147";
    protected String city = "San Salvador de Jujuy";
    protected String state = "Jujuy";
    protected String postalCode = "4600";
    protected String phoneNumber1 = "111-111-1111";
    protected String phoneType1 = PHONE_TYPE_MOBILE;
    protected String phoneNumber2 = "222-222-2222";
    protected String phoneType2 = PHONE_TYPE_HOME;
    protected String jsonTextArea = "";
    
    /* Otras propiedades */
    protected String fileName;
    protected List<EventRow> rowList;

    public StreamingBean() {
    }

    /* Getter y Setter */
    public String getPhoneTypeHome() {
        return PHONE_TYPE_HOME;
    }
    public String getPhoneTypeMobile() {
        return PHONE_TYPE_MOBILE;
    }        
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getPhoneNumber1() {
        return phoneNumber1;
    }
    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }
    public String getPhoneType1() {
        return phoneType1;
    }
    public void setPhoneType1(String phoneType1) {
        this.phoneType1 = phoneType1;
    }
    public String getPhoneNumber2() {
        return phoneNumber2;
    }
    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }
    public String getPhoneType2() {
        return phoneType2;
    }
    public void setPhoneType2(String phoneType2) {
        this.phoneType2 = phoneType2;
    }
    public String getJsonTextArea() {
        return jsonTextArea;
    }
    public void setJsonTextArea(String jsonTextArea) {
        this.jsonTextArea = jsonTextArea;
    }
    public String getFileName() {
        return fileName;
    }
    public List<EventRow> getRowList() {
        return rowList;
    }
    
    /* Método de acción para el formulario de index.xhtml.
     * Escribe datos JSON usando la API streaming. */
    public String writeJson() {
        /* Escribe datos JSON en un archivo */
        fileName = "jsonoutput-" + new Random().nextInt(3200) + ".json";
        try {
            FileWriter writer = new FileWriter(fileName);
            try (JsonGenerator gen = Json.createGenerator(writer)) {
                gen.writeStartObject()
                    .write("firstName", firstName)
                    .write("lastName", lastName)
                    .write("age", age)
                    .write("streetAddress", streetAddress)
                    .write("streetAddress", streetAddress)
                    .write("city", city)
                    .write("state", state)
                    .write("postalCode", postalCode)
                    .writeStartArray("phoneNumbers")
                        .writeStartObject()
                            .write("number", phoneNumber1)
                            .write("type", phoneType1)
                        .writeEnd()
                        .writeStartObject()
                            .write("number", phoneNumber2)
                            .write("type", phoneType2)
                        .writeEnd()
                    .writeEnd()
                .writeEnd();
            } 
        } catch (IOException e) {
            log.log(Level.WARNING, "Error al escribir JSON en el archivo {0}", 
                    fileName + "-" + e.toString());
        }
        
        /* Muestra el JSON resultante en la página siguiente */
        jsonTextArea = this.readJsonFile();
        
        /* Navegación JSF */
        return "filewritten";
    }
    
    /* Método de acción para el formulario de filewritten.xhtml.
     * Analiza datos JSON y puebla una lista de eventos analizados
     * para una tabla JSF. */
    public String parseJson() {
        try {
            int nrow = 1;
            rowList = new ArrayList<>();
            JsonParser parser = Json.createParser(new FileReader(fileName));
            while (parser.hasNext()) {
                JsonParser.Event event = parser.next();
                switch (event) {
                    case START_OBJECT:
                    case END_OBJECT:
                    case START_ARRAY:
                    case END_ARRAY:
                    case VALUE_TRUE:
                    case VALUE_FALSE:
                    case VALUE_NULL:
                        rowList.add(new EventRow(nrow++, event.toString(), "--"));
                        break;
                    case KEY_NAME:
                    case VALUE_NUMBER:
                    case VALUE_STRING:
                        rowList.add(new EventRow(nrow++, event.toString(), parser.getString()));
                        break;
                }                
            }
        } catch (FileNotFoundException e) {
            log.log(Level.WARNING, "Archivo JSON {0} no existe", fileName);
        }        
        
        /* Navegación JSF */
        return "parsed";
    }
    
    /* Lee el archivo JSON en un String para mostrarlo en una página JSF */
    private String readJsonFile() {
        String content = "";
        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                content += line;
            }
        } catch (FileNotFoundException e) {
            log.log(Level.WARNING, "Archivo JSON {0} no existe", fileName);
        } catch (IOException ex) {
            log.log(Level.WARNING, "Error al leer el archivo {0}", fileName);
        }
        return content;
    }
    
    /* Usado para mostrar los eventos JSON como filas en una tabla JSF */
    public class EventRow {
        private int number;
        private String type;
        private String details;

        public EventRow(int number, String type, String details) {
            this.number = number;
            this.type = type;
            this.details = details;
        }

        public int getNumber() { return number; }
        public String getType() { return type; }
        public String getDetails() { return details; }                
    }
}
