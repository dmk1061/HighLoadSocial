package org.otus.social.dto;

import lombok.Value;

@Value
public class AuthenticationResponse {

    private final String jwt;

}
