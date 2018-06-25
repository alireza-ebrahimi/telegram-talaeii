package org.telegram.customization.Model;

import org.telegram.messenger.UserConfig;

public class TMData {
    String message;
    long messageId;
    String phoneNumber = UserConfig.getCurrentUser().phone;
    long userId = ((long) UserConfig.getClientUserId());

    public TMData(String msg, long messageId) {
        this.message = msg;
        this.messageId = messageId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getMessageId() {
        return this.messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}
