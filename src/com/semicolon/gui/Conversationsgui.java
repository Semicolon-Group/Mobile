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
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.semicolon.entity.*;
import static com.semicolon.gui.InstantMessaging.MEMBER_ID;
import static com.semicolon.gui.InstantMessaging.receiver;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.MessageService;
import com.semicolon.service.PhotoService;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
    private Image mask;
    private EncodedImage roundPlaceholder;
    private static EncodedImage userPlaceholder;
    private static EncodedImage userPlaceholder2;
    Photo ProfilePhoto;
    Photo ProfilePhotoR;
    Image roundedHimOrHerImageHIM;
    Image roundedHimOrHerImageME;
    EncodedImage roundPlaceholder2;
    private final HashMap<String, EncodedImage> roundedImagesOfFriends = new HashMap<>();
    Image imgs;

    public Conversationsgui() {
        Message msg = new Message();
        msg.setId(MyApplication.onlineId);
        mySoulmate = new Form("mySoulmate");

        cnt2 = new Container(BoxLayout.y());
        List<Conversation> msgs = MessageService.getInstance().getAllThreads(msg);
        if (msgs.size() == 0) {
            System.out.println("pas de conversations");
            Label lb = new Label("Aucune conversation");
            mySoulmate.add(lb);
        }
        for (Conversation c : msgs) {
            Button va = new Button("va");
            Label profileImg;
            va.addActionListener(e -> {
                InstantMessaging rc = new InstantMessaging();
                rc.setReceiverId(c.getPerson1Id(), c.getPerson2Id());

            });

            cnt = new Container(BoxLayout.x());
            nom = new Label(c.getLabel());
            Photo ProfilePhoto = PhotoService.getInstance().getProfilePhoto(c.getPerson1Id());
            if (ProfilePhoto != null) {
                EncodedImage enc = EncodedImage.createFromImage(MyApplication.theme.getImage("loading.png"), false);
                URLImage urlImage = URLImage.createToStorage(enc, (new Random()).nextInt() + "", ProfilePhoto.getPhotoUri());
                profileImg = new Label(urlImage);
                profileImg.getAllStyles().setMarginLeft(0);
                theme = UIManager.initFirstTheme("/theme");

                Style iconFontStyle = UIManager.getInstance().getComponentStyle("LargeIconFont");
                FontImage fnt = FontImage.create(" \ue80f ", iconFontStyle);
                FontImage fnt2 = FontImage.create(" \ue80f ", iconFontStyle);

                userPlaceholder = fnt.toEncodedImage();
                mask = theme.getImage("r.png");
                roundPlaceholder = EncodedImage.createFromImage(userPlaceholder.scaled(mask.getWidth(), mask.getHeight()).applyMask(mask.createMask()), false);

                imgs = getRoundedFriendImage(((new Random()).nextInt() + "") + "qsqs",ProfilePhoto.getPhotoUri());
                cnt.add(imgs);

            } else {
                Image i = MyApplication.theme.getImage("default.png");
                i = i.scaledHeight(70);
                profileImg = new Label(i);
                imgs = i;
                cnt.add(imgs);
            }

            nom.getAllStyles().setAlignment(Component.CENTER);
            
            cnt.add(nom);

            cnt.setLeadComponent(va);
            cnt2.add(cnt);
           

        } mySoulmate.add(cnt2);
        show();

    }

    public void show() {

        mySoulmate.show();

//        Thread thrd = new Thread(target)
    }

    private EncodedImage getRoundedFriendImage(String uid, String imageUrl) {
        EncodedImage roundedHimOrHerImage = roundedImagesOfFriends.get(uid);
        if (roundedHimOrHerImage == null) {
            roundedHimOrHerImage = URLImage.createToStorage(roundPlaceholder, "rounded" + uid, imageUrl, URLImage.createMaskAdapter(mask));
            roundedImagesOfFriends.put(uid, roundedHimOrHerImage);
        }
        return roundedHimOrHerImage;
    }

}
