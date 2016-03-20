package com.fortwo.server.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.atmosphere.config.managed.Encoder;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Encode a {@link Message} into a String
 */
public class JacksonEncoder implements Encoder<Chat, String> {

    @Inject
    private ObjectMapper mapper;

    @Override
    public String encode(Chat m) {
        try {
            return mapper.writeValueAsString(m);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
