package com.semicolon.gui;

import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.semicolon.entity.Member;
import com.semicolon.entity.Photo;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.MemberService;
import com.semicolon.service.PhotoService;
import java.util.Random;

public class ProfileView {
    private Form form;
    private Form parentForm;
    private Member member;
    
    private static ProfileView instance;
    
    public static void update(){
        if(instance != null)
            instance.updateView();
    }
    
    private void updateView(){
        member = MemberService.getInstance().getMember(member.getId());
        buildContainer();
        form.repaint();
    }
    
    public ProfileView(Form parentForm, Member member){
        instance = this;
        this.parentForm = parentForm;
        this.member = member;
        form = new Form("Profile", new BorderLayout());
        buildContainer();
        
        form.getToolbar().addCommandToOverflowMenu("Edit", MyApplication.theme.getImage("edit_black.png"), (e) -> {
            (new EditFormView(form, member)).getForm().show();
        });
        form.getToolbar().addCommandToOverflowMenu("Likes", MyApplication.theme.getImage("like.png"), (e) -> {
            (new LikesListView(form, member.getId())).getForm().show();
        });
        form.getToolbar().addCommandToOverflowMenu("Photos", MyApplication.theme.getImage("photos.png"), (e) -> {
            (new PhotosListView(form)).getForm().show();
        });
        form.getToolbar().addCommandToLeftBar("Back", MyApplication.theme.getImage("back-command.png"), (e) -> {
            parentForm.showBack();
        });
    }
    
    private void buildContainer(){
        Container c = new Container(BoxLayout.y());
        c.getAllStyles().setMarginBottom(20);
        
        //Cover Picture
        Label coverImg;
        Photo coverPhoto = PhotoService.getInstance().getCoverPhoto(member.getId());
        if(coverPhoto != null){
            EncodedImage enc = EncodedImage.createFromImage(MyApplication.theme.getImage("loading_cover.png"), false);
            URLImage urlImage = URLImage.createToStorage(enc, (new Random()).nextInt()+"", coverPhoto.getPhotoUri());
            coverImg = new Label(urlImage);
            //TO_DO
            coverImg.addPointerPressedListener((e) -> (new PhotoDetailsView(form, coverPhoto)).getForm().show());
        }else{
            Image i = MyApplication.theme.getImage("default.png");
            i = i.scaledHeight(200);
            coverImg = new Label(i);
        }
        coverImg.getAllStyles().setMarginLeft(0);
        
        //About
        Container aboutContainer = new Container(BoxLayout.y());
        aboutContainer.getAllStyles().setMarginLeft(40);
        
        Label aboutLabel = new Label("About");
        aboutLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        
        SpanLabel aboutSpan = new SpanLabel(member.getAbout());
        aboutSpan.getAllStyles().setMarginLeft(20);
        aboutSpan.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        
        aboutContainer.add(aboutLabel);
        aboutContainer.add(aboutSpan);
        
        c.add(coverImg);
        c.add(buildTopProfileInfo());
        c.add(buildProfileInfo());
        c.add(aboutContainer);
        
        c.setScrollableY(true);
        form.add(BorderLayout.CENTER, c);
    }
    
    public Form getContainer(){
        return this.form;
    }
    
    private Container buildProfileInfo(){
        Container c = new Container(BoxLayout.y());
        c.getAllStyles().setMarginLeft(40);
        c.getAllStyles().setPaddingTop(20);
        
        Label infoLabel = new Label("Info");
        infoLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        
        Container mainContainer = new Container(BoxLayout.y());
        mainContainer.getAllStyles().setMarginLeft(30);
        mainContainer.add(buildPairLabel("Gender", member.isGender()?"Male":"Female"));
        mainContainer.add(buildPairLabel("Birthdate", (new SimpleDateFormat("dd MMMM, yyy").format(member.getBirthDate()))));
        mainContainer.add(buildPairLabel("Height", member.getHeight()+""));
        mainContainer.add(buildPairLabel("Body Type", member.getBodyType().name()));
        mainContainer.add(buildPairLabel("Smoker", member.isSmoker()?"Yes":"No"));
        mainContainer.add(buildPairLabel("Drinker", member.isDrinker()?"Yes":"No"));
        mainContainer.add(buildPairLabel("Religion", member.getReligion().name()));
        mainContainer.add(buildPairLabel("Marital Status", member.getMaritalStatus().name()));
        mainContainer.add(buildPairLabel("Number of children", member.getChildrenNumber()+""));
        
        c.add(infoLabel);
        c.add(mainContainer);
        return c;
    }
    
    private Container buildPairLabel(String label, String value){
        Container c = new Container(BoxLayout.x());
        
        Label labelLabel = new Label(label+":");
        labelLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
        
        Label labelValue = new Label(value);
        labelValue.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        
        c.add(labelLabel);
        c.add(labelValue);
        return c;
    }
    
    private Container buildTopProfileInfo(){
        //Profile Picture
        Label profileImg;
        Photo ProfilePhoto = PhotoService.getInstance().getProfilePhoto(member.getId());
        if(ProfilePhoto != null){
            EncodedImage enc = EncodedImage.createFromImage(MyApplication.theme.getImage("loading.png"), false);
            URLImage urlImage = URLImage.createToStorage(enc, (new Random()).nextInt()+"", ProfilePhoto.getPhotoUri());
            profileImg = new Label(urlImage);
            //TO_DO
            profileImg.addPointerPressedListener((e) -> (new PhotoDetailsView(form, ProfilePhoto)).getForm().show());
        }else{
            Image i = MyApplication.theme.getImage("default.png");
            i = i.scaledHeight(70);
            profileImg = new Label(i);
        }
        profileImg.getAllStyles().setMarginLeft(0);
        
        Container profileSideContainer = new Container(BoxLayout.y());
        
        //Name
        Container nameAgeContainer = new Container(BoxLayout.x());
        nameAgeContainer.getAllStyles().setMarginLeft(5);
        
        Label nameLabel = new Label(member.getFirstname()+" "+member.getLastname());
        nameLabel.getAllStyles().setPaddingTop(0);
        nameLabel.getAllStyles().setPaddingRight(0);
        nameLabel.getAllStyles().setPaddingBottom(0);
        nameLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        nameAgeContainer.add(nameLabel);
        //Age
        Label ageLabel = new Label("("+member.getAge()+")");
        ageLabel.getAllStyles().setPaddingTop(0);
        ageLabel.getAllStyles().setPaddingBottom(0);
        ageLabel.getAllStyles().setMarginRight(0);
        ageLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        nameAgeContainer.add(ageLabel);
        
        profileSideContainer.add(nameAgeContainer);
        
        //Address
        Label addressLabel = new Label(member.getAddress().getCity()+", "+member.getAddress().getCountry());
        addressLabel.getAllStyles().setMarginLeft(20);
        addressLabel.getAllStyles().setPaddingTop(0);
        addressLabel.getAllStyles().setPaddingBottom(0);
        addressLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
        profileSideContainer.add(addressLabel);
        
        //Since
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
        Label sinceLabel = new Label("Memeber since: "+sdf.format(member.getCreatedAt()));
        sinceLabel.getAllStyles().setMarginLeft(20);
        sinceLabel.getAllStyles().setPaddingTop(0);
        sinceLabel.getAllStyles().setPaddingBottom(0);
        sinceLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        profileSideContainer.add(sinceLabel);
        
        Container profileAddressContainer = new Container(BoxLayout.x());
        profileAddressContainer.add(profileImg);
        profileAddressContainer.add(profileSideContainer);
        
        return profileAddressContainer;
    }
}
