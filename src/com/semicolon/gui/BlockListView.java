package com.semicolon.gui;

import com.codename1.ui.Container;
import com.codename1.ui.Form;
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
        blocks = BlockService.getInstance().getMemberBlocks(memberId);
        buildContainer();
        form.repaint();
    }
    
    public BlockListView(Form parentForm, int memberId){
        instance = this;
        this.parentForm = parentForm;
        this.memberId = memberId;
        this.form = new Form("Blocks", new BorderLayout());
        blocks = BlockService.getInstance().getMemberBlocks(memberId);
        buildContainer();
        form.getToolbar().addCommandToLeftBar("Back", MyApplication.theme.getImage("back-command.png"), (e) -> {
           parentForm.showBack();
        });
    }
    
    private void buildContainer(){
        Container c = new Container(BoxLayout.y());
        c.setScrollableY(true);
        
        for(Block block : blocks){
            c.add((new BlockView(block)).getContainer());
        }
        
        form.removeAll();
        form.add(BorderLayout.CENTER, c);
    }
    
    public Form getForm(){
        return form;
    }
}
