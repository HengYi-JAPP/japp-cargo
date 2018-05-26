package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.application.query.OperatorQuery;
import com.hengyi.japp.cargo.domain.Operator;

import java.security.Principal;
import java.util.stream.Stream;

public interface OperatorRepository {

    Operator find(String id);

    Operator find(Principal principal);

    Operator save(Operator operator);

    Operator findByHrIdOrOaId(String uid);

    void query(OperatorQuery query);
}
