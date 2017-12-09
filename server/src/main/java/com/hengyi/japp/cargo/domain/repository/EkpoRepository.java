package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.sap.Ekpo;
import com.hengyi.japp.cargo.domain.sap.EkpoPK;

public interface EkpoRepository {
    Ekpo save(Ekpo ekpo);

    Ekpo find(EkpoPK pk);
}
