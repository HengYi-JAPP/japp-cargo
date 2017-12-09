package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.sap.Lfa1;
import com.hengyi.japp.cargo.domain.sap.T001;

import java.util.stream.Stream;

public interface Lfa1Repository {
    Lfa1 save(Lfa1 lfa1);

    Lfa1 find(String lifnr);

    Stream<Lfa1> autocomplete(T001 t001, String q);
}
