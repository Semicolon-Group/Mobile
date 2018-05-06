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
import com.codename1.ui.animations.BubbleTransition;
import com.semicolon.entity.Notification;
import com.semicolon.mysoulmate.MyApplication;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map;

/**
 *
 * @author vaider
 */
public class NotificationView {
     Form f;
     public Resources theme;
     EncodedImage enc;
    Image imgs;
    ImageViewer imgv;
    private Form parentForm;


    public NotificationView(Form parentForm, int id) {
      String url="http://localhost/mysoulmate/web/app_dev.php/service/notification/notif/"+2;
        System.out.println(url);
        this.parentForm = parentForm;
      ConnectionRequest con =  new ConnectionRequest();
        UIBuilder ui = new UIBuilder();
        Resources themeH = UIManager.initFirstTheme("/theme_h");
        f = ui.createContainer(themeH, "GUI 2").getComponentForm();
        con.setUrl(url);
        f.getToolbar().addCommandToLeftBar("Back", MyApplication.theme.getImage("back-command.png"), (e) -> {
            parentForm.showBack();
        });
       con.addResponseListener(new ActionListener<NetworkEvent>() {

          @Override
          public void actionPerformed(NetworkEvent evt) {
              
            

              ArrayList<Notification> lv = getListnotification(new String(con.getResponseData()));
           Container contY = new Container(BoxLayout.y());
                    for (Notification lv1 : lv) {
                        
                        
               //   Button delete = new Button("supprimer");
                  
                   
                  
                        System.out.println(lv1.getDate());
                    Container contX = new Container(BoxLayout.x());
                    Label d = new Label();
                    
                    

                    Label name = new Label(lv1.getNameUser());
                        //System.out.println(lv1.getImage());

                    try {
                        enc = EncodedImage.create("/load.png");
                    } catch (IOException ex) {
                    }
ImageViewer imgv = new ImageViewer(URLImage.createToStorage(enc, "http://localhost/semicolon.png", "http://localhost/semicolon.png", URLImage.RESIZE_SCALE)
                            .scaled(30, 30));
                    //  Image imgs;
                    // ImageViewer imgv;
                   

                    contY.add(name);
                    Container k = new Container(new BorderLayout());
                   //k.add(BorderLayout.CENTER, imgv);
                   contY.setLayout(BoxLayout.y());

                    contY.add(BorderLayout.CENTER);
                    
                    MultiButton gui_LA = new MultiButton("");
                    FontImage.setMaterialIcon(gui_LA, FontImage.MATERIAL_HOTEL);
                    gui_LA.setIconPosition(BorderLayout.EAST);
                    //contY.add(BorderLayout.SOUTH,new Label(lv1.getCountry()));
                  //   Container head = new  Container();
                    //  head.add(new Label("aa"));
                    //  contY.add(BorderLayout.NORTH,new Label(lv1.getName()));

                   
                    Container foot = new Container();
                    foot.setLayout(new BorderLayout());
                    

                    Container descContainer = new Container();
                    contX.setScrollableY(true);
                    /*TextArea desci = new TextArea(lv1.getDescription());
                  desci.setRows(2);
                  
        desci.setColumns(100);
        desci.setGrowByContent(false);
        desci.setEditable(false);
        desci.setSmoothScrolling(true);

        descContainer.setLayout(new BorderLayout());
                    descContainer.add(BorderLayout.WEST,desci);;
                 foot.add(BorderLayout.WEST,descContainer);
                     */
                    //   gui_LA.set
                    //  gui_LA.setVisible(false);
                    contY.add(BorderLayout.SOUTH);
                    contX.setLayout((new BorderLayout()));
                    Container head = new Container(BoxLayout.y());
                    head.setLayout(new BorderLayout());
                    Container headname = new Container();
                    //ahawa
                    headname.setLayout(new BorderLayout());
                    // headname.add(BorderLayout.NORTH,new Label(lv1.getName()));
                    Container hotelstar = new Container(BoxLayout.x());

                    Label type = new Label(lv1.getContent());
                    type.getAllStyles().setMarginBottom(1);
                    Container namehotelc = new Container();
                    namehotelc.add(imgv);
                    Container xnames = new Container(BoxLayout.y());

                    xnames.add(type);
                    Label namepromo = new Label(lv1.getNameUser());
                    xnames.add(namepromo);

                   namehotelc.add(xnames);

                    type.getAllStyles().setFgColor(0xffffff);

                    // nom.getStyle().setFgColor();
                    headname.add(BorderLayout.SOUTH, namehotelc);

                    // headname.add(BorderLayout.CENTER,"stars");
                    head.add(BorderLayout.WEST, headname);
                    // head.add(BorderLayout.CENTER,headname);
                   // head.add(BorderLayout.EAST, new Label(lv1.getPrix() + "€"));
                    //  head.setUIID("Container_uiid_name");
                    head.getStyle().setBgColor(0x329b1a);
                    //  Font fnt = Font.createTrueTypeFont("Achilles", );

                    //  contY.getStyle().setFont(ff);
                    // head.getStyle().setFont(ff);
                    head.getStyle().setBgTransparency(122);
                    foot.getStyle().setBgColor(0xefe888);
                    foot.getStyle().setBgTransparency(122);
                    Button showBubble = new Button("+");
                    showBubble.setName("BubbleButton");
                    
                  
String aa ="                                  ";
String bb ="                                                      ";


       
     foot.add(BorderLayout.WEST,aa);
 
 
 
                    
                    foot.add(BorderLayout.EAST, showBubble);
                    
                    Style buttonStyle = showBubble.getAllStyles();
                    buttonStyle.setBorder(Border.createEmpty());
                    buttonStyle.setFgColor(0xFFFFFF);
                    buttonStyle.setBgPainter((g, rect) -> {
                        g.setColor(0xff);
                        int actualWidth = rect.getWidth();
                        int actualHeight = rect.getHeight();
                        int xPos, yPos;
                        int size;
                        if (actualWidth > actualHeight) {
                            yPos = rect.getY();
                            xPos = rect.getX() + (actualWidth - actualHeight) / 2;
                            size = actualHeight;
                        } else {
                            yPos = rect.getY() + (actualHeight - actualWidth) / 2;
                            xPos = rect.getX();
                            size = actualWidth;
                        }
                        g.setAntiAliased(true);
                        g.fillArc(xPos, yPos, size, size, 0, 360);
                    });
                   // f.add(showBubble);
                   // f.setTintColor(0);
                    showBubble.addActionListener((e) -> {
                        //Dialog dlg = new Dialog(""+lv1.getUsername().toString()+"");
                        Dialog dlg = new Dialog("pop");

                        dlg.setLayout(new BorderLayout());
                        //SpanLabel sl = new SpanLabel("Commentaire:" + lv1.getCommentaire() );
                        SpanLabel sl = new SpanLabel("Commentaire:" );
                        dlg.add(BorderLayout.CENTER, sl);
                        dlg.setTransitionInAnimator(new BubbleTransition(500, "BubbleButton"));
                        dlg.setTransitionOutAnimator(new BubbleTransition(500, "BubbleButton"));
                        dlg.setDisposeWhenPointerOutOfBounds(true);
                        dlg.getTitleStyle().setFgColor(0x00CCFF);
                        Style dlgStyle = dlg.getDialogStyle();
                        dlgStyle.setBorder(Border.createEmpty());
                        dlgStyle.setBgColor(0xFFFFFF);
                        dlgStyle.setBgTransparency(19);
                        dlg.showPacked(BorderLayout.NORTH, true);
                    });

                    //    head.getStyle().setBgColor(0xFF0000);
                    //   head.getComponentForm().repaint();
                    contY.add(BorderLayout.NORTH);
                    contX.add(BorderLayout.CENTER, contY);
                    Button btnom = new Button(lv1.getNameUser());
                    int idd = lv1.getId();
                   
                    head.setLeadComponent(btnom);

                    contY.getStyle().setMarginBottom(40);
                  
             f.add(contX);
                    }
                      
                   f.add(contY);
                    f.show(); 
                    f.refreshTheme();
                    
          }
          
          
      });
       
                    
                NetworkManager.getInstance().addToQueue(con);
               
                    

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

     public ArrayList<Notification> getListnotification(String json) {
        ArrayList<Notification> listCours = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();
            Map<String, Object> croiz = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) croiz.get("root");
            for (Map<String, Object> obj : list) {
                Notification e = new Notification();
                e.setNameUser(obj.get("username").toString());
                 e.setDate(obj.get("date").toString());
                 e.setContent(obj.get("content").toString());
               // System.out.println(obj.get("nom").toString());
               //e.setId(Integer.parseInt(obj.get("id").toString()));
                
             // double a = (double)double.valueOf((int) (double) obj.get("id"));
               //
                listCours.add(e);

            }

        } catch (IOException ex) {
        }

        return listCours;

    }   
}

    
    
     
