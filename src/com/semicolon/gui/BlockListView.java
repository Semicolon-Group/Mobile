package com.semicolon.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.semicolon.entity.Block;
import com.semicolon.entity.Like;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.BlockService;
import com.semicolon.service.LikeService;
import java.util.List;

public class BlockListView {
    private int memberId;
    private Form form;
    private Form parentForm;
    private List<Block> blocks;
    
    private static BlockListView instance;
    
    public static void update(){
        if(instance != null){
            instance.updateView();
        }
    }
    
    private void updateView(){
        Dialog i = new InfiniteProgress().showInifiniteBlocking();
        blocks = BlockService.getInstance().getMemberBlocks(memberId);
        buildContainer();
        form.revalidate();
        i.dispose();
    }
    
    public BlockListView(Form parentForm, int memberId){
        Dialog i = new InfiniteProgress().showInifiniteBlocking();
        instance = this;
        this.parentForm = parentForm;
        this.memberId = memberId;
        this.form = new Form("Blocks", new BorderLayout());
        blocks = BlockService.getInstance().getMemberBlocks(memberId);
        buildContainer();
        form.getToolbar().addCommandToLeftBar("Back", MyApplication.theme.getImage("back-command.png"), (e) -> {
           parentForm.showBack();
        });
        i.dispose();
    }
    
    private void buildContainer(){
        Container c = new Container(BoxLayout.y());
        c.setScrollableY(true);
        
        for(Block block : blocks){
            c.add((new BlockView(block)).getContainer());
        }
        if(blocks.isEmpty()){
            c.setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
            Label l = new Label("You have no blocks");
            c.add(BorderLayout.CENTER, l);
        }
        
        form.removeAll();
        form.add(BorderLayout.CENTER, c);
    }
    
    public Form getForm(){
        return form;
    }
}
