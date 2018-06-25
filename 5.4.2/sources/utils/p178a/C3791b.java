package utils.p178a;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.p098a.C1768f;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Ads.Category;
import org.telegram.customization.Model.CAI;
import org.telegram.customization.Model.DialogTab;
import org.telegram.customization.Model.OfficialJoinChannel;
import org.telegram.customization.Model.ProxyServerModel;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;

/* renamed from: utils.a.b */
public class C3791b {
    /* renamed from: b */
    static ArrayList<Integer> f10149b = null;
    /* renamed from: c */
    static Random f10150c = new Random();
    /* renamed from: d */
    private static C3791b f10151d;
    /* renamed from: f */
    private static List<Long> f10152f = null;
    /* renamed from: g */
    private static List<Long> f10153g = null;
    /* renamed from: h */
    private static Boolean f10154h = null;
    /* renamed from: i */
    private static Boolean f10155i = null;
    /* renamed from: j */
    private static Integer f10156j = null;
    /* renamed from: a */
    public Editor f10157a = this.f10158e.edit();
    /* renamed from: e */
    private SharedPreferences f10158e;

    private C3791b(Context context, String str) {
        this.f10158e = context.getSharedPreferences(str, 0);
    }

    /* renamed from: A */
    public static int m13892A(Context context) {
        if (f10156j == null) {
            f10156j = Integer.valueOf(C3791b.av(context).m14069b("SUBTITLE_TYPE"));
        }
        return f10156j.intValue();
    }

    /* renamed from: A */
    public static void m13893A(Context context, String str) {
        C3791b.av(context).m14067a("SP_DEFAULT_PROXY", str);
    }

    /* renamed from: B */
    public static long m13894B(Context context) {
        return C3791b.av(context).m14071c("SP_LAST_SUCCESS_SYNC_CONTACT").longValue();
    }

    /* renamed from: B */
    public static ArrayList<String> m13895B(Context context, String str) {
        ArrayList arrayList;
        int i;
        ArrayList arrayList2 = new ArrayList();
        try {
            arrayList2 = (ArrayList) new C1768f().m8393a(C3791b.av(context).m14063a(str), new b$4().b());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (arrayList2 == null || arrayList2.size() < 1 || TextUtils.isEmpty((CharSequence) arrayList2.get(0))) {
            arrayList = new ArrayList();
            i = -1;
            switch (str.hashCode()) {
                case 1367331718:
                    if (str.equals("set-1051")) {
                        i = 0;
                        break;
                    }
                    break;
                case 1367331719:
                    if (str.equals("set-1052")) {
                        i = 3;
                        break;
                    }
                    break;
                case 1367331720:
                    if (str.equals("set-1053")) {
                        i = 5;
                        break;
                    }
                    break;
                case 1367331721:
                    if (str.equals("set-1054")) {
                        i = 2;
                        break;
                    }
                    break;
                case 1367331722:
                    if (str.equals("set-1055")) {
                        i = 4;
                        break;
                    }
                    break;
                case 1367331723:
                    if (str.equals("set-1056")) {
                        i = 1;
                        break;
                    }
                    break;
                case 1367331724:
                    if (str.equals("set-1057")) {
                        i = 6;
                        break;
                    }
                    break;
                case 1367331750:
                    if (str.equals("set-1062")) {
                        i = 7;
                        break;
                    }
                    break;
                case 1367331753:
                    if (str.equals("set-1065")) {
                        i = 10;
                        break;
                    }
                    break;
                case 1367331754:
                    if (str.equals("set-1066")) {
                        i = 8;
                        break;
                    }
                    break;
                case 1367331755:
                    if (str.equals("set-1067")) {
                        i = 9;
                        break;
                    }
                    break;
            }
            switch (i) {
                case 0:
                    arrayList.add("http://uapi.hotgram.ir/");
                    arrayList.add("http://uapi.harsobh.com/");
                    arrayList.add("http://uapi.talagram.ir/");
                    break;
                case 1:
                    arrayList.add("http://gpapi.hotgram.ir/");
                    arrayList.add("http://gpapi.harsobh.com/");
                    arrayList.add("http://gpapi.talagram.ir/");
                    break;
                case 2:
                    arrayList.add("http://giapi.hotgram.ir/");
                    arrayList.add("http://giapi.harsobh.com/");
                    arrayList.add("http://giapi.talagram.ir/");
                    break;
                case 3:
                    arrayList.add("http://sapi.hotgram.ir/");
                    arrayList.add("http://sapi.harsobh.com/");
                    arrayList.add("http://sapi.talagram.ir/");
                    break;
                case 4:
                    arrayList.add("http://rgapi.hotgram.ir/");
                    arrayList.add("http://rgapi.harsobh.com/");
                    arrayList.add("http://rgapi.talagram.ir/");
                    break;
                case 5:
                    arrayList.add("http://gsapi.hotgram.ir/");
                    arrayList.add("http://gsapi.harsobh.com/");
                    arrayList.add("http://gsapi.talagram.ir/");
                    break;
                case 6:
                    arrayList.add("http://cfapi.hotgram.ir/");
                    arrayList.add("http://cfapi.harsobh.com/");
                    arrayList.add("http://cfapi.talagram.ir/");
                    break;
                case 7:
                    arrayList.add("https://api.pay.hotgram.ir/");
                    break;
                case 8:
                case 9:
                case 10:
                    arrayList.add("http://lh0.hotgram.ir/");
                    arrayList.add("http://lh1.harsobh.com/");
                    arrayList.add("http://lh2.hotgram.ir/");
                    arrayList.add("http://lh3.harsobh.com/");
                    arrayList.add("http://lh4.hotgram.ir/");
                    arrayList.add("http://lh5.talagram.ir/");
                    arrayList.add("http://lh6.hotgram.ir/");
                    arrayList.add("http://lh7.harsobh.com/");
                    arrayList.add("http://lh8.hotgram.ir/");
                    arrayList.add("http://lh9.talagram.ir/");
                    break;
            }
        }
        arrayList = arrayList2;
        Collections.shuffle(arrayList);
        if (str.equals("set-1066") || str.equals("set-1065") || str.equals("set-1067")) {
            Collection arrayList3 = new ArrayList();
            for (i = 0; i <= 9; i++) {
                String str2 = "http://lh" + i + ".hotgram.ir/";
                if (!arrayList.contains(str2)) {
                    arrayList3.add(str2);
                }
            }
            Collections.shuffle(arrayList3);
            arrayList.addAll(arrayList3);
        }
        ArrayList<String> arrayList4 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str3 = (String) it.next();
            if (str3 != null) {
                Object trim = str3.trim();
                if (!trim.endsWith("/")) {
                    trim = trim + "/";
                }
                arrayList4.add(trim);
            }
        }
        return arrayList4;
    }

    /* renamed from: C */
    public static long m13896C(Context context) {
        return C3791b.av(context).m14071c("SP_LAST_SUCCESS_SYNC_LOCATION").longValue();
    }

    /* renamed from: C */
    public static void m13897C(Context context, String str) {
        C3791b.av(context).m14067a("THEME_LIST", str);
    }

    /* renamed from: D */
    public static long m13898D(Context context) {
        return C3791b.av(context).m14071c("SP_LAST_SUCCESS_SYNC_CHANNEL").longValue();
    }

    /* renamed from: D */
    public static void m13899D(Context context, String str) {
        C3791b.av(context).m14067a("PUSHTOKEN", str);
    }

    /* renamed from: E */
    public static long m13900E(Context context) {
        return C3791b.av(context).m14071c("SP_CHANNEL_SYNC_PERIOD").longValue() == 0 ? 172800000 : C3791b.av(context).m14071c("SP_CHANNEL_SYNC_PERIOD").longValue();
    }

    /* renamed from: E */
    private static PublicKey m13901E(Context context, String str) {
        InputStream inputStream = null;
        try {
            CertificateFactory instance = CertificateFactory.getInstance("X.509");
            inputStream = context.getAssets().open(str);
            PublicKey publicKey = ((X509Certificate) instance.generateCertificate(inputStream)).getPublicKey();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            return publicKey;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                }
            }
        }
    }

    /* renamed from: F */
    public static long m13902F(Context context) {
        return C3791b.av(context).m14071c("SP_CONTACT_SYNC_PERIOD").longValue() == 0 ? 1209600000 : C3791b.av(context).m14071c("SP_CONTACT_SYNC_PERIOD").longValue();
    }

    /* renamed from: G */
    public static long m13903G(Context context) {
        return C3791b.av(context).m14071c("SP_LOCATION_SYNC_PERIOD").longValue() == 0 ? 14400000 : C3791b.av(context).m14071c("SP_LOCATION_SYNC_PERIOD").longValue();
    }

    /* renamed from: H */
    public static long m13904H(Context context) {
        return C3791b.av(context).m14071c("SP_SUPET_SYNC_PERIOD").longValue() == 0 ? 1209600000 : C3791b.av(context).m14071c("SP_SUPET_SYNC_PERIOD").longValue();
    }

    /* renamed from: I */
    public static long m13905I(Context context) {
        return C3791b.av(context).m14071c("SP_LAST_SUCCESSFULLY_SYNC_SUPER").longValue();
    }

    /* renamed from: J */
    public static long m13906J(Context context) {
        return C3791b.av(context).m14071c("SP_STATE_SYNC_PERIOD").longValue() == 0 ? 86400000 : C3791b.av(context).m14071c("SP_STATE_SYNC_PERIOD").longValue();
    }

    /* renamed from: K */
    public static long m13907K(Context context) {
        return C3791b.av(context).m14071c("SP_LAST_SUCCESSFULLY_SYNC_STATE").longValue();
    }

    /* renamed from: L */
    public static long m13908L(Context context) {
        return C3791b.av(context).m14071c("SP_BOT_SYNC_PERIOD").longValue() == 0 ? 1209600000 : C3791b.av(context).m14071c("SP_BOT_SYNC_PERIOD").longValue();
    }

    /* renamed from: M */
    public static long m13909M(Context context) {
        return C3791b.av(context).m14071c("SP_LAST_SUCCESSFULLY_SYNC_BOT").longValue();
    }

    /* renamed from: N */
    public static boolean m13910N(Context context) {
        return C3791b.av(context).m14068a("IS_ENABLE_STREAM", false);
    }

    /* renamed from: O */
    public static int m13911O(Context context) {
        return C3791b.av(context).m14069b("IS_ENABLE_GHOST");
    }

    /* renamed from: P */
    public static final int m13912P(Context context) {
        return C3791b.av(context).m14069b("SP_TEL_APP_ID") == 0 ? context.getResources().getInteger(R.integer.TELEGRAM_APP_ID) : C3791b.av(context).m14069b("SP_TEL_APP_ID");
    }

    /* renamed from: Q */
    public static final String m13913Q(Context context) {
        return TextUtils.isEmpty(C3791b.av(context).m14063a("SP_TEL_APP_HASH")) ? context.getResources().getString(R.string.TELEGRAM_APP_HASH) : C3791b.av(context).m14063a("SP_TEL_APP_HASH");
    }

    /* renamed from: R */
    public static boolean m13914R(Context context) {
        return C3791b.av(context).m14068a("IS_ENABLE_FREE_ICON", true);
    }

    /* renamed from: S */
    public static OfficialJoinChannel m13915S(Context context) {
        try {
            return (OfficialJoinChannel) new C1768f().m8392a(C3791b.av(context).m14063a("JOIN_OFFICIAL_CHANNEL"), OfficialJoinChannel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: T */
    public static String m13916T(Context context) {
        try {
            Object a = C3791b.av(context).m14063a("SP_FILTER_MESSAGE");
            return TextUtils.isEmpty(a) ? context.getString(R.string.filter_message) : a;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: U */
    public static boolean m13917U(Context context) {
        return C3791b.av(context).m14068a("SHOW_FREE_POPUP", false);
    }

    /* renamed from: V */
    public static boolean m13918V(Context context) {
        return C3791b.av(context).m14068a("SCHEDULED_DOWNLOAD", false);
    }

    /* renamed from: W */
    public static boolean m13919W(Context context) {
        return C3791b.av(context).m14068a("WIFI_ON", false);
    }

    /* renamed from: X */
    public static boolean m13920X(Context context) {
        return C3791b.av(context).m14068a("WIFI_ON", false);
    }

    /* renamed from: Y */
    public static ArrayList<Category> m13921Y(Context context) {
        String a = C3791b.av(context).m14063a("ADS_CAHNNEL");
        ArrayList arrayList = new ArrayList();
        ArrayList<Category> arrayList2 = (ArrayList) new C1768f().m8393a(a, new b$2().b());
        return arrayList2 == null ? new ArrayList() : arrayList2;
    }

    /* renamed from: Z */
    public static ArrayList<Category> m13922Z(Context context) {
        ArrayList<Category> arrayList = new ArrayList();
        Iterator it = C3791b.m13921Y(context).iterator();
        while (it.hasNext()) {
            Category category = (Category) it.next();
            if (category.getStatus() == 1) {
                arrayList.add(category);
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    public static int m13923a(Context context, int i) {
        return C3791b.av(context).m14069b("HOME_FRAGMENT_POS" + i);
    }

    /* renamed from: a */
    public static long m13924a() {
        return C3791b.av(ApplicationLoader.applicationContext).m14071c("SP_GET_PROXY_PERIOD").longValue() == 0 ? 300000 : C3791b.av(ApplicationLoader.applicationContext).m14071c("SP_GET_PROXY_PERIOD").longValue();
    }

    /* renamed from: a */
    public static void m13925a(int i) {
        C3791b.av(ApplicationLoader.applicationContext).m14064a("VIDEO_PLAY_COUNT", i);
    }

    /* renamed from: a */
    public static void m13926a(long j) {
        C3791b.av(ApplicationLoader.applicationContext).m14066a("SP_GET_PROXY_PERIOD", Long.valueOf(j));
    }

    /* renamed from: a */
    public static void m13927a(Context context, int i, int i2) {
        C3791b.av(context).m14064a("HOME_FRAGMENT_POS" + i, i2);
    }

    /* renamed from: a */
    public static void m13928a(Context context, long j) {
        Object a = C3791b.av(context).m14063a("HIDDEN_CHATS");
        C3791b.av(context).m14067a("HIDDEN_CHATS", TextUtils.isEmpty(a) ? String.valueOf(j) : a + "," + String.valueOf(j));
        f10152f = null;
    }

    /* renamed from: a */
    public static void m13929a(Context context, Float f) {
        C3791b.av(context).m14065a("TEXT_SIZE", f);
    }

    /* renamed from: a */
    public static void m13930a(Context context, String str) {
        try {
            C3791b.av(context).m14067a("SP_MIRROR_ADDRESS", new C1768f().m8395a(str.split(",")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public static void m13931a(Context context, String str, long j) {
        C3791b.av(context).m14066a(str, Long.valueOf(j));
    }

    /* renamed from: a */
    public static void m13932a(Context context, String str, String str2) {
        C3791b.av(context).m14067a(str, str2);
    }

    /* renamed from: a */
    public static void m13933a(Context context, String str, String str2, String str3, String str4, boolean z, long j) {
        C3791b.m14055w(context, str);
        C3791b.m14057x(context, str2);
        C3791b.m14059y(context, str3);
        C3791b.m14062z(context, str4);
        C3791b.m14042s(context, z);
        C3791b.m13942b(j);
    }

    /* renamed from: a */
    public static void m13934a(Context context, String str, boolean z) {
        C3791b.av(context).m14070b("IS_JOIN_" + str, z);
    }

    /* renamed from: a */
    public static void m13935a(Context context, ArrayList<Category> arrayList) {
        C3791b.av(context).m14067a("ADS_CAHNNEL", new C1768f().m8395a((Object) arrayList));
    }

    /* renamed from: a */
    public static void m13936a(Context context, boolean z) {
        C3791b.av(context).m14070b("USER_REGISTERED", z);
    }

    /* renamed from: a */
    public static void m13937a(boolean z) {
        C3791b.av(ApplicationLoader.applicationContext).m14070b("AP_REGISTERED", z);
    }

    /* renamed from: a */
    public static boolean m13938a(Context context) {
        return C3791b.av(context).m14068a("USER_REGISTERED", false);
    }

    /* renamed from: a */
    public static boolean m13939a(String str, Context context) {
        Iterator it = C3791b.m14051v(context).iterator();
        while (it.hasNext()) {
            DialogTab dialogTab = (DialogTab) it.next();
            if (dialogTab.getTag().contentEquals(str) && !dialogTab.isHidden()) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Integer> aa(Context context) {
        if (f10149b == null) {
            f10149b = new ArrayList();
            Iterator it = C3791b.m13921Y(context).iterator();
            while (it.hasNext()) {
                f10149b.add(Integer.valueOf(((Category) it.next()).getChannelId()));
            }
        }
        return f10149b;
    }

    public static boolean ab(Context context) {
        return C3791b.av(context).m14068a("IS_ENABLE_ADS", false);
    }

    public static boolean ac(Context context) {
        return C3791b.av(context).m14068a("IS_SHOW_COIN", true);
    }

    public static String ad(Context context) {
        return C3791b.av(context).m14063a("SP_ADS_TU");
    }

    public static String ae(Context context) {
        return C3791b.av(context).m14063a("SP_ADS_TU1");
    }

    public static String af(Context context) {
        return C3791b.av(context).m14063a("SP_ADS_JOIN_MSG");
    }

    public static String ag(Context context) {
        return C3791b.av(context).m14063a("SP_ADS_URL");
    }

    public static String ah(Context context) {
        return C3791b.av(context).m14063a("SP_ADS_TU_URL");
    }

    public static String ai(Context context) {
        return C3791b.av(context).f10158e.getString("SP_PROXY_ADDRESS", TtmlNode.ANONYMOUS_REGION_ID);
    }

    public static String aj(Context context) {
        return C3791b.av(context).f10158e.getString("SP_PROXY_PORT", TtmlNode.ANONYMOUS_REGION_ID);
    }

    public static String ak(Context context) {
        return C3791b.av(context).f10158e.getString("SP_PROXY_USERNAME", TtmlNode.ANONYMOUS_REGION_ID);
    }

    public static String al(Context context) {
        return C3791b.av(context).f10158e.getString("SP_PROXY_PASSWORD", TtmlNode.ANONYMOUS_REGION_ID);
    }

    public static int am(Context context) {
        return C3791b.av(context).f10158e.getInt("SP_PROXY_ENABLE", 0);
    }

    public static boolean an(Context context) {
        return C3791b.av(context).f10158e.getBoolean("SP_PROXY_HEALTH", true);
    }

    public static ArrayList<ProxyServerModel> ao(Context context) {
        try {
            ArrayList arrayList = new ArrayList();
            String a = C3791b.av(context).m14063a("SP_PROXY_LIST");
            if (!TextUtils.isEmpty(a)) {
                arrayList = (ArrayList) new C1768f().m8393a(a, new b$3().b());
            }
            if (arrayList == null || arrayList.size() == 0) {
                return new ArrayList();
            }
            ArrayList<ProxyServerModel> arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ProxyServerModel proxyServerModel = (ProxyServerModel) it.next();
                long localExpireTime = proxyServerModel.getLocalExpireTime() - System.currentTimeMillis();
                if (proxyServerModel.getLocalExpireTime() - System.currentTimeMillis() >= 0) {
                    arrayList2.add(proxyServerModel);
                }
            }
            return arrayList2;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    public static boolean ap(Context context) {
        return C3791b.av(context).m14068a("FILTER_CHANNEL", true);
    }

    public static boolean aq(Context context) {
        return C3791b.av(context).m14068a("THEME_SHOW", false);
    }

    public static String ar(Context context) {
        if (!TextUtils.isEmpty(C3791b.av(context).m14063a("SECURITY_TOKEN"))) {
            return C3791b.av(context).m14063a("SECURITY_TOKEN");
        }
        C3791b.as(context);
        return C3791b.av(context).m14063a("SECURITY_TOKEN");
    }

    public static void as(Context context) {
        C3791b.av(context).m14067a("SECURITY_TOKEN", C3791b.m13959d());
    }

    public static boolean at(Context context) {
        return C3791b.av(context).m14068a("CURRENT_USER_ADDED_TO_FAVE", false);
    }

    public static String au(Context context) {
        return C3791b.av(context).m14063a("PUSHTOKEN");
    }

    private static C3791b av(Context context) {
        if (f10151d == null) {
            f10151d = new C3791b(context, "SP_MAIN");
        }
        return f10151d;
    }

    /* renamed from: b */
    public static long m13940b() {
        return C3791b.av(ApplicationLoader.applicationContext).f10158e.getLong("SP_PROXY_EXPIRE", 0);
    }

    /* renamed from: b */
    public static List<Long> m13941b(Context context) {
        if (f10152f == null) {
            Object a = C3791b.av(context).m14063a("HIDDEN_CHATS");
            f10152f = new ArrayList();
            if (!TextUtils.isEmpty(a)) {
                String[] split = a.split(",");
                if (split != null && split.length > 0) {
                    for (String parseLong : split) {
                        try {
                            f10152f.add(Long.valueOf(Long.parseLong(parseLong)));
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        return f10152f;
    }

    /* renamed from: b */
    public static void m13942b(long j) {
        C3791b.av(ApplicationLoader.applicationContext).m14066a("SP_PROXY_EXPIRE", Long.valueOf(j));
    }

    /* renamed from: b */
    public static void m13943b(Context context, int i) {
        C3791b.av(context).m14064a("APP_VERSION", i);
    }

    /* renamed from: b */
    public static void m13944b(Context context, long j) {
        Object a = C3791b.av(context).m14063a("HIDDEN_CHATS");
        if (!TextUtils.isEmpty(a)) {
            String[] split = a.split(",");
            if (split != null && split.length > 0) {
                String str = TtmlNode.ANONYMOUS_REGION_ID;
                for (int i = 0; i < split.length; i++) {
                    try {
                        if (Long.parseLong(split[i]) != j) {
                            str = str + split[i];
                            if (i < split.length - 1) {
                                str = str + ",";
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                C3791b.av(context).m14067a("HIDDEN_CHATS", str);
                f10152f = null;
            }
        }
    }

    /* renamed from: b */
    public static void m13945b(Context context, String str) {
        C3791b.av(context).m14067a("PFLC", str);
    }

    /* renamed from: b */
    public static void m13946b(Context context, String str, String str2) {
        try {
            C3791b.av(context).m14067a(str2, new C1768f().m8395a(str.split(",")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: b */
    public static void m13947b(Context context, ArrayList<ProxyServerModel> arrayList) {
        C3791b.av(context).m14067a("SP_PROXY_LIST", new C1768f().m8395a((Object) arrayList));
    }

    /* renamed from: b */
    public static void m13948b(Context context, boolean z) {
        C3791b.av(context).m14070b("SORT_DIALOGS_BY_UNREAD", z);
    }

    /* renamed from: b */
    public static void m13949b(boolean z) {
        C3791b.av(ApplicationLoader.applicationContext).m14070b("PAYMENT_ENABLE", z);
    }

    /* renamed from: c */
    public static List<Long> m13950c(Context context) {
        if (f10153g == null) {
            Object a = C3791b.av(context).m14063a("HIDDEN_CONTACTS");
            f10153g = new ArrayList();
            if (!TextUtils.isEmpty(a)) {
                String[] split = a.split(",");
                if (split != null && split.length > 0) {
                    for (String parseLong : split) {
                        try {
                            f10153g.add(Long.valueOf(Long.parseLong(parseLong)));
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        return f10153g;
    }

    /* renamed from: c */
    public static void m13951c(long j) {
        C3791b.av(ApplicationLoader.applicationContext).m14066a("SP_LAST_CALL_URL", Long.valueOf(j));
    }

    /* renamed from: c */
    public static void m13952c(Context context, int i) {
        C3791b.av(context).m14064a("SELECTED_FONT", i);
    }

    /* renamed from: c */
    public static void m13953c(Context context, String str) {
        C3791b.av(context).m14067a("START_DOWNLOAD_TIME", str);
    }

    /* renamed from: c */
    public static void m13954c(Context context, boolean z) {
        f10154h = Boolean.valueOf(z);
        C3791b.av(context).m14070b("CHECK_LOCAL_URL", z);
    }

    /* renamed from: c */
    public static void m13955c(boolean z) {
        C3791b.av(ApplicationLoader.applicationContext).m14070b("SP_TAG_POST_ENABLE", z);
    }

    /* renamed from: c */
    public static boolean m13956c() {
        return true;
    }

    /* renamed from: c */
    public static boolean m13957c(Context context, long j) {
        try {
            return C3791b.m13941b(context).contains(Long.valueOf(j));
        } catch (Exception e) {
            return false;
        }
    }

    /* renamed from: c */
    public static boolean m13958c(Context context, String str, String str2) {
        boolean z = false;
        try {
            PublicKey E = C3791b.m13901E(context, "sign/PaymentPublic.cer");
            String[] split = str2.split("#");
            Integer.parseInt(split[0]);
            Integer.parseInt(split[1]);
            byte[] decode = Base64.decode(split[2], 2);
            Signature instance = Signature.getInstance("SHA256withRSA");
            instance.initVerify(E);
            instance.update(str.getBytes());
            z = instance.verify(decode);
        } catch (Exception e) {
        }
        return z;
    }

    /* renamed from: d */
    public static String m13959d() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        int nextInt = random.nextInt(100);
        for (int i = 0; i < nextInt; i++) {
            stringBuilder.append((char) (random.nextInt(96) + 32));
        }
        return stringBuilder.toString();
    }

    /* renamed from: d */
    public static String m13960d(Context context) {
        String a = C3791b.av(context).m14063a("PFLC");
        return a == null ? TtmlNode.ANONYMOUS_REGION_ID : a;
    }

    /* renamed from: d */
    public static void m13961d(Context context, int i) {
        f10156j = Integer.valueOf(i);
        C3791b.av(context).m14064a("SUBTITLE_TYPE", i);
    }

    /* renamed from: d */
    public static void m13962d(Context context, long j) {
        Object a = C3791b.av(context).m14063a("HIDDEN_CONTACTS");
        C3791b.av(context).m14067a("HIDDEN_CONTACTS", TextUtils.isEmpty(a) ? String.valueOf(j) : a + "," + String.valueOf(j));
        f10153g = null;
    }

    /* renamed from: d */
    public static void m13963d(Context context, String str) {
        C3791b.av(context).m14067a("END_DOWNLOAD_TIME", str);
    }

    /* renamed from: d */
    public static void m13964d(Context context, boolean z) {
        C3791b.av(context).m14070b("SHOW_ELECTION_TAB", z);
    }

    /* renamed from: d */
    public static void m13965d(boolean z) {
        C3791b.av(ApplicationLoader.applicationContext).m14070b("SP_SEND_STAT", z);
    }

    /* renamed from: e */
    public static String m13966e(Context context, String str) {
        return C3791b.av(context).m14063a(str);
    }

    /* renamed from: e */
    public static String m13967e(String str) {
        ArrayList B = C3791b.m13895B(ApplicationLoader.applicationContext, str);
        return (B == null || B.size() <= 0) ? TtmlNode.ANONYMOUS_REGION_ID : (String) B.get(0);
    }

    /* renamed from: e */
    public static void m13968e(Context context, int i) {
        C3791b.av(context).m14064a("IS_ENABLE_GHOST", i);
    }

    /* renamed from: e */
    public static void m13969e(Context context, long j) {
        Object a = C3791b.av(context).m14063a("HIDDEN_CONTACTS");
        if (!TextUtils.isEmpty(a)) {
            String[] split = a.split(",");
            if (split != null && split.length > 0) {
                String str = TtmlNode.ANONYMOUS_REGION_ID;
                for (int i = 0; i < split.length; i++) {
                    try {
                        if (Long.parseLong(split[i]) != j) {
                            str = str + split[i];
                            if (i < split.length - 1) {
                                str = str + ",";
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                C3791b.av(context).m14067a("HIDDEN_CONTACTS", str);
                f10153g = null;
            }
        }
    }

    /* renamed from: e */
    public static void m13970e(Context context, boolean z) {
        C3791b.av(context).m14070b("SHOW_HOT_TAB", z);
    }

    /* renamed from: e */
    public static boolean m13971e() {
        return C3791b.av(ApplicationLoader.applicationContext).m14068a("AP_REGISTERED", false);
    }

    /* renamed from: e */
    public static boolean m13972e(Context context) {
        return C3791b.av(context).m14068a("SORT_DIALOGS_BY_UNREAD", false);
    }

    /* renamed from: f */
    public static String m13973f(Context context) {
        Object a = C3791b.av(context).m14063a("START_DOWNLOAD_TIME");
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        String str = "2:00";
        C3791b.m13953c(context, str);
        return str;
    }

    /* renamed from: f */
    public static void m13974f(Context context, int i) {
        C3791b.av(context).m14064a("SP_TEL_APP_ID", i);
    }

    /* renamed from: f */
    public static void m13975f(Context context, String str) {
        C3791b.av(context).m14067a("MAIN_DOMAIN", str);
    }

    /* renamed from: f */
    public static void m13976f(Context context, boolean z) {
        C3791b.av(context).m14070b("SHOW_SEARCH_TAB", z);
    }

    /* renamed from: f */
    public static void m13977f(String str) {
        Iterable B = C3791b.m13895B(ApplicationLoader.applicationContext, str);
        String str2 = (String) B.get(0);
        B.remove(str2);
        B.add(str2);
        C3791b.m13946b(ApplicationLoader.applicationContext, TextUtils.join(",", B), str);
    }

    /* renamed from: f */
    public static boolean m13978f() {
        return C3791b.av(ApplicationLoader.applicationContext).m14068a("PAYMENT_ENABLE", false);
    }

    /* renamed from: f */
    public static boolean m13979f(Context context, long j) {
        try {
            return C3791b.m13950c(context).contains(Long.valueOf(j));
        } catch (Exception e) {
            return false;
        }
    }

    /* renamed from: g */
    public static String m13980g(Context context) {
        Object a = C3791b.av(context).m14063a("END_DOWNLOAD_TIME");
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        String str = "8:00";
        C3791b.m13963d(context, str);
        return str;
    }

    /* renamed from: g */
    public static void m13981g(Context context, int i) {
        C3791b.av(context).m14064a("SP_PROXY_ENABLE", i);
    }

    /* renamed from: g */
    public static void m13982g(Context context, long j) {
        C3791b.av(context).m14066a("SAVE_CONTACT_PERIOD", Long.valueOf(j));
    }

    /* renamed from: g */
    public static void m13983g(Context context, String str) {
        C3791b.av(context).m14067a("INVITE_MESSEAGE", str);
    }

    /* renamed from: g */
    public static void m13984g(Context context, boolean z) {
        C3791b.av(context).m14070b("SHOW_NEWS_LIST", z);
    }

    /* renamed from: g */
    public static void m13985g(String str) {
        C3791b.av(ApplicationLoader.applicationContext).m14067a("SP_CHECK_APP", str);
    }

    /* renamed from: g */
    public static boolean m13986g() {
        return C3791b.av(ApplicationLoader.applicationContext).m14068a("SP_TAG_POST_ENABLE", false);
    }

    /* renamed from: h */
    public static int m13987h() {
        return C3791b.av(ApplicationLoader.applicationContext).m14069b("VIDEO_PLAY_COUNT");
    }

    /* renamed from: h */
    public static void m13988h(Context context, long j) {
        C3791b.av(context).m14066a("NEWS_TAG_ID", Long.valueOf(j));
    }

    /* renamed from: h */
    public static void m13989h(Context context, String str) {
        C3791b.av(context).m14067a("TAG_API_BASE_URL", str);
    }

    /* renamed from: h */
    public static void m13990h(Context context, boolean z) {
        f10155i = Boolean.valueOf(z);
        C3791b.av(context).m14070b("SHOW_HIDDEN_DIALOGS", z);
    }

    /* renamed from: h */
    public static void m13991h(String str) {
        C3791b.av(ApplicationLoader.applicationContext).m14067a("SP_CALL_URL", str);
    }

    /* renamed from: h */
    public static boolean m13992h(Context context) {
        if (f10154h == null) {
            f10154h = Boolean.valueOf(C3791b.av(context).m14068a("CHECK_LOCAL_URL", true));
        }
        return f10154h.booleanValue();
    }

    /* renamed from: i */
    public static int m13993i(Context context) {
        return C3791b.av(context).m14069b("APP_VERSION");
    }

    /* renamed from: i */
    public static void m13994i(Context context, long j) {
        C3791b.av(context).m14066a("SP_LAST_SUCCESS_SYNC_CONTACT", Long.valueOf(j));
    }

    /* renamed from: i */
    public static void m13995i(Context context, String str) {
        C3791b.av(context).m14067a("DIALOG_TABS", str);
    }

    /* renamed from: i */
    public static void m13996i(Context context, boolean z) {
        C3791b.av(context).m14070b("OFF_MODE", z);
    }

    /* renamed from: i */
    public static void m13997i(String str) {
        C3791b.av(ApplicationLoader.applicationContext).m14067a("SP_SUPPORT_TEXT", str);
    }

    /* renamed from: i */
    public static boolean m13998i() {
        return C3791b.av(ApplicationLoader.applicationContext).m14068a("SP_SEND_STAT", false);
    }

    /* renamed from: j */
    public static String m13999j(Context context) {
        return TextUtils.isEmpty(C3791b.av(context).m14063a("INVITE_MESSEAGE")) ? LocaleController.getString("inviteMessage", R.string.inviteMessage) : C3791b.av(context).m14063a("INVITE_MESSEAGE");
    }

    /* renamed from: j */
    public static CAI m14000j() {
        CAI cai = new CAI();
        String a = C3791b.av(ApplicationLoader.applicationContext).m14063a("SP_CHECK_APP");
        return !TextUtils.isEmpty(a) ? (CAI) new C1768f().m8392a(a, CAI.class) : cai;
    }

    /* renamed from: j */
    public static void m14001j(Context context, long j) {
        C3791b.av(context).m14066a("SP_LAST_SUCCESS_SYNC_LOCATION", Long.valueOf(j));
    }

    /* renamed from: j */
    public static void m14002j(Context context, String str) {
        C3791b.av(context).m14067a("SUBTITLE", str);
    }

    /* renamed from: j */
    public static void m14003j(Context context, boolean z) {
        C3791b.av(context).m14070b("SP_FIRST_TIME_SYNC", z);
    }

    /* renamed from: k */
    public static int m14004k(Context context) {
        int b = C3791b.av(context).m14069b(InstanceID.ERROR_TIMEOUT);
        return b == 0 ? DefaultLoadControl.DEFAULT_MAX_BUFFER_MS : b;
    }

    /* renamed from: k */
    public static long m14005k() {
        return C3791b.av(ApplicationLoader.applicationContext).m14071c("SP_LAST_CALL_URL").longValue();
    }

    /* renamed from: k */
    public static long m14006k(Context context, String str) {
        return C3791b.av(context).m14071c(str).longValue();
    }

    /* renamed from: k */
    public static void m14007k(Context context, long j) {
        C3791b.av(context).m14066a("SP_LAST_SUCCESS_SYNC_CHANNEL", Long.valueOf(j));
    }

    /* renamed from: k */
    public static void m14008k(Context context, boolean z) {
        C3791b.av(context).m14070b("IS_ENABLE_STREAM", z);
    }

    /* renamed from: l */
    public static String m14009l() {
        return C3791b.av(ApplicationLoader.applicationContext).m14063a("SP_CALL_URL");
    }

    /* renamed from: l */
    public static void m14010l(Context context) {
        C3791b.av(context).m14066a("HOT_POST_RANDOM_NUM", Long.valueOf(C3791b.m14014m(context) + 1));
    }

    /* renamed from: l */
    public static void m14011l(Context context, long j) {
        C3791b.av(context).m14066a("SP_CHANNEL_SYNC_PERIOD", Long.valueOf(j));
    }

    /* renamed from: l */
    public static void m14012l(Context context, boolean z) {
        C3791b.av(context).m14070b("IS_ENABLE_FREE_ICON", z);
    }

    /* renamed from: l */
    public static boolean m14013l(Context context, String str) {
        return C3791b.av(context).m14068a("IS_JOIN_" + str, false);
    }

    /* renamed from: m */
    public static long m14014m(Context context) {
        return C3791b.av(context).m14071c("HOT_POST_RANDOM_NUM").longValue();
    }

    /* renamed from: m */
    public static String m14015m() {
        return C3791b.av(ApplicationLoader.applicationContext).m14063a("SP_SUPPORT_TEXT");
    }

    /* renamed from: m */
    public static void m14016m(Context context, long j) {
        C3791b.av(context).m14066a("SP_CONTACT_SYNC_PERIOD", Long.valueOf(j));
    }

    /* renamed from: m */
    public static void m14017m(Context context, String str) {
        C3791b.av(context).m14067a("SP_TEL_APP_HASH", str);
    }

    /* renamed from: m */
    public static void m14018m(Context context, boolean z) {
        C3791b.av(context).m14070b("SHOW_FREE_POPUP", z);
    }

    /* renamed from: n */
    public static void m14019n(Context context, long j) {
        C3791b.av(context).m14066a("SP_LOCATION_SYNC_PERIOD", Long.valueOf(j));
    }

    /* renamed from: n */
    public static void m14020n(Context context, String str) {
        C3791b.av(context).m14067a("MAIN_DOMAIN_CHECK_URL", str);
    }

    /* renamed from: n */
    public static void m14021n(Context context, boolean z) {
        C3791b.av(context).m14070b("SCHEDULED_DOWNLOAD", z);
    }

    /* renamed from: n */
    public static boolean m14022n(Context context) {
        return C3791b.av(context).m14068a("SHOW_HOT_TAB", true);
    }

    /* renamed from: o */
    public static void m14023o(Context context, long j) {
        C3791b.av(context).m14066a("SP_SUPET_SYNC_PERIOD", Long.valueOf(j));
    }

    /* renamed from: o */
    public static void m14024o(Context context, String str) {
        try {
            C3791b.av(context).m14067a("SP_MIRROR_ADDRESS_FOR_CHECK_URL", new C1768f().m8395a(str.split(",")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: o */
    public static void m14025o(Context context, boolean z) {
        C3791b.av(context).m14070b("WIFI_ON", z);
    }

    /* renamed from: o */
    public static boolean m14026o(Context context) {
        return C3791b.av(context).m14068a("SHOW_SEARCH_TAB", true);
    }

    /* renamed from: p */
    public static void m14027p(Context context, long j) {
        C3791b.av(context).m14066a("SP_LAST_SUCCESSFULLY_SYNC_SUPER", Long.valueOf(j));
    }

    /* renamed from: p */
    public static void m14028p(Context context, String str) {
        C3791b.av(context).m14067a("JOIN_OFFICIAL_CHANNEL", str);
    }

    /* renamed from: p */
    public static void m14029p(Context context, boolean z) {
        C3791b.av(context).m14070b("WIFI_ON", z);
    }

    /* renamed from: p */
    public static boolean m14030p(Context context) {
        return false;
    }

    /* renamed from: q */
    public static void m14031q(Context context, long j) {
        C3791b.av(context).m14066a("SP_STATE_SYNC_PERIOD", Long.valueOf(j));
    }

    /* renamed from: q */
    public static void m14032q(Context context, String str) {
        C3791b.av(context).m14067a("SP_FILTER_MESSAGE", str);
    }

    /* renamed from: q */
    public static void m14033q(Context context, boolean z) {
        C3791b.av(context).m14070b("IS_ENABLE_ADS", z);
    }

    /* renamed from: q */
    public static boolean m14034q(Context context) {
        return C3791b.av(context).m14068a("SHOW_NEWS_LIST", false);
    }

    /* renamed from: r */
    public static long m14035r(Context context) {
        return C3791b.av(context).m14071c("NEWS_TAG_ID").longValue();
    }

    /* renamed from: r */
    public static void m14036r(Context context, long j) {
        C3791b.av(context).m14066a("SP_LAST_SUCCESSFULLY_SYNC_STATE", Long.valueOf(j));
    }

    /* renamed from: r */
    public static void m14037r(Context context, String str) {
        C3791b.av(context).m14067a("SP_ADS_TU", str);
    }

    /* renamed from: r */
    public static void m14038r(Context context, boolean z) {
        C3791b.av(context).m14070b("IS_SHOW_COIN", z);
    }

    /* renamed from: s */
    public static float m14039s(Context context) {
        float floatValue = C3791b.av(context).m14072d("TEXT_SIZE").floatValue();
        return floatValue < 14.0f ? 16.0f : floatValue;
    }

    /* renamed from: s */
    public static void m14040s(Context context, long j) {
        C3791b.av(context).m14066a("SP_BOT_SYNC_PERIOD", Long.valueOf(j));
    }

    /* renamed from: s */
    public static void m14041s(Context context, String str) {
        C3791b.av(context).m14067a("SP_ADS_TU1", str);
    }

    /* renamed from: s */
    public static void m14042s(Context context, boolean z) {
        C3791b.av(context).m14070b("SP_PROXY_HEALTH", z);
    }

    /* renamed from: t */
    public static String m14043t(Context context) {
        String str = TtmlNode.ANONYMOUS_REGION_ID;
        return TextUtils.isEmpty(C3791b.av(context).m14063a("TAG_API_BASE_URL")) ? "http://betaws.tag.ir/tag/api/mtag/" : C3791b.av(context).m14063a("TAG_API_BASE_URL").trim();
    }

    /* renamed from: t */
    public static void m14044t(Context context, long j) {
        C3791b.av(context).m14066a("SP_LAST_SUCCESSFULLY_SYNC_BOT", Long.valueOf(j));
    }

    /* renamed from: t */
    public static void m14045t(Context context, String str) {
        C3791b.av(context).m14067a("SP_ADS_JOIN_MSG", str);
    }

    /* renamed from: t */
    public static void m14046t(Context context, boolean z) {
        C3791b.av(context).m14070b("SP_SHOW_FILTER_DIALOG", z);
    }

    /* renamed from: u */
    public static ArrayList<DialogTab> m14047u(Context context) {
        ArrayList<DialogTab> arrayList = new ArrayList();
        String a = C3791b.av(context).m14063a("DIALOG_TABS");
        DialogTab dialogTab;
        if (TextUtils.isEmpty(a)) {
            dialogTab = new DialogTab(R.layout.tab_ads_drawable, R.drawable.ic_cash_off, "TAB_ADS", false, 12, LocaleController.getString("Advertise", R.string.Advertise));
            DialogTab dialogTab2 = new DialogTab(R.layout.tab_unread_drawable, R.drawable.tab_unread, "TAB_UNREAD_CHATS", true, 11, LocaleController.getString("unreadChats", R.string.unreadChats));
            DialogTab dialogTab3 = new DialogTab(R.layout.tab_locked_chats_drawable, R.drawable.tab_user, "TAB_LOCK", true, 10, LocaleController.getString("lockedChats", R.string.lockedChats));
            DialogTab dialogTab4 = new DialogTab(R.layout.tab_fav_drawable, R.drawable.tab_favs, "TAB_FAVES", false, 8, LocaleController.getString("Favorites", R.string.Favorites));
            DialogTab dialogTab5 = new DialogTab(R.layout.tab_bot_drawable, R.drawable.tab_bot, "TAB_BOTS", true, 6, LocaleController.getString("Bots", R.string.Bots));
            DialogTab dialogTab6 = new DialogTab(R.layout.tab_channel_drawable, R.drawable.tab_channel, "TAB_CHANNELS", false, 5, LocaleController.getString("Channels", R.string.Channels));
            DialogTab dialogTab7 = new DialogTab(R.layout.tab_super_group_drawable, R.drawable.tab_supergroup, "TAB_SGROUP", false, 7, LocaleController.getString("SuperGroups", R.string.SuperGroups));
            DialogTab dialogTab8 = new DialogTab(R.layout.tab_group_drawable, R.drawable.tab_group, "TAB_GROUPS", false, 4, LocaleController.getString("Groups", R.string.Groups));
            DialogTab dialogTab9 = new DialogTab(R.layout.tab_user_drawable, R.drawable.tab_user, "TAB_USERS", false, 3, LocaleController.getString("Users", R.string.Users));
            arrayList.add(new DialogTab(R.layout.tab_all_drawable, R.drawable.tab_all, "TAB_ALL", false, 0, LocaleController.getString("MyAppName", R.string.MyAppName)));
            arrayList.add(dialogTab9);
            arrayList.add(dialogTab8);
            arrayList.add(dialogTab7);
            arrayList.add(dialogTab6);
            arrayList.add(dialogTab5);
            arrayList.add(dialogTab2);
            arrayList.add(dialogTab4);
            arrayList.add(dialogTab3);
            if (C3791b.ab(context) && C3791b.m13922Z(context).size() > 0) {
                arrayList.add(dialogTab);
            }
            return arrayList;
        }
        ArrayList arrayList2 = (ArrayList) new C1768f().m8393a(a, new b$1().b());
        ArrayList<DialogTab> arrayList3 = new ArrayList();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            dialogTab = (DialogTab) it.next();
            switch (dialogTab.getDialogType()) {
                case 0:
                    dialogTab.setTabLayoutResource(R.layout.tab_all_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_all);
                    break;
                case 3:
                    dialogTab.setTabLayoutResource(R.layout.tab_user_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_user);
                    break;
                case 4:
                    dialogTab.setTabLayoutResource(R.layout.tab_group_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_group);
                    break;
                case 5:
                    dialogTab.setTabLayoutResource(R.layout.tab_channel_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_channel);
                    break;
                case 6:
                    dialogTab.setTabLayoutResource(R.layout.tab_bot_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_bot);
                    break;
                case 7:
                    dialogTab.setTabLayoutResource(R.layout.tab_super_group_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_supergroup);
                    break;
                case 8:
                    dialogTab.setTabLayoutResource(R.layout.tab_fav_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_favs);
                    break;
                case 10:
                    dialogTab.setTabLayoutResource(R.layout.tab_locked_chats_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_user);
                    break;
                case 11:
                    dialogTab.setTabLayoutResource(R.layout.tab_unread_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_unread);
                    break;
                case 12:
                    dialogTab.setTabLayoutResource(R.layout.tab_ads_drawable);
                    dialogTab.setTabDrawable(R.drawable.tab_coin);
                    break;
                default:
                    break;
            }
            arrayList3.add(dialogTab);
        }
        return arrayList3;
    }

    /* renamed from: u */
    public static void m14048u(Context context, long j) {
        C3791b.av(context).m14066a("CHANGE_JOIN_TIME", Long.valueOf(j));
    }

    /* renamed from: u */
    public static void m14049u(Context context, String str) {
        C3791b.av(context).m14067a("SP_ADS_URL", str);
    }

    /* renamed from: u */
    public static void m14050u(Context context, boolean z) {
        C3791b.av(context).m14070b("FILTER_CHANNEL", z);
    }

    /* renamed from: v */
    public static ArrayList<DialogTab> m14051v(Context context) {
        ArrayList<DialogTab> arrayList = new ArrayList();
        Iterator it = C3791b.m14047u(context).iterator();
        while (it.hasNext()) {
            DialogTab dialogTab = (DialogTab) it.next();
            if (!dialogTab.isHidden()) {
                arrayList.add(dialogTab);
            }
        }
        return arrayList;
    }

    /* renamed from: v */
    public static void m14052v(Context context, String str) {
        C3791b.av(context).m14067a("SP_ADS_TU_URL", str);
    }

    /* renamed from: v */
    public static void m14053v(Context context, boolean z) {
        C3791b.av(context).m14070b("THEME_SHOW", z);
    }

    /* renamed from: w */
    public static int m14054w(Context context) {
        return C3791b.av(context).m14069b("SELECTED_FONT");
    }

    /* renamed from: w */
    public static void m14055w(Context context, String str) {
        C3791b.av(context).m14067a("SP_PROXY_ADDRESS", str);
    }

    /* renamed from: w */
    public static void m14056w(Context context, boolean z) {
        C3791b.av(context).m14070b("CURRENT_USER_ADDED_TO_FAVE", z);
    }

    /* renamed from: x */
    public static void m14057x(Context context, String str) {
        C3791b.av(context).m14067a("SP_PROXY_PORT", str);
    }

    /* renamed from: x */
    public static boolean m14058x(Context context) {
        if (f10155i == null) {
            f10155i = Boolean.valueOf(C3791b.av(context).m14068a("SHOW_HIDDEN_DIALOGS", false));
        }
        return f10155i.booleanValue();
    }

    /* renamed from: y */
    public static void m14059y(Context context, String str) {
        C3791b.av(context).m14067a("SP_PROXY_USERNAME", str);
    }

    /* renamed from: y */
    public static boolean m14060y(Context context) {
        return C3791b.av(context).m14068a("OFF_MODE", false);
    }

    /* renamed from: z */
    public static String m14061z(Context context) {
        return C3791b.av(context).m14063a("SUBTITLE");
    }

    /* renamed from: z */
    public static void m14062z(Context context, String str) {
        C3791b.av(context).m14067a("SP_PROXY_PASSWORD", str);
    }

    /* renamed from: a */
    public String m14063a(String str) {
        return this.f10158e.getString(str, null);
    }

    /* renamed from: a */
    public void m14064a(String str, int i) {
        this.f10157a.putInt(str, i);
        this.f10157a.commit();
    }

    /* renamed from: a */
    public void m14065a(String str, Float f) {
        this.f10157a.putFloat(str, f.floatValue());
        this.f10157a.commit();
    }

    /* renamed from: a */
    public void m14066a(String str, Long l) {
        this.f10157a.putLong(str, l.longValue());
        this.f10157a.commit();
    }

    /* renamed from: a */
    public void m14067a(String str, String str2) {
        this.f10157a.putString(str, str2);
        this.f10157a.commit();
    }

    /* renamed from: a */
    public boolean m14068a(String str, boolean z) {
        return this.f10158e.getBoolean(str, z);
    }

    /* renamed from: b */
    public int m14069b(String str) {
        return this.f10158e.getInt(str, 0);
    }

    /* renamed from: b */
    public void m14070b(String str, boolean z) {
        this.f10157a.putBoolean(str, z);
        this.f10157a.commit();
    }

    /* renamed from: c */
    public Long m14071c(String str) {
        return Long.valueOf(this.f10158e.getLong(str, 0));
    }

    /* renamed from: d */
    public Float m14072d(String str) {
        return Float.valueOf(this.f10158e.getFloat(str, BitmapDescriptorFactory.HUE_RED));
    }
}
