package com.ennov.it.ticketmanagement.service.impl;

/*import com.ennov.it.ticketmanagement.filtre.GenericSpecification;
import com.ennov.it.ticketmanagement.filtre.Requete;*/
import com.ennov.it.ticketmanagement.entity.User;
import com.ennov.it.ticketmanagement.repository.UserRepository;
import com.ennov.it.ticketmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import com.ennov.it.ticketmanagement.service.TicketsService;
import com.ennov.it.ticketmanagement.entity.Ticket;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepository UserRepository;
    @Autowired
    TicketsService ticketsService;

    @Autowired
    public UserServiceImpl(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }

    @Override
    public User save(User User) {
        return UserRepository.save(User);
    }

    @Override
    public List<User> saveAll(List<User> Users) {
        return UserRepository.saveAll(Users);
    }

    @Override
    public List<User> deleteAll(List<User> Users) {
        UserRepository.deleteAll(Users);
        return Users;
    }

    /*@Override
    public Page<User> getListe(Requete requete) {
        GenericSpecification specification = new GenericSpecification(User.class, requete);
        return UserRepository.findAll(specification.specify(), specification.getPageRequest());
    }*/

    @Override
    public List<User> fetchListe() {
        return UserRepository.findAll();
    }

    @Override
    public User update(User User) {
        return UserRepository.save(User);
    }

    @Override
    public void deleteById(Long Id) {

        UserRepository.deleteById(Id);
    }

    @Override
    public void deleteCascade(User User) {

        List<Ticket> tickets = ticketsService.ticketByIdUser(User);
        for (Ticket ticket : tickets) {
            ticketsService.deleteCascade(ticket);
        }
        UserRepository.delete(User);
    }

    @Override
    public User findById(Long id) {
        Optional<User> op = UserRepository.findById(id);
        return op.orElse(null);
    }

    @Override
    public User userByUsername(String username) {
        List<User> users= UserRepository.userByUsername(username);
        return users.isEmpty()?null:users.get(0);
    }

    @Override
    public List<User> userByEmail(String email) {
        return UserRepository.userByEmail(email);
    }

    @Override
    public List<Ticket> getTicketsByUser(Long id) {
        User user=new User();
        user.setIdUser(id);
        return ticketsService.ticketByIdUser(user);
    }
}
