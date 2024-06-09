package org.otus.social.lib.filter;

import io.micrometer.core.instrument.Counter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.micrometer.core.instrument.Timer;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestLoggingFilter extends OncePerRequestFilter {

    private final Counter requestCounter;

    private final Counter errorCounter;

    private final Timer requestTimer;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.nanoTime();
        logRequest(request);
        filterChain.doFilter(request, response);
        logResponse(response, startTime);
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

    private void logResponse(HttpServletResponse response, long startTime ) {
        requestCounter.increment();
        if (response.getStatus()!= 200){
            errorCounter.increment();
        }

        StringBuilder responseLog = new StringBuilder();
        responseLog.append("Outgoing Response: ")
                .append("status=").append(response.getStatus())
                .append(", headers={");

        response.getHeaderNames().forEach(headerName ->
                responseLog.append(headerName).append(": ").append(response.getHeader(headerName)).append(", ")
        );

        responseLog.append("}");
        logger.info(responseLog.toString());
        long duration = System.nanoTime() - startTime;
        requestTimer.record(duration, TimeUnit.NANOSECONDS);
    }

    private  void metrics (HttpServletResponse response){

    }


}