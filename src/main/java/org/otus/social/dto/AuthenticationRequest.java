package org.otus.social.dto;


import lombok.ToString;
import lombok.Value;

@Value
public class AuthenticationRequest {

    private String login;


    @ToString.Exclude
    private String password;

}
