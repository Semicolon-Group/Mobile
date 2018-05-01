/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.entity;

import java.util.Date;


/**
 *
 * @author Elyes
 */
public class Conversation {
    private int id;
    private String label;
    private boolean seen;
    private Date seenDate;
    private int person1Id;
    private int person2Id;

    public Conversation() {
    }
    
    public Conversation(int id){
        this.id = id;
    }

    public Conversation(int id, String label, boolean seen, Date seenDate, int person1Id, int person2Id) {
	this.id = id;
	this.label = label;
	this.seen = seen;
	this.seenDate = seenDate;
	this.person1Id = person1Id;
	this.person2Id = person2Id;
    }

    public Conversation(String label, boolean seen, Date seenDate, int person1Id, int person2Id) {
	this.label = label;
	this.seen = seen;
	this.seenDate = seenDate;
	this.person1Id = person1Id;
	this.person2Id = person2Id;
    }

    public Conversation(int person1Id, int person2Id) {
        this.person1Id = person1Id;
        this.person2Id = person2Id;
    }
    
    

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getLabel() {
	return label;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    public boolean isSeen() {
	return seen;
    }

    public void setSeen(boolean seen) {
	this.seen = seen;
    }

    public Date getSeenDate() {
	return seenDate;
    }

    public void setSeenDate(Date seenDate) {
	this.seenDate = seenDate;
    }

    public int getPerson1Id() {
	return person1Id;
    }

    @Override
    public String toString() {
        return "Conversation{" + "id=" + id + ", label=" + label + ", seen=" + seen + ", seenDate=" + seenDate + ", person1Id=" + person1Id + ", person2Id=" + person2Id + '}';
    }

    public void setPerson1Id(int person1Id) {
	this.person1Id = person1Id;
    }

    public int getPerson2Id() {
	return person2Id;
    }

    public void setPerson2Id(int person2Id) {
	this.person2Id = person2Id;
    }
    
}
