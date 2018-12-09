package net.hockeyapp.android.p136d;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import com.google.android.gms.dynamite.ProviderConstants;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Locale;
import net.hockeyapp.android.C2367a;
import net.hockeyapp.android.C2419i;
import net.hockeyapp.android.p137e.C2400d;
import net.hockeyapp.android.p137e.C2408i;
import net.hockeyapp.android.p137e.C2409j;
import net.hockeyapp.android.p137e.C2411k;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: net.hockeyapp.android.d.b */
public class C2383b extends AsyncTask<Void, String, JSONArray> {
    /* renamed from: a */
    protected String f8022a;
    /* renamed from: b */
    protected String f8023b;
    /* renamed from: c */
    protected Boolean f8024c;
    /* renamed from: d */
    protected C2419i f8025d;
    /* renamed from: e */
    private Context f8026e;
    /* renamed from: f */
    private long f8027f;

    /* renamed from: a */
    private static String m11792a(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1024);
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    stringBuilder.append(readLine + "\n");
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                try {
                    inputStream.close();
                } catch (IOException e22) {
                    e22.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                throw th;
            }
        }
        inputStream.close();
        return stringBuilder.toString();
    }

    /* renamed from: a */
    private boolean m11793a(JSONArray jSONArray, int i) {
        int i2 = 0;
        boolean z = false;
        while (i2 < jSONArray.length()) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                Object obj = jSONObject.getInt(ProviderConstants.API_COLNAME_FEATURE_VERSION) > i ? 1 : null;
                Object obj2 = (jSONObject.getInt(ProviderConstants.API_COLNAME_FEATURE_VERSION) == i && C2411k.m11897a(this.f8026e, jSONObject.getLong(Param.TIMESTAMP))) ? 1 : null;
                Object obj3 = C2411k.m11890a(jSONObject.getString("minimum_os_version"), C2411k.m11893a(VERSION.RELEASE)) <= 0 ? 1 : null;
                if (!((obj == null && obj2 == null) || obj3 == null)) {
                    if (jSONObject.has("mandatory")) {
                        this.f8024c = Boolean.valueOf(this.f8024c.booleanValue() | jSONObject.getBoolean("mandatory"));
                    }
                    z = true;
                }
                i2++;
            } catch (JSONException e) {
                return false;
            }
        }
        return z;
    }

    /* renamed from: b */
    private String m11794b(String str) {
        try {
            return URLEncoder.encode(str, C3446C.UTF8_NAME);
        } catch (UnsupportedEncodingException e) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
    }

    /* renamed from: b */
    private JSONArray m11795b(JSONArray jSONArray) {
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < Math.min(jSONArray.length(), 25); i++) {
            try {
                jSONArray2.put(jSONArray.get(i));
            } catch (JSONException e) {
            }
        }
        return jSONArray2;
    }

    /* renamed from: a */
    protected int m11796a() {
        return Integer.parseInt(C2367a.f7956b);
    }

    /* renamed from: a */
    protected String m11797a(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.f8022a);
        stringBuilder.append("api/2/apps/");
        stringBuilder.append(this.f8023b != null ? this.f8023b : this.f8026e.getPackageName());
        stringBuilder.append("?format=" + str);
        if (!TextUtils.isEmpty(Secure.getString(this.f8026e.getContentResolver(), "android_id"))) {
            stringBuilder.append("&udid=" + m11794b(Secure.getString(this.f8026e.getContentResolver(), "android_id")));
        }
        SharedPreferences sharedPreferences = this.f8026e.getSharedPreferences("net.hockeyapp.android.login", 0);
        String string = sharedPreferences.getString("auid", null);
        if (!TextUtils.isEmpty(string)) {
            stringBuilder.append("&auid=" + m11794b(string));
        }
        String string2 = sharedPreferences.getString("iuid", null);
        if (!TextUtils.isEmpty(string2)) {
            stringBuilder.append("&iuid=" + m11794b(string2));
        }
        stringBuilder.append("&os=Android");
        stringBuilder.append("&os_version=" + m11794b(C2367a.f7959e));
        stringBuilder.append("&device=" + m11794b(C2367a.f7961g));
        stringBuilder.append("&oem=" + m11794b(C2367a.f7962h));
        stringBuilder.append("&app_version=" + m11794b(C2367a.f7956b));
        stringBuilder.append("&sdk=" + m11794b("HockeySDK"));
        stringBuilder.append("&sdk_version=" + m11794b("4.1.3"));
        stringBuilder.append("&lang=" + m11794b(Locale.getDefault().getLanguage()));
        stringBuilder.append("&usage_time=" + this.f8027f);
        return stringBuilder.toString();
    }

    /* renamed from: a */
    protected URLConnection m11798a(URL url) {
        URLConnection openConnection = url.openConnection();
        openConnection.addRequestProperty("User-Agent", "HockeySDK/Android 4.1.3");
        if (VERSION.SDK_INT <= 9) {
            openConnection.setRequestProperty("connection", "close");
        }
        return openConnection;
    }

    /* renamed from: a */
    protected JSONArray m11799a(Void... voidArr) {
        Exception e;
        try {
            int a = m11796a();
            JSONArray jSONArray = new JSONArray(C2409j.m11888a(this.f8026e));
            if (m11801b() && m11793a(jSONArray, a)) {
                C2400d.m11841a("HockeyUpdate", "Returning cached JSON");
                return jSONArray;
            }
            URLConnection a2 = m11798a(new URL(m11797a("json")));
            a2.connect();
            InputStream bufferedInputStream = new BufferedInputStream(a2.getInputStream());
            String a3 = C2383b.m11792a(bufferedInputStream);
            bufferedInputStream.close();
            JSONArray jSONArray2 = new JSONArray(a3);
            if (m11793a(jSONArray2, a)) {
                return m11795b(jSONArray2);
            }
            return null;
        } catch (IOException e2) {
            e = e2;
            if (this.f8026e != null && C2408i.m11882a(this.f8026e)) {
                C2400d.m11849d("HockeyUpdate", "Could not fetch updates although connected to internet");
                e.printStackTrace();
            }
            return null;
        } catch (JSONException e3) {
            e = e3;
            C2400d.m11849d("HockeyUpdate", "Could not fetch updates although connected to internet");
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    protected void m11800a(JSONArray jSONArray) {
        if (jSONArray != null) {
            C2400d.m11841a("HockeyUpdate", "Received Update Info");
            if (this.f8025d != null) {
                this.f8025d.m11912a(jSONArray, m11797a("apk"));
                return;
            }
            return;
        }
        C2400d.m11841a("HockeyUpdate", "No Update Info available");
        if (this.f8025d != null) {
            this.f8025d.m11911a();
        }
    }

    /* renamed from: b */
    protected boolean m11801b() {
        return true;
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return m11799a((Void[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        m11800a((JSONArray) obj);
    }
}
