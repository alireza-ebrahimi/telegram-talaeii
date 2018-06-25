package org.telegram.customization.Model;

import org.telegram.messenger.UserConfig;

public class TMData {
    String message;
    long messageId;
    String phoneNumber = UserConfig.getCurrentUser().phone;
    long userId = ((long) UserConfig.getClientUserId());

    public TMData(String str, long j) {
        this.message = str;
        this.messageId = j;
    }

    public String getMessage() {
        return this.message;
    }

    public long getMessageId() {
        return this.messageId;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setMessageId(long j) {
        this.messageId = j;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    public void setUserId(long j) {
        this.userId = j;
    }
}
