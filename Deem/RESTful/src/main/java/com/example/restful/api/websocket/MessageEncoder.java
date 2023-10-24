package com.example.restful.api.websocket;
import com.example.restful.models.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


public class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public void init(final EndpointConfig config) {}

    @Override
    public void destroy() {}

    @Override
    public String encode(final Message message) throws EncodeException {
        try {
            return (new ObjectMapper().writer().withDefaultPrettyPrinter())
                    .writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
