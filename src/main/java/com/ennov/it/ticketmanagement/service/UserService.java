package com.ennov.it.ticketmanagement.service;

import com.ennov.it.ticketmanagement.entity.Ticket;
//import com.ennov.it.ticketmanagement.filtre.Requete;
import com.ennov.it.ticketmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {

    User save(User utilisateur);

    List<User> fetchListe();

    //Page<User> getListe(Requete requete);

    User update(User utilisateur);

    void deleteById(Long utilisateurId);

    List<User> deleteAll(List<User> utilisateurs);

    List<User> saveAll(List<User> utilisateurs);

    void deleteCascade(User utilisateur);

    User findById(Long id);

    public User userByUsername(String username);

    public List<User> userByEmail(String email);

    List<Ticket> getTicketsByUser(Long id);
}
