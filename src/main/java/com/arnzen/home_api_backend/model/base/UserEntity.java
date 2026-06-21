package com.arnzen.home_api_backend.model.base;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HomeEntity> homes = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmailConfirmationEntity> emailConfirmationEntities = new ArrayList<>();

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private boolean emailConfirmed;

    public UserEntity() {}

    public UserEntity(int id, List<HomeEntity> homes, List<EmailConfirmationEntity> emailConfirmationEntities, String firstName, String lastName, String username, String password, String email, boolean emailConfirmed) {
        this.id = id;
        this.homes = homes;
        this.emailConfirmationEntities = emailConfirmationEntities;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.emailConfirmed = emailConfirmed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<HomeEntity> getHomes() {
        return homes;
    }

    public void setHomes(List<HomeEntity> homes) {
        this.homes = homes;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public List<EmailConfirmationEntity> getEmailConfirmationEntities() {
        return emailConfirmationEntities;
    }

    public void setEmailConfirmationEntities(List<EmailConfirmationEntity> emailConfirmationEntities) {
        this.emailConfirmationEntities = emailConfirmationEntities;
    }
}
