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

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;

    public UserEntity() {}

    public UserEntity(int id, List<HomeEntity> homes, String firstName, String lastName, String username, String password, String email) {
        this.id = id;
        this.homes = homes;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
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
}
