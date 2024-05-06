package com.ennov.it.ticketmanagement;

import com.ennov.it.ticketmanagement.entity.User;
import com.ennov.it.ticketmanagement.pojo.AuthenticationResponse;
import com.ennov.it.ticketmanagement.repository.UserRepository;
import com.ennov.it.ticketmanagement.security.MyUserDetailsService;
import com.ennov.it.ticketmanagement.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/authentication")
class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;


    @PostMapping( "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getEmail())
            );
        } catch (Exception e) {

            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        List<User> users = userRepository.userByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        if(users.isEmpty()){
            return new ResponseEntity<>("Utilisateur non trouvé.", HttpStatus.NOT_FOUND);
        }else {
         return ResponseEntity.ok(new AuthenticationResponse(jwt, users.get(0)));
        }

    }

}
