package com.tickets.controller;

import com.tickets.entity.Role;
import com.tickets.entity.User;
import com.tickets.entity.ticket;
import com.tickets.repository.UserRepository;
import com.tickets.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import com.tickets.repository.ticketRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

//    public ticketController(TicketService ticketService) {
//        this.ticketService = ticketService;
//    }

    @GetMapping("/newticket")
    public String showNewTicketPage(Model model) {
        model.addAttribute("ticket", new ticket());
        List<String> priority = Arrays.asList("low", "normal", "urgent");
        model.addAttribute("priority", priority);

        List<String> ticketSource = Arrays.asList("Phone", "Email", "Other");
        model.addAttribute("ticketSource", ticketSource);

        List<String> department = Arrays.asList("acs department", "other");
        model.addAttribute("department", department);

        List<String> helpTopic = Arrays.asList("support", "billing");
        model.addAttribute("helpTopic", helpTopic);

        List<String> deviceModel = Arrays.asList("EF 550 R",
                "SPP R310", "EF 500 1slot cradle",
                "EF 500 4slot cradle", "Xiaomi Redmi9",
                "EF500 R", "EF500 R 1slot cradle",
                "EF500 R 4slot cradle" );
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
        ticketService.saveticket(ticket);
        return "ticketcreated";
    }


    @GetMapping("/opentickets")
    public String getOpentickets(Model model) {
        List<ticket> ticket = ticketRepository.findByticketState(1);
        model.addAttribute("ticket", ticket);
        return "opentickets";
    }

    @GetMapping("/closedtickets")
    public String getClosedtickets(Model model) {
        List<ticket> ticket = ticketRepository.findClosedtickets();
        model.addAttribute("ticket", ticket);
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
                return "redirect:/login?error";}


            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // Set the authentication details
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Redirect the user after successful login
            return "redirect:/home"; // Replace '/dashboard' with your desired endpoint
        } catch (Exception e) {
            // Handle authentication failure, redirect back to login page with an error message
            return "redirect:/login?error"; // You can handle errors in the login page
        }
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/landing")
    public String landing() {
        return "landingpage";
    }

    @GetMapping("/updateTicket/{id}")
    public String showUpdateTicketForm(@PathVariable("id") Long id, Model model) {
        Optional<ticket> optionalTicket = ticketService.getTicketById(id);
        ticket ticket = optionalTicket.orElse(null);

        model.addAttribute("ticket", ticket);
        return "updateTicket";
    }



}
