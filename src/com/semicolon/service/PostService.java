/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.service;

import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.semicolon.entity.Address;
import com.semicolon.entity.Enumerations;
import com.semicolon.entity.Enumerations.PostType;
import com.semicolon.entity.Member;
import com.semicolon.entity.Post;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Elyes
 */
public class PostService {
    private List<Post> list;
    private static PostService instance;
    private Post singlePost;
    private static boolean verif = true;
    
    public static PostService getInstance(){
        if(instance == null)
            instance = new PostService();
        return instance;
    }
    private PostService(){
        list = new ArrayList();
    }
    
    public List<Post> getAll(int onlineId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/get_posts?id=" + onlineId;
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            list = parsePosts(str);
	    addToLocal(list);
        });
	con.addExceptionListener(e -> {
	    list = getFromLocal();
	});
        NetworkManager.getInstance().addToQueueAndWait(con);
        return list;
    }
    
    private List<Post> parsePosts(String json){
        list = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            
            Map<String, Object> postMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
            
            List<Map<String, Object>> postList = (List<Map<String, Object>>) postMap.get("root");
            
            for (Map<String, Object> obj : postList) {
                Post p = new Post();
                p.setId((int)Float.parseFloat(obj.get("id").toString()));
                p.setType((int)Float.parseFloat(obj.get("type").toString()));
                p.setUserId((int)Float.parseFloat(obj.get("userId").toString()));
                p.setUserPhoto(obj.get("userPhoto").toString());
                p.setContent(obj.get("content").toString());
                p.setTime(obj.get("time").toString());
                p.setUserName(obj.get("userName").toString());
                p.setNbrReaction((int)Float.parseFloat(obj.get("nbrReaction").toString()));
                p.setNbrComment((int)Float.parseFloat(obj.get("nbrComment").toString()));
                p.setCurrReaction((int)Float.parseFloat(obj.get("currReaction").toString()));
                list.add(p);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    
    public Post create(String text, int userId){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/create_post?text=" + text + "&userId=" + userId;
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            singlePost = parseSinglePost(str);
        });
	con.addExceptionListener(e -> {
	    singlePost = null;
	});
        NetworkManager.getInstance().addToQueueAndWait(con);
	return singlePost;
    }
    
    private Post parseSinglePost(String str){
	list = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            
            Map<String, Object> postMap = j.parseJSON(new CharArrayReader(str.toCharArray()));
            
            List<Map<String, Object>> postList = (List<Map<String, Object>>) postMap.get("root");
            
            for (Map<String, Object> obj : postList) {
                Post p = new Post();
                p.setId((int)Float.parseFloat(obj.get("id").toString()));
                p.setType((int)Float.parseFloat(obj.get("type").toString()));
                p.setUserId((int)Float.parseFloat(obj.get("userId").toString()));
                p.setUserPhoto(obj.get("userPhoto").toString());
                p.setContent(obj.get("content").toString());
                p.setTime(obj.get("time").toString());
                p.setUserName(obj.get("userName").toString());
                p.setNbrReaction((int)Float.parseFloat(obj.get("nbrReaction").toString()));
                p.setNbrComment((int)Float.parseFloat(obj.get("nbrComment").toString()));
                p.setCurrReaction(0);
                list.add(p);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return list.get(0);
    }

    public boolean delete(int id) {
	verif = true;
	ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/delete_post?id=" + id;
        con.setUrl(url);
	con.addExceptionListener(e -> {
	    verif = false;
	});
        NetworkManager.getInstance().addToQueueAndWait(con);
	return verif;
    }
    
    public void addToLocal(List<Post> list){
	try {
            Database database = Database.openOrCreate("mysoulmate");
            database.execute("create table if not exists post(id INTEGER, userId INTEGER, type INTEGER, nbrReaction INTEGER, nbrComment INTEGER, currReaction INTEGER, userPhoto text, userName text, time text, content text);");
            String deleteQuery = "delete from post";
            database.execute(deleteQuery);
            for(Post p : list){
		if(p.getType() == PostType.PICTURE.ordinal())
		    continue;
		String insertQuery = "insert into post values(?,?,?,?,?,?,?,?,?,?)";
		List<String> params = new ArrayList();
		params.add("" + p.getId());
		params.add("" + p.getUserId());
		params.add("" + p.getType());
		params.add("" + p.getNbrReaction());
		params.add("" + p.getNbrComment());
		params.add("" + p.getCurrReaction());
		params.add(p.getUserPhoto());
		params.add(p.getUserName());
		params.add(p.getTime());
		params.add(p.getContent());
		database.execute(insertQuery, params.toArray());
	    }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public List<Post> getFromLocal(){
	List<Post> listP = new ArrayList();
	try {
            Database database = Database.openOrCreate("mysoulmate");
            Cursor c = database.executeQuery("select * from post");
            while(c.next()){
		Row r = c.getRow();
		Post p = new Post();
		p.setId(r.getInteger(0));
		p.setUserId(r.getInteger(1));
		p.setType(r.getInteger(2));
		p.setNbrReaction(r.getInteger(3));
		p.setNbrComment(r.getInteger(4));
		p.setCurrReaction(r.getInteger(5));
		p.setUserPhoto(r.getString(6));
		p.setUserName(r.getString(7));
		p.setTime(r.getString(8));
		p.setContent(r.getString(9));
		p.setOffLine(true);
		listP.add(p);
	    }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	return listP;
    }
}
