package com.example.taipeizoo.model;

import java.io.Serializable;

public class Area implements Serializable {
    private String E_Pic_URL;
    //private String E_Geo;
    private String E_Info;
    //private String E_no;
    private String E_Category;
    private String E_Name;
    private String E_Memo;
    //private int _id;
    private String E_URL;

    public String getPictureURL() {
        return E_Pic_URL;
    }

//    public String getGeo() {
//        return E_Geo;
//    }

    public String getInfo() {
        return E_Info;
    }

//    public String getNo() {
//        return E_no;
//    }

    public String getCategory() {
        return E_Category;
    }

    public String getName() {
        return E_Name;
    }

    public String getMemo() {
        return E_Memo;
    }

//    public int getID() {
//        return _id;
//    }

    public String getURL() {
        return E_URL;
    }

}
