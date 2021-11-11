package com.matejuh.webfluxmdc;

import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.UUID;

public class RequestContextFilter implements Filter {

    private static final String REQUEST_ID = "requestId";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final String requestId = UUID.randomUUID().toString();
        MDC.put(REQUEST_ID, requestId);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
