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
import com.codename1.io.File;


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
import com.codename1.social.FacebookConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
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

    /**
     *
     * @throws IOException
     */
    public Login1() {

        loginField = new TextField();
        mdpField = new TextField();
        loginField.setHint("Login");
        mdpField.setHint("Mdp");
        mdpField.setConstraint(TextField.PASSWORD);
        confirmerBtn = new Button("Connexion");
        registerBtn = new Button("Register");
        fbButton = new Button("Facebook Login");

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
                        Form page2 = new Form("Welcome");
                        Button back = new Button("Back");
                        back.addActionListener(b -> {
                            show();
                        });
                        page2.add(back);
                        page2.show();

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
        mySoulMate = new Form("MySoulMate");
        mySoulMate.add(loginContainer);

    }

    public void show() {
        mySoulMate.show();
    }

}
