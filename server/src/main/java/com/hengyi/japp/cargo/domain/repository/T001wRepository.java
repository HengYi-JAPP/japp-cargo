package com.hengyi.japp.cargo.domain.repository;


import com.hengyi.japp.cargo.domain.sap.T001l;
import com.hengyi.japp.cargo.domain.sap.T001w;

import java.util.stream.Stream;

public interface T001wRepository {
    T001w save(T001w t001l);

    T001w find(String id);

    T001w find(T001l t001l);

    Stream<T001w> queryAll();
}
