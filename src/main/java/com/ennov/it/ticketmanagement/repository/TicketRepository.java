package com.ennov.it.ticketmanagement.repository;

import com.ennov.it.ticketmanagement.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.ennov.it.ticketmanagement.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>,
        JpaSpecificationExecutor<Ticket> {

    @Query("select t from ticket_management_ticket t where t.titre = ?1")
    List<Ticket> ticketByTitre(String titre);


    @Query("select t from ticket_management_ticket t where t.description = ?1")
    List<Ticket> ticketByDescription(String description);


    @Query("select t from ticket_management_ticket t where t.statut = ?1")
    List<Ticket> ticketByStatut(String statut);


    @Query("select distinct t.idUser from ticket_management_ticket t where t.idUser.idUser=?1")
    List<User> idUser(long idUser);


    @Query("select t from ticket_management_ticket t where t.idUser.idUser=?1")
    List<Ticket> ticketByIdUser(long idUser);


    @Query("select t from ticket_management_ticket t where t.idUser.idUser=?1")
    List<Ticket> ticketByUser(long idUser);
    @Query("select t from ticket_management_ticket t where t.idUser.username=?1")
    List<Ticket> findAllTicketsByUser(String currentUsername);
}
