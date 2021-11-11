package com.matejuh.webfluxmdc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoggingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        if (servletRequest instanceof HttpServletRequest) {
            final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            final HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            log.info(
                    "method={} uri={} status={}",
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI(),
                    httpServletResponse.getStatus()
            );
        }
    }
}
