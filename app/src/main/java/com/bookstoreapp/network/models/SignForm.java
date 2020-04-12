package com.bookstoreapp.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SignForm implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("password")
    @Expose
    private String password;

    public SignForm(String name, String phone, String email, String address, String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.password = password;
    }

    public SignForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
