/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.util;

import java.sql.Timestamp;

/**
 *
 * @author Elyes
 */
public class TimeDiff {
    private long seconds;
    private long minutes;
    private long hours;
    private int days;
    private int weeks;
    private int months;
    private int years;
    private String timeDiffString;
    
    private static TimeDiff instance;
    
    private TimeDiff(){
        
    }
    
    public static TimeDiff getInstance(Timestamp A, Timestamp B){
        if(instance == null)
            instance = new TimeDiff();
        
        long diff = Math.abs(A.getTime() - B.getTime());
        
        instance.setSeconds(diff / 1000);
        instance.setMinutes(instance.getSeconds() / 60);
        instance.setHours(instance.getMinutes() / 60);
        instance.setDays((int)(instance.getHours() / 24));
        instance.setWeeks(instance.getDays() / 7);
        instance.setMonths(instance.getDays() / 30);
        instance.setYears(instance.getDays() / 365);
        instance.setTimeDiffString(instance.makeTimeDiffString());
        
        return instance;
    }
    
    private void setTimeDiffString(String s){
        timeDiffString = s;
    }
    
    public String getTimeDiffString(){
        return timeDiffString;
    }
    
    public String makeTimeDiffString(){
        
        if(instance.getYears() == 1)
            return instance.getYears() + " year ago";
        if(instance.getYears() > 1)
            return instance.getYears() + " years ago";
        
        if(instance.getMonths() == 1)
            return instance.getMonths() + " month ago";
        if(instance.getMonths() > 1)
            return instance.getMonths() + " months ago";
        
        if(instance.getWeeks() == 1)
            return instance.getWeeks() + " week ago";
        if(instance.getWeeks() > 1)
            return instance.getWeeks() + " weeks ago";
        
        if(instance.getDays() == 1)
            return instance.getDays() + " day ago";
        if(instance.getDays() > 1)
            return instance.getDays() + " days ago";
        
        if(instance.getHours() == 1)
            return instance.getHours() + " hour ago";
        if(instance.getHours() > 1)
            return instance.getHours() + " hours ago";
        
        if(instance.getMinutes() == 1)
            return instance.getMinutes() + " minute ago";
        if(instance.getMinutes() > 1)
            return instance.getMinutes() + " minutes ago";
        
        if(instance.getSeconds() == 1)
            return instance.getSeconds() + " second ago";
        return instance.getSeconds() + " seconds ago";
    }

    public long getSeconds() {
        return seconds;
    }

    private void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public long getMinutes() {
        return minutes;
    }

    private void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public long getHours() {
        return hours;
    }

    private void setHours(long hours) {
        this.hours = hours;
    }

    public int getDays() {
        return days;
    }

    private void setDays(int days) {
        this.days = days;
    }

    public int getWeeks() {
        return weeks;
    }

    private void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    public int getMonths() {
        return months;
    }

    private void setMonths(int months) {
        this.months = months;
    }

    public int getYears() {
        return years;
    }

    private void setYears(int years) {
        this.years = years;
    }
    
    
}
