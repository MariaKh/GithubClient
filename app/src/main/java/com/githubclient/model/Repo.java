package com.githubclient.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 1 on 3/27/2018.
 */

@Entity(indices = {@Index("id"), @Index("owner_login")},
        primaryKeys = {"name", "owner_login"})
public class Repo {

    private int id;
    @NonNull
    private String name;
    @SerializedName("full_name")
    private String fullName;
    private String description;
    private String language;
    @SerializedName("html_url")
    private String url;
    @SerializedName("owner")
    @Embedded(prefix = "owner_")
    @NonNull
    public User owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @NonNull
    public User getOwner() {
        return owner;
    }

    public void setOwner(@NonNull User owner) {
        this.owner = owner;
    }
}
