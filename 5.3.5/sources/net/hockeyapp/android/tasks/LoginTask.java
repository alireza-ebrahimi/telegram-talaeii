package net.hockeyapp.android.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.google.android.gms.measurement.AppMeasurement.Param;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.Map;
import net.hockeyapp.android.Constants;
import net.hockeyapp.android.utils.HttpURLConnectionBuilder;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginTask extends ConnectionTask<Void, Void, Boolean> {
    public static final String BUNDLE_SUCCESS = "success";
    private Context mContext;
    private Handler mHandler;
    private final int mMode;
    private final Map<String, String> mParams;
    private ProgressDialog mProgressDialog;
    private boolean mShowProgressDialog = true;
    private final String mUrlString;

    public LoginTask(Context context, Handler handler, String urlString, int mode, Map<String, String> params) {
        this.mContext = context;
        this.mHandler = handler;
        this.mUrlString = urlString;
        this.mMode = mode;
        this.mParams = params;
        if (context != null) {
            Constants.loadFromContext(context);
        }
    }

    public void setShowProgressDialog(boolean showProgressDialog) {
        this.mShowProgressDialog = showProgressDialog;
    }

    public void attach(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
    }

    public void detach() {
        this.mContext = null;
        this.mHandler = null;
        this.mProgressDialog = null;
    }

    protected void onPreExecute() {
        if ((this.mProgressDialog == null || !this.mProgressDialog.isShowing()) && this.mShowProgressDialog) {
            this.mProgressDialog = ProgressDialog.show(this.mContext, "", "Please wait...", true, false);
        }
    }

    protected Boolean doInBackground(Void... args) {
        HttpURLConnection connection = null;
        try {
            connection = makeRequest(this.mMode, this.mParams);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                String responseStr = ConnectionTask.getStringFromConnection(connection);
                if (!TextUtils.isEmpty(responseStr)) {
                    Boolean valueOf = Boolean.valueOf(handleResponse(responseStr));
                    if (connection == null) {
                        return valueOf;
                    }
                    connection.disconnect();
                    return valueOf;
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            if (connection != null) {
                connection.disconnect();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            if (connection != null) {
                connection.disconnect();
            }
        } catch (Throwable th) {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return Boolean.valueOf(false);
    }

    protected void onPostExecute(Boolean success) {
        if (this.mProgressDialog != null) {
            try {
                this.mProgressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.mHandler != null) {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putBoolean("success", success.booleanValue());
            msg.setData(bundle);
            this.mHandler.sendMessage(msg);
        }
    }

    private HttpURLConnection makeRequest(int mode, Map<String, String> params) throws IOException {
        if (mode == 1) {
            return new HttpURLConnectionBuilder(this.mUrlString).setRequestMethod(HttpRequest.METHOD_POST).writeFormFields(params).build();
        }
        if (mode == 2) {
            return new HttpURLConnectionBuilder(this.mUrlString).setRequestMethod(HttpRequest.METHOD_POST).setBasicAuthorization((String) params.get("email"), (String) params.get("password")).build();
        }
        if (mode == 3) {
            return new HttpURLConnectionBuilder(this.mUrlString + "?" + ((String) params.get(Param.TYPE)) + "=" + ((String) params.get("id"))).build();
        }
        throw new IllegalArgumentException("Login mode " + mode + " not supported.");
    }

    private boolean handleResponse(String responseStr) {
        SharedPreferences prefs = this.mContext.getSharedPreferences("net.hockeyapp.android.login", 0);
        try {
            JSONObject response = new JSONObject(responseStr);
            String status = response.getString("status");
            if (TextUtils.isEmpty(status)) {
                return false;
            }
            if (this.mMode == 1) {
                if (status.equals("identified")) {
                    String iuid = response.getString("iuid");
                    if (!TextUtils.isEmpty(iuid)) {
                        prefs.edit().putString("iuid", iuid).putString("email", (String) this.mParams.get("email")).apply();
                        return true;
                    }
                }
            } else if (this.mMode == 2) {
                if (status.equals("authorized")) {
                    String auid = response.getString("auid");
                    if (!TextUtils.isEmpty(auid)) {
                        prefs.edit().putString("auid", auid).putString("email", (String) this.mParams.get("email")).apply();
                        return true;
                    }
                }
            } else if (this.mMode != 3) {
                throw new IllegalArgumentException("Login mode " + this.mMode + " not supported.");
            } else if (status.equals("validated")) {
                return true;
            } else {
                prefs.edit().remove("iuid").remove("auid").remove("email").apply();
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
