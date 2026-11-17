package com.mcpserverremote.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "helpdesk_tickets")
public class HelpDeskTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String issue;

    private String priority;

    private String contactPhone;

    private String status; // e.g., OPEN, IN_PROGRESS, CLOSED

    private LocalDateTime createdAt;

    private LocalDateTime eta;

}
