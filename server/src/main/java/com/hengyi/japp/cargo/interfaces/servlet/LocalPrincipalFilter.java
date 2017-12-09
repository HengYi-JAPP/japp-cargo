package com.hengyi.japp.cargo.interfaces.servlet;

import com.hengyi.japp.cargo.domain.Operator;
import com.hengyi.japp.cargo.domain.repository.OperatorRepository;
import com.sun.security.auth.UserPrincipal;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by jzb on 17-4-15.
 */
public class LocalPrincipalFilter implements Filter {
    @Inject
    private OperatorRepository operatorRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(new HttpServletRequestWrapper((HttpServletRequest) request) {
            @Override
            public Principal getUserPrincipal() {
                Operator operator = operatorRepository.findByHrIdOrOaId("12000077");
                return new UserPrincipal(operator.getId());
            }
        }, response);
    }

    @Override
    public void destroy() {

    }
}
