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

    public ContactChangeLog(int i, long j, int i2, String str, long j2) {
        this.id = i;
        this.chatId = j;
        this.type = i2;
        this.previousName = str;
        this.date = j2;
    }

    public ContactChangeLog(long j, int i, String str, long j2) {
        this.id = this.id;
        this.chatId = j;
        this.type = i;
        this.previousName = str;
        this.date = j2;
    }

    public static String getLogStringByType(int i) {
        switch (i) {
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

    public long getChatId() {
        return this.chatId;
    }

    public long getDate() {
        return this.date;
    }

    public int getId() {
        return this.id;
    }

    public String getPreviousName() {
        return this.previousName;
    }

    public int getType() {
        return this.type;
    }

    public void setChatId(long j) {
        this.chatId = j;
    }

    public void setDate(long j) {
        this.date = j;
    }

    public void setId(int i) {
        this.id = i;
    }

    public void setPreviousName(String str) {
        this.previousName = str;
    }

    public void setType(int i) {
        this.type = i;
    }
}
