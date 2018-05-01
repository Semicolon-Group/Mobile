/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.entity;

import java.util.HashSet;
import com.semicolon.entity.Enumerations.NotificationType;
import com.semicolon.entity.Enumerations.PhotoType;
import com.semicolon.service.NotificationService;

/**
 *
 * @author vaider
 */
public class Notification {
     private int id;
    private String nameUser;
    private int receiverId;
    private NotificationType type;
    private String content;
    private String date;
    private String icon;
    private int postId;
    private int photoId;
    private boolean seen;

    public Notification() {
    }
    
    public Notification(int id){
        this.id = id;
    }

    public Notification(int id, String nameUser, int receiverId, NotificationType type, String content, String date, String icon, int postId, int photoId, boolean seen) {
	this.id = id;
	this.nameUser = nameUser;
	this.receiverId = receiverId;
	this.type = type;
	this.content = content;
	this.date = date;
	this.icon = icon;
	this.postId = postId;
	this.photoId = photoId;
	this.seen = seen;
    }

    public Notification(String nameUser, int receiverId, NotificationType type, String content, String date, String icon, int postId, int photoId, boolean seen) {
	this.nameUser = nameUser;
	this.receiverId = receiverId;
	this.type = type;
	this.content = content;
	this.date = date;
	this.icon = icon;
	this.postId = postId;
	this.photoId = photoId;
	this.seen = seen;
    }

    public Notification(String content) {
        this.content = content;
    }

    public boolean isSeen() {
	return seen;
    }

    public void setSeen(boolean seen) {
	this.seen = seen;
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

   

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
    

    

    public int getReceiverId() {
	return receiverId;
    }

    public void setReceiverId(int receiverId) {
	this.receiverId = receiverId;
    }

    public NotificationType getType() {
	return type;
    }

    public void setType(NotificationType type) {
	this.type = type;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    public String getIcon() {
	return icon;
    }

    public void setIcon(String icon) {
	this.icon = icon;
    }

    @Override
    public String toString() {
	return "Notification{" + "id=" + id + ", nameUser=" + nameUser + ", receiverId=" + receiverId + ", type=" + type + ", content=" + content + ", date=" + date + ", icon=" + icon + ", postId=" + postId + ", photoId=" + photoId + ", seen=" + seen + '}';
    }
    
    
    
}
