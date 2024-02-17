package com.campus.clean.arc.api.author.dto.request;

import lombok.Getter;

@Getter
public class RegistrationRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
