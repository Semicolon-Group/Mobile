/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Elyes
 */
public abstract class Post {
    private int id;
    private int ownerId;
    private Timestamp date;
    private List<Reaction> reactions;

    public Post() {
        reactions = new ArrayList<>();
    }
    
    public Post(int id){
        this.id = id;
        reactions = new ArrayList<>();
    }

    public Post(int ownerId, Timestamp date) {
	this.ownerId = ownerId;
	this.date = date;
        reactions = new ArrayList<>();
    }

    public Post(int id, int ownerId, Timestamp date) {
	this.id = id;
	this.ownerId = ownerId;
	this.date = date;
        reactions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", ownerId=" + ownerId + ", date=" + date + ", reactions=" + reactions + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        hash = 97 * hash + this.ownerId;
        hash = 97 * hash + Objects.hashCode(this.date);
        hash = 97 * hash + Objects.hashCode(this.reactions);
        return hash;
    }

    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Post other = (Post) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
