package com.semicolon.gui;

import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.pofper.maps.api.GooglePlacesApi;
import com.pofper.maps.entity.Place;
import com.pofper.maps.entity.Point;
import java.util.List;

public class RecommandationsListView {
    private Form form;
    private List<Place> places;
    
    public RecommandationsListView(Point location, int range, int maxPhotoWidth, int maxPhotoHeight){
        try {
            places = (new GooglePlacesApi("AIzaSyDiFoJPMY8zxTiT8XTEjunsnI5gqKfZAJo")).getNearByPlaces(
                    location,
                    GooglePlacesApi.PLACE_TYPE.REST,
                    range,
                    maxPhotoWidth,
                    maxPhotoHeight);
            form = new Form(BoxLayout.y());
            buildContainer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void buildContainer(){
        for(Place place : places){
            form.add((new RecommandationView(place, form)).getContainer());
        }
    }
    
    public Form getContainer(){
        return this.form;
    }
}
