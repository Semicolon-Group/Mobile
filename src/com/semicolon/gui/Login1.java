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
import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.facebook.User;
import com.codename1.io.JSONParser;
import com.codename1.io.Storage;
import com.codename1.l10n.SimpleDateFormat;
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
import com.semicolon.entity.Address;
import com.semicolon.entity.Enumerations;
import com.semicolon.entity.Member;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.MemberService;
import java.util.Random;

import java.io.IOException;
import java.util.Date;
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
    Button qr;
    Button msg;
    Button recoverButton;
    int mem;
    Member user;
    Member member;
    Database database;

    /**
     *
     * @throws IOException
     */
    private void populateBd() {
        try {
            database = Database.openOrCreate("mysoulmate");
            database.execute("create table if not exists user (login text, password text);");
            String deleteQuery = "delete from user";
            database.execute(deleteQuery);
            String insertQuery = "insert into user (login ,password ) values ('" + loginField.getText() + "','" + mdpField.getText() + "'); ";
            database.execute(insertQuery);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void getMemberFromLocal() {
        try {
            database = Database.openOrCreate("mysoulmate");
            Cursor c = database.executeQuery("select * from user");
            while (c.next()) {
                Row r = c.getRow();
                loginField.setText(r.getString(0));
                mdpField.setText(r.getString(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Login1() {

        Random r = new Random();
        r.setSeed(12151532);
        String token = (r.toString()).substring(17);

        loginField = new TextField();
        mdpField = new TextField();
        loginField.setHint("Login");
        mdpField.setHint("Mdp");
        mdpField.setConstraint(TextField.PASSWORD);
        confirmerBtn = new Button("Connexion");
        registerBtn = new Button("Register");
        fbButton = new Button("Facebook Login");
        recoverButton = new Button("Recover your password");
        recoverButton.addActionListener(e -> {

            Form recovery = new Form("Recover your password");
            Container cont = new Container(BoxLayout.y());
            Label lb = new Label("Please insert your Email");
            TextField email = new TextField();
            email.setHint("Write your email here : ");
            Button confirm = new Button("Recover my password");
            TextField tokentext = new TextField();
            TextField newpw = new TextField();
            newpw.setHint("Put your new Password here : ");
            tokentext.setHint("Put token here :");
            Button tokenconfirm = new Button("Change Password");

            confirm.addActionListener(ee -> {
                mem = MemberService.getInstance().getMemberByEmail(email.getText());
                user = MemberService.getInstance().getMember(mem);
                if (user != null) {
                    Message m = new Message("Here is the token to change your password : " + token);

                    Display.getInstance().sendMessage(new String[]{user.getEmail()}, "Password recovery", m);
                    Dialog.show("Email sent", "We have sent you an email", "OK", null);
                } else {
                    Dialog.show("User not found", "User not found", "OK", null);
                }

            });
            tokenconfirm.addActionListener(rrr -> {

                System.out.println("Old password :" + user.getPassword());
                if (tokentext.getText() == token && newpw.getText() != "") {
                    user.setPassword(newpw.getText());
                    MemberService.getInstance().editMemeber(user);
                }
                Member l = MemberService.getInstance().getMember(mem);
                System.out.println("New password :" + l.getPassword());

            });

            cont.add(lb);
            cont.add(email);
            cont.add(confirm);
            cont.add(tokentext);
            cont.add(newpw);
            cont.add(tokenconfirm);
            recovery.add(cont);

            recovery.show();

        });

        confirmerBtn.addActionListener(e -> {
            LoginAction();
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

        loginContainer.add(recoverButton);
        getMemberFromLocal();
        mySoulMate = new Form("MySoulMate");

        registerBtn.addActionListener(register -> {
            new InscriptionView(mySoulMate).getF().show();
        });
        mySoulMate.add(loginContainer);

    }

    private void LoginAction() {
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
                }
                if (MEMBER_ID != 0) {
                    populateBd();

                    MyApplication.MemberId = MEMBER_ID;
                    MyApplication.onlineId = MEMBER_ID;
                    Member m = MemberService.getInstance().getMember(MEMBER_ID);
                    m.setConnected(true);
                    m.setLastLogin(new Date());

                    MemberService.getInstance().editMemeber(m);
                    new NewsfeedView().getForm().show();
                    MyApplication.MemberId = MEMBER_ID;

                } else {
                    Dialog.show("Wrong credentials", "Error", "OK", "Cancel");

                }

            } catch (JSONException ex) {

            }

        });
        NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
    }

    public void Log(String username, String password) {
        loginField.setText(username);
        mdpField.setText(password);
        LoginAction();
    }

    public Form getContainer() {
        return mySoulMate;
    }

    public void show() {
        mySoulMate.show();
    }

}
