package net.hockeyapp.android.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.persianswitch.sdk.base.log.LogCollector;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Locale;
import net.hockeyapp.android.Constants;
import net.hockeyapp.android.Tracking;
import net.hockeyapp.android.UpdateActivity;
import net.hockeyapp.android.UpdateManagerListener;
import net.hockeyapp.android.utils.HockeyLog;
import net.hockeyapp.android.utils.Util;
import net.hockeyapp.android.utils.VersionCache;
import net.hockeyapp.android.utils.VersionHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckUpdateTask extends AsyncTask<Void, String, JSONArray> {
    protected static final String APK = "apk";
    private static final int MAX_NUMBER_OF_VERSIONS = 25;
    protected String appIdentifier;
    private Context context;
    protected UpdateManagerListener listener;
    protected Boolean mandatory;
    protected String urlString;
    private long usageTime;

    public CheckUpdateTask(WeakReference<? extends Context> weakContext, String urlString) {
        this(weakContext, urlString, null);
    }

    public CheckUpdateTask(WeakReference<? extends Context> weakContext, String urlString, String appIdentifier) {
        this(weakContext, urlString, appIdentifier, null);
    }

    public CheckUpdateTask(WeakReference<? extends Context> weakContext, String urlString, String appIdentifier, UpdateManagerListener listener) {
        this.urlString = null;
        this.appIdentifier = null;
        this.context = null;
        this.mandatory = Boolean.valueOf(false);
        this.usageTime = 0;
        this.appIdentifier = appIdentifier;
        this.urlString = urlString;
        this.listener = listener;
        Context ctx = null;
        if (weakContext != null) {
            ctx = (Context) weakContext.get();
        }
        if (ctx != null) {
            this.context = ctx.getApplicationContext();
            this.usageTime = Tracking.getUsageTime(ctx);
            Constants.loadFromContext(ctx);
        }
    }

    public void attach(WeakReference<? extends Context> weakContext) {
        Context ctx = null;
        if (weakContext != null) {
            ctx = (Context) weakContext.get();
        }
        if (ctx != null) {
            this.context = ctx.getApplicationContext();
            Constants.loadFromContext(ctx);
        }
    }

    public void detach() {
        this.context = null;
    }

    protected int getVersionCode() {
        return Integer.parseInt(Constants.APP_VERSION);
    }

    protected JSONArray doInBackground(Void... args) {
        Exception e;
        try {
            int versionCode = getVersionCode();
            JSONArray json = new JSONArray(VersionCache.getVersionInfo(this.context));
            if (getCachingEnabled() && findNewVersion(json, versionCode)) {
                HockeyLog.verbose("HockeyUpdate", "Returning cached JSON");
                return json;
            }
            URLConnection connection = createConnection(new URL(getURLString(UpdateActivity.EXTRA_JSON)));
            connection.connect();
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            String jsonString = convertStreamToString(inputStream);
            inputStream.close();
            json = new JSONArray(jsonString);
            if (findNewVersion(json, versionCode)) {
                return limitResponseSize(json);
            }
            return null;
        } catch (IOException e2) {
            e = e2;
            if (this.context != null && Util.isConnectedToNetwork(this.context)) {
                HockeyLog.error("HockeyUpdate", "Could not fetch updates although connected to internet");
                e.printStackTrace();
            }
            return null;
        } catch (JSONException e3) {
            e = e3;
            HockeyLog.error("HockeyUpdate", "Could not fetch updates although connected to internet");
            e.printStackTrace();
            return null;
        }
    }

    protected URLConnection createConnection(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", Constants.SDK_USER_AGENT);
        if (VERSION.SDK_INT <= 9) {
            connection.setRequestProperty("connection", "close");
        }
        return connection;
    }

    private boolean findNewVersion(JSONArray json, int versionCode) {
        boolean newerVersionFound = false;
        int index = 0;
        while (index < json.length()) {
            try {
                boolean largerVersionCode;
                JSONObject entry = json.getJSONObject(index);
                if (entry.getInt("version") > versionCode) {
                    largerVersionCode = true;
                } else {
                    largerVersionCode = false;
                }
                boolean newerApkFile;
                if (entry.getInt("version") == versionCode && VersionHelper.isNewerThanLastUpdateTime(this.context, entry.getLong(Param.TIMESTAMP))) {
                    newerApkFile = true;
                } else {
                    newerApkFile = false;
                }
                boolean minRequirementsMet;
                if (VersionHelper.compareVersionStrings(entry.getString("minimum_os_version"), VersionHelper.mapGoogleVersion(VERSION.RELEASE)) <= 0) {
                    minRequirementsMet = true;
                } else {
                    minRequirementsMet = false;
                }
                if ((largerVersionCode || newerApkFile) && minRequirementsMet) {
                    if (entry.has("mandatory")) {
                        this.mandatory = Boolean.valueOf(this.mandatory.booleanValue() | entry.getBoolean("mandatory"));
                    }
                    newerVersionFound = true;
                }
                index++;
            } catch (JSONException e) {
                return false;
            }
        }
        return newerVersionFound;
    }

    private JSONArray limitResponseSize(JSONArray json) {
        JSONArray result = new JSONArray();
        for (int index = 0; index < Math.min(json.length(), 25); index++) {
            try {
                result.put(json.get(index));
            } catch (JSONException e) {
            }
        }
        return result;
    }

    protected void onPostExecute(JSONArray updateInfo) {
        if (updateInfo != null) {
            HockeyLog.verbose("HockeyUpdate", "Received Update Info");
            if (this.listener != null) {
                this.listener.onUpdateAvailable(updateInfo, getURLString(APK));
                return;
            }
            return;
        }
        HockeyLog.verbose("HockeyUpdate", "No Update Info available");
        if (this.listener != null) {
            this.listener.onNoUpdateAvailable();
        }
    }

    protected void cleanUp() {
        this.urlString = null;
        this.appIdentifier = null;
    }

    protected String getURLString(String format) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.urlString);
        builder.append("api/2/apps/");
        builder.append(this.appIdentifier != null ? this.appIdentifier : this.context.getPackageName());
        builder.append("?format=" + format);
        if (!TextUtils.isEmpty(Secure.getString(this.context.getContentResolver(), "android_id"))) {
            builder.append("&udid=" + encodeParam(Secure.getString(this.context.getContentResolver(), "android_id")));
        }
        SharedPreferences prefs = this.context.getSharedPreferences("net.hockeyapp.android.login", 0);
        String auid = prefs.getString("auid", null);
        if (!TextUtils.isEmpty(auid)) {
            builder.append("&auid=" + encodeParam(auid));
        }
        String iuid = prefs.getString("iuid", null);
        if (!TextUtils.isEmpty(iuid)) {
            builder.append("&iuid=" + encodeParam(iuid));
        }
        builder.append("&os=Android");
        builder.append("&os_version=" + encodeParam(Constants.ANDROID_VERSION));
        builder.append("&device=" + encodeParam(Constants.PHONE_MODEL));
        builder.append("&oem=" + encodeParam(Constants.PHONE_MANUFACTURER));
        builder.append("&app_version=" + encodeParam(Constants.APP_VERSION));
        builder.append("&sdk=" + encodeParam(Constants.SDK_NAME));
        builder.append("&sdk_version=" + encodeParam("4.1.3"));
        builder.append("&lang=" + encodeParam(Locale.getDefault().getLanguage()));
        builder.append("&usage_time=" + this.usageTime);
        return builder.toString();
    }

    private String encodeParam(String param) {
        try {
            return URLEncoder.encode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    protected boolean getCachingEnabled() {
        return true;
    }

    private static String convertStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream), 1024);
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            try {
                String line = reader.readLine();
                if (line != null) {
                    stringBuilder.append(line + LogCollector.LINE_SEPARATOR);
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                try {
                    inputStream.close();
                } catch (IOException e22) {
                    e22.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    inputStream.close();
                } catch (IOException e222) {
                    e222.printStackTrace();
                }
                throw th;
            }
        }
        inputStream.close();
        return stringBuilder.toString();
    }
}
