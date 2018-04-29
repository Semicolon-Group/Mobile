package com.semicolon.gui;

import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Tabs;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.pofper.maps.api.GooglePlacesApi;
import com.pofper.maps.entity.Place;
import com.pofper.maps.entity.Point;
import com.semicolon.entity.Member;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.MemberService;
import java.util.List;

public class RecommandationsListView {
    private Form form;
    private Form parentForm;
    private List<Place> cafes;
    private List<Place> rests;
    private List<Place> parks;
    
    private Container tab1 = new Container(BoxLayout.y()); 
    private Container tab2 = new Container(BoxLayout.y());;
    private Container tab3 = new Container(BoxLayout.y());
    
    private final String KEY = "AIzaSyCiVB-qwVIdq-xykpRt-H2Xf27SVIYO0iY";
    
    public RecommandationsListView(int range, int maxPhotoWidth, int maxPhotoHeight, Form parentForm){
        this.parentForm = parentForm;
        form = new Form(new BorderLayout());
        Member member = MemberService.getInstance().getMember(MyApplication.MemberId);
        Point location = new Point(member.getAddress().getLatitude(), member.getAddress().getLongitude());
        
        Tabs tab = new Tabs();
        tab.getAllStyles().setBgColor(0xffffff);
        tab.addSelectionListener((o, n) -> {
            switch(n){
                case 0:
                    if(cafes == null){
                        try {
                            cafes = (new GooglePlacesApi(KEY)).getNearByPlaces(
                                    location,
                                    GooglePlacesApi.PLACE_TYPE.CAFE,
                                    range,
                                    maxPhotoWidth,
                                    maxPhotoHeight);
                            buildContainer(cafes, tab1);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    if(rests == null){
                        try {
                            rests = (new GooglePlacesApi(KEY)).getNearByPlaces(
                                    location,
                                    GooglePlacesApi.PLACE_TYPE.REST,
                                    range,
                                    maxPhotoWidth,
                                    maxPhotoHeight);
                            buildContainer(rests, tab2);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    if(parks == null){
                        try {
                            parks = (new GooglePlacesApi(KEY)).getNearByPlaces(
                                    location,
                                    GooglePlacesApi.PLACE_TYPE.PARK,
                                    range,
                                    maxPhotoWidth,
                                    maxPhotoHeight);
                            buildContainer(parks, tab3);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    break;
            }
        });
        tab.addTab("Cafes", tab1);
        tab.addTab("Restos", tab2);
        tab.addTab("Parks", tab3);
        
        try {
            cafes = (new GooglePlacesApi(KEY)).getNearByPlaces(
                    location,
                    GooglePlacesApi.PLACE_TYPE.CAFE,
                    range,
                    maxPhotoWidth,
                    maxPhotoHeight);
            buildContainer(cafes, tab1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        form.add(BorderLayout.CENTER, tab);
        form.getToolbar().addCommandToLeftBar("Back", MyApplication.theme.getImage("back-command.png"), (ev) -> {
            parentForm.showBack();
        });
    }
    
    private void buildContainer(List<Place> places, Container c){
        c.setScrollableY(true);
        for(Place place : places){
            c.add((new RecommandationView(place, form)).getContainer());
        }
    }
    
    public Form getContainer(){
        return this.form;
    }
}
