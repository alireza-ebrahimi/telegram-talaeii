package org.telegram.messenger;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.android.gms.common.stats.LoggingConstants;
import com.google.p098a.C1768f;
import java.lang.reflect.Array;
import java.util.ArrayList;
import org.telegram.customization.Model.MediaUsageStatistics;
import org.telegram.customization.Model.NetworkUsage;

public class StatsController {
    private static volatile StatsController Instance = null;
    private static final int TYPES_COUNT = 7;
    public static final int TYPE_AUDIOS = 3;
    public static final int TYPE_CALLS = 0;
    public static final int TYPE_FILES = 5;
    public static final int TYPE_MESSAGES = 1;
    public static final int TYPE_MOBILE = 0;
    public static final int TYPE_PHOTOS = 4;
    public static final int TYPE_ROAMING = 2;
    public static final int TYPE_TOTAL = 6;
    public static final int TYPE_VIDEOS = 2;
    public static final int TYPE_WIFI = 1;
    private static final ThreadLocal<Long> lastStatsSaveTime = new C34101();
    private int[] callsTotalTime = new int[3];
    private Editor editor;
    private long[][] receivedBytes = ((long[][]) Array.newInstance(Long.TYPE, new int[]{3, 7}));
    private int[][] receivedItems = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{3, 7}));
    private long[] resetStatsDate = new long[3];
    private long[][] sentBytes = ((long[][]) Array.newInstance(Long.TYPE, new int[]{3, 7}));
    private int[][] sentItems = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{3, 7}));
    private DispatchQueue statsSaveQueue = new DispatchQueue("statsSaveQueue");

    /* renamed from: org.telegram.messenger.StatsController$1 */
    static class C34101 extends ThreadLocal<Long> {
        C34101() {
        }

        protected Long initialValue() {
            return Long.valueOf(System.currentTimeMillis() - 1000);
        }
    }

    /* renamed from: org.telegram.messenger.StatsController$2 */
    class C34112 implements Runnable {
        C34112() {
        }

        public void run() {
            for (int i = 0; i < 3; i++) {
                for (int i2 = 0; i2 < 7; i2++) {
                    StatsController.this.editor.putInt("receivedItems" + i + "_" + i2, StatsController.this.receivedItems[i][i2]);
                    StatsController.this.editor.putInt("sentItems" + i + "_" + i2, StatsController.this.sentItems[i][i2]);
                    StatsController.this.editor.putLong("receivedBytes" + i + "_" + i2, StatsController.this.receivedBytes[i][i2]);
                    StatsController.this.editor.putLong("sentBytes" + i + "_" + i2, StatsController.this.sentBytes[i][i2]);
                }
                StatsController.this.editor.putInt("callsTotalTime" + i, StatsController.this.callsTotalTime[i]);
                StatsController.this.editor.putLong("resetStatsDate" + i, StatsController.this.resetStatsDate[i]);
            }
            try {
                StatsController.this.editor.apply();
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    private StatsController() {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences(LoggingConstants.LOG_FILE_PREFIX, 0);
        this.editor = sharedPreferences.edit();
        int i = 0;
        for (int i2 = 0; i2 < 3; i2++) {
            this.callsTotalTime[i2] = sharedPreferences.getInt("callsTotalTime" + i2, 0);
            this.resetStatsDate[i2] = sharedPreferences.getLong("resetStatsDate" + i2, 0);
            for (int i3 = 0; i3 < 7; i3++) {
                this.sentBytes[i2][i3] = sharedPreferences.getLong("sentBytes" + i2 + "_" + i3, 0);
                this.receivedBytes[i2][i3] = sharedPreferences.getLong("receivedBytes" + i2 + "_" + i3, 0);
                this.sentItems[i2][i3] = sharedPreferences.getInt("sentItems" + i2 + "_" + i3, 0);
                this.receivedItems[i2][i3] = sharedPreferences.getInt("receivedItems" + i2 + "_" + i3, 0);
            }
            if (this.resetStatsDate[i2] == 0) {
                i = 1;
                this.resetStatsDate[i2] = System.currentTimeMillis();
            }
        }
        if (i != 0) {
            saveStats();
        }
    }

    public static StatsController getInstance() {
        StatsController statsController = Instance;
        if (statsController == null) {
            synchronized (StatsController.class) {
                statsController = Instance;
                if (statsController == null) {
                    statsController = new StatsController();
                    Instance = statsController;
                }
            }
        }
        return statsController;
    }

    private void saveStats() {
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(currentTimeMillis - ((Long) lastStatsSaveTime.get()).longValue()) >= 1000) {
            lastStatsSaveTime.set(Long.valueOf(currentTimeMillis));
            this.statsSaveQueue.postRunnable(new C34112());
        }
    }

    public int getCallsTotalTime(int i) {
        return this.callsTotalTime[i];
    }

    public String getNetworkUsageStatistics() {
        Object arrayList = new ArrayList();
        for (int i = 0; i < 3; i++) {
            ArrayList arrayList2 = new ArrayList();
            NetworkUsage networkUsage = new NetworkUsage();
            networkUsage.setType(i);
            networkUsage.setCarrierName(networkUsage.getCarrierName());
            networkUsage.setTime(networkUsage.getTime());
            for (int i2 = 0; i2 < 7; i2++) {
                MediaUsageStatistics mediaUsageStatistics = new MediaUsageStatistics();
                mediaUsageStatistics.setSent(getSentItemsCount(i, i2));
                mediaUsageStatistics.setReceived(getRecivedItemsCount(i, i2));
                mediaUsageStatistics.setBytesSent(getSentBytesCount(i, i2));
                mediaUsageStatistics.setBytesReceived(getReceivedBytesCount(i, i2));
                mediaUsageStatistics.setMediaType(i2);
                arrayList2.add(mediaUsageStatistics);
            }
            networkUsage.setMediaUsageStatistics(arrayList2);
            arrayList.add(networkUsage);
        }
        return new C1768f().m8395a(arrayList);
    }

    public long getReceivedBytesCount(int i, int i2) {
        return i2 == 1 ? (((this.receivedBytes[i][6] - this.receivedBytes[i][5]) - this.receivedBytes[i][3]) - this.receivedBytes[i][2]) - this.receivedBytes[i][4] : this.receivedBytes[i][i2];
    }

    public int getRecivedItemsCount(int i, int i2) {
        return this.receivedItems[i][i2];
    }

    public long getResetStatsDate(int i) {
        return this.resetStatsDate[i];
    }

    public long getSentBytesCount(int i, int i2) {
        return i2 == 1 ? (((this.sentBytes[i][6] - this.sentBytes[i][5]) - this.sentBytes[i][3]) - this.sentBytes[i][2]) - this.sentBytes[i][4] : this.sentBytes[i][i2];
    }

    public int getSentItemsCount(int i, int i2) {
        return this.sentItems[i][i2];
    }

    public void incrementReceivedBytesCount(int i, int i2, long j) {
        long[] jArr = this.receivedBytes[i];
        jArr[i2] = jArr[i2] + j;
        saveStats();
    }

    public void incrementReceivedItemsCount(int i, int i2, int i3) {
        int[] iArr = this.receivedItems[i];
        iArr[i2] = iArr[i2] + i3;
        saveStats();
    }

    public void incrementSentBytesCount(int i, int i2, long j) {
        long[] jArr = this.sentBytes[i];
        jArr[i2] = jArr[i2] + j;
        saveStats();
    }

    public void incrementSentItemsCount(int i, int i2, int i3) {
        int[] iArr = this.sentItems[i];
        iArr[i2] = iArr[i2] + i3;
        saveStats();
    }

    public void incrementTotalCallsTime(int i, int i2) {
        int[] iArr = this.callsTotalTime;
        iArr[i] = iArr[i] + i2;
        saveStats();
    }

    public void resetStats(int i) {
        this.resetStatsDate[i] = System.currentTimeMillis();
        for (int i2 = 0; i2 < 7; i2++) {
            this.sentBytes[i][i2] = 0;
            this.receivedBytes[i][i2] = 0;
            this.sentItems[i][i2] = 0;
            this.receivedItems[i][i2] = 0;
        }
        this.callsTotalTime[i] = 0;
        saveStats();
    }
}
