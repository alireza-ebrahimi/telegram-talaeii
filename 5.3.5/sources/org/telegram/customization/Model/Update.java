package org.telegram.customization.Model;

import java.util.ArrayList;

public class Update {
    ArrayList<String> changeList;
    String downloadLink;
    boolean forceUpdate;
    boolean fromMarket;
    int lastVersion;

    public boolean isFromMarket() {
        return this.fromMarket;
    }

    public void setFromMarket(boolean fromMarket) {
        this.fromMarket = fromMarket;
    }

    public int getLastVersion() {
        return this.lastVersion;
    }

    public void setLastVersion(int lastVersion) {
        this.lastVersion = lastVersion;
    }

    public String getDownloadLink() {
        return this.downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public boolean isForceUpdate() {
        return this.forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public ArrayList<String> getChangeList() {
        return this.changeList;
    }

    public void setChangeList(ArrayList<String> changeList) {
        this.changeList = changeList;
    }
}
