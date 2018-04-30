/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.service;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.semicolon.entity.Feedback;

/**
 *
 * @author asus
 */
public class FeedbackService {
    
    public void ajoutFeedback(Feedback fa) {
        ConnectionRequest con = new ConnectionRequest();
          fa.setSenderId(2);
        String Url = "http://localhost/mysoulmate/web/app_dev.php/service/feed/new/"+fa.getSenderId()+"/"+fa.getContent();
        con.setUrl(Url);
            
//        System.out.println("tt");

        con.addResponseListener((l) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
           Dialog.show("Confirmation", "Your Feedback has been successfully added", "Ok", null);


        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
}
