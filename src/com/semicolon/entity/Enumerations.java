/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.entity;

/**
 *
 * @author asus
 */
public class Enumerations {
        public enum SignalReason{
        INAPPROPRIATE_CONTENT ,
	Racism,
        Violence,
        Harrasment,
        FALSE_PROFILE,
        Other
    }
        public enum BodyType{
	Rather_not_say,
	Thin,
	Overweight,
	Average,
	Fit,
	Herculean,
	Curvy;

    }
        public enum Religion{
	ISLAM,
	JUDAISM,
	CHRISTIANITY,
	ATHEISM,
	AGNOSTICISM
    }
        
        public enum Importance{
	INDIFFERENT,
	SOMEWHAT_IMPORTANT,
	IMPORTANT
    }
        public enum MaritalStatus{
	SINGLE,
	WIDOW,
	DIVORCED
    }
        public enum RelationType{
	SERIOUS,
	FRIENDSHIP,
	CASUAL
    }
    
}
