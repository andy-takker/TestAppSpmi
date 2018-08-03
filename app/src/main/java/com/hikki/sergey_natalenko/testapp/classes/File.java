package com.hikki.sergey_natalenko.testapp.classes;

import java.util.UUID;

public class File {
    private UUID mAddressId;
    private String mName;
    private String mUrl;

    public File(UUID addressId, String name, String url) {
        mAddressId = addressId;
        mName = name;
        mUrl = url;
    }

    public void setAddressId(UUID addressId) {
        mAddressId = addressId;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public UUID getAddressId() {
        return mAddressId;
    }

    public String getName() {
        return mName;
    }

    public String getUrl() {
        return mUrl;
    }
}
