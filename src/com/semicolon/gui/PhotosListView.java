package com.semicolon.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.semicolon.entity.Photo;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.PhotoService;
import java.util.List;
import java.util.Random;

public class PhotosListView {
    private Form parentForm;
    private Form form;
    private Container mainContainer;
    
    private List<Photo> photos;
    
    private static PhotosListView instance;
    
    public static void update(){
        if(instance != null)
            instance.updateView();
    }
    
    public PhotosListView(Form parentForm){
        Dialog i = new InfiniteProgress().showInifiniteBlocking();
        this.parentForm = parentForm;
        this.instance = this;
        form = new Form("Photos", new BorderLayout());
        mainContainer = new Container(new FlowLayout());
        mainContainer.setScrollableY(true);
        form.add(BorderLayout.CENTER, mainContainer);
        photos = PhotoService.getInstance().getRegularPhotos(MyApplication.MemberId);
        buildContainer();
        form.getToolbar().addCommandToLeftBar("Back", MyApplication.theme.getImage("back-command.png"), (e) -> {
            parentForm.showBack();
        });
        form.getToolbar().addCommandToOverflowMenu("Add", MyApplication.theme.getImage("add_photo.png"), (ev) -> {
            ActionListener callback = e->{
                if (e != null && e.getSource() != null) {
                    Dialog ip = new InfiniteProgress().showInifiniteBlocking();
                    String filePath = ((String)e.getSource()).substring(7);
                    PhotoService.getInstance().addPhoto(filePath);
                    updateView();
                    ip.dispose();
                }
             };
             if (FileChooser.isAvailable()) {
                 FileChooser.showOpenDialog(".pdf,application/pdf,.gif,image/gif,.png,image/png,.jpg,image/jpg,.tif,image/tif,.jpeg", callback);
             } else {
                 Display.getInstance().openGallery(callback, Display.GALLERY_IMAGE);
             }
        });
        i.dispose();
    }
    
    private void updateView(){
        photos = PhotoService.getInstance().getRegularPhotos(MyApplication.MemberId);
        buildContainer();
    }
    
    private void buildContainer(){
        mainContainer.removeAll();
        for(Photo photo : photos){
            EncodedImage enc = EncodedImage.createFromImage(MyApplication.theme.getImage("loading_img.png"), false);
            URLImage urlImage = URLImage.createToStorage(enc, (new Random()).nextInt()+"", photo.getPhotoUri());
            Label imageLabel = new Label(urlImage);
            Container imgContainer = new Container();
            Button bImg = new Button();
            imgContainer.setLeadComponent(bImg);
            bImg.addActionListener((e) -> {
                (new PhotoDetailsView(form, photo)).getForm().show();
            });
            imgContainer.add(imageLabel);
            
            mainContainer.add(FlowLayout.encloseIn(imgContainer));
        }
        mainContainer.revalidate();
    }
    
    public Form getForm(){
        return this.form;
    }
    
    public Form getParentForm(){
        return parentForm;
    }
    
}
