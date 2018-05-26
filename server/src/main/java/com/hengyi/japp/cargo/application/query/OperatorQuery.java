package com.hengyi.japp.cargo.application.query;

import com.hengyi.japp.cargo.domain.Operator;
import com.hengyi.japp.cargo.domain.repository.OperatorRepository;
import org.jzb.search.JPageQuery;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Stream;

/**
 * 描述：
 *
 * @author jzb 2017-12-01
 */
public class OperatorQuery extends JPageQuery<Stream<Operator>> {
    public String q;
    public Set<String> defaultReceiveBukrsSet;

    public OperatorQuery(Principal principal, int first, Integer pageSize) {
        super(principal, first, pageSize);
    }

    public OperatorQuery exe(OperatorRepository operatorRepository) {
        operatorRepository.query(this);
        return this;
    }
}
