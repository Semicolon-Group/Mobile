/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.entity;

import java.sql.Timestamp;



/**
 *
 * @author Elyes
 */
public class StatusPost extends Post{
    private String Content;

    public StatusPost() {
    }
    
    public StatusPost(int id){
        super(id);
    }

    public StatusPost(String Content, int ownerId, Timestamp date) {
        super(ownerId, date);
        this.Content = Content;
    }

    public StatusPost(String Content, int id, int ownerId, Timestamp date) {
        super(id, ownerId, date);
        this.Content = Content;
    }

    
    
    public String getContent() {
	return Content;
    }

    public void setContent(String Content) {
	this.Content = Content;
    }

    @Override
    public String toString() {
        return super.toString() + "StatusPost{" + "Content=" + Content + '}';
    }
    
}
