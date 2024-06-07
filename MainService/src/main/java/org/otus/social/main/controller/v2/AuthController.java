package org.otus.social.main.controller.v2;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.otus.social.lib.util.JwtUtil;
import org.otus.social.main.dto.AuthenticationRequest;
import org.otus.social.main.dto.AuthenticationResponse;
import org.otus.social.main.dto.RegisterUserDto;
import org.otus.social.main.service.MyUserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth/v2")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailService userDetailsService;
    private final JwtUtil jwtUtil;
    @PostMapping("/getToken")
    public ResponseEntity getToken(@RequestBody final AuthenticationRequest authenticationRequest) {
        log.info("token request received " + authenticationRequest);
        final RegisterUserDto user;
        if (authenticationRequest.getUsername() != null) {
            user = userDetailsService.loadUserDataByUsername(authenticationRequest.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email or phone  wasn't provided in request");
        }
        try {
            final Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), authenticationRequest.getPassword());
            authenticationManager.authenticate(authentication);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
            final String jwt = jwtUtil.generateToken(userDetails, user.getId().toString());
            log.info("Token request completed {} --> {}", authenticationRequest, jwt);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch (NullPointerException | BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad credentials");
        }
    }
}
