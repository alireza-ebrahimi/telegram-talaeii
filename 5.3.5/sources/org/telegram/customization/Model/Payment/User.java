package org.telegram.customization.Model.Payment;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class User {
    public static final int UserAvailable = 101;
    public static final int UserNotExists = 81;
    public static final int UserNotVerified = 80;
    String address;
    int bankId;
    String cardNo;
    ArrayList<Channel> channels;
    @SerializedName("contact")
    long contactNumber;
    String description;
    String firstName;
    String lastName;
    @SerializedName("menu")
    ArrayList<Menu> menus;
    String message;
    Long mobileNumber;
    String nationalCode;
    String password;
    String shaba;
    int status;
    String username;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getContactNumber() {
        return this.contactNumber;
    }

    public void setContactNumber(long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public ArrayList<Menu> getMenus() {
        return this.menus;
    }

    public void setMenus(ArrayList<Menu> menus) {
        this.menus = menus;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationalCode() {
        return this.nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getBankId() {
        return this.bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getShaba() {
        return this.shaba;
    }

    public void setShaba(String shaba) {
        this.shaba = shaba;
    }

    public ArrayList<Channel> getChannels() {
        return this.channels;
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }
}
