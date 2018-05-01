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
public class AnswerView {
     Form formAffichage;
    
    public AnswerView(Resources theme){
      String url="http://localhost/Question/select.php?utlisateur="+2;
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

		Container cnt1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label question= new Label("question: "+evenement.get("question"));
        Label choice= new Label("choice: "+evenement.get("choice"));
        //Label importance= new Label("importance: "+evenement.get("importance"));
	//Label sep = new Label("----------");

        cnt1.add(question);
        cnt1.add(choice);
        //cnt1.add(importance);
        //cnt1.add(sep);

	formAffichage.add(cnt1);
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
