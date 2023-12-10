package com.tickets.service;

import com.tickets.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(UserRegistrationDTO registrationDTO);


}
