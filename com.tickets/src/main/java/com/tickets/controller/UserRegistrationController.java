package com.tickets.controller;
import com.tickets.entity.Role;
import com.tickets.entity.User;
import com.tickets.service.UpdateUserDTO;
import com.tickets.service.UserRegistrationDTO;
import com.tickets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/adduser")
public class UserRegistrationController {
    @Autowired
    private UserService userService;

    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

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
    public String adduser(@ModelAttribute("user") UserRegistrationDTO userRegistrationDTO) {
        userService.save(userRegistrationDTO);
        return "redirect:/adduser?success";
    }

    @GetMapping("/updateuser/{id}")
    public String showUpdateUserForm(@PathVariable("id") Long id, Model model) {
        Optional<User> userOptional  = Optional.ofNullable(userService.getUserById(id));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Role> allRoles = (List<Role>) user.getRole();
            model.addAttribute("user", userOptional.get());
            model.addAttribute("allRoles", allRoles);
            model.addAttribute("userRoles", user.getRole().stream().map(Role::getId).collect(Collectors.toList()));
        } else {
            // Handle the case where the user is not found, you might redirect or show an error page.
            return "redirect:/admin"; // Assuming you want to redirect to the admin page
        }
        return "updateuser";
    }



    @PostMapping("/updateuser")
    public String updateUser(@ModelAttribute("user") UserRegistrationDTO userRegistrationDTO,
                             @RequestParam(name = "userRoles", required = false ) List<String> roleNames,
                             Model model) {
        // Retrieve the existing user from the database
        User existingUser = userService.getUserById(userRegistrationDTO.getId());

        // Update the existing user with the new information
        existingUser.setUsername(userRegistrationDTO.getUsername());
        existingUser.setPassword(userRegistrationDTO.getPassword());
        existingUser.setFullname(userRegistrationDTO.getFullname());
        existingUser.setPhonenumber(userRegistrationDTO.getPhonenumber());
        existingUser.setEmail(userRegistrationDTO.getEmail());
        existingUser.setEnabled(userRegistrationDTO.getEnabled());

        // Update user details
        userService.updateUser(existingUser);

        if (roleNames != null) {  // Check if roleNames is not null before using the stream
            List<Role> selectedRoles = roleNames.stream()
                    .map(Role::new)
                    .collect(Collectors.toList());
            existingUser.setRole(selectedRoles);
            userService.updateUserRoles(existingUser);
        }

        // Redirect to a success page or show an appropriate message
        return "redirect:/admin"   ;
    }





}
