/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.InteractionDialog;
import com.codename1.components.SpanLabel;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import static com.codename1.ui.Font.FACE_SYSTEM;
import static com.codename1.ui.Font.SIZE_MEDIUM;
import static com.codename1.ui.Font.SIZE_SMALL;
import static com.codename1.ui.Font.STYLE_PLAIN;
import static com.codename1.ui.Font.STYLE_BOLD;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.semicolon.entity.Comment;
import com.semicolon.entity.Enumerations;
import com.semicolon.entity.Enumerations.PostType;
import com.semicolon.entity.Enumerations.ReactionType;
import com.semicolon.entity.Post;
import static com.semicolon.mysoulmate.MyApplication.onlineId;
import com.semicolon.service.CommentService;
import com.semicolon.service.FacebookShare;
import com.semicolon.service.PhotoService;
import com.semicolon.service.PostService;
import com.semicolon.service.ReactionService;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Elyes
 */
public class PostView {
    private Container postContainer;
    private static  Image smileImg;
    private static Image loveImg;
    private static Image laughImg;
    private static Image scowlImg;
    private Form form;
    
    public PostView(Post p, Form form){
	this.form = form;
        postContainer = new Container(BoxLayout.y());
	postContainer.getAllStyles().setMarginBottom(5);
	postContainer.getAllStyles().setPaddingBottom(10);
	postContainer.getAllStyles().setBorder(Border.createUnderlineBorder(1));
        /* Header */
        Container postHeader = new Container(BoxLayout.x());
	postHeader.getAllStyles().setMarginLeft(5);
	if(!p.isOffLine())
	    postHeader.add(PhotoService.getInstance().EmakeImageViewer(p.getUserPhoto()));
        Button goToProfile = new Button();
        goToProfile.addActionListener(e -> {
            if(onlineId != p.getUserId())
                new OtherProfileView(form, p.getUserId()).getForm().show();
            else
                new ProfileView(form, onlineId).getContainer().show();
        });
        postHeader.setLeadComponent(goToProfile);
        Container nameTime = new Container(BoxLayout.y());
        nameTime.add(new Label(p.getUserName()));
	Label timeLabel = new Label(p.getTime());
	timeLabel.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_PLAIN, SIZE_MEDIUM));
        nameTime.add(timeLabel);
        postHeader.add(nameTime);
        postContainer.add(postHeader);
        /* Content */
        if(p.getType() == Enumerations.PostType.STATUS.ordinal()){
	    SpanLabel content = new SpanLabel(p.getContent());
            postContainer.add(content);
        }
        else{
            Label img = PhotoService.getInstance().EmakeImageViewerBig(p.getContent());
            postContainer.add(img);
        }
        /* Reactions */
	if(smileImg == null){
	    makeReactionImages();
	}
	Container postReactions = new Container(BoxLayout.y());
	Label stats = new Label(p.getNbrReaction() + " reactions & " + p.getNbrComment() + " comments");
	stats.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_PLAIN, SIZE_MEDIUM));
	postReactions.add(stats);
	if(onlineId != p.getUserId()){
	    Container reactionBox = new Container(BoxLayout.x());
	    try {
		reactionBox.add(createReactionButton(p, smileImg, ReactionType.SMILE));
		reactionBox.add(createReactionButton(p, loveImg, ReactionType.LOVE));
		reactionBox.add(createReactionButton(p, laughImg, ReactionType.LAUGH));
		reactionBox.add(createReactionButton(p, scowlImg, ReactionType.SCOWL));
	    } catch (IOException ex) {
		System.out.println(ex.getMessage());
	    }
	    postContainer.add(postReactions).add(reactionBox);
	}
	/* Delete & Share Buttons*/
	if(p.getUserId() == onlineId && p.getType() == PostType.STATUS.ordinal()){
	    Container shareDelete = new Container(BoxLayout.x());
	    /* Delete */
	    Button deleteBtn = new Button();
	    deleteBtn.addActionListener(e -> {
		Dialog ip = new InfiniteProgress().showInifiniteBlocking();
		if(PostService.getInstance().delete(p.getId())){
		    form.removeComponent(postContainer);
		    ip.dispose();
		    form.revalidate();
		}else{
		    ip.dispose();
		    InteractionDialog dlg = new InteractionDialog("Notification");
		    dlg.setLayout(new BorderLayout());
		    dlg.add(BorderLayout.CENTER, new SpanLabel("Error. No internet."));
		    Button close = new Button("OK");
		    close.addActionListener((ee) -> dlg.dispose());
		    dlg.addComponent(BorderLayout.SOUTH, close);
		    Dimension pre = dlg.getContentPane().getPreferredSize();
		    dlg.show(50, 100, 30, 30);
		}
	    });
	    Label deleteLabel = new Label("Delete post");
	    deleteLabel.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, SIZE_SMALL));
	    deleteLabel.getAllStyles().setUnderline(true);
	    deleteLabel.getAllStyles().setFgColor(0xFF0000);
	    Container deleteContainer = new Container(BoxLayout.x());
	    deleteContainer.add(deleteLabel);
	    deleteContainer.setLeadComponent(deleteBtn);
	    shareDelete.add(deleteContainer);
	    /* Share */
	    Button shareBtn = new Button();
	    shareBtn.addActionListener(e -> {
		FacebookShare fb = new FacebookShare((String) Storage.getInstance().readObject("token"));
		Dialog ip = new InfiniteProgress().showInifiniteBlocking();
		try {
		    if(fb == null)
			throw new IOException();
		    fb.share(p.getContent());
		    ip.dispose();
		    InteractionDialog dlg = new InteractionDialog("Notification");
		    dlg.setLayout(new BorderLayout());
		    dlg.add(BorderLayout.CENTER, new SpanLabel("Post shared successfully!"));
		    Button close = new Button("OK");
		    close.addActionListener((ee) -> dlg.dispose());
		    dlg.addComponent(BorderLayout.SOUTH, close);
		    Dimension pre = dlg.getContentPane().getPreferredSize();
		    dlg.show(50, 100, 30, 30);
		} catch (IOException ex) {
		    ip.dispose();
		    InteractionDialog dlg = new InteractionDialog("Notification");
		    dlg.setLayout(new BorderLayout());
		    dlg.add(BorderLayout.CENTER, new SpanLabel("Sharing failed.\nNo internet or you haven't logged in with facebook yet."));
		    Button close = new Button("OK");
		    close.addActionListener((ee) -> dlg.dispose());
		    dlg.addComponent(BorderLayout.SOUTH, close);
		    Dimension pre = dlg.getContentPane().getPreferredSize();
		    dlg.show(50, 100, 30, 30);
		}
	    });
	    Label shareLabel = new Label("Share post");
	    shareLabel.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, SIZE_SMALL));
	    shareLabel.getAllStyles().setUnderline(true);
	    shareLabel.getAllStyles().setFgColor(0x00FF00);
	    Container shareContainer = new Container(BoxLayout.x());
	    shareContainer.add(shareLabel);
	    shareContainer.setLeadComponent(shareBtn);
	    shareDelete.add(shareContainer);
	    
	    postContainer.add(shareDelete);
	}
        /* Comments */
	    /* New Comment */
	TextField newComment = new TextField();
	newComment.setHint("Leave a comment...");
	postContainer.add(newComment);
	postContainer.add(makeCommentButton(p, newComment));
	    /* Comment-List */
        List<Comment> comments = CommentService.getInstance().getAll(p);
        for(Comment c : comments){
            CommentView cView = new CommentView(c, postContainer, form);
            postContainer.add(cView.getCommentContainer());
        }
    }

    public Container getPostContainer() {
        return postContainer;
    }

    public void setPostContainer(Container postContainer) {
        this.postContainer = postContainer;
    }
    
    private Button createReactionButton(Post p, Image img, ReactionType type) throws IOException{
	Button btn = new Button(img);
	if(type.ordinal() == p.getCurrReaction()){
	    btn.getAllStyles().setBorder(Border.createBevelRaised());
	}
	else{
	    btn.getAllStyles().setBorder(Border.createEmpty());
	}
	btn.addActionListener(e -> {
	    Dialog ip = new InfiniteProgress().showInifiniteBlocking();
	    if(ReactionService.getInstance().react(p, type)){
		for(int i=0; i < 4; i++){
		    btn.getParent().getComponentAt(i).getAllStyles().setBorder(Border.createEmpty());
		}
		if(type.ordinal() != p.getCurrReaction()){
		    btn.getAllStyles().setBorder(Border.createBevelRaised());
		    p.setCurrReaction(type.ordinal());
		}
		ip.dispose();
		form.repaint();
	    }else{
		ip.dispose();
		InteractionDialog dlg = new InteractionDialog("Notification");
		dlg.setLayout(new BorderLayout());
		dlg.add(BorderLayout.CENTER, new SpanLabel("Error. No Internet."));
		Button close = new Button("OK");
		close.addActionListener((ee) -> dlg.dispose());
		dlg.addComponent(BorderLayout.SOUTH, close);
		Dimension pre = dlg.getContentPane().getPreferredSize();
		dlg.show(50, 100, 30, 30);
	    }
	});
	return btn;
    }
    
    private void makeReactionImages(){
	try {
	    smileImg = Image.createImage("/Smile.png").scaled(24, 24);
	    loveImg = Image.createImage("/Love.png").scaled(24, 24);
	    laughImg = Image.createImage("/Laugh.png").scaled(24, 24);
	    scowlImg = Image.createImage("/Scowl.png").scaled(24, 24);
	} catch (IOException ex) {
	    System.out.println(ex.getMessage());
	}
    }
    
    private Button makeCommentButton(Post p, TextField comment){
	Button btn = new Button("Share comment");
	btn.addActionListener(e -> {
	    if(comment.getText().equals(""))
		return;
	    Dialog ip = new InfiniteProgress().showInifiniteBlocking();
	    Comment c = CommentService.getInstance().create(p, comment.getText(), onlineId);
	    if(c != null){
		comment.setText("");
		postContainer.add(new CommentView(c, postContainer, form).getCommentContainer());
		ip.dispose();
		form.revalidate();
	    }else{
		ip.dispose();
		InteractionDialog dlg = new InteractionDialog("Notification");
		dlg.setLayout(new BorderLayout());
		dlg.add(BorderLayout.CENTER, new SpanLabel("Error. No Internet."));
		Button close = new Button("OK");
		close.addActionListener((ee) -> dlg.dispose());
		dlg.addComponent(BorderLayout.SOUTH, close);
		Dimension pre = dlg.getContentPane().getPreferredSize();
		dlg.show(50, 100, 30, 30);
	    }
	});
	return btn;
    }
    
}
