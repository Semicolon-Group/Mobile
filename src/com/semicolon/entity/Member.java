/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.semicolon.entity.Enumerations.BodyType;
import com.semicolon.entity.Enumerations.Importance;
import com.semicolon.entity.Enumerations.MaritalStatus;
import com.semicolon.entity.Enumerations.RelationType;
import com.semicolon.util.TimeDiff;

/**
 *
 * @author Elyes
 */
public class Member extends User{
    private Date birthDate;
    private boolean gender;
    private float height;
    private BodyType bodyType;
    private int childrenNumber;
    private Enumerations.Religion religion;
    private Importance religionImportance;
    private boolean smoker;
    private boolean drinker;
    private int minAge;
    private int maxAge;
    private int phone;
    private Timestamp lastLogin;
    private short locked;
    private Address address;
    private List<RelationType> preferedRelations;
    private List<MaritalStatus> preferedStatuses;
    private String about;
    private MaritalStatus maritalStatus;
    private boolean connected;
    private Timestamp createdAt;

    public Member() {
        preferedRelations = new ArrayList<>();
        preferedStatuses = new ArrayList<>();
    }

    public Member(String pseudo, String email) {
        super(pseudo, email);
    }
  
    public Member(int id){
        super(id);
        preferedRelations = new ArrayList<>();
        preferedStatuses = new ArrayList<>();
    }

    public Member(Date birthDate, boolean gender, float height, BodyType bodyType, int childrenNumber, Enumerations.Religion religion, Importance religionImportance, boolean smoker, boolean drinker, int minAge, int maxAge, int phone, Address address, List<RelationType> preferedRelations, String about, String pseudo, String firstname, String lastname, String email, String password) {
        super(pseudo, firstname, lastname, email, password);
        this.birthDate = birthDate;
        this.gender = gender;
        this.height = height;
        this.bodyType = bodyType;
        this.childrenNumber = childrenNumber;
        this.religion = religion;
        this.religionImportance = religionImportance;
        this.smoker = smoker;
        this.drinker = drinker;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.phone = phone;
        this.address = address;
        this.preferedRelations = preferedRelations;
        this.about = about;
    }

    public Member(Date birthDate, boolean gender, float height, BodyType bodyType, int childrenNumber, Enumerations.Religion religion, Importance religionImportance, boolean smoker, boolean drinker, int minAge, int maxAge, int phone, Address address, List<RelationType> preferedRelations, String pseudo, String firstname, String lastname, String email, String password) {
        super(pseudo, firstname, lastname, email, password);
        this.birthDate = birthDate;
        this.gender = gender;
        this.height = height;
        this.bodyType = bodyType;
        this.childrenNumber = childrenNumber;
        this.religion = religion;
        this.religionImportance = religionImportance;
        this.smoker = smoker;
        this.drinker = drinker;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.phone = phone;
        this.address = address;
        this.preferedRelations = preferedRelations;
    }
    

    public Member(Date birthDate, boolean gender, float height, BodyType bodyType, int childrenNumber, Enumerations.Religion religion, Importance religionImportance, boolean smoker, boolean drinker, int minAge, int maxAge, int phone, Address address, List<RelationType> preferedRelations, List<MaritalStatus> preferedStatuses, String about, String pseudo, String firstname, String lastname, String email, String password) {
        super(pseudo, firstname, lastname, email, password);
        this.birthDate = birthDate;
        this.gender = gender;
        this.height = height;
        this.bodyType = bodyType;
        this.childrenNumber = childrenNumber;
        this.religion = religion;
        this.religionImportance = religionImportance;
        this.smoker = smoker;
        this.drinker = drinker;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.phone = phone;
        this.address = address;
        this.preferedRelations = preferedRelations;
        this.preferedStatuses = preferedStatuses;
        this.about = about;
    }
    
  
    public Member(int id, Date birthDate, boolean gender, float height, BodyType bodyType, int childrenNumber, Enumerations.Religion religion, 
            Importance religionImportance, boolean smoker, boolean drinker, int minAge, int maxAge, 
            int phone, Timestamp lastLogin, short locked, Address address, String pseudo, 
            String firstname, String lastname, String email, String password, String ip, int port, String about, 
            MaritalStatus maritalStatus, boolean connected, Timestamp createdAt) {
	super(id, pseudo, firstname, lastname, email, password, ip, port);
	this.birthDate = birthDate;
	this.gender = gender;
	this.height = height;
	this.bodyType = bodyType;
	this.childrenNumber = childrenNumber;
	this.religion = religion;
	this.religionImportance = religionImportance;
	this.smoker = smoker;
	this.drinker = drinker;
	this.minAge = minAge;
	this.maxAge = maxAge;
	this.phone = phone;
	this.lastLogin = lastLogin;
	this.locked = locked;
	this.address = address;
        this.about = about;
        this.maritalStatus = maritalStatus;
        this.connected = connected;
        this.createdAt = createdAt;
	this.preferedRelations = new ArrayList<RelationType>();
	this.preferedStatuses = new ArrayList<MaritalStatus>();
    }
  
    public Member(Date birthDate, boolean gender, float height, BodyType bodyType, int childrenNumber, Enumerations.Religion religion, 
            Importance religionImportance, boolean smoker, boolean drinker, int minAge, int maxAge, int phone, 
            Timestamp lastLogin, short locked, Address address, String pseudo, String firstname, String lastname, String email, 
            String password, String ip, int port, String about, MaritalStatus maritalStatus, boolean connected,
            Timestamp createdAt) {
	super(pseudo, firstname, lastname, email, password, ip, port);
	this.birthDate = birthDate;
	this.gender = gender;
	this.height = height;
	this.bodyType = bodyType;
	this.childrenNumber = childrenNumber;
	this.religion = religion;
	this.religionImportance = religionImportance;
	this.smoker = smoker;
	this.drinker = drinker;
	this.minAge = minAge;
	this.maxAge = maxAge;
	this.phone = phone;
	this.lastLogin = lastLogin;
	this.locked = locked;
	this.address = address;
        this.about = about;
        this.maritalStatus = maritalStatus;
        this.connected = connected;
        this.createdAt = createdAt;
	this.preferedRelations = new ArrayList<RelationType>();
	this.preferedStatuses = new ArrayList<MaritalStatus>();
    }
      
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getAbout(){
        return about;
    }
    
    public void setAbout(String about){
        this.about = about;
    }

    public Date getBirthDate() {
	return birthDate;
    }

    public void setBirthDate(Date birthDate) {
	this.birthDate = birthDate;
    }

    public boolean isGender() {
	return gender;
    }

    public void setGender(boolean gender) {
	this.gender = gender;
    }

    public float getHeight() {
	return height;
    }

    public void setHeight(float height) {
	this.height = height;
    }

    public BodyType getBodyType() {
	return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
	this.bodyType = bodyType;
    }

    public int getChildrenNumber() {
	return childrenNumber;
    }

    public void setChildrenNumber(int childrenNumber) {
	this.childrenNumber = childrenNumber;
    }

    public Enumerations.Religion getReligion() {
	return religion;
    }

    public void setReligion(Enumerations.Religion religion) {
	this.religion = religion;
    }

    public Importance getReligionImportance() {
	return religionImportance;
    }

    public void setReligionImportance(Importance religionImportance) {
	this.religionImportance = religionImportance;
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

    public int getPhone() {
	return phone;
    }

    public void setPhone(int phone) {
	this.phone = phone;
    }

    public Timestamp getLastLogin() {
	return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
	this.lastLogin = lastLogin;
    }

    public short getLocked() {
        return locked;
    }

    public void setLocked(short locked) {
        this.locked = locked;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
        
    @Override
    public String toString() {
	return super.toString() + "Member{" + "birthDate=" + birthDate + ", gender=" + gender + ", height=" + height + ", bodyType=" + bodyType + ", childrenNumber=" + childrenNumber + ", religion=" + religion + ", religionImportance=" + religionImportance + ", smoker=" + smoker + ", drinker=" + drinker + ", minAge=" + minAge + ", maxAge=" + maxAge + ", phone=" + phone + ", lastLogin=" + lastLogin + ", locked=" + locked + ", address=" + address + ", preferedRelations=" + preferedRelations + ", preferedStatuses=" + preferedStatuses + "}\n";
    }

    public int getAge() {
        return TimeDiff.getInstance(new Timestamp(birthDate.getTime()), new Timestamp(new java.util.Date().getTime())).getYears();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.gender ? 1 : 0);
        hash = 97 * hash + Float.floatToIntBits(this.height);
        hash = 97 * hash + Objects.hashCode(this.bodyType);
        hash = 97 * hash + this.childrenNumber;
        hash = 97 * hash + Objects.hashCode(this.religion);
        hash = 97 * hash + Objects.hashCode(this.createdAt);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Member other = (Member) obj;
        if (this.gender != other.gender) {
            return false;
        }
        if (Float.floatToIntBits(this.height) != Float.floatToIntBits(other.height)) {
            return false;
        }
        if (this.childrenNumber != other.childrenNumber) {
            return false;
        }
        if (this.bodyType != other.bodyType) {
            return false;
        }
        if (!Objects.equals(this.createdAt, other.createdAt)) {
            return false;
        }
        return true;
    }
    
}
