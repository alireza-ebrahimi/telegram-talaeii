package net.hockeyapp.android.metrics;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import net.hockeyapp.android.metrics.model.Data;
import net.hockeyapp.android.metrics.model.Domain;
import net.hockeyapp.android.metrics.model.SessionState;
import net.hockeyapp.android.metrics.model.TelemetryData;
import net.hockeyapp.android.utils.AsyncTaskUtils;
import net.hockeyapp.android.utils.HockeyLog;
import net.hockeyapp.android.utils.Util;

public class MetricsManager {
    protected static final AtomicInteger ACTIVITY_COUNT = new AtomicInteger(0);
    protected static final AtomicLong LAST_BACKGROUND = new AtomicLong(getTime());
    private static final Object LOCK = new Object();
    private static final Integer SESSION_RENEWAL_INTERVAL = Integer.valueOf(BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT);
    private static final String TAG = "HA-MetricsManager";
    private static volatile MetricsManager instance;
    private static Channel sChannel;
    private static Sender sSender;
    private static TelemetryContext sTelemetryContext;
    private static boolean sUserMetricsEnabled = true;
    private static WeakReference<Application> sWeakApplication;
    private volatile boolean mSessionTrackingDisabled;
    private MetricsManager$TelemetryLifecycleCallbacks mTelemetryLifecycleCallbacks;

    protected MetricsManager(Context context, TelemetryContext telemetryContext, Sender sender, Persistence persistence, Channel channel) {
        sTelemetryContext = telemetryContext;
        if (sender == null) {
            sender = new Sender();
        }
        sSender = sender;
        if (persistence == null) {
            persistence = new Persistence(context, sender);
        } else {
            persistence.setSender(sender);
        }
        sSender.setPersistence(persistence);
        if (channel == null) {
            sChannel = new Channel(sTelemetryContext, persistence);
        } else {
            sChannel = channel;
        }
        if (persistence.hasFilesAvailable()) {
            persistence.getSender().triggerSending();
        }
    }

    public static void register(Application application) {
        String appIdentifier = Util.getAppIdentifier(application.getApplicationContext());
        if (appIdentifier == null || appIdentifier.length() == 0) {
            throw new IllegalArgumentException("HockeyApp app identifier was not configured correctly in manifest or build configuration.");
        }
        register(application, appIdentifier);
    }

    public static void register(Application application, String appIdentifier) {
        register(application, appIdentifier, null, null, null);
    }

    @Deprecated
    public static void register(Context context, Application application) {
        String appIdentifier = Util.getAppIdentifier(context);
        if (appIdentifier == null || appIdentifier.length() == 0) {
            throw new IllegalArgumentException("HockeyApp app identifier was not configured correctly in manifest or build configuration.");
        }
        register(context, application, appIdentifier);
    }

    @Deprecated
    public static void register(Context context, Application application, String appIdentifier) {
        register(application, appIdentifier, null, null, null);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static void register(android.app.Application r9, java.lang.String r10, net.hockeyapp.android.metrics.Sender r11, net.hockeyapp.android.metrics.Persistence r12, net.hockeyapp.android.metrics.Channel r13) {
        /*
        r7 = 0;
        r0 = instance;
        if (r0 != 0) goto L_0x004f;
    L_0x0005:
        r8 = LOCK;
        monitor-enter(r8);
        r6 = instance;	 Catch:{ all -> 0x0052 }
        if (r6 != 0) goto L_0x0058;
    L_0x000c:
        r1 = r9.getApplicationContext();	 Catch:{ all -> 0x0055 }
        net.hockeyapp.android.Constants.loadFromContext(r1);	 Catch:{ all -> 0x0055 }
        r0 = new net.hockeyapp.android.metrics.MetricsManager;	 Catch:{ all -> 0x0055 }
        r1 = r9.getApplicationContext();	 Catch:{ all -> 0x0055 }
        r2 = new net.hockeyapp.android.metrics.TelemetryContext;	 Catch:{ all -> 0x0055 }
        r3 = r9.getApplicationContext();	 Catch:{ all -> 0x0055 }
        r2.<init>(r3, r10);	 Catch:{ all -> 0x0055 }
        r3 = r11;
        r4 = r12;
        r5 = r13;
        r0.<init>(r1, r2, r3, r4, r5);	 Catch:{ all -> 0x0055 }
        r1 = new java.lang.ref.WeakReference;	 Catch:{ all -> 0x0052 }
        r1.<init>(r9);	 Catch:{ all -> 0x0052 }
        sWeakApplication = r1;	 Catch:{ all -> 0x0052 }
    L_0x002f:
        r1 = net.hockeyapp.android.utils.Util.sessionTrackingSupported();	 Catch:{ all -> 0x0052 }
        if (r1 != 0) goto L_0x0050;
    L_0x0035:
        r1 = 1;
    L_0x0036:
        r0.mSessionTrackingDisabled = r1;	 Catch:{ all -> 0x0052 }
        instance = r0;	 Catch:{ all -> 0x0052 }
        r1 = r0.mSessionTrackingDisabled;	 Catch:{ all -> 0x0052 }
        if (r1 != 0) goto L_0x0046;
    L_0x003e:
        r1 = 0;
        r1 = java.lang.Boolean.valueOf(r1);	 Catch:{ all -> 0x0052 }
        setSessionTrackingDisabled(r1);	 Catch:{ all -> 0x0052 }
    L_0x0046:
        monitor-exit(r8);	 Catch:{ all -> 0x0052 }
        r1 = new net.hockeyapp.android.metrics.MetricsManager$1;
        r1.<init>();
        net.hockeyapp.android.PrivateEventManager.addEventListener(r1);
    L_0x004f:
        return;
    L_0x0050:
        r1 = r7;
        goto L_0x0036;
    L_0x0052:
        r1 = move-exception;
    L_0x0053:
        monitor-exit(r8);	 Catch:{ all -> 0x0052 }
        throw r1;
    L_0x0055:
        r1 = move-exception;
        r0 = r6;
        goto L_0x0053;
    L_0x0058:
        r0 = r6;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: net.hockeyapp.android.metrics.MetricsManager.register(android.app.Application, java.lang.String, net.hockeyapp.android.metrics.Sender, net.hockeyapp.android.metrics.Persistence, net.hockeyapp.android.metrics.Channel):void");
    }

    public static void disableUserMetrics() {
        setUserMetricsEnabled(false);
    }

    public static void enableUserMetrics() {
        setUserMetricsEnabled(true);
    }

    public static boolean isUserMetricsEnabled() {
        return sUserMetricsEnabled;
    }

    private static void setUserMetricsEnabled(boolean enabled) {
        sUserMetricsEnabled = enabled;
        if (sUserMetricsEnabled) {
            instance.registerTelemetryLifecycleCallbacks();
        } else {
            instance.unregisterTelemetryLifecycleCallbacks();
        }
    }

    public static boolean sessionTrackingEnabled() {
        return isUserMetricsEnabled() && !instance.mSessionTrackingDisabled;
    }

    public static void setSessionTrackingDisabled(Boolean disabled) {
        if (instance == null || !isUserMetricsEnabled()) {
            HockeyLog.warn(TAG, "MetricsManager hasn't been registered or User Metrics has been disabled. No User Metrics will be collected!");
            return;
        }
        synchronized (LOCK) {
            if (Util.sessionTrackingSupported()) {
                instance.mSessionTrackingDisabled = disabled.booleanValue();
                if (!disabled.booleanValue()) {
                    instance.registerTelemetryLifecycleCallbacks();
                }
            } else {
                instance.mSessionTrackingDisabled = true;
                instance.unregisterTelemetryLifecycleCallbacks();
            }
        }
    }

    @TargetApi(14)
    private void registerTelemetryLifecycleCallbacks() {
        if (this.mTelemetryLifecycleCallbacks == null) {
            this.mTelemetryLifecycleCallbacks = new MetricsManager$TelemetryLifecycleCallbacks(this, null);
        }
        getApplication().registerActivityLifecycleCallbacks(this.mTelemetryLifecycleCallbacks);
    }

    @TargetApi(14)
    private void unregisterTelemetryLifecycleCallbacks() {
        if (this.mTelemetryLifecycleCallbacks != null) {
            getApplication().unregisterActivityLifecycleCallbacks(this.mTelemetryLifecycleCallbacks);
            this.mTelemetryLifecycleCallbacks = null;
        }
    }

    public static void setCustomServerURL(String serverURL) {
        if (sSender != null) {
            sSender.setCustomServerURL(serverURL);
        } else {
            HockeyLog.warn(TAG, "HockeyApp couldn't set the custom server url. Please register(...) the MetricsManager before setting the server URL.");
        }
    }

    private static Application getApplication() {
        if (sWeakApplication != null) {
            return (Application) sWeakApplication.get();
        }
        return null;
    }

    private static long getTime() {
        return new Date().getTime();
    }

    protected static Channel getChannel() {
        return sChannel;
    }

    protected void setChannel(Channel channel) {
        sChannel = channel;
    }

    protected static Sender getSender() {
        return sSender;
    }

    protected static void setSender(Sender sender) {
        sSender = sender;
    }

    protected static MetricsManager getInstance() {
        return instance;
    }

    private void updateSession() {
        if (ACTIVITY_COUNT.getAndIncrement() != 0) {
            long now = getTime();
            long then = LAST_BACKGROUND.getAndSet(getTime());
            boolean shouldRenew = now - then >= ((long) SESSION_RENEWAL_INTERVAL.intValue());
            HockeyLog.debug(TAG, "Checking if we have to renew a session, time difference is: " + (now - then));
            if (shouldRenew && sessionTrackingEnabled()) {
                HockeyLog.debug(TAG, "Renewing session");
                renewSession();
            }
        } else if (sessionTrackingEnabled()) {
            HockeyLog.debug(TAG, "Starting & tracking session");
            renewSession();
        } else {
            HockeyLog.debug(TAG, "Session management disabled by the developer");
        }
    }

    protected void renewSession() {
        sTelemetryContext.renewSessionContext(UUID.randomUUID().toString());
        trackSessionState(SessionState.START);
    }

    private void trackSessionState(SessionState sessionState) {
        try {
            AsyncTaskUtils.execute(new MetricsManager$2(this, sessionState));
        } catch (RejectedExecutionException e) {
            HockeyLog.error("Could not track session state. Executor rejected async task.", e);
        }
    }

    protected static Data<Domain> createData(TelemetryData telemetryData) {
        Data<Domain> data = new Data();
        data.setBaseData(telemetryData);
        data.setBaseType(telemetryData.getBaseType());
        data.QualifiedName = telemetryData.getEnvelopeName();
        return data;
    }

    public static void trackEvent(String eventName) {
        trackEvent(eventName, null);
    }

    public static void trackEvent(String eventName, Map<String, String> properties) {
        trackEvent(eventName, properties, null);
    }

    public static void trackEvent(String eventName, Map<String, String> properties, Map<String, Double> measurements) {
        if (!TextUtils.isEmpty(eventName)) {
            if (instance == null) {
                Log.w(TAG, "MetricsManager hasn't been registered or User Metrics has been disabled. No User Metrics will be collected!");
            } else if (isUserMetricsEnabled()) {
                try {
                    AsyncTaskUtils.execute(new MetricsManager$3(eventName, properties, measurements));
                } catch (RejectedExecutionException e) {
                    HockeyLog.error("Could not track custom event. Executor rejected async task.", e);
                }
            } else {
                HockeyLog.warn("User Metrics is disabled. Will not track event.");
            }
        }
    }
}
