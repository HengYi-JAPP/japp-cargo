package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.sap.Knb1;
import com.hengyi.japp.cargo.domain.sap.Knb1PK;

public interface Knb1Repository {
    Knb1 save(Knb1 kna1);

    Knb1 find(Knb1PK pk);
}
