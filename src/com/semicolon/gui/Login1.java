/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.gui;

/**
 *
 * @author badis
 */
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import org.json.*;



import java.io.IOException;



import com.codename1.components.ScaleImageLabel;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.facebook.User;
import com.codename1.io.Storage;
import com.codename1.social.FacebookConnect;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Button;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;
import com.codename1.components.ScaleImageLabel;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.facebook.User;
import com.codename1.io.JSONParser;
import com.codename1.io.Storage;
import com.codename1.messaging.Message;
import com.codename1.social.FacebookConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.FBLogin6;
import com.mycompany.myapp.UserForm;
import com.semicolon.mysoulmate.MyApplication;
import java.util.Random;

import java.io.IOException;
import jdk.nashorn.internal.objects.NativeString;





public class Login1 {

    private static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";
     public static int MEMBER_ID = 0;
    TextField loginField;
    TextField mdpField;
    Container loginContainer;
    Button confirmerBtn;
    Button registerBtn;
    Button fbButton;
    Form mySoulMate;
    private String url = "http://localhost/mysoulmate/web/app_dev.php/service/Login/";
    private ConnectionRequest connectionRequest;
    String login = "";
    String pw = "";
    String status = "";
    String appSecret = "15378d7426361fe464f5af2e08f780e3";
    String domain = "http://localhost";
    String appId = "212394559315715";
    Button qr ;
    Button msg ;

    /**
     *
     * @throws IOException
     */
    public Login1() {
        Random r = new Random();
        r.setSeed(12151532);
        System.out.println((r.toString()).substring(17));

        loginField = new TextField();
        mdpField = new TextField();
        loginField.setHint("Login");
        mdpField.setHint("Mdp");
        mdpField.setConstraint(TextField.PASSWORD);
        confirmerBtn = new Button("Connexion");
        registerBtn = new Button("Register");
        fbButton = new Button("Facebook Login");
        qr = new Button("qr");
        msg = new Button("msg");
        Button convs = new Button("convs");
        msg.addActionListener(ff->{
         Recovery rc = new Recovery();
                        rc.show();
        
        });
        convs.addActionListener(e->{
        Conversationsgui cv = new Conversationsgui();
        cv.show();
        });

        confirmerBtn.addActionListener(e -> {
            connectionRequest = new ConnectionRequest();
            connectionRequest.setPost(false);
            connectionRequest.setUrl(url + loginField.getText() + "-" + mdpField.getText());
            connectionRequest.addResponseListener(a -> {

                try {
                    String resultat = new String(connectionRequest.getResponseData());

                    JSONArray jsonarray = new JSONArray(resultat);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String name = jsonobject.getString("firstname");
                        String url = jsonobject.getString("lastname");
                        final int id = jsonobject.getInt("id");
                        MEMBER_ID = id;
                        System.out.println(name + "  , " + url + " ; id : " + id);
                    }
                    if (MEMBER_ID != 0) {
//             Message m = new Message("<html><body>Check out <a href=\"https://www.codenameone.com/\">Codename One</a></body></html>");
//m.setMimeType(Message.MIME_HTML);
//
//// notice that we provide a plain text alternative as well in the send method
//boolean success = m.sendMessageViaCloudSync("Codename One", "badis.maalej@gmail.com", "Name Of User", "Message Subject",
//                            "Check out Codename One at https://www.codenameone.com/");
//                Display.getInstance().sendMessage(new String[] {"someone@gmail.com"}, "Subject of message", m);

                
//                        Form page2 = new Form("Welcome");
//                        Button back = new Button("Back");
//                        back.addActionListener(b -> {
//                            show();
//                        });
//                        page2.add(back);
//                        page2.show();
                        MyApplication.MEMBER_ID= MEMBER_ID;
                        Recovery rc = new Recovery();
                        rc.show();

                    } else {
                        Dialog.show("Wrong credentials", "Error", "OK", "Cancel");

                    }

                } catch (JSONException ex) {

                }

            });
            NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
        });
        fbButton.addActionListener(e -> {
            FBLogin6 fb = new FBLogin6();
            fb.start();
            


        });
      
        loginContainer = new Container(BoxLayout.y());
        loginContainer.add(loginField);
        loginContainer.add(mdpField);
        loginContainer.add(confirmerBtn);
        loginContainer.add(registerBtn);
        loginContainer.add(fbButton);
        loginContainer.add(msg);
        loginContainer.add(convs);
        
        mySoulMate = new Form("MySoulMate");
        mySoulMate.add(loginContainer);

    }

    public void show() {
        mySoulMate.show();
    }

}
