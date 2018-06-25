package org.telegram.customization.util.push;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat.BigPictureStyle;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import android.view.View;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.onesignal.OneSignalDbContract.NotificationTable;
import org.ir.talaeii.R;
import org.telegram.customization.util.AppImageLoader;
import utils.Utilities;
import utils.view.Constants;

public class NotificationManaging {
    public static final int NOTIFICATION_ID = 1;
    private static NotificationManager mNotificationManager;
    Builder builder;

    public static synchronized void showNotification(Context context, String smallTile, String smallText, String bigTitle, String bigText, String backgroundImage, int notifId, Intent clickIntent, Intent dismissIntent, long pushRequestId) {
        synchronized (NotificationManaging.class) {
            int pri = (int) pushRequestId;
            if (clickIntent != null) {
                PendingIntent clickPIntent = PendingIntent.getActivity(context, pri, clickIntent, 134217728);
                dismissIntent = createDeleteIntent(context, pushRequestId);
                PendingIntent dismissPIntent = null;
                if (dismissIntent != null) {
                    dismissPIntent = PendingIntent.getBroadcast(context, pri, dismissIntent, 134217728);
                }
                showNotification(context, smallTile, smallText, bigTitle, bigText, backgroundImage, notifId, clickPIntent, dismissPIntent);
            }
        }
    }

    private static synchronized void showNotification(Context context, String smallTile, String smallText, String bigTitle, String bigText, String backgroundImage, int notifId, PendingIntent clickIntent, PendingIntent dismissIntent) {
        synchronized (NotificationManaging.class) {
            if (TextUtils.isEmpty(smallTile)) {
                smallTile = "";
            }
            if (TextUtils.isEmpty(smallText)) {
                smallText = "";
            }
            if (TextUtils.isEmpty(smallText)) {
                smallText = "";
            }
            if (TextUtils.isEmpty(bigTitle)) {
                bigTitle = "";
            }
            if (TextUtils.isEmpty(bigText)) {
                bigText = "";
            }
            final Builder mBuilder = new Builder(context).setPriority(1).setContentTitle(smallTile).setContentText(smallText).setAutoCancel(true);
            mBuilder.setDefaults(1);
            int icon = getNotifIcon(context);
            if (icon > 0) {
                mBuilder.setSmallIcon(icon);
            }
            mBuilder.setColor(context.getResources().getColor(R.color.colorPrimary));
            if (clickIntent != null) {
                mBuilder.setContentIntent(clickIntent);
            }
            if (dismissIntent != null) {
                mBuilder.setDeleteIntent(dismissIntent);
            }
            if (TextUtils.isEmpty(backgroundImage)) {
                BigTextStyle bigTextStyle = new BigTextStyle();
                bigTextStyle.setBigContentTitle(bigTitle);
                bigTextStyle.setSummaryText(smallText);
                bigTextStyle.bigText(bigText);
                mBuilder.setStyle(bigTextStyle);
                getmNotificationManager(context).notify(notifId, mBuilder.build());
            } else {
                final String finalBigTitle = bigTitle;
                final String finalSmallText = smallText;
                final String finalSmallTile = smallTile;
                final String finalBigText = bigText;
                final Context context2 = context;
                final int i = notifId;
                final PendingIntent pendingIntent = clickIntent;
                final PendingIntent pendingIntent2 = dismissIntent;
                ImageLoader.getInstance().loadImage(backgroundImage, AppImageLoader.getImageOptionForAdapter(), new ImageLoadingListener() {
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        NotificationManaging.getmNotificationManager(context2).notify(i, mBuilder.build());
                        NotificationManaging.showNotification(context2, finalSmallTile, finalSmallText, finalBigTitle, finalBigText, "", i, pendingIntent, pendingIntent2);
                    }

                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        BigPictureStyle bigPictureStyle = new BigPictureStyle();
                        bigPictureStyle.setSummaryText(finalSmallText);
                        bigPictureStyle.setBigContentTitle(finalBigTitle);
                        bigPictureStyle.bigPicture(loadedImage);
                        mBuilder.setStyle(bigPictureStyle);
                        NotificationManaging.getmNotificationManager(context2).notify(i, mBuilder.build());
                    }

                    public void onLoadingCancelled(String imageUri, View view) {
                        NotificationManaging.getmNotificationManager(context2).notify(i, mBuilder.build());
                    }
                });
            }
        }
    }

    private static int getNotifIcon(Context context) {
        try {
            for (PackageInfo pi : context.getPackageManager().getInstalledPackages(0)) {
                if (pi.packageName.equals(context.getPackageName())) {
                    int res1 = -1;
                    if (Utilities.getAndroidVersion() > 8) {
                        res1 = pi.applicationInfo.logo;
                    }
                    return res1 > 0 ? res1 : pi.applicationInfo.icon;
                }
            }
        } catch (Exception e) {
        }
        return -1;
    }

    private static int getCorrectIcon(int icon) {
        return 0;
    }

    private static Intent createDeleteIntent(Context context, long requestId) {
        Intent delete = new Intent(context, NotificationDismissReceiver.class);
        try {
            delete.setAction(Constants.INTENT_DISMISS_NOTIF);
            delete.putExtra(Constants.SLS_EXTRA_PUSH_RI, requestId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return delete;
    }

    private static NotificationManager getmNotificationManager(Context context) {
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) context.getSystemService(NotificationTable.TABLE_NAME);
        }
        return mNotificationManager;
    }

    public static void cancel(Context context, int ri) {
        getmNotificationManager(context).cancel(ri);
    }
}
