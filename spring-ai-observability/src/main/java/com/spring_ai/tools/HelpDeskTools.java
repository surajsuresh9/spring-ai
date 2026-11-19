package com.spring_ai.tools;

import com.spring_ai.model.HelpDeskTicket;
import com.spring_ai.model.TicketRequest;
import com.spring_ai.service.HelpDeskTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HelpDeskTools {

    private final HelpDeskTicketService helpDeskTicketService;

    public HelpDeskTools(HelpDeskTicketService helpDeskTicketService) {
        this.helpDeskTicketService = helpDeskTicketService;
    }


    @Tool(name = "create ticket", description = "Create the Support ticket", returnDirect = true)
    public String createTicket(@ToolParam(description = "Details to create a Support ticket") TicketRequest request, ToolContext toolContext) {
        String username = (String) toolContext.getContext().get("username");
        HelpDeskTicket ticket = helpDeskTicketService.createTicket(request, username);
        return "Ticket #" + ticket.getId() + " successfully created";
    }

    @Tool(description = "Fetch the status of the open tickets based on given username")
    public List<HelpDeskTicket> fetchTicketStatus(ToolContext toolContext) {
        String username = (String) toolContext.getContext().get("username");
//        throw new RuntimeException("Unable to fetch ticket information");
        return helpDeskTicketService.getTicketsByUsername(username);
    }
}