package org.telegram.customization.Model;

import org.ir.talaeii.R;
import org.telegram.messenger.LocaleController;

public class ContactChangeLog {
    public static final int CHANGE_TYPE_ALL = 0;
    public static final int CHANGE_TYPE_AVATAR = 1;
    public static final int CHANGE_TYPE_NAME = 2;
    public static final int CHANGE_TYPE_PHONE_NUMBER = 3;
    public static final int CHANGE_TYPE_USER_NAME = 4;
    long chatId;
    long date;
    int id;
    String previousName;
    int type;

    public long getChatId() {
        return this.chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getPreviousName() {
        return this.previousName;
    }

    public void setPreviousName(String previousName) {
        this.previousName = previousName;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContactChangeLog(int id, long chatId, int type, String previousName, long date) {
        this.id = id;
        this.chatId = chatId;
        this.type = type;
        this.previousName = previousName;
        this.date = date;
    }

    public ContactChangeLog(long chatId, int type, String previousName, long date) {
        this.id = this.id;
        this.chatId = chatId;
        this.type = type;
        this.previousName = previousName;
        this.date = date;
    }

    public static String getLogStringByType(int type) {
        switch (type) {
            case 1:
                return LocaleController.getString("ChangeProfilePic", R.string.ChangeProfilePic);
            case 2:
                return LocaleController.getString("ChangeProfileName", R.string.ChangeProfileName);
            case 3:
                return LocaleController.getString("ChangeProfileUsername", R.string.ChangeProfileUsername);
            default:
                return null;
        }
    }
}
