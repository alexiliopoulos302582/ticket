package com.tickets.controller;

import com.tickets.entity.Role;
import com.tickets.entity.User;
import com.tickets.entity.ticket;
import com.tickets.repository.UserRepository;
import com.tickets.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.tickets.repository.ticketRepository;
import java.util.Arrays;
import java.util.List;

@Controller
//@RequiredArgsConstructor
public class ticketController {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private ticketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

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


//    @GetMapping("/adduser")
//    public String addUser(Model model) {
//        model.addAttribute("user", new User());
//        return "adduser";
//    }





}
