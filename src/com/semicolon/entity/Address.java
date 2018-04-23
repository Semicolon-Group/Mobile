/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.entity;

/**
 *
 * @author Elyes
 */
public class Address {
    private int userId;
    private double longitude;
    private double latitude;
    private String country;
    private String city;
    private String placeId;

    public Address() {
    }
    
    public Address(int userId){
        this.userId = userId;
    }

    public Address(int userId, double longitude, double latitude, String country, String city) {
	this.userId = userId;
	this.longitude = longitude;
	this.latitude = latitude;
	this.country = country;
	this.city = city;
    }

    public Address(double longitude, double latitude, String country, String city) {
	this.longitude = longitude;
	this.latitude = latitude;
	this.country = country;
	this.city = city;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public int getUserId() {
	return userId;
    }

    public void setUserId(int userId) {
	this.userId = userId;
    }

    public double getLongitude() {
	return longitude;
    }

    public void setLongitude(double longitude) {
	this.longitude = longitude;
    }

    public double getLatitude() {
	return latitude;
    }

    public void setLatitude(double latitude) {
	this.latitude = latitude;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }
    
    public String toString(){
        return city+", "+country;
    }
    
}
