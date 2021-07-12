package com.example.project3;

import android.graphics.Bitmap;

import java.io.Serializable;

public class userinfo implements Serializable {
    private String userId;
    private String userPassword;
    private String userName;
    private String userMajor;
    private String image;
    private String myid;


     public userinfo(String id, String s, String name, String slMajor) {
        userId = id;
        userPassword = s;
        userName = name;
        userMajor = slMajor;
        this.myid = myid;
    }


    public String getId(){return userId;}
    public String getPassword(){return userPassword;}
    public String getName(){return userName;}
    public String getUserMajor(){return userMajor;}
    public String getImage(){return image;}
    public String getMyid(){return myid;}
}
