package org.telegram.customization.Model;

import java.util.ArrayList;

public class Update {
    ArrayList<String> changeList;
    String downloadLink;
    boolean forceUpdate;
    boolean fromMarket;
    int lastVersion;

    public ArrayList<String> getChangeList() {
        return this.changeList;
    }

    public String getDownloadLink() {
        return this.downloadLink;
    }

    public int getLastVersion() {
        return this.lastVersion;
    }

    public boolean isForceUpdate() {
        return this.forceUpdate;
    }

    public boolean isFromMarket() {
        return this.fromMarket;
    }

    public void setChangeList(ArrayList<String> arrayList) {
        this.changeList = arrayList;
    }

    public void setDownloadLink(String str) {
        this.downloadLink = str;
    }

    public void setForceUpdate(boolean z) {
        this.forceUpdate = z;
    }

    public void setFromMarket(boolean z) {
        this.fromMarket = z;
    }

    public void setLastVersion(int i) {
        this.lastVersion = i;
    }
}
