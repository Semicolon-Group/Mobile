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
import static com.codename1.ui.Font.FACE_SYSTEM;
import static com.codename1.ui.Font.SIZE_MEDIUM;
import static com.codename1.ui.Font.SIZE_SMALL;
import static com.codename1.ui.Font.STYLE_PLAIN;
import com.codename1.ui.Label;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.semicolon.entity.Comment;
import com.semicolon.entity.Enumerations;
import com.semicolon.entity.Post;
import static com.semicolon.mysoulmate.MyApplication.onlineId;
import com.semicolon.service.CommentService;
import com.semicolon.service.PhotoService;
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
    
    public PostView(Post p){
        postContainer = new Container(BoxLayout.y());
	postContainer.getAllStyles().setMarginBottom(5);
	postContainer.getAllStyles().setPaddingBottom(10);
	postContainer.getAllStyles().setBorder(Border.createUnderlineBorder(1));
        /* Header */
        Container postHeader = new Container(BoxLayout.x());
        postHeader.add(PhotoService.getInstance().EmakeImageViewer(p.getUserPhoto()));
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
            ImageViewer img = PhotoService.getInstance().EmakeImageViewer(p.getContent());
	    img.setPreferredSize(new Dimension(250, 250));
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
		reactionBox.add(createReactionButton(p, smileImg, stats));
		reactionBox.add(createReactionButton(p, loveImg, stats));
		reactionBox.add(createReactionButton(p, laughImg, stats));
		reactionBox.add(createReactionButton(p, scowlImg, stats));
	    } catch (IOException ex) {
		System.out.println(ex.getMessage());
	    }
	    postContainer.add(postReactions).add(reactionBox);
	}
        /* Comments */
	    /* New Comment */
	TextField newComment = new TextField("Leave a comment...");
	postContainer.add(newComment);
	postContainer.add(makeCommentButton(p, newComment));
	    /* Comment-List */
        List<Comment> comments = CommentService.getInstance().getAll(p);
        for(Comment c : comments){
            CommentView cView = new CommentView(c);
            postContainer.add(cView.getCommentContainer());
        }
    }

    public Container getPostContainer() {
        return postContainer;
    }

    public void setPostContainer(Container postContainer) {
        this.postContainer = postContainer;
    }
    
    private Button createReactionButton(Post p, Image img, Label stats) throws IOException{
	Button btn = new Button(img);
	//btn.getAllStyles().setBorder(Border.createBevelRaised());
	btn.addActionListener(e -> {
	    
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
	    Comment c = CommentService.getInstance().create(p, comment.getText(), onlineId);
	    comment.setText("");
	    postContainer.add(new CommentView(c).getCommentContainer());
	});
	return btn;
    }
    
}
