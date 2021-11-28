package com.java.zendesk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.java.zendesk.model.Ticket;
import com.java.zendesk.repository.TicketRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    
    @Mock
    private TicketRepository tickets;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void addTicket_NewTicket_ReturnTicket() {
        Ticket ticket = new Ticket(1, "2021-11-24T03:10:05Z", "2021-11-24T03:10:05Z", "null", "null", "null", "null");

        when(tickets.findById(any(Integer.class))).thenReturn(Optional.of(ticket));

        Ticket savedTicket = ticketService.getIndividualTicket(ticket.getId());

        assertNotNull(savedTicket);
    }

    @Test
    void connectServer_InvalidCredentials_ReturnError() {
        String url = "https://subdomain.zendesk.com/api/v2/tickets.json";
        
        IOException exception = assertThrows(IOException.class, () -> {
            ticketService.addAllTickets(url);
        });

        assertEquals(exception.getMessage(), "Server returned HTTP response code: 401 for URL: https://subdomain.zendesk.com/api/v2/tickets.json");
    }
}
