package org.telegram.messenger;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;

public class SendMessagesHelper$LocationProvider {
    private LocationProviderDelegate delegate;
    private GpsLocationListener gpsLocationListener = new GpsLocationListener();
    private Location lastKnownLocation;
    private LocationManager locationManager;
    private Runnable locationQueryCancelRunnable;
    private GpsLocationListener networkLocationListener = new GpsLocationListener();

    /* renamed from: org.telegram.messenger.SendMessagesHelper$LocationProvider$LocationProviderDelegate */
    public interface LocationProviderDelegate {
        void onLocationAcquired(Location location);

        void onUnableLocationAcquire();
    }

    /* renamed from: org.telegram.messenger.SendMessagesHelper$LocationProvider$1 */
    class C16611 implements Runnable {
        C16611() {
        }

        public void run() {
            if (SendMessagesHelper$LocationProvider.this.locationQueryCancelRunnable == this) {
                if (SendMessagesHelper$LocationProvider.this.delegate != null) {
                    if (SendMessagesHelper$LocationProvider.this.lastKnownLocation != null) {
                        SendMessagesHelper$LocationProvider.this.delegate.onLocationAcquired(SendMessagesHelper$LocationProvider.this.lastKnownLocation);
                    } else {
                        SendMessagesHelper$LocationProvider.this.delegate.onUnableLocationAcquire();
                    }
                }
                SendMessagesHelper$LocationProvider.this.cleanup();
            }
        }
    }

    /* renamed from: org.telegram.messenger.SendMessagesHelper$LocationProvider$GpsLocationListener */
    private class GpsLocationListener implements LocationListener {
        private GpsLocationListener() {
        }

        public void onLocationChanged(Location location) {
            if (location != null && SendMessagesHelper$LocationProvider.this.locationQueryCancelRunnable != null) {
                FileLog.e("found location " + location);
                SendMessagesHelper$LocationProvider.this.lastKnownLocation = location;
                if (location.getAccuracy() < 100.0f) {
                    if (SendMessagesHelper$LocationProvider.this.delegate != null) {
                        SendMessagesHelper$LocationProvider.this.delegate.onLocationAcquired(location);
                    }
                    if (SendMessagesHelper$LocationProvider.this.locationQueryCancelRunnable != null) {
                        AndroidUtilities.cancelRunOnUIThread(SendMessagesHelper$LocationProvider.this.locationQueryCancelRunnable);
                    }
                    SendMessagesHelper$LocationProvider.this.cleanup();
                }
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    }

    public SendMessagesHelper$LocationProvider(LocationProviderDelegate locationProviderDelegate) {
        this.delegate = locationProviderDelegate;
    }

    public void setDelegate(LocationProviderDelegate locationProviderDelegate) {
        this.delegate = locationProviderDelegate;
    }

    private void cleanup() {
        this.locationManager.removeUpdates(this.gpsLocationListener);
        this.locationManager.removeUpdates(this.networkLocationListener);
        this.lastKnownLocation = null;
        this.locationQueryCancelRunnable = null;
    }

    public void start() {
        if (this.locationManager == null) {
            this.locationManager = (LocationManager) ApplicationLoader.applicationContext.getSystemService(Param.LOCATION);
        }
        try {
            this.locationManager.requestLocationUpdates("gps", 1, 0.0f, this.gpsLocationListener);
        } catch (Exception e) {
            FileLog.e(e);
        }
        try {
            this.locationManager.requestLocationUpdates("network", 1, 0.0f, this.networkLocationListener);
        } catch (Exception e2) {
            FileLog.e(e2);
        }
        try {
            this.lastKnownLocation = this.locationManager.getLastKnownLocation("gps");
            if (this.lastKnownLocation == null) {
                this.lastKnownLocation = this.locationManager.getLastKnownLocation("network");
            }
        } catch (Exception e22) {
            FileLog.e(e22);
        }
        if (this.locationQueryCancelRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.locationQueryCancelRunnable);
        }
        this.locationQueryCancelRunnable = new C16611();
        AndroidUtilities.runOnUIThread(this.locationQueryCancelRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
    }

    public void stop() {
        if (this.locationManager != null) {
            if (this.locationQueryCancelRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(this.locationQueryCancelRunnable);
            }
            cleanup();
        }
    }
}
