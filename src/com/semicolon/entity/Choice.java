/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.entity;

/**
 *
 * @author vaider
 */
public class Choice {
    private int id;
    private int questionId;
    private String choice;

    public Choice() {
    }

    public Choice(int id) {
        this.id = id;
    }

    public Choice(int id, int questionId, String choice) {
        this.id = id;
        this.questionId = questionId;
        this.choice = choice;
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

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
    

    
}
