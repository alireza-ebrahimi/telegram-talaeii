package org.telegram.customization.Model;

public class JoinChannelModel {
    long expireTime;
    String username;

    public long getExpireTime() {
        return this.expireTime;
    }

    public String getUsername() {
        return this.username;
    }

    public void setExpireTime(long j) {
        this.expireTime = j;
    }

    public void setUsername(String str) {
        this.username = str;
    }
}
