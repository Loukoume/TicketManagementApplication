package com.ennov.it.ticketmanagement.pojo;


import com.ennov.it.ticketmanagement.entity.User;
import lombok.Data;

import java.io.Serializable;
@Data
public class AuthenticationResponse implements Serializable {
    private  String jwt;
    private User user;


    public AuthenticationResponse(String jwt, User user) {
        this.jwt=jwt;
        this.user=user;
    }
}
