/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.gui;

import com.codename1.components.ImageViewer;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import java.util.List;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import static com.semicolon.gui.Login1.MEMBER_ID;
import java.util.ArrayList;
import static java.util.Collections.list;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.codename1.io.JSONParser;
import java.util.Map;
import com.semicolon.entity.Message;
import com.semicolon.service.MessageService;
import java.util.Timer;
import com.codename1.ui.util.UITimer;
import com.semicolon.mysoulmate.MyApplication;

/**
 *
 * @author badis
 */
public class Recovery {

    private int thrd;
    private int receiver;
    Form mySoulMate;
    Container conty;
    Container contx;
    Container cntt;
    private ConnectionRequest connectionRequest;
    private List<Message> list;
    TextField txf;

    public void setReceiverId(int id, int thrd) {
        this.receiver = id;
        this.thrd = thrd;
        initiatefield();
        mySoulMate.show();

    }

    public Recovery() {
        mySoulMate = new Form("MySoulMate");
        conty = new Container(BoxLayout.y());
        contx = new Container(BoxLayout.x());
        ImageViewer img = new ImageViewer();

        cntt = new Container(BoxLayout.y());
        cntt.setHeight(150);
        cntt.getStyle().setMarginTop(0);
        cntt.getStyle().setMarginBottom(30);
        txf = new TextField();
        txf.setHint("type msg here");
        txf.setWidth(80);
        contx.add(txf);
        conty.add(cntt);
        conty.add(contx);
        mySoulMate.add(conty);

        show();
        txf.addActionListener(send -> {

            Label lb = new Label(txf.getText());
            Message msg = new Message();
            msg.setContent(txf.getText());
            msg.setSenderId(2);
            msg.setReceiverId(receiver);
            txf.setText("");
            lb.getStyle().setAlignment(Component.RIGHT);
            cntt.add(lb);
           
            cntt.scrollComponentToVisible(txf);
            MessageService.getInstance().SendMSg(msg);
           

        });

    }

    public void show() {
        mySoulMate.show();
    }

    public void initiatefield() {

        Message msg = new Message();
        msg.setId(thrd);
        System.out.println(thrd);
        List<Message> msgs = MessageService.getInstance().getAll(msg);
        for (Message c : msgs) {
            if (c.getSenderId() == 2) {
                Label lb = new Label(c.getContent());
                lb.getStyle().setAlignment(Component.RIGHT);
                cntt.scrollComponentToVisible(lb);
                cntt.add(lb);
                cntt.scrollComponentToVisible(lb);
                cntt.scrollComponentToVisible(txf);

            } else {
                Label lb = new Label(c.getContent());
                lb.getStyle().setAlignment(Component.LEFT);
                cntt.scrollComponentToVisible(lb);
                cntt.add(lb);
                cntt.scrollComponentToVisible(lb);
                cntt.scrollComponentToVisible(txf);

            }

        }

    }

    protected com.codename1.ui.util.UITimer refresherTimer = null;

    protected void stopRefreshing() {
        if (refresherTimer != null) {
            refresherTimer.cancel();
            refresherTimer = null;
        }
    }

    public static class MyThread implements Runnable {

        protected com.codename1.ui.util.UITimer refresherTimer = null;

        @Override
        public void run() {
            MyThread2 thr = new MyThread2();
            Recovery rc = new Recovery();
            refresherTimer.timer(10000, false, thr);
            rc.cntt.removeAll();
            rc.initiatefield();

        }

    }

    public static class MyThread2 implements Runnable {

        protected com.codename1.ui.util.UITimer refresherTimer = null;

        @Override
        public void run() {
            MyThread thr = new MyThread();
            Recovery rc = new Recovery();
            refresherTimer.timer(10000, false, thr);
        }
    }
}
