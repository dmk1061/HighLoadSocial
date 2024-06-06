package org.otus.social.main.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    private String username;


    @ToString.Exclude
    private String password;

}
