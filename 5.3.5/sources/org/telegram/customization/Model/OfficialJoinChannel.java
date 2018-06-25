package org.telegram.customization.Model;

public class OfficialJoinChannel {
    String channelUserName;
    int isForce;

    public String getChannelUserName() {
        return this.channelUserName;
    }

    public void setChannelUserName(String channelUserName) {
        this.channelUserName = channelUserName;
    }

    public int isForce() {
        return this.isForce;
    }

    public void setForce(int force) {
        this.isForce = force;
    }
}
