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
public class Enumerations {
     public enum Role{
	ADMIN,
	MEMBER
    }
    public enum BodyType{
	RATHER_NOT_SAY,
	THIN,
	OVERWEIGHT,
	AVERAGE,
	FIT,
	HERCULEAN,
	CURVY
    }
    public enum MaritalStatus{
	SINGLE,
	WIDOW,
	DIVORCED
    }
    public enum Religion{
	ISLAM,
	JUDAISM,
	CHRISTIANITY,
	ATHEISM,
	AGNOSTICISM
    }
    public enum RelationType{
	SERIOUS,
	FRIENDSHIP,
	CASUAL
    }
    public enum Importance{
	INDIFFERENT,
	SOMEWHAT_IMPORTANT,
	IMPORTANT
    }
    public enum ReactionType{
	LIKE,
	LAUGH,
        SMILE,
        LOVE,
        SCOWL
    }
    public enum SignalReason{
	INAPPROPRIATE_CONTENT,
	RACISM,
        VIOLENCE,
        HARRASSMENT,
        FALSE_PROFILE,
        OTHER
    }
    public enum Proximity{
	CLOSE,
	DISTANT,
	ANYWHERE
    }
    public enum LockedType{
	ENABLED,
	DISABLED, //l'utilisateur a désactivé son compte par lui meme
	BANNED
    }
    public enum NotificationType{
	MESSAGE,
	LIKE,
	REACTION,
	SIGNAL,
	FEEDBACK
    }
    
    public enum PostType{
        ANSWER,
        PICTURE,
        STATUS,
        UPDATE
    }
    public enum Topic{
	RELIGION,
	RELATIONSHIP,
	CULTURE,
	SPORT,
	GENERAL,
        MANDATORY,
    }
    public enum PhotoType{
        REGULAR,
        PROFILE,
        COVER
    }
    public enum LastLogin{
        ONE_DAY,
        WEEK,
        MONTH,
        YEAR
    }
    
}
