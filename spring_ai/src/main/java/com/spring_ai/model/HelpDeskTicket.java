package com.spring_ai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "help_desk_tickets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpDeskTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String issue;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime eta;
}