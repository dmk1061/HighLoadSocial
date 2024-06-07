package org.otus.social.lib.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

@Slf4j
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logRequest(request);
        filterChain.doFilter(request, response);
        logResponse(response);
    }

    private void logRequest(HttpServletRequest request) {
        StringBuilder requestLog = new StringBuilder();
        requestLog.append("Incoming Request: ")
                .append("method=").append(request.getMethod())
                .append(", uri=").append(request.getRequestURI())
                .append(", query=").append(request.getQueryString())
                .append(", headers={");

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headerValues = request.getHeaders(headerName);
            Collections.list(headerValues).forEach(headerValue ->
                    requestLog.append(headerName).append(": ").append(headerValue).append(", ")
            );
        }

        requestLog.append("}");
        logger.info(requestLog.toString());
    }

    private void logResponse(HttpServletResponse response) {
        StringBuilder responseLog = new StringBuilder();
        responseLog.append("Outgoing Response: ")
                .append("status=").append(response.getStatus())
                .append(", headers={");

        response.getHeaderNames().forEach(headerName ->
                responseLog.append(headerName).append(": ").append(response.getHeader(headerName)).append(", ")
        );

        responseLog.append("}");
        logger.info(responseLog.toString());
    }


}