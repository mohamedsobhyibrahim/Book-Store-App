package com.bookstoreapp.network.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Book implements Serializable {

    @SerializedName("_id")
    @Expose
    @PrimaryKey
    @ColumnInfo
    private String id;

    @SerializedName("name")
    @Expose
    @ColumnInfo
    private String name;

    @SerializedName("image")
    @Expose
    @ColumnInfo
    private String image;

    @SerializedName("discount")
    @Expose
    @ColumnInfo
    private int discount;

    @SerializedName("price")
    @Expose
    @ColumnInfo
    private double price;

    @SerializedName("url")
    @Expose
    @ColumnInfo
    private String url;

    @SerializedName("author")
    @Expose
    @ColumnInfo
    private String author;

    @SerializedName("bookDescription")
    @Expose
    @ColumnInfo
    private String bookDescription;

    @SerializedName("aboutAuthor")
    @Expose
    @ColumnInfo
    private String aboutAuthor;

    @SerializedName("rating")
    @Expose
    @ColumnInfo
    private float rating;

    @SerializedName("totalRating")
    @Expose
    @ColumnInfo
    private int totalRating;

    @SerializedName("isActive")
    @Expose
    @ColumnInfo
    private boolean isActive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getAboutAuthor() {
        return aboutAuthor;
    }

    public void setAboutAuthor(String aboutAuthor) {
        this.aboutAuthor = aboutAuthor;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
