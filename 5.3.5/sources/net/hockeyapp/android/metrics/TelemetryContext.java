package net.hockeyapp.android.metrics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import net.hockeyapp.android.Constants;
import net.hockeyapp.android.metrics.model.Application;
import net.hockeyapp.android.metrics.model.Device;
import net.hockeyapp.android.metrics.model.Internal;
import net.hockeyapp.android.metrics.model.Session;
import net.hockeyapp.android.metrics.model.User;
import net.hockeyapp.android.utils.HockeyLog;
import net.hockeyapp.android.utils.Util;

class TelemetryContext {
    private static final String SESSION_IS_FIRST_KEY = "SESSION_IS_FIRST";
    private static final String SHARED_PREFERENCES_KEY = "HOCKEY_APP_TELEMETRY_CONTEXT";
    private static final String TAG = "HockeyApp-Metrics";
    private final Object IKEY_LOCK;
    protected final Application mApplication;
    protected Context mContext;
    protected final Device mDevice;
    private String mInstrumentationKey;
    protected final Internal mInternal;
    private String mPackageName;
    protected final Session mSession;
    private SharedPreferences mSettings;
    protected final User mUser;

    private TelemetryContext() {
        this.IKEY_LOCK = new Object();
        this.mDevice = new Device();
        this.mSession = new Session();
        this.mUser = new User();
        this.mApplication = new Application();
        this.mInternal = new Internal();
    }

    protected TelemetryContext(Context context, String appIdentifier) {
        this();
        this.mSettings = context.getSharedPreferences(SHARED_PREFERENCES_KEY, 0);
        this.mContext = context;
        this.mInstrumentationKey = Util.convertAppIdentifierToGuid(appIdentifier);
        configDeviceContext();
        configUserId();
        configInternalContext();
        configApplicationContext();
    }

    protected void renewSessionContext(String sessionId) {
        configSessionContext(sessionId);
    }

    protected void configSessionContext(String sessionId) {
        HockeyLog.debug(TAG, "Configuring session context");
        setSessionId(sessionId);
        HockeyLog.debug(TAG, "Setting the isNew-flag to true, as we only count new sessions");
        setIsNewSession("true");
        Editor editor = this.mSettings.edit();
        if (this.mSettings.getBoolean(SESSION_IS_FIRST_KEY, false)) {
            setIsFirstSession("false");
            HockeyLog.debug(TAG, "It's not their first session, writing false to SharedPreferences.");
            return;
        }
        editor.putBoolean(SESSION_IS_FIRST_KEY, true);
        editor.apply();
        setIsFirstSession("true");
        HockeyLog.debug(TAG, "It's our first session, writing true to SharedPreferences.");
    }

    protected void configApplicationContext() {
        HockeyLog.debug(TAG, "Configuring application context");
        String version = "unknown";
        this.mPackageName = "";
        try {
            PackageInfo info = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0);
            if (info.packageName != null) {
                this.mPackageName = info.packageName;
            }
            String appBuild = Integer.toString(info.versionCode);
            version = String.format("%s (%S)", new Object[]{info.versionName, appBuild});
        } catch (NameNotFoundException e) {
            HockeyLog.debug(TAG, "Could not get application context");
        } finally {
            setAppVersion(version);
        }
        setSdkVersion("android:" + "4.1.3");
    }

    protected void configUserId() {
        HockeyLog.debug(TAG, "Configuring user context");
        HockeyLog.debug("Using pre-supplied anonymous device identifier.");
        setAnonymousUserId(Constants.CRASH_IDENTIFIER);
    }

    protected void configDeviceContext() {
        HockeyLog.debug(TAG, "Configuring device context");
        setOsVersion(VERSION.RELEASE);
        setOsName("Android");
        setDeviceModel(Build.MODEL);
        setDeviceOemName(Build.MANUFACTURER);
        setOsLocale(Locale.getDefault().toString());
        setOsLanguage(Locale.getDefault().getLanguage());
        updateScreenResolution();
        setDeviceId(Constants.DEVICE_IDENTIFIER);
        if (((TelephonyManager) this.mContext.getSystemService("phone")).getPhoneType() != 0) {
            setDeviceType("Phone");
        } else {
            setDeviceType("Tablet");
        }
        if (Util.isEmulator()) {
            setDeviceModel("[Emulator]" + this.mDevice.getModel());
        }
    }

    @SuppressLint({"NewApi", "Deprecation"})
    protected void updateScreenResolution() {
        Point size;
        if (this.mContext != null) {
            int width;
            int height;
            WindowManager wm = (WindowManager) this.mContext.getSystemService("window");
            Display d;
            if (VERSION.SDK_INT >= 17) {
                size = new Point();
                d = wm.getDefaultDisplay();
                if (d != null) {
                    d.getRealSize(size);
                    width = size.x;
                    height = size.y;
                } else {
                    width = 0;
                    height = 0;
                }
            } else if (VERSION.SDK_INT >= 13) {
                try {
                    Method mGetRawW = Display.class.getMethod("getRawWidth", new Class[0]);
                    Method mGetRawH = Display.class.getMethod("getRawHeight", new Class[0]);
                    Display display = wm.getDefaultDisplay();
                    width = ((Integer) mGetRawW.invoke(display, new Object[0])).intValue();
                    height = ((Integer) mGetRawH.invoke(display, new Object[0])).intValue();
                } catch (Exception ex) {
                    size = new Point();
                    d = wm.getDefaultDisplay();
                    if (d != null) {
                        d.getRealSize(size);
                        width = size.x;
                        height = size.y;
                    } else {
                        width = 0;
                        height = 0;
                    }
                    HockeyLog.debug(TAG, "Couldn't determine screen resolution: " + ex.toString());
                }
            } else {
                d = wm.getDefaultDisplay();
                width = d.getWidth();
                height = d.getHeight();
            }
            setScreenResolution(String.valueOf(height) + "x" + String.valueOf(width));
        }
    }

    protected void configInternalContext() {
        setSdkVersion("android:" + "4.1.3");
    }

    protected String getPackageName() {
        return this.mPackageName;
    }

    protected Map<String, String> getContextTags() {
        Map<String, String> contextTags = new LinkedHashMap();
        synchronized (this.mApplication) {
            this.mApplication.addToHashMap(contextTags);
        }
        synchronized (this.mDevice) {
            this.mDevice.addToHashMap(contextTags);
        }
        synchronized (this.mSession) {
            this.mSession.addToHashMap(contextTags);
        }
        synchronized (this.mUser) {
            this.mUser.addToHashMap(contextTags);
        }
        synchronized (this.mInternal) {
            this.mInternal.addToHashMap(contextTags);
        }
        return contextTags;
    }

    public String getInstrumentationKey() {
        String str;
        synchronized (this.IKEY_LOCK) {
            str = this.mInstrumentationKey;
        }
        return str;
    }

    public synchronized void setInstrumentationKey(String instrumentationKey) {
        synchronized (this.IKEY_LOCK) {
            this.mInstrumentationKey = instrumentationKey;
        }
    }

    public String getScreenResolution() {
        String screenResolution;
        synchronized (this.mDevice) {
            screenResolution = this.mDevice.getScreenResolution();
        }
        return screenResolution;
    }

    public void setScreenResolution(String screenResolution) {
        synchronized (this.mDevice) {
            this.mDevice.setScreenResolution(screenResolution);
        }
    }

    public String getAppVersion() {
        String ver;
        synchronized (this.mApplication) {
            ver = this.mApplication.getVer();
        }
        return ver;
    }

    public void setAppVersion(String appVersion) {
        synchronized (this.mApplication) {
            this.mApplication.setVer(appVersion);
        }
    }

    public String getAnonymousUserId() {
        String id;
        synchronized (this.mUser) {
            id = this.mUser.getId();
        }
        return id;
    }

    public void setAnonymousUserId(String userId) {
        synchronized (this.mUser) {
            this.mUser.setId(userId);
        }
    }

    public String getSdkVersion() {
        String sdkVersion;
        synchronized (this.mInternal) {
            sdkVersion = this.mInternal.getSdkVersion();
        }
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        synchronized (this.mInternal) {
            this.mInternal.setSdkVersion(sdkVersion);
        }
    }

    public String getSessionId() {
        String id;
        synchronized (this.mSession) {
            id = this.mSession.getId();
        }
        return id;
    }

    public void setSessionId(String sessionId) {
        synchronized (this.mSession) {
            this.mSession.setId(sessionId);
        }
    }

    public String getIsFirstSession() {
        String isFirst;
        synchronized (this.mSession) {
            isFirst = this.mSession.getIsFirst();
        }
        return isFirst;
    }

    public void setIsFirstSession(String isFirst) {
        synchronized (this.mSession) {
            this.mSession.setIsFirst(isFirst);
        }
    }

    public String getIsNewSession() {
        String isNew;
        synchronized (this.mSession) {
            isNew = this.mSession.getIsNew();
        }
        return isNew;
    }

    public void setIsNewSession(String isNewSession) {
        synchronized (this.mSession) {
            this.mSession.setIsNew(isNewSession);
        }
    }

    public String getOsVersion() {
        String osVersion;
        synchronized (this.mDevice) {
            osVersion = this.mDevice.getOsVersion();
        }
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        synchronized (this.mDevice) {
            this.mDevice.setOsVersion(osVersion);
        }
    }

    public String getOsName() {
        String os;
        synchronized (this.mDevice) {
            os = this.mDevice.getOs();
        }
        return os;
    }

    public void setOsName(String osName) {
        synchronized (this.mDevice) {
            this.mDevice.setOs(osName);
        }
    }

    public String getDeviceModel() {
        String model;
        synchronized (this.mDevice) {
            model = this.mDevice.getModel();
        }
        return model;
    }

    public void setDeviceModel(String deviceModel) {
        synchronized (this.mDevice) {
            this.mDevice.setModel(deviceModel);
        }
    }

    public String getDeviceOemName() {
        String oemName;
        synchronized (this.mDevice) {
            oemName = this.mDevice.getOemName();
        }
        return oemName;
    }

    public void setDeviceOemName(String deviceOemName) {
        synchronized (this.mDevice) {
            this.mDevice.setOemName(deviceOemName);
        }
    }

    public String getOsLocale() {
        String locale;
        synchronized (this.mDevice) {
            locale = this.mDevice.getLocale();
        }
        return locale;
    }

    public void setOsLocale(String osLocale) {
        synchronized (this.mDevice) {
            this.mDevice.setLocale(osLocale);
        }
    }

    public String getOSLanguage() {
        String language;
        synchronized (this.mDevice) {
            language = this.mDevice.getLanguage();
        }
        return language;
    }

    public void setOsLanguage(String osLanguage) {
        synchronized (this.mDevice) {
            this.mDevice.setLanguage(osLanguage);
        }
    }

    public String getDeviceId() {
        return this.mDevice.getId();
    }

    public void setDeviceId(String deviceId) {
        synchronized (this.mDevice) {
            this.mDevice.setId(deviceId);
        }
    }

    public String getDeviceType() {
        return this.mDevice.getType();
    }

    public void setDeviceType(String deviceType) {
        synchronized (this.mDevice) {
            this.mDevice.setType(deviceType);
        }
    }
}
