/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
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
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.notifications.LocalNotification;
import com.codename1.notifications.LocalNotificationCallback;

import com.codename1.ui.Command;
import com.codename1.ui.Display;
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
import com.semicolon.entity.Conversation;
import com.semicolon.entity.Member;
import com.semicolon.entity.Photo;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.MemberService;
import com.semicolon.service.PhotoService;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author badis
 */
public class InstantMessaging {

    protected com.codename1.ui.util.UITimer refresherTimer = null;
    public static int thrd;
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

    private Image mask;
    private EncodedImage roundPlaceholder;
    private static EncodedImage userPlaceholder;
    private static EncodedImage userPlaceholder2;
    Photo ProfilePhoto;
    Photo ProfilePhotoR;
    static Image roundedHimOrHerImageHIM;
    static Image roundedHimOrHerImageME;
    EncodedImage roundPlaceholder2;
    Media media;

    private final HashMap<String, EncodedImage> roundedImagesOfFriends = new HashMap<>();

    public void setReceiverId(int id, int thrd) {
        this.receiver = id;
        this.thrd = thrd;
        this.MEMBER_ID = MyApplication.onlineId;
        Mawjoud = new ArrayList<>();

        Go();
        theme = UIManager.initFirstTheme("/theme");
        ProfilePhoto = PhotoService.getInstance().getProfilePhoto(MEMBER_ID);
        ProfilePhotoR = PhotoService.getInstance().getProfilePhoto(receiver);
        if (ProfilePhoto != null) {

            theme = UIManager.initFirstTheme("/theme");

            Style iconFontStyle = UIManager.getInstance().getComponentStyle("LargeIconFont");
            FontImage fnt = FontImage.create(" \ue80f ", iconFontStyle);

            userPlaceholder = fnt.toEncodedImage();
            mask = theme.getImage("r.png");
            roundPlaceholder = EncodedImage.createFromImage(userPlaceholder.scaled(mask.getWidth(), mask.getHeight()).applyMask(mask.createMask()), false);

            roundedHimOrHerImageME = getRoundedFriendImage(((new Random()).nextInt() + "") + "qsqs", ProfilePhoto.getPhotoUri());

        } else {
            Image i = MyApplication.theme.getImage("default.png");
            i = i.scaledHeight(70);
            roundedHimOrHerImageME = i;
        }
        if (ProfilePhotoR != null) {

            theme = UIManager.initFirstTheme("/theme");

            Style iconFontStyle = UIManager.getInstance().getComponentStyle("LargeIconFont");
            FontImage fnt2 = FontImage.create(" \ue80f ", iconFontStyle);

            userPlaceholder2 = fnt2.toEncodedImage();
            mask = theme.getImage("r.png");
            roundPlaceholder2 = EncodedImage.createFromImage(userPlaceholder2.scaled(mask.getWidth(), mask.getHeight()).applyMask(mask.createMask()), false);

            roundedHimOrHerImageHIM = getRoundedFriendImage2(((new Random()).nextInt() + "") + "qsqs", ProfilePhotoR.getPhotoUri());

        } else {
            Image i = MyApplication.theme.getImage("default.png");
            i = i.scaledHeight(70);
            roundedHimOrHerImageME = i;
        }

        initiatefield(InstantMessaging.thrd, receiver, MEMBER_ID);
        chatForm.repaint();
        MyThread2 thr = new MyThread2();
        refresherTimer.timer(3000, true, thr);

    }

    public void setOld(List<Message> mm) {
        this.old = mm;

    }

    public List<Message> getOld() {
     
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

    public InstantMessaging() {
    }
    Command cmd;

    public void Go() {
        Member m = MemberService.getInstance().getMember(receiver);

        chatForm = new Form("" + m.getFirstname(), new BorderLayout());

        chatForm.setLayout(new BorderLayout());
        Toolbar tb = new Toolbar();
        chatArea = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        chatArea.setScrollableY(true);
        chatArea.setName("ChatArea");
        write = new TextField(30);
        ok = new Button();
        write.setHint("Write your message ");
        chatForm.addComponent(BorderLayout.CENTER, chatArea);
        chatForm.getToolbar().addCommandToLeftBar("Back", MyApplication.theme.getImage("back-command.png"), (e) -> {
            Conversationsgui cs = new Conversationsgui(new NewsfeedView().getForm());
            cs.show();

        });
        try {
            Image img = Image.createImage("/home.png");
            chatForm.getToolbar().addCommandToRightBar("", img, (e) -> {
                new NewsfeedView().getForm().show();

            });

        } catch (IOException ex) {
        }

        chatForm.addComponent(BorderLayout.SOUTH, write);
        write.addActionListener((e) -> {

            String text = write.getText();
            if (text.length() < 1) {
                return;
            } else {
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

    private EncodedImage getRoundedFriendImage(String uid, String imageUrl) {
        EncodedImage roundedHimOrHerImage = roundedImagesOfFriends.get(uid);
        if (roundedHimOrHerImage == null) {
            roundedHimOrHerImage = URLImage.createToStorage(roundPlaceholder, "rounded" + uid, imageUrl, URLImage.createMaskAdapter(mask));
            roundedImagesOfFriends.put(uid, roundedHimOrHerImage);
        }
        return roundedHimOrHerImage;
    }

    private EncodedImage getRoundedFriendImage2(String uid, String imageUrl) {
        EncodedImage roundedHimOrHerImage2 = roundedImagesOfFriends.get(uid);
        if (roundedHimOrHerImage2 == null) {
            roundedHimOrHerImage2 = URLImage.createToStorage(roundPlaceholder2, "rounded" + uid, imageUrl, URLImage.createMaskAdapter(mask));
            roundedImagesOfFriends.put(uid, roundedHimOrHerImage2);
        }
        return roundedHimOrHerImage2;
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

                Button gotoprofile = new Button();
                t.setLeadComponent(gotoprofile);

                gotoprofile.addActionListener(e -> {
                    Form form = new Form();
                    new OtherProfileView(form, receiver).getForm().show();

                });
                Component answer = respondNoLayoutYsar(chatArea, text, roundedHimOrHerImageHIM);

                answer.setX(chatArea.getWidth());
                answer.setWidth(chatArea.getWidth());
                answer.setHeight(40);

                chatArea.scrollComponentToVisible(answer);
            }
            chatForm.scrollComponentToVisible(t);

        }
        chatForm.show();

    }

    public void Update(List<Message> msges, int thrd, int senderId, int receiverId) {

        for (Message c : msges) {
            String text = c.getContent();
            c.setId(0);
            c.setReceiverId(0);
            SpanLabel t = new SpanLabel(text);
            if (c.getSenderId() == MEMBER_ID && Mawjoud.contains(c)) {

                return;
            } else if (c.getSenderId() == MEMBER_ID && !Mawjoud.contains(c)) {

              
                final Component tx = respond(chatArea, text, roundedHimOrHerImageME);
            } else {

                Button gotoprofile = new Button();
                t.setLeadComponent(gotoprofile);

                gotoprofile.addActionListener(e -> {
                    Form form = new Form();
                    new OtherProfileView(form, receiver).getForm().show();

                });
                if (Display.getInstance().getCurrent() != null) {

                    LocalNotification n = new LocalNotification();
                    n.setId("1");
                    n.setAlertBody(text);
                    n.setAlertTitle("New message");
                    Display.getInstance().scheduleLocalNotification(
                            n,
                            System.currentTimeMillis() + 800, // fire date/time
                            LocalNotification.REPEAT_NONE// Whether to repeat and what frequency
                    );

                    localNotificationReceived("1");
                }
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

    public void localNotificationReceived(String notificationId) {
      

        ToastBar ts = ToastBar.getInstance();
        ts.setPosition(Component.TOP);
        ts.showMessage("New message", FontImage.MATERIAL_MESSAGE, (int) System.currentTimeMillis() + 800, e -> {
            ts.setVisible(false);
        });

        try {
            media = MediaManager.createMedia((Display.getInstance().getResourceAsStream(getClass(), "/m.mp3")), "audio/mpeg");
            media.play();
        } catch (IOException ex) {

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

            InstantMessaging rc = new InstantMessaging();
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

            InstantMessaging rc = new InstantMessaging();
            List<Message> msgs = rc.refresh(rc.thrd);
            rc.nnn = msgs;
            MyThread thr = new MyThread();
            refresherTimer.timer(3000, false, thr);

        }
    }

}
