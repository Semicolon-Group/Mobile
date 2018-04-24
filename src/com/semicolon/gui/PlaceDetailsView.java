package com.semicolon.gui;

import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.javascript.JSObject;
import com.codename1.javascript.JavascriptContext;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.pofper.maps.api.GoogleDistanceMatrixApi;
import com.pofper.maps.entity.Distance;
import com.pofper.maps.entity.Place;
import com.pofper.maps.entity.Point;
import com.pofper.maps.entity.Review;
import com.semicolon.mysoulmate.MyApplication;
import java.util.Random;

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
        BrowserComponent bc = new BrowserComponent();
        bc.setPreferredH(300);
        
        bc.addWebEventListener("onLoad", new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JavascriptContext context = new JavascriptContext(bc);
                JSObject window = context.getWindow();
                window.call("initialize", new Object[]{
                    36.872530,
                    10.316018,
                    place.getAddress().getLocation().getLatitude(),
                    place.getAddress().getLocation().getLongitude()
                });
            }
        });
        
        Distance distance = (new GoogleDistanceMatrixApi("AIzaSyDiFoJPMY8zxTiT8XTEjunsnI5gqKfZAJo").getDistance(
                new Point(36.872530, 10.316018), place.getAddress().getLocation()));
        Label distanceLabel = new Label(distance.getFormattedDuration()+ " ("+distance.getFormattedDistance()+")");
        distanceLabel.getUnselectedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        
        Container firstRow = new Container(new FlowLayout());
        firstRow.add(FlowLayout.encloseIn(new Label(place.getPlaceName()), distanceLabel));
        
        Container phoneContainer = new Container(BoxLayout.x());
        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.getUnselectedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        
        Label phoneNumber = new Label(place.getPhoneNumber());
        phoneNumber.getUnselectedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        
        phoneContainer.add(phoneLabel);
        phoneContainer.add(phoneNumber);
        
        Button b = new Button("Link");
        b.getAllStyles().setBorder(Border.createEmpty());
        b.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);
        b.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        b.getUnselectedStyle().setFgColor(0x1e90ff);
        b.addActionListener((ev) -> Display.getInstance().execute(place.getWebsite()));
        
        Container websiteContainer = new Container(BoxLayout.x());
        Label websiteLabel = new Label("Website:");
        websiteLabel.getUnselectedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        websiteContainer.add(websiteLabel);
        websiteContainer.add(b);
        
        Container reviewsContainer = new Container(BoxLayout.y());
        reviewsContainer.getAllStyles().setMarginTop(20);
        reviewsContainer.getAllStyles().setMarginBottom(20);
        
        
        reviewsContainer.add(firstRow);
        if(place.getPhoneNumber()!= null && !place.getPhoneNumber().isEmpty())
            reviewsContainer.add(phoneContainer);
        if(place.getWebsite() != null && !place.getWebsite().isEmpty())
            reviewsContainer.add(websiteContainer);
        reviewsContainer.add(new Label("Reviews:"));
        for(Review r: place.getReviews()){
            reviewsContainer.add(buildReviewContainer(r));
        }
        
        
        container.add(bc);
        container.add(reviewsContainer);
        form.add(container);
        bc.setURL("jar:///assets/MapsView.html");
    }
    
    private Container buildReviewContainer(Review review){
        Container c = new Container(BoxLayout.x());
        c.getAllStyles().setPaddingBottom(20);
        
        Container leftContainer = new Container(BoxLayout.y());
        leftContainer.setWidth(20);
        Container rightContainer = new Container(new BorderLayout());
        
        Label imgv;
        if(review.getAuthorProfilePhotoUrl() != null && !review.getAuthorProfilePhotoUrl().isEmpty()){
            EncodedImage enc = EncodedImage.createFromImage(MyApplication.theme.getImage("loading.png"), false);
            URLImage urlImage = URLImage.createToStorage(enc, (new Random()).nextInt()+"", review.getAuthorProfilePhotoUrl());
            imgv = new Label(urlImage);
        }else{
            imgv = new Label(MyApplication.theme.getImage("default_place.jpg"));
        }
        leftContainer.add(imgv);
        
        Container firstRow = new Container(new BorderLayout());
        firstRow.getAllStyles().setMarginBottom(0);
        firstRow.getAllStyles().setPaddingBottom(0);
        
        SpanLabel name = new SpanLabel(review.getAuthorName());
        name.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Container nameContainer = new Container();
        nameContainer.add(name);
        
        Label rating = new Label(review.getRating()+"");
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
        
        SpanLabel content = new SpanLabel(review.getText()+" - "+review.getTime());
        content.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        
        rightContainer.getAllStyles().setMarginTop(0);
        rightContainer.getAllStyles().setPaddingTop(0);
        
        rightContainer.add(BorderLayout.NORTH, firstRow);
        rightContainer.add(BorderLayout.SOUTH, content);
        
        c.add(leftContainer);
        c.add(rightContainer);
        return c;
    }
    
    public Form getForm(){
        return form;
    }
}
