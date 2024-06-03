package org.otus.social.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    private String username;


    @ToString.Exclude
    private String password;

}
