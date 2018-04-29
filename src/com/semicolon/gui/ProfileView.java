package com.semicolon.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.semicolon.entity.Address;
import com.semicolon.entity.Enumerations;
import com.semicolon.entity.Member;
import com.semicolon.entity.Photo;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.MemberService;
import com.semicolon.service.PhotoService;
import java.io.IOException;
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
        Dialog i = new InfiniteProgress().showInifiniteBlocking();
        member = MemberService.getInstance().getMember(member.getId());
        buildContainer();
        form.revalidate();
        i.dispose();
    }
    
    public ProfileView(Form parentForm, int memberId){
        Dialog ip = new InfiniteProgress().showInifiniteBlocking();
        instance = this;
        this.parentForm = parentForm;
        form = new Form("Profile", new BorderLayout());
        this.member = MemberService.getInstance().getMember(memberId);
        
        if(member == null){
            getMemberFromLocal();
        }else{
            populateBd();
            form.getToolbar().addCommandToOverflowMenu("Edit", MyApplication.theme.getImage("edit_black.png"), (e) -> {
                (new EditFormView(form, member)).getForm().show();
            });
            form.getToolbar().addCommandToOverflowMenu("Likes", MyApplication.theme.getImage("like.png"), (e) -> {
                (new LikesListView(form, member.getId())).getForm().show();
            });
            form.getToolbar().addCommandToOverflowMenu("Photos", MyApplication.theme.getImage("photos.png"), (e) -> {
                (new PhotosListView(form)).getForm().show();
            });
        }
        form.getToolbar().addCommandToLeftBar("Back", MyApplication.theme.getImage("back-command.png"), (e) -> {
            parentForm.showBack();
        });
        buildContainer();
        ip.dispose();
    }
    
    private void populateBd(){
        try {
            Database database = Database.openOrCreate("mysoulmate");
            database.execute("create table if not exists member(id INTEGER, firstname text, lastname text,"
                    + "username text, gender boolean, birthday date, min_age int, max_age int, height real, body_type int, religion int,"
                    + "religion_importance int, children_number int, smoker boolean, drinker boolean, phone int, email text,"
                    + "civil_status int, city text, country text, lat double, lng double, create_at date, about text);");
            String deleteQuery = "delete from member";
            database.execute(deleteQuery);
            String insertQuery = "insert into member values("+member.getId()+", '"+member.getFirstname()+"', '"+member.getLastname()
                    +"', '"+member.getPseudo()+"', "+(member.isGender()?1:0)+", '"+(new SimpleDateFormat("dd-MM-yyyy")).format(member.getBirthDate())+"', "+member.getMinAge()
                    +", "+member.getMaxAge()+", "+member.getHeight()+", "+member.getBodyType().ordinal()+", "+member.getReligion().ordinal()
                    +", "+member.getReligionImportance().ordinal()+", "+member.getChildrenNumber()+", "+(member.isSmoker()?1:0)
                    +", "+(member.isDrinker()?1:0)+", "+member.getPhone()+", '"+member.getEmail()+"', "+member.getMaritalStatus().ordinal()
                    +", '"+member.getAddress().getCity()+"', '"+member.getAddress().getCountry()+"', "+member.getAddress().getLatitude()
                    +", "+member.getAddress().getLongitude()+", '"+(new SimpleDateFormat("dd-MM-yyyy")).format(member.getCreatedAt())
                    +"', '"+member.getAbout()+"')";
            database.execute(insertQuery);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void getMemberFromLocal(){
        try {
            Database database = Database.openOrCreate("mysoulmate");
            Cursor c = database.executeQuery("select * from member");
            c.next();
            Row r = c.getRow();
            member = new Member();
            member.setBirthDate((new SimpleDateFormat("dd-MM-yyyy").parse(r.getString(5))));
            member.setId(r.getInteger(0));
            member.setPseudo(r.getString(3));
            member.setFirstname(r.getString(1));
            member.setLastname(r.getString(2));
            member.setGender(r.getShort(4)==1?true:false);
            member.setHeight(r.getFloat(8));
            member.setBodyType(Enumerations.BodyType.values()[r.getInteger(9)]);
            member.setChildrenNumber(r.getInteger(12));
            member.setReligion(Enumerations.Religion.values()[r.getInteger(10)]);
            member.setReligionImportance(Enumerations.Importance.values()[r.getInteger(11)]);
            member.setSmoker(r.getShort(13)==1?true:false);
            member.setDrinker(r.getShort(14)==1?true:false);
            member.setMinAge(r.getInteger(6));
            member.setMaxAge(r.getInteger(7));
            member.setPhone(r.getInteger(15));
            member.setCreatedAt((new SimpleDateFormat("dd-MM-yyyy").parse(r.getString(22))));
            member.setAbout(r.getString(23));
            member.setMaritalStatus(Enumerations.MaritalStatus.values()[r.getInteger(17)]);
            member.setEmail(r.getString(16));

            Address address = new Address();
            address.setCity(r.getString(18));
            address.setCountry(r.getString(19));
            address.setLongitude(r.getDouble(21));
            address.setLatitude(r.getDouble(20));

            member.setAddress(address);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
            Image i = MyApplication.theme.getImage("default_banner.png");
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
