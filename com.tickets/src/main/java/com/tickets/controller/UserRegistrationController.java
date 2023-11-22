package com.tickets.controller;

import com.tickets.entity.User;
import com.tickets.service.UserRegistrationDTO;
import com.tickets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adduser")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDTO userRegistrationDTO() {
        return new UserRegistrationDTO();
    }

    @GetMapping
    public String showadduser(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "adduser";
    }

    @PostMapping
    public String adduser(@ModelAttribute("user")
                          UserRegistrationDTO userRegistrationDTO) {

        userService.save(userRegistrationDTO);
        return "redirect:/adduser";

    }


}
