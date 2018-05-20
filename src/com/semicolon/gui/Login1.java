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

import com.codename1.ui.Container;
import com.codename1.ui.Form;

import com.codename1.ui.TextField;
import org.json.*;

import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;

import com.codename1.messaging.Message;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;

import com.codename1.ui.Label;

import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.FBLogin6;

import com.semicolon.entity.Member;

import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.MemberService;
import java.util.Random;

import java.io.IOException;

import java.util.Date;

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
    private String url2 = "http://localhost/mysoulmate/web/app_dev.php/service/Newpass/";
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

    TextField newpw ;
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

            recovery.getToolbar().addCommandToLeftBar("Back", MyApplication.theme.getImage("back-command.png"), (ee) -> {
                Conversationsgui cs = new Conversationsgui();
                cs.show();

            });
            Container cont = new Container(BoxLayout.y());
            Label lb = new Label("Please insert your Email");
            TextField email = new TextField();
            email.setHint("Write your email here : ");
            Button confirm = new Button("Recover my password");
            TextField tokentext = new TextField();
            newpw = new TextField();
            newpw.setConstraint(TextField.PASSWORD);
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
            System.out.println(token);
            tokenconfirm.addActionListener(rrr -> {

                if (tokentext.getText() == "" || newpw.getText() == "") {
                    
                    
                    System.out.println(token);
                    Dialog.show("Check your infos ", "check your token or new password", "OK", null);
                } else {
                    
                    Member l = MemberService.getInstance().getMember(mem);
                    ChangePassword(user.getPseudo());
                    MyApplication.MemberId = l.getId();
                    MyApplication mc = new MyApplication();
                    mc.start();
                }

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
    public String password;
    
    private void ChangePassword(String name)
    {
        connectionRequest = new ConnectionRequest();
        connectionRequest.setPost(false);
        connectionRequest.setUrl(url2 + name +"-"+newpw.getText());
       
        NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
        
    }

    private void LoginAction() {
        connectionRequest = new ConnectionRequest();
        connectionRequest.setPost(false);
        connectionRequest.setUrl(url + loginField.getText()+"-"+mdpField.getText());
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
                    System.out.println(MEMBER_ID);
                     
                }
            } catch (JSONException ex) {

            }
            if (MEMBER_ID != 0) {
                
                    populateBd();
                    MyApplication.MemberId = MEMBER_ID;
                    MyApplication.onlineId = MEMBER_ID;
                    Member m = MemberService.getInstance().getMember(MyApplication.onlineId);
                    m.setConnected(true);
                    m.setLastLogin(new Date());
                    MemberService.getInstance().editMemeber(m);
                    new NewsfeedView().getForm().show();
                    MyApplication.MemberId = m.getId();
                } else {
                    Dialog.show("Wrong credentials", "Error", "OK", "Cancel");

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
