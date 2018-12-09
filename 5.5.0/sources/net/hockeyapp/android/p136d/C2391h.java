package net.hockeyapp.android.p136d;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hockeyapp.android.C2367a;
import net.hockeyapp.android.C2417f.C2416d;
import net.hockeyapp.android.p137e.C2400d;
import net.hockeyapp.android.p137e.C2401e;
import net.hockeyapp.android.p137e.C2408i;
import org.telegram.messenger.exoplayer2.util.MimeTypes;

/* renamed from: net.hockeyapp.android.d.h */
public class C2391h extends C2384c<Void, Void, HashMap<String, String>> {
    /* renamed from: a */
    private Context f8050a;
    /* renamed from: b */
    private Handler f8051b;
    /* renamed from: c */
    private String f8052c;
    /* renamed from: d */
    private String f8053d;
    /* renamed from: e */
    private String f8054e;
    /* renamed from: f */
    private String f8055f;
    /* renamed from: g */
    private String f8056g;
    /* renamed from: h */
    private List<Uri> f8057h;
    /* renamed from: i */
    private String f8058i;
    /* renamed from: j */
    private boolean f8059j;
    /* renamed from: k */
    private ProgressDialog f8060k;
    /* renamed from: l */
    private boolean f8061l = true;
    /* renamed from: m */
    private int f8062m = -1;

    public C2391h(Context context, String str, String str2, String str3, String str4, String str5, List<Uri> list, String str6, Handler handler, boolean z) {
        this.f8050a = context;
        this.f8052c = str;
        this.f8053d = str2;
        this.f8054e = str3;
        this.f8055f = str4;
        this.f8056g = str5;
        this.f8057h = list;
        this.f8058i = str6;
        this.f8051b = handler;
        this.f8059j = z;
        if (context != null) {
            C2367a.m11720a(context);
        }
    }

    /* renamed from: b */
    private HashMap<String, String> m11826b() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put(Param.TYPE, "send");
        HttpURLConnection httpURLConnection = null;
        try {
            Map hashMap2 = new HashMap();
            hashMap2.put("name", this.f8053d);
            hashMap2.put(Scopes.EMAIL, this.f8054e);
            hashMap2.put("subject", this.f8055f);
            hashMap2.put(MimeTypes.BASE_TYPE_TEXT, this.f8056g);
            hashMap2.put("bundle_identifier", C2367a.f7958d);
            hashMap2.put("bundle_short_version", C2367a.f7957c);
            hashMap2.put("bundle_version", C2367a.f7956b);
            hashMap2.put("os_version", C2367a.f7959e);
            hashMap2.put("oem", C2367a.f7962h);
            hashMap2.put("model", C2367a.f7961g);
            hashMap2.put("sdk_version", "4.1.3");
            if (this.f8058i != null) {
                this.f8052c += this.f8058i + "/";
            }
            httpURLConnection = new C2401e(this.f8052c).m11852a(this.f8058i != null ? "PUT" : "POST").m11854a(hashMap2).m11851a();
            httpURLConnection.connect();
            hashMap.put("status", String.valueOf(httpURLConnection.getResponseCode()));
            hashMap.put("response", C2384c.m11803a(httpURLConnection));
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (Throwable th) {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return hashMap;
    }

    /* renamed from: b */
    private void m11827b(HashMap<String, String> hashMap) {
        String str = (String) hashMap.get("status");
        if (str != null && str.startsWith("2") && this.f8050a != null) {
            File file = new File(this.f8050a.getCacheDir(), "HockeyApp");
            if (file != null && file.exists()) {
                for (File file2 : file.listFiles()) {
                    if (!(file2 == null || Boolean.valueOf(file2.delete()).booleanValue())) {
                        C2400d.m11845b("SendFeedbackTask", "Error deleting file from temporary folder");
                    }
                }
            }
        }
    }

    /* renamed from: c */
    private HashMap<String, String> m11828c() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put(Param.TYPE, "send");
        HttpURLConnection httpURLConnection = null;
        try {
            Map hashMap2 = new HashMap();
            hashMap2.put("name", this.f8053d);
            hashMap2.put(Scopes.EMAIL, this.f8054e);
            hashMap2.put("subject", this.f8055f);
            hashMap2.put(MimeTypes.BASE_TYPE_TEXT, this.f8056g);
            hashMap2.put("bundle_identifier", C2367a.f7958d);
            hashMap2.put("bundle_short_version", C2367a.f7957c);
            hashMap2.put("bundle_version", C2367a.f7956b);
            hashMap2.put("os_version", C2367a.f7959e);
            hashMap2.put("oem", C2367a.f7962h);
            hashMap2.put("model", C2367a.f7961g);
            hashMap2.put("sdk_version", "4.1.3");
            if (this.f8058i != null) {
                this.f8052c += this.f8058i + "/";
            }
            httpURLConnection = new C2401e(this.f8052c).m11852a(this.f8058i != null ? "PUT" : "POST").m11855a(hashMap2, this.f8050a, this.f8057h).m11851a();
            httpURLConnection.connect();
            hashMap.put("status", String.valueOf(httpURLConnection.getResponseCode()));
            hashMap.put("response", C2384c.m11803a(httpURLConnection));
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (Throwable th) {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return hashMap;
    }

    /* renamed from: d */
    private HashMap<String, String> m11829d() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.f8052c + C2408i.m11880a(this.f8058i));
        if (this.f8062m != -1) {
            stringBuilder.append("?last_message_id=" + this.f8062m);
        }
        HashMap<String, String> hashMap = new HashMap();
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = new C2401e(stringBuilder.toString()).m11851a();
            hashMap.put(Param.TYPE, "fetch");
            httpURLConnection.connect();
            hashMap.put("status", String.valueOf(httpURLConnection.getResponseCode()));
            hashMap.put("response", C2384c.m11803a(httpURLConnection));
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (Throwable th) {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return hashMap;
    }

    /* renamed from: a */
    protected HashMap<String, String> m11830a(Void... voidArr) {
        if (this.f8059j && this.f8058i != null) {
            return m11829d();
        }
        if (this.f8059j) {
            return null;
        }
        if (this.f8057h.isEmpty()) {
            return m11826b();
        }
        HashMap<String, String> c = m11828c();
        if (c == null) {
            return c;
        }
        m11827b(c);
        return c;
    }

    /* renamed from: a */
    public void m11831a() {
        this.f8050a = null;
        this.f8060k = null;
    }

    /* renamed from: a */
    protected void m11832a(HashMap<String, String> hashMap) {
        if (this.f8060k != null) {
            try {
                this.f8060k.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.f8051b != null) {
            Message message = new Message();
            Bundle bundle = new Bundle();
            if (hashMap != null) {
                bundle.putString("request_type", (String) hashMap.get(Param.TYPE));
                bundle.putString("feedback_response", (String) hashMap.get("response"));
                bundle.putString("feedback_status", (String) hashMap.get("status"));
            } else {
                bundle.putString("request_type", "unknown");
            }
            message.setData(bundle);
            this.f8051b.sendMessage(message);
        }
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return m11830a((Void[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        m11832a((HashMap) obj);
    }

    protected void onPreExecute() {
        CharSequence string = this.f8050a.getString(C2416d.hockeyapp_feedback_sending_feedback_text);
        if (this.f8059j) {
            string = this.f8050a.getString(C2416d.hockeyapp_feedback_fetching_feedback_text);
        }
        if ((this.f8060k == null || !this.f8060k.isShowing()) && this.f8061l) {
            this.f8060k = ProgressDialog.show(this.f8050a, TtmlNode.ANONYMOUS_REGION_ID, string, true, false);
        }
    }
}
