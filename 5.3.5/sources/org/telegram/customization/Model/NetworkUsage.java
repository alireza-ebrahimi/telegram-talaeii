package org.telegram.customization.Model;

import java.util.ArrayList;
import utils.Utilities;

public class NetworkUsage {
    String carrierName;
    ArrayList<MediaUsageStatistics> mediaUsageStatistics;
    long time;
    int type;

    public String getCarrierName() {
        return Utilities.getCarrierName();
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<MediaUsageStatistics> getMediaUsageStatistics() {
        return this.mediaUsageStatistics;
    }

    public void setMediaUsageStatistics(ArrayList<MediaUsageStatistics> mediaUsageStatistics) {
        this.mediaUsageStatistics = mediaUsageStatistics;
    }
}
