/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.service;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.semicolon.entity.Answer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static jdk.nashorn.internal.objects.NativeArray.map;
import static jdk.nashorn.internal.objects.NativeDebug.map;


/**
 *
 * @author vaider
 */
public class AnswerService {
    
        int idUser = 1;

    public void addAnswer(Answer AN) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/mysoulmate/web/app_dev.php/service/answer/addanswer" + AN.getObjet() + "&question=" + AN.getQuestionId() + "&idUser=" + idUser;
        con.setUrl(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
     public ArrayList<Answer> getListAnswers(String json) {

        ArrayList<Answer> listAnswers = new ArrayList<>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
            System.out.println(etudiants);

            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("root");

            for (Map<String, Object> obj : list) {
                Answer AN = new Answer();

                // System.out.println(obj.get("id"));
                float id = Float.parseFloat(obj.get("id").toString());
                System.out.println(id);
                AN.setId((int) id);
                //e.setId(Integer.parseInt(obj.get("id").toString().trim()));
                //AN.setObjet(obj.get("objet").toString());
            Map<String, Object> questions = j.parseJSON(new CharArrayReader(obj.get("question").toString().toCharArray()));
                AN.setQuestionId(((Double)((Map<String, Object>)obj.get("question")).get("id")).intValue());

                           //AN.setQuestionId((int) questions.get("id"));

                System.out.println(AN);
                listAnswers.add(AN);

            }

        } catch (IOException ex) {
        }
        System.out.println(listAnswers);
        return listAnswers;

    }
     ArrayList<Answer> listAnswers = new ArrayList<>();

    public ArrayList<Answer> getListAnswer2() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/mysoulmate/web/app_dev.php/service/answer/allanswer");


        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                AnswerService ser = new AnswerService();
                listAnswers = ser.getListAnswers(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listAnswers;
    }
    
   
    
    
    

    
}
