package org.telegram.customization.Model.Payment;

import com.google.p098a.p099a.C1662c;
import java.util.ArrayList;

public class User {
    public static final int UserAvailable = 101;
    public static final int UserNotExists = 81;
    public static final int UserNotVerified = 80;
    String address;
    int bankId;
    String cardNo;
    ArrayList<Channel> channels;
    @C1662c(a = "contact")
    long contactNumber;
    String description;
    String firstName;
    String lastName;
    @C1662c(a = "menu")
    ArrayList<Menu> menus;
    String message;
    Long mobileNumber;
    String nationalCode;
    String password;
    String shaba;
    int status;
    String username;

    public String getAddress() {
        return this.address;
    }

    public int getBankId() {
        return this.bankId;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public ArrayList<Channel> getChannels() {
        return this.channels;
    }

    public long getContactNumber() {
        return this.contactNumber;
    }

    public String getDescription() {
        return this.description;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public ArrayList<Menu> getMenus() {
        return this.menus;
    }

    public String getMessage() {
        return this.message;
    }

    public Long getMobileNumber() {
        return this.mobileNumber;
    }

    public String getNationalCode() {
        return this.nationalCode;
    }

    public String getPassword() {
        return this.password;
    }

    public String getShaba() {
        return this.shaba;
    }

    public int getStatus() {
        return this.status;
    }

    public String getUsername() {
        return this.username;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public void setBankId(int i) {
        this.bankId = i;
    }

    public void setCardNo(String str) {
        this.cardNo = str;
    }

    public void setChannels(ArrayList<Channel> arrayList) {
        this.channels = arrayList;
    }

    public void setContactNumber(long j) {
        this.contactNumber = j;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setFirstName(String str) {
        this.firstName = str;
    }

    public void setLastName(String str) {
        this.lastName = str;
    }

    public void setMenus(ArrayList<Menu> arrayList) {
        this.menus = arrayList;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setMobileNumber(Long l) {
        this.mobileNumber = l;
    }

    public void setNationalCode(String str) {
        this.nationalCode = str;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public void setShaba(String str) {
        this.shaba = str;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public void setUsername(String str) {
        this.username = str;
    }
}
