/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.gui;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.AutoCompleteTextField;
//import javax.mail.Message;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.validation.Constraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.pofper.maps.api.GooglePlacesApi;
import com.pofper.maps.entity.Address;
import com.semicolon.entity.Enumerations;
import com.semicolon.entity.UserR;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.InscriptionService;
import java.util.ArrayList;
import java.util.List;
//import com.sun.mail.smtp.SMTPTransport;
//import java.util.Date;
////import java.util.Properties;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

/**
 *
 * @author asus
 */
public class InscriptionView {

    Form f;
    TextField tfirstname;
    TextField tlastname;
    TextField tpseudo;
    TextField tphone;
    TextField tabout;
    TextField tchildrennbr;
    TextField tminage;
    TextField tmaxage;
    RadioButton maleRadio;
    RadioButton femaleRadio;
    ButtonGroup group;
    RadioButton smokerRadio;
    RadioButton NoSmokerRadio;
    ButtonGroup group2;
    RadioButton DrinkerRadio;
    RadioButton NoDrinkerRadio;
    ButtonGroup group3;
    Picker birthdate;
    Picker body;
    Picker religion;
    Picker importance;
    Picker maritalstatus;
    Button btnajout;
    public static UserR u1;
    private AutoCompleteTextField addressField;
    private com.semicolon.entity.Address selectedAddress;
    private TextField aboutField;
    private boolean selected;
    private Form parentForm;

//    Picker body;
    public InscriptionView(Form parentForm) {
        this.parentForm = parentForm;
        f = new Form("Inscription", BoxLayout.y());
        f.getToolbar().addCommandToLeftBar("Back", MyApplication.theme.getImage("back-command.png"), (ev) -> {
            parentForm.showBack();
        });
        tfirstname = new TextField();
        tfirstname.setHint("Firstname");
        Label labelname = new Label();
        labelname.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        labelname.getAllStyles().setFgColor(0xFF0000);
        tlastname = new TextField();
        tlastname.setHint("Lastname");
        Label labelLast = new Label();
        labelLast.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        labelLast.getAllStyles().setFgColor(0xFF0000);
        TextField email = new TextField("", "E-Mail", 30, TextField.EMAILADDR);
        Label labelemail = new Label();
        labelemail.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        labelemail.getAllStyles().setFgColor(0xFF0000);
        Label labelemailV = new Label();
        labelemailV.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        labelemailV.getAllStyles().setFgColor(0xFF0000);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        Label labelPassword = new Label();
        labelPassword.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        labelPassword.getAllStyles().setFgColor(0xFF0000);
        TextField confirmPassword = new TextField("", "Confirm Password", 30, TextField.PASSWORD);
        Label labelRPassword = new Label();
        labelRPassword.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        labelRPassword.getAllStyles().setFgColor(0xFF0000);
        tpseudo = new TextField();
        tpseudo.setHint("pseudo");
        Label labelPseudo = new Label();
        labelPseudo.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        labelPseudo.getAllStyles().setFgColor(0xFF0000);
        tphone = new TextField();
        tphone.setHint("phone");
        Label labelPhone = new Label();
        labelPhone.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        labelPhone.getAllStyles().setFgColor(0xFF0000);
        tabout = new TextField();
        tabout.setHint("about");
        Label labelAbout = new Label();
        labelAbout.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        labelAbout.getAllStyles().setFgColor(0xFF0000);
        Container genderContainer = new Container(BoxLayout.x());
        Label Gender = new Label("Gender :");
        Gender.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Gender.getAllStyles().setFgColor(0x09345E);
        Label male = new Label("Male");
        male.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Label female = new Label("Female");
        female.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        maleRadio = new RadioButton();
        femaleRadio = new RadioButton();
        Label Vgender = new Label();
        Vgender.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        Vgender.getAllStyles().setFgColor(0xFF0000);
        genderContainer.add(Gender).add(male).add(maleRadio).add(female).add(femaleRadio);
        group = new ButtonGroup(maleRadio, femaleRadio);

        Container smokerContainer = new Container(BoxLayout.x());
        Label smokerLabel = new Label("Smoker:");
        smokerLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        smokerLabel.getAllStyles().setFgColor(0x09345E);
        Label Smoker = new Label("Yes");
        Smoker.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Label NonSmoker = new Label("No");
        NonSmoker.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        smokerRadio = new RadioButton();
        NoSmokerRadio = new RadioButton();
        Label Vsmoker = new Label();
        Vsmoker.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        Vsmoker.getAllStyles().setFgColor(0xFF0000);
        smokerContainer.add(smokerLabel).add(Smoker).add(smokerRadio).add(NonSmoker).add(NoSmokerRadio);
        group2 = new ButtonGroup(smokerRadio, NoSmokerRadio);
        Container drinkerContainer = new Container(BoxLayout.x());
        Label drinkerLabel = new Label("Drinker:");
        drinkerLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        drinkerLabel.getAllStyles().setFgColor(0x09345E);
        Label Drinker = new Label("Yes");
        Drinker.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Label NonDrinker = new Label("No");
        NonDrinker.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        DrinkerRadio = new RadioButton();
        NoDrinkerRadio = new RadioButton();
        Label Vdrinker = new Label();
        Vdrinker.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        Vdrinker.getAllStyles().setFgColor(0xFF0000);
        drinkerContainer.add(drinkerLabel).add(Drinker).add(DrinkerRadio).add(NonDrinker).add(NoDrinkerRadio);
        group2 = new ButtonGroup(DrinkerRadio, NoDrinkerRadio);
        tchildrennbr = new TextField();
        tchildrennbr.setHint("Children number");
        Label childrenLabel = new Label();
        childrenLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        childrenLabel.getAllStyles().setFgColor(0xFF0000);
        tminage = new TextField();
        tminage.setHint("Prefered min_age");
        Label minageLabel = new Label();
        minageLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        minageLabel.getAllStyles().setFgColor(0xFF0000);
        tmaxage = new TextField();
        tmaxage.setHint("Prefered max_age");
        Label maxageLabel = new Label();
        maxageLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        maxageLabel.getAllStyles().setFgColor(0xFF0000);
        Container cntBirth = new Container(BoxLayout.x());
        birthdate = new Picker();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(this.birthdate.getDate());
        Label birthText = new Label("Birth Date :");
        cntBirth.add(birthText).add(birthdate);
        birthText.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        birthText.getAllStyles().setFgColor(0x09345E);

        Container cntBody = new Container(BoxLayout.x());
        body = new Picker();
        String[] charactersBody = {"Rather_Not_Say", "Thin", "Overweight", "Average", "Fit", "Herculean", "Curvy"};
        Label Bodytext = new Label("Body Type :");
        Bodytext.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Bodytext.getAllStyles().setFgColor(0x09345E);

        body.setStrings(charactersBody);
        Label Vbody = new Label();
        Vbody.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        Vbody.getAllStyles().setFgColor(0xFF0000);
        cntBody.add(Bodytext).add(body);
        TextField Height = new TextField();
        Height.setHint("Height");
        Label HeightLabel = new Label();
        HeightLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        HeightLabel.getAllStyles().setFgColor(0xFF0000);

        Container cntReligion = new Container(BoxLayout.x());
        religion = new Picker();
        String[] charactersReligion = {"Islam", "Judaism", "Christianity", "Atheism", "Agnosticism"};
        Label Religiontext = new Label("Religion :");
        cntReligion.add(Religiontext).add(religion);
        Religiontext.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Religiontext.getAllStyles().setFgColor(0x09345E);
        religion.setStrings(charactersReligion);
        Label Vreligion = new Label();
        Vreligion.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        Vreligion.getAllStyles().setFgColor(0xFF0000);

        Container cntImportance = new Container(BoxLayout.x());
        importance = new Picker();
        String[] charactersImportance = {"Indifferent", "Somewhat important", "Important"};
        Label Importancetext = new Label("Religion Importance :");
        cntImportance.add(Importancetext).add(importance);
        Importancetext.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Importancetext.getAllStyles().setFgColor(0x09345E);
        importance.setStrings(charactersImportance);
        Label Vimportance = new Label();
        Vimportance.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        Vimportance.getAllStyles().setFgColor(0xFF0000);

        Container cntMarital = new Container(BoxLayout.x());
        maritalstatus = new Picker();
        String[] charactersMaritalStatus = {"Single", "Widow", "Divorced"};
        Label Maritaltext = new Label("Civil Status :");
        cntMarital.add(Maritaltext).add(maritalstatus);
        Maritaltext.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Maritaltext.getAllStyles().setFgColor(0x09345E);
        maritalstatus.setStrings(charactersMaritalStatus);
        Label Vmarital = new Label();
        Vmarital.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        Vmarital.getAllStyles().setFgColor(0xFF0000);

        //Address
        final DefaultListModel<String> options = new DefaultListModel<>();
        List<Address> addressesAuto = new ArrayList<>();
        GooglePlacesApi api = new GooglePlacesApi("AIzaSyCiVB-qwVIdq-xykpRt-H2Xf27SVIYO0iY");
        addressField = new AutoCompleteTextField(options) {
            @Override
            protected boolean filter(String text) {
                if (text.length() == 0) {
                    return false;
                }
                List<Address> addresses = api.autoCompleteAddress(text);
                if (addresses == null || addresses.size() == 0) {
                    return false;
                }

                options.removeAll();
                addressesAuto.clear();
                for (Address address : addresses) {
                    options.addItem(address.getCity() + ", " + address.getCountry());
                    addressesAuto.add(address);
                }
                return true;
            }
        };
        addressField.addDataChangedListener((oldVal, newVal) -> {
            if (!selected) {
                selectedAddress = null;
            } else {
                selected = false;
            }
        });
        addressField.addListListener((e) -> {
            selected = true;
            Address a = addressesAuto.get(options.getSelectedIndex());
            selectedAddress = new com.semicolon.entity.Address();
            selectedAddress.setCity(a.getCity());
            selectedAddress.setCountry(a.getCountry());
            selectedAddress.setLatitude(a.getLocation().getLatitude());
            selectedAddress.setLongitude(a.getLocation().getLongitude());
        });

        btnajout = new Button("Register");
        f.add(tfirstname);
        f.add(labelname);
        f.add(tlastname);
        f.add(labelLast);
        f.add(tpseudo);
        f.add(labelPseudo);
        f.add(email);
        f.add(labelemail);
        f.add(labelemailV);
        f.add(tphone);
        f.add(labelPhone);
        f.add(password);
        f.add(labelPassword);
        f.add(confirmPassword);
        f.add(labelRPassword);
        f.add(genderContainer);
        f.add(Vgender);
        maleRadio.isSelected();
        f.add(cntBirth);
        f.add(cntBody);
        f.add(Vbody);
        f.add(Height);
        f.add(HeightLabel);
        f.add(cntReligion);
        f.add(Vreligion);
        f.add(cntImportance);
        f.add(Vimportance);
        f.add(cntMarital);
        f.add(Vmarital);
        f.add(tminage);
        f.add(minageLabel);
        f.add(tmaxage);
        f.add(maxageLabel);
        f.add(tchildrennbr);
        f.add(childrenLabel);
        f.add(smokerContainer);
        f.add(Vsmoker);
        f.add(drinkerContainer);
        f.add(Vdrinker);
        smokerRadio.isSelected();
        NoDrinkerRadio.isSelected();
        f.add(tabout);
        f.add(labelAbout);
        f.add(new Label("Address")).add(addressField);
        f.add(btnajout);

        Validator v = new Validator();
        v.addConstraint(email, RegexConstraint.validEmail("Unvalid email!"));
        v.addSubmitButtons(btnajout);

        btnajout.addActionListener((l) -> {
            boolean valid = true;
            if (tfirstname.getText().equals("")) {
                labelname.setText("Field is empty !");
                labelname.setVisible(true);
                valid = false;
            } else {
                labelname.setText("");

            }
            if (tlastname.getText().equals("")) {
                labelLast.setText("Field is empty !");
                labelLast.setVisible(true);
                valid = false;
            } else {
                labelLast.setText("");

            }
            if (email.getText().equals("")) {
                labelemail.setText("Field is empty !");
                labelemail.setVisible(true);
                valid = false;
            } else {
                labelemail.setText("");

            }
            if (!email.getText().equals("") && !email.getText().contains("@") && !email.getText().contains(".")) {
                labelemailV.setText("Mail not valid !");
                labelemailV.setVisible(true);
                valid = false;
            } else {
                labelemailV.setText("");

            }

            if (tpseudo.getText().equals("")) {
                labelPseudo.setText("Field is empty !");
                labelPseudo.setVisible(true);
                valid = false;
            } else {
                labelPseudo.setText("");

            }

            if (tphone.getText().equals("")) {
                labelPhone.setText("Field is empty !");
                labelPhone.setVisible(true);
                valid = false;
            } else {
                labelPhone.setText("");

            }
            if (tabout.getText().equals("")) {
                labelAbout.setText("Field is empty !");
                labelAbout.setVisible(true);
                valid = false;
            } else {
                labelAbout.setText("");

            }

            //Address
            if(addressField.getText().isEmpty()){
                Dialog.show("Error", "Address is mandatory!", "Ok", null);
                valid = false;
            }else if(selectedAddress == null){
                Dialog.show("Error", "Please select a valid Address", "Ok", null);
                valid = false;
            }
            
            if (password.getText().equals("")) {
                labelPassword.setText("Field is empty !");
                labelPassword.setVisible(true);
                valid = false;
            } else {
                labelPassword.setText("");
            }

            if (confirmPassword.getText().equals("")) {
                labelRPassword.setText("Field is empty !");
                labelRPassword.setVisible(true);
                valid = false;
            }

            if (!password.getText().equals(confirmPassword.getText())) {
                labelRPassword.setText("Password doesn't match !");
                labelRPassword.setVisible(true);
                valid = false;
            } else if (password.getText().equals(confirmPassword.getText()) && !password.getText().equals("")) {
                labelRPassword.setText("Password match !");
                labelRPassword.getAllStyles().setFgColor(0x388D38);
                labelRPassword.setVisible(true);
            }
            if (tminage.getText().equals("")) {
                minageLabel.setText("Field is empty !");
                minageLabel.setVisible(true);
                valid = false;
            } else {
                minageLabel.setText("");
            }
            if (tmaxage.getText().equals("")) {
                maxageLabel.setText("Field is empty !");
                maxageLabel.setVisible(true);
                valid = false;
            } else {
                maxageLabel.setText("");
            }

            if (tchildrennbr.getText().equals("")) {
                childrenLabel.setText("Field is empty !");
                childrenLabel.setVisible(true);
                valid = false;
            } else {
                childrenLabel.setText("");
            }
            if (Height.getText().equals("")) {
                HeightLabel.setText("Field is empty !");
                HeightLabel.setVisible(true);
                valid = false;
            } else {
                HeightLabel.setText("");
            }
            if (maritalstatus.getSelectedStringIndex() == -1) {
                Vmarital.setText("You should select a choice !");
                Vmarital.setVisible(true);
                valid = false;
            } else {
                Vmarital.setText("");
            }
            if (religion.getSelectedStringIndex() == -1) {
                Vreligion.setText("You should select a choice !");
                Vreligion.setVisible(true);
                valid = false;
            } else {
                Vreligion.setText("");
            }
            if (importance.getSelectedStringIndex() == -1) {
                Vimportance.setText("You should select a choice !");
                Vimportance.setVisible(true);
                valid = false;
            } else {
                Vimportance.setText("");
            }
            if (body.getSelectedStringIndex() == -1) {
                Vbody.setText("You should select a choice !");
                Vbody.setVisible(true);
                valid = false;
            } else {
                Vbody.setText("");
            }
            if (!maleRadio.isSelected() && !femaleRadio.isSelected()) {
                Vgender.setText("You should select a choice !");
                Vgender.setVisible(true);
                valid = false;
            } else {
                Vgender.setText("");
            }
            if (!smokerRadio.isSelected() && !NoSmokerRadio.isSelected()) {
                Vsmoker.setText("You should select a choice !");
                Vsmoker.setVisible(true);
                valid = false;
            } else {
                Vsmoker.setText("");
            }
            if (!DrinkerRadio.isSelected() && !NoDrinkerRadio.isSelected()) {
                Vdrinker.setText("You should select a choice !");
                Vdrinker.setVisible(true);
                valid = false;
            } else {
                Vdrinker.setText("");
            }

            if (!valid) {
                return;
            }

            u1 = new UserR();

            if (body.getSelectedStringIndex() == 0) {
                u1.setBodyType(Enumerations.BodyType.RATHER_NOT_SAY);
            } else if (body.getSelectedStringIndex() == 1) {
                u1.setBodyType(Enumerations.BodyType.THIN);
            } else if (body.getSelectedStringIndex() == 2) {
                u1.setBodyType(Enumerations.BodyType.OVERWEIGHT);
            } else if (body.getSelectedStringIndex() == 3) {
                u1.setBodyType(Enumerations.BodyType.AVERAGE);
            } else if (body.getSelectedStringIndex() == 4) {
                u1.setBodyType(Enumerations.BodyType.FIT);
            } else if (body.getSelectedStringIndex() == 5) {
                u1.setBodyType(Enumerations.BodyType.HERCULEAN);
            } else {
                u1.setBodyType(Enumerations.BodyType.CURVY);
            }

            if (religion.getSelectedStringIndex() == 0) {
                u1.setReligion(Enumerations.Religion.ISLAM);
            } else if (religion.getSelectedStringIndex() == 1) {
                u1.setReligion(Enumerations.Religion.JUDAISM);
            } else if (religion.getSelectedStringIndex() == 2) {
                u1.setReligion(Enumerations.Religion.CHRISTIANITY);
            } else if (religion.getSelectedStringIndex() == 3) {
                u1.setReligion(Enumerations.Religion.ATHEISM);
            } else {
                u1.setReligion(Enumerations.Religion.AGNOSTICISM);
            }

            if (importance.getSelectedStringIndex() == 0) {
                u1.setReligionImportance(Enumerations.Importance.INDIFFERENT);
            } else if (importance.getSelectedStringIndex() == 1) {
                u1.setReligionImportance(Enumerations.Importance.SOMEWHAT_IMPORTANT);
            } else {
                u1.setReligionImportance(Enumerations.Importance.IMPORTANT);
            }

            if (maritalstatus.getSelectedStringIndex() == 0) {
                u1.setMaritalStatus(Enumerations.MaritalStatus.SINGLE);
            } else if (maritalstatus.getSelectedStringIndex() == 1) {
                u1.setMaritalStatus(Enumerations.MaritalStatus.WIDOW);
            } else {
                u1.setMaritalStatus(Enumerations.MaritalStatus.DIVORCED);
            }

            InscriptionService ins = new InscriptionService();
            UserR utilis = new UserR(tpseudo.getText(), tfirstname.getText(), tlastname.getText(), email.getText(), password.getText(), tabout.getText(), Integer.parseInt(tphone.getText()), maleRadio.isSelected(), smokerRadio.isSelected(), DrinkerRadio.isSelected(), date, u1.getBodyType(), Integer.parseInt(tchildrennbr.getText()), Integer.parseInt(tminage.getText()), Integer.parseInt(tmaxage.getText()), u1.getReligion(), u1.getReligionImportance(), u1.getMaritalStatus(), Float.parseFloat(Height.getText()));
            utilis.setAddress(selectedAddress);
            ins.Inscription(utilis);
            Login1 log = new Login1();
            log.getContainer().showBack();
            log.Log(tfirstname.getText(), tlastname.getText());
//            Mail sm = new Mail(u1.getEmail(), " Confirmation d'inscription ", " Bonjour " + u1.getFirstname() + "Felicitations! Vous etes maintenant inscrit à MySoulMate");
//                            try {
//               
//                Properties props = new Properties();
//                props.put("mail.transport.protocol", "smtp");
//                props.put("mail.smtps.host", "smtp.gmail.com");
//                props.put("mail.smtps.auth", "true");
//                Session session = Session.getDefaultInstance(null, null);
//                
//                MimeMessage msg = new MimeMessage(session);
//                
//                msg.setFrom(new InternetAddress("SocialPro <my_email@myDomain.com>"));
//                msg.setRecipients(Message.RecipientType.TO, email.getText());
//                msg.setSubject("Bienvenue sur SocialPro ");
//                msg.setSentDate(new Date(System.currentTimeMillis()));
//                
//                String txt = "Bonjour,\n "+tpseudo.getText()+"\n Cordialement";
//                
//                msg.setText(txt);
//                SMTPTransport st = (SMTPTransport)session.getTransport("smtps");
//                st.connect("smtp.gmail.com","benjaziaachref@gmail.com","achref07");
//                st.sendMessage(msg, msg.getAllRecipients());
//                System.out.println("ServerResponse : " + st.getLastServerResponse());
//                 
//            } catch (MessagingException ex) {
//            }

        });

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
