package org.telegram.customization.Model;

import com.google.p098a.p099a.C1662c;

public class ContactHelper {
    @C1662c(a = "a")
    long accessHash;
    int id;
    @C1662c(a = "m")
    String mobile;
    @C1662c(a = "n")
    String name;
    @C1662c(a = "u")
    String username;

    public long getAccessHash() {
        return this.accessHash;
    }

    public int getId() {
        return this.id;
    }

    public String getMobile() {
        return this.mobile;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setAccessHash(long j) {
        this.accessHash = j;
    }

    public void setId(int i) {
        this.id = i;
    }

    public void setMobile(String str) {
        this.mobile = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setUsername(String str) {
        this.username = str;
    }
}
