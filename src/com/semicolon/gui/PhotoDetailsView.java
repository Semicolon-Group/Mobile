package com.semicolon.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.semicolon.entity.Enumerations;
import com.semicolon.entity.Photo;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.MemberService;
import com.semicolon.service.PhotoService;
import java.util.Random;

public class PhotoDetailsView {
    private Form parentForm;
    private Form form;
    private Photo photo;
    
    public PhotoDetailsView(Form parentForm, Photo photo){
        Dialog ip = new InfiniteProgress().showInifiniteBlocking();
        this.parentForm = parentForm;
        this.photo = photo;
        this.form = new Form("Photo details", new BorderLayout());
        
        buildContainer();
        
        form.getToolbar().addCommandToLeftBar("Back", MyApplication.theme.getImage("back-command.png"), (e) -> {
            this.parentForm.showBack();
        });
        
        if(photo.getType() == Enumerations.PhotoType.REGULAR){
            form.getToolbar().addCommandToOverflowMenu("Set as profile", MyApplication.theme.getImage("profile_photo.png"), (e) -> {
                Dialog i = new InfiniteProgress().showInifiniteBlocking();
                PhotoService.getInstance().setAsProfilePhoto(photo.getId());
                i.dispose();
                (new ProfileView(MyApplication.firstForm, MyApplication.MemberId)).getContainer().showBack();
            });
            form.getToolbar().addCommandToOverflowMenu("Set as cover", MyApplication.theme.getImage("cover_photo.png"), (e) -> {
                Dialog i = new InfiniteProgress().showInifiniteBlocking();
                PhotoService.getInstance().setAsCoverPhoto(photo.getId());
                i.dispose();
                (new ProfileView(MyApplication.firstForm, MyApplication.MemberId)).getContainer().showBack();
            });
            form.getToolbar().addCommandToOverflowMenu("Delete", MyApplication.theme.getImage("delete_photo.png"), (e) -> {
                Dialog i = new InfiniteProgress().showInifiniteBlocking();
                PhotoService.getInstance().deletePhoto(photo.getId());
                PhotosListView.update();
                i.dispose();
                parentForm.show();
            });
        }
        ip.dispose();
    }
    
    private void buildContainer(){
        Container c = new Container(BoxLayout.y());
        
        EncodedImage enc = EncodedImage.createFromImage(MyApplication.theme.getImage("loading_cover.png"), false);
        URLImage urlImage = URLImage.createToStorage(enc, (new Random()).nextInt()+"", photo.getPhotoUri());
        Label imageLabel = new Label(urlImage);
        
        Label typeLabel = new Label("Photo type: "+photo.getType().name());
        typeLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        
        Label dateLabel = new Label("Uploaded since: "+(new SimpleDateFormat("dd MMMM, yyyy").format(photo.getUpdateDate())));
        dateLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        
        c.add(imageLabel);
        c.add(typeLabel);
        c.add(dateLabel);
        
        form.add(BorderLayout.CENTER, c);
    }
    
    public Form getForm(){
        return this.form;
    }
}
