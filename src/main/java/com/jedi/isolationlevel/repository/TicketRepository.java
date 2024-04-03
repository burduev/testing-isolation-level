package com.jedi.isolationlevel.repository;

import com.jedi.isolationlevel.isolation.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
