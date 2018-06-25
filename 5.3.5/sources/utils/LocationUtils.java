package utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

public class LocationUtils {
    public static LatLng getLastKnownLocation(Context ctx) {
        LatLng selectedLatLong = new LatLng(-1.0d, -1.0d);
        LocationManager locationManager = (LocationManager) ctx.getSystemService(Param.LOCATION);
        if (ActivityCompat.checkSelfPermission(ctx, "android.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission(ctx, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
            return selectedLatLong;
        }
        Location lastLoc = null;
        if (locationManager.isProviderEnabled("network")) {
            lastLoc = locationManager.getLastKnownLocation("network");
        }
        if (lastLoc != null) {
            selectedLatLong = new LatLng(lastLoc.getLatitude(), lastLoc.getLongitude());
        } else {
            if (locationManager.isProviderEnabled("gps")) {
                lastLoc = locationManager.getLastKnownLocation("gps");
            }
            if (lastLoc != null) {
                selectedLatLong = new LatLng(lastLoc.getLatitude(), lastLoc.getLongitude());
            }
        }
        return selectedLatLong;
    }
}
