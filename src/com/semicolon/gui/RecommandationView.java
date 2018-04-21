package com.semicolon.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.pofper.maps.entity.Place;
import com.semicolon.mysoulmate.MyApplication;
import java.util.Random;

public class RecommandationView {
    private Container container;
    private final Place place;
    private final Form parentForm;
    
    public RecommandationView(Place place, Form parentForm){
        this.place = place;
        this.container = new Container(BoxLayout.x());
        this.parentForm = parentForm;
        buildContainer();
    }
    
    private void buildContainer(){
        Container leftContainer = new Container(BoxLayout.y());
        leftContainer.setWidth(20);
        Container rightContainer = new Container(new BorderLayout());
        
        Label imgv;
        if(place.getPhotoUrl().indexOf("http")>=0){
            EncodedImage enc = EncodedImage.createFromImage(MyApplication.theme.getImage("loading.png"), false);
            URLImage urlImage = URLImage.createToStorage(enc, (new Random()).nextInt()+"", place.getPhotoUrl());
            imgv = new Label(urlImage);
        }else{
            imgv = new Label(MyApplication.theme.getImage("default_place.jpg"));
        }
        leftContainer.add(imgv);
        
        Container firstRow = new Container(new BorderLayout());
        SpanLabel name = new SpanLabel(place.getPlaceName());
        name.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Container nameContainer = new Container();
        nameContainer.add(name);
        
        Label rating = new Label(place.getRating()+"");
        rating.getUnselectedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        rating.getAllStyles().setMarginRight(0);
        rating.getAllStyles().setPaddingRight(0);
        
        Label ratingStar = new Label(MyApplication.theme.getImage("rating-star.png"));
        ratingStar.getAllStyles().setMarginLeft(0);
        ratingStar.getAllStyles().setPaddingLeft(0);
        Container ratingContainer = FlowLayout.encloseRight(rating, ratingStar);
        
        firstRow.add(BorderLayout.CENTER, nameContainer);
        firstRow.add(BorderLayout.EAST, ratingContainer);
        firstRow.setWidth(50);
        
        SpanLabel address = new SpanLabel(place.getAddress().getCity()+", "+place.getAddress().getCountry());
        address.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        
        
        rightContainer.add(BorderLayout.NORTH, firstRow);
        rightContainer.add(BorderLayout.SOUTH, address);
        
        Button lead = new Button();
        lead.setVisible(false);
        lead.addActionListener((e) -> (new PlaceDetailsView(place, parentForm)).getForm().show());
        container.add(leftContainer);
        container.add(rightContainer);
        container.add(lead);
        
        container.setLeadComponent(lead);
    }
    
    public Container getContainer(){
        return this.container;
    }
}
