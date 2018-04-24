/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elyes
 */
public class Post {
    private int id;
    private int userId;
    private String userName;
    private String content;
    private int type;
    private String time;
    private int nbrReaction;
    private int nbrComment;
    private int currReaction;
    private List<Reaction> reactions;
    private List<Comment> comments;
    
    public Post(){
        reactions = new ArrayList();
        comments = new ArrayList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNbrReaction() {
        return nbrReaction;
    }

    public void setNbrReaction(int nbrReaction) {
        this.nbrReaction = nbrReaction;
    }

    public int getNbrComment() {
        return nbrComment;
    }

    public void setNbrComment(int nbrComment) {
        this.nbrComment = nbrComment;
    }

    public int getCurrReaction() {
        return currReaction;
    }

    public void setCurrReaction(int currReaction) {
        this.currReaction = currReaction;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", userId=" + userId + ", userName=" + userName + ", content=" + content + ", type=" + type + ", time=" + time + ", nbrReaction=" + nbrReaction + ", nbrComment=" + nbrComment + ", currReaction=" + currReaction + ", reactions=" + reactions + ", comments=" + comments + "}\n";
    }
    
    
}
