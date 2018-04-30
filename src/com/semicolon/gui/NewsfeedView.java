/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.semicolon.entity.Enumerations;
import com.semicolon.entity.Post;
import static com.semicolon.mysoulmate.MyApplication.onlineId;
import com.semicolon.service.PhotoService;
import com.semicolon.service.PostService;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Elyes
 */
public class NewsfeedView {
    Form form;
    TextArea newPost;
    Button postBtn;
    
    public NewsfeedView(){
        form = new Form("NewsFeed", BoxLayout.y());
        newPost = new TextArea("Share your thoughts...", 3, 5);
	newPost.getAllStyles().setFgColor(0, true);
        postBtn = new Button("Post");
        postBtn.addActionListener(e -> {
            if(newPost.getText().equals(""))
		return;
	    form.addComponent(2, new PostView(PostService.getInstance().create(newPost.getText(), onlineId), form).getPostContainer());
	    newPost.setText("");
	    form.repaint();
        });
        form.add(newPost).add(postBtn);
        List<Post> posts = PostService.getInstance().getAll(onlineId);
        for(Post p : posts){
            PostView postView = new PostView(p, form);
            form.add(postView.getPostContainer());
        }
        form.show();
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public TextArea getNewPost() {
        return newPost;
    }

    public void setNewPost(TextArea newPost) {
        this.newPost = newPost;
    }

    public Button getPostBtn() {
        return postBtn;
    }

    public void setPostBtn(Button postBtn) {
        this.postBtn = postBtn;
    }
    
    
}
