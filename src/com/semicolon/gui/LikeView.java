package com.semicolon.gui;

import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.pofper.maps.entity.Place;
import com.semicolon.entity.Like;
import com.semicolon.entity.Member;
import com.semicolon.entity.Photo;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.MemberService;
import com.semicolon.service.PhotoService;
import java.util.Random;

public class LikeView {
    private Container container;
    private Form parentForm;
    private final Like like;
    private final Member member;
    
    public LikeView(Form parentForm, Like like){
        this.like = like;
        this.parentForm = parentForm;
        this.member = MemberService.getInstance().getMember(like.getReceiverId());
        this.container = new Container(BoxLayout.x());
        buildContainer();
    }
    
    private void buildContainer(){
        Container leftContainer = new Container(BoxLayout.y());
        leftContainer.setWidth(20);
        Container rightContainer = new Container(new BorderLayout());
        
        Label imgv;
        Photo profilePhoto = PhotoService.getInstance().getProfilePhoto(member.getId());
        if(profilePhoto != null && !profilePhoto.getPhotoUri().isEmpty()){
            EncodedImage enc = EncodedImage.createFromImage(MyApplication.theme.getImage("loading.png"), false);
            URLImage urlImage = URLImage.createToStorage(enc, (new Random()).nextInt()+"", profilePhoto.getPhotoUri());
            imgv = new Label(urlImage);
        }else{
            Image i = MyApplication.theme.getImage("default-like.jpg");
            imgv = new Label(i);
        }
        leftContainer.add(imgv);
        
        Container firstRow = new Container(new BorderLayout());
        SpanLabel name = new SpanLabel(member.getFirstname()+" " + member.getLastname());
        name.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Container nameContainer = new Container();
        nameContainer.add(name);
        
        firstRow.add(BorderLayout.CENTER, nameContainer);
        firstRow.getAllStyles().setMarginBottom(0);
        firstRow.getAllStyles().setPaddingBottom(0);
        firstRow.setWidth(50);
        
        SpanLabel address = new SpanLabel("Liked since: "+(new SimpleDateFormat("dd MMMM, yyyy")).format(like.getDate()));
        address.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        address.getAllStyles().setMarginTop(0);
        address.getAllStyles().setPaddingTop(0);
        
        
        rightContainer.add(BorderLayout.NORTH, firstRow);
        rightContainer.add(BorderLayout.SOUTH, address);
        
        Button lead = new Button();
        lead.setVisible(false);
        lead.addActionListener((e) -> (new OtherProfileView(parentForm, like.getReceiverId())).getForm().show());
        container.add(leftContainer);
        container.add(rightContainer);
        container.add(lead);
        
        container.setLeadComponent(lead);
    }
    
    public Container getContainer(){
        return this.container;
    }
}
