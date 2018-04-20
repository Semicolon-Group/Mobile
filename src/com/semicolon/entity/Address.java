package com.semicolon.entity;

public class Address {
    private int id;
    private double longitude;
    private double latitude;
    private String country;
    private String city;
    private String placeId;

    public Address() {
    }
    
    public Address(int id){
        this.id = id;
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

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
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
