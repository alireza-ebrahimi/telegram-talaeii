package com.onesignal;

import android.content.Context;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;

class TrackFirebaseAnalytics {
    private static final String EVENT_NOTIFICATION_INFLUENCE_OPEN = "os_notification_influence_open";
    private static final String EVENT_NOTIFICATION_OPENED = "os_notification_opened";
    private static final String EVENT_NOTIFICATION_RECEIVED = "os_notification_received";
    private static Class<?> FirebaseAnalyticsClass;
    private static AtomicLong lastOpenedTime;
    private static OSNotificationPayload lastReceivedPayload;
    private static AtomicLong lastReceivedTime;
    private Context appContext;
    private Object mFirebaseAnalyticsInstance;

    TrackFirebaseAnalytics(Context activity) {
        this.appContext = activity;
    }

    static boolean CanTrack() {
        try {
            FirebaseAnalyticsClass = Class.forName("com.google.firebase.analytics.FirebaseAnalytics");
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    void trackInfluenceOpenEvent() {
        if (lastReceivedTime != null && lastReceivedPayload != null) {
            long now = System.currentTimeMillis();
            if (now - lastReceivedTime.get() > 120000) {
                return;
            }
            if (lastOpenedTime == null || now - lastOpenedTime.get() >= 30000) {
                try {
                    Object firebaseAnalyticsInstance = getFirebaseAnalyticsInstance(this.appContext);
                    Method trackMethod = getTrackMethod(FirebaseAnalyticsClass);
                    String event = EVENT_NOTIFICATION_INFLUENCE_OPEN;
                    Bundle bundle = new Bundle();
                    bundle.putString(Param.SOURCE, "OneSignal");
                    bundle.putString(Param.MEDIUM, NotificationTable.TABLE_NAME);
                    bundle.putString(NotificationTable.COLUMN_NAME_NOTIFICATION_ID, lastReceivedPayload.notificationID);
                    bundle.putString(Param.CAMPAIGN, getCampaignNameFromPayload(lastReceivedPayload));
                    trackMethod.invoke(firebaseAnalyticsInstance, new Object[]{event, bundle});
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }

    void trackOpenedEvent(OSNotificationOpenResult openResult) {
        if (lastOpenedTime == null) {
            lastOpenedTime = new AtomicLong();
        }
        lastOpenedTime.set(System.currentTimeMillis());
        try {
            Object firebaseAnalyticsInstance = getFirebaseAnalyticsInstance(this.appContext);
            Method trackMethod = getTrackMethod(FirebaseAnalyticsClass);
            Bundle bundle = new Bundle();
            bundle.putString(Param.SOURCE, "OneSignal");
            bundle.putString(Param.MEDIUM, NotificationTable.TABLE_NAME);
            bundle.putString(NotificationTable.COLUMN_NAME_NOTIFICATION_ID, openResult.notification.payload.notificationID);
            bundle.putString(Param.CAMPAIGN, getCampaignNameFromPayload(openResult.notification.payload));
            trackMethod.invoke(firebaseAnalyticsInstance, new Object[]{EVENT_NOTIFICATION_OPENED, bundle});
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    void trackReceivedEvent(OSNotificationOpenResult receivedResult) {
        try {
            Object firebaseAnalyticsInstance = getFirebaseAnalyticsInstance(this.appContext);
            Method trackMethod = getTrackMethod(FirebaseAnalyticsClass);
            Bundle bundle = new Bundle();
            bundle.putString(Param.SOURCE, "OneSignal");
            bundle.putString(Param.MEDIUM, NotificationTable.TABLE_NAME);
            bundle.putString(NotificationTable.COLUMN_NAME_NOTIFICATION_ID, receivedResult.notification.payload.notificationID);
            bundle.putString(Param.CAMPAIGN, getCampaignNameFromPayload(receivedResult.notification.payload));
            trackMethod.invoke(firebaseAnalyticsInstance, new Object[]{EVENT_NOTIFICATION_RECEIVED, bundle});
            if (lastReceivedTime == null) {
                lastReceivedTime = new AtomicLong();
            }
            lastReceivedTime.set(System.currentTimeMillis());
            lastReceivedPayload = receivedResult.notification.payload;
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private String getCampaignNameFromPayload(OSNotificationPayload payload) {
        if (!payload.templateName.isEmpty() && !payload.templateId.isEmpty()) {
            return payload.templateName + " - " + payload.templateId;
        }
        if (payload.title != null) {
            return payload.title.substring(0, Math.min(10, payload.title.length()));
        }
        return "";
    }

    private Object getFirebaseAnalyticsInstance(Context context) {
        if (this.mFirebaseAnalyticsInstance == null) {
            try {
                this.mFirebaseAnalyticsInstance = getInstanceMethod(FirebaseAnalyticsClass).invoke(null, new Object[]{context});
            } catch (Throwable e) {
                e.printStackTrace();
                return null;
            }
        }
        return this.mFirebaseAnalyticsInstance;
    }

    private static Method getTrackMethod(Class clazz) {
        try {
            return clazz.getMethod("logEvent", new Class[]{String.class, Bundle.class});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Method getInstanceMethod(Class clazz) {
        try {
            return clazz.getMethod("getInstance", new Class[]{Context.class});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
