/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.semicolon.entity.MatchCard;
import static com.semicolon.mysoulmate.MyApplication.MemberId;
import static com.semicolon.mysoulmate.MyApplication.firstForm;
import static com.semicolon.mysoulmate.MyApplication.onlineId;
import static com.semicolon.mysoulmate.MyApplication.sideBar;
import static com.semicolon.mysoulmate.MyApplication.theme;
import com.semicolon.service.BlockService;
import com.semicolon.service.LikeService;
import com.semicolon.service.MatchingService;
import com.semicolon.service.PhotoService;
import java.util.List;

/**
 *
 * @author Elyes
 */
public class MatchingView {
    private List<MatchCard> list;
    private Form form;
    
    public MatchingView(){
        Dialog ip = new InfiniteProgress().showInifiniteBlocking();
	form = new Form(BoxLayout.y());
	sideBar(form);
	list = MatchingService.getInstance().getAll(onlineId);
	fillNext();
        ip.dispose();
    }
    
    private void fillNext(){
	try{
	    form.removeAll();
	    MatchCard card = list.get(0);
	    form.add(PhotoService.getInstance().EmakeImageViewerBig(card.getPhotoUrl()));
	    Button blockBtn = new Button("Block");
	    Button profileBtn = new Button("Profile");
	    Button likeBtn = new Button("Like");
	    blockBtn.addActionListener(e -> {
		BlockService.getInstance().blockUser(onlineId, card.getMemberId());
		list.remove(0);
		fillNext();
	    });
	    likeBtn.addActionListener(e -> {
		LikeService.getInstance().doLike(onlineId, card.getMemberId());
		list.remove(0);
		fillNext();
	    });
	    profileBtn.addActionListener(e -> {
		new OtherProfileView(form, card.getMemberId()).getForm().show();
	    });
	    form.addAll(likeBtn, profileBtn, blockBtn);
	    
	}catch(Exception ex){
	    form.removeAll();
	    form.add(new Label("No more users to match with."));
	    form.repaint();
	}
    }
    
    public Form getForm(){
	return this.form;
    }
    
}