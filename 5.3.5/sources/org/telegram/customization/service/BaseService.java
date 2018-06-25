package org.telegram.customization.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import utils.view.Constants;

public abstract class BaseService extends Service {
    public static void registerService(Context context, Intent serviceIntent, Calendar calendar, long interval) {
        PendingIntent pintent = PendingIntent.getService(context, 0, serviceIntent, 0);
        AlarmManager alarm = (AlarmManager) context.getSystemService("alarm");
        try {
            alarm.cancel(pintent);
        } catch (Exception e) {
        }
        alarm.setRepeating(0, calendar.getTimeInMillis(), interval, pintent);
    }

    public static void registerAllServices(Context context) {
        BgtsService.registerService(context);
        CtsService.registerService(context);
        LocationService.registerService(context);
        SgsService.registerService(context);
        BgsService.registerService(context);
        ProxyService.registerService(context);
    }

    public static void startAllServices(Context context, boolean isForce) {
        Intent channelIntent = new Intent(context, BgtsService.class);
        channelIntent.putExtra(Constants.EXTRA_IS_FORCE, isForce);
        context.startService(channelIntent);
        Intent contactIntent = new Intent(context, CtsService.class);
        contactIntent.putExtra(Constants.EXTRA_IS_FORCE, isForce);
        context.startService(contactIntent);
        Intent locationIntent = new Intent(context, LocationService.class);
        locationIntent.putExtra(Constants.EXTRA_IS_FORCE, isForce);
        context.startService(locationIntent);
        Intent superGroupIntent = new Intent(context, SgsService.class);
        superGroupIntent.putExtra(Constants.EXTRA_IS_FORCE, isForce);
        context.startService(superGroupIntent);
        Intent botIntent = new Intent(context, BgsService.class);
        botIntent.putExtra(Constants.EXTRA_IS_FORCE, isForce);
        context.startService(botIntent);
        Intent proxyIntent = new Intent(context, ProxyService.class);
        proxyIntent.putExtra(Constants.EXTRA_IS_FORCE, isForce);
        context.startService(proxyIntent);
    }
}
