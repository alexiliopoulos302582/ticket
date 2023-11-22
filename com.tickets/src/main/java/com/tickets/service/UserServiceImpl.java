package com.tickets.service;

import com.tickets.entity.Role;
import com.tickets.entity.User;
import com.tickets.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public User save(UserRegistrationDTO registrationDTO) {

        User user = new User(
                registrationDTO.getUsername(),
                registrationDTO.getPassword(),
                registrationDTO.getFullname(),
                registrationDTO.getPhonenumber(),
                registrationDTO.getEmail(),
                registrationDTO.isEnabled(),
                Arrays.asList(new Role("ROLE_USER"))
        );
        return userRepository.save(user);



    }
}
