package org.otus.social.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    private String login;


    @ToString.Exclude
    private String password;

}
