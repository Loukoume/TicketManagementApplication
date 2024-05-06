package org.itec.kek.admin.app.service;

import org.itec.kek.admin.app.entity.Utilisateur;
import org.itec.kek.admin.app.repository.UtilisateurRepository;
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
   UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Utilisateur utilisateur=utilisateurRepository.findByLogin(s);

        if (utilisateur == null) {

            throw new UsernameNotFoundException(s);
        }

        //  return new MyUserPrincipal(utilisateur);
         return new User(utilisateur.getLogin(), utilisateur.getPassword(),
                new ArrayList<>());
    }
}
