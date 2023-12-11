package com.tickets.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {

    private String username;
    private String password;
    private String fullname;
    private String phonenumber;
    private String email;
    private String enabled;

    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(String username,
                               String password,
                               String fullname,
                               String phonenumber,
                               String email,
                               String enabled) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.email = email;
        this.enabled = enabled;
    }

}
