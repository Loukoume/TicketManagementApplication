package com.ennov.it.ticketmanagement.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.ennov.it.ticketmanagement.repository.TicketRepository;
import com.ennov.it.ticketmanagement.service.impl.TicketsServiceImpl;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.ennov.it.ticketmanagement.entity.Ticket;
import com.ennov.it.ticketmanagement.service.TicketsService;
import org.junit.jupiter.api.Test;

public class TicketsServiceTest extends Mockito{

    @Test
    public void testGetAllTickets() {
        // Mocking the TicketsService
        TicketsService ticketsService = mock(TicketsService.class);

        // Stubbing the behavior of the getAllTickets() method
        Ticket ticket1=new Ticket();
        ticket1.setTitre("Ticket 1");
        ticket1.setDescription("Description du Ticket 1");

        Ticket ticket2=new Ticket();
        ticket2.setTitre("Ticket 2");
        ticket2.setDescription("Description du Ticket 2");

        when(ticketsService.fetchListe()).thenReturn(Arrays.asList(ticket1, ticket2));

        // Calling the method to be tested
        List<Ticket> tickets = ticketsService.fetchListe();

        // Verifying the result
        assertEquals(2, tickets.size());
        assertEquals("Ticket 1", tickets.get(0).getTitre());
        assertEquals("Ticket 2", tickets.get(1).getTitre());
    }
    @Test
    public void testSave() {
        // Mocking the TicketRepository
        TicketRepository ticketRepository = mock(TicketRepository.class);

        // Stubbing the behavior of the save() method
        Ticket ticket=new Ticket();
        ticket.setTitre("Ticket");
        ticket.setDescription("Description du Ticket");

        when(ticketRepository.save(ticket)).thenReturn(ticket);

        // Calling the method to be tested
        TicketsServiceImpl ticketService = new TicketsServiceImpl(ticketRepository);
        Ticket savedTicket = ticketService.save(ticket);

        // Verifying the result
        assertNotNull(savedTicket);
        assertEquals(ticket, savedTicket);
    }
    @Test
    public void testSaveAll() {
        // Mocking the TicketRepository
        TicketRepository ticketRepository = mock(TicketRepository.class);

        // Stubbing the behavior of the saveAll() method
        Ticket ticket1=new Ticket();
        ticket1.setTitre("Ticket 1");
        ticket1.setDescription("Description du Ticket 1");

        Ticket ticket2=new Ticket();
        ticket2.setTitre("Ticket 2");
        ticket2.setDescription("Description du Ticket 2");

        List<Ticket> tickets = Arrays.asList(ticket1,ticket2);
        when(ticketRepository.saveAll(tickets)).thenReturn(tickets);

        // Calling the method to be tested
        TicketsServiceImpl ticketService = new TicketsServiceImpl(ticketRepository);
        List<Ticket> savedTickets = ticketService.saveAll(tickets);

        // Verifying the result
        assertNotNull(savedTickets);
        assertEquals(tickets.size(), savedTickets.size());
        assertTrue(savedTickets.containsAll(tickets));
    }

    @Test
    public void testDeleteAll() {
        // Mocking the TicketRepository
        TicketRepository ticketRepository = mock(TicketRepository.class);

        // Stubbing the behavior of the deleteAll() method
        Ticket ticket1=new Ticket();
        ticket1.setTitre("Ticket 1");
        ticket1.setDescription("Description du Ticket 1");

        Ticket ticket2=new Ticket();
        ticket2.setTitre("Ticket 2");
        ticket2.setDescription("Description du Ticket 2");

        List<Ticket> tickets = Arrays.asList(ticket1,ticket2);
        when(ticketRepository.saveAll(tickets)).thenReturn(tickets);

        // Calling the method to be tested
        TicketsServiceImpl ticketService = new TicketsServiceImpl(ticketRepository);
        List<Ticket> deletedTickets = ticketService.deleteAll(tickets);

        // Verifying the result
        assertNotNull(deletedTickets);
        assertEquals(tickets.size(), deletedTickets.size());
    }
    @Test
    public void testFetchListe() {
        // Mocking the TicketRepository
        TicketRepository ticketRepository = mock(TicketRepository.class);

        // Stubbing the behavior of the fetchListe() method
        Ticket ticket1=new Ticket();
        ticket1.setTitre("Ticket 1");
        ticket1.setDescription("Description du Ticket 1");

        Ticket ticket2=new Ticket();
        ticket2.setTitre("Ticket 2");
        ticket2.setDescription("Description du Ticket 2");

        List<Ticket> tickets = Arrays.asList(ticket1,ticket2);
        when(ticketRepository.findAll()).thenReturn(tickets);

        // Calling the method to be tested
        TicketsServiceImpl ticketService = new TicketsServiceImpl(ticketRepository);
        List<Ticket> fetchedTickets = ticketService.fetchListe();

        // Verifying the result
        assertNotNull(fetchedTickets);
        assertEquals(tickets.size(), fetchedTickets.size());
        assertTrue(fetchedTickets.containsAll(tickets));
    }

    @Test
    public void testUpdate() {
        // Mocking the TicketRepository
        TicketRepository ticketRepository = mock(TicketRepository.class);

        // Stubbing the behavior of the update() method
        Ticket ticket=new Ticket();
        ticket.setTitre("Ticket 1");
        ticket.setDescription("Description du Ticket 1");

        when(ticketRepository.save(ticket)).thenReturn(ticket);

        // Calling the method to be tested
        TicketsServiceImpl ticketService = new TicketsServiceImpl(ticketRepository);
        Ticket updatedTicket = ticketService.update(ticket);

        // Verifying the result
        assertNotNull(updatedTicket);
        assertEquals(ticket, updatedTicket);
    }

    @Test
    public void testDeleteById() {
        // Mocking the TicketRepository
        TicketRepository ticketRepository = mock(TicketRepository.class);

        // Stubbing the behavior of the deleteById() method
        doNothing().when(ticketRepository).deleteById(1L);

        // Calling the method to be tested
        TicketsServiceImpl ticketService = new TicketsServiceImpl(ticketRepository);
        ticketService.deleteById(1L);

        // Verifying the result
        verify(ticketRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCascade() {
        // Mocking the TicketRepository
        TicketRepository ticketRepository = mock(TicketRepository.class);

        // Stubbing the behavior of the delete() method
        Ticket ticket=new Ticket();
        ticket.setTitre("Ticket 1");
        ticket.setDescription("Description du Ticket 1");
        doNothing().when(ticketRepository).delete(ticket);

        // Calling the method to be tested
        TicketsServiceImpl ticketService = new TicketsServiceImpl(ticketRepository);
        ticketService.deleteCascade(ticket);

        // Verifying the result
        verify(ticketRepository, times(1)).delete(ticket);
    }

    @Test
    public void testFindById() {
        // Mocking the TicketRepository
        TicketRepository ticketRepository = mock(TicketRepository.class);

        // Stubbing the behavior of the findById() method
        Ticket ticket=new Ticket();
        ticket.setTitre("Ticket 1");
        ticket.setDescription("Description du Ticket 1");
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        // Calling the method to be tested
        TicketsServiceImpl ticketService = new TicketsServiceImpl(ticketRepository);
        Ticket foundTicket = ticketService.findById(1L);

        // Verifying the result
        assertNotNull(foundTicket);
        assertEquals(ticket, foundTicket);
    }

    @Test
    public void testTicketByTitre() {
        // Mocking the TicketRepository
        TicketRepository ticketRepository = mock(TicketRepository.class);

        // Stubbing the behavior of the ticketByTitre() method
        Ticket ticket1=new Ticket();
        ticket1.setTitre("Ticket 1");
        ticket1.setDescription("Description du Ticket 1");

        Ticket ticket2=new Ticket();
        ticket2.setTitre("Ticket 2");
        ticket2.setDescription("Description du Ticket 2");

        List<Ticket> tickets = Arrays.asList(ticket1,ticket2);
        when(ticketRepository.ticketByTitre("Ticket 1")).thenReturn(tickets);

        // Calling the method to be tested
        TicketsServiceImpl ticketService = new TicketsServiceImpl(ticketRepository);
        List<Ticket> foundTickets = ticketService.ticketByTitre("Ticket 1");

        // Verifying the result
        assertNotNull(foundTickets);
        assertEquals(tickets.size(), foundTickets.size());
        assertTrue(foundTickets.containsAll(tickets));
    }

    @Test
    public void testTicketByDescription() {
        // Mocking the TicketRepository
        TicketRepository ticketRepository = mock(TicketRepository.class);

        // Stubbing the behavior of the ticketByDescription() method
        Ticket ticket1=new Ticket();
        ticket1.setTitre("Ticket 1");
        ticket1.setDescription("Description du Ticket 1");

        Ticket ticket2=new Ticket();
        ticket2.setTitre("Ticket 2");
        ticket2.setDescription("Description du Ticket 2");

        List<Ticket> tickets = Arrays.asList(ticket1,ticket2);
        when(ticketRepository.ticketByDescription("Description du Ticket 1")).thenReturn(tickets);

        // Calling the method to be tested
        TicketsServiceImpl ticketService = new TicketsServiceImpl(ticketRepository);
        List<Ticket> foundTickets = ticketService.ticketByDescription("Description du Ticket 1");

        // Verifying the result
        assertNotNull(foundTickets);
        assertEquals(tickets.size(), foundTickets.size());
        assertTrue(foundTickets.containsAll(tickets));
    }

    @Test
    public void testTicketByStatut() {
        // Mocking the TicketRepository
        TicketRepository ticketRepository = mock(TicketRepository.class);

        // Stubbing the behavior of the ticketByStatut() method
        Ticket ticket1=new Ticket();
        ticket1.setTitre("Ticket 1");
        ticket1.setDescription("Description du Ticket 1");

        Ticket ticket2=new Ticket();
        ticket2.setTitre("Ticket 2");
        ticket2.setDescription("Description du Ticket 2");

        List<Ticket> tickets = Arrays.asList(ticket1,ticket2);
        when(ticketRepository.ticketByStatut("En cours")).thenReturn(tickets);

        // Calling the method to be tested
        TicketsServiceImpl ticketService = new TicketsServiceImpl(ticketRepository);
        List<Ticket> foundTickets = ticketService.ticketByStatut("En cours");

        // Verifying the result
        assertNotNull(foundTickets);
        assertEquals(tickets.size(), foundTickets.size());
        assertTrue(foundTickets.containsAll(tickets));
    }

}


