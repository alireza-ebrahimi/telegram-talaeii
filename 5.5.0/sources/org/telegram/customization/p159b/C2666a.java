package org.telegram.customization.p159b;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.os.StrictMode.VmPolicy;
import android.support.p009b.C0024a;
import android.util.Log;
import com.crashlytics.android.C1364a;
import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.p077f.p078a.p079a.p080a.p083b.C1535c;
import com.p077f.p078a.p086b.C1570c;
import com.p077f.p078a.p086b.C1570c.C1566a;
import com.p077f.p078a.p086b.C1575d;
import com.p077f.p078a.p086b.C1584e.C1577a;
import com.p077f.p078a.p086b.p087a.C1555g;
import com.p077f.p078a.p095c.C1602c;
import com.p118i.p119a.C2012i;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.fetch.C2748c;
import org.telegram.customization.fetch.C2748c.C2746a;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.service.C2827a;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.p150b.C2491a;
import p033b.p034a.p035a.p036a.C1230c;
import utils.p178a.C3791b;

/* renamed from: org.telegram.customization.b.a */
public class C2666a extends Application implements C2497d {
    public static boolean KEEP_ORIGINAL_FILENAME;
    public static boolean SHOW_ANDROID_EMOJI;
    public static boolean USE_DEVICE_FONT;
    public static C2491a databaseHandler;
    public static C2748c fetch = null;
    private static C1570c options;
    public static C2012i thinDl;

    private void CopyAssets() {
        AssetManager assets = getAssets();
        String[] strArr = null;
        try {
            strArr = assets.list(DataBufferSafeParcelable.DATA_FIELD);
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        for (String str : r0) {
            if (str.contentEquals("white_bg.jpg")) {
                System.out.println("File name => " + str);
                try {
                    InputStream open = assets.open("data/" + str);
                    OutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/" + str);
                    copyFile(open, fileOutputStream);
                    open.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e2) {
                    Log.e("tag", e2.getMessage());
                }
            }
        }
    }

    private void copyFile(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
    }

    public static C2491a getDatabaseHandler() {
        if (databaseHandler == null) {
            try {
                databaseHandler = new C2491a(ApplicationLoader.applicationContext);
            } catch (Exception e) {
            }
        }
        return databaseHandler;
    }

    public static C1570c getImageOptions() {
        if (options == null) {
            options = new C1566a().m7736a(true).m7741c(true).m7739b(true).m7740c(17170445).m7737a();
        }
        return options;
    }

    public static void initUniversaImageLoader(Context context) {
        C1577a c1577a = new C1577a(context);
        c1577a.m7840a(3);
        c1577a.m7839a();
        c1577a.m7841a(new C1535c());
        c1577a.m7844b(52428800);
        c1577a.m7842a(C1555g.LIFO);
        c1577a.m7843b();
        C1602c.m7934a();
        C1575d.m7807a().m7809a(c1577a.m7845c());
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        C0024a.m77a((Context) this);
    }

    public void onCreate() {
        super.onCreate();
        C1230c.m6404a((Context) this, new C1364a());
        new C2746a(getApplicationContext()).m12729a((int) Callback.DEFAULT_DRAG_ANIMATION_DURATION).m12730a(false).m12732b(2).m12731a();
        fetch = C2748c.m12735b(getApplicationContext());
        fetch.m12739a();
        thinDl = new C2012i();
        databaseHandler = new C2491a(getApplicationContext());
        C2818c.m13090a(getApplicationContext());
        C2666a.initUniversaImageLoader(this);
        getString(R.string.SID);
        getString(R.string.ANALYTIC_ID);
        if (C3791b.m13907K(getApplicationContext()) == 0) {
            C3791b.m14038r(getApplicationContext(), System.currentTimeMillis());
        }
        C2827a.m13162a(getApplicationContext());
        int i = C3791b.m13994i((Context) this);
        if (i == 0 || i != BuildConfig.VERSION_CODE) {
            C3791b.m13976f(getApplicationContext(), TtmlNode.ANONYMOUS_REGION_ID);
            C3791b.m13930a(getApplicationContext(), TtmlNode.ANONYMOUS_REGION_ID);
            C3791b.m13936a(getApplicationContext(), false);
            C3791b.m14050u(getApplicationContext(), System.currentTimeMillis());
            C3791b.m13947b(getApplicationContext(), new ArrayList());
            try {
                if (!(C3791b.m13915S(ApplicationLoader.applicationContext) == null || BuildConfig.FLAVOR.contentEquals("hotgram"))) {
                    if (BuildConfig.FLAVOR.contentEquals(BuildConfig.FLAVOR)) {
                    }
                }
            } catch (Exception e) {
            }
        }
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("plusconfig", 0);
        SHOW_ANDROID_EMOJI = sharedPreferences.getBoolean("showAndroidEmoji", false);
        USE_DEVICE_FONT = sharedPreferences.getBoolean("useDeviceFont", false);
        KEEP_ORIGINAL_FILENAME = sharedPreferences.getBoolean("keepOriginalFilename", false);
        if (!new File(Environment.getExternalStorageDirectory().toString() + "/white_bg.png").exists()) {
            CopyAssets();
        }
        Log.i("alireza", "token FCM" + C3791b.au(ApplicationLoader.applicationContext));
    }

    public void onResult(Object obj, int i) {
    }
}
