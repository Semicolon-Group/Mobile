package com.semicolon.gui;

import com.codename1.ext.filechooser.FileChooser;
import com.codename1.ui.Container;
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
    private static PhotosListView instance;
    
    public static void updateView(){
        if(instance != null){
            instance.updateContainer();
        }
    }
    
    private List<Photo> photos;
    
    public PhotosListView(Form parentForm){
        this.instance = this;
        this.parentForm = parentForm;
        form = new Form("Photos", new BorderLayout());
        photos = PhotoService.getInstance().getRegularPhotos(MyApplication.MemberId);
        buildContainer();
        form.getToolbar().addCommandToLeftBar("Back", MyApplication.theme.getImage("back-command.png"), (e) -> {
            parentForm.showBack();
        });
        form.getToolbar().addCommandToOverflowMenu("Add", MyApplication.theme.getImage("add_photo.png"), (ev) -> {
            ActionListener callback = e->{
                if (e != null && e.getSource() != null) {
                    String filePath = ((String)e.getSource()).substring(7);
                    PhotoService.getInstance().addPhoto(filePath);
                    updateContainer();
                }
             };
             if (FileChooser.isAvailable()) {
                 FileChooser.showOpenDialog(".pdf,application/pdf,.gif,image/gif,.png,image/png,.jpg,image/jpg,.tif,image/tif,.jpeg", callback);
             } else {
                 Display.getInstance().openGallery(callback, Display.GALLERY_IMAGE);
             }
        });
    }
    
    private void updateContainer(){
        photos = PhotoService.getInstance().getRegularPhotos(MyApplication.MemberId);
        buildContainer();
        form.repaint();
    }
    
    private void buildContainer(){
        Container c = new Container(new FlowLayout());
        c.setScrollableY(true);
        
        for(Photo photo : photos){
            EncodedImage enc = EncodedImage.createFromImage(MyApplication.theme.getImage("loading_img.png"), false);
            URLImage urlImage = URLImage.createToStorage(enc, (new Random()).nextInt()+"", photo.getPhotoUri());
            Label imageLabel = new Label(urlImage);
            imageLabel.addPointerPressedListener((e) -> {
                (new PhotoDetailsView(form, photo)).getForm().show();
            });
            
            c.add(FlowLayout.encloseIn(imageLabel));
        }
        
        form.removeAll();
        form.add(BorderLayout.CENTER, c);
    }
    
    public Form getForm(){
        return this.form;
    }
    
    public Form getParentForm(){
        return parentForm;
    }
    
}
