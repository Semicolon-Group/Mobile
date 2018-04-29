/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.entity;

/**
 *
 * @author Elyes
 */
public abstract class User {

    private int id;
    private String pseudo;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String ip;
    private int port;

    public User() {
    }

    public User(String pseudo, String firstname, String lastname, String email, String password) {
        this.pseudo = pseudo;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
    
    
    public User(int id){
        this.id = id;
    }

    public User(int id, String pseudo, String firstname, String lastname, String email, String password, String ip, int port) {
        this.id = id;
        this.pseudo = pseudo;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.ip = ip;
        this.port = port;
    }

    public User(String pseudo, String firstname, String lastname, String email, String password, String ip, int port) {
        this.pseudo = pseudo;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.ip = ip;
        this.port = port;
    }

    public User(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", pseudo=" + pseudo + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password=" + password + ", ip=" + ip + ", port=" + port + '}';
    }
    
}