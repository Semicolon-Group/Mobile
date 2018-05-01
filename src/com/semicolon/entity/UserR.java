/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.entity;

import com.semicolon.entity.Enumerations.BodyType;
import com.semicolon.entity.Enumerations.Importance;
import com.semicolon.entity.Enumerations.MaritalStatus;
import com.semicolon.entity.Enumerations.RelationType;
import com.semicolon.entity.Enumerations.Religion;
import java.util.List;

/**
 *
 * @author asus
 */
public class UserR {
    private int id;
    private String pseudo;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String about;
    private int phone;
    private boolean gender;
    private boolean smoker;
    private boolean drinker;
    private String birthDate;
    private BodyType bodyType;
    private int childrenNumber;
    private int minAge;
    private int maxAge;
    private Religion religion;
    private Importance religionImportance;
    private MaritalStatus maritalStatus;
    private List<RelationType> preferedRelations;
    private List<MaritalStatus> preferedStatuses;
    private float height;


    public UserR() {
    }



    public UserR(String pseudo, String firstname, String lastname, String email, String password, String about, int phone, boolean gender, boolean smoker, boolean drinker, String birthDate, BodyType bodyType, int childrenNumber, int minAge, int maxAge, Religion religion, Importance religionImportance, MaritalStatus maritalStatus, float height) {
        this.pseudo = pseudo;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.about = about;
        this.phone = phone;
        this.gender = gender;
        this.smoker = smoker;
        this.drinker = drinker;
        this.birthDate = birthDate;
        this.bodyType = bodyType;
        this.childrenNumber = childrenNumber;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.religion = religion;
        this.religionImportance = religionImportance;
        this.maritalStatus = maritalStatus;
        this.height = height;
    }

    public UserR(String pseudo, String firstname, String lastname, String email, String password, String about, int phone, boolean gender, boolean smoker, boolean drinker, String birthDate, BodyType bodyType, int childrenNumber, int minAge, int maxAge, Religion religion, Importance religionImportance, MaritalStatus maritalStatus, List<RelationType> preferedRelations, List<MaritalStatus> preferedStatuses) {
        this.pseudo = pseudo;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.about = about;
        this.phone = phone;
        this.gender = gender;
        this.smoker = smoker;
        this.drinker = drinker;
        this.birthDate = birthDate;
        this.bodyType = bodyType;
        this.childrenNumber = childrenNumber;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.religion = religion;
        this.religionImportance = religionImportance;
        this.maritalStatus = maritalStatus;
        this.preferedRelations = preferedRelations;
        this.preferedStatuses = preferedStatuses;
    }
    
    
    

    public List<RelationType> getPreferedRelations() {
        return preferedRelations;
    }

    public void setPreferedRelations(List<RelationType> preferedRelations) {
        this.preferedRelations = preferedRelations;
    }

    public List<MaritalStatus> getPreferedStatuses() {
        return preferedStatuses;
    }

    public void setPreferedStatuses(List<MaritalStatus> preferedStatuses) {
        this.preferedStatuses = preferedStatuses;
    }
    
    



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }



    public int getChildrenNumber() {
        return childrenNumber;
    }

    public void setChildrenNumber(int childrenNumber) {
        this.childrenNumber = childrenNumber;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public boolean isSmoker() {
        return smoker;
    }

    public void setSmoker(boolean smoker) {
        this.smoker = smoker;
    }

    public boolean isDrinker() {
        return drinker;
    }

    public void setDrinker(boolean drinker) {
        this.drinker = drinker;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public Religion getReligion() {
        return religion;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public Importance getReligionImportance() {
        return religionImportance;
    }

    public void setReligionImportance(Importance religionImportance) {
        this.religionImportance = religionImportance;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
    

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", pseudo=" + pseudo + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password=" + password + ", about=" + about + ", phone=" + phone + ", gender=" + gender + ", smoker=" + smoker + ", drinker=" + drinker + ", birthDate=" + birthDate + ", bodyType=" + bodyType + ", childrenNumber=" + childrenNumber + ", minAge=" + minAge + ", maxAge=" + maxAge + ", religion=" + religion + ", religionImportance=" + religionImportance + ", maritalStatus=" + maritalStatus + ", preferedRelations=" + preferedRelations + ", preferedStatuses=" + preferedStatuses + '}';
    }
    

    




 


   
    


 
    

 
    
      
    
}
