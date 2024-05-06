package org.itec.kek.admin.app.models;

import org.itec.kek.admin.app.entity.Menu;
import org.itec.kek.admin.app.entity.Utilisateur;

import java.io.Serializable;
import java.util.List;

public class AuthenticationResponse implements Serializable {

    private final String jwt;
    private List<Menu> utilisateurMenus;
    private Utilisateur utilisateur;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public List<Menu> getUtilisateurMenus() {
        return utilisateurMenus;
    }

    public void setUtilisateurMenus(List<Menu> utilisateurMenus) {
        this.utilisateurMenus = utilisateurMenus;
    }

    public AuthenticationResponse(String jwt, List<Menu> utilisateurMenus) {
        this.jwt = jwt;
        this.utilisateurMenus=utilisateurMenus;
    }

    public AuthenticationResponse(String jwt,  Utilisateur utilisateur) {
        this.jwt = jwt;
      // this.utilisateurMenus = utilisateurMenus;
        this.utilisateur = utilisateur;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getJwt() {
        return jwt;
    }
}
