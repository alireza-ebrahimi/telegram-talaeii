package utils.volley.toolbox;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build.VERSION;
import java.io.File;
import utils.volley.Network;
import utils.volley.RequestQueue;

public class Volley {
    private static final String DEFAULT_CACHE_DIR = "volley";

    public static RequestQueue newRequestQueue(Context context) {
        return newRequestQueue(context, (BaseHttpStack) null);
    }

    private static RequestQueue newRequestQueue(Context context, Network network) {
        RequestQueue requestQueue = new RequestQueue(new DiskBasedCache(new File(context.getCacheDir(), DEFAULT_CACHE_DIR)), network);
        requestQueue.start();
        return requestQueue;
    }

    public static RequestQueue newRequestQueue(Context context, BaseHttpStack baseHttpStack) {
        Network basicNetwork;
        if (baseHttpStack != null) {
            basicNetwork = new BasicNetwork(baseHttpStack);
        } else if (VERSION.SDK_INT >= 9) {
            basicNetwork = new BasicNetwork(new HurlStack());
        } else {
            String str = "volley/0";
            try {
                String packageName = context.getPackageName();
                str = packageName + "/" + context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
            } catch (NameNotFoundException e) {
            }
            Object basicNetwork2 = new BasicNetwork(new HttpClientStack(AndroidHttpClient.newInstance(str)));
        }
        return newRequestQueue(context, basicNetwork);
    }

    @Deprecated
    public static RequestQueue newRequestQueue(Context context, HttpStack httpStack) {
        return httpStack == null ? newRequestQueue(context, (BaseHttpStack) null) : newRequestQueue(context, new BasicNetwork(httpStack));
    }
}
