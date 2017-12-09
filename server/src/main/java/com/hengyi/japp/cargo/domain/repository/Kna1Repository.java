package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.sap.Kna1;
import com.hengyi.japp.cargo.domain.sap.T001;

import java.util.stream.Stream;

public interface Kna1Repository {
    Kna1 save(Kna1 kna1);

    Kna1 find(String kunnr);

    Stream<Kna1> autocomplete(T001 t001, String q);
}
