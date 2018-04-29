/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.semicolon.entity.Comment;
import com.semicolon.service.PhotoService;

/**
 *
 * @author Elyes
 */
public class CommentView {
    private Container commentContainer;
    
    public CommentView(Comment c){
        commentContainer = new Container(BoxLayout.x());
        commentContainer.add(new Label("     "));
        commentContainer.add(PhotoService.getInstance().EmakeImageViewer(c.getPhotoUrl()));
        commentContainer.add(new SpanLabel(c.getContent()));
    }

    public Container getCommentContainer() {
        return commentContainer;
    }

    public void setCommentContainer(Container commentContainer) {
        this.commentContainer = commentContainer;
    }
    
    
}
