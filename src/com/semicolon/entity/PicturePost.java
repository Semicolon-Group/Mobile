package com.semicolon.entity;

import java.sql.Timestamp;

public class PicturePost extends Post{
    private String url;
    private int photoId;
    
    public PicturePost(){
        super();
    }
    
    public PicturePost(int id){
        super(id);
    }
    
    public PicturePost(int ownerId, Timestamp date) {
        super(ownerId, date);
    }

    public PicturePost(int id, int ownerId, Timestamp date) {
        super(id, ownerId, date);
    }

    public PicturePost(String url, int id, int ownerId, Timestamp date) {
	super(id, ownerId, date);
	this.url = url;
    }
    
    public PicturePost(String url, int photoId, int id, int ownerId, Timestamp date) {
	super(id, ownerId, date);
	this.url = url;
        this.photoId = photoId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
    
    public String getUrl(){
        return this.url;
    }
}
