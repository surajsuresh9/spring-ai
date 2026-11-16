package com.spring_ai.repository;


import com.spring_ai.model.HelpDeskTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelpDeskTicketRepository extends JpaRepository<HelpDeskTicket, Long> {
    List<HelpDeskTicket> findByUsername(String username);

}
