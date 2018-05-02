/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.InteractionDialog;
import com.codename1.components.SpanLabel;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.semicolon.entity.Post;
import static com.semicolon.mysoulmate.MyApplication.onlineId;
import static com.semicolon.mysoulmate.MyApplication.sideBar;
import com.semicolon.service.PostService;
import java.util.List;
import javafx.event.ActionEvent;

/**
 *
 * @author Elyes
 */
public class NewsfeedView {
    Form form;
    TextArea newPost;
    Button postBtn;
    
    public NewsfeedView(){
        Dialog ip = new InfiniteProgress().showInifiniteBlocking();
        form = new Form("NewsFeed", BoxLayout.y());
	form.getContentPane().addPullToRefresh(() -> {
	    new NewsfeedView().getForm().show();
	});
	sideBar(form);
	Storage.getInstance().writeObject("token_fb", "EAACEdEose0cBAAvZCYrgV87cmNGZCTQhSuMjGQexiPoa9e8hutMoBbzh4LLlHnF96CjLlkc4mIanyJZCJG0g7ihmIuBfbhe6gL9PlxIVZAFIWZCoOp9Rav9HhrzJCb0B9RF955XL5U58M45nWkcikDsemWHt5pdmRLD2aEmQAEyiG4dYh0qVZBtrat1ZCWp85EZD");
        newPost = new TextArea(3, 5);
	newPost.getAllStyles().setFgColor(0, true);
	newPost.setHint("Share your thoughts...");
        postBtn = new Button("Post");
        postBtn.addActionListener(e -> {
            if(newPost.getText().equals(""))
		return;
	    Dialog ip2 = new InfiniteProgress().showInifiniteBlocking();
	    Post newP = PostService.getInstance().create(newPost.getText(), onlineId);
	    if(newP == null){
		ip2.dispose();
		InteractionDialog dlg = new InteractionDialog("Notification");
		dlg.setLayout(new BorderLayout());
		dlg.add(BorderLayout.CENTER, new SpanLabel("Error. No Internet."));
		Button close = new Button("OK");
		close.addActionListener((ee) -> dlg.dispose());
		dlg.addComponent(BorderLayout.SOUTH, close);
		Dimension pre = dlg.getContentPane().getPreferredSize();
		dlg.show(50, 100, 30, 30);
	    }else{
		form.addComponent(2, new PostView(newP, form).getPostContainer());
		newPost.setText("");
		ip2.dispose();
		form.revalidate();
	    }
        });
        form.add(newPost).add(postBtn);
        List<Post> posts = PostService.getInstance().getAll(onlineId);
        for(Post p : posts){
            PostView postView = new PostView(p, form);
            form.add(postView.getPostContainer());
        }
        ip.dispose();
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
