package com.ennov.it.ticketmanagement.service.impl;
/*
import com.ennov.it.ticketmanagement.filtre.GenericSpecification;
import com.ennov.it.ticketmanagement.filtre.Requete;*/
import com.ennov.it.ticketmanagement.entity.Ticket;
import com.ennov.it.ticketmanagement.repository.TicketRepository;
import com.ennov.it.ticketmanagement.service.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import com.ennov.it.ticketmanagement.entity.User;

import java.util.Optional;

@Service
public class TicketsServiceImpl implements TicketsService {

    TicketRepository ticketRepository;

    @Autowired
    public TicketsServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> saveAll(List<Ticket> tickets) {
        return ticketRepository.saveAll(tickets);
    }

    @Override
    public List<Ticket> deleteAll(List<Ticket> tickets) {
        ticketRepository.deleteAll(tickets);
        return tickets;
    }

   /* @Override
    public Page<Ticket> getListe(Requete requete) {
        GenericSpecification specification = new GenericSpecification(Ticket.class, requete);
        return ticketRepository.findAll(specification.specify(), specification.getPageRequest());
    }*/

    @Override
    public List<Ticket> fetchListe() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket update(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteById(Long Id) {

        ticketRepository.deleteById(Id);
    }

    @Override
    public void deleteCascade(Ticket ticket) {

        ticketRepository.delete(ticket);
    }

    @Override
    public Ticket findById(Long ticket) {
        Optional<Ticket> op = ticketRepository.findById(ticket);
        return op.orElse(null);
    }

    @Override
    public List<Ticket> ticketByTitre(String titre) {
        return ticketRepository.ticketByTitre(titre);
    }

    @Override
    public List<Ticket> ticketByDescription(String description) {
        return ticketRepository.ticketByDescription(description);
    }

    @Override
    public List<Ticket> ticketByStatut(String statut) {
        return ticketRepository.ticketByStatut(statut);
    }

    @Override
    public List<User> idUser(Ticket ticket) {
        return ticketRepository.idUser(ticket.getIdUser().getIdUser());
    }

    @Override
    public List<User> idUserByidUser(User User) {
        return ticketRepository.idUser(User.getIdUser());
    }

    @Override
    public List<Ticket> ticketByIdUser(User User) {
        return ticketRepository.ticketByIdUser(User.getIdUser());
    }

    @Override
    public List<Ticket> ticketByUser(User User) {
        return ticketRepository.ticketByUser(User.getIdUser());
    }

    @Override
    public Ticket findTicketById(Long id) {
        Optional<Ticket> op = ticketRepository.findById(id);
        return op.orElse(null);
    }

    @Override
    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public List<Ticket> findAllTicketsByUser(String currentUsername) {
        return ticketRepository.findAllTicketsByUser(currentUsername);
    }


}
