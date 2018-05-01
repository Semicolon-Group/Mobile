/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.service;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.semicolon.entity.UserR;

/**
 *
 * @author asus
 */
public class InscriptionService {
        public void Inscription(UserR u) {
        ConnectionRequest con = new ConnectionRequest();
       
        
        String Url = "http://localhost/mysoulmate/web/app_dev.php/service/inscription/new?birth=" + u.getBirthDate().substring(0, 10) + "&firstname=" +u.getFirstname() +"&lastname=" +u.getLastname()+"&email="+u.getEmail()+"&password="+u.getPassword()+"&username="+u.getPseudo()+"&about="+ u.getAbout()+"&phone=" +u.getPhone()+"&gender="+u.isGender() +"&smoker="+u.isSmoker() + "&drinker="+u.isDrinker() +"&bodytype="+ u.getBodyType().ordinal() +"&childrennbr="+u.getChildrenNumber()+"&minage="+u.getMinAge()+"&maxage="+u.getMaxAge()+"&relegion="+ u.getReligion().ordinal()+"&relegionImportance="+ u.getReligionImportance().ordinal()+"&civilStatus="+ u.getMaritalStatus().ordinal() +"&height="+u.getHeight();
        con.setUrl(Url);
            
//        System.out.println("tt");

        con.addResponseListener((l) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    
}
