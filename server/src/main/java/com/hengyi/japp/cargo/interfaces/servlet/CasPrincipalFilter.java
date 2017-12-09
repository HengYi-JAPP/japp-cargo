package com.hengyi.japp.cargo.interfaces.servlet;

import com.hengyi.japp.cargo.application.OperatorService;
import com.hengyi.japp.cargo.domain.Operator;
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

public class CasPrincipalFilter implements Filter {
    @Inject
    private OperatorService operatorService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(new HttpServletRequestWrapper((HttpServletRequest) request) {
            @Override
            public Principal getUserPrincipal() {
                Operator operator = operatorService.findByCas();
                return new UserPrincipal(operator.getId());
            }
        }, response);
    }

    @Override
    public void destroy() {

    }
}
