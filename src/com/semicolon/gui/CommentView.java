/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import static com.codename1.ui.Font.FACE_SYSTEM;
import static com.codename1.ui.Font.SIZE_MEDIUM;
import static com.codename1.ui.Font.STYLE_BOLD;
import static com.codename1.ui.Font.STYLE_PLAIN;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;
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
	ImageViewer img = PhotoService.getInstance().EmakeImageViewer(c.getPhotoUrl());
	img.setPreferredSize(new Dimension(35, 35));
        commentContainer.add(img);
	SpanLabel content = new SpanLabel(c.getContent());
	content.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, SIZE_MEDIUM));
        commentContainer.add(content);
    }

    public Container getCommentContainer() {
        return commentContainer;
    }

    public void setCommentContainer(Container commentContainer) {
        this.commentContainer = commentContainer;
    }
    
    
}
