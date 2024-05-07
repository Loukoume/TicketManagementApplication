package com.ennov.it.ticketmanagement.controller;

import com.ennov.it.ticketmanagement.entity.Ticket;
///import com.ennov.it.ticketmanagement.filtre.Requete;
import com.ennov.it.ticketmanagement.entity.User;
import com.ennov.it.ticketmanagement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ennov.it.ticketmanagement.utils.S;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public User save(@RequestBody User User) {
        User.setCreatedAt(S.timestamp());
        return userService.save(User);
    }
   // @ApiOperation("Save a new ticket")
    @GetMapping("/users")
    public List<User> getAll() {
        return userService.fetchListe();
    }

    @GetMapping("/users/{id}/ticket")
    public List<Ticket> getTicketsByUser(@PathVariable Long id) {
        return userService.getTicketsByUser(id);
    }
    /*@PostMapping("/list")
    public Page<User> getUser(@RequestBody Requete requete) {
        return userService.getListe(requete);
    }*/

    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        // Vérifier si l'utilisateur existe
        User existingUser = userService.findById(id);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setIdUser(existingUser.getIdUser());
        if (user.getCreatedAt() == null) user.setCreatedAt(S.timestamp());
        else {
            user.setModifiedAt(S.timestamp());
        }
        user=userService.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public User delete(@RequestBody User user) {
        userService.deleteById(
                user.getIdUser());
        return user;
    }

    @PostMapping("/delete_cascade")
    public User deleteCascade(@RequestBody User User) {
        userService.deleteCascade(
                User);
        return User;
    }

    @PostMapping("/find_by_id")
    public User findById(@RequestBody User User) {
        return userService.findById(User.getIdUser());
    }

    @PostMapping("/user_by_username")
    public User UserByUsername(@RequestBody String username) {
        return userService.userByUsername(username);
    }

    @PostMapping("/user_by_email")
    public List<User> userByEmail(@RequestBody String email) {
        return userService.userByEmail(email);
    }
}
