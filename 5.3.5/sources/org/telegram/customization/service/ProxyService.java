package org.telegram.customization.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.util.Calendar;
import org.telegram.customization.Internet.SLSProxyHelper;
import utils.app.AppPreferences;
import utils.view.Constants;

public class ProxyService extends BaseService {
    public static Calendar getCalender() {
        return Calendar.getInstance();
    }

    public static void registerService(Context context) {
        BaseService.registerService(context, new Intent(context, ProxyService.class), getCalender(), AppPreferences.getProxyCallPeriod());
    }

    public static void startProxyService(Context context, boolean forceCallApi) {
        Intent proxyIntent = new Intent(context, ProxyService.class);
        proxyIntent.putExtra(Constants.EXTRA_IS_FORCE_GET_PROXY, forceCallApi);
        context.startService(proxyIntent);
    }

    public static void startProxyService(Context context) {
        startProxyService(context, false);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isForce = false;
        if (intent != null) {
            isForce = intent.getBooleanExtra(Constants.EXTRA_IS_FORCE_GET_PROXY, false);
        }
        Log.d("LEE", "ConnectionManager onStartCommand Start proxy service  " + isForce);
        if (AppPreferences.getProxyEnable(getApplicationContext()) > 0) {
            SLSProxyHelper.getProxyServer(getApplicationContext(), isForce);
        }
        return 2;
    }

    public void onCreate() {
        super.onCreate();
    }
}
