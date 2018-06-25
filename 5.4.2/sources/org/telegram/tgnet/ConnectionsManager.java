package org.telegram.tgnet;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.data.DataBufferSafeParcelable;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.customization.p151g.C2820e;
import org.telegram.customization.service.ProxyService;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.StatsController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import utils.p178a.C3791b;

public class ConnectionsManager {
    public static final int ConnectionStateConnected = 3;
    public static final int ConnectionStateConnecting = 1;
    public static final int ConnectionStateConnectingToProxy = 4;
    public static final int ConnectionStateUpdating = 5;
    public static final int ConnectionStateWaitingForNetwork = 2;
    public static final int ConnectionTypeDownload = 2;
    public static final int ConnectionTypeDownload2 = 65538;
    public static final int ConnectionTypeGeneric = 1;
    public static final int ConnectionTypePush = 8;
    public static final int ConnectionTypeUpload = 4;
    public static final int DEFAULT_DATACENTER_ID = Integer.MAX_VALUE;
    public static final int FileTypeAudio = 50331648;
    public static final int FileTypeFile = 67108864;
    public static final int FileTypePhoto = 16777216;
    public static final int FileTypeVideo = 33554432;
    private static volatile ConnectionsManager Instance = null;
    public static final int RequestFlagCanCompress = 4;
    public static final int RequestFlagEnableUnauthorized = 1;
    public static final int RequestFlagFailOnServerErrors = 2;
    public static final int RequestFlagForceDownload = 32;
    public static final int RequestFlagInvokeAfter = 64;
    public static final int RequestFlagNeedQuickAck = 128;
    public static final int RequestFlagTryDifferentDc = 16;
    public static final int RequestFlagWithoutLogin = 8;
    public static int connectingToProxyRetryThreshold = 0;
    private static AsyncTask currentTask;
    private static final int dnsConfigVersion = 0;
    private static long lastDnsRequestTime;
    public static long startServiceTime = 0;
    private boolean appPaused = true;
    private int appResumeCount;
    private int connectionState = native_getConnectionState();
    private boolean isUpdating;
    private int lastClassGuid = 1;
    private long lastPauseTime = System.currentTimeMillis();
    private AtomicInteger lastRequestToken = new AtomicInteger(1);
    private WakeLock wakeLock;

    /* renamed from: org.telegram.tgnet.ConnectionsManager$2 */
    class C37802 extends BroadcastReceiver {
        C37802() {
        }

        public void onReceive(Context context, Intent intent) {
            ConnectionsManager.this.checkConnection();
        }
    }

    /* renamed from: org.telegram.tgnet.ConnectionsManager$3 */
    static class C37813 implements Runnable {
        C37813() {
        }

        public void run() {
            if (ConnectionsManager.getInstance().wakeLock.isHeld()) {
                FileLog.m13725d("release wakelock");
                ConnectionsManager.getInstance().wakeLock.release();
            }
        }
    }

    /* renamed from: org.telegram.tgnet.ConnectionsManager$5 */
    static class C37835 implements Runnable {
        C37835() {
        }

        public void run() {
            MessagesController.getInstance().updateTimerProc();
        }
    }

    /* renamed from: org.telegram.tgnet.ConnectionsManager$6 */
    static class C37846 implements Runnable {
        C37846() {
        }

        public void run() {
            MessagesController.getInstance().getDifference();
        }
    }

    /* renamed from: org.telegram.tgnet.ConnectionsManager$8 */
    static class C37868 implements Runnable {
        C37868() {
        }

        public void run() {
            if (UserConfig.getClientUserId() != 0) {
                UserConfig.clearConfig();
                MessagesController.getInstance().performLogout(false);
            }
        }
    }

    private static class DnsLoadTask extends AsyncTask<Void, Void, NativeByteBuffer> {
        private DnsLoadTask() {
        }

        public static NetworkInfo getNetworkInfo(Context context) {
            return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        }

        public static boolean isConnectedMobile(Context context) {
            NetworkInfo networkInfo = getNetworkInfo(context);
            return networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == 0;
        }

        protected NativeByteBuffer doInBackground(Void... voidArr) {
            ByteArrayOutputStream byteArrayOutputStream;
            byte[] decode;
            NativeByteBuffer nativeByteBuffer;
            try {
                URLConnection openConnection = (ConnectionsManager.native_isTestBackend() != 0 ? new URL("https://google.com/test/") : new URL("https://google.com")).openConnection();
                openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 10_0 like Mac OS X) AppleWebKit/602.1.38 (KHTML, like Gecko) Version/10.0 Mobile/14A5297c Safari/602.1");
                openConnection.addRequestProperty("Host", String.format(Locale.US, "dns-telegram%1$s.appspot.com", new Object[]{TtmlNode.ANONYMOUS_REGION_ID}));
                openConnection.setConnectTimeout(DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS);
                openConnection.setReadTimeout(DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS);
                openConnection.connect();
                InputStream inputStream = openConnection.getInputStream();
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[TLRPC.MESSAGE_FLAG_EDITED];
                while (!isCancelled()) {
                    int read = inputStream.read(bArr);
                    if (read > 0) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        if (read == -1) {
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        decode = Base64.decode(byteArrayOutputStream.toByteArray(), 0);
                        nativeByteBuffer = new NativeByteBuffer(decode.length);
                        nativeByteBuffer.writeBytes(decode);
                        return nativeByteBuffer;
                    }
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Throwable th) {
                FileLog.m13728e(th);
                return null;
            }
            decode = Base64.decode(byteArrayOutputStream.toByteArray(), 0);
            nativeByteBuffer = new NativeByteBuffer(decode.length);
            nativeByteBuffer.writeBytes(decode);
            return nativeByteBuffer;
        }

        protected void onPostExecute(NativeByteBuffer nativeByteBuffer) {
            if (nativeByteBuffer != null) {
                ConnectionsManager.currentTask = null;
                ConnectionsManager.native_applyDnsConfig(nativeByteBuffer.address);
                return;
            }
            AsyncTask dnsTxtLoadTask = new DnsTxtLoadTask();
            dnsTxtLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
            ConnectionsManager.currentTask = dnsTxtLoadTask;
        }
    }

    private static class DnsTxtLoadTask extends AsyncTask<Void, Void, NativeByteBuffer> {

        /* renamed from: org.telegram.tgnet.ConnectionsManager$DnsTxtLoadTask$1 */
        class C37881 implements Comparator<String> {
            C37881() {
            }

            public int compare(String str, String str2) {
                int length = str.length();
                int length2 = str2.length();
                return length > length2 ? -1 : length < length2 ? 1 : 0;
            }
        }

        private DnsTxtLoadTask() {
        }

        protected NativeByteBuffer doInBackground(Void... voidArr) {
            ByteArrayOutputStream byteArrayOutputStream;
            JSONArray jSONArray;
            int length;
            ArrayList arrayList;
            int i;
            StringBuilder stringBuilder;
            byte[] decode;
            NativeByteBuffer nativeByteBuffer;
            try {
                URLConnection openConnection = new URL("https://google.com/resolve?name=" + String.format(Locale.US, ConnectionsManager.native_isTestBackend() != 0 ? "tap%1$s.stel.com" : "ap%1$s.stel.com", new Object[]{TtmlNode.ANONYMOUS_REGION_ID}) + "&type=16").openConnection();
                openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 10_0 like Mac OS X) AppleWebKit/602.1.38 (KHTML, like Gecko) Version/10.0 Mobile/14A5297c Safari/602.1");
                openConnection.addRequestProperty("Host", "dns.google.com");
                openConnection.setConnectTimeout(DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS);
                openConnection.setReadTimeout(DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS);
                openConnection.connect();
                InputStream inputStream = openConnection.getInputStream();
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[TLRPC.MESSAGE_FLAG_EDITED];
                while (!isCancelled()) {
                    int read = inputStream.read(bArr);
                    if (read > 0) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        if (read == -1) {
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        jSONArray = new JSONObject(new String(byteArrayOutputStream.toByteArray(), C3446C.UTF8_NAME)).getJSONArray("Answer");
                        length = jSONArray.length();
                        arrayList = new ArrayList(length);
                        for (i = 0; i < length; i++) {
                            arrayList.add(jSONArray.getJSONObject(i).getString(DataBufferSafeParcelable.DATA_FIELD));
                        }
                        Collections.sort(arrayList, new C37881());
                        stringBuilder = new StringBuilder();
                        for (i = 0; i < arrayList.size(); i++) {
                            stringBuilder.append(((String) arrayList.get(i)).replace("\"", TtmlNode.ANONYMOUS_REGION_ID));
                        }
                        decode = Base64.decode(stringBuilder.toString(), 0);
                        nativeByteBuffer = new NativeByteBuffer(decode.length);
                        nativeByteBuffer.writeBytes(decode);
                        return nativeByteBuffer;
                    }
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Throwable th) {
                FileLog.m13728e(th);
                return null;
            }
            jSONArray = new JSONObject(new String(byteArrayOutputStream.toByteArray(), C3446C.UTF8_NAME)).getJSONArray("Answer");
            length = jSONArray.length();
            arrayList = new ArrayList(length);
            for (i = 0; i < length; i++) {
                arrayList.add(jSONArray.getJSONObject(i).getString(DataBufferSafeParcelable.DATA_FIELD));
            }
            Collections.sort(arrayList, new C37881());
            stringBuilder = new StringBuilder();
            for (i = 0; i < arrayList.size(); i++) {
                stringBuilder.append(((String) arrayList.get(i)).replace("\"", TtmlNode.ANONYMOUS_REGION_ID));
            }
            decode = Base64.decode(stringBuilder.toString(), 0);
            nativeByteBuffer = new NativeByteBuffer(decode.length);
            nativeByteBuffer.writeBytes(decode);
            return nativeByteBuffer;
        }

        protected void onPostExecute(NativeByteBuffer nativeByteBuffer) {
            if (nativeByteBuffer != null) {
                ConnectionsManager.native_applyDnsConfig(nativeByteBuffer.address);
            }
            ConnectionsManager.currentTask = null;
        }
    }

    public ConnectionsManager() {
        String systemLocaleStringIso639;
        String localeStringIso639;
        String str;
        String str2;
        String str3;
        String file = ApplicationLoader.getFilesDirFixed().toString();
        boolean z = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("pushConnection", true);
        try {
            systemLocaleStringIso639 = LocaleController.getSystemLocaleStringIso639();
            localeStringIso639 = LocaleController.getLocaleStringIso639();
            str = Build.MANUFACTURER + Build.MODEL;
            PackageInfo packageInfo = ApplicationLoader.applicationContext.getPackageManager().getPackageInfo(ApplicationLoader.applicationContext.getPackageName(), 0);
            str2 = packageInfo.versionName + " (" + packageInfo.versionCode + ")";
            str3 = "SDK " + VERSION.SDK_INT;
        } catch (Exception e) {
            systemLocaleStringIso639 = "en";
            localeStringIso639 = TtmlNode.ANONYMOUS_REGION_ID;
            str = "Android unknown";
            str2 = "App version unknown";
            str3 = "SDK " + VERSION.SDK_INT;
        }
        String str4 = systemLocaleStringIso639.trim().length() == 0 ? "en" : localeStringIso639;
        String str5 = str.trim().length() == 0 ? "Android unknown" : str;
        String str6 = str2.trim().length() == 0 ? "App version unknown" : str2;
        String str7 = str3.trim().length() == 0 ? "SDK Unknown" : str3;
        UserConfig.loadConfig();
        if (!C3791b.m14060y(ApplicationLoader.applicationContext)) {
            init(BuildVars.BUILD_VERSION, 73, C3791b.m13912P(ApplicationLoader.applicationContext), str5, str7, str6, str4, systemLocaleStringIso639, file, FileLog.getNetworkLogPath(), UserConfig.getClientUserId(), z);
        }
        try {
            this.wakeLock = ((PowerManager) ApplicationLoader.applicationContext.getSystemService("power")).newWakeLock(1, "lock");
            this.wakeLock.setReferenceCounted(false);
        } catch (Throwable e2) {
            FileLog.m13728e(e2);
        }
    }

    public static int getCurrentNetworkType() {
        return isConnectedOrConnectingToWiFi() ? 1 : isRoaming() ? 2 : 0;
    }

    public static ConnectionsManager getInstance() {
        ConnectionsManager connectionsManager = Instance;
        if (connectionsManager == null) {
            synchronized (ConnectionsManager.class) {
                connectionsManager = Instance;
                if (connectionsManager == null) {
                    connectionsManager = new ConnectionsManager();
                    Instance = connectionsManager;
                }
            }
        }
        return connectionsManager;
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
    }

    public static boolean isConnectedMobile(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == 0;
    }

    public static boolean isConnectedOrConnectingToWiFi() {
        try {
            NetworkInfo networkInfo = ((ConnectivityManager) ApplicationLoader.applicationContext.getSystemService("connectivity")).getNetworkInfo(1);
            State state = networkInfo.getState();
            if (networkInfo != null && (state == State.CONNECTED || state == State.CONNECTING || state == State.SUSPENDED)) {
                return true;
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return false;
    }

    public static boolean isConnectedToWiFi() {
        try {
            NetworkInfo networkInfo = ((ConnectivityManager) ApplicationLoader.applicationContext.getSystemService("connectivity")).getNetworkInfo(1);
            if (networkInfo != null && networkInfo.getState() == State.CONNECTED) {
                return true;
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return false;
    }

    public static boolean isNetworkOnline() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) ApplicationLoader.applicationContext.getSystemService("connectivity");
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && (activeNetworkInfo.isConnectedOrConnecting() || activeNetworkInfo.isAvailable())) {
                return true;
            }
            activeNetworkInfo = connectivityManager.getNetworkInfo(0);
            if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
                return true;
            }
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return true;
        }
    }

    public static boolean isRoaming() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) ApplicationLoader.applicationContext.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isRoaming();
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        return false;
    }

    public static native void native_applyDatacenterAddress(int i, String str, int i2);

    public static native void native_applyDnsConfig(int i);

    public static native void native_bindRequestToGuid(int i, int i2);

    public static native void native_cancelRequest(int i, boolean z);

    public static native void native_cancelRequestsForGuid(int i);

    public static native void native_cleanUp();

    public static native int native_getConnectionState();

    public static native int native_getCurrentTime();

    public static native long native_getCurrentTimeMillis();

    public static native int native_getTimeDifference();

    public static native void native_init(int i, int i2, int i3, String str, String str2, String str3, String str4, String str5, String str6, String str7, int i4, boolean z, boolean z2, int i5);

    public static native int native_isTestBackend();

    public static native void native_pauseNetwork();

    public static native void native_resumeNetwork(boolean z);

    public static native void native_sendRequest(int i, RequestDelegateInternal requestDelegateInternal, QuickAckDelegate quickAckDelegate, WriteToSocketDelegate writeToSocketDelegate, int i2, int i3, int i4, boolean z, int i5);

    public static native void native_setJava(boolean z);

    public static native void native_setLangCode(String str);

    public static native void native_setNetworkAvailable(boolean z, int i);

    public static native void native_setProxySettings(String str, int i, String str2, String str3);

    public static native void native_setPushConnectionEnabled(boolean z);

    public static native void native_setUseIpv6(boolean z);

    public static native void native_setUserId(int i);

    public static native void native_switchBackend();

    public static native void native_updateDcSettings();

    public static void onBytesReceived(int i, int i2) {
        try {
            StatsController.getInstance().incrementReceivedBytesCount(i2, 6, (long) i);
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public static void onBytesSent(int i, int i2) {
        try {
            StatsController.getInstance().incrementSentBytesCount(i2, 6, (long) i);
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public static void onConnectionStateChanged(final int i) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                ConnectionsManager.getInstance().connectionState = i;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.didUpdatedConnectionState, new Object[0]);
            }
        });
    }

    public static void onInternalPushReceived() {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                try {
                    if (!ConnectionsManager.getInstance().wakeLock.isHeld()) {
                        ConnectionsManager.getInstance().wakeLock.acquire(10000);
                        FileLog.m13725d("acquire wakelock");
                    }
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public static void onLogout() {
        AndroidUtilities.runOnUIThread(new C37868());
    }

    public static void onRequestNewServerIpAndPort(int i) {
        if (currentTask != null) {
            return;
        }
        if ((i == 1 || Math.abs(lastDnsRequestTime - System.currentTimeMillis()) >= 10000) && isNetworkOnline()) {
            lastDnsRequestTime = System.currentTimeMillis();
            AsyncTask dnsTxtLoadTask;
            if (i == 1) {
                dnsTxtLoadTask = new DnsTxtLoadTask();
                dnsTxtLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
                currentTask = dnsTxtLoadTask;
                return;
            }
            dnsTxtLoadTask = new DnsLoadTask();
            dnsTxtLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
            currentTask = dnsTxtLoadTask;
        }
    }

    public static void onSessionCreated() {
        Utilities.stageQueue.postRunnable(new C37846());
    }

    public static void onUnparsedMessageReceived(int i) {
        try {
            NativeByteBuffer wrap = NativeByteBuffer.wrap(i);
            wrap.reused = true;
            final TLObject TLdeserialize = TLClassStore.Instance().TLdeserialize(wrap, wrap.readInt32(true), true);
            if (TLdeserialize instanceof TLRPC$Updates) {
                FileLog.m13725d("java received " + TLdeserialize);
                AndroidUtilities.runOnUIThread(new C37813());
                Utilities.stageQueue.postRunnable(new Runnable() {
                    public void run() {
                        MessagesController.getInstance().processUpdates((TLRPC$Updates) TLdeserialize, false);
                    }
                });
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public static void onUpdate() {
        Utilities.stageQueue.postRunnable(new C37835());
        try {
            if ((getInstance().connectionState == 4 || getInstance().connectionState == 1) && System.currentTimeMillis() - startServiceTime > 10000) {
                startServiceTime = System.currentTimeMillis();
                ProxyService.m13191b(ApplicationLoader.applicationContext, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onUpdateConfig(int i) {
        try {
            AbstractSerializedData wrap = NativeByteBuffer.wrap(i);
            wrap.reused = true;
            final TLRPC$TL_config TLdeserialize = TLRPC$TL_config.TLdeserialize(wrap, wrap.readInt32(true), true);
            if (TLdeserialize != null) {
                Utilities.stageQueue.postRunnable(new Runnable() {
                    public void run() {
                        MessagesController.getInstance().updateConfig(TLdeserialize);
                    }
                });
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public static void printHumanReadableStatus(int i) {
        switch (i) {
            case 1:
                Log.d("LEE", "ConnectionStateConnecting");
                return;
            case 2:
                Log.d("LEE", "ConnectionStateWaitingForNetwork");
                return;
            case 3:
                Log.d("LEE", "ConnectionStateConnected");
                return;
            case 4:
                Log.d("LEE", "ConnectionStateConnectingToProxy");
                return;
            case 5:
                Log.d("LEE", "ConnectionStateUpdating");
                return;
            default:
                return;
        }
    }

    @SuppressLint({"NewApi"})
    protected static boolean useIpv6Address() {
        if (VERSION.SDK_INT < 19) {
            return false;
        }
        NetworkInterface networkInterface;
        InetAddress address;
        if (BuildVars.DEBUG_VERSION) {
            try {
                Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    networkInterface = (NetworkInterface) networkInterfaces.nextElement();
                    if (!(!networkInterface.isUp() || networkInterface.isLoopback() || networkInterface.getInterfaceAddresses().isEmpty())) {
                        FileLog.m13726e("valid interface: " + networkInterface);
                        List interfaceAddresses = networkInterface.getInterfaceAddresses();
                        for (int i = 0; i < interfaceAddresses.size(); i++) {
                            address = ((InterfaceAddress) interfaceAddresses.get(i)).getAddress();
                            if (BuildVars.DEBUG_VERSION) {
                                FileLog.m13726e("address: " + address.getHostAddress());
                            }
                            if (!(address.isLinkLocalAddress() || address.isLoopbackAddress() || address.isMulticastAddress() || !BuildVars.DEBUG_VERSION)) {
                                FileLog.m13726e("address is good");
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                FileLog.m13728e(th);
            }
        }
        try {
            Enumeration networkInterfaces2 = NetworkInterface.getNetworkInterfaces();
            boolean z = false;
            boolean z2 = false;
            while (networkInterfaces2.hasMoreElements()) {
                networkInterface = (NetworkInterface) networkInterfaces2.nextElement();
                if (networkInterface.isUp() && !networkInterface.isLoopback()) {
                    List interfaceAddresses2 = networkInterface.getInterfaceAddresses();
                    int i2 = 0;
                    while (i2 < interfaceAddresses2.size()) {
                        boolean z3;
                        address = ((InterfaceAddress) interfaceAddresses2.get(i2)).getAddress();
                        if (!(address.isLinkLocalAddress() || address.isLoopbackAddress())) {
                            if (address.isMulticastAddress()) {
                                z3 = z;
                                z = z2;
                            } else if (address instanceof Inet6Address) {
                                z3 = true;
                                z = z2;
                            } else if ((address instanceof Inet4Address) && !address.getHostAddress().startsWith("192.0.0.")) {
                                z3 = z;
                                z = true;
                            }
                            i2++;
                            z2 = z;
                            z = z3;
                        }
                        z3 = z;
                        z = z2;
                        i2++;
                        z2 = z;
                        z = z3;
                    }
                }
            }
            if (!z2 && r1) {
                return true;
            }
        } catch (Throwable th2) {
            FileLog.m13728e(th2);
        }
        return false;
    }

    public void applyDatacenterAddress(int i, String str, int i2) {
        native_applyDatacenterAddress(i, str, i2);
    }

    public void bindRequestToGuid(int i, int i2) {
        native_bindRequestToGuid(i, i2);
    }

    public void cancelRequest(int i, boolean z) {
        native_cancelRequest(i, z);
    }

    public void cancelRequestsForGuid(int i) {
        native_cancelRequestsForGuid(i);
    }

    public void checkConnection() {
        native_setUseIpv6(useIpv6Address());
        native_setNetworkAvailable(isNetworkOnline(), getCurrentNetworkType());
    }

    public void cleanup() {
        native_cleanUp();
    }

    public int generateClassGuid() {
        int i = this.lastClassGuid;
        this.lastClassGuid = i + 1;
        return i;
    }

    public int getConnectionState() {
        return (this.connectionState == 3 && this.isUpdating) ? 5 : this.connectionState;
    }

    public int getCurrentTime() {
        return native_getCurrentTime();
    }

    public long getCurrentTimeMillis() {
        return native_getCurrentTimeMillis();
    }

    public long getPauseTime() {
        return this.lastPauseTime;
    }

    public int getTimeDifference() {
        return native_getTimeDifference();
    }

    public void init(int i, int i2, int i3, String str, String str2, String str3, String str4, String str5, String str6, String str7, int i4, boolean z) {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        Object string = sharedPreferences.getString("proxy_ip", TtmlNode.ANONYMOUS_REGION_ID);
        String string2 = sharedPreferences.getString("proxy_user", TtmlNode.ANONYMOUS_REGION_ID);
        String string3 = sharedPreferences.getString("proxy_pass", TtmlNode.ANONYMOUS_REGION_ID);
        int i5 = sharedPreferences.getInt("proxy_port", 1080);
        if (sharedPreferences.getBoolean("proxy_enabled", false) && !TextUtils.isEmpty(string)) {
            native_setProxySettings(string, i5, string2, string3);
        }
        native_init(i, i2, i3, str, str2, str3, str4, str5, str6, str7, i4, z, isNetworkOnline(), getCurrentNetworkType());
        checkConnection();
        ApplicationLoader.applicationContext.registerReceiver(new C37802(), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public void resumeNetworkMaybe() {
        native_resumeNetwork(true);
    }

    public int sendRequest(TLObject tLObject, RequestDelegate requestDelegate) {
        return sendRequest(tLObject, requestDelegate, null, 0);
    }

    public int sendRequest(TLObject tLObject, RequestDelegate requestDelegate, int i) {
        return sendRequest(tLObject, requestDelegate, null, null, i, Integer.MAX_VALUE, 1, true);
    }

    public int sendRequest(TLObject tLObject, RequestDelegate requestDelegate, int i, int i2) {
        return sendRequest(tLObject, requestDelegate, null, null, i, Integer.MAX_VALUE, i2, true);
    }

    public int sendRequest(TLObject tLObject, RequestDelegate requestDelegate, QuickAckDelegate quickAckDelegate, int i) {
        return sendRequest(tLObject, requestDelegate, quickAckDelegate, null, i, Integer.MAX_VALUE, 1, true);
    }

    public int sendRequest(TLObject tLObject, RequestDelegate requestDelegate, QuickAckDelegate quickAckDelegate, WriteToSocketDelegate writeToSocketDelegate, int i, int i2, int i3, boolean z) {
        final int andIncrement = this.lastRequestToken.getAndIncrement();
        final TLObject tLObject2 = tLObject;
        final RequestDelegate requestDelegate2 = requestDelegate;
        final QuickAckDelegate quickAckDelegate2 = quickAckDelegate;
        final WriteToSocketDelegate writeToSocketDelegate2 = writeToSocketDelegate;
        final int i4 = i;
        final int i5 = i2;
        final int i6 = i3;
        final boolean z2 = z;
        Utilities.stageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.tgnet.ConnectionsManager$1$1 */
            class C37781 implements RequestDelegateInternal {
                C37781() {
                }

                public void run(int i, int i2, String str, int i3) {
                    TLObject deserializeResponse;
                    TLRPC$TL_error tLRPC$TL_error = null;
                    if (i != 0) {
                        try {
                            AbstractSerializedData wrap = NativeByteBuffer.wrap(i);
                            wrap.reused = true;
                            deserializeResponse = tLObject2.deserializeResponse(wrap, wrap.readInt32(true), true);
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                            return;
                        }
                    } else if (str != null) {
                        TLRPC$TL_error tLRPC$TL_error2 = new TLRPC$TL_error();
                        tLRPC$TL_error2.code = i2;
                        tLRPC$TL_error2.text = str;
                        FileLog.m13726e(tLObject2 + " got error " + tLRPC$TL_error2.code + " " + tLRPC$TL_error2.text);
                        TLRPC$TL_error tLRPC$TL_error3 = tLRPC$TL_error2;
                        deserializeResponse = null;
                        tLRPC$TL_error = tLRPC$TL_error3;
                    } else {
                        deserializeResponse = null;
                    }
                    if (deserializeResponse != null) {
                        deserializeResponse.networkType = i3;
                    }
                    FileLog.m13725d("java received " + deserializeResponse + " error = " + tLRPC$TL_error);
                    Utilities.stageQueue.postRunnable(new Runnable() {
                        public void run() {
                            requestDelegate2.run(deserializeResponse, tLRPC$TL_error);
                            if (deserializeResponse != null) {
                                deserializeResponse.freeResources();
                            }
                        }
                    });
                }
            }

            public void run() {
                FileLog.m13725d("send request " + tLObject2 + " with token = " + andIncrement);
                try {
                    AbstractSerializedData nativeByteBuffer = new NativeByteBuffer(tLObject2.getObjectSize());
                    tLObject2.serializeToStream(nativeByteBuffer);
                    tLObject2.freeResources();
                    ConnectionsManager.native_sendRequest(nativeByteBuffer.address, new C37781(), quickAckDelegate2, writeToSocketDelegate2, i4, i5, i6, z2, andIncrement);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
        return andIncrement;
    }

    public void setAppPaused(boolean z, boolean z2) {
        if (!z2) {
            this.appPaused = z;
            FileLog.m13725d("app paused = " + z);
            if (z) {
                this.appResumeCount--;
            } else {
                this.appResumeCount++;
            }
            FileLog.m13725d("app resume count " + this.appResumeCount);
            if (this.appResumeCount < 0) {
                this.appResumeCount = 0;
            }
        }
        if (this.appResumeCount == 0) {
            if (this.lastPauseTime == 0) {
                this.lastPauseTime = System.currentTimeMillis();
            }
            native_pauseNetwork();
        } else if (!this.appPaused) {
            FileLog.m13726e("reset app pause time");
            if (this.lastPauseTime != 0 && System.currentTimeMillis() - this.lastPauseTime > DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS) {
                ContactsController.getInstance().checkContacts();
            }
            this.lastPauseTime = 0;
            native_resumeNetwork(false);
        } else {
            return;
        }
        if (!z) {
            Log.d("LEE", "ConnectionManager getProxyServer");
            C2820e.m13150a(ApplicationLoader.applicationContext);
        }
    }

    public void setIsUpdating(final boolean z) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (ConnectionsManager.this.isUpdating != z) {
                    ConnectionsManager.this.isUpdating = z;
                    if (ConnectionsManager.this.connectionState == 3) {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.didUpdatedConnectionState, new Object[0]);
                    }
                }
            }
        });
    }

    public void setLangCode(String str) {
        native_setLangCode(str);
    }

    public void setPushConnectionEnabled(boolean z) {
        native_setPushConnectionEnabled(z);
    }

    public void setUserId(int i) {
        native_setUserId(i);
    }

    public void switchBackend() {
        ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().remove("language_showed2").commit();
        native_switchBackend();
    }

    public void updateDcSettings() {
        native_updateDcSettings();
    }
}
