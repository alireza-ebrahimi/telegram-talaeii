package org.telegram.customization.Model;

import com.google.gson.annotations.SerializedName;

public class ContactHelper {
    @SerializedName("a")
    long accessHash;
    int id;
    @SerializedName("m")
    String mobile;
    @SerializedName("n")
    String name;
    @SerializedName("u")
    String username;

    public long getAccessHash() {
        return this.accessHash;
    }

    public void setAccessHash(long accessHash) {
        this.accessHash = accessHash;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
