/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.semicolon.entity.Comment;
import com.semicolon.entity.Post;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Elyes
 */
public class CommentService {
    private List<Comment> list;
    private static CommentService instance;
    private Comment singleComment;
    
    public static CommentService getInstance(){
        if (instance == null)
            instance = new CommentService();
        return instance;
    }
    private CommentService(){
        
    }
    
    public List<Comment> getAll(Post post){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/get_comments?id=" + post.getId() + "&type=" + post.getType();
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            list = parseComments(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return list;
    }
    
    private List<Comment> parseComments(String json){
        list = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            
            Map<String, Object> postMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
            
            List<Map<String, Object>> postList = (List<Map<String, Object>>) postMap.get("root");
            
            for (Map<String, Object> obj : postList) {
                Comment c = new Comment();
                c.setId((int)Float.parseFloat(obj.get("id").toString()));
                c.setSenderId((int)Float.parseFloat(obj.get("senderId").toString()));
                c.setPhotoUrl(obj.get("photoUrl").toString());
                c.setContent(obj.get("content").toString());
                list.add(c);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    
    public Comment create(Post post, String text, int senderId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/create_comment?postId=" + post.getId() + "&type=" + post.getType() + "&text=" + text + "&senderId=" + senderId;
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            singleComment = parseSingleComment(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
	return singleComment;
    }
    
    private Comment parseSingleComment(String str){
	list = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            
            Map<String, Object> postMap = j.parseJSON(new CharArrayReader(str.toCharArray()));
            
            List<Map<String, Object>> postList = (List<Map<String, Object>>) postMap.get("root");
            
            for (Map<String, Object> obj : postList) {
                Comment c = new Comment();
                c.setId((int)Float.parseFloat(obj.get("id").toString()));
                c.setSenderId((int)Float.parseFloat(obj.get("senderId").toString()));
                c.setPhotoUrl(obj.get("photoUrl").toString());
                c.setContent(obj.get("content").toString());
                list.add(c);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return list.get(0);
    }

    public void delete(int id) {
	ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/delete_comment?id=" + id;
        con.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
}
