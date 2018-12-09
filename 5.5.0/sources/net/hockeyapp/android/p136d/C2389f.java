package net.hockeyapp.android.p136d;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.Map;
import net.hockeyapp.android.C2367a;
import net.hockeyapp.android.p137e.C2401e;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

/* renamed from: net.hockeyapp.android.d.f */
public class C2389f extends C2384c<Void, Void, Boolean> {
    /* renamed from: a */
    private final int f8038a;
    /* renamed from: b */
    private final String f8039b;
    /* renamed from: c */
    private final Map<String, String> f8040c;
    /* renamed from: d */
    private Context f8041d;
    /* renamed from: e */
    private Handler f8042e;
    /* renamed from: f */
    private ProgressDialog f8043f;
    /* renamed from: g */
    private boolean f8044g = true;

    public C2389f(Context context, Handler handler, String str, int i, Map<String, String> map) {
        this.f8041d = context;
        this.f8042e = handler;
        this.f8039b = str;
        this.f8038a = i;
        this.f8040c = map;
        if (context != null) {
            C2367a.m11720a(context);
        }
    }

    /* renamed from: a */
    private HttpURLConnection m11816a(int i, Map<String, String> map) {
        if (i == 1) {
            return new C2401e(this.f8039b).m11852a("POST").m11854a((Map) map).m11851a();
        }
        if (i == 2) {
            return new C2401e(this.f8039b).m11852a("POST").m11857b((String) map.get(Scopes.EMAIL), (String) map.get("password")).m11851a();
        }
        if (i == 3) {
            return new C2401e(this.f8039b + "?" + ((String) map.get(Param.TYPE)) + "=" + ((String) map.get(TtmlNode.ATTR_ID))).m11851a();
        }
        throw new IllegalArgumentException("Login mode " + i + " not supported.");
    }

    /* renamed from: a */
    private boolean m11817a(String str) {
        SharedPreferences sharedPreferences = this.f8041d.getSharedPreferences("net.hockeyapp.android.login", 0);
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("status");
            if (TextUtils.isEmpty(string)) {
                return false;
            }
            Object string2;
            if (this.f8038a == 1) {
                if (string.equals("identified")) {
                    string2 = jSONObject.getString("iuid");
                    if (!TextUtils.isEmpty(string2)) {
                        sharedPreferences.edit().putString("iuid", string2).putString(Scopes.EMAIL, (String) this.f8040c.get(Scopes.EMAIL)).apply();
                        return true;
                    }
                }
            } else if (this.f8038a == 2) {
                if (string.equals("authorized")) {
                    string2 = jSONObject.getString("auid");
                    if (!TextUtils.isEmpty(string2)) {
                        sharedPreferences.edit().putString("auid", string2).putString(Scopes.EMAIL, (String) this.f8040c.get(Scopes.EMAIL)).apply();
                        return true;
                    }
                }
            } else if (this.f8038a != 3) {
                throw new IllegalArgumentException("Login mode " + this.f8038a + " not supported.");
            } else if (string.equals("validated")) {
                return true;
            } else {
                sharedPreferences.edit().remove("iuid").remove("auid").remove(Scopes.EMAIL).apply();
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* renamed from: a */
    protected Boolean m11818a(Void... voidArr) {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = m11816a(this.f8038a, this.f8040c);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                String a = C2384c.m11803a(httpURLConnection);
                if (!TextUtils.isEmpty(a)) {
                    Boolean valueOf = Boolean.valueOf(m11817a(a));
                    if (httpURLConnection == null) {
                        return valueOf;
                    }
                    httpURLConnection.disconnect();
                    return valueOf;
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (Throwable th) {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return Boolean.valueOf(false);
    }

    /* renamed from: a */
    public void m11819a() {
        this.f8041d = null;
        this.f8042e = null;
        this.f8043f = null;
    }

    /* renamed from: a */
    public void m11820a(Context context, Handler handler) {
        this.f8041d = context;
        this.f8042e = handler;
    }

    /* renamed from: a */
    protected void m11821a(Boolean bool) {
        if (this.f8043f != null) {
            try {
                this.f8043f.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.f8042e != null) {
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putBoolean(C1797b.SUCCESS, bool.booleanValue());
            message.setData(bundle);
            this.f8042e.sendMessage(message);
        }
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return m11818a((Void[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        m11821a((Boolean) obj);
    }

    protected void onPreExecute() {
        if ((this.f8043f == null || !this.f8043f.isShowing()) && this.f8044g) {
            this.f8043f = ProgressDialog.show(this.f8041d, TtmlNode.ANONYMOUS_REGION_ID, "Please wait...", true, false);
        }
    }
}
