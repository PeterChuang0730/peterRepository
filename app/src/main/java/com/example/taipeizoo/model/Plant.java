package com.example.taipeizoo.model;

import java.io.Serializable;

public class Plant implements Serializable {
    private String F_Location;
    private String F_Pic01_URL;
    private String F_Pic02_URL;
    private String F_Pic03_URL;
    private String F_AlsoKnown;
    private String F_Name_Ch;
    private String F_Name_En;
    private String F_Brief;
    private String F_Feature;
    private String F_Family;
    private String F_Genus;
    private String F_Update;

    public String getLocation() {
        return F_Location;
    }

    public String getPictureURL() {
        return F_Pic01_URL;
    }

    public String getPictureURL2() {
        return F_Pic02_URL;
    }

    public String getPictureURL3() {
        return F_Pic03_URL;
    }

    public String getAlsoKnown() {
        return F_AlsoKnown;
    }

    public String getName_Ch() {
        return F_Name_Ch;
    }

    public String getName_En() {
        return F_Name_En;
    }

    public String getBrief() {
        return F_Brief;
    }

    public String getFeature() {
        return F_Feature;
    }

    public String getFamily() {
        return F_Family;
    }

    public String getGenus() {
        return F_Genus;
    }

    public String getUpdate() {
        return F_Update;
    }

}
