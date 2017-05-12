/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jsonpmodel;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

/** 
 * Esta clase gestiona los datos del formulario JSF, crea un modelo
 * de objeto JSON a partir de este, y aniliza los datos JSON.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de mayo de 2017 20:07:24 ART
 */
@Named
@SessionScoped
public class ObjectModelBean implements Serializable {

    private static final long serialVersionUID = 8085187183558101928L;    
    private static final Logger log = Logger.getLogger("ObjectModelBean");
    
    /* Información del modelo JSON */
    protected String documentJson;
    protected String documentJsonFomatted;
    private List<DOMTreeRow> rowList;
    
    
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

    public ObjectModelBean() {
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
    public String getDocumentJson() {
        return documentJson;
    }
    public String getDocumentJsonFomatted() {
        return documentJsonFomatted;
    }
    public List<DOMTreeRow> getRowList() {
        return rowList;
    }

    /* Método de acción para el formulario de index.xhtml.
     * Construye un modelo de objeto JSON a partir de los
     * datos del formulario. */
    public String buildJson() {
        /* Construye el modelo de objeto JSON */
        JsonObject model = Json.createObjectBuilder()
            .add("firstName", firstName)
            .add("lastName", lastName)
            .add("age", age)
            .add("streetAddress", streetAddress)
            .add("city", city)
            .add("state", state)
            .add("postalCode", postalCode)
            .add("phoneNumbers", Json.createArrayBuilder()
                .add(Json.createObjectBuilder()
                    .add("number", phoneNumber1)
                    .add("type", phoneType1))
                .add(Json.createObjectBuilder()
                    .add("number", phoneNumber2)
                    .add("type", phoneType2)))
        .build();
        
        /* Escribe la salida JSON */
        StringWriter sw = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(sw)) {
            jsonWriter.writeObject(model);
        }
        documentJson = sw.toString();
        
        /* Escribe la salida JSON formateada */
        Map<String, String> config = new HashMap<>();
        config.put(JsonGenerator.PRETTY_PRINTING, "");
        JsonWriterFactory factory = Json.createWriterFactory(config);
        StringWriter swFormatted = new StringWriter();
        try (JsonWriter jsonFormatted = factory.createWriter(swFormatted)) {
            jsonFormatted.writeObject(model);
        }
        documentJsonFomatted = swFormatted.toString();
        jsonTextArea = documentJsonFomatted;
        
        /* Navegación JSF */
        return "modelcreated";
    }    
    
    /* Método de acción para el formulario de modelcreated.xhtml.
     * Analiza datos JSON del textarea. */
    public String parseJson() {
        /* Analiza los datos aplicando el enfoque document object model */
        JsonStructure parsed;
        try (JsonReader reader = Json.createReader(new StringReader(jsonTextArea))) {
            parsed = reader.readObject();
        }
        
        /* Representa un árbol DOM en una lista para una tabla JSF */
        rowList = new ArrayList<>();
        printTree(parsed, 0, "");
        
        /* Navegación JSF */
        return "parsejson";
    }
    
    /* Usado para poblar rowList para mostrar el árbol DOM sobre una tabla JSF */
    private void printTree(JsonValue tree, int level, String key) {
        switch (tree.getValueType()) {
            case OBJECT:
                JsonObject object = (JsonObject) tree;
                rowList.add(new DOMTreeRow(level, tree.getValueType().toString(), key, "--"));
                for (String name : object.keySet()) {
                    printTree(object.get(name), level+1, name);
                }                    
                break;
            case ARRAY:
                JsonArray array = (JsonArray) tree;
                rowList.add(new DOMTreeRow(level, tree.getValueType().toString(), key, "--"));
                for (JsonValue val : array) {
                    printTree(val, level+1, "");
                }
                break;
            case STRING:
                JsonString st = (JsonString) tree;
                rowList.add(new DOMTreeRow(level, tree.getValueType().toString(), key, st.getString()));
                break;
            case NUMBER:
                JsonNumber num = (JsonNumber) tree;
                rowList.add(new DOMTreeRow(level, tree.getValueType().toString(), key, num.toString()));
                break;
            case FALSE:
            case TRUE:
            case NULL:
                String valtype = tree.getValueType().toString();
                rowList.add(new DOMTreeRow(level, valtype, key, valtype.toLowerCase()));
                break;
        }
    }
    
    /* Usado para mostrar el árbol del JSON DOM como 
     * una fila en una tabla JSF */
    public class DOMTreeRow {
        private int level;
        private String type;
        private String name;
        private String value;

        public DOMTreeRow(int level, String type, String name, String value) {
            this.level = level;
            this.type = type;
            this.name = name;
            this.value = value;
        }

        public int getLevel() { return level; }
        public String getType() { return type; }
        public String getName() { return name; }
        public String getValue() { return value; }                
    }

}
