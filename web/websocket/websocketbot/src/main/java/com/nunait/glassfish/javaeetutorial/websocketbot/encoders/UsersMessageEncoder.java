/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.websocketbot.encoders;

import com.nunait.glassfish.javaeetutorial.websocketbot.messages.UsersMessage;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/** 
 * Codifica un UsersMessage como un JSON.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de mayo de 2017 10:43:50 ART
 */
public class UsersMessageEncoder implements Encoder.Text<UsersMessage> {

    @Override
    public String encode(UsersMessage usersMessage) throws EncodeException {
        StringWriter swriter = new StringWriter();
        try (JsonGenerator jsonGen = Json.createGenerator(swriter)) {
            jsonGen.writeStartObject()
                    .write("type", "users")
                    .writeStartArray("userlist");
            for (String user : usersMessage.getUserList())
                jsonGen.write(user);
            jsonGen.writeEnd().writeEnd();
        }
        return swriter.toString();
    }

    @Override
    public void init(EndpointConfig config) {}

    @Override
    public void destroy() {}

}
