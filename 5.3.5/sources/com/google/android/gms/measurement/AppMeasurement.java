package com.google.android.gms.measurement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.Size;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.zzbz;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzckj;
import com.google.android.gms.internal.zzclz;
import com.google.android.gms.internal.zzcnl;
import com.google.android.gms.internal.zzcno;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.List;
import java.util.Map;

@Keep
@Deprecated
public class AppMeasurement {
    @Hide
    @KeepForSdk
    public static final String CRASH_ORIGIN = "crash";
    @Hide
    @KeepForSdk
    public static final String FCM_ORIGIN = "fcm";
    private final zzckj zzjev;

    @Hide
    public static class ConditionalUserProperty {
        @Keep
        @Hide
        public boolean mActive;
        @Keep
        @Hide
        public String mAppId;
        @Keep
        @Hide
        public long mCreationTimestamp;
        @Keep
        @Hide
        public String mExpiredEventName;
        @Keep
        @Hide
        public Bundle mExpiredEventParams;
        @Keep
        @Hide
        public String mName;
        @Keep
        @Hide
        public String mOrigin;
        @Keep
        @Hide
        public long mTimeToLive;
        @Keep
        @Hide
        public String mTimedOutEventName;
        @Keep
        @Hide
        public Bundle mTimedOutEventParams;
        @Keep
        @Hide
        public String mTriggerEventName;
        @Keep
        @Hide
        public long mTriggerTimeout;
        @Keep
        @Hide
        public String mTriggeredEventName;
        @Keep
        @Hide
        public Bundle mTriggeredEventParams;
        @Keep
        @Hide
        public long mTriggeredTimestamp;
        @Keep
        @Hide
        public Object mValue;

        public ConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) {
            zzbq.checkNotNull(conditionalUserProperty);
            this.mAppId = conditionalUserProperty.mAppId;
            this.mOrigin = conditionalUserProperty.mOrigin;
            this.mCreationTimestamp = conditionalUserProperty.mCreationTimestamp;
            this.mName = conditionalUserProperty.mName;
            if (conditionalUserProperty.mValue != null) {
                this.mValue = zzcno.zzag(conditionalUserProperty.mValue);
                if (this.mValue == null) {
                    this.mValue = conditionalUserProperty.mValue;
                }
            }
            this.mValue = conditionalUserProperty.mValue;
            this.mActive = conditionalUserProperty.mActive;
            this.mTriggerEventName = conditionalUserProperty.mTriggerEventName;
            this.mTriggerTimeout = conditionalUserProperty.mTriggerTimeout;
            this.mTimedOutEventName = conditionalUserProperty.mTimedOutEventName;
            if (conditionalUserProperty.mTimedOutEventParams != null) {
                this.mTimedOutEventParams = new Bundle(conditionalUserProperty.mTimedOutEventParams);
            }
            this.mTriggeredEventName = conditionalUserProperty.mTriggeredEventName;
            if (conditionalUserProperty.mTriggeredEventParams != null) {
                this.mTriggeredEventParams = new Bundle(conditionalUserProperty.mTriggeredEventParams);
            }
            this.mTriggeredTimestamp = conditionalUserProperty.mTriggeredTimestamp;
            this.mTimeToLive = conditionalUserProperty.mTimeToLive;
            this.mExpiredEventName = conditionalUserProperty.mExpiredEventName;
            if (conditionalUserProperty.mExpiredEventParams != null) {
                this.mExpiredEventParams = new Bundle(conditionalUserProperty.mExpiredEventParams);
            }
        }
    }

    @Hide
    @KeepForSdk
    public static final class Event extends com.google.firebase.analytics.FirebaseAnalytics.Event {
        @Hide
        @KeepForSdk
        public static final String AD_REWARD = "_ar";
        @Hide
        @KeepForSdk
        public static final String APP_EXCEPTION = "_ae";
        @Hide
        public static final String[] zzjew = new String[]{"app_clear_data", "app_exception", "app_remove", "app_upgrade", "app_install", "app_update", "firebase_campaign", "error", "first_open", "first_visit", "in_app_purchase", "notification_dismiss", "notification_foreground", "notification_open", "notification_receive", "os_update", "session_start", "user_engagement", "ad_exposure", "adunit_exposure", "ad_query", "ad_activeview", "ad_impression", "ad_click", "ad_reward", "screen_view", "ga_extra_parameter"};
        @Hide
        public static final String[] zzjex = new String[]{"_cd", APP_EXCEPTION, "_ui", "_ug", "_in", "_au", "_cmp", "_err", "_f", "_v", "_iap", "_nd", "_nf", "_no", "_nr", "_ou", "_s", "_e", "_xa", "_xu", "_aq", "_aa", "_ai", "_ac", AD_REWARD, "_vs", "_ep"};

        private Event() {
        }

        public static String zzix(String str) {
            return zzcno.zza(str, zzjew, zzjex);
        }
    }

    @Hide
    @KeepForSdk
    public interface EventInterceptor {
        @WorkerThread
        @KeepForSdk
        void interceptEvent(String str, String str2, Bundle bundle, long j);
    }

    @Hide
    @KeepForSdk
    public interface OnEventListener {
        @WorkerThread
        @KeepForSdk
        void onEvent(String str, String str2, Bundle bundle, long j);
    }

    @Hide
    @KeepForSdk
    public static final class Param extends com.google.firebase.analytics.FirebaseAnalytics.Param {
        @Hide
        @KeepForSdk
        public static final String FATAL = "fatal";
        @Hide
        @KeepForSdk
        public static final String TIMESTAMP = "timestamp";
        @Hide
        @KeepForSdk
        public static final String TYPE = "type";
        @Hide
        public static final String[] zzjey = new String[]{"firebase_conversion", "engagement_time_msec", "exposure_time", "ad_event_id", "ad_unit_id", "firebase_error", "firebase_error_value", "firebase_error_length", "firebase_event_origin", "firebase_screen", "firebase_screen_class", "firebase_screen_id", "firebase_previous_screen", "firebase_previous_class", "firebase_previous_id", "message_device_time", "message_id", "message_name", "message_time", "previous_app_version", "previous_os_version", "topic", "update_with_analytics", "previous_first_open_count", "system_app", "system_app_update", "previous_install_count", "ga_event_id", "ga_extra_params_ct", "ga_group_name", "ga_list_length", "ga_index", "ga_event_name"};
        @Hide
        public static final String[] zzjez = new String[]{"_c", "_et", "_xt", "_aeid", "_ai", "_err", "_ev", "_el", "_o", "_sn", "_sc", "_si", "_pn", "_pc", "_pi", "_ndt", "_nmid", "_nmn", "_nmt", "_pv", "_po", "_nt", "_uwa", "_pfo", "_sys", "_sysu", "_pin", "_eid", "_epc", "_gn", "_ll", "_i", "_en"};

        private Param() {
        }

        public static String zzix(String str) {
            return zzcno.zza(str, zzjey, zzjez);
        }
    }

    @Hide
    @KeepForSdk
    public static final class UserProperty extends com.google.firebase.analytics.FirebaseAnalytics.UserProperty {
        @Hide
        @KeepForSdk
        public static final String FIREBASE_LAST_NOTIFICATION = "_ln";
        @Hide
        public static final String[] zzjfa = new String[]{"firebase_last_notification", "first_open_time", "first_visit_time", "last_deep_link_referrer", "user_id", "first_open_after_install", "lifetime_user_engagement"};
        @Hide
        public static final String[] zzjfb = new String[]{FIREBASE_LAST_NOTIFICATION, "_fot", "_fvt", "_ldl", "_id", "_fi", "_lte"};

        private UserProperty() {
        }

        public static String zzix(String str) {
            return zzcno.zza(str, zzjfa, zzjfb);
        }
    }

    @Hide
    public interface zza {
        @MainThread
        boolean zza(zzclz zzclz, zzclz zzclz2);
    }

    @Hide
    public AppMeasurement(zzckj zzckj) {
        zzbq.checkNotNull(zzckj);
        this.zzjev = zzckj;
    }

    @Keep
    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK"})
    @Deprecated
    public static AppMeasurement getInstance(Context context) {
        return zzckj.zzed(context).zzbbq();
    }

    @Keep
    @Hide
    public void beginAdUnitExposure(@Size(min = 1) @NonNull String str) {
        this.zzjev.zzayb().beginAdUnitExposure(str);
    }

    @Keep
    @Hide
    protected void clearConditionalUserProperty(@Size(max = 24, min = 1) @NonNull String str, @Nullable String str2, @Nullable Bundle bundle) {
        this.zzjev.zzayd().clearConditionalUserProperty(str, str2, bundle);
    }

    @Keep
    @Hide
    protected void clearConditionalUserPropertyAs(@Size(min = 1) @NonNull String str, @Size(max = 24, min = 1) @NonNull String str2, @Nullable String str3, @Nullable Bundle bundle) {
        this.zzjev.zzayd().clearConditionalUserPropertyAs(str, str2, str3, bundle);
    }

    @Keep
    @Hide
    public void endAdUnitExposure(@Size(min = 1) @NonNull String str) {
        this.zzjev.zzayb().endAdUnitExposure(str);
    }

    @Keep
    @Hide
    public long generateEventId() {
        return this.zzjev.zzayl().zzbcq();
    }

    @Keep
    @Nullable
    @Hide
    public String getAppInstanceId() {
        return this.zzjev.zzayd().zzbbf();
    }

    @Keep
    @WorkerThread
    @Hide
    protected List<ConditionalUserProperty> getConditionalUserProperties(@Nullable String str, @Nullable @Size(max = 23, min = 1) String str2) {
        return this.zzjev.zzayd().getConditionalUserProperties(str, str2);
    }

    @Keep
    @WorkerThread
    @Hide
    protected List<ConditionalUserProperty> getConditionalUserPropertiesAs(@Size(min = 1) @NonNull String str, @Nullable String str2, @Nullable @Size(max = 23, min = 1) String str3) {
        return this.zzjev.zzayd().getConditionalUserPropertiesAs(str, str2, str3);
    }

    @Keep
    @Nullable
    @Hide
    public String getCurrentScreenClass() {
        zzclz zzbch = this.zzjev.zzayh().zzbch();
        return zzbch != null ? zzbch.zzjqk : null;
    }

    @Keep
    @Nullable
    @Hide
    public String getCurrentScreenName() {
        zzclz zzbch = this.zzjev.zzayh().zzbch();
        return zzbch != null ? zzbch.zzjqj : null;
    }

    @Keep
    @Nullable
    @Hide
    public String getGmpAppId() {
        try {
            return zzbz.zzakq();
        } catch (IllegalStateException e) {
            this.zzjev.zzayp().zzbau().zzj("getGoogleAppId failed with exception", e);
            return null;
        }
    }

    @Keep
    @WorkerThread
    @Hide
    protected int getMaxUserProperties(@Size(min = 1) @NonNull String str) {
        this.zzjev.zzayd();
        zzbq.zzgv(str);
        return 25;
    }

    @Keep
    @WorkerThread
    @Hide
    protected Map<String, Object> getUserProperties(@Nullable String str, @Nullable @Size(max = 24, min = 1) String str2, boolean z) {
        return this.zzjev.zzayd().getUserProperties(str, str2, z);
    }

    @WorkerThread
    @Hide
    @KeepForSdk
    public Map<String, Object> getUserProperties(boolean z) {
        List<zzcnl> zzbv = this.zzjev.zzayd().zzbv(z);
        Map<String, Object> arrayMap = new ArrayMap(zzbv.size());
        for (zzcnl zzcnl : zzbv) {
            arrayMap.put(zzcnl.name, zzcnl.getValue());
        }
        return arrayMap;
    }

    @Keep
    @WorkerThread
    @Hide
    protected Map<String, Object> getUserPropertiesAs(@Size(min = 1) @NonNull String str, @Nullable String str2, @Nullable @Size(max = 23, min = 1) String str3, boolean z) {
        return this.zzjev.zzayd().getUserPropertiesAs(str, str2, str3, z);
    }

    @Hide
    @KeepForSdk
    public void logEvent(@Size(max = 40, min = 1) @NonNull String str, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.zzjev.zzayd().zza(SettingsJsonConstants.APP_KEY, str, bundle, true);
    }

    @Keep
    @Hide
    public void logEventInternal(String str, String str2, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.zzjev.zzayd().zzd(str, str2, bundle);
    }

    @Hide
    @KeepForSdk
    public void logEventInternalNoInterceptor(String str, String str2, Bundle bundle, long j) {
        this.zzjev.zzayd().zza(str, str2, bundle == null ? new Bundle() : bundle, j);
    }

    @Hide
    @KeepForSdk
    public void registerOnMeasurementEventListener(OnEventListener onEventListener) {
        this.zzjev.zzayd().registerOnMeasurementEventListener(onEventListener);
    }

    @Keep
    @Hide
    public void registerOnScreenChangeCallback(@NonNull zza zza) {
        this.zzjev.zzayh().registerOnScreenChangeCallback(zza);
    }

    @Keep
    @Hide
    protected void setConditionalUserProperty(@NonNull ConditionalUserProperty conditionalUserProperty) {
        this.zzjev.zzayd().setConditionalUserProperty(conditionalUserProperty);
    }

    @Keep
    @Hide
    protected void setConditionalUserPropertyAs(@NonNull ConditionalUserProperty conditionalUserProperty) {
        this.zzjev.zzayd().setConditionalUserPropertyAs(conditionalUserProperty);
    }

    @WorkerThread
    @Hide
    @KeepForSdk
    public void setEventInterceptor(EventInterceptor eventInterceptor) {
        this.zzjev.zzayd().setEventInterceptor(eventInterceptor);
    }

    @Deprecated
    public void setMeasurementEnabled(boolean z) {
        this.zzjev.zzayd().setMeasurementEnabled(z);
    }

    @Hide
    @KeepForSdk
    public void setMinimumSessionDuration(long j) {
        this.zzjev.zzayd().setMinimumSessionDuration(j);
    }

    @Hide
    @KeepForSdk
    public void setSessionTimeoutDuration(long j) {
        this.zzjev.zzayd().setSessionTimeoutDuration(j);
    }

    @Hide
    @KeepForSdk
    public void setUserProperty(@Size(max = 24, min = 1) @NonNull String str, @Nullable @Size(max = 36) String str2) {
        int zzkj = this.zzjev.zzayl().zzkj(str);
        if (zzkj != 0) {
            this.zzjev.zzayl();
            this.zzjev.zzayl().zza(zzkj, "_ev", zzcno.zza(str, 24, true), str != null ? str.length() : 0);
            return;
        }
        setUserPropertyInternal(SettingsJsonConstants.APP_KEY, str, str2);
    }

    @Hide
    @KeepForSdk
    public void setUserPropertyInternal(String str, String str2, Object obj) {
        this.zzjev.zzayd().zzb(str, str2, obj);
    }

    @Hide
    @KeepForSdk
    public void unregisterOnMeasurementEventListener(OnEventListener onEventListener) {
        this.zzjev.zzayd().unregisterOnMeasurementEventListener(onEventListener);
    }

    @Keep
    @Hide
    public void unregisterOnScreenChangeCallback(@NonNull zza zza) {
        this.zzjev.zzayh().unregisterOnScreenChangeCallback(zza);
    }
}
