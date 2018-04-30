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
public class MatchCard {
    private int memberId;
    private String pseudo;
    private String photoUrl;
    private int age;
    private String city;
    private int match;
    private int enemy;
    private int lastLogin;
    private boolean online;
    private double distance;

    public MatchCard() {
    }

    public MatchCard(int memberId, String pseudo, String photoUrl, int age, String city, int match, int enemy, int lastLogin, boolean online, float distance) {
        this.memberId = memberId;
        this.pseudo = pseudo;
        this.photoUrl = photoUrl;
        this.age = age;
        this.city = city;
        this.match = match;
        this.enemy = enemy;
        this.lastLogin = lastLogin;
        this.online = online;
        this.distance = distance;
    }

    public MatchCard(int memberId, String pseudo, String photoUrl, int age, String city, int match, int enemy) {
        this.memberId = memberId;
        this.pseudo = pseudo;
        this.photoUrl = photoUrl;
        this.age = age;
        this.city = city;
        this.match = match;
        this.enemy = enemy;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getMatch() {
        return match;
    }

    public void setMatch(int match) {
        this.match = match;
    }

    public int getEnemy() {
        return enemy;
    }

    public void setEnemy(int enemy) {
        this.enemy = enemy;
    }

    public int getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(int lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "MatchCard{" + "memberId=" + memberId + ", pseudo=" + pseudo + ", photoUrl=" + photoUrl + ", age=" + age + ", city=" + city + ", match=" + match + ", enemy=" + enemy + ", lastLogin=" + lastLogin + ", online=" + online + ", distance=" + distance + "}\n";
    }
    
    
}
