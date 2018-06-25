package org.telegram.customization.Model;

public class OfficialJoinChannel {
    String channelUserName;
    int isForce;

    public String getChannelUserName() {
        return this.channelUserName;
    }

    public int isForce() {
        return this.isForce;
    }

    public void setChannelUserName(String str) {
        this.channelUserName = str;
    }

    public void setForce(int i) {
        this.isForce = i;
    }
}
