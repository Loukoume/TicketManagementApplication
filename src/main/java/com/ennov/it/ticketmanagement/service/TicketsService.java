package com.ennov.it.ticketmanagement.service;

//import com.ennov.it.ticketmanagement.filtre.Requete;
import com.ennov.it.ticketmanagement.entity.Ticket;
import org.springframework.data.domain.Page;

import java.util.List;

import com.ennov.it.ticketmanagement.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface TicketsService {

    Ticket save(Ticket ticket);

    List<Ticket> fetchListe();

   // Page<Ticket> getListe(Requete requete);

    Ticket update(Ticket ticket);

    void deleteById(Long ticketId);

    List<Ticket> deleteAll(List<Ticket> tickets);

    List<Ticket> saveAll(List<Ticket> tickets);

    void deleteCascade(Ticket ticket);

    Ticket findById(Long ticket);

    public List<Ticket> ticketByTitre(String titre);

    public List<Ticket> ticketByDescription(String description);

    public List<Ticket> ticketByStatut(String statut);

    List<User> idUser(Ticket ticket);

    List<User> idUserByidUser(User idUser);

    List<Ticket> ticketByIdUser(User idUser);

    List<Ticket> ticketByUser(User User);

    Ticket findTicketById(Long id);

    void delete(Long id);

    List<Ticket> findAllTicketsByUser(String currentUsername);
}
