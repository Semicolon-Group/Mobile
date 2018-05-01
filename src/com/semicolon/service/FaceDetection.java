/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.service;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.NetworkManager;
import com.codename1.util.Base64;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Elyes
 */
public class FaceDetection {
    private static double confidence = 0;
    
    public static double getMaleConfidence(String path){
	String base64 = encodeToBase64(path);
	HashMap postBody = new HashMap();
	postBody.put("\"image\"", "\"" + base64 + "\"");
	String payload1 = postBody.toString();
	String payload2 = payload1.substring(0, 8) + ":" + payload1.substring(9);
        ConnectionRequest con = new ConnectionRequest(){
	    @Override
	    protected void buildRequestBody(OutputStream os) throws IOException {
                os.write(payload2.getBytes("UTF-8"));
            }
	};
	con.setUrl("http://api.kairos.com/detect");
	con.setPost(true);
	con.setContentType("application/json");
	con.addRequestHeader("app_id", "f44fc0ce");
	con.addRequestHeader("app_key", "f1adc68703b0891fcd9af1426c7f3915");
	con.addResponseListener(e -> {
	    try {
		parseConfidence(new String(con.getResponseData()));
	    } catch (Exception ex) {
		confidence = -1;
	    }
	});
        NetworkManager.getInstance().addToQueueAndWait(con);
	return confidence;
    }
    
    public static String encodeToBase64(String path){
	File file = new File(path);
	String encodedfile = null;
        try {
            DataInputStream baos = new DataInputStream(FileSystemStorage.getInstance().openInputStream(path));
	    byte[] bytes = new byte[(int) file.length()];
	    baos.read(bytes);
            encodedfile = Base64.encodeNoNewline(bytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encodedfile;
    }
    
    private static void parseConfidence(String str) throws Exception{
        try {
            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        JSONObject json = (JSONObject) parser.parse(str.toString());
        JSONArray array = (JSONArray)json.values().toArray()[0];
        json = (JSONObject)array.get(0);
        array = (JSONArray)((JSONArray)json.get("faces"));
        if(array.size() > 1)
            throw new Exception();
        Object conf = ((JSONObject)((JSONObject)((JSONObject)array.get(0)).get("attributes")).get("gender")).get("maleConfidence");
        if(conf instanceof Double)
            confidence = (double)conf;
        if(conf instanceof Long)
            confidence = (long)conf + 0.0d;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
