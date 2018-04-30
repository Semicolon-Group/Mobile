/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.entity;

import java.util.Date;

/**
 *
 * @author asus
 */
public class Feedback {
    private int id;
    private int senderId;
    private String content;
    private boolean state;
    private Date date;

    public Feedback() {
    }

    public Feedback(String content) {
        this.content = content;
    }
    
    

    public Feedback(int id, int senderId, String content, boolean state, Date date) {
        this.id = id;
        this.senderId = senderId;
        this.content = content;
        this.state = state;
        this.date = date;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Feedback{" + "id=" + id + ", senderId=" + senderId + ", content=" + content + ", state=" + state + ", date=" + date + '}';
    }
    
    
    
    
    
}
