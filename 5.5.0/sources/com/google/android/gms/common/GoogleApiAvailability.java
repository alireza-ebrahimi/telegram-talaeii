package com.google.android.gms.common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.GuardedBy;
import android.support.v4.app.C0353t;
import android.support.v4.app.al.C0265c;
import android.support.v4.app.al.C0266d;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import com.google.android.gms.base.C1787R;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.GooglePlayServicesUpdatedReceiver;
import com.google.android.gms.common.api.internal.GooglePlayServicesUpdatedReceiver.Callback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zzbt;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.api.internal.zzk;
import com.google.android.gms.common.internal.ConnectionErrorMessages;
import com.google.android.gms.common.internal.DialogRedirect;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class GoogleApiAvailability extends GoogleApiAvailabilityLight {
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final Object mLock = new Object();
    private static final GoogleApiAvailability zzas = new GoogleApiAvailability();
    @GuardedBy("mLock")
    private String zzat;

    @SuppressLint({"HandlerLeak"})
    private class zza extends Handler {
        private final Context zzau;
        private final /* synthetic */ GoogleApiAvailability zzav;

        public zza(GoogleApiAvailability googleApiAvailability, Context context) {
            this.zzav = googleApiAvailability;
            super(Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper());
            this.zzau = context.getApplicationContext();
        }

        public final void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    int isGooglePlayServicesAvailable = this.zzav.isGooglePlayServicesAvailable(this.zzau);
                    if (this.zzav.isUserResolvableError(isGooglePlayServicesAvailable)) {
                        this.zzav.showErrorNotification(this.zzau, isGooglePlayServicesAvailable);
                        return;
                    }
                    return;
                default:
                    Log.w("GoogleApiAvailability", "Don't know how to handle this message: " + message.what);
                    return;
            }
        }
    }

    GoogleApiAvailability() {
    }

    public static GoogleApiAvailability getInstance() {
        return zzas;
    }

    static Dialog zza(Context context, int i, DialogRedirect dialogRedirect, OnCancelListener onCancelListener) {
        Builder builder = null;
        if (i == 0) {
            return null;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843529, typedValue, true);
        if ("Theme.Dialog.Alert".equals(context.getResources().getResourceEntryName(typedValue.resourceId))) {
            builder = new Builder(context, 5);
        }
        if (builder == null) {
            builder = new Builder(context);
        }
        builder.setMessage(ConnectionErrorMessages.getErrorMessage(context, i));
        if (onCancelListener != null) {
            builder.setOnCancelListener(onCancelListener);
        }
        CharSequence errorDialogButtonMessage = ConnectionErrorMessages.getErrorDialogButtonMessage(context, i);
        if (errorDialogButtonMessage != null) {
            builder.setPositiveButton(errorDialogButtonMessage, dialogRedirect);
        }
        errorDialogButtonMessage = ConnectionErrorMessages.getErrorTitle(context, i);
        if (errorDialogButtonMessage != null) {
            builder.setTitle(errorDialogButtonMessage);
        }
        return builder.create();
    }

    @TargetApi(26)
    private final String zza(Context context, NotificationManager notificationManager) {
        Preconditions.checkState(PlatformVersion.isAtLeastO());
        String zzb = zzb();
        if (zzb == null) {
            zzb = "com.google.android.gms.availability";
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(zzb);
            CharSequence defaultNotificationChannelName = ConnectionErrorMessages.getDefaultNotificationChannelName(context);
            if (notificationChannel == null) {
                notificationManager.createNotificationChannel(new NotificationChannel(zzb, defaultNotificationChannelName, 4));
            } else if (!defaultNotificationChannelName.equals(notificationChannel.getName())) {
                notificationChannel.setName(defaultNotificationChannelName);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        return zzb;
    }

    static void zza(Activity activity, Dialog dialog, String str, OnCancelListener onCancelListener) {
        if (activity instanceof C0353t) {
            SupportErrorDialogFragment.newInstance(dialog, onCancelListener).show(((C0353t) activity).m1547e(), str);
            return;
        }
        ErrorDialogFragment.newInstance(dialog, onCancelListener).show(activity.getFragmentManager(), str);
    }

    @TargetApi(20)
    private final void zza(Context context, int i, String str, PendingIntent pendingIntent) {
        if (i == 18) {
            zza(context);
        } else if (pendingIntent != null) {
            Notification build;
            int i2;
            CharSequence errorNotificationTitle = ConnectionErrorMessages.getErrorNotificationTitle(context, i);
            CharSequence errorNotificationMessage = ConnectionErrorMessages.getErrorNotificationMessage(context, i);
            Resources resources = context.getResources();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            if (DeviceProperties.isWearable(context)) {
                Preconditions.checkState(PlatformVersion.isAtLeastKitKatWatch());
                Notification.Builder style = new Notification.Builder(context).setSmallIcon(context.getApplicationInfo().icon).setPriority(2).setAutoCancel(true).setContentTitle(errorNotificationTitle).setStyle(new BigTextStyle().bigText(errorNotificationMessage));
                if (DeviceProperties.isWearableWithoutPlayStore(context)) {
                    style.addAction(C1787R.drawable.common_full_open_on_phone, resources.getString(C1787R.string.common_open_on_phone), pendingIntent);
                } else {
                    style.setContentIntent(pendingIntent);
                }
                if (PlatformVersion.isAtLeastO() && PlatformVersion.isAtLeastO()) {
                    style.setChannelId(zza(context, notificationManager));
                }
                build = style.build();
            } else {
                C0266d a = new C0266d(context).m1227a(17301642).m1249c(resources.getString(C1787R.string.common_google_play_services_notification_ticker)).m1231a(System.currentTimeMillis()).m1240a(true).m1232a(pendingIntent).m1238a(errorNotificationTitle).m1245b(errorNotificationMessage).m1247b(true).m1237a(new C0265c().m1223a(errorNotificationMessage));
                if (PlatformVersion.isAtLeastO() && PlatformVersion.isAtLeastO()) {
                    a.setChannelId(zza(context, notificationManager));
                }
                build = a.m1242b();
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                    i2 = 10436;
                    GooglePlayServicesUtilLight.zzbt.set(false);
                    break;
                default:
                    i2 = 39789;
                    break;
            }
            if (str == null) {
                notificationManager.notify(i2, build);
            } else {
                notificationManager.notify(str, i2, build);
            }
        } else if (i == 6) {
            Log.w("GoogleApiAvailability", "Missing resolution for ConnectionResult.RESOLUTION_REQUIRED. Call GoogleApiAvailability#showErrorNotification(Context, ConnectionResult) instead.");
        }
    }

    private final String zzb() {
        String str;
        synchronized (mLock) {
            str = this.zzat;
        }
        return str;
    }

    public Task<Void> checkApiAvailability(GoogleApi<?> googleApi, GoogleApi<?>... googleApiArr) {
        return checkApiAvailabilityAndPackages(googleApi, googleApiArr).continueWith(new zza(this));
    }

    public Task<Map<zzh<?>, String>> checkApiAvailabilityAndPackages(GoogleApi<?> googleApi, GoogleApi<?>... googleApiArr) {
        Preconditions.checkNotNull(googleApi, "Requested API must not be null.");
        for (Object checkNotNull : googleApiArr) {
            Preconditions.checkNotNull(checkNotNull, "Requested API must not be null.");
        }
        Iterable arrayList = new ArrayList(googleApiArr.length + 1);
        arrayList.add(googleApi);
        arrayList.addAll(Arrays.asList(googleApiArr));
        return GoogleApiManager.zzbf().zza(arrayList);
    }

    public int getApkVersion(Context context) {
        return super.getApkVersion(context);
    }

    public int getClientVersion(Context context) {
        return super.getClientVersion(context);
    }

    public Dialog getErrorDialog(Activity activity, int i, int i2) {
        return getErrorDialog(activity, i, i2, null);
    }

    public Dialog getErrorDialog(Activity activity, int i, int i2, OnCancelListener onCancelListener) {
        return zza((Context) activity, i, DialogRedirect.getInstance(activity, getErrorResolutionIntent(activity, i, "d"), i2), onCancelListener);
    }

    @Deprecated
    public Intent getErrorResolutionIntent(int i) {
        return super.getErrorResolutionIntent(i);
    }

    public Intent getErrorResolutionIntent(Context context, int i, String str) {
        return super.getErrorResolutionIntent(context, i, str);
    }

    public PendingIntent getErrorResolutionPendingIntent(Context context, int i, int i2) {
        return super.getErrorResolutionPendingIntent(context, i, i2);
    }

    public PendingIntent getErrorResolutionPendingIntent(Context context, int i, int i2, String str) {
        return super.getErrorResolutionPendingIntent(context, i, i2, str);
    }

    public PendingIntent getErrorResolutionPendingIntent(Context context, ConnectionResult connectionResult) {
        return connectionResult.hasResolution() ? connectionResult.getResolution() : getErrorResolutionPendingIntent(context, connectionResult.getErrorCode(), 0);
    }

    public final String getErrorString(int i) {
        return super.getErrorString(i);
    }

    public int isGooglePlayServicesAvailable(Context context) {
        return super.isGooglePlayServicesAvailable(context);
    }

    public int isGooglePlayServicesAvailable(Context context, int i) {
        return super.isGooglePlayServicesAvailable(context, i);
    }

    public boolean isPlayServicesPossiblyUpdating(Context context, int i) {
        return super.isPlayServicesPossiblyUpdating(context, i);
    }

    public boolean isPlayStorePossiblyUpdating(Context context, int i) {
        return super.isPlayStorePossiblyUpdating(context, i);
    }

    public final boolean isUserResolvableError(int i) {
        return super.isUserResolvableError(i);
    }

    public Task<Void> makeGooglePlayServicesAvailable(Activity activity) {
        Preconditions.checkMainThread("makeGooglePlayServicesAvailable must be called from the main thread");
        int isGooglePlayServicesAvailable = isGooglePlayServicesAvailable(activity);
        if (isGooglePlayServicesAvailable == 0) {
            return Tasks.forResult(null);
        }
        zzk zzd = zzbt.zzd(activity);
        zzd.zzb(new ConnectionResult(isGooglePlayServicesAvailable, null), 0);
        return zzd.getTask();
    }

    public GooglePlayServicesUpdatedReceiver registerCallbackOnUpdate(Context context, Callback callback) {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        BroadcastReceiver googlePlayServicesUpdatedReceiver = new GooglePlayServicesUpdatedReceiver(callback);
        context.registerReceiver(googlePlayServicesUpdatedReceiver, intentFilter);
        googlePlayServicesUpdatedReceiver.zzc(context);
        if (isUninstalledAppPossiblyUpdating(context, "com.google.android.gms")) {
            return googlePlayServicesUpdatedReceiver;
        }
        callback.zzv();
        googlePlayServicesUpdatedReceiver.unregister();
        return null;
    }

    @TargetApi(26)
    public void setDefaultNotificationChannelId(Context context, String str) {
        if (PlatformVersion.isAtLeastO()) {
            Preconditions.checkNotNull(((NotificationManager) context.getSystemService("notification")).getNotificationChannel(str));
        }
        synchronized (mLock) {
            this.zzat = str;
        }
    }

    public boolean showErrorDialogFragment(Activity activity, int i, int i2) {
        return showErrorDialogFragment(activity, i, i2, null);
    }

    public boolean showErrorDialogFragment(Activity activity, int i, int i2, OnCancelListener onCancelListener) {
        Dialog errorDialog = getErrorDialog(activity, i, i2, onCancelListener);
        if (errorDialog == null) {
            return false;
        }
        zza(activity, errorDialog, GooglePlayServicesUtil.GMS_ERROR_DIALOG, onCancelListener);
        return true;
    }

    public boolean showErrorDialogFragment(Activity activity, LifecycleFragment lifecycleFragment, int i, int i2, OnCancelListener onCancelListener) {
        Dialog zza = zza((Context) activity, i, DialogRedirect.getInstance(lifecycleFragment, getErrorResolutionIntent(activity, i, "d"), i2), onCancelListener);
        if (zza == null) {
            return false;
        }
        zza(activity, zza, GooglePlayServicesUtil.GMS_ERROR_DIALOG, onCancelListener);
        return true;
    }

    public void showErrorNotification(Context context, int i) {
        showErrorNotification(context, i, null);
    }

    public void showErrorNotification(Context context, int i, String str) {
        zza(context, i, str, getErrorResolutionPendingIntent(context, i, 0, "n"));
    }

    public void showErrorNotification(Context context, ConnectionResult connectionResult) {
        zza(context, connectionResult.getErrorCode(), null, getErrorResolutionPendingIntent(context, connectionResult));
    }

    public Dialog showUpdatingDialog(Activity activity, OnCancelListener onCancelListener) {
        View progressBar = new ProgressBar(activity, null, 16842874);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(0);
        Builder builder = new Builder(activity);
        builder.setView(progressBar);
        builder.setMessage(ConnectionErrorMessages.getErrorMessage(activity, 18));
        builder.setPositiveButton(TtmlNode.ANONYMOUS_REGION_ID, null);
        Dialog create = builder.create();
        zza(activity, create, "GooglePlayServicesUpdatingDialog", onCancelListener);
        return create;
    }

    public boolean showWrappedErrorNotification(Context context, ConnectionResult connectionResult, int i) {
        PendingIntent errorResolutionPendingIntent = getErrorResolutionPendingIntent(context, connectionResult);
        if (errorResolutionPendingIntent == null) {
            return false;
        }
        zza(context, connectionResult.getErrorCode(), null, GoogleApiActivity.zza(context, errorResolutionPendingIntent, i));
        return true;
    }

    final void zza(Context context) {
        new zza(this, context).sendEmptyMessageDelayed(1, 120000);
    }
}
