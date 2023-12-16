package com.tickets.controller;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

import com.tickets.entity.Role;
import com.tickets.entity.User;
import com.tickets.entity.ticket;
import com.tickets.repository.UserRepository;
import com.tickets.service.TicketService;
import com.tickets.service.UserRegistrationDTO;
import com.tickets.service.UserService;
import com.tickets.service.UserTicketCountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import com.tickets.repository.ticketRepository;

import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
//@RequiredArgsConstructor
public class ticketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private ticketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

//

    @GetMapping("/newticket")
    public String showNewTicketPage(Model model) {
        model.addAttribute("ticket", new ticket());

        List<String> ticketSource = Arrays.asList("Phone", "Email", "Other");
        model.addAttribute("ticketSource", ticketSource);

        List<String> department = Arrays.asList("acs department", "other");
        model.addAttribute("department", department);

        List<String> priority = Arrays.asList("low", "normal", "urgent");
        model.addAttribute("priority", priority);

        List<String> helpTopic = Arrays.asList("support", "billing");
        model.addAttribute("helpTopic", helpTopic);

        List<String> deviceModel = Arrays.asList("EF 550 R",
                "SPP R310",
                "EF 500 1slot cradle",
                "EF 500 4slot cradle",
                "Xiaomi Redmi9",
                "EF500 R",
                "EF500 R 1slot cradle",
                "EF500 R 4slot cradle");
        model.addAttribute("deviceModel", deviceModel);

        List<String> category = Arrays.asList(
                "hardware issue", "software issue", "network - communications",
                "application properties", "hardware properties", "hardware misuse",
                "software misuse", "general info software/hardware", "caused damage",
                "theft / loss");
        model.addAttribute("category", category);


        List<String> assignedTo = Arrays.asList(
                "Tsaloukidis Al", "Psaras B", "Giannetakis St",
                "Maistrou M", "Kritikos Sp", "Tzouvelis St");
        model.addAttribute("assignedTo", assignedTo);


        return "newticket";
    }

    @PostMapping("/newticket")
    public String createticket(@ModelAttribute ticket ticket) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        ticket.setCreationDate(dateFormat.format(new Date()));
        ticketService.saveticket(ticket);
        return "ticketcreated";
    }

    //    @GetMapping("/opentickets")
//    public String getOpentickets(Model model) {
//        List<ticket> ticket = ticketRepository.findByticketState(1);
//        model.addAttribute("ticket", ticket);
//        return "opentickets";
//    }
    @GetMapping("/opentickets")
    public String getopentickets(@RequestParam(name = "search", required = false) String search,
                                 Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());

        }
        List<ticket> tickets;
        if (search != null && !search.isEmpty()) {
            tickets = ticketRepository.findOpenticketsBySearch(search);
        } else {
            tickets = ticketRepository.findByticketState(1);
        }
        model.addAttribute("ticket", tickets);
        model.addAttribute("search", search);
        return "opentickets";
    }

    //    @GetMapping("/closedtickets")
//    public String getClosedtickets(Model model) {
//        List<ticket> ticket = ticketRepository.findClosedtickets();
//
//        model.addAttribute("ticket", ticket);
//        return "closedtickets";
//    }
    @GetMapping("/closedtickets")
    public String getClosedtickets(
            @RequestParam(name = "search", required = false) String search,
            Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());
        }
        List<ticket> tickets;
        if (search != null && !search.isEmpty()) {
            tickets = ticketRepository.findClosedticketsBySearch(search);
        } else {
            tickets = ticketRepository.findClosedtickets();
        }
        model.addAttribute("ticket", tickets);
        model.addAttribute("search", search);
        return "closedtickets";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        try {
            if (email == null || email.isEmpty()) {
                // Handle the case where email is empty
                return "redirect:/login?error";
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/home"; // Replace '/dashboard' with your desired endpoint
        } catch (Exception e) {
            // Handle authentication failure, redirect back to login page with an error message
            return "redirect:/login?error"; // You can handle errors in the login page
        }
    }


    @GetMapping("/landing")
    public String landing() {
        return "landingpage";
    }

    @GetMapping("/updateTicket/{id}")
    public String showUpdateTicketForm(@PathVariable("id") Long id, Model model) {
        Optional<ticket> optionalTicket = ticketService.getTicketById(id);
        ticket ticket = optionalTicket.orElse(null);

        List<String> ticketSource = Arrays.asList("Phone", "Email", "Other");
        model.addAttribute("ticketSource", ticketSource);

        model.addAttribute("ticket", ticket);
        return "updateTicket";
    }

    @PostMapping("/update")
    public String updateTicket(@ModelAttribute ticket updatedticket) {

        ticketService.saveticket(updatedticket);
        return "redirect:/home";

    }

    @GetMapping("/admin")
    public String getUserList(Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("user", userList);
        return "admin";
    }

//


    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loggedInUserEmail = userDetails.getUsername();

        List<User> userList = userService.getAllUsers();
        Long userid = null;
        System.out.println(userDetails.getUsername()+" this is one ");

        for (User i :userList) {
            if (Objects.equals(i.getEmail(), loggedInUserEmail)) {
             userid=i.getId();
            }
        }


        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("user", userList);
        model.addAttribute("userid", userid);

        ticketRepository.truncateUserTicketCounts();
        ticketRepository.updateUserTicketCounts();

        List<UserTicketCountDTO> ticketCounts = ticketRepository.getUserTicketCountsFromTable();
        model.addAttribute("ticketCounts", ticketCounts);




        return "home";
    }



}





