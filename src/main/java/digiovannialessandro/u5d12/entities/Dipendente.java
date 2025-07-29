package digiovannialessandro.u5d12.entities;

import digiovannialessandro.u5d12.enums.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "dipendenti")
public class Dipendente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String urlImg;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;


    public Dipendente() {
    }

    public Dipendente(String email, String surname, String name, String username,String password) {
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.username = username;
        this.password=password;
        this.role=Role.USER;
    }

    public int getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Questo Metodo deve tornare una lista di ruoli dell'utente. Per essere più precisi vuole che venga restituita una collection di oggetti
        // che estendono GrantedAuthority. SimpleGrantedAuthority è una classe che rappresenta i ruoli degli utenti nel mondo Spring Security, essa
        // estende GrantedAuthority. Dobbiamo passare il nostro ruolo (enum), convertito in string al costruttore dell'oggetto
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }



    @Override
    public String toString() {
        return "Dipendente{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
