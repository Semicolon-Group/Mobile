/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.service;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.semicolon.entity.Enumerations.ReactionType;
import com.semicolon.entity.Post;
import static com.semicolon.mysoulmate.MyApplication.onlineId;

/**
 *
 * @author Elyes
 */
public class ReactionService {
    private static ReactionService instance;
    
    public static ReactionService getInstance(){
        if (instance == null)
            instance = new ReactionService();
        return instance;
    }
    private ReactionService(){
        
    }
    
    public void react(Post p, ReactionType type){
	ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/react?id=" + p.getId() + "&reaction=" + type.ordinal() + "&userId=" + onlineId + "&type=" + p.getType();
        con.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
}
