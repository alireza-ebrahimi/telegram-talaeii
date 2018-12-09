package org.telegram.customization.p151g;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import com.crashlytics.android.p064a.C1333b;
import com.crashlytics.android.p064a.C1351m;
import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.p098a.C1768f;
import com.google.p098a.C1769g;
import com.google.p098a.C1771l;
import com.google.p098a.C1776o;
import com.google.p098a.p103c.C1748a;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Ads.Category;
import org.telegram.customization.Model.Ads.Statistics;
import org.telegram.customization.Model.CheckUrlMeta;
import org.telegram.customization.Model.CheckUrlResponseModel;
import org.telegram.customization.Model.DialogStatus;
import org.telegram.customization.Model.FilterResponse;
import org.telegram.customization.Model.HotgramTheme;
import org.telegram.customization.Model.Payment.HostRequestData;
import org.telegram.customization.Model.Payment.HostResponseData;
import org.telegram.customization.Model.Payment.PaymentLink;
import org.telegram.customization.Model.Payment.User;
import org.telegram.customization.Model.ProxyServerModel;
import org.telegram.customization.Model.RegisterModel;
import org.telegram.customization.Model.SearchRequest;
import org.telegram.customization.Model.SettingAndUpdate;
import org.telegram.customization.compression.lz4.LZ4Factory;
import org.telegram.customization.dynamicadapter.GsonAdapterFactory;
import org.telegram.customization.dynamicadapter.data.DocAvailableInfo;
import org.telegram.customization.dynamicadapter.data.DocAvailableInfo.NewDocAvailableInfoAdapterFactory;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsFilter;
import org.telegram.customization.dynamicadapter.viewholder.ResponseReportHelper;
import org.telegram.customization.dynamicadapter.viewholder.ResponseSettleHelper;
import org.telegram.customization.g.c.AnonymousClass10;
import org.telegram.customization.g.c.AnonymousClass11;
import org.telegram.customization.g.c.AnonymousClass12;
import org.telegram.customization.g.c.AnonymousClass13;
import org.telegram.customization.g.c.AnonymousClass14;
import org.telegram.customization.g.c.AnonymousClass15;
import org.telegram.customization.g.c.AnonymousClass16;
import org.telegram.customization.g.c.AnonymousClass18;
import org.telegram.customization.g.c.AnonymousClass20;
import org.telegram.customization.g.c.AnonymousClass21;
import org.telegram.customization.g.c.AnonymousClass22;
import org.telegram.customization.g.c.AnonymousClass23;
import org.telegram.customization.g.c.AnonymousClass24;
import org.telegram.customization.g.c.AnonymousClass25;
import org.telegram.customization.g.c.AnonymousClass26;
import org.telegram.customization.g.c.AnonymousClass28;
import org.telegram.customization.g.c.AnonymousClass29;
import org.telegram.customization.g.c.AnonymousClass30;
import org.telegram.customization.g.c.AnonymousClass31;
import org.telegram.customization.g.c.AnonymousClass32;
import org.telegram.customization.g.c.AnonymousClass33;
import org.telegram.customization.g.c.AnonymousClass34;
import org.telegram.customization.g.c.AnonymousClass35;
import org.telegram.customization.g.c.AnonymousClass36;
import org.telegram.customization.g.c.AnonymousClass37;
import org.telegram.customization.g.c.AnonymousClass39;
import org.telegram.customization.g.c.AnonymousClass40;
import org.telegram.customization.g.c.AnonymousClass47;
import org.telegram.customization.g.c.AnonymousClass48;
import org.telegram.customization.g.c.AnonymousClass49;
import org.telegram.customization.p151g.C2757a;
import org.telegram.customization.p151g.C2758b;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.p151g.C2818c.12.C27621;
import org.telegram.customization.p151g.C2818c.16.C27671;
import org.telegram.customization.p151g.C2818c.20.C27731;
import org.telegram.customization.p151g.C2818c.24.C27792;
import org.telegram.customization.p151g.C2818c.28.C27832;
import org.telegram.customization.p151g.C2818c.47.C27991;
import org.telegram.customization.p151g.C2818c.48.C28011;
import org.telegram.customization.p151g.C2818c.49.C28031;
import org.telegram.customization.p151g.C2820e;
import org.telegram.customization.p159b.C2666a;
import org.telegram.customization.util.C2861a;
import org.telegram.customization.util.C2872c;
import org.telegram.customization.util.C2885i;
import org.telegram.customization.util.view.Poll.C2896c;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.news.p177b.C3744b;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.ui.ChatActivity;
import utils.C5319b;
import utils.C5323c;
import utils.p178a.C3791b;
import utils.view.Constants;
import utils.volley.DefaultRetryPolicy;
import utils.volley.Request;
import utils.volley.RequestQueue;
import utils.volley.Response$ErrorListener;
import utils.volley.Response$Listener;
import utils.volley.RetryPolicy;
import utils.volley.TimeoutError;
import utils.volley.VolleyError;
import utils.volley.toolbox.StringRequest;
import utils.volley.toolbox.Volley;

/* renamed from: org.telegram.customization.g.c */
public class C2818c implements C2497d, Response$ErrorListener, Response$Listener<String> {
    /* renamed from: b */
    public static long f9262b = 0;
    /* renamed from: c */
    public static long f9263c = 0;
    /* renamed from: d */
    public static long f9264d = 0;
    /* renamed from: f */
    private static RequestQueue f9265f;
    /* renamed from: l */
    private static boolean f9266l = false;
    /* renamed from: m */
    private static long f9267m = 0;
    /* renamed from: a */
    Handler f9268a;
    /* renamed from: e */
    private String f9269e;
    /* renamed from: g */
    private final Context f9270g;
    /* renamed from: h */
    private C2760a f9271h;
    /* renamed from: i */
    private C2497d f9272i;
    /* renamed from: j */
    private C2497d f9273j;
    /* renamed from: k */
    private int f9274k = 1;

    /* renamed from: org.telegram.customization.g.c$a */
    interface C2760a {
        /* renamed from: a */
        RetryPolicy mo3478a();

        /* renamed from: a */
        void mo3479a(C2757a c2757a);

        /* renamed from: a */
        void mo3480a(VolleyError volleyError);

        /* renamed from: b */
        Request mo3481b();

        /* renamed from: c */
        boolean mo3482c();

        /* renamed from: d */
        Type mo3483d();

        /* renamed from: e */
        String mo3484e();
    }

    /* renamed from: org.telegram.customization.g.c$2 */
    class C27852 implements C2760a {
        /* renamed from: a */
        final /* synthetic */ C2818c f9162a;

        /* renamed from: org.telegram.customization.g.c$2$1 */
        class C27711 extends C1748a<ArrayList<SlsFilter>> {
            /* renamed from: d */
            final /* synthetic */ C27852 f9134d;

            C27711(C27852 c27852) {
                this.f9134d = c27852;
            }
        }

        C27852(C2818c c2818c) {
            this.f9162a = c2818c;
        }

        /* renamed from: a */
        public RetryPolicy mo3478a() {
            return null;
        }

        /* renamed from: a */
        public void mo3479a(C2757a c2757a) {
            try {
                C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                String c1771l = k.m8426a(DataBufferSafeParcelable.DATA_FIELD) != null ? k.m8426a(DataBufferSafeParcelable.DATA_FIELD).toString() : null;
                ArrayList arrayList = (ArrayList) new C1768f().m8393a(c1771l, new C27711(this).m8360b());
                C2885i.m13374a(this.f9162a.f9270g, c1771l);
                C2885i.m13373a(this.f9162a.f9270g, System.currentTimeMillis());
                this.f9162a.f9272i.onResult(arrayList, 2);
            } catch (Exception e) {
                C2757a c2757a2 = new C2757a();
                c2757a2.m12802a(-1);
                c2757a2.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                this.f9162a.f9272i.onResult(c2757a2, -2);
            }
        }

        /* renamed from: a */
        public void mo3480a(VolleyError volleyError) {
            C2757a c2757a = new C2757a();
            c2757a.m12802a(-1);
            c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
            this.f9162a.f9272i.onResult(c2757a, -2);
        }

        /* renamed from: b */
        public Request mo3481b() {
            return new C2758b(this, 0, "v12/content/getFilters", this.f9162a, this.f9162a, this.f9162a.f9270g, mo3484e()) {
                /* renamed from: b */
                final /* synthetic */ C27852 f9135b;

                public byte[] getBody() {
                    return super.getBody();
                }

                protected Map<String, String> getParams() {
                    return super.getParams();
                }
            };
        }

        /* renamed from: c */
        public boolean mo3482c() {
            return true;
        }

        /* renamed from: d */
        public Type mo3483d() {
            return null;
        }

        /* renamed from: e */
        public String mo3484e() {
            return "set-1051";
        }

        public String toString() {
            return "WS_GET_FILTERS";
        }
    }

    /* renamed from: org.telegram.customization.g.c$b */
    interface C2805b extends C2760a {
        /* renamed from: f */
        String mo3487f();
    }

    /* renamed from: org.telegram.customization.g.c$5 */
    class C28095 implements C2760a {
        /* renamed from: a */
        final /* synthetic */ C2818c f9245a;

        /* renamed from: org.telegram.customization.g.c$5$1 */
        class C28071 extends C1748a<ArrayList<ObjBase>> {
            /* renamed from: d */
            final /* synthetic */ C28095 f9243d;

            C28071(C28095 c28095) {
                this.f9243d = c28095;
            }
        }

        C28095(C2818c c2818c) {
            this.f9245a = c2818c;
        }

        /* renamed from: a */
        public RetryPolicy mo3478a() {
            return null;
        }

        /* renamed from: a */
        public void mo3479a(C2757a c2757a) {
            C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
            String str = null;
            if (k.m8426a(DataBufferSafeParcelable.DATA_FIELD) != null) {
                str = k.m8426a(DataBufferSafeParcelable.DATA_FIELD).toString();
            }
            this.f9245a.f9272i.onResult((ArrayList) new C1769g().m8403a(new GsonAdapterFactory()).m8402a().m8393a(str, new C28071(this).m8360b()), 9);
        }

        /* renamed from: a */
        public void mo3480a(VolleyError volleyError) {
            C2757a c2757a = new C2757a();
            c2757a.m12802a(-1);
            c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
            this.f9245a.f9272i.onResult(c2757a, -9);
        }

        /* renamed from: b */
        public Request mo3481b() {
            return new C2758b(this, 0, String.format("v12/user/getTags?userId=%s", new Object[]{Integer.valueOf(UserConfig.getCurrentUser().id)}), this.f9245a, this.f9245a, this.f9245a.f9270g, mo3484e()) {
                /* renamed from: b */
                final /* synthetic */ C28095 f9244b;

                public byte[] getBody() {
                    return super.getBody();
                }

                protected Map<String, String> getParams() {
                    return super.getParams();
                }
            };
        }

        /* renamed from: c */
        public boolean mo3482c() {
            return true;
        }

        /* renamed from: d */
        public Type mo3483d() {
            return null;
        }

        /* renamed from: e */
        public String mo3484e() {
            return "set-1051";
        }

        public String toString() {
            return "WS_GET_USER_TAGS";
        }
    }

    /* renamed from: org.telegram.customization.g.c$7 */
    class C28127 implements C2760a {
        /* renamed from: a */
        final /* synthetic */ C2818c f9249a;

        C28127(C2818c c2818c) {
            this.f9249a = c2818c;
        }

        /* renamed from: a */
        public RetryPolicy mo3478a() {
            return null;
        }

        /* renamed from: a */
        public void mo3479a(C2757a c2757a) {
            try {
                C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                String str = null;
                if (k.m8426a(DataBufferSafeParcelable.DATA_FIELD) != null) {
                    str = k.m8426a(DataBufferSafeParcelable.DATA_FIELD).toString();
                }
                Object obj = (SettingAndUpdate) new C1768f().m8392a(str, SettingAndUpdate.class);
                C5323c.a(obj.getSetting(), this.f9249a.f9270g);
                Log.d("alireza ", "alireza setting " + new C1768f().m8395a(obj));
                this.f9249a.f9272i.onResult(obj, 10);
            } catch (Exception e) {
                e.printStackTrace();
                C2757a c2757a2 = new C2757a();
                c2757a2.m12802a(-1);
                c2757a2.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                this.f9249a.f9272i.onResult(c2757a2, -10);
            }
        }

        /* renamed from: a */
        public void mo3480a(VolleyError volleyError) {
            C2757a c2757a = new C2757a();
            c2757a.m12802a(-1);
            c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
            this.f9249a.f9272i.onResult(c2757a, -10);
        }

        /* renamed from: b */
        public Request mo3481b() {
            Request c28111 = new C2758b(this, 0, String.format("v3/config?appId=%s&appVer=%s&apiVer=%s", new Object[]{String.valueOf(this.f9249a.f9270g.getResources().getInteger(R.integer.APP_ID)), String.valueOf(BuildConfig.VERSION_CODE), String.valueOf(12)}), this.f9249a, this.f9249a, this.f9249a.f9270g, mo3484e()) {
                /* renamed from: b */
                final /* synthetic */ C28127 f9248b;

                public byte[] getBody() {
                    return super.getBody();
                }

                protected Map<String, String> getParams() {
                    return super.getParams();
                }
            };
            c28111.setShouldCache(false);
            return c28111;
        }

        /* renamed from: c */
        public boolean mo3482c() {
            return true;
        }

        /* renamed from: d */
        public Type mo3483d() {
            return null;
        }

        /* renamed from: e */
        public String mo3484e() {
            return "set-1066";
        }

        public String toString() {
            return "WS_LIGHT_HOST_CONFIG";
        }
    }

    private C2818c(Context context, C2497d c2497d) {
        this.f9270g = context;
        this.f9272i = this;
        this.f9273j = c2497d;
        try {
            this.f9268a = new Handler(Looper.getMainLooper());
        } catch (Exception e) {
        }
    }

    /* renamed from: a */
    public static C2818c m13087a(Context context, C2497d c2497d) {
        return new C2818c(context, c2497d);
    }

    /* renamed from: a */
    public static RequestQueue m13089a() {
        return f9265f;
    }

    /* renamed from: a */
    public static RequestQueue m13090a(Context context) {
        if (f9265f == null) {
            f9265f = Volley.newRequestQueue(context);
        }
        return f9265f;
    }

    /* renamed from: a */
    private void m13091a(final C2760a c2760a) {
        if (this.f9268a != null) {
            new Thread(this) {
                /* renamed from: b */
                final /* synthetic */ C2818c f9195b;

                public void run() {
                    super.run();
                    this.f9195b.m13095b(c2760a);
                }
            }.start();
        } else {
            m13095b(c2760a);
        }
    }

    /* renamed from: b */
    private void m13095b(C2760a c2760a) {
        this.f9271h = c2760a;
        Request b = this.f9271h.mo3481b();
        this.f9269e = b.getUrl();
        if (b == null || !ConnectionsManager.isNetworkOnline()) {
            this.f9272i.onResult("offline", C3446C.PRIORITY_DOWNLOAD);
            return;
        }
        RetryPolicy a = c2760a.mo3478a();
        if (a == null) {
            b.setRetryPolicy(new DefaultRetryPolicy(C2822f.m13155a(this.f9270g), 0, 1.0f));
        } else {
            b.setRetryPolicy(a);
        }
        if (c2760a instanceof C2805b) {
            try {
                b.setTag(((C2805b) c2760a).mo3487f());
            } catch (Exception e) {
            }
        }
        C2818c.m13089a().add(b);
    }

    /* renamed from: c */
    public static void m13097c(String str) {
        C2818c.m13089a().cancelAll((Object) str);
    }

    /* renamed from: k */
    public static String m13100k() {
        return new Object() {
            /* renamed from: a */
            int f9209a;

            public String toString() {
                r0 = new byte[16];
                this.f9209a = -1146426114;
                r0[0] = (byte) (this.f9209a >>> 23);
                this.f9209a = -1797346490;
                r0[1] = (byte) (this.f9209a >>> 14);
                this.f9209a = 1620530314;
                r0[2] = (byte) (this.f9209a >>> 17);
                this.f9209a = 280132393;
                r0[3] = (byte) (this.f9209a >>> 17);
                this.f9209a = 911222007;
                r0[4] = (byte) (this.f9209a >>> 8);
                this.f9209a = -718635942;
                r0[5] = (byte) (this.f9209a >>> 15);
                this.f9209a = -2027781029;
                r0[6] = (byte) (this.f9209a >>> 11);
                this.f9209a = 1075422348;
                r0[7] = (byte) (this.f9209a >>> 5);
                this.f9209a = -1506462008;
                r0[8] = (byte) (this.f9209a >>> 15);
                this.f9209a = 1231375103;
                r0[9] = (byte) (this.f9209a >>> 19);
                this.f9209a = -644791617;
                r0[10] = (byte) (this.f9209a >>> 22);
                this.f9209a = -1412508669;
                r0[11] = (byte) (this.f9209a >>> 4);
                this.f9209a = -1213432602;
                r0[12] = (byte) (this.f9209a >>> 20);
                this.f9209a = 1963683952;
                r0[13] = (byte) (this.f9209a >>> 1);
                this.f9209a = -145400071;
                r0[14] = (byte) (this.f9209a >>> 10);
                this.f9209a = 1875206718;
                r0[15] = (byte) (this.f9209a >>> 13);
                return new String(r0);
            }
        }.toString();
    }

    /* renamed from: l */
    public static String m13101l() {
        return new Object() {
            /* renamed from: a */
            int f9210a;

            public String toString() {
                r0 = new byte[50];
                this.f9210a = 1748961344;
                r0[0] = (byte) (this.f9210a >>> 16);
                this.f9210a = 733408092;
                r0[1] = (byte) (this.f9210a >>> 3);
                this.f9210a = 1915698508;
                r0[2] = (byte) (this.f9210a >>> 2);
                this.f9210a = -610650695;
                r0[3] = (byte) (this.f9210a >>> 8);
                this.f9210a = -1833414587;
                r0[4] = (byte) (this.f9210a >>> 9);
                this.f9210a = 710755243;
                r0[5] = (byte) (this.f9210a >>> 10);
                this.f9210a = -1966242239;
                r0[6] = (byte) (this.f9210a >>> 19);
                this.f9210a = 2013847863;
                r0[7] = (byte) (this.f9210a >>> 9);
                this.f9210a = -1297991548;
                r0[8] = (byte) (this.f9210a >>> 2);
                this.f9210a = -1629668290;
                r0[9] = (byte) (this.f9210a >>> 17);
                this.f9210a = 1395312367;
                r0[10] = (byte) (this.f9210a >>> 4);
                this.f9210a = 383630200;
                r0[11] = (byte) (this.f9210a >>> 22);
                this.f9210a = 458468302;
                r0[12] = (byte) (this.f9210a >>> 11);
                this.f9210a = 1769850473;
                r0[13] = (byte) (this.f9210a >>> 4);
                this.f9210a = 1209178569;
                r0[14] = (byte) (this.f9210a >>> 7);
                this.f9210a = 1701124779;
                r0[15] = (byte) (this.f9210a >>> 3);
                this.f9210a = -1624828247;
                r0[16] = (byte) (this.f9210a >>> 7);
                this.f9210a = 581284651;
                r0[17] = (byte) (this.f9210a >>> 15);
                this.f9210a = -2140686275;
                r0[18] = (byte) (this.f9210a >>> 7);
                this.f9210a = 1291097231;
                r0[19] = (byte) (this.f9210a >>> 21);
                this.f9210a = 1504938734;
                r0[20] = (byte) (this.f9210a >>> 12);
                this.f9210a = -1251227850;
                r0[21] = (byte) (this.f9210a >>> 18);
                this.f9210a = 1733967305;
                r0[22] = (byte) (this.f9210a >>> 2);
                this.f9210a = 366406377;
                r0[23] = (byte) (this.f9210a >>> 14);
                this.f9210a = 483939836;
                r0[24] = (byte) (this.f9210a >>> 14);
                this.f9210a = -389836815;
                r0[25] = (byte) (this.f9210a >>> 21);
                this.f9210a = -1208243749;
                r0[26] = (byte) (this.f9210a >>> 7);
                this.f9210a = 2098557969;
                r0[27] = (byte) (this.f9210a >>> 14);
                this.f9210a = 1450222276;
                r0[28] = (byte) (this.f9210a >>> 7);
                this.f9210a = 662963414;
                r0[29] = (byte) (this.f9210a >>> 5);
                this.f9210a = -1275901816;
                r0[30] = (byte) (this.f9210a >>> 8);
                this.f9210a = 1632367724;
                r0[31] = (byte) (this.f9210a >>> 19);
                this.f9210a = 1715292235;
                r0[32] = (byte) (this.f9210a >>> 10);
                this.f9210a = -1523848812;
                r0[33] = (byte) (this.f9210a >>> 13);
                this.f9210a = -1471394602;
                r0[34] = (byte) (this.f9210a >>> 17);
                this.f9210a = 1956469843;
                r0[35] = (byte) (this.f9210a >>> 18);
                this.f9210a = 1669480763;
                r0[36] = (byte) (this.f9210a >>> 3);
                this.f9210a = -321882675;
                r0[37] = (byte) (this.f9210a >>> 21);
                this.f9210a = 509591513;
                r0[38] = (byte) (this.f9210a >>> 22);
                this.f9210a = 404820427;
                r0[39] = (byte) (this.f9210a >>> 3);
                this.f9210a = 1155351698;
                r0[40] = (byte) (this.f9210a >>> 8);
                this.f9210a = 1397758681;
                r0[41] = (byte) (this.f9210a >>> 6);
                this.f9210a = 1407876858;
                r0[42] = (byte) (this.f9210a >>> 9);
                this.f9210a = -80507156;
                r0[43] = (byte) (this.f9210a >>> 16);
                this.f9210a = 882858469;
                r0[44] = (byte) (this.f9210a >>> 2);
                this.f9210a = 839159394;
                r0[45] = (byte) (this.f9210a >>> 12);
                this.f9210a = 308957427;
                r0[46] = (byte) (this.f9210a >>> 17);
                this.f9210a = -1657011054;
                r0[47] = (byte) (this.f9210a >>> 22);
                this.f9210a = -1646008178;
                r0[48] = (byte) (this.f9210a >>> 12);
                this.f9210a = -1511721590;
                r0[49] = (byte) (this.f9210a >>> 2);
                return new String(r0);
            }
        }.toString();
    }

    /* renamed from: m */
    public static String m13102m() {
        return new Object() {
            /* renamed from: a */
            int f9211a;

            public String toString() {
                r0 = new byte[50];
                this.f9211a = 145891398;
                r0[0] = (byte) (this.f9211a >>> 21);
                this.f9211a = 319737629;
                r0[1] = (byte) (this.f9211a >>> 19);
                this.f9211a = -851587979;
                r0[2] = (byte) (this.f9211a >>> 1);
                this.f9211a = 1942148620;
                r0[3] = (byte) (this.f9211a >>> 24);
                this.f9211a = -392868703;
                r0[4] = (byte) (this.f9211a >>> 18);
                this.f9211a = 695341963;
                r0[5] = (byte) (this.f9211a >>> 11);
                this.f9211a = -716409239;
                r0[6] = (byte) (this.f9211a >>> 22);
                this.f9211a = -745655375;
                r0[7] = (byte) (this.f9211a >>> 3);
                this.f9211a = 1572494708;
                r0[8] = (byte) (this.f9211a >>> 22);
                this.f9211a = -856020485;
                r0[9] = (byte) (this.f9211a >>> 21);
                this.f9211a = 1160389040;
                r0[10] = (byte) (this.f9211a >>> 13);
                this.f9211a = -1138009353;
                r0[11] = (byte) (this.f9211a >>> 15);
                this.f9211a = -1783476828;
                r0[12] = (byte) (this.f9211a >>> 15);
                this.f9211a = 512218506;
                r0[13] = (byte) (this.f9211a >>> 2);
                this.f9211a = 1124761521;
                r0[14] = (byte) (this.f9211a >>> 19);
                this.f9211a = 1893819989;
                r0[15] = (byte) (this.f9211a >>> 1);
                this.f9211a = 732259226;
                r0[16] = (byte) (this.f9211a >>> 23);
                this.f9211a = 1832196843;
                r0[17] = (byte) (this.f9211a >>> 18);
                this.f9211a = 313211973;
                r0[18] = (byte) (this.f9211a >>> 7);
                this.f9211a = 457247168;
                r0[19] = (byte) (this.f9211a >>> 23);
                this.f9211a = -1095852606;
                r0[20] = (byte) (this.f9211a >>> 17);
                this.f9211a = -1805716531;
                r0[21] = (byte) (this.f9211a >>> 9);
                this.f9211a = 1020315532;
                r0[22] = (byte) (this.f9211a >>> 24);
                this.f9211a = -215381177;
                r0[23] = (byte) (this.f9211a >>> 19);
                this.f9211a = 111191823;
                r0[24] = (byte) (this.f9211a >>> 10);
                this.f9211a = -1820812176;
                r0[25] = (byte) (this.f9211a >>> 22);
                this.f9211a = 746582634;
                r0[26] = (byte) (this.f9211a >>> 24);
                this.f9211a = 513172472;
                r0[27] = (byte) (this.f9211a >>> 22);
                this.f9211a = -176518283;
                r0[28] = (byte) (this.f9211a >>> 18);
                this.f9211a = -42447201;
                r0[29] = (byte) (this.f9211a >>> 8);
                this.f9211a = -1409422587;
                r0[30] = (byte) (this.f9211a >>> 21);
                this.f9211a = -1199087777;
                r0[31] = (byte) (this.f9211a >>> 6);
                this.f9211a = 512388206;
                r0[32] = (byte) (this.f9211a >>> 11);
                this.f9211a = 9259597;
                r0[33] = (byte) (this.f9211a >>> 1);
                this.f9211a = 1823149385;
                r0[34] = (byte) (this.f9211a >>> 21);
                this.f9211a = -99305106;
                r0[35] = (byte) (this.f9211a >>> 10);
                this.f9211a = 1592849782;
                r0[36] = (byte) (this.f9211a >>> 22);
                this.f9211a = -1628304960;
                r0[37] = (byte) (this.f9211a >>> 5);
                this.f9211a = -1507479708;
                r0[38] = (byte) (this.f9211a >>> 7);
                this.f9211a = 686146935;
                r0[39] = (byte) (this.f9211a >>> 18);
                this.f9211a = -850755381;
                r0[40] = (byte) (this.f9211a >>> 2);
                this.f9211a = 1102112889;
                r0[41] = (byte) (this.f9211a >>> 9);
                this.f9211a = 317194826;
                r0[42] = (byte) (this.f9211a >>> 22);
                this.f9211a = 1852610100;
                r0[43] = (byte) (this.f9211a >>> 9);
                this.f9211a = -1142678850;
                r0[44] = (byte) (this.f9211a >>> 7);
                this.f9211a = -1589078898;
                r0[45] = (byte) (this.f9211a >>> 6);
                this.f9211a = 1605332530;
                r0[46] = (byte) (this.f9211a >>> 22);
                this.f9211a = 1794743168;
                r0[47] = (byte) (this.f9211a >>> 21);
                this.f9211a = 1696409484;
                r0[48] = (byte) (this.f9211a >>> 4);
                this.f9211a = -22606017;
                r0[49] = (byte) (this.f9211a >>> 12);
                return new String(r0);
            }
        }.toString();
    }

    /* renamed from: p */
    public static byte[] m13103p(String str) {
        byte[] bArr = null;
        try {
            bArr = new C2861a(C2818c.m13100k(), C2818c.m13101l(), C2818c.m13102m()).m13323a(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encode(LZ4Factory.fastestInstance().fastCompressor().compress(bArr), 2);
    }

    /* renamed from: q */
    public static String m13104q(String str) {
        String str2 = null;
        try {
            str2 = new C2861a(new Object() {
                /* renamed from: a */
                int f9212a;

                public String toString() {
                    r0 = new byte[16];
                    this.f9212a = -921327696;
                    r0[0] = (byte) (this.f9212a >>> 21);
                    this.f9212a = -1454805131;
                    r0[1] = (byte) (this.f9212a >>> 18);
                    this.f9212a = -997177012;
                    r0[2] = (byte) (this.f9212a >>> 18);
                    this.f9212a = 848718914;
                    r0[3] = (byte) (this.f9212a >>> 24);
                    this.f9212a = -1312795763;
                    r0[4] = (byte) (this.f9212a >>> 18);
                    this.f9212a = 1492750920;
                    r0[5] = (byte) (this.f9212a >>> 3);
                    this.f9212a = 445447860;
                    r0[6] = (byte) (this.f9212a >>> 22);
                    this.f9212a = -1022029244;
                    r0[7] = (byte) (this.f9212a >>> 3);
                    this.f9212a = 1135421775;
                    r0[8] = (byte) (this.f9212a >>> 7);
                    this.f9212a = -650566676;
                    r0[9] = (byte) (this.f9212a >>> 11);
                    this.f9212a = -687928167;
                    r0[10] = (byte) (this.f9212a >>> 6);
                    this.f9212a = -2106923253;
                    r0[11] = (byte) (this.f9212a >>> 4);
                    this.f9212a = 136663648;
                    r0[12] = (byte) (this.f9212a >>> 6);
                    this.f9212a = 1588933977;
                    r0[13] = (byte) (this.f9212a >>> 7);
                    this.f9212a = 462980392;
                    r0[14] = (byte) (this.f9212a >>> 15);
                    this.f9212a = 365316230;
                    r0[15] = (byte) (this.f9212a >>> 13);
                    return new String(r0);
                }
            }.toString(), new Object() {
                /* renamed from: a */
                int f9213a;

                public String toString() {
                    byte[] bArr = new byte[29];
                    this.f9213a = -1323374672;
                    bArr[0] = (byte) (this.f9213a >>> 9);
                    this.f9213a = -1697530697;
                    bArr[1] = (byte) (this.f9213a >>> 17);
                    this.f9213a = -553214828;
                    bArr[2] = (byte) (this.f9213a >>> 12);
                    this.f9213a = -1183682831;
                    bArr[3] = (byte) (this.f9213a >>> 16);
                    this.f9213a = 622355557;
                    bArr[4] = (byte) (this.f9213a >>> 14);
                    this.f9213a = -1869800696;
                    bArr[5] = (byte) (this.f9213a >>> 6);
                    this.f9213a = 115773841;
                    bArr[6] = (byte) (this.f9213a >>> 12);
                    this.f9213a = 601159015;
                    bArr[7] = (byte) (this.f9213a >>> 9);
                    this.f9213a = -1502212024;
                    bArr[8] = (byte) (this.f9213a >>> 12);
                    this.f9213a = -1316186378;
                    bArr[9] = (byte) (this.f9213a >>> 13);
                    this.f9213a = -1523525308;
                    bArr[10] = (byte) (this.f9213a >>> 6);
                    this.f9213a = -852937073;
                    bArr[11] = (byte) (this.f9213a >>> 4);
                    this.f9213a = -790793511;
                    bArr[12] = (byte) (this.f9213a >>> 4);
                    this.f9213a = 1133015572;
                    bArr[13] = (byte) (this.f9213a >>> 8);
                    this.f9213a = 1637923373;
                    bArr[14] = (byte) (this.f9213a >>> 24);
                    this.f9213a = 1488246512;
                    bArr[15] = (byte) (this.f9213a >>> 22);
                    this.f9213a = 162058779;
                    bArr[16] = (byte) (this.f9213a >>> 9);
                    this.f9213a = -1701493558;
                    bArr[17] = (byte) (this.f9213a >>> 1);
                    this.f9213a = -950482349;
                    bArr[18] = (byte) (this.f9213a >>> 9);
                    this.f9213a = 615001492;
                    bArr[19] = (byte) (this.f9213a >>> 8);
                    this.f9213a = -1615639500;
                    bArr[20] = (byte) (this.f9213a >>> 5);
                    this.f9213a = 324922794;
                    bArr[21] = (byte) (this.f9213a >>> 5);
                    this.f9213a = -1497713243;
                    bArr[22] = (byte) (this.f9213a >>> 2);
                    this.f9213a = 132316049;
                    bArr[23] = (byte) (this.f9213a >>> 3);
                    this.f9213a = -349379853;
                    bArr[24] = (byte) (this.f9213a >>> 1);
                    this.f9213a = 1500385852;
                    bArr[25] = (byte) (this.f9213a >>> 19);
                    this.f9213a = 1255987002;
                    bArr[26] = (byte) (this.f9213a >>> 14);
                    this.f9213a = 727776337;
                    bArr[27] = (byte) (this.f9213a >>> 19);
                    this.f9213a = -1060285537;
                    bArr[28] = (byte) (this.f9213a >>> 3);
                    return new String(bArr);
                }
            }.toString(), new Object() {
                /* renamed from: a */
                int f9214a;

                public String toString() {
                    r0 = new byte[38];
                    this.f9214a = 1846853717;
                    r0[0] = (byte) (this.f9214a >>> 24);
                    this.f9214a = -457943302;
                    r0[1] = (byte) (this.f9214a >>> 4);
                    this.f9214a = -1657788742;
                    r0[2] = (byte) (this.f9214a >>> 22);
                    this.f9214a = -316881697;
                    r0[3] = (byte) (this.f9214a >>> 21);
                    this.f9214a = -1389270379;
                    r0[4] = (byte) (this.f9214a >>> 21);
                    this.f9214a = -1229048018;
                    r0[5] = (byte) (this.f9214a >>> 7);
                    this.f9214a = -1393597039;
                    r0[6] = (byte) (this.f9214a >>> 21);
                    this.f9214a = 25351900;
                    r0[7] = (byte) (this.f9214a >>> 12);
                    this.f9214a = -670706977;
                    r0[8] = (byte) (this.f9214a >>> 10);
                    this.f9214a = 534508768;
                    r0[9] = (byte) (this.f9214a >>> 14);
                    this.f9214a = 1293329753;
                    r0[10] = (byte) (this.f9214a >>> 15);
                    this.f9214a = -610414205;
                    r0[11] = (byte) (this.f9214a >>> 19);
                    this.f9214a = -1377839407;
                    r0[12] = (byte) (this.f9214a >>> 1);
                    this.f9214a = 935520358;
                    r0[13] = (byte) (this.f9214a >>> 23);
                    this.f9214a = 1493569260;
                    r0[14] = (byte) (this.f9214a >>> 5);
                    this.f9214a = 689348297;
                    r0[15] = (byte) (this.f9214a >>> 15);
                    this.f9214a = 429671249;
                    r0[16] = (byte) (this.f9214a >>> 23);
                    this.f9214a = -2073667319;
                    r0[17] = (byte) (this.f9214a >>> 13);
                    this.f9214a = -529863353;
                    r0[18] = (byte) (this.f9214a >>> 16);
                    this.f9214a = 1911096426;
                    r0[19] = (byte) (this.f9214a >>> 13);
                    this.f9214a = -1332100585;
                    r0[20] = (byte) (this.f9214a >>> 11);
                    this.f9214a = 1096227095;
                    r0[21] = (byte) (this.f9214a >>> 19);
                    this.f9214a = 1757390365;
                    r0[22] = (byte) (this.f9214a >>> 4);
                    this.f9214a = -9331610;
                    r0[23] = (byte) (this.f9214a >>> 6);
                    this.f9214a = 74053918;
                    r0[24] = (byte) (this.f9214a >>> 21);
                    this.f9214a = -190420129;
                    r0[25] = (byte) (this.f9214a >>> 13);
                    this.f9214a = 1310801925;
                    r0[26] = (byte) (this.f9214a >>> 15);
                    this.f9214a = -974174850;
                    r0[27] = (byte) (this.f9214a >>> 21);
                    this.f9214a = -2082445906;
                    r0[28] = (byte) (this.f9214a >>> 9);
                    this.f9214a = 1191342008;
                    r0[29] = (byte) (this.f9214a >>> 20);
                    this.f9214a = -1187927464;
                    r0[30] = (byte) (this.f9214a >>> 16);
                    this.f9214a = 737697723;
                    r0[31] = (byte) (this.f9214a >>> 21);
                    this.f9214a = -188773899;
                    r0[32] = (byte) (this.f9214a >>> 20);
                    this.f9214a = 870997882;
                    r0[33] = (byte) (this.f9214a >>> 24);
                    this.f9214a = 686637404;
                    r0[34] = (byte) (this.f9214a >>> 2);
                    this.f9214a = -1597208344;
                    r0[35] = (byte) (this.f9214a >>> 13);
                    this.f9214a = -1818999423;
                    r0[36] = (byte) (this.f9214a >>> 19);
                    this.f9214a = 1651707427;
                    r0[37] = (byte) (this.f9214a >>> 5);
                    return new String(r0);
                }
            }.toString()).m13324b(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str2;
    }

    /* renamed from: s */
    private byte[] m13105s(String str) {
        byte[] bArr = new byte[0];
        try {
            bArr = str.getBytes(C3446C.UTF8_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bArr;
    }

    /* renamed from: t */
    private boolean m13106t(String str) {
        Log.d("LEE", "retryByAnotherAddress");
        C3791b.m13978f(this.f9271h.mo3484e());
        this.f9274k++;
        String a = C2822f.m13156a(this.f9274k, this.f9269e, this.f9270g, this.f9271h.mo3484e(), this.f9271h.mo3484e());
        if (a == null) {
            return false;
        }
        Request anonymousClass27 = new C2758b(this, this.f9271h.mo3481b().getMethod(), a, this, this, this.f9270g, true, this.f9271h.mo3484e()) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9156b;

            public byte[] getBody() {
                return this.f9156b.f9271h.mo3481b().getBody();
            }

            public String getBodyContentType() {
                return this.f9156b.f9271h.mo3481b().getBodyContentType();
            }

            public Map<String, String> getHeaders() {
                try {
                    return this.f9156b.f9271h.mo3481b().getHeaders();
                } catch (Exception e) {
                    return super.getHeaders();
                }
            }

            protected Map<String, String> getParams() {
                return super.getParams();
            }

            public String getPostBodyContentType() {
                return this.f9156b.f9271h.mo3481b().getPostBodyContentType();
            }
        };
        RetryPolicy a2 = this.f9271h.mo3478a();
        if (a2 == null) {
            anonymousClass27.setRetryPolicy(new DefaultRetryPolicy(C2822f.m13155a(this.f9270g), 0, 1.0f));
        } else {
            anonymousClass27.setRetryPolicy(a2);
        }
        anonymousClass27.setShouldCache(true);
        if (this.f9271h.mo3484e().contentEquals("set-1062") && !anonymousClass27.getUrl().startsWith("https")) {
            return true;
        }
        C2818c.m13089a().add(anonymousClass27);
        return true;
    }

    /* renamed from: a */
    public void m13107a(int i, int i2, long j, long j2) {
        final int i3 = i;
        final int i4 = i2;
        final long j3 = j;
        final long j4 = j2;
        m13091a(new C2760a(this) {
            /* renamed from: e */
            final /* synthetic */ C2818c f9187e;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                ResponseReportHelper responseReportHelper = (ResponseReportHelper) c2757a.m12801a();
                if (responseReportHelper != null) {
                    this.f9187e.f9272i.onResult(responseReportHelper.getResponse(), 33);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                this.f9187e.f9272i.onResult(null, -33);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 0, String.format("v12/payment/gpr?pageIndex=%s&pageCount=%s&startDate=%s&endDate=%s", new Object[]{Integer.valueOf(i3), Integer.valueOf(i4), Long.valueOf(j3), Long.valueOf(j4)}), this.f9187e, this.f9187e, this.f9187e.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass36 f9182b;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    public String getBodyContentType() {
                        return "Application/json";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return false;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return ResponseReportHelper.class;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1062";
            }

            public String toString() {
                return "WS_REPORT_PAYMENT";
            }
        });
    }

    /* renamed from: a */
    public void m13108a(final long j, final int i) {
        m13091a(new C2760a(this) {
            /* renamed from: c */
            final /* synthetic */ C2818c f9130c;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return new DefaultRetryPolicy(0, 1, 1.0f);
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                if (c2757a != null && c2757a.m12805b() != -1) {
                    this.f9130c.f9272i.onResult(c2757a.m12801a(), 19);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                C2757a c2757a = new C2757a();
                c2757a.m12802a(-1);
                c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                this.f9130c.f9272i.onResult(c2757a, -19);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(0, String.format("v12/Health/cif?u=%s&c=%s", new Object[]{Long.valueOf(j), Integer.valueOf(i)}), this.f9130c, this.f9130c, this.f9130c.f9270g, mo3484e());
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return false;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return FilterResponse.class;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1057";
            }

            public String toString() {
                return "WS_GET_SINGLE_FILTER";
            }
        });
    }

    /* renamed from: a */
    public void m13109a(long j, long j2, long j3, String str) {
        final long j4 = j;
        final long j5 = j2;
        final long j6 = j3;
        final String str2 = str;
        m13091a(new C2760a(this) {
            /* renamed from: e */
            final /* synthetic */ C2818c f9193e;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                PaymentLink paymentLink = (PaymentLink) c2757a.m12801a();
                if (paymentLink != null && !TextUtils.isEmpty(paymentLink.getPaymentId()) && !TextUtils.isEmpty(paymentLink.getLink())) {
                    this.f9193e.f9272i.onResult(paymentLink, 32);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                this.f9193e.f9272i.onResult(null, -32);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 1, String.format("v12/payment/gpl?fromTelegramUserId=%s&toTelegramUserId=%s&amount=%s&description=%s", new Object[]{Long.valueOf(j4), Long.valueOf(j5), Long.valueOf(j6), str2}), this.f9193e, this.f9193e, this.f9193e.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass37 f9188b;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    public String getBodyContentType() {
                        return "text/plain";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return false;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return PaymentLink.class;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1062";
            }

            public String toString() {
                return "WS_POST_T_M";
            }
        });
    }

    /* renamed from: a */
    public void m13110a(long j, long j2, boolean z, long j3, int i) {
        final long j4 = j2;
        final long j5 = j;
        final boolean z2 = z;
        final int i2 = i;
        final long j6 = j3;
        m13091a(new C2760a(this) {
            /* renamed from: f */
            final /* synthetic */ C2818c f9222f;

            /* renamed from: org.telegram.customization.g.c$47$1 */
            class C27991 extends C1748a<ArrayList<ObjBase>> {
                /* renamed from: d */
                final /* synthetic */ AnonymousClass47 f9215d;

                C27991(AnonymousClass47 anonymousClass47) {
                    this.f9215d = anonymousClass47;
                }
            }

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                String str = null;
                if (k.m8426a(DataBufferSafeParcelable.DATA_FIELD) != null) {
                    str = k.m8426a(DataBufferSafeParcelable.DATA_FIELD).toString();
                }
                this.f9222f.f9272i.onResult((ArrayList) new C1769g().m8403a(new GsonAdapterFactory()).m8402a().m8393a(str, new C27991(this).m8360b()), 1);
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                C2757a c2757a = new C2757a();
                c2757a.m12802a(-1);
                c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                this.f9222f.f9272i.onResult(c2757a, -1);
            }

            /* renamed from: b */
            public Request mo3481b() {
                if (j4 == 0) {
                    C3791b.m14011l(this.f9222f.f9270g);
                }
                String str = "v12/content/getHome?tagId=%s&direction=%s&pageSize=%s&mediaType=%s&lastRow=%s&itr=%s";
                Object[] objArr = new Object[6];
                objArr[0] = Long.valueOf(j5);
                objArr[1] = z2 ? "previous" : "next";
                objArr[2] = Integer.valueOf(i2);
                objArr[3] = Long.valueOf(j6);
                objArr[4] = Long.valueOf(j4);
                objArr[5] = Long.valueOf(C3791b.m14015m(this.f9222f.f9270g));
                return new C2758b(this, 0, String.format(str, objArr), this.f9222f, this.f9222f, this.f9222f.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass47 f9216b;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1051";
            }

            public String toString() {
                return "WS_GET_HOME";
            }
        });
    }

    /* renamed from: a */
    public void m13111a(long j, long j2, boolean z, String str, long j3, int i, long j4, boolean z2) {
        final String str2 = str;
        final boolean z3 = z;
        final long j5 = j2;
        final long j6 = j3;
        final int i2 = i;
        final boolean z4 = z2;
        final long j7 = j4;
        m13091a(new C2805b(this) {
            /* renamed from: h */
            final /* synthetic */ C2818c f9240h;

            /* renamed from: org.telegram.customization.g.c$49$1 */
            class C28031 extends C1748a<ArrayList<ObjBase>> {
                /* renamed from: d */
                final /* synthetic */ AnonymousClass49 f9231d;

                C28031(AnonymousClass49 anonymousClass49) {
                    this.f9231d = anonymousClass49;
                }
            }

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                String str = null;
                if (k.m8426a(DataBufferSafeParcelable.DATA_FIELD) != null) {
                    str = k.m8426a(DataBufferSafeParcelable.DATA_FIELD).toString();
                }
                this.f9240h.f9272i.onResult((ArrayList) new C1769g().m8403a(new GsonAdapterFactory()).m8402a().m8393a(str, new C28031(this).m8360b()), 1);
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                C2757a c2757a = new C2757a();
                c2757a.m12802a(-1);
                c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                this.f9240h.f9272i.onResult(c2757a, -1);
            }

            /* renamed from: b */
            public Request mo3481b() {
                String str = str2;
                try {
                    URLEncoder.encode(str2, C3446C.UTF8_NAME);
                } catch (Exception e) {
                }
                return new C2758b(this, 1, "v12/content/getSearch", this.f9240h, this.f9240h, this.f9240h.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass49 f9232b;

                    public byte[] getBody() {
                        Object searchRequest = new SearchRequest();
                        searchRequest.setDirection(z3 ? 1 : 0);
                        searchRequest.setLastRow(j5);
                        searchRequest.setMediaType(j6);
                        searchRequest.setPageSize(i2);
                        searchRequest.setPhrasesearch(z4);
                        searchRequest.setSortOrder(j7);
                        searchRequest.setSearchTerm(str2);
                        return C2818c.m13103p(new C1768f().m8395a(searchRequest));
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1051";
            }

            /* renamed from: f */
            public String mo3487f() {
                return "v12/content/getSearch";
            }

            public String toString() {
                return "WS_GET_SEARCH";
            }
        });
    }

    /* renamed from: a */
    public void m13112a(long j, String str, View view) {
        final View view2 = view;
        final String str2 = str;
        final long j2 = j;
        m13091a(new C2760a(this) {
            /* renamed from: d */
            final /* synthetic */ C2818c f9261d;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                C3744b c3744b = (C3744b) new C1768f().m8392a((String) c2757a.m12801a(), C3744b.class);
                this.f9261d.f9272i.onResult(new Object[]{view2, c3744b}, 12);
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                C2757a c2757a = new C2757a();
                c2757a.m12802a(-1);
                c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                this.f9261d.f9272i.onResult(c2757a, -12);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new StringRequest(this, 0, String.format(C3791b.m14045t(this.f9261d.f9270g) + "generalcontent/v5/getitem?newsId=%s&ParentTagId=%s", new Object[]{str2, Long.valueOf(j2)}), this.f9261d, this.f9261d) {
                    /* renamed from: a */
                    final /* synthetic */ C28179 f9257a;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    public Map<String, String> getHeaders() {
                        Map<String, String> hashMap = new HashMap();
                        hashMap.put("X-SLS-Version", String.valueOf(BuildConfig.VERSION_CODE));
                        String str = "550205994";
                        String str2 = "-1";
                        if (str == null || str.length() <= 0) {
                            hashMap.put("X-SLS-DeviceId", String.valueOf(0));
                        } else {
                            hashMap.put("X-SLS-DeviceId", String.valueOf(str));
                        }
                        if (str2 == null || str2.length() <= 0) {
                            hashMap.put("X-SLS-UserId", String.valueOf(0));
                        } else {
                            hashMap.put("X-SLS-UserId", String.valueOf(str2));
                        }
                        hashMap.put("X-SLS-AppId", String.valueOf(4));
                        try {
                            hashMap.put("X-SLS-Token", "dVQGpLWTfXBOs7dH2FI37LX+MmI=");
                        } catch (Exception e) {
                        }
                        return hashMap;
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return null;
            }

            public String toString() {
                return "WS_GET_NEWS_LIST";
            }
        });
    }

    /* renamed from: a */
    public void m13113a(long j, String str, String str2, int i) {
        final long j2 = j;
        final int i2 = i;
        final String str3 = str;
        final String str4 = str2;
        m13091a(new C2760a(this) {
            /* renamed from: e */
            final /* synthetic */ C2818c f9256e;

            /* renamed from: org.telegram.customization.g.c$8$1 */
            class C28131 extends C1748a<ArrayList<C3744b>> {
                /* renamed from: d */
                final /* synthetic */ C28158 f9250d;

                C28131(C28158 c28158) {
                    this.f9250d = c28158;
                }
            }

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                String str = (String) c2757a.m12801a();
                C1768f c1768f = new C1768f();
                C1776o k = ((C1771l) c1768f.m8392a(str, C1771l.class)).m8415k();
                Object arrayList = new ArrayList();
                if (k.m8426a("result") != null) {
                    arrayList = (ArrayList) c1768f.m8393a(k.m8426a("result").toString(), new C28131(this).m8360b());
                }
                this.f9256e.f9272i.onResult(arrayList, 11);
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                C2757a c2757a = new C2757a();
                c2757a.m12802a(-1);
                c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                this.f9256e.f9272i.onResult(c2757a, -11);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new StringRequest(this, 0, String.format(C3791b.m14045t(this.f9256e.f9270g) + "generalcontent/v5/GetListByTagId?tagId=%s&count=%s&nid=%s&dir=%s&itemId=%s&date=&legendId=%s", new Object[]{Long.valueOf(j2), Integer.valueOf(i2), str3, str4, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID}), this.f9256e, this.f9256e) {
                    /* renamed from: a */
                    final /* synthetic */ C28158 f9251a;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    public Map<String, String> getHeaders() {
                        Map<String, String> hashMap = new HashMap();
                        hashMap.put("X-SLS-Version", String.valueOf(BuildConfig.VERSION_CODE));
                        String str = "550205994";
                        String str2 = "-1";
                        if (str == null || str.length() <= 0) {
                            hashMap.put("X-SLS-DeviceId", String.valueOf(0));
                        } else {
                            hashMap.put("X-SLS-DeviceId", String.valueOf(str));
                        }
                        if (str2 == null || str2.length() <= 0) {
                            hashMap.put("X-SLS-UserId", String.valueOf(0));
                        } else {
                            hashMap.put("X-SLS-UserId", String.valueOf(str2));
                        }
                        hashMap.put("X-SLS-AppId", String.valueOf(4));
                        try {
                            hashMap.put("X-SLS-Token", "dVQGpLWTfXBOs7dH2FI37LX+MmI=");
                        } catch (Exception e) {
                        }
                        return hashMap;
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return null;
            }

            public String toString() {
                return "WS_GET_NEWS_LIST";
            }
        });
    }

    /* renamed from: a */
    public void m13114a(Runnable runnable) {
        if (this.f9273j == null) {
            return;
        }
        if (this.f9268a != null) {
            this.f9268a.post(runnable);
        } else {
            runnable.run();
        }
    }

    /* renamed from: a */
    public void m13115a(final String str) {
        Log.d("LEE", "HandleRequest onResponse");
        try {
            if (this.f9268a != null) {
                new Thread(this) {
                    /* renamed from: b */
                    final /* synthetic */ C2818c f9124b;

                    public void run() {
                        super.run();
                        this.f9124b.m13127b(str);
                    }
                }.start();
            } else {
                m13127b(str);
            }
        } catch (Exception e) {
        }
    }

    /* renamed from: a */
    public void m13116a(final String str, final String str2) {
        m13091a(new C2760a(this) {
            /* renamed from: c */
            final /* synthetic */ C2818c f9178c;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                HostRequestData hostRequestData = (HostRequestData) c2757a.m12801a();
                if (hostRequestData != null) {
                    this.f9178c.f9272i.onResult(hostRequestData, 36);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                this.f9178c.f9272i.onResult(null, -36);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 1, String.format("v12/payment/fp?paymentId=%s&description=%s", new Object[]{str, str2}), this.f9178c, this.f9178c, this.f9178c.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass34 f9175b;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    public String getBodyContentType() {
                        return "text/plain";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return false;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return HostRequestData.class;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1062";
            }

            public String toString() {
                return "WS_POST_T_M";
            }
        });
    }

    /* renamed from: a */
    public void m13117a(final ArrayList<Category> arrayList) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9143b;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                try {
                    C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                    String str = null;
                    if (k.m8426a("code") != null) {
                        str = k.m8426a("code").toString();
                    }
                    if (k.m8426a("message") != null) {
                        str = k.m8426a("message").toString();
                    }
                    if (Integer.parseInt(str) == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                        this.f9143b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, 20);
                    } else {
                        this.f9143b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, -20);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    C2757a c2757a2 = new C2757a();
                    c2757a2.m12802a(-1);
                    c2757a2.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                    this.f9143b.f9272i.onResult(c2757a2, -20);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 2, String.format("v12/ad/ManageCategory ", new Object[0]), this.f9143b, this.f9143b, this.f9143b.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass22 f9141b;

                    public byte[] getBody() {
                        return new C1768f().m8395a(arrayList).getBytes();
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1051";
            }

            public String toString() {
                return "WS_POST_SEND_CONTACTS";
            }
        });
    }

    /* renamed from: a */
    public void m13118a(final ArrayList<DocAvailableInfo> arrayList, final long j) {
        if (arrayList != null && arrayList.size() >= 1) {
            f9262b = System.currentTimeMillis();
            m13091a(new C2760a(this) {
                /* renamed from: c */
                final /* synthetic */ C2818c f9204c;

                /* renamed from: a */
                public RetryPolicy mo3478a() {
                    return new DefaultRetryPolicy(7000, 0, 1.0f);
                }

                /* renamed from: a */
                public void mo3479a(C2757a c2757a) {
                    try {
                        C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                        String c1771l = k.m8426a(DataBufferSafeParcelable.DATA_FIELD).toString() != null ? k.m8426a(DataBufferSafeParcelable.DATA_FIELD).toString() : null;
                        CheckUrlMeta checkUrlMeta = k.m8426a("meta").toString() != null ? (CheckUrlMeta) new C1768f().m8392a(k.m8426a("meta").toString(), CheckUrlMeta.class) : null;
                        CheckUrlResponseModel checkUrlResponseModel = (CheckUrlResponseModel) new C1768f().m8392a(c1771l, CheckUrlResponseModel.class);
                        if (checkUrlResponseModel != null) {
                            ArrayList arrayList = new ArrayList();
                            for (String str : checkUrlResponseModel.getUrl().keySet()) {
                                DocAvailableInfo docAvailableInfo = null;
                                if (TextUtils.isEmpty(str) || !str.contains(".")) {
                                    docAvailableInfo = new DocAvailableInfo(Long.parseLong(str), 0, 0, 0, true);
                                    docAvailableInfo.setLocalUrl((String) checkUrlResponseModel.getUrl().get(str));
                                } else {
                                    String[] split = str.split("\\.");
                                    if (split.length == 2) {
                                        docAvailableInfo = new DocAvailableInfo(0, Integer.parseInt(split[0]), Long.parseLong(split[1]), 0, true);
                                        docAvailableInfo.setLocalUrl((String) checkUrlResponseModel.getUrl().get(str));
                                    }
                                }
                                if (docAvailableInfo != null) {
                                    if (checkUrlMeta != null) {
                                        docAvailableInfo.trafficStatusLabel = checkUrlMeta.getLbl();
                                        docAvailableInfo.freeState = checkUrlMeta.getFst();
                                    }
                                    arrayList.add(docAvailableInfo);
                                }
                            }
                            C5319b.a(arrayList);
                            C5319b.a(checkUrlResponseModel.getTag());
                            this.f9204c.f9272i.onResult(arrayList, 4);
                        }
                        C2818c.f9263c++;
                        C2818c.f9264d += System.currentTimeMillis() - C2818c.f9262b;
                    } catch (Exception e) {
                        e.printStackTrace();
                        C2757a c2757a2 = new C2757a();
                        c2757a2.m12802a(-1);
                        c2757a2.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                        this.f9204c.f9272i.onResult(c2757a2, -4);
                    }
                }

                /* renamed from: a */
                public void mo3480a(VolleyError volleyError) {
                    int i = 0;
                    while (i < arrayList.size()) {
                        try {
                            ((DocAvailableInfo) arrayList.get(i)).exists = false;
                            i++;
                        } catch (Exception e) {
                        }
                    }
                    C5319b.a(arrayList);
                    C2757a c2757a = new C2757a();
                    c2757a.m12802a(-1);
                    c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                    this.f9204c.f9272i.onResult(c2757a, -4);
                }

                /* renamed from: b */
                public Request mo3481b() {
                    return new C2758b(this, 1, String.format("v3/churl?gprs=%s&car=%s&uid=%s", new Object[]{Boolean.valueOf(ConnectionsManager.isConnectedMobile(this.f9204c.f9270g)), C2822f.m13157b(this.f9204c.f9270g), Integer.valueOf(UserConfig.getClientUserId()), Long.valueOf(j)}), this.f9204c, this.f9204c, this.f9204c.f9270g, mo3484e()) {
                        /* renamed from: b */
                        final /* synthetic */ C27963 f9163b;

                        public byte[] getBody() {
                            return this.f9163b.f9204c.m13105s(new C1769g().m8403a(new NewDocAvailableInfoAdapterFactory()).m8402a().m8395a(arrayList));
                        }

                        protected Map<String, String> getParams() {
                            return super.getParams();
                        }
                    };
                }

                /* renamed from: c */
                public boolean mo3482c() {
                    return true;
                }

                /* renamed from: d */
                public Type mo3483d() {
                    return null;
                }

                /* renamed from: e */
                public String mo3484e() {
                    return "set-1065";
                }

                public String toString() {
                    return "WS_LIGHT_HOST_CHECK_URL";
                }
            });
        }
    }

    /* renamed from: a */
    public void m13119a(final org.telegram.customization.Model.Ads.Log log) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9146b;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                try {
                    C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                    String c1771l = k.m8426a("code") != null ? k.m8426a("code").toString() : null;
                    String c1771l2 = k.m8426a("message") != null ? k.m8426a("message").toString() : null;
                    int parseInt = Integer.parseInt(c1771l);
                    if (parseInt == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                        C2757a c2757a2 = new C2757a();
                        c2757a2.m12802a(parseInt);
                        c2757a2.m12804a(c1771l2);
                        this.f9146b.f9272i.onResult(c2757a2, 23);
                        return;
                    }
                    this.f9146b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, -23);
                } catch (Exception e) {
                    e.printStackTrace();
                    C2757a c2757a3 = new C2757a();
                    c2757a3.m12802a(-1);
                    c2757a3.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                    this.f9146b.f9272i.onResult(c2757a3, -23);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 1, "v12/ad/log", this.f9146b, this.f9146b, this.f9146b.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass23 f9144b;

                    public byte[] getBody() {
                        Object arrayList = new ArrayList();
                        arrayList.add(log);
                        return C2818c.m13103p(new C1768f().m8395a(arrayList));
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1051";
            }

            public String toString() {
                return "WS_POST_SEND_CONTACTS";
            }
        });
    }

    /* renamed from: a */
    public void m13120a(final HostResponseData hostResponseData) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9171b;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                this.f9171b.f9272i.onResult(c2757a, 30);
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                this.f9171b.f9272i.onResult(null, -30);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 1, "v12/payment/ic", this.f9171b, this.f9171b, this.f9171b.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass32 f9169b;

                    public byte[] getBody() {
                        return this.f9169b.f9171b.m13105s(new C1768f().m8395a(hostResponseData));
                    }

                    public String getBodyContentType() {
                        return "Application/json";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return false;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return User.class;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1062";
            }

            public String toString() {
                return "WS_POST_T_M";
            }
        });
    }

    /* renamed from: a */
    public void m13121a(final User user) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9166b;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                this.f9166b.f9272i.onResult(c2757a, 28);
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                this.f9166b.f9272i.onResult(null, -28);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 1, "v12/payment/rsu", this.f9166b, this.f9166b, this.f9166b.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass30 f9164b;

                    public byte[] getBody() {
                        return this.f9164b.f9166b.m13105s(new C1768f().m8395a(user));
                    }

                    public String getBodyContentType() {
                        return "Application/json";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return false;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1062";
            }

            public String toString() {
                return "WS_POST_T_M";
            }
        });
    }

    /* renamed from: a */
    public void m13122a(VolleyError volleyError) {
        String str;
        try {
            if (this.f9271h.mo3484e().equals("set-1056")) {
                f9266l = false;
            }
        } catch (Exception e) {
        }
        String str2 = "0";
        if (volleyError != null) {
            try {
                if (volleyError instanceof TimeoutError) {
                    str = "1000";
                    str2 = this.f9271h.mo3481b().getUrl() + " ";
                    str2 + volleyError.networkResponse.headers.toString();
                    C1333b.m6818c().m6821a((C1351m) new C1351m("NETWORK_ERROR").m6784a(this.f9271h.toString(), str + " - " + BuildConfig.VERSION_CODE + " " + "v12/"));
                    Log.d("LEE", "HandleRequest onErrorResponseInternal " + str + this.f9271h.mo3484e());
                    if (!(this.f9271h.mo3484e() == "set-1067" || this.f9271h.mo3484e() == "KEY_CALL_URL" || !m13106t(str))) {
                    }
                    if (this.f9273j != null) {
                        try {
                            this.f9271h.mo3480a(volleyError);
                            return;
                        } catch (Exception e2) {
                            return;
                        }
                    }
                    return;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (volleyError != null) {
            if (volleyError.networkResponse != null) {
                str = String.valueOf(volleyError.networkResponse.statusCode);
                str2 = this.f9271h.mo3481b().getUrl() + " ";
                str2 + volleyError.networkResponse.headers.toString();
                C1333b.m6818c().m6821a((C1351m) new C1351m("NETWORK_ERROR").m6784a(this.f9271h.toString(), str + " - " + BuildConfig.VERSION_CODE + " " + "v12/"));
                Log.d("LEE", "HandleRequest onErrorResponseInternal " + str + this.f9271h.mo3484e());
            }
        }
        str = str2;
        try {
            str2 = this.f9271h.mo3481b().getUrl() + " ";
            str2 + volleyError.networkResponse.headers.toString();
        } catch (Exception e4) {
            str2 + "bad class";
        } catch (Throwable th) {
        }
        C1333b.m6818c().m6821a((C1351m) new C1351m("NETWORK_ERROR").m6784a(this.f9271h.toString(), str + " - " + BuildConfig.VERSION_CODE + " " + "v12/"));
        Log.d("LEE", "HandleRequest onErrorResponseInternal " + str + this.f9271h.mo3484e());
        try {
        } catch (Exception e5) {
        }
    }

    /* renamed from: a */
    public void m13123a(final boolean z) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9242b;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                try {
                    C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                    String c1771l = k.m8426a("code") != null ? k.m8426a("code").toString() : null;
                    CharSequence c1771l2 = k.m8426a("message") != null ? k.m8426a("message").toString() : null;
                    if (Integer.parseInt(c1771l) == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                        this.f9242b.f9272i.onResult(Boolean.valueOf(TextUtils.isEmpty(c1771l2)), 5);
                    } else {
                        this.f9242b.f9272i.onResult(Boolean.valueOf(false), -5);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    C2757a c2757a2 = new C2757a();
                    c2757a2.m12802a(-1);
                    c2757a2.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                    this.f9242b.f9272i.onResult(c2757a2, -5);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 1, "v12/user/register", this.f9242b, this.f9242b, this.f9242b.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ C28064 f9205b;

                    public byte[] getBody() {
                        Object instance = RegisterModel.getInstance();
                        instance.setPushToken(C3791b.au(ApplicationLoader.applicationContext));
                        instance.setFromInfo(z);
                        Log.d("LEE", "RegisterModel" + new C1768f().m8395a(instance));
                        return C2818c.m13103p(new C1768f().m8395a(instance));
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1055";
            }

            public String toString() {
                return "WS_REGISTER";
            }
        });
    }

    /* renamed from: b */
    public void m13124b() {
        ArrayList f = C2885i.m13385f(this.f9270g);
        Object obj = null;
        if (3600000 + C2885i.m13383e(this.f9270g) > System.currentTimeMillis() && f != null && f.size() > 0) {
            obj = 1;
        }
        if (obj != null) {
            this.f9272i.onResult(f, 2);
        } else {
            m13091a(new C27852(this));
        }
    }

    /* renamed from: b */
    public void m13125b(int i, int i2, long j, long j2) {
        final int i3 = i;
        final int i4 = i2;
        final long j3 = j;
        final long j4 = j2;
        m13091a(new C2760a(this) {
            /* renamed from: e */
            final /* synthetic */ C2818c f9201e;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                ResponseSettleHelper responseSettleHelper = (ResponseSettleHelper) c2757a.m12801a();
                if (responseSettleHelper != null) {
                    this.f9201e.f9272i.onResult(responseSettleHelper.getResponse(), 34);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                this.f9201e.f9272i.onResult(null, -34);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 0, String.format("v12/payment/gsr?pageIndex=%s&pageCount=%s&startDate=%s&endDate=%s", new Object[]{Integer.valueOf(i3), Integer.valueOf(i4), Long.valueOf(j3), Long.valueOf(j4)}), this.f9201e, this.f9201e, this.f9201e.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass39 f9196b;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    public String getBodyContentType() {
                        return "Application/json";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return false;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return ResponseSettleHelper.class;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1062";
            }

            public String toString() {
                return "WS_REPORT_SETTLEMENT";
            }
        });
    }

    /* renamed from: b */
    public void m13126b(long j, long j2, boolean z, long j3, int i) {
        final long j4 = j;
        final boolean z2 = z;
        final int i2 = i;
        final long j5 = j3;
        final long j6 = j2;
        m13091a(new C2760a(this) {
            /* renamed from: f */
            final /* synthetic */ C2818c f9230f;

            /* renamed from: org.telegram.customization.g.c$48$1 */
            class C28011 extends C1748a<ArrayList<ObjBase>> {
                /* renamed from: d */
                final /* synthetic */ AnonymousClass48 f9223d;

                C28011(AnonymousClass48 anonymousClass48) {
                    this.f9223d = anonymousClass48;
                }
            }

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                String str = null;
                if (k.m8426a(DataBufferSafeParcelable.DATA_FIELD) != null) {
                    str = k.m8426a(DataBufferSafeParcelable.DATA_FIELD).toString();
                }
                this.f9230f.f9272i.onResult((ArrayList) new C1769g().m8403a(new GsonAdapterFactory()).m8402a().m8393a(str, new C28011(this).m8360b()), 1);
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                C2757a c2757a = new C2757a();
                c2757a.m12802a(-1);
                c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                this.f9230f.f9272i.onResult(c2757a, -1);
            }

            /* renamed from: b */
            public Request mo3481b() {
                String str = "v12/content/getDashboard?userId=%s&direction=%s&pageSize=%s&mediaType=%s&lastRow=%s";
                Object[] objArr = new Object[5];
                objArr[0] = Long.valueOf(j4);
                objArr[1] = z2 ? "previous" : "next";
                objArr[2] = Integer.valueOf(i2);
                objArr[3] = Long.valueOf(j5);
                objArr[4] = Long.valueOf(j6);
                return new C2758b(this, 0, String.format(str, objArr), this.f9230f, this.f9230f, this.f9230f.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass48 f9224b;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1051";
            }

            public String toString() {
                return "WS_GET_DASHBOARD";
            }
        });
    }

    /* renamed from: b */
    public void m13127b(String str) {
        Exception exception;
        C2757a c2757a;
        try {
            if (this.f9271h.mo3484e().equals("set-1056")) {
                f9266l = false;
            }
        } catch (Exception e) {
        }
        if (this.f9273j != null) {
            try {
                if (this.f9271h.mo3482c()) {
                    C2757a c2757a2 = new C2757a();
                    try {
                        c2757a2.m12803a((Object) str);
                        this.f9271h.mo3479a(c2757a2);
                        return;
                    } catch (Exception e2) {
                        exception = e2;
                        c2757a = c2757a2;
                        try {
                            exception.printStackTrace();
                        } catch (Exception e3) {
                        }
                        if (c2757a == null) {
                            c2757a = new C2757a();
                            c2757a.m12802a(-1);
                            c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                        }
                        try {
                            this.f9271h.mo3479a(c2757a);
                        } catch (Exception e4) {
                            try {
                                this.f9271h.mo3480a(null);
                                return;
                            } catch (Exception e5) {
                                return;
                            }
                        }
                    }
                }
                int parseInt;
                C1776o k = ((C1771l) new C1768f().m8392a(str, C1771l.class)).m8415k();
                String c1771l = k.m8426a("code") != null ? k.m8426a("code").toString() : null;
                String c1771l2 = k.m8426a(DataBufferSafeParcelable.DATA_FIELD) != null ? k.m8426a(DataBufferSafeParcelable.DATA_FIELD).toString() : null;
                String c1771l3 = k.m8426a("message") != null ? k.m8426a("message").toString() : null;
                C2757a c2757a3 = new C2757a();
                c2757a3.m12804a(c1771l3);
                try {
                    parseInt = Integer.parseInt(c1771l);
                } catch (Exception e6) {
                    parseInt = -1;
                }
                if (parseInt == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                    c2757a3.m12802a(parseInt);
                    c2757a3.m12803a(new C1768f().m8393a(c1771l2, this.f9271h.mo3483d()));
                    this.f9271h.mo3479a(c2757a3);
                } else {
                    c2757a3.m12802a(parseInt);
                    this.f9271h.mo3479a(c2757a3);
                }
                c2757a = null;
                if (c2757a == null) {
                    c2757a = new C2757a();
                    c2757a.m12802a(-1);
                    c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                }
                this.f9271h.mo3479a(c2757a);
            } catch (Exception e22) {
                Exception exception2 = e22;
                c2757a = null;
                exception = exception2;
                exception.printStackTrace();
                if (c2757a == null) {
                    c2757a = new C2757a();
                    c2757a.m12802a(-1);
                    c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                }
                this.f9271h.mo3479a(c2757a);
            }
        }
    }

    /* renamed from: b */
    public void m13128b(final boolean z) {
        Log.d("LEE", "proxyGetServer");
        if (System.currentTimeMillis() - f9267m > 6000) {
            f9266l = false;
        }
        if (C3791b.am(this.f9270g) != 0 && !f9266l) {
            f9267m = System.currentTimeMillis();
            f9266l = true;
            m13091a(new C2760a(this) {
                /* renamed from: b */
                final /* synthetic */ C2818c f9150b;

                /* renamed from: org.telegram.customization.g.c$24$2 */
                class C27792 extends C1748a<ArrayList<ProxyServerModel>> {
                    /* renamed from: d */
                    final /* synthetic */ AnonymousClass24 f9148d;

                    C27792(AnonymousClass24 anonymousClass24) {
                        this.f9148d = anonymousClass24;
                    }
                }

                /* renamed from: a */
                public RetryPolicy mo3478a() {
                    return null;
                }

                /* renamed from: a */
                public void mo3479a(C2757a c2757a) {
                    try {
                        C2818c.f9266l = false;
                        C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                        String str = null;
                        if (k.m8426a(DataBufferSafeParcelable.DATA_FIELD) != null) {
                            str = k.m8426a(DataBufferSafeParcelable.DATA_FIELD).toString();
                        }
                        Log.d("alireza", "alireza" + str);
                        if (TextUtils.isEmpty(str)) {
                            this.f9150b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, -24);
                            return;
                        }
                        ArrayList arrayList = new ArrayList();
                        for (String c : (String[]) new C1768f().m8392a(str, String[].class)) {
                            arrayList.add((ProxyServerModel) new C1768f().m8392a(C2872c.m13350c(c), ProxyServerModel.class));
                        }
                        if (arrayList == null || arrayList.size() <= 0) {
                            this.f9150b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, -24);
                            return;
                        }
                        ArrayList arrayList2 = new ArrayList();
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            ProxyServerModel proxyServerModel = (ProxyServerModel) it.next();
                            if (proxyServerModel != null) {
                                try {
                                    proxyServerModel.fillLocalExpireTime();
                                    Log.d("alireza", "alireza onresult : proxy recieved " + proxyServerModel.getIp() + "---- pass :" + proxyServerModel.getPassWord());
                                    arrayList2.add(proxyServerModel);
                                } catch (Exception e) {
                                }
                            }
                        }
                        if (arrayList2.size() > 0) {
                            Collections.shuffle(arrayList2);
                            C3791b.m13947b(this.f9150b.f9270g, arrayList2);
                            C3791b.m14044s(ApplicationLoader.applicationContext, true);
                        }
                        if (z) {
                            C2820e.m13150a(this.f9150b.f9270g);
                        }
                        this.f9150b.f9272i.onResult(arrayList2, 24);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        C2757a c2757a2 = new C2757a();
                        c2757a2.m12802a(-1);
                        c2757a2.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                        this.f9150b.f9272i.onResult(c2757a2, -24);
                    }
                }

                /* renamed from: a */
                public void mo3480a(VolleyError volleyError) {
                    Log.d("LEE", "HandleRequest onErrorResponse get proxy");
                    C2818c.f9266l = false;
                }

                /* renamed from: b */
                public Request mo3481b() {
                    return new C2758b(this, 1, "v3/proxy", this.f9150b, this.f9150b, this.f9150b.f9270g, mo3484e()) {
                        /* renamed from: b */
                        final /* synthetic */ AnonymousClass24 f9147b;

                        public byte[] getBody() {
                            ProxyServerModel fromShared = ProxyServerModel.getFromShared();
                            Object proxyServerModel = new ProxyServerModel();
                            if (!TextUtils.isEmpty(fromShared.getIp())) {
                                String[] split = fromShared.getIp().split("\\.");
                                if (split.length > 2) {
                                    proxyServerModel.setIp(split[split.length - 2] + "." + split[split.length - 1]);
                                }
                            }
                            if (!TextUtils.isEmpty(fromShared.getUserName()) && fromShared.getUserName().length() > 2) {
                                proxyServerModel.setUserName(fromShared.getUserName().substring(0, 2));
                            }
                            proxyServerModel.setPassWord(this.f9147b.f9150b.m13149r(fromShared.getPassWord()));
                            proxyServerModel.setPort(fromShared.getPort());
                            proxyServerModel.setPorxyHealth(fromShared.isPorxyHealth());
                            proxyServerModel.setExpireDateSecs(fromShared.getExpireDateSecs());
                            return new C1768f().m8395a(proxyServerModel).getBytes();
                        }

                        protected Map<String, String> getParams() {
                            return super.getParams();
                        }
                    };
                }

                /* renamed from: c */
                public boolean mo3482c() {
                    return true;
                }

                /* renamed from: d */
                public Type mo3483d() {
                    return new C27792(this).m8360b();
                }

                /* renamed from: e */
                public String mo3484e() {
                    return "set-1067";
                }

                public String toString() {
                    return "WS_LIGHT_HOST_GET_PROXY";
                }
            });
        }
    }

    /* renamed from: c */
    public void m13129c() {
        m13091a(new C28095(this));
    }

    /* renamed from: d */
    public void m13130d() {
        m13091a(new C28127(this));
    }

    /* renamed from: d */
    public void m13131d(String str) {
        m13091a(new C2760a(this) {
            /* renamed from: a */
            final /* synthetic */ C2818c f9102a;

            {
                this.f9102a = r1;
            }

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                this.f9102a.f9272i.onResult((C2896c) new C1768f().m8392a((String) c2757a.m12801a(), C2896c.class), 13);
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                C2757a c2757a = new C2757a();
                c2757a.m12802a(-1);
                c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                this.f9102a.f9272i.onResult(c2757a, -13);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new StringRequest(this, 0, C3791b.m14045t(this.f9102a.f9270g) + "election/v5/GetVoteResult", this.f9102a, this.f9102a) {
                    /* renamed from: a */
                    final /* synthetic */ AnonymousClass10 f9101a;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    public Map<String, String> getHeaders() {
                        Map<String, String> hashMap = new HashMap();
                        hashMap.put("X-SLS-Version", String.valueOf(BuildConfig.VERSION_CODE));
                        String str = "550205994";
                        String str2 = "-1";
                        if (str == null || str.length() <= 0) {
                            hashMap.put("X-SLS-DeviceId", String.valueOf(0));
                        } else {
                            hashMap.put("X-SLS-DeviceId", String.valueOf(str));
                        }
                        if (str2 == null || str2.length() <= 0) {
                            hashMap.put("X-SLS-UserId", String.valueOf(0));
                        } else {
                            hashMap.put("X-SLS-UserId", String.valueOf(str2));
                        }
                        hashMap.put("X-SLS-AppId", String.valueOf(4));
                        try {
                            hashMap.put("X-SLS-Token", "dVQGpLWTfXBOs7dH2FI37LX+MmI=");
                        } catch (Exception e) {
                        }
                        return hashMap;
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return null;
            }

            public String toString() {
                return "WS_GET_POLL";
            }
        });
    }

    /* renamed from: e */
    public void m13132e() {
        m13091a(new C2760a(this) {
            /* renamed from: a */
            final /* synthetic */ C2818c f9138a;

            /* renamed from: org.telegram.customization.g.c$20$1 */
            class C27731 extends C1748a<ArrayList<Category>> {
                /* renamed from: d */
                final /* synthetic */ AnonymousClass20 f9136d;

                C27731(AnonymousClass20 anonymousClass20) {
                    this.f9136d = anonymousClass20;
                }
            }

            {
                this.f9138a = r1;
            }

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                String str = null;
                if (k.m8426a(DataBufferSafeParcelable.DATA_FIELD) != null) {
                    str = k.m8426a(DataBufferSafeParcelable.DATA_FIELD).toString();
                }
                this.f9138a.f9272i.onResult((ArrayList) new C1769g().m8403a(new GsonAdapterFactory()).m8402a().m8393a(str, new C27731(this).m8360b()), 21);
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                C2757a c2757a = new C2757a();
                c2757a.m12802a(-1);
                c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                this.f9138a.f9272i.onResult(c2757a, -21);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 0, "v12/ad/Categories", this.f9138a, this.f9138a, this.f9138a.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass20 f9137b;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1051";
            }

            public String toString() {
                return "WS_GET_HOME";
            }
        });
    }

    /* renamed from: e */
    public void m13133e(final String str) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9105b;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                try {
                    C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                    String str = null;
                    if (k.m8426a("code") != null) {
                        str = k.m8426a("code").toString();
                    }
                    if (k.m8426a("message") != null) {
                        str = k.m8426a("message").toString();
                    }
                    Integer.parseInt(str);
                } catch (Exception e) {
                    e.printStackTrace();
                    C2757a c2757a2 = new C2757a();
                    c2757a2.m12802a(-1);
                    c2757a2.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                    this.f9105b.f9272i.onResult(c2757a2, -5);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
            }

            /* renamed from: b */
            public Request mo3481b() {
                int i = UserConfig.getCurrentUser() != null ? UserConfig.getCurrentUser().id : 0;
                return new C2758b(this, 1, String.format("v12/user/SaveStickers?userId=%s", new Object[]{Integer.valueOf(i)}), this.f9105b, this.f9105b, this.f9105b.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass11 f9103b;

                    public byte[] getBody() {
                        return this.f9103b.f9105b.m13105s(str);
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1054";
            }

            public String toString() {
                return "WS_GET_SEND_CHANNEL_LIST";
            }
        });
    }

    /* renamed from: f */
    public void m13134f() {
        m13091a(new C2760a(this) {
            /* renamed from: a */
            final /* synthetic */ C2818c f9140a;

            {
                this.f9140a = r1;
            }

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                String str = null;
                if (k.m8426a(DataBufferSafeParcelable.DATA_FIELD) != null) {
                    str = k.m8426a(DataBufferSafeParcelable.DATA_FIELD).toString();
                }
                this.f9140a.f9272i.onResult((Statistics) new C1769g().m8403a(new GsonAdapterFactory()).m8402a().m8392a(str, Statistics.class), 22);
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                C2757a c2757a = new C2757a();
                c2757a.m12802a(-1);
                c2757a.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                this.f9140a.f9272i.onResult(c2757a, -22);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 0, "v12/ad/Statistics", this.f9140a, this.f9140a, this.f9140a.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass21 f9139b;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1051";
            }

            public String toString() {
                return "WS_GET_HOME";
            }
        });
    }

    /* renamed from: f */
    public void m13135f(final String str) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9109b;

            /* renamed from: org.telegram.customization.g.c$12$1 */
            class C27621 extends C1748a<ArrayList<FilterResponse>> {
                /* renamed from: d */
                final /* synthetic */ AnonymousClass12 f9106d;

                C27621(AnonymousClass12 anonymousClass12) {
                    this.f9106d = anonymousClass12;
                }
            }

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                try {
                    C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                    String str = null;
                    if (k.m8426a("code") != null) {
                        str = k.m8426a("code").toString();
                    }
                    if (Integer.parseInt(str) == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                        if (k.m8426a(DataBufferSafeParcelable.DATA_FIELD) != null) {
                            str = k.m8426a(DataBufferSafeParcelable.DATA_FIELD).toString();
                        }
                        ArrayList arrayList = (ArrayList) new C1769g().m8403a(new GsonAdapterFactory()).m8402a().m8393a(str, new C27621(this).m8360b());
                        ApplicationLoader.databaseHandler.m12225e();
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            FilterResponse filterResponse = (FilterResponse) it.next();
                            DialogStatus dialogStatus = new DialogStatus();
                            dialogStatus.setFilter(true);
                            dialogStatus.setDialogId(filterResponse.getChannelId());
                            ApplicationLoader.databaseHandler.m12218a(dialogStatus);
                        }
                        C3791b.m14008k(this.f9109b.f9270g, System.currentTimeMillis());
                        this.f9109b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, 16);
                        return;
                    }
                    this.f9109b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, -16);
                } catch (Exception e) {
                    e.printStackTrace();
                    C2757a c2757a2 = new C2757a();
                    c2757a2.m12802a(-1);
                    c2757a2.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                    this.f9109b.f9272i.onResult(c2757a2, -16);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
            }

            /* renamed from: b */
            public Request mo3481b() {
                int i = UserConfig.getCurrentUser() != null ? UserConfig.getCurrentUser().id : 0;
                return new C2758b(this, 1, String.format("v12/health/shn?u=%s", new Object[]{Integer.valueOf(i)}), this.f9109b, this.f9109b, this.f9109b.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass12 f9107b;

                    public byte[] getBody() {
                        return C2818c.m13103p(str);
                    }

                    public String getBodyContentType() {
                        return "text/plain";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1054";
            }

            public String toString() {
                return "WS_GET_SEND_CHANNEL_LIST";
            }
        });
    }

    /* renamed from: g */
    public void m13136g() {
        m13091a(new C2760a(this) {
            /* renamed from: a */
            final /* synthetic */ C2818c f9155a;

            {
                this.f9155a = r1;
            }

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                C3791b.m13925a(0);
                this.f9155a.onResult(Boolean.valueOf(true), 37);
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                this.f9155a.onResult(Boolean.valueOf(false), -38);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 1, "v3/cstats", this.f9155a, this.f9155a, this.f9155a.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass26 f9154b;

                    public byte[] getBody() {
                        long K = C3791b.m13907K(ApplicationLoader.applicationContext) / 1000;
                        return new C1768f().m8395a(MessagesStorage.getInstance().getUserStateWithStartAndEndTime(K, System.currentTimeMillis() / 1000)).getBytes();
                    }

                    public String getBodyContentType() {
                        return "application/json";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12810b(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1066";
            }

            public String toString() {
                return "WS_POST_S_S";
            }
        });
    }

    /* renamed from: g */
    public void m13137g(final String str) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9112b;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                try {
                    C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                    String str = null;
                    if (k.m8426a("code") != null) {
                        str = k.m8426a("code").toString();
                    }
                    if (Integer.parseInt(str) == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                        C3791b.m14029p(this.f9112b.f9270g, System.currentTimeMillis());
                        this.f9112b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, 17);
                        return;
                    }
                    this.f9112b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, -17);
                } catch (Exception e) {
                    e.printStackTrace();
                    C2757a c2757a2 = new C2757a();
                    c2757a2.m12802a(-1);
                    c2757a2.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                    this.f9112b.f9272i.onResult(c2757a2, -17);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
            }

            /* renamed from: b */
            public Request mo3481b() {
                int i = UserConfig.getCurrentUser() != null ? UserConfig.getCurrentUser().id : 0;
                return new C2758b(this, 1, String.format("v12/health/sshn?u=%s", new Object[]{Integer.valueOf(i)}), this.f9112b, this.f9112b, this.f9112b.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass13 f9110b;

                    public byte[] getBody() {
                        return C2818c.m13103p(str);
                    }

                    public String getBodyContentType() {
                        return "text/plain";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1054";
            }

            public String toString() {
                return "WS_GET_SEND_CHANNEL_LIST";
            }
        });
    }

    /* renamed from: h */
    public void m13138h() {
        m13091a(new C2760a(this) {
            /* renamed from: a */
            final /* synthetic */ C2818c f9159a;

            /* renamed from: org.telegram.customization.g.c$28$2 */
            class C27832 extends C1748a<ArrayList<HotgramTheme>> {
                /* renamed from: d */
                final /* synthetic */ AnonymousClass28 f9158d;

                C27832(AnonymousClass28 anonymousClass28) {
                    this.f9158d = anonymousClass28;
                }
            }

            {
                this.f9159a = r1;
            }

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                try {
                    Object obj = (ArrayList) c2757a.m12801a();
                    if (obj == null || obj.size() <= 0) {
                        this.f9159a.f9272i.onResult(obj, -26);
                        return;
                    }
                    C3791b.m13897C(ApplicationLoader.applicationContext, new C1768f().m8395a(obj));
                    this.f9159a.f9272i.onResult(obj, 26);
                } catch (Exception e) {
                    e.printStackTrace();
                    C2757a c2757a2 = new C2757a();
                    c2757a2.m12802a(-1);
                    c2757a2.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                    this.f9159a.f9272i.onResult(c2757a2, -26);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                this.f9159a.f9272i.onResult(null, -26);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 0, "v12/setting/getthemes", this.f9159a, this.f9159a, this.f9159a.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass28 f9157b;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return false;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return new C27832(this).m8360b();
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1051";
            }

            public String toString() {
                return "WS_GET_THEMES";
            }
        });
    }

    /* renamed from: h */
    public void m13139h(final String str) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9115b;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                try {
                    C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                    String str = null;
                    if (k.m8426a("code") != null) {
                        str = k.m8426a("code").toString();
                    }
                    if (Integer.parseInt(str) == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                        C3791b.m14046t(this.f9115b.f9270g, System.currentTimeMillis());
                        this.f9115b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, 18);
                        return;
                    }
                    this.f9115b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, -18);
                } catch (Exception e) {
                    e.printStackTrace();
                    C2757a c2757a2 = new C2757a();
                    c2757a2.m12802a(-1);
                    c2757a2.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                    this.f9115b.f9272i.onResult(c2757a2, -18);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
            }

            /* renamed from: b */
            public Request mo3481b() {
                int i = UserConfig.getCurrentUser() != null ? UserConfig.getCurrentUser().id : 0;
                return new C2758b(this, 1, String.format("v12/health/sb?u=%s", new Object[]{Integer.valueOf(i)}), this.f9115b, this.f9115b, this.f9115b.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass14 f9113b;

                    public byte[] getBody() {
                        return C2818c.m13103p(str);
                    }

                    public String getBodyContentType() {
                        return "text/plain";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1054";
            }

            public String toString() {
                return "WS_GET_SEND_CHANNEL_LIST";
            }
        });
    }

    /* renamed from: i */
    public void m13140i() {
        m13091a(new C2760a(this) {
            /* renamed from: a */
            final /* synthetic */ C2818c f9161a;

            {
                this.f9161a = r1;
            }

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                HostRequestData hostRequestData = (HostRequestData) c2757a.m12801a();
                if (hostRequestData != null) {
                    this.f9161a.f9272i.onResult(hostRequestData, 27);
                } else {
                    this.f9161a.f9272i.onResult(hostRequestData, -27);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                this.f9161a.f9272i.onResult(null, -27);
            }

            /* renamed from: b */
            public Request mo3481b() {
                if (UserConfig.getCurrentUser() != null) {
                    int i = UserConfig.getCurrentUser().id;
                }
                return new C2758b(this, 1, "v12/payment/rsd", this.f9161a, this.f9161a, this.f9161a.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass29 f9160b;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    public String getBodyContentType() {
                        return "text/plain";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return false;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return HostRequestData.class;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1062";
            }

            public String toString() {
                return "WS_REGISTER_AP_SDK";
            }
        });
    }

    /* renamed from: i */
    public void m13141i(final String str) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9118b;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                try {
                    C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                    String str = null;
                    if (k.m8426a("code") != null) {
                        str = k.m8426a("code").toString();
                    }
                    if (Integer.parseInt(str) == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                        C3791b.m14046t(this.f9118b.f9270g, System.currentTimeMillis());
                        this.f9118b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, 18);
                        return;
                    }
                    this.f9118b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, -18);
                } catch (Exception e) {
                    e.printStackTrace();
                    C2757a c2757a2 = new C2757a();
                    c2757a2.m12802a(-1);
                    c2757a2.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                    this.f9118b.f9272i.onResult(c2757a2, -18);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
            }

            /* renamed from: b */
            public Request mo3481b() {
                int i = UserConfig.getCurrentUser() != null ? UserConfig.getCurrentUser().id : 0;
                return new C2758b(this, 1, String.format("v12/health/nui?u=%s", new Object[]{Integer.valueOf(i)}), this.f9118b, this.f9118b, this.f9118b.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass15 f9116b;

                    public byte[] getBody() {
                        return C2818c.m13103p(str);
                    }

                    public String getBodyContentType() {
                        return "text/plain";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1054";
            }

            public String toString() {
                return "WS_GET_SEND_CHANNEL_LIST";
            }
        });
    }

    /* renamed from: j */
    public void m13142j() {
        m13091a(new C2760a(this) {
            /* renamed from: a */
            final /* synthetic */ C2818c f9168a;

            {
                this.f9168a = r1;
            }

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                User user = (User) c2757a.m12801a();
                if (user != null) {
                    this.f9168a.f9272i.onResult(user, 29);
                } else {
                    this.f9168a.f9272i.onResult(user, -29);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                this.f9168a.f9272i.onResult(null, -29);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 1, "v12/payment/crs", this.f9168a, this.f9168a, this.f9168a.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass31 f9167b;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    public String getBodyContentType() {
                        return "text/plain";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return false;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return User.class;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1062";
            }

            public String toString() {
                return "WS_POST_T_M";
            }
        });
    }

    /* renamed from: j */
    public void m13143j(final String str) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9122b;

            /* renamed from: org.telegram.customization.g.c$16$1 */
            class C27671 extends C1748a<ArrayList<Integer>> {
                /* renamed from: d */
                final /* synthetic */ AnonymousClass16 f9119d;

                C27671(AnonymousClass16 anonymousClass16) {
                    this.f9119d = anonymousClass16;
                }
            }

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                try {
                    C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                    String c1771l = k.m8426a("message") != null ? k.m8426a("message").toString() : k.m8426a("code") != null ? k.m8426a("code").toString() : null;
                    String c1771l2 = k.m8426a(DataBufferSafeParcelable.DATA_FIELD) != null ? k.m8426a(DataBufferSafeParcelable.DATA_FIELD).toString() : null;
                    if (Integer.parseInt(c1771l) == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                        if (!TextUtils.isEmpty(c1771l2)) {
                            ArrayList arrayList = new ArrayList();
                            ArrayList arrayList2 = (ArrayList) new C1768f().m8393a(c1771l2, new C27671(this).m8360b());
                            if (arrayList2.size() > 0) {
                                Iterator it = arrayList2.iterator();
                                while (it.hasNext()) {
                                    C2666a.getDatabaseHandler().m12218a(new DialogStatus((long) ((Integer) it.next()).intValue(), true, false));
                                }
                            }
                        }
                        this.f9122b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, 14);
                        C3791b.m13995i(this.f9122b.f9270g, System.currentTimeMillis());
                        return;
                    }
                    this.f9122b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, -14);
                } catch (Exception e) {
                    e.printStackTrace();
                    C3791b.m13983g(this.f9122b.f9270g, 0);
                    C2757a c2757a2 = new C2757a();
                    c2757a2.m12802a(-1);
                    c2757a2.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                    this.f9122b.f9272i.onResult(c2757a2, -14);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
            }

            /* renamed from: b */
            public Request mo3481b() {
                int i = UserConfig.getCurrentUser() != null ? UserConfig.getCurrentUser().id : 0;
                return new C2758b(this, 1, String.format("v12/health/sta?u=%s", new Object[]{Integer.valueOf(i)}), this.f9122b, this.f9122b, this.f9122b.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass16 f9120b;

                    public byte[] getBody() {
                        return C2818c.m13103p(str);
                    }

                    public String getBodyContentType() {
                        return "text/plain";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1054";
            }

            public String toString() {
                return "WS_POST_SEND_CONTACTS";
            }
        });
    }

    /* renamed from: k */
    public void m13144k(final String str) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9127b;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                try {
                    C1776o k = ((C1771l) new C1768f().m8392a((String) c2757a.m12801a(), C1771l.class)).m8415k();
                    String str = null;
                    if (k.m8426a("code") != null) {
                        str = k.m8426a("code").toString();
                    }
                    if (k.m8426a("message") != null) {
                        str = k.m8426a("message").toString();
                    }
                    if (Integer.parseInt(str) == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                        C3791b.m14002j(this.f9127b.f9270g, System.currentTimeMillis());
                        this.f9127b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, 14);
                        return;
                    }
                    this.f9127b.f9272i.onResult(TtmlNode.ANONYMOUS_REGION_ID, -14);
                } catch (Exception e) {
                    e.printStackTrace();
                    C2757a c2757a2 = new C2757a();
                    c2757a2.m12802a(-1);
                    c2757a2.m12804a(TtmlNode.ANONYMOUS_REGION_ID);
                    this.f9127b.f9272i.onResult(c2757a2, -14);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
            }

            /* renamed from: b */
            public Request mo3481b() {
                int i = UserConfig.getCurrentUser() != null ? UserConfig.getCurrentUser().id : 0;
                return new C2758b(this, 1, String.format("v12/health/slc?u=%s", new Object[]{Integer.valueOf(i)}), this.f9127b, this.f9127b, this.f9127b.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass18 f9125b;

                    public byte[] getBody() {
                        return C2818c.m13103p(str);
                    }

                    public String getBodyContentType() {
                        return "text/plain";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1054";
            }

            public String toString() {
                return "WS_POST_SEND_CONTACTS";
            }
        });
    }

    /* renamed from: l */
    public void m13145l(final String str) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9153b;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
            }

            /* renamed from: b */
            public Request mo3481b() {
                int i = UserConfig.getCurrentUser() != null ? UserConfig.getCurrentUser().id : 0;
                return new C2758b(this, 1, String.format("v12/health/svi?u=%s", new Object[]{Integer.valueOf(i)}), this.f9153b, this.f9153b, this.f9153b.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass25 f9151b;

                    public byte[] getBody() {
                        return C2818c.m13103p(str);
                    }

                    public String getBodyContentType() {
                        return "text/plain";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return true;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1054";
            }

            public String toString() {
                return "WS_POST_T_M";
            }
        });
    }

    /* renamed from: m */
    public void m13146m(final String str) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9174b;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                if (c2757a.m12805b() != ChatActivity.startAllServices) {
                    HostRequestData hostRequestData = (HostRequestData) c2757a.m12801a();
                    if (hostRequestData != null) {
                        this.f9174b.f9272i.onResult(hostRequestData, 35);
                        return;
                    }
                    return;
                }
                this.f9174b.f9272i.onResult(null, -35);
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                this.f9174b.f9272i.onResult(null, -35);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 1, String.format("v12/payment/gpd?paymentId=%s", new Object[]{str}), this.f9174b, this.f9174b, this.f9174b.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass33 f9172b;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    public String getBodyContentType() {
                        return "text/plain";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return false;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return HostRequestData.class;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1062";
            }

            public String toString() {
                return "WS_POST_T_M";
            }
        });
    }

    /* renamed from: n */
    public void m13147n(final String str) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9181b;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                HostRequestData hostRequestData = (HostRequestData) c2757a.m12801a();
                if (hostRequestData != null) {
                    this.f9181b.f9272i.onResult(hostRequestData, 31);
                }
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                this.f9181b.f9272i.onResult(null, -31);
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new C2758b(this, 1, String.format("v12/payment/rp?paymentId=%s", new Object[]{str}), this.f9181b, this.f9181b, this.f9181b.f9270g, mo3484e()) {
                    /* renamed from: b */
                    final /* synthetic */ AnonymousClass35 f9179b;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    public String getBodyContentType() {
                        return "text/plain";
                    }

                    public Map<String, String> getHeaders() {
                        return C2758b.m12808a(this.a);
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return false;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return HostRequestData.class;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "set-1062";
            }

            public String toString() {
                return "WS_POST_T_M";
            }
        });
    }

    /* renamed from: o */
    public void m13148o(final String str) {
        m13091a(new C2760a(this) {
            /* renamed from: b */
            final /* synthetic */ C2818c f9208b;

            /* renamed from: a */
            public RetryPolicy mo3478a() {
                return null;
            }

            /* renamed from: a */
            public void mo3479a(C2757a c2757a) {
                Log.d("alireza", "alireza call yrl success");
            }

            /* renamed from: a */
            public void mo3480a(VolleyError volleyError) {
                C3791b.m13951c(System.currentTimeMillis());
                Log.d("alireza", "alireza call yrl error");
            }

            /* renamed from: b */
            public Request mo3481b() {
                return new StringRequest(this, 0, str, this.f9208b, this.f9208b) {
                    /* renamed from: a */
                    final /* synthetic */ AnonymousClass40 f9206a;

                    public byte[] getBody() {
                        return super.getBody();
                    }

                    public Map<String, String> getHeaders() {
                        Map<String, String> hashMap = new HashMap();
                        List asList = Arrays.asList(Constants.f10266a);
                        Collections.shuffle(asList);
                        hashMap.put("User-agent", asList.get(0));
                        return hashMap;
                    }

                    protected Map<String, String> getParams() {
                        return super.getParams();
                    }
                };
            }

            /* renamed from: c */
            public boolean mo3482c() {
                return false;
            }

            /* renamed from: d */
            public Type mo3483d() {
                return null;
            }

            /* renamed from: e */
            public String mo3484e() {
                return "KEY_CALL_URL";
            }
        });
    }

    public void onErrorResponse(final VolleyError volleyError) {
        try {
            Log.d("LEE", "HandleRequest onErrorResponse" + volleyError.networkResponse + "----" + volleyError.networkResponse.statusCode);
        } catch (Exception e) {
        }
        try {
            if (this.f9268a != null) {
                new Thread(this) {
                    /* renamed from: b */
                    final /* synthetic */ C2818c f9247b;

                    public void run() {
                        super.run();
                        this.f9247b.m13122a(volleyError);
                    }
                }.start();
            } else {
                m13122a(volleyError);
            }
        } catch (Exception e2) {
        }
    }

    public /* synthetic */ void onResponse(Object obj) {
        m13115a((String) obj);
    }

    public void onResult(final Object obj, final int i) {
        m13114a(new Runnable(this) {
            /* renamed from: c */
            final /* synthetic */ C2818c f9133c;

            public void run() {
                this.f9133c.f9273j.onResult(obj, i);
            }
        });
    }

    /* renamed from: r */
    public String m13149r(String str) {
        int i = 0;
        String str2 = TtmlNode.ANONYMOUS_REGION_ID;
        for (int i2 = 0; i2 < str.length(); i2++) {
            Character valueOf = Character.valueOf(str.charAt(i2));
            str2 = str2 + (Character.isLowerCase(valueOf.charValue()) ? Character.toUpperCase(valueOf.charValue()) : Character.toLowerCase(valueOf.charValue()));
        }
        String str3 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?/.,";
        Random random = new Random();
        while (i < 5) {
            str2 = str2 + str3.charAt(random.nextInt(str3.length()));
            i++;
        }
        return str2;
    }
}
