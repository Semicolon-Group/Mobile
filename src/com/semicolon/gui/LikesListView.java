package com.semicolon.gui;

import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.semicolon.entity.Like;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.LikeService;
import java.util.List;

public class LikesListView {
    private int memberId;
    private Form form;
    private Form parentForm;
    private List<Like> likes;
    
    private static LikesListView instance;
    
    public static void update(){
        if(instance != null){
            instance.updateView();
        }
    }
    
    private void updateView(){
        likes = LikeService.getInstance().getMemberLikes(memberId);
        buildContainer();
        form.repaint();
    }
    
    public LikesListView(Form parentForm, int memberId){
        instance = this;
        this.parentForm = parentForm;
        this.memberId = memberId;
        this.form = new Form("Likes", new BorderLayout());
        likes = LikeService.getInstance().getMemberLikes(memberId);
        buildContainer();
        form.getToolbar().addCommandToLeftBar("Back", MyApplication.theme.getImage("back-command.png"), (e) -> {
           parentForm.showBack();
        });
    }
    
    private void buildContainer(){
        Container c = new Container(BoxLayout.y());
        c.setScrollableY(true);
        
        for(Like like : likes){
            c.add((new LikeView(form, like)).getContainer());
        }
        
        form.removeAll();
        form.add(BorderLayout.CENTER, c);
    }
    
    public Form getForm(){
        return form;
    }
}
