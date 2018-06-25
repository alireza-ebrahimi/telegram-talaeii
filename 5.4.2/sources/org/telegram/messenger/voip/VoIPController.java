package org.telegram.messenger.voip;

import android.media.audiofx.AcousticEchoCanceler;
import android.os.Build.VERSION;
import android.os.SystemClock;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.tgnet.TLRPC$TL_phoneConnection;
import org.telegram.ui.Components.voip.VoIPHelper;

public class VoIPController {
    public static final int DATA_SAVING_ALWAYS = 2;
    public static final int DATA_SAVING_MOBILE = 1;
    public static final int DATA_SAVING_NEVER = 0;
    public static final int ERROR_AUDIO_IO = 3;
    public static final int ERROR_INCOMPATIBLE = 1;
    public static final int ERROR_LOCALIZED = -3;
    public static final int ERROR_PEER_OUTDATED = -1;
    public static final int ERROR_PRIVACY = -2;
    public static final int ERROR_TIMEOUT = 2;
    public static final int ERROR_UNKNOWN = 0;
    public static final int NET_TYPE_3G = 3;
    public static final int NET_TYPE_DIALUP = 10;
    public static final int NET_TYPE_EDGE = 2;
    public static final int NET_TYPE_ETHERNET = 7;
    public static final int NET_TYPE_GPRS = 1;
    public static final int NET_TYPE_HSPA = 4;
    public static final int NET_TYPE_LTE = 5;
    public static final int NET_TYPE_OTHER_HIGH_SPEED = 8;
    public static final int NET_TYPE_OTHER_LOW_SPEED = 9;
    public static final int NET_TYPE_OTHER_MOBILE = 11;
    public static final int NET_TYPE_UNKNOWN = 0;
    public static final int NET_TYPE_WIFI = 6;
    public static final int STATE_ESTABLISHED = 3;
    public static final int STATE_FAILED = 4;
    public static final int STATE_RECONNECTING = 5;
    public static final int STATE_WAIT_INIT = 1;
    public static final int STATE_WAIT_INIT_ACK = 2;
    protected long callStartTime;
    protected ConnectionStateListener listener;
    protected long nativeInst;

    public interface ConnectionStateListener {
        void onConnectionStateChanged(int i);

        void onSignalBarCountChanged(int i);
    }

    public static class Stats {
        public long bytesRecvdMobile;
        public long bytesRecvdWifi;
        public long bytesSentMobile;
        public long bytesSentWifi;

        public String toString() {
            return "Stats{bytesRecvdMobile=" + this.bytesRecvdMobile + ", bytesSentWifi=" + this.bytesSentWifi + ", bytesRecvdWifi=" + this.bytesRecvdWifi + ", bytesSentMobile=" + this.bytesSentMobile + '}';
        }
    }

    public VoIPController() {
        this.nativeInst = 0;
        this.nativeInst = nativeInit();
    }

    private String getLogFilePath(long j) {
        File logsDir = VoIPHelper.getLogsDir();
        File[] listFiles = logsDir.listFiles();
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(listFiles));
        while (arrayList.size() > 20) {
            File file = (File) arrayList.get(0);
            Iterator it = arrayList.iterator();
            File file2 = file;
            while (it.hasNext()) {
                file = (File) it.next();
                if (!file.getName().endsWith(".log") || file.lastModified() >= file2.lastModified()) {
                    file = file2;
                }
                file2 = file;
            }
            file2.delete();
            arrayList.remove(file2);
        }
        return new File(logsDir, j + ".log").getAbsolutePath();
    }

    private String getLogFilePath(String str) {
        Calendar instance = Calendar.getInstance();
        return new File(ApplicationLoader.applicationContext.getExternalFilesDir(null), String.format(Locale.US, "logs/%02d_%02d_%04d_%02d_%02d_%02d_%s.txt", new Object[]{Integer.valueOf(instance.get(5)), Integer.valueOf(instance.get(2) + 1), Integer.valueOf(instance.get(1)), Integer.valueOf(instance.get(11)), Integer.valueOf(instance.get(12)), Integer.valueOf(instance.get(13)), str})).getAbsolutePath();
    }

    public static String getVersion() {
        return nativeGetVersion();
    }

    private void handleSignalBarsChange(int i) {
        if (this.listener != null) {
            this.listener.onSignalBarCountChanged(i);
        }
    }

    private void handleStateChange(int i) {
        if (i == 3 && this.callStartTime == 0) {
            this.callStartTime = SystemClock.elapsedRealtime();
        }
        if (this.listener != null) {
            this.listener.onConnectionStateChanged(i);
        }
    }

    private native void nativeConnect(long j);

    private native void nativeDebugCtl(long j, int i, int i2);

    private native String nativeGetDebugLog(long j);

    private native String nativeGetDebugString(long j);

    private native int nativeGetLastError(long j);

    private native long nativeGetPreferredRelayID(long j);

    private native void nativeGetStats(long j, Stats stats);

    private static native String nativeGetVersion();

    private native long nativeInit();

    private native void nativeRelease(long j);

    private native void nativeSetAudioOutputGainControlEnabled(long j, boolean z);

    private native void nativeSetConfig(long j, double d, double d2, int i, boolean z, boolean z2, boolean z3, String str, String str2);

    private native void nativeSetEncryptionKey(long j, byte[] bArr, boolean z);

    private native void nativeSetMicMute(long j, boolean z);

    private static native void nativeSetNativeBufferSize(int i);

    private native void nativeSetNetworkType(long j, int i);

    private native void nativeSetProxy(long j, String str, int i, String str2, String str3);

    private native void nativeSetRemoteEndpoints(long j, TLRPC$TL_phoneConnection[] tLRPC$TL_phoneConnectionArr, boolean z);

    private native void nativeStart(long j);

    public static void setNativeBufferSize(int i) {
        nativeSetNativeBufferSize(i);
    }

    public void connect() {
        ensureNativeInstance();
        nativeConnect(this.nativeInst);
    }

    public void debugCtl(int i, int i2) {
        ensureNativeInstance();
        nativeDebugCtl(this.nativeInst, i, i2);
    }

    protected void ensureNativeInstance() {
        if (this.nativeInst == 0) {
            throw new IllegalStateException("Native instance is not valid");
        }
    }

    public long getCallDuration() {
        return SystemClock.elapsedRealtime() - this.callStartTime;
    }

    public String getDebugLog() {
        ensureNativeInstance();
        return nativeGetDebugLog(this.nativeInst);
    }

    public String getDebugString() {
        ensureNativeInstance();
        return nativeGetDebugString(this.nativeInst);
    }

    public int getLastError() {
        ensureNativeInstance();
        return nativeGetLastError(this.nativeInst);
    }

    public long getPreferredRelayID() {
        ensureNativeInstance();
        return nativeGetPreferredRelayID(this.nativeInst);
    }

    public void getStats(Stats stats) {
        ensureNativeInstance();
        if (stats == null) {
            throw new NullPointerException("You're not supposed to pass null here");
        }
        nativeGetStats(this.nativeInst, stats);
    }

    public void release() {
        ensureNativeInstance();
        nativeRelease(this.nativeInst);
        this.nativeInst = 0;
    }

    public void setAudioOutputGainControlEnabled(boolean z) {
        ensureNativeInstance();
        nativeSetAudioOutputGainControlEnabled(this.nativeInst, z);
    }

    public void setConfig(double d, double d2, int i, long j) {
        ensureNativeInstance();
        boolean z = false;
        boolean z2 = false;
        if (VERSION.SDK_INT >= 16) {
            try {
                z = AcousticEchoCanceler.isAvailable();
                z2 = AcousticEchoCanceler.isAvailable();
            } catch (Throwable th) {
            }
        }
        ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getBoolean("dbg_dump_call_stats", false);
        long j2 = this.nativeInst;
        boolean z3 = (VERSION.SDK_INT >= 16 && z && VoIPServerConfig.getBoolean("use_system_aec", true)) ? false : true;
        boolean z4 = (VERSION.SDK_INT >= 16 && z2 && VoIPServerConfig.getBoolean("use_system_ns", true)) ? false : true;
        nativeSetConfig(j2, d, d2, i, z3, z4, true, getLogFilePath(j), null);
    }

    public void setConnectionStateListener(ConnectionStateListener connectionStateListener) {
        this.listener = connectionStateListener;
    }

    public void setEncryptionKey(byte[] bArr, boolean z) {
        if (bArr.length != 256) {
            throw new IllegalArgumentException("key length must be exactly 256 bytes but is " + bArr.length);
        }
        ensureNativeInstance();
        nativeSetEncryptionKey(this.nativeInst, bArr, z);
    }

    public void setMicMute(boolean z) {
        ensureNativeInstance();
        nativeSetMicMute(this.nativeInst, z);
    }

    public void setNetworkType(int i) {
        ensureNativeInstance();
        nativeSetNetworkType(this.nativeInst, i);
    }

    public void setProxy(String str, int i, String str2, String str3) {
        ensureNativeInstance();
        if (str == null) {
            throw new NullPointerException("address can't be null");
        }
        nativeSetProxy(this.nativeInst, str, i, str2, str3);
    }

    public void setRemoteEndpoints(TLRPC$TL_phoneConnection[] tLRPC$TL_phoneConnectionArr, boolean z) {
        if (tLRPC$TL_phoneConnectionArr.length == 0) {
            throw new IllegalArgumentException("endpoints size is 0");
        }
        int i = 0;
        while (i < tLRPC$TL_phoneConnectionArr.length) {
            TLRPC$TL_phoneConnection tLRPC$TL_phoneConnection = tLRPC$TL_phoneConnectionArr[i];
            if (tLRPC$TL_phoneConnection.ip == null || tLRPC$TL_phoneConnection.ip.length() == 0) {
                throw new IllegalArgumentException("endpoint " + tLRPC$TL_phoneConnection + " has empty/null ipv4");
            } else if (tLRPC$TL_phoneConnection.peer_tag == null || tLRPC$TL_phoneConnection.peer_tag.length == 16) {
                i++;
            } else {
                throw new IllegalArgumentException("endpoint " + tLRPC$TL_phoneConnection + " has peer_tag of wrong length");
            }
        }
        ensureNativeInstance();
        nativeSetRemoteEndpoints(this.nativeInst, tLRPC$TL_phoneConnectionArr, z);
    }

    public void start() {
        ensureNativeInstance();
        nativeStart(this.nativeInst);
    }
}
