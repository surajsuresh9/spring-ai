package com.spring_ai.service;

import com.spring_ai.model.HelpDeskTicket;
import com.spring_ai.model.TicketRequest;
import com.spring_ai.repository.HelpDeskTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HelpDeskTicketService {

    private final HelpDeskTicketRepository helpDeskTicketRepository;

    public HelpDeskTicketService(HelpDeskTicketRepository helpDeskTicketRepository) {
        this.helpDeskTicketRepository = helpDeskTicketRepository;
    }

    public HelpDeskTicket createTicket(TicketRequest request, String username) {
        HelpDeskTicket helpDeskTicket = HelpDeskTicket.builder()
                .issue(request.issue())
                .username(username)
                .status("OPEN")
                .createdAt(LocalDateTime.now())
                .eta(LocalDateTime.now().plusDays(7))
                .build();
        return helpDeskTicketRepository.save(helpDeskTicket);
    }

    public List<HelpDeskTicket> getTicketsByUsername(String username) {
        return helpDeskTicketRepository.findByUsername(username);
    }
}
