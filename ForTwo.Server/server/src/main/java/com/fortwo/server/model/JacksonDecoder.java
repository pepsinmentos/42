package com.fortwo.server.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.atmosphere.config.managed.Decoder;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Decode a String into a {@link Message}.
 */
public class JacksonDecoder implements Decoder<String, Chat> {

    @Inject
    private ObjectMapper mapper;

    @Override
    public Chat decode(String s) {
        try {
            return mapper.readValue(s, Chat.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
