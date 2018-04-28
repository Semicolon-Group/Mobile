package com.semicolon.service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.semicolon.entity.Block;
import com.semicolon.entity.Like;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BlockService {
    private List<Block> blocks;
    private static BlockService instance;
    
    private BlockService(){}
    
    public static BlockService getInstance(){
        if(instance == null)
            instance = new BlockService();
        return instance;
    }
    
    public List<Block> getMemberBlocks(int id){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/getUserBlocks/"+id;
        con.setUrl(url);
        con.addResponseListener((e) -> {
            try {
                String str = new String(con.getResponseData());
                blocks = new ArrayList<>();
                JSONParser j = new JSONParser();
                
                Map<String, Object> blockMap = j.parseJSON(new CharArrayReader(str.toCharArray()));
                List<Map<String, Object>> blockList = (List<Map<String, Object>>) blockMap.get("root");
                if(blockList != null){
                    for(Map<String, Object> map : blockList){
                        blocks.add(parseBlock(map));
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return blocks;
    }
    
    private Block parseBlock(Map<String, Object> map){
        Block b = new Block();
        b.setDate(new Date((((Double)((Map<String, Object>)map.get("date")).get("timestamp")).longValue()*1000)));
        b.setSenderId(((Double)((Map)map.get("blockSender")).get("id")).intValue());
        b.setReceiverId(((Double)((Map)map.get("blockReceiver")).get("id")).intValue());
        return b;
    }
    
    private Block block;
    public Block getBlock(int senderId, int receiverId){
        block = null;
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/getUserBlock/"+senderId+"/"+receiverId;
        con.setUrl(url);
        con.addResponseListener((e) -> {
            try {
                String str = new String(con.getResponseData());
                JSONParser j = new JSONParser();
                
                Map<String, Object> blockMap = j.parseJSON(new CharArrayReader(str.toCharArray()));
                if(blockMap != null && !blockMap.isEmpty()){
                    block = parseBlock(blockMap);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return block;
    }
    
    public void removeBlock(int senderId, int receiverId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/removeBlock/"+senderId+"/"+receiverId;
        con.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public void blockUser(int senderId, int receiverId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/blockUser/"+senderId+"/"+receiverId;
        con.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
}
