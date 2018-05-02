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
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import static com.codename1.ui.Font.FACE_SYSTEM;
import static com.codename1.ui.Font.SIZE_MEDIUM;
import static com.codename1.ui.Font.SIZE_SMALL;
import static com.codename1.ui.Font.STYLE_BOLD;
import static com.codename1.ui.Font.STYLE_PLAIN;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.semicolon.entity.Comment;
import static com.semicolon.mysoulmate.MyApplication.onlineId;
import com.semicolon.service.CommentService;
import com.semicolon.service.PhotoService;

/**
 *
 * @author Elyes
 */
public class CommentView {
    private Container big;
    
    public CommentView(Comment c, Container postContainer, Form form){
	big = new Container(BoxLayout.y());
        Container commentContainer = new Container(BoxLayout.x());
        commentContainer.add(new Label("     "));
	ImageViewer img = PhotoService.getInstance().EmakeImageViewer(c.getPhotoUrl());
	img.setPreferredSize(new Dimension(35, 35));
        commentContainer.add(img);
	SpanLabel content = new SpanLabel(c.getContent());
	content.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, SIZE_MEDIUM));
        commentContainer.add(content);
	big.add(commentContainer);
	if(c.getSenderId() == onlineId){
	    Button deleteBtn = new Button();
	    deleteBtn.addActionListener(e -> {
		Dialog ip = new InfiniteProgress().showInifiniteBlocking();
		if(CommentService.getInstance().delete(c.getId())){
		    postContainer.removeComponent(big);
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
	    Label deleteLabel = new Label("Delete");
	    deleteLabel.getAllStyles().setFont(Font.createSystemFont(FACE_SYSTEM, STYLE_BOLD, SIZE_SMALL));
	    deleteLabel.getAllStyles().setUnderline(true);
	    deleteLabel.getAllStyles().setFgColor(0xFF0000);
	    Container deleteContainer = new Container(BoxLayout.x());
	    deleteContainer.add(new Label("   "));
	    deleteContainer.add(deleteLabel);
	    deleteContainer.setLeadComponent(deleteBtn);
	    big.add(deleteContainer);
	}
    }

    public Container getCommentContainer() {
        return big;
    }

    public void setCommentContainer(Container commentContainer) {
        this.big = commentContainer;
    }
    
    
}
