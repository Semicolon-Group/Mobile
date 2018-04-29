package com.semicolon.gui;

import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.semicolon.entity.Block;
import com.semicolon.entity.Like;
import com.semicolon.entity.Member;
import com.semicolon.entity.Photo;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.BlockService;
import com.semicolon.service.MemberService;
import com.semicolon.service.PhotoService;
import java.util.Random;

public class BlockView {
    private Container container;
    private final Block block;
    private final Member member;
    
    public BlockView(Block block){
        this.block = block;
        this.member = MemberService.getInstance().getMember(block.getReceiverId());
        this.container = new Container(BoxLayout.x());
        buildContainer();
    }
    
    private void buildContainer(){
        Container leftContainer = new Container(BoxLayout.y());
        leftContainer.setWidth(20);
        Container rightContainer = new Container(new BorderLayout());
        
        Label imgv;
        Photo profilePhoto = PhotoService.getInstance().getProfilePhoto(member.getId());
        if(profilePhoto != null && !profilePhoto.getPhotoUri().isEmpty()){
            EncodedImage enc = EncodedImage.createFromImage(MyApplication.theme.getImage("loading.png"), false);
            URLImage urlImage = URLImage.createToStorage(enc, (new Random()).nextInt()+"", profilePhoto.getPhotoUri());
            imgv = new Label(urlImage);
        }else{
            Image i = MyApplication.theme.getImage("default-like.jpg");
            imgv = new Label(i);
        }
        leftContainer.add(imgv);
        
        Container firstRow = new Container(new BorderLayout());
        SpanLabel name = new SpanLabel(member.getFirstname()+" " + member.getLastname());
        name.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Container nameContainer = new Container();
        nameContainer.add(name);
        
        Button removeBt = new Button(MyApplication.theme.getImage("remove.png"));
        removeBt.getUnselectedStyle().setBorder(Border.createEmpty());
        removeBt.addActionListener((e) -> {
            BlockService.getInstance().removeBlock(MyApplication.MemberId, block.getReceiverId());
            BlockListView.update();
        });
        
        firstRow.add(BorderLayout.CENTER, nameContainer);
        firstRow.add(BorderLayout.EAST, removeBt);
        firstRow.getAllStyles().setMarginBottom(0);
        firstRow.getAllStyles().setPaddingBottom(0);
        
        SpanLabel address = new SpanLabel("Blocked since: "+(new SimpleDateFormat("dd MMMM, yyyy")).format(block.getDate()));
        address.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        address.getAllStyles().setMarginTop(0);
        address.getAllStyles().setPaddingTop(0);
        
        
        rightContainer.add(BorderLayout.NORTH, firstRow);
        rightContainer.add(BorderLayout.SOUTH, address);
        
        container.add(leftContainer);
        container.add(rightContainer);
    }
    
    public Container getContainer(){
        return this.container;
    }
}
