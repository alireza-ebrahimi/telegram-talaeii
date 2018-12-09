package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.am.C0288a;
import android.support.v4.app.an.C0289a;
import android.support.v4.app.ao.C0290a;
import android.support.v4.app.ap.C0261a;
import android.support.v4.app.ap.C0261a.C0258a;
import android.support.v4.app.ap.C0271b;
import android.support.v4.app.ap.C0271b.C0268a;
import android.support.v4.app.ar.C0291a;
import android.support.v4.app.as.C0292a;
import android.support.v4.app.at.C0293a;
import android.support.v4.app.ba.C0312a;
import android.support.v4.p014d.C0432c;
import android.widget.RemoteViews;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.telegram.messenger.exoplayer2.util.MimeTypes;

public class al {
    /* renamed from: a */
    static final C0278j f939a;

    /* renamed from: android.support.v4.app.al$a */
    public static class C0262a extends C0261a {
        /* renamed from: e */
        public static final C0258a f852e = new C02591();
        /* renamed from: a */
        final Bundle f853a;
        /* renamed from: b */
        public int f854b;
        /* renamed from: c */
        public CharSequence f855c;
        /* renamed from: d */
        public PendingIntent f856d;
        /* renamed from: f */
        private final ay[] f857f;
        /* renamed from: g */
        private boolean f858g;

        /* renamed from: android.support.v4.app.al$a$1 */
        static class C02591 implements C0258a {
            C02591() {
            }
        }

        /* renamed from: android.support.v4.app.al$a$a */
        public static final class C0260a {
            /* renamed from: a */
            private final int f846a;
            /* renamed from: b */
            private final CharSequence f847b;
            /* renamed from: c */
            private final PendingIntent f848c;
            /* renamed from: d */
            private boolean f849d;
            /* renamed from: e */
            private final Bundle f850e;
            /* renamed from: f */
            private ArrayList<ay> f851f;

            public C0260a(int i, CharSequence charSequence, PendingIntent pendingIntent) {
                this(i, charSequence, pendingIntent, new Bundle(), null, true);
            }

            private C0260a(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, ay[] ayVarArr, boolean z) {
                this.f849d = true;
                this.f846a = i;
                this.f847b = C0266d.m1225d(charSequence);
                this.f848c = pendingIntent;
                this.f850e = bundle;
                this.f851f = ayVarArr == null ? null : new ArrayList(Arrays.asList(ayVarArr));
                this.f849d = z;
            }

            /* renamed from: a */
            public C0260a m1202a(ay ayVar) {
                if (this.f851f == null) {
                    this.f851f = new ArrayList();
                }
                this.f851f.add(ayVar);
                return this;
            }

            /* renamed from: a */
            public C0260a m1203a(boolean z) {
                this.f849d = z;
                return this;
            }

            /* renamed from: a */
            public C0262a m1204a() {
                return new C0262a(this.f846a, this.f847b, this.f848c, this.f850e, this.f851f != null ? (ay[]) this.f851f.toArray(new ay[this.f851f.size()]) : null, this.f849d);
            }
        }

        public C0262a(int i, CharSequence charSequence, PendingIntent pendingIntent) {
            this(i, charSequence, pendingIntent, new Bundle(), null, true);
        }

        C0262a(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, ay[] ayVarArr, boolean z) {
            this.f854b = i;
            this.f855c = C0266d.m1225d(charSequence);
            this.f856d = pendingIntent;
            if (bundle == null) {
                bundle = new Bundle();
            }
            this.f853a = bundle;
            this.f857f = ayVarArr;
            this.f858g = z;
        }

        /* renamed from: a */
        public int mo209a() {
            return this.f854b;
        }

        /* renamed from: b */
        public CharSequence mo210b() {
            return this.f855c;
        }

        /* renamed from: c */
        public PendingIntent mo211c() {
            return this.f856d;
        }

        /* renamed from: d */
        public Bundle mo212d() {
            return this.f853a;
        }

        /* renamed from: e */
        public boolean mo213e() {
            return this.f858g;
        }

        /* renamed from: f */
        public ay[] m1216f() {
            return this.f857f;
        }

        /* renamed from: g */
        public /* synthetic */ C0312a[] mo214g() {
            return m1216f();
        }
    }

    /* renamed from: android.support.v4.app.al$s */
    public static abstract class C0263s {
        /* renamed from: d */
        C0266d f859d;
        /* renamed from: e */
        CharSequence f860e;
        /* renamed from: f */
        CharSequence f861f;
        /* renamed from: g */
        boolean f862g = false;

        /* renamed from: a */
        public void mo222a(Bundle bundle) {
        }

        /* renamed from: a */
        public void m1219a(C0266d c0266d) {
            if (this.f859d != c0266d) {
                this.f859d = c0266d;
                if (this.f859d != null) {
                    this.f859d.m1237a(this);
                }
            }
        }
    }

    /* renamed from: android.support.v4.app.al$b */
    public static class C0264b extends C0263s {
        /* renamed from: a */
        Bitmap f863a;
        /* renamed from: b */
        Bitmap f864b;
        /* renamed from: c */
        boolean f865c;

        /* renamed from: a */
        public C0264b m1220a(Bitmap bitmap) {
            this.f863a = bitmap;
            return this;
        }

        /* renamed from: a */
        public C0264b m1221a(CharSequence charSequence) {
            this.e = C0266d.m1225d(charSequence);
            return this;
        }

        /* renamed from: b */
        public C0264b m1222b(CharSequence charSequence) {
            this.f = C0266d.m1225d(charSequence);
            this.g = true;
            return this;
        }
    }

    /* renamed from: android.support.v4.app.al$c */
    public static class C0265c extends C0263s {
        /* renamed from: a */
        CharSequence f866a;

        /* renamed from: a */
        public C0265c m1223a(CharSequence charSequence) {
            this.f866a = C0266d.m1225d(charSequence);
            return this;
        }
    }

    /* renamed from: android.support.v4.app.al$d */
    public static class C0266d {
        /* renamed from: A */
        int f867A = 0;
        /* renamed from: B */
        Notification f868B;
        /* renamed from: C */
        RemoteViews f869C;
        /* renamed from: D */
        RemoteViews f870D;
        /* renamed from: E */
        RemoteViews f871E;
        /* renamed from: F */
        public Notification f872F = new Notification();
        /* renamed from: G */
        public ArrayList<String> f873G;
        /* renamed from: a */
        public Context f874a;
        /* renamed from: b */
        public CharSequence f875b;
        /* renamed from: c */
        public CharSequence f876c;
        /* renamed from: d */
        PendingIntent f877d;
        /* renamed from: e */
        PendingIntent f878e;
        /* renamed from: f */
        RemoteViews f879f;
        /* renamed from: g */
        public Bitmap f880g;
        /* renamed from: h */
        public CharSequence f881h;
        /* renamed from: i */
        public int f882i;
        /* renamed from: j */
        int f883j;
        /* renamed from: k */
        boolean f884k = true;
        /* renamed from: l */
        public boolean f885l;
        /* renamed from: m */
        public C0263s f886m;
        /* renamed from: n */
        public CharSequence f887n;
        /* renamed from: o */
        public CharSequence[] f888o;
        /* renamed from: p */
        int f889p;
        /* renamed from: q */
        int f890q;
        /* renamed from: r */
        boolean f891r;
        /* renamed from: s */
        String f892s;
        /* renamed from: t */
        boolean f893t;
        /* renamed from: u */
        String f894u;
        /* renamed from: v */
        public ArrayList<C0262a> f895v = new ArrayList();
        /* renamed from: w */
        boolean f896w = false;
        /* renamed from: x */
        String f897x;
        /* renamed from: y */
        Bundle f898y;
        /* renamed from: z */
        int f899z = 0;

        public C0266d(Context context) {
            this.f874a = context;
            this.f872F.when = System.currentTimeMillis();
            this.f872F.audioStreamType = -1;
            this.f883j = 0;
            this.f873G = new ArrayList();
        }

        /* renamed from: a */
        private void m1224a(int i, boolean z) {
            if (z) {
                Notification notification = this.f872F;
                notification.flags |= i;
                return;
            }
            notification = this.f872F;
            notification.flags &= i ^ -1;
        }

        /* renamed from: d */
        protected static CharSequence m1225d(CharSequence charSequence) {
            return (charSequence != null && charSequence.length() > 5120) ? charSequence.subSequence(0, 5120) : charSequence;
        }

        /* renamed from: a */
        public Bundle m1226a() {
            if (this.f898y == null) {
                this.f898y = new Bundle();
            }
            return this.f898y;
        }

        /* renamed from: a */
        public C0266d m1227a(int i) {
            this.f872F.icon = i;
            return this;
        }

        /* renamed from: a */
        public C0266d m1228a(int i, int i2, int i3) {
            int i4 = 1;
            this.f872F.ledARGB = i;
            this.f872F.ledOnMS = i2;
            this.f872F.ledOffMS = i3;
            int i5 = (this.f872F.ledOnMS == 0 || this.f872F.ledOffMS == 0) ? 0 : 1;
            Notification notification = this.f872F;
            int i6 = this.f872F.flags & -2;
            if (i5 == 0) {
                i4 = 0;
            }
            notification.flags = i6 | i4;
            return this;
        }

        /* renamed from: a */
        public C0266d m1229a(int i, int i2, boolean z) {
            this.f889p = i;
            this.f890q = i2;
            this.f891r = z;
            return this;
        }

        /* renamed from: a */
        public C0266d m1230a(int i, CharSequence charSequence, PendingIntent pendingIntent) {
            this.f895v.add(new C0262a(i, charSequence, pendingIntent));
            return this;
        }

        /* renamed from: a */
        public C0266d m1231a(long j) {
            this.f872F.when = j;
            return this;
        }

        /* renamed from: a */
        public C0266d m1232a(PendingIntent pendingIntent) {
            this.f877d = pendingIntent;
            return this;
        }

        /* renamed from: a */
        public C0266d m1233a(Bitmap bitmap) {
            this.f880g = bitmap;
            return this;
        }

        /* renamed from: a */
        public C0266d m1234a(Uri uri) {
            this.f872F.sound = uri;
            this.f872F.audioStreamType = -1;
            return this;
        }

        /* renamed from: a */
        public C0266d m1235a(Uri uri, int i) {
            this.f872F.sound = uri;
            this.f872F.audioStreamType = i;
            return this;
        }

        /* renamed from: a */
        public C0266d m1236a(C0273g c0273g) {
            c0273g.mo221a(this);
            return this;
        }

        /* renamed from: a */
        public C0266d m1237a(C0263s c0263s) {
            if (this.f886m != c0263s) {
                this.f886m = c0263s;
                if (this.f886m != null) {
                    this.f886m.m1219a(this);
                }
            }
            return this;
        }

        /* renamed from: a */
        public C0266d m1238a(CharSequence charSequence) {
            this.f875b = C0266d.m1225d(charSequence);
            return this;
        }

        /* renamed from: a */
        public C0266d m1239a(String str) {
            this.f897x = str;
            return this;
        }

        /* renamed from: a */
        public C0266d m1240a(boolean z) {
            m1224a(16, z);
            return this;
        }

        /* renamed from: a */
        public C0266d m1241a(long[] jArr) {
            this.f872F.vibrate = jArr;
            return this;
        }

        /* renamed from: b */
        public Notification m1242b() {
            return al.f939a.mo223a(this, m1252c());
        }

        /* renamed from: b */
        public C0266d m1243b(int i) {
            this.f882i = i;
            return this;
        }

        /* renamed from: b */
        public C0266d m1244b(PendingIntent pendingIntent) {
            this.f872F.deleteIntent = pendingIntent;
            return this;
        }

        /* renamed from: b */
        public C0266d m1245b(CharSequence charSequence) {
            this.f876c = C0266d.m1225d(charSequence);
            return this;
        }

        /* renamed from: b */
        public C0266d m1246b(String str) {
            this.f873G.add(str);
            return this;
        }

        /* renamed from: b */
        public C0266d m1247b(boolean z) {
            this.f896w = z;
            return this;
        }

        /* renamed from: c */
        public C0266d m1248c(int i) {
            this.f872F.defaults = i;
            if ((i & 4) != 0) {
                Notification notification = this.f872F;
                notification.flags |= 1;
            }
            return this;
        }

        /* renamed from: c */
        public C0266d m1249c(CharSequence charSequence) {
            this.f872F.tickerText = C0266d.m1225d(charSequence);
            return this;
        }

        /* renamed from: c */
        public C0266d m1250c(String str) {
            this.f892s = str;
            return this;
        }

        /* renamed from: c */
        public C0266d m1251c(boolean z) {
            this.f893t = z;
            return this;
        }

        /* renamed from: c */
        protected C0267e m1252c() {
            return new C0267e();
        }

        /* renamed from: d */
        public C0266d m1253d(int i) {
            this.f883j = i;
            return this;
        }

        /* renamed from: d */
        protected CharSequence m1254d() {
            return this.f876c;
        }

        /* renamed from: e */
        public C0266d m1255e(int i) {
            this.f899z = i;
            return this;
        }

        /* renamed from: e */
        protected CharSequence m1256e() {
            return this.f875b;
        }
    }

    /* renamed from: android.support.v4.app.al$e */
    protected static class C0267e {
        protected C0267e() {
        }

        /* renamed from: a */
        public Notification m1257a(C0266d c0266d, ak akVar) {
            Notification b = akVar.mo229b();
            if (c0266d.f869C != null) {
                b.contentView = c0266d.f869C;
            }
            return b;
        }
    }

    /* renamed from: android.support.v4.app.al$g */
    public interface C0273g {
        /* renamed from: a */
        C0266d mo221a(C0266d c0266d);
    }

    /* renamed from: android.support.v4.app.al$f */
    public static final class C0274f implements C0273g {
        /* renamed from: a */
        private Bitmap f913a;
        /* renamed from: b */
        private C0272a f914b;
        /* renamed from: c */
        private int f915c = 0;

        /* renamed from: android.support.v4.app.al$f$a */
        public static class C0272a extends C0271b {
            /* renamed from: a */
            static final C0268a f906a = new C02691();
            /* renamed from: b */
            private final String[] f907b;
            /* renamed from: c */
            private final ay f908c;
            /* renamed from: d */
            private final PendingIntent f909d;
            /* renamed from: e */
            private final PendingIntent f910e;
            /* renamed from: f */
            private final String[] f911f;
            /* renamed from: g */
            private final long f912g;

            /* renamed from: android.support.v4.app.al$f$a$1 */
            static class C02691 implements C0268a {
                C02691() {
                }
            }

            /* renamed from: android.support.v4.app.al$f$a$a */
            public static class C0270a {
                /* renamed from: a */
                private final List<String> f900a = new ArrayList();
                /* renamed from: b */
                private final String f901b;
                /* renamed from: c */
                private ay f902c;
                /* renamed from: d */
                private PendingIntent f903d;
                /* renamed from: e */
                private PendingIntent f904e;
                /* renamed from: f */
                private long f905f;

                public C0270a(String str) {
                    this.f901b = str;
                }

                /* renamed from: a */
                public C0270a m1258a(long j) {
                    this.f905f = j;
                    return this;
                }

                /* renamed from: a */
                public C0270a m1259a(PendingIntent pendingIntent) {
                    this.f903d = pendingIntent;
                    return this;
                }

                /* renamed from: a */
                public C0270a m1260a(PendingIntent pendingIntent, ay ayVar) {
                    this.f902c = ayVar;
                    this.f904e = pendingIntent;
                    return this;
                }

                /* renamed from: a */
                public C0270a m1261a(String str) {
                    this.f900a.add(str);
                    return this;
                }

                /* renamed from: a */
                public C0272a m1262a() {
                    return new C0272a((String[]) this.f900a.toArray(new String[this.f900a.size()]), this.f902c, this.f904e, this.f903d, new String[]{this.f901b}, this.f905f);
                }
            }

            C0272a(String[] strArr, ay ayVar, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] strArr2, long j) {
                this.f907b = strArr;
                this.f908c = ayVar;
                this.f910e = pendingIntent2;
                this.f909d = pendingIntent;
                this.f911f = strArr2;
                this.f912g = j;
            }

            /* renamed from: a */
            public String[] mo215a() {
                return this.f907b;
            }

            /* renamed from: b */
            public ay m1270b() {
                return this.f908c;
            }

            /* renamed from: c */
            public PendingIntent mo216c() {
                return this.f909d;
            }

            /* renamed from: d */
            public PendingIntent mo217d() {
                return this.f910e;
            }

            /* renamed from: e */
            public String[] mo218e() {
                return this.f911f;
            }

            /* renamed from: f */
            public long mo219f() {
                return this.f912g;
            }

            /* renamed from: g */
            public /* synthetic */ C0312a mo220g() {
                return m1270b();
            }
        }

        /* renamed from: a */
        public C0266d mo221a(C0266d c0266d) {
            if (VERSION.SDK_INT >= 21) {
                Bundle bundle = new Bundle();
                if (this.f913a != null) {
                    bundle.putParcelable("large_icon", this.f913a);
                }
                if (this.f915c != 0) {
                    bundle.putInt("app_color", this.f915c);
                }
                if (this.f914b != null) {
                    bundle.putBundle("car_conversation", al.f939a.mo225a(this.f914b));
                }
                c0266d.m1226a().putBundle("android.car.EXTENSIONS", bundle);
            }
            return c0266d;
        }

        /* renamed from: a */
        public C0274f m1278a(C0272a c0272a) {
            this.f914b = c0272a;
            return this;
        }
    }

    /* renamed from: android.support.v4.app.al$h */
    public static class C0275h extends C0263s {
        /* renamed from: a */
        ArrayList<CharSequence> f916a = new ArrayList();

        /* renamed from: a */
        public C0275h m1279a(CharSequence charSequence) {
            this.e = C0266d.m1225d(charSequence);
            return this;
        }

        /* renamed from: b */
        public C0275h m1280b(CharSequence charSequence) {
            this.f = C0266d.m1225d(charSequence);
            this.g = true;
            return this;
        }

        /* renamed from: c */
        public C0275h m1281c(CharSequence charSequence) {
            this.f916a.add(C0266d.m1225d(charSequence));
            return this;
        }
    }

    /* renamed from: android.support.v4.app.al$i */
    public static class C0277i extends C0263s {
        /* renamed from: a */
        CharSequence f922a;
        /* renamed from: b */
        CharSequence f923b;
        /* renamed from: c */
        List<C0276a> f924c = new ArrayList();

        /* renamed from: android.support.v4.app.al$i$a */
        public static final class C0276a {
            /* renamed from: a */
            private final CharSequence f917a;
            /* renamed from: b */
            private final long f918b;
            /* renamed from: c */
            private final CharSequence f919c;
            /* renamed from: d */
            private String f920d;
            /* renamed from: e */
            private Uri f921e;

            public C0276a(CharSequence charSequence, long j, CharSequence charSequence2) {
                this.f917a = charSequence;
                this.f918b = j;
                this.f919c = charSequence2;
            }

            /* renamed from: a */
            static Bundle[] m1282a(List<C0276a> list) {
                Bundle[] bundleArr = new Bundle[list.size()];
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    bundleArr[i] = ((C0276a) list.get(i)).m1283f();
                }
                return bundleArr;
            }

            /* renamed from: f */
            private Bundle m1283f() {
                Bundle bundle = new Bundle();
                if (this.f917a != null) {
                    bundle.putCharSequence(MimeTypes.BASE_TYPE_TEXT, this.f917a);
                }
                bundle.putLong("time", this.f918b);
                if (this.f919c != null) {
                    bundle.putCharSequence("sender", this.f919c);
                }
                if (this.f920d != null) {
                    bundle.putString(Param.TYPE, this.f920d);
                }
                if (this.f921e != null) {
                    bundle.putParcelable("uri", this.f921e);
                }
                return bundle;
            }

            /* renamed from: a */
            public CharSequence m1284a() {
                return this.f917a;
            }

            /* renamed from: b */
            public long m1285b() {
                return this.f918b;
            }

            /* renamed from: c */
            public CharSequence m1286c() {
                return this.f919c;
            }

            /* renamed from: d */
            public String m1287d() {
                return this.f920d;
            }

            /* renamed from: e */
            public Uri m1288e() {
                return this.f921e;
            }
        }

        C0277i() {
        }

        public C0277i(CharSequence charSequence) {
            this.f922a = charSequence;
        }

        /* renamed from: a */
        public C0277i m1289a(CharSequence charSequence) {
            this.f923b = charSequence;
            return this;
        }

        /* renamed from: a */
        public C0277i m1290a(CharSequence charSequence, long j, CharSequence charSequence2) {
            this.f924c.add(new C0276a(charSequence, j, charSequence2));
            if (this.f924c.size() > 25) {
                this.f924c.remove(0);
            }
            return this;
        }

        /* renamed from: a */
        public void mo222a(Bundle bundle) {
            super.mo222a(bundle);
            if (this.f922a != null) {
                bundle.putCharSequence("android.selfDisplayName", this.f922a);
            }
            if (this.f923b != null) {
                bundle.putCharSequence("android.conversationTitle", this.f923b);
            }
            if (!this.f924c.isEmpty()) {
                bundle.putParcelableArray("android.messages", C0276a.m1282a(this.f924c));
            }
        }
    }

    /* renamed from: android.support.v4.app.al$j */
    interface C0278j {
        /* renamed from: a */
        Notification mo223a(C0266d c0266d, C0267e c0267e);

        /* renamed from: a */
        Bundle mo224a(Notification notification);

        /* renamed from: a */
        Bundle mo225a(C0271b c0271b);

        /* renamed from: a */
        ArrayList<Parcelable> mo226a(C0262a[] c0262aArr);
    }

    /* renamed from: android.support.v4.app.al$n */
    static class C0279n implements C0278j {
        C0279n() {
        }

        /* renamed from: a */
        public Notification mo223a(C0266d c0266d, C0267e c0267e) {
            Notification a = ap.m1335a(c0266d.f872F, c0266d.f874a, c0266d.m1256e(), c0266d.m1254d(), c0266d.f877d, c0266d.f878e);
            if (c0266d.f883j > 0) {
                a.flags |= 128;
            }
            if (c0266d.f869C != null) {
                a.contentView = c0266d.f869C;
            }
            return a;
        }

        /* renamed from: a */
        public Bundle mo224a(Notification notification) {
            return null;
        }

        /* renamed from: a */
        public Bundle mo225a(C0271b c0271b) {
            return null;
        }

        /* renamed from: a */
        public ArrayList<Parcelable> mo226a(C0262a[] c0262aArr) {
            return null;
        }
    }

    /* renamed from: android.support.v4.app.al$q */
    static class C0280q extends C0279n {
        C0280q() {
        }

        /* renamed from: a */
        public Notification mo223a(C0266d c0266d, C0267e c0267e) {
            ak c0292a = new C0292a(c0266d.f874a, c0266d.f872F, c0266d.m1256e(), c0266d.m1254d(), c0266d.f881h, c0266d.f879f, c0266d.f882i, c0266d.f877d, c0266d.f878e, c0266d.f880g, c0266d.f889p, c0266d.f890q, c0266d.f891r, c0266d.f885l, c0266d.f883j, c0266d.f887n, c0266d.f896w, c0266d.f898y, c0266d.f892s, c0266d.f893t, c0266d.f894u, c0266d.f869C, c0266d.f870D);
            al.m1317a((aj) c0292a, c0266d.f895v);
            al.m1318a(c0292a, c0266d.f886m);
            Notification a = c0267e.m1257a(c0266d, c0292a);
            if (c0266d.f886m != null) {
                Bundle a2 = mo224a(a);
                if (a2 != null) {
                    c0266d.f886m.mo222a(a2);
                }
            }
            return a;
        }

        /* renamed from: a */
        public Bundle mo224a(Notification notification) {
            return as.m1343a(notification);
        }

        /* renamed from: a */
        public ArrayList<Parcelable> mo226a(C0262a[] c0262aArr) {
            return as.m1346a((C0261a[]) c0262aArr);
        }
    }

    /* renamed from: android.support.v4.app.al$r */
    static class C0281r extends C0280q {
        C0281r() {
        }

        /* renamed from: a */
        public Notification mo223a(C0266d c0266d, C0267e c0267e) {
            ak c0293a = new C0293a(c0266d.f874a, c0266d.f872F, c0266d.m1256e(), c0266d.m1254d(), c0266d.f881h, c0266d.f879f, c0266d.f882i, c0266d.f877d, c0266d.f878e, c0266d.f880g, c0266d.f889p, c0266d.f890q, c0266d.f891r, c0266d.f884k, c0266d.f885l, c0266d.f883j, c0266d.f887n, c0266d.f896w, c0266d.f873G, c0266d.f898y, c0266d.f892s, c0266d.f893t, c0266d.f894u, c0266d.f869C, c0266d.f870D);
            al.m1317a((aj) c0293a, c0266d.f895v);
            al.m1318a(c0293a, c0266d.f886m);
            return c0267e.m1257a(c0266d, c0293a);
        }

        /* renamed from: a */
        public Bundle mo224a(Notification notification) {
            return at.m1353a(notification);
        }
    }

    /* renamed from: android.support.v4.app.al$k */
    static class C0282k extends C0281r {
        C0282k() {
        }

        /* renamed from: a */
        public Notification mo223a(C0266d c0266d, C0267e c0267e) {
            ak c0288a = new C0288a(c0266d.f874a, c0266d.f872F, c0266d.m1256e(), c0266d.m1254d(), c0266d.f881h, c0266d.f879f, c0266d.f882i, c0266d.f877d, c0266d.f878e, c0266d.f880g, c0266d.f889p, c0266d.f890q, c0266d.f891r, c0266d.f884k, c0266d.f885l, c0266d.f883j, c0266d.f887n, c0266d.f896w, c0266d.f873G, c0266d.f898y, c0266d.f892s, c0266d.f893t, c0266d.f894u, c0266d.f869C, c0266d.f870D);
            al.m1317a((aj) c0288a, c0266d.f895v);
            al.m1318a(c0288a, c0266d.f886m);
            Notification a = c0267e.m1257a(c0266d, c0288a);
            if (c0266d.f886m != null) {
                c0266d.f886m.mo222a(mo224a(a));
            }
            return a;
        }

        /* renamed from: a */
        public ArrayList<Parcelable> mo226a(C0262a[] c0262aArr) {
            return am.m1324a((C0261a[]) c0262aArr);
        }
    }

    /* renamed from: android.support.v4.app.al$l */
    static class C0283l extends C0282k {
        C0283l() {
        }

        /* renamed from: a */
        public Notification mo223a(C0266d c0266d, C0267e c0267e) {
            ak c0289a = new C0289a(c0266d.f874a, c0266d.f872F, c0266d.m1256e(), c0266d.m1254d(), c0266d.f881h, c0266d.f879f, c0266d.f882i, c0266d.f877d, c0266d.f878e, c0266d.f880g, c0266d.f889p, c0266d.f890q, c0266d.f891r, c0266d.f884k, c0266d.f885l, c0266d.f883j, c0266d.f887n, c0266d.f896w, c0266d.f897x, c0266d.f873G, c0266d.f898y, c0266d.f899z, c0266d.f867A, c0266d.f868B, c0266d.f892s, c0266d.f893t, c0266d.f894u, c0266d.f869C, c0266d.f870D, c0266d.f871E);
            al.m1317a((aj) c0289a, c0266d.f895v);
            al.m1318a(c0289a, c0266d.f886m);
            Notification a = c0267e.m1257a(c0266d, c0289a);
            if (c0266d.f886m != null) {
                c0266d.f886m.mo222a(mo224a(a));
            }
            return a;
        }

        /* renamed from: a */
        public Bundle mo225a(C0271b c0271b) {
            return an.m1330a(c0271b);
        }
    }

    /* renamed from: android.support.v4.app.al$m */
    static class C0284m extends C0283l {
        C0284m() {
        }

        /* renamed from: a */
        public Notification mo223a(C0266d c0266d, C0267e c0267e) {
            aj c0290a = new C0290a(c0266d.f874a, c0266d.f872F, c0266d.f875b, c0266d.f876c, c0266d.f881h, c0266d.f879f, c0266d.f882i, c0266d.f877d, c0266d.f878e, c0266d.f880g, c0266d.f889p, c0266d.f890q, c0266d.f891r, c0266d.f884k, c0266d.f885l, c0266d.f883j, c0266d.f887n, c0266d.f896w, c0266d.f897x, c0266d.f873G, c0266d.f898y, c0266d.f899z, c0266d.f867A, c0266d.f868B, c0266d.f892s, c0266d.f893t, c0266d.f894u, c0266d.f888o, c0266d.f869C, c0266d.f870D, c0266d.f871E);
            al.m1317a(c0290a, c0266d.f895v);
            al.m1319b(c0290a, c0266d.f886m);
            Notification a = c0267e.m1257a(c0266d, c0290a);
            if (c0266d.f886m != null) {
                c0266d.f886m.mo222a(mo224a(a));
            }
            return a;
        }
    }

    /* renamed from: android.support.v4.app.al$o */
    static class C0285o extends C0279n {
        C0285o() {
        }

        /* renamed from: a */
        public Notification mo223a(C0266d c0266d, C0267e c0267e) {
            Notification a = aq.m1336a(c0266d.f874a, c0266d.f872F, c0266d.m1256e(), c0266d.m1254d(), c0266d.f881h, c0266d.f879f, c0266d.f882i, c0266d.f877d, c0266d.f878e, c0266d.f880g);
            if (c0266d.f869C != null) {
                a.contentView = c0266d.f869C;
            }
            return a;
        }
    }

    /* renamed from: android.support.v4.app.al$p */
    static class C0286p extends C0279n {
        C0286p() {
        }

        /* renamed from: a */
        public Notification mo223a(C0266d c0266d, C0267e c0267e) {
            return c0267e.m1257a(c0266d, new C0291a(c0266d.f874a, c0266d.f872F, c0266d.m1256e(), c0266d.m1254d(), c0266d.f881h, c0266d.f879f, c0266d.f882i, c0266d.f877d, c0266d.f878e, c0266d.f880g, c0266d.f889p, c0266d.f890q, c0266d.f891r));
        }
    }

    /* renamed from: android.support.v4.app.al$t */
    public static final class C0287t implements C0273g {
        /* renamed from: a */
        private ArrayList<C0262a> f925a = new ArrayList();
        /* renamed from: b */
        private int f926b = 1;
        /* renamed from: c */
        private PendingIntent f927c;
        /* renamed from: d */
        private ArrayList<Notification> f928d = new ArrayList();
        /* renamed from: e */
        private Bitmap f929e;
        /* renamed from: f */
        private int f930f;
        /* renamed from: g */
        private int f931g = 8388613;
        /* renamed from: h */
        private int f932h = -1;
        /* renamed from: i */
        private int f933i = 0;
        /* renamed from: j */
        private int f934j;
        /* renamed from: k */
        private int f935k = 80;
        /* renamed from: l */
        private int f936l;
        /* renamed from: m */
        private String f937m;
        /* renamed from: n */
        private String f938n;

        /* renamed from: a */
        public C0266d mo221a(C0266d c0266d) {
            Bundle bundle = new Bundle();
            if (!this.f925a.isEmpty()) {
                bundle.putParcelableArrayList("actions", al.f939a.mo226a((C0262a[]) this.f925a.toArray(new C0262a[this.f925a.size()])));
            }
            if (this.f926b != 1) {
                bundle.putInt("flags", this.f926b);
            }
            if (this.f927c != null) {
                bundle.putParcelable("displayIntent", this.f927c);
            }
            if (!this.f928d.isEmpty()) {
                bundle.putParcelableArray("pages", (Parcelable[]) this.f928d.toArray(new Notification[this.f928d.size()]));
            }
            if (this.f929e != null) {
                bundle.putParcelable("background", this.f929e);
            }
            if (this.f930f != 0) {
                bundle.putInt("contentIcon", this.f930f);
            }
            if (this.f931g != 8388613) {
                bundle.putInt("contentIconGravity", this.f931g);
            }
            if (this.f932h != -1) {
                bundle.putInt("contentActionIndex", this.f932h);
            }
            if (this.f933i != 0) {
                bundle.putInt("customSizePreset", this.f933i);
            }
            if (this.f934j != 0) {
                bundle.putInt("customContentHeight", this.f934j);
            }
            if (this.f935k != 80) {
                bundle.putInt("gravity", this.f935k);
            }
            if (this.f936l != 0) {
                bundle.putInt("hintScreenTimeout", this.f936l);
            }
            if (this.f937m != null) {
                bundle.putString("dismissalId", this.f937m);
            }
            if (this.f938n != null) {
                bundle.putString("bridgeTag", this.f938n);
            }
            c0266d.m1226a().putBundle("android.wearable.EXTENSIONS", bundle);
            return c0266d;
        }

        /* renamed from: a */
        public C0287t m1313a() {
            C0287t c0287t = new C0287t();
            c0287t.f925a = new ArrayList(this.f925a);
            c0287t.f926b = this.f926b;
            c0287t.f927c = this.f927c;
            c0287t.f928d = new ArrayList(this.f928d);
            c0287t.f929e = this.f929e;
            c0287t.f930f = this.f930f;
            c0287t.f931g = this.f931g;
            c0287t.f932h = this.f932h;
            c0287t.f933i = this.f933i;
            c0287t.f934j = this.f934j;
            c0287t.f935k = this.f935k;
            c0287t.f936l = this.f936l;
            c0287t.f937m = this.f937m;
            c0287t.f938n = this.f938n;
            return c0287t;
        }

        /* renamed from: a */
        public C0287t m1314a(C0262a c0262a) {
            this.f925a.add(c0262a);
            return this;
        }

        /* renamed from: a */
        public C0287t m1315a(String str) {
            this.f937m = str;
            return this;
        }

        public /* synthetic */ Object clone() {
            return m1313a();
        }
    }

    static {
        if (C0432c.m1912a()) {
            f939a = new C0284m();
        } else if (VERSION.SDK_INT >= 21) {
            f939a = new C0283l();
        } else if (VERSION.SDK_INT >= 20) {
            f939a = new C0282k();
        } else if (VERSION.SDK_INT >= 19) {
            f939a = new C0281r();
        } else if (VERSION.SDK_INT >= 16) {
            f939a = new C0280q();
        } else if (VERSION.SDK_INT >= 14) {
            f939a = new C0286p();
        } else if (VERSION.SDK_INT >= 11) {
            f939a = new C0285o();
        } else {
            f939a = new C0279n();
        }
    }

    /* renamed from: a */
    public static Bundle m1316a(Notification notification) {
        return f939a.mo224a(notification);
    }

    /* renamed from: a */
    static void m1317a(aj ajVar, ArrayList<C0262a> arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ajVar.mo228a((C0262a) it.next());
        }
    }

    /* renamed from: a */
    static void m1318a(ak akVar, C0263s c0263s) {
        if (c0263s == null) {
            return;
        }
        if (c0263s instanceof C0265c) {
            C0265c c0265c = (C0265c) c0263s;
            as.m1348a(akVar, c0265c.e, c0265c.g, c0265c.f, c0265c.f866a);
        } else if (c0263s instanceof C0275h) {
            C0275h c0275h = (C0275h) c0263s;
            as.m1349a(akVar, c0275h.e, c0275h.g, c0275h.f, c0275h.f916a);
        } else if (c0263s instanceof C0264b) {
            C0264b c0264b = (C0264b) c0263s;
            as.m1347a(akVar, c0264b.e, c0264b.g, c0264b.f, c0264b.f863a, c0264b.f864b, c0264b.f865c);
        }
    }

    /* renamed from: b */
    static void m1319b(ak akVar, C0263s c0263s) {
        if (c0263s == null) {
            return;
        }
        if (c0263s instanceof C0277i) {
            C0277i c0277i = (C0277i) c0263s;
            List arrayList = new ArrayList();
            List arrayList2 = new ArrayList();
            List arrayList3 = new ArrayList();
            List arrayList4 = new ArrayList();
            List arrayList5 = new ArrayList();
            for (C0276a c0276a : c0277i.f924c) {
                arrayList.add(c0276a.m1284a());
                arrayList2.add(Long.valueOf(c0276a.m1285b()));
                arrayList3.add(c0276a.m1286c());
                arrayList4.add(c0276a.m1287d());
                arrayList5.add(c0276a.m1288e());
            }
            ao.m1334a(akVar, c0277i.f922a, c0277i.f923b, arrayList, arrayList2, arrayList3, arrayList4, arrayList5);
            return;
        }
        m1318a(akVar, c0263s);
    }
}
