/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
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
import com.codename1.ui.Command;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.SwipeBackSupport;
import java.util.Map;
import com.semicolon.entity.Message;
import com.semicolon.service.MessageService;
import java.util.Timer;
import com.codename1.ui.util.UITimer;
import com.semicolon.entity.Photo;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.PhotoService;
import static java.lang.Thread.sleep;
import java.util.HashMap;

/**
 *
 * @author badis
 */
public class Recovery {

    protected com.codename1.ui.util.UITimer refresherTimer = null;
    public static int thrd = 1;
    public static int receiver;
    static Form mySoulMate;
    public static int MEMBER_ID;
    Container conty;
    Container contx;
    Container cntt;
    private ConnectionRequest connectionRequest;
    private List<Message> list;
    TextField txf;
    static Container chatArea;
    static Form chatForm;
    static TextField write;
    Button ok;
    public static List<Message> old;
    public static List<Message> nnn;
    private Image roundedMeImage;
    private Resources theme;
    static List<Message> Mawjoud;

    public void setReceiverId(int id, int thrd) {
        this.receiver = id;
        this.thrd = thrd;
        this.MEMBER_ID = MyApplication.onlineId;
        Go();
        initiatefield(thrd, receiver, MEMBER_ID);
        MyThread2 thr = new MyThread2();
        refresherTimer.timer(10000, true, thr);

    }

    public void setOld(List<Message> mm) {
        this.old = mm;

    }

    public List<Message> getOld() {
        System.out.println("getter" + old);
        return old;

    }

    private Component say(Container chatArea, String text) {
        Component t = sayNoLayout(chatArea, text);
        t.setY(chatArea.getHeight());
        t.setWidth(chatArea.getWidth());
        t.setHeight(40);
        chatArea.animateLayoutAndWait(300);
        chatArea.scrollComponentToVisible(t);
        return t;
    }

    private Component respond(Container chatArea, String text, Image roundedHimOrHerImage) {
        Component answer = respondNoLayout(chatArea, text, roundedHimOrHerImage);
        answer.setX(chatArea.getWidth());
        answer.setWidth(chatArea.getWidth());
        answer.setHeight(40);
        chatArea.animateLayoutAndWait(300);
        chatArea.scrollComponentToVisible(answer);
        return answer;
    }

    private Component respondYsar(Container chatArea, String text, Image roundedHimOrHerImage) {
        Component answer = respondNoLayoutYsar(chatArea, text, roundedHimOrHerImage);
        answer.setX(chatArea.getWidth());
        answer.setWidth(chatArea.getWidth());
        answer.setHeight(40);
        chatArea.animateLayoutAndWait(300);
        chatArea.scrollComponentToVisible(answer);
        return answer;
    }

    private Component respondNoLayout(Container chatArea, String text, Image roundedHimOrHerImage) {
        SpanLabel answer = new SpanLabel(text);
        answer.setIcon(roundedHimOrHerImage);
        answer.setIconPosition(BorderLayout.EAST);
        answer.setTextUIID("BubbleThem");
        answer.setTextBlockAlign(Component.RIGHT);
        chatArea.addComponent(answer);
        return answer;
    }

    private Component respondNoLayoutYsar(Container chatArea, String text, Image roundedHimOrHerImage) {
        SpanLabel answer = new SpanLabel(text);
        answer.setIcon(roundedHimOrHerImage);
        answer.setIconPosition(BorderLayout.WEST);
        answer.setTextUIID("BubbleThem");
        answer.setTextBlockAlign(Component.LEFT);
        chatArea.addComponent(answer);
        return answer;
    }

    private Component sayYsar(Container chatArea, String text) {
        Component t = sayNoLayoutYsar(chatArea, text);
        t.setY(chatArea.getHeight());
        t.setWidth(chatArea.getWidth());
        t.setHeight(40);
        chatArea.animateLayoutAndWait(300);
        chatArea.scrollComponentToVisible(t);
        return t;
    }

    private Component sayNoLayout(Container chatArea, String text) {
        SpanLabel t = new SpanLabel(text);

        t.setTextBlockAlign(Component.RIGHT);
        chatArea.addComponent(t);
        return t;
    }

    private Component sayNoLayoutYsar(Container chatArea, String text) {
        SpanLabel t = new SpanLabel(text);

        t.setTextBlockAlign(Component.LEFT);
        chatArea.addComponent(t);
        return t;
    }

    public Recovery() {
      
    }

    private Image mask;
    private EncodedImage roundPlaceholder;
    private static EncodedImage userPlaceholder;
    Photo ProfilePhoto;
    Photo ProfilePhotoR;
    static Image roundedHimOrHerImageHIM;
    static Image roundedHimOrHerImageME;
    static EncodedImage roundPlaceholder2;

    public void Go() {
        Mawjoud = new ArrayList<>();

        theme = UIManager.initFirstTheme("/theme");
        ProfilePhoto = PhotoService.getInstance().getProfilePhoto(MEMBER_ID);
        ProfilePhotoR = PhotoService.getInstance().getProfilePhoto(receiver);

        Style iconFontStyle = UIManager.getInstance().getComponentStyle("LargeIconFont");
        FontImage fnt = FontImage.create(" \ue80f ", iconFontStyle);
        userPlaceholder = fnt.toEncodedImage();
        mask = theme.getImage("r.png");
        roundPlaceholder = EncodedImage.createFromImage(userPlaceholder.scaled(mask.getWidth(), mask.getHeight()).applyMask(mask.createMask()), false);
        roundPlaceholder2 = EncodedImage.createFromImage(userPlaceholder.scaled(mask.getWidth(), mask.getHeight()).applyMask(mask.createMask()), false);

        roundedHimOrHerImageME = getRoundedFriendImage("pllfsqdqsdqsd3332", ProfilePhoto.getPhotoUri());
        roundedHimOrHerImageHIM = getRoundedFriendImage("89qddsfsdsddfs562", ProfilePhotoR.getPhotoUri());

        chatForm = new Form();
        chatForm.setLayout(new BorderLayout());
        Toolbar tb = new Toolbar();
        chatArea = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        chatArea.setScrollableY(true);
        chatArea.setName("ChatArea");
        write = new TextField(30);
        ok = new Button();
        write.setHint("Write your message ");
        chatForm.addComponent(BorderLayout.CENTER, chatArea);

        chatForm.addComponent(BorderLayout.SOUTH, write);
        chatArea.addPullToRefresh(new Runnable() {
            @Override
            public void run() {
                 initiatefield(thrd, receiver, MEMBER_ID);
        MyThread2 thr = new MyThread2();
        refresherTimer.timer(10000, true, thr);
            }
        });
        write.addActionListener((e) -> {
            if (write.getText() == "" || write.getText() == null) {
                return;
            } else {

                String text = write.getText();
                final Component t = respond(chatArea, text, roundedHimOrHerImageME);

                Message msg = new Message();

                msg.setContent(write.getText());
                msg.setSenderId(MEMBER_ID);
                msg.setReceiverId(receiver);
                Mawjoud.add(msg);
                write.setText("");
                MessageService.getInstance().SendMSg(msg);

            }
        });
        chatForm.show();

    }

    public void show() {

    }
    private final HashMap<String, EncodedImage> roundedImagesOfFriends = new HashMap<>();

    private EncodedImage getRoundedFriendImage(String uid, String imageUrl) {
        EncodedImage roundedHimOrHerImage = roundedImagesOfFriends.get(uid);
        if (roundedHimOrHerImage == null) {
            roundedHimOrHerImage = URLImage.createToStorage(roundPlaceholder, "rounded" + uid, imageUrl, URLImage.createMaskAdapter(mask));
            roundedImagesOfFriends.put(uid, roundedHimOrHerImage);
        }
        return roundedHimOrHerImage;
    }

    private EncodedImage getRoundedFriendImage2(String uid, String imageUrl) {
        EncodedImage roundedHimOrHerImage = roundedImagesOfFriends.get(uid);
        if (roundedHimOrHerImage == null) {
            roundedHimOrHerImage = URLImage.createToStorage(roundPlaceholder2, "rounded" + uid, imageUrl, URLImage.createMaskAdapter(mask));
            roundedImagesOfFriends.put(uid, roundedHimOrHerImage);
        }
        return roundedHimOrHerImage;
    }

    public void initiatefield(int thrd, int senderId, int receiverId) {

        Message msg = new Message();
        msg.setId(thrd);
        List<Message> msgs = MessageService.getInstance().getAll(msg);
        setOld(msgs);

        for (Message c : msgs) {
            String text = c.getContent();

            SpanLabel t = new SpanLabel(text);
            if (c.getSenderId() == MEMBER_ID) {
                Component answer = respondNoLayout(chatArea, text, roundedHimOrHerImageME);
                answer.setX(chatArea.getWidth());
                answer.setWidth(chatArea.getWidth());
                answer.setHeight(40);

                chatArea.scrollComponentToVisible(answer);
            } else {
                Component answer = respondNoLayoutYsar(chatArea, text, roundedHimOrHerImageHIM);
                answer.setX(chatArea.getWidth());
                answer.setWidth(chatArea.getWidth());
                answer.setHeight(40);

                chatArea.scrollComponentToVisible(answer);
            }
            chatForm.scrollComponentToVisible(t);

        }

    }

    public void Update(List<Message> msges, int thrd, int senderId, int receiverId) {

        for (Message c : msges) {
            String text = c.getContent();

            SpanLabel t = new SpanLabel(text);
            if (c.getSenderId() == MEMBER_ID || Mawjoud.equals(msges)) {
                return;
            } else {
                final Component tx = respondYsar(chatArea, text, roundedHimOrHerImageHIM);

            }

            chatForm.scrollComponentToVisible(t);
           

        }
        chatForm.scrollComponentToVisible(chatArea);

        try {
            sleep(500);
        } catch (InterruptedException ex) {
        }

    }

    public List<Message> refresh(int thrd) {

        Message msg = new Message();
        msg.setId(thrd);
        List<Message> msgs = MessageService.getInstance().getAll(msg);
        return msgs;
    }

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

            Recovery rc = new Recovery();
            MyThread2 thr = new MyThread2();

            if (rc.nnn.size() > rc.old.size()) {

                rc.nnn.removeAll(rc.old);

                rc.Update(rc.nnn, rc.thrd, rc.MEMBER_ID, rc.receiver);
                rc.old = rc.refresh(rc.thrd);

            } else {

                return;
            }

        }

    }

    public static class MyThread2 implements Runnable {

        protected com.codename1.ui.util.UITimer refresherTimer = null;

        @Override
        public void run() {

            Recovery rc = new Recovery();
            List<Message> msgs = rc.refresh(rc.thrd);
            rc.nnn = msgs;
            MyThread thr = new MyThread();
            refresherTimer.timer(3000, false, thr);

        }
    }

}
