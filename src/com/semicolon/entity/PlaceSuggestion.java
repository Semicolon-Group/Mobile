package com.semicolon.entity;

public class PlaceSuggestion {
    private String placeId;
    private String name;
    private String photoRef;
    private double rating;
    private String distance;
    private String duration;
    private boolean open;
    private double lat;
    private double lng;
    private String address;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public String toString() {
        return "PlaceSuggestion{" + "placeId=" + placeId + ", name=" + name + ", photoRef=" + photoRef + ", rating=" + rating + ", distance=" + distance + ", duration=" + duration + ", open=" + open + ", lat=" + lat + ", lng=" + lng + ", address=" + address + '}';
    }
}
