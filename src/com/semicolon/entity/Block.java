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
public class Block {
    private int senderId;
    private int receiverId;
    private Date date;
    
    public Block() {
    }

    public Block(int senderId, int receiverId, Date date) {
	this.senderId = senderId;
	this.receiverId = receiverId;
	this.date = date;
    }

    public int getSenderId() {
	return senderId;
    }

    public void setSenderId(int senderId) {
	this.senderId = senderId;
    }

    public int getReceiverId() {
	return receiverId;
    }

    public void setReceiverId(int receiverId) {
	this.receiverId = receiverId;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }
}
