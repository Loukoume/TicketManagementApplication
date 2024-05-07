package com.ennov.it.ticketmanagement.controller;

//import com.ennov.it.ticketmanagement.filtre.Requete;
import com.ennov.it.ticketmanagement.entity.Ticket;
import com.ennov.it.ticketmanagement.service.TicketsService;
import com.ennov.it.ticketmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.ennov.it.ticketmanagement.utils.S;

import java.util.List;

import com.ennov.it.ticketmanagement.entity.User;

@RestController
@CrossOrigin("*")
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketsService ticketsService;
    @Autowired
    UserService userService;

    //@ApiOperation("Save a new ticket")
    @PostMapping("/ticket")
    public ResponseEntity<Ticket> save(@RequestBody Ticket ticket) {
        ticket.setCreatedAt(S.timestamp());
        return new ResponseEntity<>(ticketsService.save(ticket), HttpStatus.OK);
    }

   // @ApiOperation("get all tickets")
    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getAll() {
        return new ResponseEntity<>(ticketsService.fetchListe(),HttpStatus.OK);
    }

  //  @ApiOperation("find ticket by id")
    @PostMapping("/tickets/{id}")
    public Ticket findTicketById(@PathVariable Long id) {
        return ticketsService.findTicketById(id);
    }
   // @ApiOperation("update a ticket")
    @PutMapping("/tickets/{id}")
    public ResponseEntity<Ticket> update(@PathVariable Long id, @RequestBody Ticket ticket) {
        // Vérifier si l'utilisateur existe
        Ticket existingTicket = ticketsService.findById(id);
        if (existingTicket == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ticket.setIdUser(existingTicket.getIdUser());
        ticket.setModifiedAt(S.timestamp());

        ticket= ticketsService.update(ticket);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    // Méthode pour assigner un ticket à un utilisateur
   // @ApiOperation("assign Ticket to user")
    @PutMapping("/{id}/assign/{userId}")
    public ResponseEntity<String> assignTicketToUser(@PathVariable Long id, @PathVariable Long userId) {
        // Vérifier si le ticket existe
        Ticket ticket = ticketsService.findById(id);
        if (ticket == null) {
            return new ResponseEntity<>("Ticket non trouvé.", HttpStatus.NOT_FOUND);
        }

        // Vérifier si l'utilisateur existe
        User user = userService.findById(userId);
        if (user == null) {
            return new ResponseEntity<>("Utilisateur non trouvé.", HttpStatus.NOT_FOUND);
        }

        // Assigner le ticket à l'utilisateur
        ticket.setIdUser(user);

        // Enregistrer les modifications dans la base de données
        ticketsService.update(ticket);

        return new ResponseEntity<>("Ticket assigné à l'utilisateur avec succès.", HttpStatus.OK);
    }

    // Méthode pour supprimer un ticket par son ID
  //  @ApiOperation("delete ticket")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicketById(@PathVariable Long id) {
        // Vérifier si le ticket existe
        Ticket ticket = ticketsService.findById(id);
        if (ticket == null) {
            return new ResponseEntity<>("Ticket non trouvé.", HttpStatus.NOT_FOUND);
        }

        // Supprimer le ticket de la base de données
        ticketsService.delete(id);
        return new ResponseEntity<>("Ticket supprimé avec succès.", HttpStatus.OK);
    }

    // Méthode pour récupérer tous les tickets accessibles à l'utilisateur connecté
   // @ApiOperation("user's tickets")
    @GetMapping("/tickets_for_current_user")
    public ResponseEntity<List<Ticket>> getAllTicketsForCurrentUser() {
        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Récupérer tous les tickets associés à l'utilisateur connecté
        List<Ticket> tickets = ticketsService.findAllTicketsByUser(currentUsername);

        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

   // @ApiOperation("get tickets by pagination")
    /*@PostMapping("/list")
    public Page<Ticket> getTicket(@RequestBody Requete requete) {
        return ticketsService.getListe(requete);
    }*/

   // @ApiOperation("Save a new ticket")
    @PostMapping("/update")
    public Ticket update(@RequestBody Ticket ticket) {
        if (ticket.getCreatedAt() == null) ticket.setCreatedAt(S.timestamp());
        else {
            ticket.setModifiedAt(S.timestamp());
        }
        return ticketsService.update(ticket);
    }

    @PostMapping("/delete")
    public Ticket delete(@RequestBody Ticket ticket) {
        ticketsService.deleteById(
                ticket.getIdTicket());
        return ticket;
    }

    @PostMapping("/delete_cascade")
    public Ticket deleteCascade(@RequestBody Ticket ticket) {
        ticketsService.deleteCascade(
                ticket);
        return ticket;
    }

    @PostMapping("/find_by_id")
    public Ticket findById(@RequestBody Ticket ticket) {
        return ticketsService.findById(ticket.getIdTicket());
    }

    @PostMapping("/ticket_by_titre")
    public List<Ticket> ticketByTitre(@RequestBody String titre) {
        return ticketsService.ticketByTitre(titre);
    }

    @PostMapping("/ticket_by_description")
    public List<Ticket> ticketByDescription(@RequestBody String description) {
        return ticketsService.ticketByDescription(description);
    }

    @PostMapping("/ticket_by_statut")
    public List<Ticket> ticketByStatut(@RequestBody String statut) {
        return ticketsService.ticketByStatut(statut);
    }

    @PostMapping("/id_User")
    public List<User> idUser(@RequestBody Ticket ticket) {
        return ticketsService.idUser(ticket);
    }

    @PostMapping("/ticket_by_id_User")
    public List<Ticket> ticketByIdUser(@RequestBody User idUser) {
        return ticketsService.ticketByIdUser(idUser);
    }

    @PostMapping("/ticket_by_User")
    public List<Ticket> ticketByUser(@RequestBody User User) {
        return ticketsService.ticketByUser(User);
    }


}
