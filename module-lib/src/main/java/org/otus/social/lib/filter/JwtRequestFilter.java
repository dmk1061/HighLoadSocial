package org.otus.social.lib.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.otus.social.lib.dto.AuthenticationWrap;
import org.otus.social.lib.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws  IOException {

        final String jwt = jwtUtil.extractJwtFromRequest(request);
        try {
            if (StringUtils.hasText(jwt)) {
                final String username = jwtUtil.extractUserName(jwt);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtUtil.validateToken(jwt, username)) {

                        final AuthenticationWrap usernamePasswordAuthenticationToken = new AuthenticationWrap(username, null, new ArrayList<>());
                        usernamePasswordAuthenticationToken.setUserId(Long.valueOf(jwtUtil.extractAllClaims(jwt).get("user_id").toString()));
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        final Map<String, String> customDetails = new HashMap<>();
                        customDetails.put("Bearer", request.getHeader("Authorization"));
                        usernamePasswordAuthenticationToken.setDetails(customDetails);
                        SecurityContextHolder.getContext().setAuthentication((usernamePasswordAuthenticationToken));
                        log.info("request jwt token check passed");
                    }
                }
            }
            filterChain.doFilter(request, response);
        }catch (Exception e ) {
            sendErrorResponse(response, e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
            log.info("request jwt token check not passed");
        }

    }
    private void sendErrorResponse(final HttpServletResponse response, final String message, final int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
