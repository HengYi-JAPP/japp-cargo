package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.AbstractSendInfoEntity;
import com.hengyi.japp.cargo.domain.CarNoInfoLog;

import java.util.stream.Stream;

public interface CarNoInfoLogRepository {

    CarNoInfoLog save(AbstractSendInfoEntity sendInfo);

    CarNoInfoLog save(CarNoInfoLog log);

    CarNoInfoLog find(String carNo);

    Stream<CarNoInfoLog> autocomplete(String q);
}
