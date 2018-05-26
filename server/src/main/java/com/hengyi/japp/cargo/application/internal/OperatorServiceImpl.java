package com.hengyi.japp.cargo.application.internal;

import com.hengyi.japp.cargo.application.OperatorService;
import com.hengyi.japp.cargo.application.command.EntityDTO;
import com.hengyi.japp.cargo.application.command.OperatorPermissionUpdateCommand;
import com.hengyi.japp.cargo.domain.Operator;
import com.hengyi.japp.cargo.domain.OperatorPermission;
import com.hengyi.japp.cargo.domain.repository.OperatorPermissionRepository;
import com.hengyi.japp.cargo.domain.repository.OperatorRepository;
import com.hengyi.japp.cargo.domain.repository.T001Repository;
import com.hengyi.japp.cargo.domain.sap.T001;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;
import org.jzb.J;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
public class OperatorServiceImpl implements OperatorService {
    @Inject
    private OperatorRepository operatorRepository;
    @Inject
    private OperatorPermissionRepository operatorPermissionRepository;
    @Inject
    private T001Repository t001Repository;

    @Override
    public OperatorPermission update(Principal principal, String id, OperatorPermissionUpdateCommand command) {
        OperatorPermission operatorPermission = operatorPermissionRepository.find(id);
        if (operatorPermission == null) {
            operatorPermission = new OperatorPermission();
            Operator operator = operatorRepository.find(id);
            operatorPermission.setOperator(operator);
        }
        final T001 defaultReceiveT001 = Optional.ofNullable(command.getDefaultReceiveT001())
                .map(EntityDTO::getId)
                .map(t001Repository::find)
                .orElse(null);
        operatorPermission.setDefaultReceiveT001(defaultReceiveT001);
        operatorPermission.setAllT001s(command.isAllT001s());
        if (command.isAllT001s()) {
            operatorPermission.setT001s(null);
        } else {
            final Set<T001> t001s = J.emptyIfNull(command.getT001s())
                    .stream()
                    .map(EntityDTO::getId)
                    .map(t001Repository::find)
                    .collect(Collectors.toSet());
            t001s.add(defaultReceiveT001);
            operatorPermission.setT001s(t001s);
        }
        final Operator operator = operatorPermission.getOperator();
        operator.setAdmin(command.isAdmin());
        operatorRepository.save(operator);
        return operatorPermissionRepository.save(operatorPermission);
    }

    @Override
    public Operator findByCas() {
        Operator operator = _findByCas();
        return operator != null ? operator : createByCas();
    }

    private Operator createByCas() {
        synchronized (OperatorService.class) {
            Operator operator = _findByCas();
            if (operator != null)
                return operator;

            Assertion assertion = AssertionHolder.getAssertion();
            AttributePrincipal attributePrincipal = assertion.getPrincipal();
            String uid = attributePrincipal.getName();
            Map<String, Object> map = attributePrincipal.getAttributes();
            String oaId = (String) map.get("oauser");
            String name = (String) map.get("displayName");
            operator = new Operator();
            operator.setHrId(uid);
            operator.setOaId(oaId);
            operator.setName(name);
            return operatorRepository.save(operator);
        }
    }

    private Operator _findByCas() {
        Assertion assertion = AssertionHolder.getAssertion();
        if (assertion == null)
            return null;

        AttributePrincipal attributePrincipal = assertion.getPrincipal();
        String uid = attributePrincipal.getName();
        Operator operator = operatorRepository.findByHrIdOrOaId(uid);
        if (operator != null)
            return operator;

        Map<String, Object> map = attributePrincipal.getAttributes();
        String oaId = (String) map.get("oauser");
        operator = operatorRepository.findByHrIdOrOaId(oaId);
        return operator;
    }
}
