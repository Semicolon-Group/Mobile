package com.semicolon.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.spinner.Picker;
import com.pofper.maps.api.GooglePlacesApi;
import com.pofper.maps.entity.Address;
import com.semicolon.entity.Enumerations;
import com.semicolon.entity.Member;
import com.semicolon.mysoulmate.MyApplication;
import com.semicolon.service.MemberService;
import java.util.ArrayList;
import java.util.List;

public class EditFormView {
    private Form parentForm;
    private Form form;
    private Member member;
    
    private Picker birthdateField;
    private TextField usernameField;
    private TextField firstnameField;
    private TextField lastnameField;
    private RadioButton maleField;
    private RadioButton femaleField;
    private TextField heightField;
    private ComboBox<Enumerations.BodyType> bodyField;
    private TextField childrenField;
    private ComboBox<Enumerations.Religion> religionField;
    private ComboBox<Enumerations.Importance> importanceField;
    private CheckBox smokerField;
    private CheckBox drinkerField;
    private TextField minAgeField;
    private TextField maxAgeField;
    private TextField phoneField;
    private ComboBox<Enumerations.MaritalStatus> maritalField;
    private TextField emailField;
    private AutoCompleteTextField addressField;
    private com.semicolon.entity.Address selectedAddress;
    private TextField aboutField;
    
    
    public EditFormView(Form parentForm, Member member){
        Dialog i = new InfiniteProgress().showInifiniteBlocking();
        this.member = member;
        this.parentForm = parentForm;
        form = new Form("Edit", new BorderLayout());
        
        selectedAddress = member.getAddress();
        birthdateField = new Picker();
        birthdateField.setDate(member.getBirthDate());
        usernameField = new TextField(member.getPseudo(), "Username", 60, TextField.ANY);
        firstnameField = new TextField(member.getFirstname(), "Firstname", 60, TextField.ANY);
        lastnameField = new TextField(member.getLastname(), "Firstname", 60, TextField.ANY);
        maleField = new RadioButton("Male");
        femaleField = new RadioButton("Female");
        new ButtonGroup(maleField, femaleField);
        if(member.isGender()) maleField.setSelected(true); else femaleField.setSelected(true);
        heightField = new TextField(member.getHeight()+"", "Height", 3, TextField.DECIMAL);
        bodyField = new ComboBox<>(Enumerations.BodyType.values());
        bodyField.setSelectedItem(member.getBodyType());
        childrenField = new TextField(member.getChildrenNumber()+"", "Children Number", 2, TextField.NUMERIC);
        religionField = new ComboBox<>(Enumerations.Religion.values());
        religionField.setSelectedItem(member.getReligion());
        importanceField = new ComboBox<>(Enumerations.Importance.values());
        importanceField.setSelectedItem(member.getReligionImportance());
        smokerField = new CheckBox("Smoker");
        if(member.isSmoker()) smokerField.setSelected(true);
        drinkerField = new CheckBox("Drinker");
        if(member.isDrinker()) drinkerField.setSelected(true);
        minAgeField = new TextField(member.getMinAge()+"", "Min. Age", 2, TextField.NUMERIC);
        maxAgeField = new TextField(member.getMaxAge()+"", "Max. Age", 2, TextField.NUMERIC);
        phoneField = new TextField(member.getPhone()+"", "Photo Number", 8, TextField.PHONENUMBER);
        maritalField = new ComboBox<>(Enumerations.MaritalStatus.values());
        maritalField.setSelectedItem(member.getMaritalStatus());
        emailField = new TextField(member.getEmail(), "Email", 60, TextField.EMAILADDR);
        aboutField = new TextField(member.getAbout(), "About", 500, TextArea.ANY);
        aboutField.setPreferredH(200);
        
        final DefaultListModel<String> options = new DefaultListModel<>();
        List<Address> addressesAuto = new ArrayList<>();
        GooglePlacesApi api = new GooglePlacesApi("AIzaSyCiVB-qwVIdq-xykpRt-H2Xf27SVIYO0iY");
        addressField = new AutoCompleteTextField(options) {
            @Override
            protected boolean filter(String text) {
                if(text.length() == 0) {
                    return false;
                }
                List<Address> addresses = api.autoCompleteAddress(text);
                if(addresses == null || addresses.size() == 0) {
                    return false;
                }

                options.removeAll();
                addressesAuto.clear();
                for(Address address : addresses) {
                    options.addItem(address.getCity()+", "+address.getCountry());
                    addressesAuto.add(address);
                }
                return true;
            }
        };
        addressField.setText(member.getAddress().getCity()+", "+member.getAddress().getCountry());
        addressField.addListListener((e) -> {
            Address a = addressesAuto.get(options.getSelectedIndex());
            selectedAddress.setCity(a.getCity());
            selectedAddress.setCountry(a.getCountry());
            selectedAddress.setLatitude(a.getLocation().getLatitude());
            selectedAddress.setLongitude(a.getLocation().getLongitude());
        });
        
        buildContainer();
        
        form.getToolbar().addCommandToLeftBar("Back", MyApplication.theme.getImage("back-command.png"), (e) -> {
            parentForm.showBack();
        });
        i.dispose();
    }
    
    private void buildContainer(){
        Container c = new Container(BoxLayout.y());
        c.setScrollableY(true);
        c.getAllStyles().setMarginBottom(20);
        
        c.add(new Label("Email: ")).add(emailField);
        c.add(new Label("Username: ")).add(usernameField);
        c.add(new Label("Firstname: ")).add(firstnameField);
        c.add(new Label("Lastname: ")).add(lastnameField);
        c.add(new Label("Birthdate: ")).add(birthdateField);
        c.add(new Label("Phono Number: ")).add(phoneField);
        Container genderContainer = new Container(BoxLayout.x());
        c.add(new Label("Gender: "));
        genderContainer.add(maleField).add(femaleField);
        c.add(genderContainer);
        c.add(new Label("Height: ")).add(heightField);
        c.add(new Label("Body Type: ")).add(bodyField);
        c.add(new Label("Children Number: ")).add(childrenField);
        c.add(new Label("Religion: ")).add(religionField);
        c.add(new Label("Religion Importance: ")).add(importanceField);
        c.add(smokerField);
        c.add(drinkerField);
        c.add(new Label("Min. Age: ")).add(minAgeField);
        c.add(new Label("Max. Age: ")).add(maxAgeField);
        c.add(new Label("Marital Status: ")).add(maritalField);
        c.add(new Label("Address: ")).add(addressField);
        c.add(new Label("About: ")).add(aboutField);
        
        Button submitButton = new Button("Confirm");
        submitButton.getUnselectedStyle().setMarginTop(5);
        submitButton.addActionListener((e) -> {
            Dialog i = new InfiniteProgress().showInifiniteBlocking();
            member.setBirthDate(birthdateField.getDate());
            member.setPseudo(usernameField.getText());
            member.setFirstname(firstnameField.getText());
            member.setLastname(lastnameField.getText());
            member.setGender(maleField.isSelected());
            member.setHeight(Float.parseFloat(heightField.getText()));
            member.setBodyType(bodyField.getSelectedItem());
            member.setChildrenNumber(Integer.parseInt(childrenField.getText()));
            member.setReligion(religionField.getSelectedItem());
            member.setReligionImportance(importanceField.getSelectedItem());
            member.setSmoker(smokerField.isSelected());
            member.setDrinker(drinkerField.isSelected());
            member.setMinAge(Integer.parseInt(minAgeField.getText()));
            member.setMaxAge(Integer.parseInt(maxAgeField.getText()));
            member.setPhone(Integer.parseInt(phoneField.getText()));
            member.setAbout(aboutField.getText());
            member.setMaritalStatus(maritalField.getSelectedItem());
            member.setEmail(emailField.getText());
            member.setAddress(selectedAddress);
            
            MemberService.getInstance().editMemeber(member);
            ProfileView.update();
            i.dispose();
            parentForm.showBack();
        });
        
        c.add(submitButton);
        
        form.removeAll();
        form.add(BorderLayout.CENTER, c);
    }
    
    private boolean chackFormValidity(){
        boolean valid = true;
        
        return valid;
    }
    
    public Form getForm(){
        return form;
    }
}
