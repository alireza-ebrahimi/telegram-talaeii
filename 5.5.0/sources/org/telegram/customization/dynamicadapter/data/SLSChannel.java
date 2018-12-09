package org.telegram.customization.dynamicadapter.data;

import com.google.p098a.p099a.C1662c;

public class SLSChannel {
    @C1662c(a = "username")
    String channelId;
    @C1662c(a = "channel_id")
    long id;
    boolean isAdmin;
    boolean isInChat;
    boolean isMute;
    @C1662c(a = "title")
    String name;
    int userId;

    public String getChannelId() {
        return this.channelId;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getUserId() {
        return this.userId;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public boolean isInChat() {
        return this.isInChat;
    }

    public boolean isMute() {
        return this.isMute;
    }

    public void setAdmin(boolean z) {
        this.isAdmin = z;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public void setId(long j) {
        this.id = j;
    }

    public void setInChat(boolean z) {
        this.isInChat = z;
    }

    public void setMute(boolean z) {
        this.isMute = z;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setUserId(int i) {
        this.userId = i;
    }
}
