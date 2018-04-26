package com.semicolon.service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.semicolon.entity.Enumerations;
import com.semicolon.entity.Like;
import com.semicolon.entity.Member;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LikeService {
    private List<Like> likes;
    private static LikeService instance;
    
    private LikeService(){}
    
    public static LikeService getInstance(){
        if(instance == null)
            instance = new LikeService();
        return instance;
    }
    
    public List<Like> getMemberLikes(int id){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/getUserLikes/"+id;
        con.setUrl(url);
        con.addResponseListener((e) -> {
            try {
                String str = new String(con.getResponseData());
                likes = new ArrayList<>();
                JSONParser j = new JSONParser();
                
                Map<String, Object> likeMap = j.parseJSON(new CharArrayReader(str.toCharArray()));
                List<Map<String, Object>> likeList = (List<Map<String, Object>>) likeMap.get("root");
                if(likeList != null){
                    for(Map<String, Object> map : likeList){
                        likes.add(parseLike(map));
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return likes;
    }
    
    private Like parseLike(Map<String, Object> map){
        Like l = new Like();
        l.setDate(new Date((((Double)((Map<String, Object>)map.get("date")).get("timestamp")).longValue()*1000)));
        l.setSenderId(((Double)((Map)map.get("likeSender")).get("id")).intValue());
        l.setReceiverId(((Double)((Map)map.get("likeReceiver")).get("id")).intValue());
        return l;
    }
    
    public void doLike(int senderId, int receiverId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/likeUser/"+senderId+"/"+receiverId;
        con.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public void disLike(int senderId, int receiverId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/dislikeUser/"+senderId+"/"+receiverId;
        con.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public void blockUser(int senderId, int receiverId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/blockUser/"+senderId+"/"+receiverId;
        con.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
}
