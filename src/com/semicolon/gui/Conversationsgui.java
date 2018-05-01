/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.semicolon.entity.*;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.MessageService;
import java.util.List;

/**
 *
 * @author badis
 */
public class Conversationsgui {

    Form mySoulmate;
    Container cnt;
    Container cnt2;
    Label nom;
    private Resources theme;
    ImageViewer img;

    public Conversationsgui() {
        Message msg = new Message();
        msg.setId(2);
        mySoulmate = new Form("mySoulmate");

        cnt2 = new Container(BoxLayout.y());
        List<Conversation> msgs = MessageService.getInstance().getAllThreads(msg);
        if (msgs.size()==0){
            System.out.println("pas de conversations");
            Label lb = new Label("Aucune conversation");
            mySoulmate.add(lb);
        }
        for (Conversation c : msgs) {
            Button va = new Button("va");
            System.out.println(c.getPerson1Id() + " , " + c.getPerson2Id());
            va.addActionListener(e -> {
                InstantMessaging rc = new InstantMessaging();
                rc.setReceiverId(c.getPerson1Id(), c.getPerson2Id());

            });

            cnt = new Container(BoxLayout.x());
            nom = new Label(c.getLabel());

            cnt.add(nom);
            cnt.add(va);

            cnt.setLeadComponent(va);
            cnt2.add(cnt);
            mySoulmate.add(cnt2);

        }
        show();

    }

    public void show() {

        mySoulmate.show();

//        Thread thrd = new Thread(target)
    }

}
