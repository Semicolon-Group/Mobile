/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.entity;

import com.semicolon.entity.Enumerations.Importance;
import java.util.Date;
import java.util.HashSet;

/**
 *
 * @author vaider
 */
public class Answer {
     private int id;
    private int questionId;
    private Date date;
    private Importance importance;
    private int memberId;
    private HashSet<Choice> selectedChoices;
    private HashSet<Choice> acceptedChoices;
    private String objet;

    public Answer(int id, int questionId, Date date, Importance importance, int memberId, HashSet<Choice> selectedChoices, HashSet<Choice> acceptedChoices, String objet) {
        this.id = id;
        this.questionId = questionId;
        this.date = date;
        this.importance = importance;
        this.memberId = memberId;
        this.selectedChoices = selectedChoices;
        this.acceptedChoices = acceptedChoices;
        this.objet = objet;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    
    public Answer() {
        selectedChoices = new HashSet<>();
        acceptedChoices = new HashSet<>();
    }
    
    public Answer(int id){
        this.id = id;
        selectedChoices = new HashSet<>();
        acceptedChoices = new HashSet<>();
    }

    public Answer(int questionId, Date date, Importance importance, int memberId) {
	this.questionId = questionId;
	this.date = date;
	this.importance = importance;
	this.memberId = memberId;
        selectedChoices = new HashSet<>();
        acceptedChoices = new HashSet<>();
    }

    public Answer(int id, int questionId, Date date, Importance importance, int memberId) {
	this.id = id;
	this.questionId = questionId;
	this.date = date;
	this.importance = importance;
	this.memberId = memberId;
        selectedChoices = new HashSet<>();
        acceptedChoices = new HashSet<>();
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getQuestionId() {
	return questionId;
    }

    public void setQuestionId(int questionId) {
	this.questionId = questionId;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public Importance getImportance() {
	return importance;
    }

    public void setImportance(Importance importance) {
	this.importance = importance;
    }

    public int getMemberId() {
	return memberId;
    }

    public void setMemberId(int memberId) {
	this.memberId = memberId;
    }

    public HashSet<Choice> getSelectedChoices() {
        return selectedChoices;
    }

    public HashSet<Choice> getAcceptedChoices() {
        return acceptedChoices;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.id;
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
        final Answer other = (Answer) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Answer{" + "id=" + id + ", questionId=" + questionId + ", date=" + date + ", importance=" + importance + ", memberId=" + memberId + ", selectedChoices=" + selectedChoices + ", acceptedChoices=" + acceptedChoices + "}\n";
    }
    
    
}
