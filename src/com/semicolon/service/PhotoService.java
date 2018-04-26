/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.service;

import com.codename1.components.ImageViewer;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.URLImage;
import static com.semicolon.mysoulmate.MyApplication.theme;

/**
 *
 * @author Elyes
 */
public class PhotoService {
    private static PhotoService instance;
    
    public static PhotoService getInstance(){
        if (instance == null)
            instance = new PhotoService();
        return instance;
    }
    private PhotoService(){}
    
    public void deletePhoto(int photoId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/deletePhoto/"+photoId;
        con.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public void setAsProfilePhoto(int photoId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/setAsCoverPhoto/"+photoId;
        con.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public void setAsCoverPhoto(int photoId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/deletePhoto/"+photoId;
        con.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public ImageViewer EmakeImageViewer(String url){
        ImageViewer img = new ImageViewer();
        EncodedImage encodedImage = EncodedImage.createFromImage(theme.getImage("round.png"),false);
        URLImage uRLImage = URLImage.createToStorage(encodedImage, url.substring(20), "http://localhost" + url);
        uRLImage = URLImage.createToStorage(encodedImage, url.substring(20), "http://localhost" + url);
        img.setImage(uRLImage);
        return img;
    }
}
