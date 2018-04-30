/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.entity;

import com.semicolon.entity.Enumerations.SignalReason;
import java.util.Date;

/**
 *
 * @author asus
 */
public class Signal {
     private int id;
    private int senderId;
    private int receiverId;
    private SignalReason reason;
    private boolean state;
    private Date date;
    private String content;

    public Signal() {
    }

    public Signal(String content) {
        this.content = content;
    }

    public Signal(SignalReason reason, String content) {
        this.reason = reason;
        this.content = content;
    }
    
    
    
    public Signal(int id, int senderId, int receiverId, SignalReason reason, boolean state, Date date, String content) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.reason = reason;
        this.state = state;
        this.date = date;
        this.content = content;
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

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public SignalReason getReason() {
        return reason;
    }

    public void setReason(SignalReason reason) {
        this.reason = reason;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Signal{" + "id=" + id + ", senderId=" + senderId + ", receiverId=" + receiverId + ", reason=" + reason + ", state=" + state + ", date=" + date + ", content=" + content + '}';
    }
    
    
    
}
