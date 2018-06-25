package com.crashlytics.android.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import io.fabric.sdk.android.services.common.IdManager;

class AppData {
    public final String apiKey;
    public final String buildId;
    public final String installerPackageName;
    public final String packageName;
    public final String versionCode;
    public final String versionName;

    public static AppData create(Context context, IdManager idManager, String apiKey, String buildId) throws NameNotFoundException {
        String packageName = context.getPackageName();
        String installerPackageName = idManager.getInstallerPackageName();
        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        return new AppData(apiKey, buildId, installerPackageName, packageName, Integer.toString(packageInfo.versionCode), packageInfo.versionName == null ? IdManager.DEFAULT_VERSION_NAME : packageInfo.versionName);
    }

    AppData(String apiKey, String buildId, String installerPackageName, String packageName, String versionCode, String versionName) {
        this.apiKey = apiKey;
        this.buildId = buildId;
        this.installerPackageName = installerPackageName;
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.versionName = versionName;
    }
}
