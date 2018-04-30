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
    private String name;
    private String photoUrl;
    private int match;
    private int enemy;

    public MatchCard() {
    }

    public int getMemberId() {
	return memberId;
    }

    public void setMemberId(int memberId) {
	this.memberId = memberId;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getPhotoUrl() {
	return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
	this.photoUrl = photoUrl;
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

    
}