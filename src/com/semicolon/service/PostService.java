/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.semicolon.entity.Post;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Elyes
 */
public class PostService {
    private List<Post> list;
    private static PostService instance;
    
    public static PostService getInstance(){
        if(instance == null)
            instance = new PostService();
        return instance;
    }
    private PostService(){
        list = new ArrayList();
    }
    
    public List<Post> getAll(int onlineId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/get_posts?id=" + onlineId;
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            list = parsePosts(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return list;
    }
    
    private List<Post> parsePosts(String json){
        list = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            
            Map<String, Object> postMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
            
            List<Map<String, Object>> postList = (List<Map<String, Object>>) postMap.get("root");
            
            for (Map<String, Object> obj : postList) {
                Post p = new Post();
                p.setId((int)Float.parseFloat(obj.get("id").toString()));
                p.setUserId((int)Float.parseFloat(obj.get("userId").toString()));
                p.setContent(obj.get("content").toString());
                p.setTime(obj.get("time").toString());
                p.setUserName(obj.get("userName").toString());
                p.setNbrReaction((int)Float.parseFloat(obj.get("nbrReaction").toString()));
                p.setNbrComment((int)Float.parseFloat(obj.get("nbrComment").toString()));
                p.setCurrReaction((int)Float.parseFloat(obj.get("currReaction").toString()));
                list.add(p);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
}
