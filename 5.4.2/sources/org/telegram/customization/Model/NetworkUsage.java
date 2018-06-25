package org.telegram.customization.Model;

import java.util.ArrayList;
import utils.C3792d;

public class NetworkUsage {
    String carrierName;
    ArrayList<MediaUsageStatistics> mediaUsageStatistics;
    long time;
    int type;

    public String getCarrierName() {
        return C3792d.m14090d();
    }

    public ArrayList<MediaUsageStatistics> getMediaUsageStatistics() {
        return this.mediaUsageStatistics;
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public int getType() {
        return this.type;
    }

    public void setCarrierName(String str) {
        this.carrierName = str;
    }

    public void setMediaUsageStatistics(ArrayList<MediaUsageStatistics> arrayList) {
        this.mediaUsageStatistics = arrayList;
    }

    public void setTime(long j) {
        this.time = j;
    }

    public void setType(int i) {
        this.type = i;
    }
}
