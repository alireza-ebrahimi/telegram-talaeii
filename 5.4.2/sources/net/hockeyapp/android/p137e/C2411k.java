package net.hockeyapp.android.p137e;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.google.android.gms.dynamite.ProviderConstants;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;
import net.hockeyapp.android.C2365g;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: net.hockeyapp.android.e.k */
public class C2411k {
    /* renamed from: a */
    private ArrayList<JSONObject> f8104a;
    /* renamed from: b */
    private JSONObject f8105b;
    /* renamed from: c */
    private C2365g f8106c;
    /* renamed from: d */
    private int f8107d;

    /* renamed from: net.hockeyapp.android.e.k$1 */
    class C24101 implements Comparator<JSONObject> {
        /* renamed from: a */
        final /* synthetic */ C2411k f8103a;

        C24101(C2411k c2411k) {
            this.f8103a = c2411k;
        }

        /* renamed from: a */
        public int m11889a(JSONObject jSONObject, JSONObject jSONObject2) {
            try {
                return jSONObject.getInt(ProviderConstants.API_COLNAME_FEATURE_VERSION) > jSONObject2.getInt(ProviderConstants.API_COLNAME_FEATURE_VERSION) ? 0 : 0;
            } catch (JSONException e) {
            } catch (NullPointerException e2) {
            }
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m11889a((JSONObject) obj, (JSONObject) obj2);
        }
    }

    public C2411k(Context context, String str, C2365g c2365g) {
        this.f8106c = c2365g;
        m11896a(context, str);
        m11902d();
    }

    /* renamed from: a */
    public static int m11890a(String str, String str2) {
        if (str == null || str2 == null) {
            return 0;
        }
        try {
            Scanner scanner = new Scanner(str.replaceAll("\\-.*", TtmlNode.ANONYMOUS_REGION_ID));
            Scanner scanner2 = new Scanner(str2.replaceAll("\\-.*", TtmlNode.ANONYMOUS_REGION_ID));
            scanner.useDelimiter("\\.");
            scanner2.useDelimiter("\\.");
            while (scanner.hasNextInt() && scanner2.hasNextInt()) {
                int nextInt = scanner.nextInt();
                int nextInt2 = scanner2.nextInt();
                if (nextInt < nextInt2) {
                    return -1;
                }
                if (nextInt > nextInt2) {
                    return 1;
                }
            }
            return scanner.hasNextInt() ? 1 : !scanner2.hasNextInt() ? 0 : -1;
        } catch (Exception e) {
            return 0;
        }
    }

    /* renamed from: a */
    private static long m11891a(JSONObject jSONObject, String str, long j) {
        try {
            j = jSONObject.getLong(str);
        } catch (JSONException e) {
        }
        return j;
    }

    /* renamed from: a */
    private String m11892a(int i, JSONObject jSONObject) {
        StringBuilder stringBuilder = new StringBuilder();
        Object a = m11894a(jSONObject);
        if (!TextUtils.isEmpty(a)) {
            stringBuilder.append("<a href='restore:" + a + "'  style='background: #c8c8c8; color: #000; display: block; float: right; padding: 7px; margin: 0px 10px 10px; text-decoration: none;'>Restore</a>");
        }
        return stringBuilder.toString();
    }

    /* renamed from: a */
    public static String m11893a(String str) {
        return (str == null || str.equalsIgnoreCase("L")) ? "5.0" : str.equalsIgnoreCase("M") ? "6.0" : str.equalsIgnoreCase("N") ? "7.0" : Pattern.matches("^[a-zA-Z]+", str) ? "99.0" : str;
    }

    /* renamed from: a */
    private String m11894a(JSONObject jSONObject) {
        String str = TtmlNode.ANONYMOUS_REGION_ID;
        try {
            str = jSONObject.getString(TtmlNode.ATTR_ID);
        } catch (JSONException e) {
        }
        return str;
    }

    /* renamed from: a */
    private static String m11895a(JSONObject jSONObject, String str, String str2) {
        try {
            str2 = jSONObject.getString(str);
        } catch (JSONException e) {
        }
        return str2;
    }

    /* renamed from: a */
    private void m11896a(Context context, String str) {
        this.f8105b = new JSONObject();
        this.f8104a = new ArrayList();
        this.f8107d = this.f8106c.mo3383a();
        try {
            JSONArray jSONArray = new JSONArray(str);
            int a = this.f8106c.mo3383a();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                Object obj = jSONObject.getInt(ProviderConstants.API_COLNAME_FEATURE_VERSION) > a ? 1 : null;
                Object obj2 = (jSONObject.getInt(ProviderConstants.API_COLNAME_FEATURE_VERSION) == a && C2411k.m11897a(context, jSONObject.getLong(Param.TIMESTAMP))) ? 1 : null;
                if (!(obj == null && obj2 == null)) {
                    this.f8105b = jSONObject;
                    a = jSONObject.getInt(ProviderConstants.API_COLNAME_FEATURE_VERSION);
                }
                this.f8104a.add(jSONObject);
            }
        } catch (JSONException e) {
        } catch (NullPointerException e2) {
        }
    }

    /* renamed from: a */
    public static boolean m11897a(Context context, long j) {
        if (context == null) {
            return false;
        }
        try {
            return j > (new File(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir).lastModified() / 1000) + 1800;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* renamed from: b */
    private int m11898b(JSONObject jSONObject) {
        int i = 0;
        try {
            i = jSONObject.getInt(ProviderConstants.API_COLNAME_FEATURE_VERSION);
        } catch (JSONException e) {
        }
        return i;
    }

    /* renamed from: b */
    private String m11899b(int i, JSONObject jSONObject) {
        StringBuilder stringBuilder = new StringBuilder();
        int b = m11898b(this.f8105b);
        int b2 = m11898b(jSONObject);
        String c = m11901c(jSONObject);
        stringBuilder.append("<div style='padding: 20px 10px 10px;'><strong>");
        if (i == 0) {
            stringBuilder.append("Newest version:");
        } else {
            stringBuilder.append("Version " + c + " (" + b2 + "): ");
            if (b2 != b && b2 == this.f8107d) {
                this.f8107d = -1;
                stringBuilder.append("[INSTALLED]");
            }
        }
        stringBuilder.append("</strong></div>");
        return stringBuilder.toString();
    }

    /* renamed from: c */
    private String m11900c(int i, JSONObject jSONObject) {
        StringBuilder stringBuilder = new StringBuilder();
        String a = C2411k.m11895a(jSONObject, "notes", TtmlNode.ANONYMOUS_REGION_ID);
        stringBuilder.append("<div style='padding: 0px 10px;'>");
        if (a.trim().length() == 0) {
            stringBuilder.append("<em>No information.</em>");
        } else {
            stringBuilder.append(a);
        }
        stringBuilder.append("</div>");
        return stringBuilder.toString();
    }

    /* renamed from: c */
    private String m11901c(JSONObject jSONObject) {
        String str = TtmlNode.ANONYMOUS_REGION_ID;
        try {
            str = jSONObject.getString("shortversion");
        } catch (JSONException e) {
        }
        return str;
    }

    /* renamed from: d */
    private void m11902d() {
        Collections.sort(this.f8104a, new C24101(this));
    }

    /* renamed from: e */
    private Object m11903e() {
        return "<hr style='border-top: 1px solid #c8c8c8; border-bottom: 0px; margin: 40px 10px 0px 10px;' />";
    }

    /* renamed from: a */
    public String m11904a() {
        return C2411k.m11895a(this.f8105b, "shortversion", TtmlNode.ANONYMOUS_REGION_ID) + " (" + C2411k.m11895a(this.f8105b, ProviderConstants.API_COLNAME_FEATURE_VERSION, TtmlNode.ANONYMOUS_REGION_ID) + ")";
    }

    /* renamed from: a */
    public String m11905a(boolean z) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>");
        stringBuilder.append("<body style='padding: 0px 0px 20px 0px'>");
        Iterator it = this.f8104a.iterator();
        int i = 0;
        while (it.hasNext()) {
            JSONObject jSONObject = (JSONObject) it.next();
            if (i > 0) {
                stringBuilder.append(m11903e());
                if (z) {
                    stringBuilder.append(m11892a(i, jSONObject));
                }
            }
            stringBuilder.append(m11899b(i, jSONObject));
            stringBuilder.append(m11900c(i, jSONObject));
            i++;
        }
        stringBuilder.append("</body>");
        stringBuilder.append("</html>");
        return stringBuilder.toString();
    }

    @SuppressLint({"SimpleDateFormat"})
    /* renamed from: b */
    public String m11906b() {
        return new SimpleDateFormat("dd.MM.yyyy").format(new Date(C2411k.m11891a(this.f8105b, Param.TIMESTAMP, 0) * 1000));
    }

    /* renamed from: c */
    public long m11907c() {
        boolean booleanValue = Boolean.valueOf(C2411k.m11895a(this.f8105b, "external", "false")).booleanValue();
        long a = C2411k.m11891a(this.f8105b, "appsize", 0);
        return (booleanValue && a == 0) ? -1 : a;
    }
}
