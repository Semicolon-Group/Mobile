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
public class Comment {
    private int id;
    private int senderId;
    private int receiverId;
    private int postId;
    private int photoId;
    private String content;
    private Date date;

    public Comment() {
    }

    public Comment(int id, int senderId, int receiverId, int postId, int photoId, String content, Date date) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.postId = postId;
        this.photoId = photoId;
        this.content = content;
        this.date = date;
    }

    public Comment(int senderId, int receiverId, int postId, int photoId, String content, Date date) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.postId = postId;
        this.photoId = photoId;
        this.content = content;
        this.date = date;
    }

    public Comment(int senderId, int receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", senderId=" + senderId + ", receiverId=" + receiverId + ", postId=" + postId + ", photoId=" + photoId + ", content=" + content + ", date=" + date + '}';
    }
    
    
}
