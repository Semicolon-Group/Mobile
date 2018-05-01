/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.codename1.components.MultiButton;
import com.codename1.io.Util;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.CheckBox;
import com.codename1.ui.animations.BubbleTransition;
import com.semicolon.entity.Choice;
import com.semicolon.entity.Question;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author vaider
 */
public class Detailquestion {
    Form f ;
    public static int id;
    public static int choix;

    public Detailquestion(Resources theme,int id,String nom,int idu) {
        UIBuilder ui = new UIBuilder();
        f = ui.createContainer(theme, "listquestion").getComponentForm();
         ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/mysoulmate/web/app_dev.php/service/answer/quest?id=" + id);
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
             
             Label a = new Label(nom);
             ArrayList<Choice> lv = getListchoice(new String(con.getResponseData()));
                  f.add(a);
                for (Choice lv1 : lv) {
                    Container contY = new Container(BoxLayout.y());
                    CheckBox c = new CheckBox(lv1.getChoice());
                    c.setName(lv1.getId()+"");
                    
                    f.add(c);
                    Container contX = new Container(BoxLayout.x());
                    contX.add(contY);
                    contX.setLeadComponent(c);
                    c.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent evt) {
                     
                            if(c.isSelected()==true)
                            {
                       System.out.println(c.getName());
                       choix = (Integer.parseInt(c.getName()));

                            }
                                           }
                    });
                   
                }
                Button add = new Button("Add");
                f.add(add);
                 add.addActionListener((e) -> {
            String url = "http://localhost/mysoulmate/web/app_dev.php/service/answer/new"
                    + "?id_selected="+choix
                    +"&id_question="+id
                    +"&id_user="+idu;
            System.out.println(url);
            Listquestion aa = new Listquestion(theme,idu);
            aa.getF().show();
            f.refreshTheme();
                ConnectionRequest con2 = new ConnectionRequest(url);
           
                con2.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    String response = new String(con.getResponseData());
                    if(response.equals("\"ok\"")){  
                          System.out.println("ok"+response.toString());
                          
                           f.refreshTheme();
                           
                        }
                        else 
                            System.out.println("ERROR !"+response.toString());
 
                }
            });
                 NetworkManager.getInstance().addToQueue(con2);
                

            

      
        });
    f.refreshTheme();
                setBackCommand(f, theme);
            }
        });
            NetworkManager.getInstance().addToQueue(con);
            f.refreshTheme();
        
    }

    public Form getF() {
        return f;
    }
protected void setBackCommand(Form f, Resources theme) {
        Command back = new Command("") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                Listquestion list = new Listquestion(theme,id);
                list.getF().show();
            }

        };
        Image img = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, UIManager.getInstance().getComponentStyle("TitleCommand"));
        back.setIcon(img);
        f.getToolbar().addCommandToLeftBar(back);
        f.getToolbar().setTitleCentered(true);
        f.setBackCommand(back);
    }
    public ArrayList<Choice> getListchoice(String json) {
        ArrayList<Choice> listCours = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();
            Map<String, Object> croiz = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) croiz.get("root");
            for (Map<String, Object> obj : list) {
                Choice e = new Choice();
                
                e.setChoice(obj.get("choice").toString());
               // System.out.println(obj.get("nom").toString());
               //e.setId(Integer.parseInt(obj.get("id").toString()));
                
             // double a = (double)double.valueOf((int) (double) obj.get("id"));
                double a = (double) obj.get("id");
           
                e.setId((int) a);
                System.out.println(a);
                listCours.add(e);

            }

        } catch (IOException ex) {
        }

        return listCours;

    }   
    
}
