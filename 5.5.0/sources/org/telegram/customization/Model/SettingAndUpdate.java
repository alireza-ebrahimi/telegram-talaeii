package org.telegram.customization.Model;

import java.util.ArrayList;

public class SettingAndUpdate {
    ArrayList<Setting> settings;
    Update update;

    public ArrayList<Setting> getSetting() {
        return this.settings;
    }

    public Update getUpdate() {
        return this.update;
    }

    public void setSetting(ArrayList<Setting> arrayList) {
        this.settings = arrayList;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }
}
