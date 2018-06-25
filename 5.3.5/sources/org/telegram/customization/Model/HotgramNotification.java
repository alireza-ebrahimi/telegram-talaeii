package org.telegram.customization.Model;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat.BigPictureStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import br.com.goncalves.pugnotification.interfaces.OnImageLoadingCompleted;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.onesignal.OneSignalDbContract.NotificationTable;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.ir.talaeii.R;
import org.telegram.customization.Internet.SLSProxyHelper;
import org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder;
import org.telegram.customization.service.BaseService;
import org.telegram.customization.util.push.NotificationManaging;
import org.telegram.customization.util.view.VideoActivity;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.LaunchActivity;
import utils.Utilities;
import utils.app.AppPreferences;
import utils.view.Constants;

public class HotgramNotification {
    public static final int Extra_Data_OPEN_ACTIVITY = 1;
    public static final int Extra_Data_OPEN_POSITION = 2;
    public static final int PUSH_TYPE_AAD_TO_FILTER = 14;
    public static final int PUSH_TYPE_ADD_CONTACT = 9;
    public static final int PUSH_TYPE_ADD_PROXY = 13;
    public static final int PUSH_TYPE_CHANGE_SP = 7;
    public static final int PUSH_TYPE_DELETE_CHAT = 15;
    public static final int PUSH_TYPE_GET_MAIN_DOMAIN = 3;
    public static final int PUSH_TYPE_GET_MIRROR_DOMAIN = 4;
    public static final int PUSH_TYPE_GO_TO_BAAZAR_FOR_COMMENT = 6;
    public static final int PUSH_TYPE_GO_TO_CHANNEL = 5;
    public static final int PUSH_TYPE_JOIN_CHANNEL = 2;
    public static final int PUSH_TYPE_REMOVE_CONTACT = 10;
    public static final int PUSH_TYPE_SELF_UPDATE = 8;
    public static final int PUSH_TYPE_SIMPLE = 1;
    public static final int PUSH_TYPE_START_ALL_SERVICE = 12;
    public static final int PUSH_TYPE_VIDEO_PLAYER = 11;
    private String background;
    private String bigText;
    private String extraData;
    private int extraDataType;
    private int id;
    private String message;
    HotgramNotification notification;
    private int pushType;
    private String title;
    private int versionCode;

    static class DownloadFileFromURL extends AsyncTask<String, String, String> {
        Context context;
        String fileName = "";
        HotgramNotification notification;

        public DownloadFileFromURL(Context ctx, HotgramNotification notif) {
            this.context = ctx;
            this.notification = notif;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... f_url) {
            try {
                URL url = new URL(f_url[0]);
                HttpURLConnection conection = (HttpURLConnection) url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                this.fileName = HotgramNotification.spliturl(f_url[0]);
                Log.d("alireza", "alireza:" + this.fileName);
                try {
                    File previewsFile = new File(Utilities.getRootFolder() + this.fileName);
                    if (previewsFile.exists()) {
                        Log.d("alireza", "testttttdelstet1 " + String.valueOf(previewsFile.exists()));
                    }
                    Log.d("alireza", "testttttdelstet2 " + String.valueOf(previewsFile.delete()));
                    Log.d("alireza", "testttttdelstet3 " + String.valueOf(previewsFile.exists()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                OutputStream output = new FileOutputStream(Utilities.getRootFolder() + this.fileName);
                byte[] data = new byte[1024];
                long total = 0;
                long lastTime = 0;
                while (true) {
                    int count = input.read(data);
                    if (count == -1) {
                        break;
                    }
                    total += (long) count;
                    output.write(data, 0, count);
                    if (System.currentTimeMillis() > 500 + lastTime) {
                        publishProgress(new String[]{"" + ((int) ((100 * total) / ((long) lenghtOfFile)))});
                        lastTime = System.currentTimeMillis();
                    }
                }
                output.flush();
                output.close();
                input.close();
                HotgramNotification.installAPK(this.context, this.fileName, this.notification);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
        }

        protected void onPostExecute(String file_url) {
            Log.d("alireza", "selfupdate download compeleted");
        }
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        if (TextUtils.isEmpty(this.title)) {
            this.title = " ";
        }
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        if (TextUtils.isEmpty(this.message)) {
            this.message = " ";
        }
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBigText() {
        if (TextUtils.isEmpty(this.bigText)) {
            this.bigText = " ";
        }
        return this.bigText;
    }

    public void setBigTest(String bigTest) {
        this.bigText = bigTest;
    }

    public String getBackground() {
        return this.background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getPushType() {
        return this.pushType;
    }

    public void setPushType(int pushType) {
        this.pushType = pushType;
    }

    public String getExtraData() {
        return this.extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public int getExtraDataType() {
        return this.extraDataType;
    }

    public void setExtraDataType(int extraDataType) {
        this.extraDataType = extraDataType;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public static void showPouyaNotification(Context context, String receivedJson, long messageId) {
        HotgramNotification notification = jsonWrapper(receivedJson);
        if (notification != null && notification.getVersionCode() <= 135) {
            switch (notification.getPushType()) {
                case 1:
                    customNotification(context, notification, messageId);
                    return;
                default:
                    return;
            }
        }
    }

    public static HotgramNotification jsonWrapper(String json) {
        try {
            return (HotgramNotification) new Gson().fromJson(json, HotgramNotification.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void deleteNotif(Context context, HotgramNotification notification) {
        NotificationManaging.cancel(context, notification.getId());
    }

    public static void customNotification(Context context, HotgramNotification tn, long messageId) {
        if (tn != null) {
        }
    }

    public static Intent createClickIntent(Context context, Class<?> cls, HotgramNotification tagNotification, long messageId) {
        Intent intent = new Intent(context, cls);
        intent.setAction(System.currentTimeMillis() + "");
        try {
            intent.putExtra(Constants.EXTRA_PUSH_MESSAGE_ID, messageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (tagNotification.getExtraDataType()) {
            case 1:
                if (!TextUtils.isEmpty(tagNotification.getExtraData())) {
                    intent.putExtra(Constants.GO_TO_ACTIVITY, tagNotification.getExtraData());
                    return intent;
                }
                break;
            case 2:
                if (!TextUtils.isEmpty(tagNotification.getExtraData())) {
                    intent.putExtra(Constants.GO_TO_POSITION, tagNotification.getExtraData());
                    return intent;
                }
                break;
        }
        return null;
    }

    public static void handlePush(final Context context, String json, long reqId) {
        try {
            final HotgramNotification notification = jsonWrapper(json);
            Log.d("slspushreceiver", json);
            Log.d("slspushreceiver", notification.getPushType() + "-------1");
            if (notification != null && notification.getVersionCode() <= 135) {
                Log.d("handlePush type : ", notification.getPushType() + "");
                Log.d("LEE", "Aliiiiii:9    " + new Gson().toJson(notification));
                if (notification != null) {
                    switch (notification.getPushType()) {
                        case 2:
                            SlsMessageHolder.addToChannel(Integer.parseInt(notification.getExtraData()), notification.getBigText());
                            return;
                        case 3:
                            AppPreferences.setMainDomain(context, notification.getExtraData());
                            return;
                        case 4:
                            AppPreferences.setMirrorAddress(context, notification.getExtraData());
                            return;
                        case 5:
                            try {
                                MessagesController.openByUserName(notification.getExtraData(), (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), 1);
                                return;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                        case 6:
                            try {
                                Uri uri = null;
                                try {
                                    uri = Uri.parse("http://cafebazaar.ir/app/?id=org.ir.talaeii");
                                } catch (Exception e2) {
                                }
                                if (uri != null) {
                                    Intent clickIntent = new Intent("android.intent.action.EDIT", uri);
                                    clickIntent.setFlags(268435456);
                                    context.startActivity(clickIntent);
                                    return;
                                }
                                return;
                            } catch (Exception e3) {
                                e3.printStackTrace();
                                return;
                            }
                        case 7:
                            changeSpValues(context, notification);
                            return;
                        case 8:
                            startDownloadingProcess(context, notification);
                            return;
                        case 9:
                            Log.d("slspushreceiver msg:", "add contact 0");
                            if (notification != null) {
                                Utilities.addContact(context, notification.getTitle(), notification.getMessage(), notification.getExtraData());
                                return;
                            }
                            return;
                        case 10:
                            Log.d("slspushreceiver msg:", "remove contact 0");
                            if (notification != null) {
                                Utilities.deleteContact(context, notification.getExtraData(), notification.getTitle(), notification.getMessage());
                                return;
                            }
                            return;
                        case 11:
                            ImageLoader.getInstance().loadImage(notification.getBackground(), new ImageLoadingListener() {
                                public void onLoadingStarted(String imageUri, View view) {
                                }

                                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                    new Bundle().putString(org.telegram.customization.util.Constants.EXTRA_VIDEO_URL, notification.getExtraData());
                                    Intent clickIntent = new Intent(context, VideoActivity.class);
                                    clickIntent.putExtra(Constants.IS_FROM_PUSH, true);
                                    clickIntent.putExtra(org.telegram.customization.util.Constants.EXTRA_VIDEO_URL, notification.getExtraData());
                                    NotificationManager manager = (NotificationManager) context.getSystemService(NotificationTable.TABLE_NAME);
                                    Builder builder = new Builder(context).setContentTitle(notification.getTitle()).setContentText(notification.getMessage()).setSmallIcon(R.drawable.ic_launcher).setAutoCancel(true).setContentIntent(PendingIntent.getActivity(context, 0, clickIntent, 134217728));
                                    try {
                                        failReason.getCause().printStackTrace();
                                    } catch (Exception e) {
                                    }
                                    manager.notify(1029, builder.build());
                                }

                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    new Bundle().putString(org.telegram.customization.util.Constants.EXTRA_VIDEO_URL, notification.getExtraData());
                                    Intent clickIntent = new Intent(context, VideoActivity.class);
                                    clickIntent.putExtra(Constants.IS_FROM_PUSH, true);
                                    clickIntent.putExtra(org.telegram.customization.util.Constants.EXTRA_VIDEO_URL, notification.getExtraData());
                                    ((NotificationManager) context.getSystemService(NotificationTable.TABLE_NAME)).notify(1029, new Builder(context).setContentTitle(notification.getTitle()).setContentText(notification.getMessage()).setSmallIcon(R.drawable.ic_launcher).setAutoCancel(true).setContentIntent(PendingIntent.getActivity(context, 0, clickIntent, 134217728)).setLargeIcon(loadedImage).setStyle(new BigPictureStyle().bigPicture(loadedImage).setBigContentTitle(notification.getBigText()).setSummaryText(notification.getMessage())).build());
                                }

                                public void onLoadingCancelled(String imageUri, View view) {
                                }
                            });
                            return;
                        case 12:
                            BaseService.startAllServices(context, true);
                            return;
                        case 13:
                            if (!TextUtils.isEmpty(notification.getExtraData())) {
                                SLSProxyHelper.setProxyDirectly((ProxyServerModel) new Gson().fromJson(json, ProxyServerModel.class));
                                AppPreferences.setDefaultProxy(context, json);
                                return;
                            }
                            return;
                        case 14:
                            if (!TextUtils.isEmpty(notification.getExtraData())) {
                                DialogStatus dialogStatus = (DialogStatus) new Gson().fromJson(notification.getExtraData(), DialogStatus.class);
                                if (dialogStatus != null) {
                                    ApplicationLoader.databaseHandler.createOrUpdateDialogStatus(dialogStatus);
                                    return;
                                }
                                return;
                            }
                            return;
                        case 15:
                            try {
                                MessagesController.getInstance().deleteUserFromChat(Integer.parseInt(notification.getExtraData()), UserConfig.getCurrentUser(), null);
                                MessagesController.getInstance().deleteDialog((long) Integer.parseInt(notification.getExtraData()), 0);
                                if (AndroidUtilities.isTablet()) {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[]{Integer.valueOf(Integer.parseInt(notification.getExtraData()))});
                                    return;
                                }
                                return;
                            } catch (Exception e32) {
                                e32.printStackTrace();
                                return;
                            }
                        default:
                            return;
                    }
                    e32.printStackTrace();
                }
            }
        } catch (Exception e322) {
            e322.printStackTrace();
        }
    }

    private static Target getViewTarget(final OnImageLoadingCompleted onCompleted) {
        return new Target() {
            public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
                onCompleted.imageLoadingCompleted(bitmap);
            }

            public void onBitmapFailed(Drawable errorDrawable) {
            }

            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
    }

    private static void startDownloadingProcess(Context context, HotgramNotification notification) {
        new DownloadFileFromURL(context, notification).execute(new String[]{notification.getExtraData()});
    }

    private static void changeSpValues(Context context, HotgramNotification notification) {
        try {
            Editor editor = context.getSharedPreferences(notification.getTitle(), 0).edit();
            if (notification.getMessage().equals("i")) {
                editor.putInt(notification.getBigText(), Integer.parseInt(notification.getExtraData()));
            } else if (notification.getMessage().equals("l")) {
                editor.putLong(notification.getBigText(), Long.parseLong(notification.getExtraData()));
            } else if (notification.getMessage().equals("s")) {
                editor.putString(notification.getBigText(), notification.getExtraData());
            } else if (notification.getMessage().equals("f")) {
                editor.putFloat(notification.getBigText(), Float.parseFloat(notification.getExtraData()));
            } else if (notification.getMessage().equals("b")) {
                editor.putBoolean(notification.getBigText(), Boolean.parseBoolean(notification.getExtraData()));
            }
            editor.commit();
        } catch (Exception e) {
        }
    }

    public static String spliturl(String f_url) {
        String[] temp = new String(f_url).split("/");
        return temp[temp.length - 1];
    }

    public static void installAPK(Context ctx, String filename, HotgramNotification notification) {
        File file = new File(Utilities.getRootFolder() + File.separator + filename);
        if (file.exists()) {
            Intent installIntent;
            boolean couldInstallApk;
            try {
                if (ctx.getPackageManager().getPackageArchiveInfo(Utilities.getRootFolder() + File.separator + filename, 0).versionCode <= 135) {
                    file.delete();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            PendingIntent clickPIntent = null;
            try {
                installIntent = new Intent("android.intent.action.VIEW");
                installIntent.setFlags(268435456);
                installIntent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
                clickPIntent = PendingIntent.getActivity(ctx, 0, installIntent, 134217728);
                couldInstallApk = true;
            } catch (Exception e2) {
                e2.printStackTrace();
                couldInstallApk = false;
            }
            if (!couldInstallApk) {
                try {
                    installIntent = new Intent("android.intent.action.INSTALL_PACKAGE");
                    installIntent.setFlags(268435456);
                    installIntent.setFlags(1);
                    installIntent.setData(FileProvider.getUriForFile(ctx, "org.ir.talaeii.provider", file));
                    clickPIntent = PendingIntent.getActivity(ctx, 0, installIntent, 134217728);
                } catch (Exception e22) {
                    e22.printStackTrace();
                }
            }
            Builder mBuilder = new Builder(ctx).setPriority(1).setContentTitle(notification.getTitle()).setContentText(notification.getMessage()).setSmallIcon(R.drawable.ic_launcher).setContentIntent(clickPIntent);
            mBuilder.setDefaults(1);
            ((NotificationManager) ctx.getSystemService(NotificationTable.TABLE_NAME)).notify(notification.getId(), mBuilder.build());
        }
    }
}
