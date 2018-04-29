/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.service;

import com.codename1.components.ImageViewer;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.URLImage;
import com.semicolon.entity.Enumerations;
import com.semicolon.entity.Photo;
import com.semicolon.javavichuploaderapi.Uploader;
import com.semicolon.mysoulmate.MyApplication;
import static com.semicolon.mysoulmate.MyApplication.theme;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Elyes
 */
public class PhotoService {
    private Photo photo;
    private List<Photo> photos;
    private static PhotoService instance;
    
    public static PhotoService getInstance(){
        if (instance == null)
            instance = new PhotoService();
        return instance;
    }
    private PhotoService(){}
    
    public Photo getPhoto(int photoId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/getPhoto/"+photoId;
        con.setUrl(url);
        con.addResponseListener((e) -> {
            try {
                String str = new String(con.getResponseData());
                JSONParser j = new JSONParser();
                
                Map<String, Object> photoMap = j.parseJSON(new CharArrayReader(str.toCharArray()));
                if(photoMap.isEmpty()){
                    photo = null;
                }else{
                    photo = parsePhoto(photoMap);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return photo;
    }
    
    public List<Photo> getRegularPhotos(int userId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/getPhotos/"+userId;
        con.setUrl(url);
        con.addResponseListener((e) -> {
            try {
                String str = new String(con.getResponseData());
                photos = new ArrayList<>();
                JSONParser j = new JSONParser();
                
                Map<String, Object> likeMap = j.parseJSON(new CharArrayReader(str.toCharArray()));
                List<Map<String, Object>> likeList = (List<Map<String, Object>>) likeMap.get("root");
                if(likeList != null){
                    for(Map<String, Object> map : likeList){
                        photos.add(parsePhoto(map));
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return photos;
    }
    
    public Photo getProfilePhoto(int userId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/getProfilePhoto/"+userId;
        con.setUrl(url);
        con.addResponseListener((e) -> {
            try {
                String str = new String(con.getResponseData());
                JSONParser j = new JSONParser();
                Map<String, Object> photoMap = j.parseJSON(new CharArrayReader(str.toCharArray()));
                
                if(photoMap.isEmpty()){
                    photo = null;
                }else{
                    photo = parsePhoto(photoMap);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        con.addExceptionListener((e) -> {
            photo = null;
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return photo;
    }
    
    public Photo getCoverPhoto(int userId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/getCoverPhoto/"+userId;
        con.setUrl(url);
        con.addResponseListener((e) -> {
            try {
                String str = new String(con.getResponseData());
                JSONParser j = new JSONParser();
                Map<String, Object> photoMap = j.parseJSON(new CharArrayReader(str.toCharArray()));
                
                if(photoMap.isEmpty()){
                    photo = null;
                }else{
                    photo = parsePhoto(photoMap);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        con.addExceptionListener((e) -> {
            photo = null;
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return photo;
    }
    
    public Photo addPhoto(String filePath){
        try {
            Photo p = new Photo("BaseBundle", "imageFile", "http://localhost/mysoulmate/web/app_dev.php/service/seif/uploadPhoto", filePath, MyApplication.MemberId);
            Uploader.upload(p);
            p.setType(Enumerations.PhotoType.REGULAR);
            return p;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    private Photo parsePhoto(Map<String, Object> photoMap){
        Photo p = new Photo();
        p.setId(((Double)photoMap.get("id")).intValue());
        p.setPhotoUri((String)photoMap.get("image"));
        p.setUpdateDate(new Date((((Double)((Map<String, Object>)photoMap.get("date")).get("timestamp")).longValue()*1000)));
        p.setType(Enumerations.PhotoType.values()[((Double)photoMap.get("type")).intValue()]);
        p.setUserId(((Double)((Map<String, Object>)photoMap.get("user")).get("id")).intValue());
        return p;
    }
    
    public void deletePhoto(int photoId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/deletePhoto/"+photoId;
        con.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public void setAsProfilePhoto(int photoId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/setAsProfilePhoto/"+photoId;
        con.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public void setAsCoverPhoto(int photoId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/setAsCoverPhoto/"+photoId;
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