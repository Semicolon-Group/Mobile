/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.service;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.semicolon.entity.Enumerations;
import com.semicolon.entity.Signal;

/**
 *
 * @author asus
 */
public class SignalService {
    
    
       public void ajoutSignal(Signal s) {
        ConnectionRequest con = new ConnectionRequest();
        s.setSenderId(2);
        s.setReceiverId(1);
     
        
        String Url =  "http://localhost/mysoulmate/web/app_dev.php/service/signal/new/" +s.getSenderId()+"/"+s.getReceiverId()+"/"+s.getContent()+"/"+s.getReason().ordinal();
        System.out.println(Url);
        con.setUrl(Url);
            

        con.addResponseListener((l) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
            Dialog.show("Confirmation", "Your Signal has been successfully added", "Ok", null);


        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
}
