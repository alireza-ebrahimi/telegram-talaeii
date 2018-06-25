package org.telegram.customization.Model;

import java.util.ArrayList;

public class SettingAndUpdate {
    ArrayList<Setting> settings;
    Update update;

    public ArrayList<Setting> getSetting() {
        return this.settings;
    }

    public void setSetting(ArrayList<Setting> settings) {
        this.settings = settings;
    }

    public Update getUpdate() {
        return this.update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }
}
