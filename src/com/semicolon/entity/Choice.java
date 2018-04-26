package com.semicolon.entity;

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

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Choice other = (Choice) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.questionId != other.questionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Choice{" + "id=" + id + ", questionId=" + questionId + ", choice=" + choice + '}';
    }
    
}
