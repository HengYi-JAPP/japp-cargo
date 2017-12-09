package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.sap.Ylips;

public interface YlipsRepository {

    Ylips save(Ylips ylips);

    Ylips find(String id);
}
