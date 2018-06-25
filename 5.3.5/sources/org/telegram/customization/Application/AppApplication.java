package org.telegram.customization.Application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.os.StrictMode.VmPolicy;
import android.support.multidex.MultiDex;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.C0658L;
import com.thin.downloadmanager.ThinDownloadManager;
import io.fabric.sdk.android.Fabric;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.SLSSQLite.DatabaseHandler;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.fetch.Fetch;
import org.telegram.customization.fetch.Fetch.Settings;
import org.telegram.customization.service.BaseService;
import org.telegram.messenger.ApplicationLoader;
import utils.app.AppPreferences;

public class AppApplication extends Application implements IResponseReceiver {
    public static boolean KEEP_ORIGINAL_FILENAME;
    public static boolean SHOW_ANDROID_EMOJI;
    public static boolean USE_DEVICE_FONT;
    public static DatabaseHandler databaseHandler;
    public static Fetch fetch = null;
    private static DisplayImageOptions options;
    public static ThinDownloadManager thinDl;

    public static DatabaseHandler getDatabaseHandler() {
        if (databaseHandler == null) {
            try {
                databaseHandler = new DatabaseHandler(ApplicationLoader.applicationContext);
            } catch (Exception e) {
            }
        }
        return databaseHandler;
    }

    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
    }

    public void onCreate() {
        super.onCreate();
        long time = System.currentTimeMillis();
        Fabric.with(this, new Crashlytics());
        new Settings(getApplicationContext()).setAllowedNetwork(200).enableLogging(false).setConcurrentDownloadsLimit(2).apply();
        fetch = Fetch.newInstance(getApplicationContext());
        fetch.removeRequests();
        thinDl = new ThinDownloadManager();
        databaseHandler = new DatabaseHandler(getApplicationContext());
        HandleRequest.initRequestQueue(getApplicationContext());
        initUniversaImageLoader(this);
        String sid = getString(R.string.SID);
        String analyticId = getString(R.string.ANALYTIC_ID);
        BaseService.registerAllServices(getApplicationContext());
        int appVer = AppPreferences.getAppVersion(this);
        if (appVer == 0 || appVer != 135) {
            AppPreferences.setMainDomain(getApplicationContext(), "");
            AppPreferences.setMirrorAddress(getApplicationContext(), "");
            AppPreferences.setRegistered(getApplicationContext(), false);
            AppPreferences.setTimeForJoin(getApplicationContext(), System.currentTimeMillis());
            AppPreferences.setThemNotShown(ApplicationLoader.applicationContext, false);
            AppPreferences.setProxyList(getApplicationContext(), new ArrayList());
        }
        SharedPreferences plusPreferences = getApplicationContext().getSharedPreferences("plusconfig", 0);
        SHOW_ANDROID_EMOJI = plusPreferences.getBoolean("showAndroidEmoji", false);
        USE_DEVICE_FONT = plusPreferences.getBoolean("useDeviceFont", false);
        KEEP_ORIGINAL_FILENAME = plusPreferences.getBoolean("keepOriginalFilename", false);
        if (!new File(Environment.getExternalStorageDirectory().toString() + "/" + "white_bg.png").exists()) {
            CopyAssets();
        }
    }

    public static void initUniversaImageLoader(Context ctx) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(ctx);
        config.threadPriority(3);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(52428800);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();
        C0658L.disableLogging();
        ImageLoader.getInstance().init(config.build());
    }

    public void onResult(Object object, int StatusCode) {
    }

    public static DisplayImageOptions getImageOptions() {
        if (options == null) {
            options = new DisplayImageOptions.Builder().cacheInMemory(true).considerExifParams(true).cacheOnDisk(true).showImageOnFail(17170445).build();
        }
        return options;
    }

    private void CopyAssets() {
        Exception e;
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("data");
        } catch (IOException e2) {
            Log.e("tag", e2.getMessage());
        }
        for (String filename : files) {
            if (filename.contentEquals("white_bg.jpg")) {
                System.out.println("File name => " + filename);
                try {
                    InputStream in = assetManager.open("data/" + filename);
                    OutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/" + filename);
                    try {
                        copyFile(in, out);
                        in.close();
                        out.flush();
                        out.close();
                    } catch (Exception e3) {
                        e = e3;
                        OutputStream outputStream = out;
                        Log.e("tag", e.getMessage());
                    }
                } catch (Exception e4) {
                    e = e4;
                    Log.e("tag", e.getMessage());
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        while (true) {
            int read = in.read(buffer);
            if (read != -1) {
                out.write(buffer, 0, read);
            } else {
                return;
            }
        }
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
