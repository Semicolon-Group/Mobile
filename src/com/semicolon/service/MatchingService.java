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
import com.semicolon.entity.MatchCard;
import com.semicolon.entity.Post;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Elyes
 */
public class MatchingService {
    private List<MatchCard> list;
    private static MatchingService instance;
    private Post singlePost;
    
    public static MatchingService getInstance(){
        if(instance == null)
            instance = new MatchingService();
        return instance;
    }
    private MatchingService(){
        list = new ArrayList();
    }
    
    public List<MatchCard> getAll(int onlineId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/get_cards?id=" + onlineId;
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            list = parseMatchCards(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return list;
    }
    
    private List<MatchCard> parseMatchCards(String json){
        list = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            
            Map<String, Object> postMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
            
            List<Map<String, Object>> postList = (List<Map<String, Object>>) postMap.get("root");
            
            for (Map<String, Object> obj : postList) {
                MatchCard c = new MatchCard();
                c.setMemberId((int)Float.parseFloat(obj.get("memberId").toString()));
                c.setMatch((int)Float.parseFloat(obj.get("match").toString()));
                c.setEnemy((int)Float.parseFloat(obj.get("enemy").toString()));
		c.setName(obj.get("name").toString());
		c.setPhotoUrl(obj.get("photoUrl").toString());
                list.add(c);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
}
