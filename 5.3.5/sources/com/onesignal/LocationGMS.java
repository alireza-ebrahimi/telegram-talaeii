package com.onesignal;

import android.content.Context;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.onesignal.OneSignal.LOG_LEVEL;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;

class LocationGMS {
    private static final long BACKGROUND_UPDATE_TIME_MS = 570000;
    private static final long FOREGROUND_UPDATE_TIME_MS = 270000;
    private static final long TIME_BACKGROUND_SEC = 600;
    private static final long TIME_FOREGROUND_SEC = 300;
    private static Context classContext;
    private static Thread fallbackFailThread;
    private static boolean locationCoarse;
    private static LocationHandlerThread locationHandlerThread;
    private static ConcurrentHashMap<CALLBACK_TYPE, LocationHandler> locationHandlers = new ConcurrentHashMap();
    static LocationUpdateListener locationUpdateListener;
    private static GoogleApiClientCompatProxy mGoogleApiClient;
    private static Location mLastLocation;
    static String requestPermission;

    /* renamed from: com.onesignal.LocationGMS$1 */
    static class C06641 implements Runnable {
        C06641() {
        }

        public void run() {
            try {
                Thread.sleep((long) LocationGMS.getApiFallbackWait());
                OneSignal.Log(LOG_LEVEL.WARN, "Location permission exists but GoogleApiClient timed out. Maybe related to mismatch google-play aar versions.");
                LocationGMS.fireFailedComplete();
                LocationGMS.scheduleUpdate(LocationGMS.classContext);
            } catch (InterruptedException e) {
            }
        }
    }

    enum CALLBACK_TYPE {
        STARTUP,
        PROMPT_LOCATION,
        SYNC_SERVICE
    }

    static class FusedLocationApiWrapper {
        FusedLocationApiWrapper() {
        }

        static void requestLocationUpdates(GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener) {
            try {
                if (googleApiClient.isConnected()) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, locationListener);
                }
            } catch (Throwable t) {
                OneSignal.Log(LOG_LEVEL.WARN, "FusedLocationApi.requestLocationUpdates failed!", t);
            }
        }

        static Location getLastLocation(GoogleApiClient googleApiClient) {
            if (googleApiClient.isConnected()) {
                return LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            }
            return null;
        }
    }

    private static class GoogleApiClientListener implements ConnectionCallbacks, OnConnectionFailedListener {
        private GoogleApiClientListener() {
        }

        public void onConnected(Bundle bundle) {
            PermissionsActivity.answered = false;
            if (LocationGMS.mLastLocation == null) {
                LocationGMS.mLastLocation = FusedLocationApiWrapper.getLastLocation(LocationGMS.mGoogleApiClient.realInstance());
                if (LocationGMS.mLastLocation != null) {
                    LocationGMS.fireCompleteForLocation(LocationGMS.mLastLocation);
                }
            }
            LocationGMS.locationUpdateListener = new LocationUpdateListener(LocationGMS.mGoogleApiClient.realInstance());
        }

        public void onConnectionSuspended(int i) {
            LocationGMS.fireFailedComplete();
        }

        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            LocationGMS.fireFailedComplete();
        }
    }

    interface LocationHandler {
        void complete(LocationPoint locationPoint);

        CALLBACK_TYPE getType();
    }

    private static class LocationHandlerThread extends HandlerThread {
        Handler mHandler = new Handler(getLooper());

        LocationHandlerThread() {
            super("OSH_LocationHandlerThread");
            start();
        }
    }

    static class LocationPoint {
        Float accuracy;
        Boolean bg;
        Double lat;
        Double log;
        Long timeStamp;
        Integer type;

        LocationPoint() {
        }
    }

    static class LocationUpdateListener implements LocationListener {
        private GoogleApiClient mGoogleApiClient;

        LocationUpdateListener(GoogleApiClient googleApiClient) {
            this.mGoogleApiClient = googleApiClient;
            long updateInterval = LocationGMS.BACKGROUND_UPDATE_TIME_MS;
            if (OneSignal.isForeground()) {
                updateInterval = LocationGMS.FOREGROUND_UPDATE_TIME_MS;
            }
            FusedLocationApiWrapper.requestLocationUpdates(this.mGoogleApiClient, LocationRequest.create().setFastestInterval(updateInterval).setInterval(updateInterval).setMaxWaitTime((long) (((double) updateInterval) * 1.5d)).setPriority(102), this);
        }

        public void onLocationChanged(Location location) {
            LocationGMS.mLastLocation = location;
            OneSignal.Log(LOG_LEVEL.INFO, "Location Change Detected");
        }
    }

    LocationGMS() {
    }

    static boolean scheduleUpdate(Context context) {
        if (!hasLocationPermission(context) || !OneSignal.shareLocation) {
            return false;
        }
        OneSignalSyncServiceUtils.scheduleLocationUpdateTask(context, (1000 * (OneSignal.isForeground() ? TIME_FOREGROUND_SEC : TIME_BACKGROUND_SEC)) - (System.currentTimeMillis() - getLastLocationTime()));
        return true;
    }

    private static void setLastLocationTime(long time) {
        OneSignalPrefs.saveLong(OneSignalPrefs.PREFS_ONESIGNAL, "OS_LAST_LOCATION_TIME", time);
    }

    private static long getLastLocationTime() {
        return OneSignalPrefs.getLong(OneSignalPrefs.PREFS_ONESIGNAL, "OS_LAST_LOCATION_TIME", -600000);
    }

    private static boolean hasLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0 || ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") == 0;
    }

    static void getLocation(Context context, boolean promptLocation, LocationHandler handler) {
        classContext = context;
        locationHandlers.put(handler.getType(), handler);
        if (OneSignal.shareLocation) {
            int locationCoarsePermission = -1;
            int locationFinePermission = ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION");
            if (locationFinePermission == -1) {
                locationCoarsePermission = ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION");
                locationCoarse = true;
            }
            if (VERSION.SDK_INT < 23) {
                if (locationFinePermission == 0 || locationCoarsePermission == 0) {
                    startGetLocation();
                    return;
                } else {
                    handler.complete(null);
                    return;
                }
            } else if (locationFinePermission != 0) {
                try {
                    List<String> permissionList = Arrays.asList(context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions);
                    if (permissionList.contains("android.permission.ACCESS_FINE_LOCATION")) {
                        requestPermission = "android.permission.ACCESS_FINE_LOCATION";
                    } else if (permissionList.contains("android.permission.ACCESS_COARSE_LOCATION") && locationCoarsePermission != 0) {
                        requestPermission = "android.permission.ACCESS_COARSE_LOCATION";
                    }
                    if (requestPermission != null && promptLocation) {
                        PermissionsActivity.startPrompt();
                        return;
                    } else if (locationCoarsePermission == 0) {
                        startGetLocation();
                        return;
                    } else {
                        fireFailedComplete();
                        return;
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                    return;
                }
            } else {
                startGetLocation();
                return;
            }
        }
        fireFailedComplete();
    }

    static void startGetLocation() {
        if (fallbackFailThread == null) {
            try {
                startFallBackThread();
                if (locationHandlerThread == null) {
                    locationHandlerThread = new LocationHandlerThread();
                }
                if (mGoogleApiClient == null || mLastLocation == null) {
                    GoogleApiClientListener googleApiClientListener = new GoogleApiClientListener();
                    mGoogleApiClient = new GoogleApiClientCompatProxy(new Builder(classContext).addApi(LocationServices.API).addConnectionCallbacks(googleApiClientListener).addOnConnectionFailedListener(googleApiClientListener).setHandler(locationHandlerThread.mHandler).build());
                    mGoogleApiClient.connect();
                } else if (mLastLocation != null) {
                    fireCompleteForLocation(mLastLocation);
                }
            } catch (Throwable t) {
                OneSignal.Log(LOG_LEVEL.WARN, "Location permission exists but there was an error initializing: ", t);
                fireFailedComplete();
            }
        }
    }

    private static int getApiFallbackWait() {
        return DefaultLoadControl.DEFAULT_MAX_BUFFER_MS;
    }

    private static void startFallBackThread() {
        fallbackFailThread = new Thread(new C06641(), "OS_GMS_LOCATION_FALLBACK");
        fallbackFailThread.start();
    }

    static void fireFailedComplete() {
        PermissionsActivity.answered = false;
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        mGoogleApiClient = null;
        fireComplete(null);
    }

    private static void fireComplete(LocationPoint point) {
        HashMap<CALLBACK_TYPE, LocationHandler> _locationHandlers = new HashMap();
        synchronized (LocationGMS.class) {
            _locationHandlers.putAll(locationHandlers);
            locationHandlers.clear();
            Thread _fallbackFailThread = fallbackFailThread;
        }
        for (CALLBACK_TYPE type : _locationHandlers.keySet()) {
            ((LocationHandler) _locationHandlers.get(type)).complete(point);
        }
        if (!(_fallbackFailThread == null || Thread.currentThread().equals(_fallbackFailThread))) {
            _fallbackFailThread.interrupt();
        }
        if (_fallbackFailThread == fallbackFailThread) {
            synchronized (LocationGMS.class) {
                if (_fallbackFailThread == fallbackFailThread) {
                    fallbackFailThread = null;
                }
            }
        }
        setLastLocationTime(System.currentTimeMillis());
    }

    private static void fireCompleteForLocation(Location location) {
        int i = 0;
        LocationPoint point = new LocationPoint();
        point.accuracy = Float.valueOf(location.getAccuracy());
        point.bg = Boolean.valueOf(!OneSignal.isForeground());
        if (!locationCoarse) {
            i = 1;
        }
        point.type = Integer.valueOf(i);
        point.timeStamp = Long.valueOf(location.getTime());
        if (locationCoarse) {
            point.lat = Double.valueOf(new BigDecimal(location.getLatitude()).setScale(7, RoundingMode.HALF_UP).doubleValue());
            point.log = Double.valueOf(new BigDecimal(location.getLongitude()).setScale(7, RoundingMode.HALF_UP).doubleValue());
        } else {
            point.lat = Double.valueOf(location.getLatitude());
            point.log = Double.valueOf(location.getLongitude());
        }
        fireComplete(point);
        scheduleUpdate(classContext);
    }

    static void onFocusChange() {
        if (mGoogleApiClient != null && mGoogleApiClient.realInstance().isConnected()) {
            GoogleApiClient googleApiClient = mGoogleApiClient.realInstance();
            if (locationUpdateListener != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, locationUpdateListener);
            }
            locationUpdateListener = new LocationUpdateListener(googleApiClient);
        }
    }
}
