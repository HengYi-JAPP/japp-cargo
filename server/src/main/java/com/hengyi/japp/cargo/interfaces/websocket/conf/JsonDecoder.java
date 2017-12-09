package com.hengyi.japp.cargo.interfaces.websocket.conf;

import com.fasterxml.jackson.databind.JsonNode;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

import static org.jzb.Constant.MAPPER;

/**
 * 描述：
 *
 * @author jzb 2017-12-09
 */
public class JsonDecoder implements Decoder.Text<JsonNode> {
    @Override
    public JsonNode decode(String s) throws DecodeException {
        try {
            return MAPPER.readTree(s);
        } catch (IOException e) {
            throw new DecodeException(s, "", e);
        }
    }

    @Override
    public boolean willDecode(String s) {
        return false;
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
