/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.entity;

import java.util.HashSet;

/**
 *
 * @author vaider
 */
public class Question {
    private int id;
    private String question;
    private String topic;
    private HashSet<Choice> choices;

    public Question() {
        choices = new HashSet<>();
    }
    
    public Question(int id){
        this.id = id;
        choices = new HashSet<>();
    }

    public Question(int id, String question, String topic) {
	this.id = id;
	this.question = question;
	this.topic = topic;
	choices = new HashSet<>();
    }

    public Question(String question, String topic) {
	this.question = question;
	this.topic = topic;
	choices = new HashSet<>();
    }

    

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getQuestion() {
	return question;
    }

    public void setQuestion(String question) {
	this.question = question;
    }

    public HashSet<Choice> getChoices() {
	return choices;
    }

    public void setChoices(HashSet<Choice> choices) {
	this.choices = choices;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Question{" + "id=" + id + ", question=" + question + ", topic=" + topic + ", choices=" + choices + '}';
    }

    
    

    
    
}

