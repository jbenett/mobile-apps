package com.cse5236.bowlbuddy.models;

import com.squareup.moshi.Json;

public class Review {
    private String details;
    @Json(name = "user_id") private int userID;
    private User author;
    @Json(name= "bathroom_id") private int bathroomID;
    private Bathroom bathroom;

    public Review() {
    }

    public Review(String details, int userID, int bathroomID) {
        this.details = details;
        this.userID = userID;
        this.bathroomID = bathroomID;
    }

    public String getDetails() {
        return details;
    }

    public int getUserID() {
        return userID;
    }

    public int getBathroomID() {
        return bathroomID;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Bathroom getBathroom() {
        return bathroom;
    }

    public void setBathroom(Bathroom bathroom) {
        this.bathroom = bathroom;
    }
}
