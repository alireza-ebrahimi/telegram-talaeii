package org.telegram.customization.Internet;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.ir.talaeii.R;
import org.telegram.customization.Model.RegisterModel;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import utils.app.AppPreferences;
import utils.view.Constants;

public class BaseStringRequest extends StringRequest {
    static Random randomForGetRandInt = null;
    static final int randomMaximum = 1000;
    Context _context;

    public BaseStringRequest(int method, String url, Listener<String> listener, ErrorListener errorListener, Context context, String setKey) {
        super(method, getUrlWithTimestamp(AppPreferences.getCurrentDomainWithSetKey(setKey) + url, context), listener, errorListener);
        this._context = context;
    }

    public BaseStringRequest(int method, String url, Listener<String> listener, ErrorListener errorListener, Context context, boolean addMainDomain, String setKey) {
        super(method, getUrlWithTimestamp((addMainDomain ? AppPreferences.getCurrentDomainWithSetKey(setKey) : "") + url, context), listener, errorListener);
        this._context = context;
    }

    static String getUrlWithTimestamp(String url, Context context) {
        Uri uri = Uri.parse(url);
        String server = uri.getAuthority();
        String path = uri.getPath();
        String protocol = uri.getScheme();
        Set<String> args = uri.getQueryParameterNames();
        Builder builder = new Builder();
        builder.scheme(protocol);
        builder.authority(server);
        builder.path(path);
        for (String pname : args) {
            if (!(isTimestampQuery(pname) || isAppIdQuery(pname))) {
                builder.appendQueryParameter(pname, uri.getQueryParameter(pname));
            }
        }
        builder.appendQueryParameter("slt", "" + System.currentTimeMillis());
        builder.appendQueryParameter("appId", String.valueOf(context.getResources().getInteger(R.integer.APP_ID)));
        return builder.build().toString();
    }

    public static boolean isTimestampQuery(String str) {
        boolean z = false;
        if (str != null) {
            try {
                z = str.startsWith("slt");
            } catch (Exception e) {
            }
        }
        return z;
    }

    public static boolean isAppIdQuery(String str) {
        boolean z = false;
        if (str != null) {
            try {
                z = str.startsWith("appId");
            } catch (Exception e) {
            }
        }
        return z;
    }

    public static int getRandTS() {
        return getRandInt(1, 1000);
    }

    public static int getRandInt(int low, int high) {
        if (randomForGetRandInt == null) {
            randomForGetRandInt = new Random();
        }
        return randomForGetRandInt.nextInt(high - low) + low;
    }

    public String getBodyContentType() {
        return "application/json";
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap();
        if (BuildConfig.FLAVOR.contentEquals("mowjgram")) {
            params.put(HttpRequest.HEADER_AUTHORIZATION, "Custom AiWKaDpp4rQzcGVkU2VzgJ4m");
        } else if (BuildConfig.FLAVOR.contentEquals("vip")) {
            params.put(HttpRequest.HEADER_AUTHORIZATION, "Custom rifl3sLp445wcGXk29VWg2m");
        } else if (BuildConfig.FLAVOR.contentEquals("gram")) {
            params.put(HttpRequest.HEADER_AUTHORIZATION, "Custom V1fk1qbm405tcGlk59VWf2m");
        } else {
            params.put(HttpRequest.HEADER_AUTHORIZATION, "Custom QWxhZGRpbjpPcGVuU2VzYW1l");
        }
        params.put("X-SLS-VersionCode", String.valueOf(135));
        params.put(Constants.X_SLS_AppId, String.valueOf(this._context.getResources().getInteger(R.integer.APP_ID)));
        params.put("X-SLS-GPRS", String.valueOf(ConnectionsManager.isConnectedMobile(this._context)));
        params.put("X-SLS-Carrier", WSUtils.getCarrier(this._context));
        try {
            params.put("X-SLS-UID", String.valueOf(UserConfig.getClientUserId()));
            if (UserConfig.getCurrentUser() != null) {
                params.put("X-SLS-UN", String.valueOf(UserConfig.getCurrentUser().username));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public static Map<String, String> getBulkRequestHeaders(Context _context) throws AuthFailureError {
        Map<String, String> params = new HashMap();
        params.put(HttpRequest.HEADER_AUTHORIZATION, "Custom QWxhZGRpbjpPcGVuU2VzYW1l");
        params.put("X-SLS-VersionCode", String.valueOf(135));
        params.put(Constants.X_SLS_AppId, String.valueOf(_context.getResources().getInteger(R.integer.APP_ID)));
        params.put("X-SLS-GPRS", String.valueOf(ConnectionsManager.isConnectedMobile(_context)));
        params.put("X-SLS-Carrier", WSUtils.getCarrier(_context));
        params.put("X-SLS-Extra", RegisterModel.getRegisterModelData());
        try {
            params.put("X-SLS-UID", String.valueOf(UserConfig.getClientUserId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public static Map<String, String> getBulkRequestHeadersNew(Context _context) throws AuthFailureError {
        Map<String, String> params = new HashMap();
        params.put(HttpRequest.HEADER_AUTHORIZATION, "Custom QWxhZGRpbjpPcGVuU2VzYW1l");
        params.put("X-SLS-VersionCode", String.valueOf(135));
        params.put(Constants.X_SLS_AppId, String.valueOf(_context.getResources().getInteger(R.integer.APP_ID)));
        params.put("X-SLS-GPRS", String.valueOf(ConnectionsManager.isConnectedMobile(_context)));
        params.put("X-SLS-Carrier", WSUtils.getCarrier(_context));
        params.put("X-SLS-Extra", RegisterModel.getRegisterModelDataNew());
        try {
            params.put("X-SLS-UID", String.valueOf(UserConfig.getClientUserId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    private String getTimeStampQueryString() {
        return "?ts1=" + System.currentTimeMillis();
    }
}
