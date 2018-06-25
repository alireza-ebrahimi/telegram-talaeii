package org.telegram.customization.service;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.IBinder;
import com.google.gson.Gson;
import java.util.Calendar;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.LocationHelper;
import org.telegram.ui.LocationActivity;
import utils.Utilities;
import utils.app.AppPreferences;
import utils.view.Constants;

public class LocationService extends BaseService implements IResponseReceiver {
    public static Calendar getCalender() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 12);
        calendar.set(12, Utilities.getRandomMinute());
        calendar.set(13, 0);
        return calendar;
    }

    public static void registerService(Context context) {
        registerService(context, new Intent(context, LocationService.class), getCalender(), AppPreferences.getLocationSyncPeriod(context));
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (VERSION.SDK_INT >= 23 && getApplicationContext().checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0) {
            stopSelf();
        }
        boolean isForce = false;
        if (intent != null) {
            isForce = intent.getBooleanExtra(Constants.EXTRA_IS_FORCE, false);
        }
        if (!AppPreferences.isRegistered(this)) {
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        } else if (isForce || checkTimeForSend()) {
            try {
                Location location = LocationActivity.getLastLocation();
                if (location != null) {
                    LocationHelper helper = new LocationHelper();
                    helper.setLatitude(location.getLatitude());
                    helper.setLongitude(location.getLongitude());
                    HandleRequest.getNew(getApplicationContext(), this).sendLocation(new Gson().toJson(helper));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        } else {
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
            case 15:
                AppPreferences.setLastSuccessFullyTimeSyncLocation(getApplicationContext(), System.currentTimeMillis());
                break;
        }
        stopSelf();
    }

    public boolean checkTimeForSend() {
        if (AppPreferences.getLocationSyncPeriod(getApplicationContext()) + AppPreferences.getLastSuccessFullyTimeSyncLocation(getApplicationContext()) < System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}
