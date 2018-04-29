/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.gui;
import com.semicolon.service.AnswerService;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 *
 * @author vaider
 */
public class NotificationView {
     Form formAffichage;
    
    public NotificationView(Resources theme){
      String url="http://localhost/Notification/select_Notif.php?utlisateur="+2;
      ConnectionRequest request=  new ConnectionRequest(url);
        UIBuilder ui = new UIBuilder();
        formAffichage = ui.createContainer(theme, "GUI 2").getComponentForm();
        request.addResponseListener((evt) -> {
 try {
                JSONParser parser= new JSONParser();
                Map<String, Object> result = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData())));
                List<Map<String,Object>> events =(List<Map<String,Object>>) result.get("root");
                for (int i=0;i<events.size();i++){
                    Map<String,Object> evenement= events.get(i);

		//Container cnt1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        //Label username= new Label((String) evenement.get("username"));
        //Label content= new Label((String) evenement.get("content"));
        //Label importance= new Label("importance: "+evenement.get("importance"));
	//Label sep = new Label("----------");

        //cnt1.add(username);
        //cnt1.add(content);
        //cnt1.add(importance);
        //cnt1.add(sep);
                    
        Container cnt1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Container cnt2=new Container(BoxLayout.x());

        Label username= new Label((String) evenement.get("username"));
        Label content= new Label((String) evenement.get("content"));
        cnt1.add(username);
        cnt2.add(cnt1);
        cnt2.add(content);
        //return cnt2;

	formAffichage.add(cnt2);
        formAffichage.show();

	 }
                
            } catch (IOException ex) {
                ex.printStackTrace();
                //Logger.getLogger(ParsingTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        
    }
   
        
    
    
     public Form getFormAffichage() {
        return formAffichage;
    }   

    public void getFormAffichage (Form formAffichage) {
        this.formAffichage = formAffichage;
    }
}
