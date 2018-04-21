package com.semicolon.gui;

import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.pofper.maps.entity.Place;
import com.semicolon.mysoulmate.MyApplication;

public class PlaceDetailsView {
    private final Place place;
    private final Form form;
    private final Form parentForm;
    
    public PlaceDetailsView(Place place, Form parentForm){
        this.place = place;
        this.form = new Form();
        this.parentForm = parentForm;
        this.form.getToolbar().addCommandToLeftBar(
                "Back", 
                MyApplication.theme.getImage("back-command.png"), 
                (ev) -> parentForm.showBack());
        buildForm();
    }
    
    public void buildForm(){
        Container container = new Container(BoxLayout.y());
        container.add(new Label(place.getPlaceName()));
        form.add(container);
    }
    
    public Form getForm(){
        return form;
    }
}
