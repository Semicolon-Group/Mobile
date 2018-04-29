/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.semicolon.entity.Comment;
import com.semicolon.entity.Enumerations;
import com.semicolon.entity.Post;
import com.semicolon.service.CommentService;
import com.semicolon.service.PhotoService;
import java.util.List;

/**
 *
 * @author Elyes
 */
public class PostView {
    private Container postContainer;
    
    public PostView(Post p){
        postContainer = new Container(BoxLayout.y());
        /* Header */
        Container postHeader = new Container(BoxLayout.x());
        postHeader.add(PhotoService.getInstance().EmakeImageViewer(p.getUserPhoto()));
        Container nameTime = new Container(BoxLayout.y());
        nameTime.add(new Label(p.getUserName()));
        nameTime.add(new Label(p.getTime()));
        postHeader.add(nameTime);
        postContainer.add(postHeader);
        /* Content */
        if(p.getType() == Enumerations.PostType.STATUS.ordinal()){
            postContainer.add(new SpanLabel(p.getContent()));
        }
        else{
            ImageViewer img = PhotoService.getInstance().EmakeImageViewer(p.getContent());
            postContainer.add(img);
        }
        /* Reactions */
        
        /* Comments */
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
    
    
}
