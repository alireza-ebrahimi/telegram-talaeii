package android.support.v7.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.support.v4.p014d.C0430a;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.telegram.messenger.exoplayer2.C3446C;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* renamed from: android.support.v7.widget.e */
class C1058e extends DataSetObservable {
    /* renamed from: a */
    static final String f3121a = C1058e.class.getSimpleName();
    /* renamed from: e */
    private static final Object f3122e = new Object();
    /* renamed from: f */
    private static final Map<String, C1058e> f3123f = new HashMap();
    /* renamed from: b */
    final Context f3124b;
    /* renamed from: c */
    final String f3125c;
    /* renamed from: d */
    boolean f3126d;
    /* renamed from: g */
    private final Object f3127g;
    /* renamed from: h */
    private final List<C1053a> f3128h;
    /* renamed from: i */
    private final List<C1055c> f3129i;
    /* renamed from: j */
    private Intent f3130j;
    /* renamed from: k */
    private C1054b f3131k;
    /* renamed from: l */
    private int f3132l;
    /* renamed from: m */
    private boolean f3133m;
    /* renamed from: n */
    private boolean f3134n;
    /* renamed from: o */
    private boolean f3135o;
    /* renamed from: p */
    private C1056d f3136p;

    /* renamed from: android.support.v7.widget.e$a */
    public final class C1053a implements Comparable<C1053a> {
        /* renamed from: a */
        public final ResolveInfo f3114a;
        /* renamed from: b */
        public float f3115b;
        /* renamed from: c */
        final /* synthetic */ C1058e f3116c;

        public C1053a(C1058e c1058e, ResolveInfo resolveInfo) {
            this.f3116c = c1058e;
            this.f3114a = resolveInfo;
        }

        /* renamed from: a */
        public int m5789a(C1053a c1053a) {
            return Float.floatToIntBits(c1053a.f3115b) - Float.floatToIntBits(this.f3115b);
        }

        public /* synthetic */ int compareTo(Object obj) {
            return m5789a((C1053a) obj);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            return Float.floatToIntBits(this.f3115b) == Float.floatToIntBits(((C1053a) obj).f3115b);
        }

        public int hashCode() {
            return Float.floatToIntBits(this.f3115b) + 31;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append("resolveInfo:").append(this.f3114a.toString());
            stringBuilder.append("; weight:").append(new BigDecimal((double) this.f3115b));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    /* renamed from: android.support.v7.widget.e$b */
    public interface C1054b {
        /* renamed from: a */
        void m5790a(Intent intent, List<C1053a> list, List<C1055c> list2);
    }

    /* renamed from: android.support.v7.widget.e$c */
    public static final class C1055c {
        /* renamed from: a */
        public final ComponentName f3117a;
        /* renamed from: b */
        public final long f3118b;
        /* renamed from: c */
        public final float f3119c;

        public C1055c(ComponentName componentName, long j, float f) {
            this.f3117a = componentName;
            this.f3118b = j;
            this.f3119c = f;
        }

        public C1055c(String str, long j, float f) {
            this(ComponentName.unflattenFromString(str), j, f);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            C1055c c1055c = (C1055c) obj;
            if (this.f3117a == null) {
                if (c1055c.f3117a != null) {
                    return false;
                }
            } else if (!this.f3117a.equals(c1055c.f3117a)) {
                return false;
            }
            return this.f3118b != c1055c.f3118b ? false : Float.floatToIntBits(this.f3119c) == Float.floatToIntBits(c1055c.f3119c);
        }

        public int hashCode() {
            return (((((this.f3117a == null ? 0 : this.f3117a.hashCode()) + 31) * 31) + ((int) (this.f3118b ^ (this.f3118b >>> 32)))) * 31) + Float.floatToIntBits(this.f3119c);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append("; activity:").append(this.f3117a);
            stringBuilder.append("; time:").append(this.f3118b);
            stringBuilder.append("; weight:").append(new BigDecimal((double) this.f3119c));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    /* renamed from: android.support.v7.widget.e$d */
    public interface C1056d {
        /* renamed from: a */
        boolean m5791a(C1058e c1058e, Intent intent);
    }

    /* renamed from: android.support.v7.widget.e$e */
    private final class C1057e extends AsyncTask<Object, Void, Void> {
        /* renamed from: a */
        final /* synthetic */ C1058e f3120a;

        C1057e(C1058e c1058e) {
            this.f3120a = c1058e;
        }

        /* renamed from: a */
        public Void m5792a(Object... objArr) {
            int i = 0;
            List list = (List) objArr[0];
            String str = (String) objArr[1];
            try {
                OutputStream openFileOutput = this.f3120a.f3124b.openFileOutput(str, 0);
                XmlSerializer newSerializer = Xml.newSerializer();
                try {
                    newSerializer.setOutput(openFileOutput, null);
                    newSerializer.startDocument(C3446C.UTF8_NAME, Boolean.valueOf(true));
                    newSerializer.startTag(null, "historical-records");
                    int size = list.size();
                    while (i < size) {
                        C1055c c1055c = (C1055c) list.remove(0);
                        newSerializer.startTag(null, "historical-record");
                        newSerializer.attribute(null, "activity", c1055c.f3117a.flattenToString());
                        newSerializer.attribute(null, "time", String.valueOf(c1055c.f3118b));
                        newSerializer.attribute(null, "weight", String.valueOf(c1055c.f3119c));
                        newSerializer.endTag(null, "historical-record");
                        i++;
                    }
                    newSerializer.endTag(null, "historical-records");
                    newSerializer.endDocument();
                    this.f3120a.f3126d = true;
                    if (openFileOutput != null) {
                        try {
                            openFileOutput.close();
                        } catch (IOException e) {
                        }
                    }
                } catch (Throwable e2) {
                    Log.e(C1058e.f3121a, "Error writing historical record file: " + this.f3120a.f3125c, e2);
                    this.f3120a.f3126d = true;
                    if (openFileOutput != null) {
                        try {
                            openFileOutput.close();
                        } catch (IOException e3) {
                        }
                    }
                } catch (Throwable e22) {
                    Log.e(C1058e.f3121a, "Error writing historical record file: " + this.f3120a.f3125c, e22);
                    this.f3120a.f3126d = true;
                    if (openFileOutput != null) {
                        try {
                            openFileOutput.close();
                        } catch (IOException e4) {
                        }
                    }
                } catch (Throwable e222) {
                    Log.e(C1058e.f3121a, "Error writing historical record file: " + this.f3120a.f3125c, e222);
                    this.f3120a.f3126d = true;
                    if (openFileOutput != null) {
                        try {
                            openFileOutput.close();
                        } catch (IOException e5) {
                        }
                    }
                } catch (Throwable th) {
                    this.f3120a.f3126d = true;
                    if (openFileOutput != null) {
                        try {
                            openFileOutput.close();
                        } catch (IOException e6) {
                        }
                    }
                }
            } catch (Throwable e2222) {
                Log.e(C1058e.f3121a, "Error writing historical record file: " + str, e2222);
            }
            return null;
        }

        public /* synthetic */ Object doInBackground(Object[] objArr) {
            return m5792a(objArr);
        }
    }

    /* renamed from: a */
    private boolean m5793a(C1055c c1055c) {
        boolean add = this.f3129i.add(c1055c);
        if (add) {
            this.f3134n = true;
            m5799h();
            m5794c();
            m5796e();
            notifyChanged();
        }
        return add;
    }

    /* renamed from: c */
    private void m5794c() {
        if (!this.f3133m) {
            throw new IllegalStateException("No preceding call to #readHistoricalData");
        } else if (this.f3134n) {
            this.f3134n = false;
            if (!TextUtils.isEmpty(this.f3125c)) {
                C0430a.m1910a(new C1057e(this), new ArrayList(this.f3129i), this.f3125c);
            }
        }
    }

    /* renamed from: d */
    private void m5795d() {
        int f = m5797f() | m5798g();
        m5799h();
        if (f != 0) {
            m5796e();
            notifyChanged();
        }
    }

    /* renamed from: e */
    private boolean m5796e() {
        if (this.f3131k == null || this.f3130j == null || this.f3128h.isEmpty() || this.f3129i.isEmpty()) {
            return false;
        }
        this.f3131k.m5790a(this.f3130j, this.f3128h, Collections.unmodifiableList(this.f3129i));
        return true;
    }

    /* renamed from: f */
    private boolean m5797f() {
        if (!this.f3135o || this.f3130j == null) {
            return false;
        }
        this.f3135o = false;
        this.f3128h.clear();
        List queryIntentActivities = this.f3124b.getPackageManager().queryIntentActivities(this.f3130j, 0);
        int size = queryIntentActivities.size();
        for (int i = 0; i < size; i++) {
            this.f3128h.add(new C1053a(this, (ResolveInfo) queryIntentActivities.get(i)));
        }
        return true;
    }

    /* renamed from: g */
    private boolean m5798g() {
        if (!this.f3126d || !this.f3134n || TextUtils.isEmpty(this.f3125c)) {
            return false;
        }
        this.f3126d = false;
        this.f3133m = true;
        m5800i();
        return true;
    }

    /* renamed from: h */
    private void m5799h() {
        int size = this.f3129i.size() - this.f3132l;
        if (size > 0) {
            this.f3134n = true;
            for (int i = 0; i < size; i++) {
                C1055c c1055c = (C1055c) this.f3129i.remove(0);
            }
        }
    }

    /* renamed from: i */
    private void m5800i() {
        try {
            InputStream openFileInput = this.f3124b.openFileInput(this.f3125c);
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setInput(openFileInput, C3446C.UTF8_NAME);
                int i = 0;
                while (i != 1 && i != 2) {
                    i = newPullParser.next();
                }
                if ("historical-records".equals(newPullParser.getName())) {
                    List list = this.f3129i;
                    list.clear();
                    while (true) {
                        int next = newPullParser.next();
                        if (next == 1) {
                            break;
                        } else if (!(next == 3 || next == 4)) {
                            if ("historical-record".equals(newPullParser.getName())) {
                                list.add(new C1055c(newPullParser.getAttributeValue(null, "activity"), Long.parseLong(newPullParser.getAttributeValue(null, "time")), Float.parseFloat(newPullParser.getAttributeValue(null, "weight"))));
                            } else {
                                throw new XmlPullParserException("Share records file not well-formed.");
                            }
                        }
                    }
                    if (openFileInput != null) {
                        try {
                            openFileInput.close();
                            return;
                        } catch (IOException e) {
                            return;
                        }
                    }
                    return;
                }
                throw new XmlPullParserException("Share records file does not start with historical-records tag.");
            } catch (Throwable e2) {
                Log.e(f3121a, "Error reading historical recrod file: " + this.f3125c, e2);
                if (openFileInput != null) {
                    try {
                        openFileInput.close();
                    } catch (IOException e3) {
                    }
                }
            } catch (Throwable e22) {
                Log.e(f3121a, "Error reading historical recrod file: " + this.f3125c, e22);
                if (openFileInput != null) {
                    try {
                        openFileInput.close();
                    } catch (IOException e4) {
                    }
                }
            } catch (Throwable th) {
                if (openFileInput != null) {
                    try {
                        openFileInput.close();
                    } catch (IOException e5) {
                    }
                }
            }
        } catch (FileNotFoundException e6) {
        }
    }

    /* renamed from: a */
    public int m5801a() {
        int size;
        synchronized (this.f3127g) {
            m5795d();
            size = this.f3128h.size();
        }
        return size;
    }

    /* renamed from: a */
    public int m5802a(ResolveInfo resolveInfo) {
        synchronized (this.f3127g) {
            m5795d();
            List list = this.f3128h;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (((C1053a) list.get(i)).f3114a == resolveInfo) {
                    return i;
                }
            }
            return -1;
        }
    }

    /* renamed from: a */
    public ResolveInfo m5803a(int i) {
        ResolveInfo resolveInfo;
        synchronized (this.f3127g) {
            m5795d();
            resolveInfo = ((C1053a) this.f3128h.get(i)).f3114a;
        }
        return resolveInfo;
    }

    /* renamed from: b */
    public Intent m5804b(int i) {
        synchronized (this.f3127g) {
            if (this.f3130j == null) {
                return null;
            }
            m5795d();
            C1053a c1053a = (C1053a) this.f3128h.get(i);
            ComponentName componentName = new ComponentName(c1053a.f3114a.activityInfo.packageName, c1053a.f3114a.activityInfo.name);
            Intent intent = new Intent(this.f3130j);
            intent.setComponent(componentName);
            if (this.f3136p != null) {
                if (this.f3136p.m5791a(this, new Intent(intent))) {
                    return null;
                }
            }
            m5793a(new C1055c(componentName, System.currentTimeMillis(), 1.0f));
            return intent;
        }
    }

    /* renamed from: b */
    public ResolveInfo m5805b() {
        synchronized (this.f3127g) {
            m5795d();
            if (this.f3128h.isEmpty()) {
                return null;
            }
            ResolveInfo resolveInfo = ((C1053a) this.f3128h.get(0)).f3114a;
            return resolveInfo;
        }
    }

    /* renamed from: c */
    public void m5806c(int i) {
        synchronized (this.f3127g) {
            m5795d();
            C1053a c1053a = (C1053a) this.f3128h.get(i);
            C1053a c1053a2 = (C1053a) this.f3128h.get(0);
            m5793a(new C1055c(new ComponentName(c1053a.f3114a.activityInfo.packageName, c1053a.f3114a.activityInfo.name), System.currentTimeMillis(), c1053a2 != null ? (c1053a2.f3115b - c1053a.f3115b) + 5.0f : 1.0f));
        }
    }
}
