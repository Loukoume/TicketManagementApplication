package com.ennov.it.ticketmanagement.security;

import com.ennov.it.ticketmanagement.repository.UserRepository;
import com.ennov.it.ticketmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {
   @Autowired
   UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.ennov.it.ticketmanagement.entity.User utilisateur=userService.userByUsername(s);
        if (utilisateur == null) {
            throw new UsernameNotFoundException(s);
        }

        //  return new MyUserPrincipal(utilisateur);
         return new User(utilisateur.getUsername(), utilisateur.getEmail(),
                new ArrayList<>());
    }
}
