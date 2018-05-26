package com.hengyi.japp.cargo.interfaces.websocket.conf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hengyi.japp.cargo.interfaces.gps.CarGpsData;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import static org.jzb.Constant.MAPPER;

/**
 * Created by jzb on 16-3-30.
 */
public class JsonEncoder implements Encoder.Text<CarGpsData> {
    @Override
    public String encode(CarGpsData dto) throws EncodeException {
        try {
            return MAPPER.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new EncodeException(dto, "", e);
        }
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
