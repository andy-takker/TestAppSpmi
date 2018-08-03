package com.hikki.sergey_natalenko.testapp.classes;

import java.util.UUID;

public class User {
    private String mName;
    private String mEmail;
    private String mPassword;
    private UUID mId;


    public User(String name, String email, UUID id, String password){
        mName = name;
        mEmail = email;
        mPassword = password;
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public UUID getId() {
        return mId;
    }

    public String getPassword() {
        return mPassword;
    }
}
