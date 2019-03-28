package com.cse5236.bowlbuddy.models;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.Objects;

public class Bathroom implements Serializable {
    private Integer id;
    private Integer sNum;
    private Integer plyCount;
    private Integer floor;
    private Integer rmNum;
    private Boolean paperTowels;
    private Boolean handicap;
    private Float cleanRating;
    private Float emptyRating;
    private Float smellRating;
    private String gender;
    @Json(name = "building_id")
    private int buildingID;
    private Building building;

    public Bathroom() {
    }


    public int getsNum() {
        return sNum;
    }

    public void setsNum(int sNum) {
        this.sNum = sNum;
    }

    public int getPlyCount() {
        return plyCount;
    }

    public void setPlyCount(int plyCount) {
        this.plyCount = plyCount;
    }

    public boolean isPaperTowels() {
        return paperTowels;
    }

    public void setPaperTowels(boolean paperTowels) {
        this.paperTowels = paperTowels;
    }

    public boolean isHandicap() {
        if (handicap != null) {
            return handicap;
        } else {
            return false;
        }
    }

    public void setHandicap(boolean handicap) {
        this.handicap = handicap;
    }

    public float getCleanRating() {
        return cleanRating;
    }

    public void setCleanRating(float cleanRating) {
        this.cleanRating = cleanRating;
    }

    public float getEmptyRating() {
        return emptyRating;
    }

    public void setEmptyRating(float emptyRating) {
        this.emptyRating = emptyRating;
    }

    public float getSmellRating() {
        return smellRating;
    }

    public void setSmellRating(float smellRating) {
        this.smellRating = smellRating;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getAverageRating() {
        return (smellRating + emptyRating + cleanRating) / 3f;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getRmNum() {
        return rmNum;
    }

    public void setRmNum(Integer rmNum) {
        this.rmNum = rmNum;
    }

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return Objects.equals(((Bathroom) obj).getId(), this.id);
    }
}
