package com.semicolon.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.semicolon.entity.Block;
import com.semicolon.entity.Like;
import com.semicolon.entity.Member;
import com.semicolon.entity.Photo;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.BlockService;
import com.semicolon.service.LikeService;
import com.semicolon.service.MemberService;
import com.semicolon.service.PhotoService;
import java.util.List;
import java.util.Random;

public class OtherProfileView {
    private Form form;
    private Form parentForm;
    private Member member;
    
    public OtherProfileView(Form parentForm, int memberId){
        Dialog ip = new InfiniteProgress().showInifiniteBlocking();
        this.parentForm = parentForm;
        this.member = MemberService.getInstance().getMember(memberId);
        form = new Form("Profile", new BorderLayout());
        buildContainer();
        
        Like like = LikeService.getInstance().getLike(MyApplication.MemberId, memberId);
        if(like == null){
            form.getToolbar().addCommandToOverflowMenu("Like", MyApplication.theme.getImage("like.png"), (e) -> {
                Dialog i = new InfiniteProgress().showInifiniteBlocking();
                LikeService.getInstance().doLike(MyApplication.MemberId, memberId);
                LikesListView.update();
                (new OtherProfileView(parentForm, memberId)).getForm().show();
                i.dispose();
            });
        }else{
            form.getToolbar().addCommandToOverflowMenu("Chat", MyApplication.theme.getImage("chat.png"), (e) -> {
                System.out.println("Chat");
            });
            form.getToolbar().addCommandToOverflowMenu("Dislike", MyApplication.theme.getImage("dislike.png"), (e) -> {
                Dialog i = new InfiniteProgress().showInifiniteBlocking();
                LikeService.getInstance().disLike(MyApplication.MemberId, memberId);
                LikesListView.update();
                (new OtherProfileView(parentForm, memberId)).getForm().show();
                i.dispose();
            });
        }
        form.getToolbar().addCommandToOverflowMenu("Block", MyApplication.theme.getImage("block.png"), (e) -> {
            Dialog i = new InfiniteProgress().showInifiniteBlocking();
            BlockService.getInstance().blockUser(6, 12);
            LikesListView.update();
            parentForm.showBack();
            i.dispose();
        });
        form.getToolbar().addCommandToOverflowMenu("Report", MyApplication.theme.getImage("report.png"), (e) -> {
            new SignalView(form, memberId).getF().show();
        });
        form.getToolbar().addCommandToLeftBar("Back", MyApplication.theme.getImage("back-command.png"), (e) -> {
            parentForm.showBack();
        });
        ip.dispose();
    }
    
    private void buildContainer(){
        Container c = new Container(BoxLayout.y());
        c.getAllStyles().setMarginBottom(20);
        c.getAllStyles().setPaddingTop(0);
        c.getAllStyles().setMarginTop(0);
        form.getAllStyles().setMarginTop(0);
        form.getAllStyles().setPaddingTop(0);
        
        //Cover Picture
        Label coverImg;
        Container coverContainer = new Container();
        Button bCover = new Button();
        coverContainer.setLeadComponent(bCover);
        Photo coverPhoto = PhotoService.getInstance().getCoverPhoto(member.getId());
        if(coverPhoto != null){
            EncodedImage enc = EncodedImage.createFromImage(MyApplication.theme.getImage("loading_cover.png"), false);
            URLImage urlImage = URLImage.createToStorage(enc, (new Random()).nextInt()+"", coverPhoto.getPhotoUri());
            coverImg = new Label(urlImage);
            bCover.addActionListener((e) -> {
                (new PhotoDetailsView(form, coverPhoto)).getForm().show();
            });
        }else{
            Image i = MyApplication.theme.getImage("default_banner.png");
            i = i.scaledHeight(200);
            coverImg = new Label(i);
        }
        coverContainer.add(coverImg);
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
        
        c.add(coverContainer);
        c.add(buildTopProfileInfo());
        c.add(buildProfileInfo());
        c.add(aboutContainer);
        
        c.setScrollableY(true);
        form.add(BorderLayout.CENTER, c);
    }
    
    public Form getForm(){
        Dialog ip = new InfiniteProgress().showInifiniteBlocking();
        if(BlockService.getInstance().getBlock(MyApplication.MemberId, member.getId()) != null || 
                BlockService.getInstance().getBlock(member.getId(), MyApplication.MemberId) != null){
            Dialog.show("User Blocked", "You can't access user profile!", "Ok", null);
            return parentForm;
        }
        ip.dispose();
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
        Container profileContainer = new Container();
        Button bProfile = new Button();
        profileContainer.setLeadComponent(bProfile);
        Photo ProfilePhoto = PhotoService.getInstance().getProfilePhoto(member.getId());
        if(ProfilePhoto != null){
            EncodedImage enc = EncodedImage.createFromImage(MyApplication.theme.getImage("loading.png"), false);
            URLImage urlImage = URLImage.createToStorage(enc, (new Random()).nextInt()+"", ProfilePhoto.getPhotoUri());
            profileImg = new Label(urlImage);
            bProfile.addActionListener((e) -> (new PhotoDetailsView(form, ProfilePhoto)).getForm().show());
        }else{
            Image i = MyApplication.theme.getImage("default.png");
            i = i.scaledHeight(70);
            profileImg = new Label(i);
        }
        profileImg.getAllStyles().setMarginLeft(0);
        profileContainer.add(profileImg);
        
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
        profileAddressContainer.add(profileContainer);
        profileAddressContainer.add(profileSideContainer);
        
        return profileAddressContainer;
    }
}
