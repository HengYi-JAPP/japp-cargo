package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.sap.Ekko;

public interface EkkoRepository {

    Ekko save(Ekko ekko);

    Ekko find(String id);
}
