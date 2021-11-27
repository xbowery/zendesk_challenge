package com.java.zendesk.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.java.zendesk.model.Ticket;
import com.java.zendesk.repository.TicketRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    
    @Mock
    private static TicketRepository tickets;

    @Mock
    private TicketService ticketService;

    // @Test
    // void addTicket_NewTicket_ReturnTicket() {
    //     Ticket ticket = new Ticket(1, "2021-11-24T03:10:05Z", "2021-11-24T03:10:05Z", "null", "null", "null", "null");

    //     when(tickets.findById(any(Integer.class))).thenReturn(Optional.of(ticket));
    //     when(tickets.save(any(Ticket.class))).thenReturn(ticket);

    //     Ticket savedTicket = TicketService.getIndividualTicket(ticket.getId());

    //     System.out.println(savedTicket);

    //     assertNotNull(savedTicket);
    // }
}
