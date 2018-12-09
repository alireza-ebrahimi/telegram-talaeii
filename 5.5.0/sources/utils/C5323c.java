package utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.p098a.C1768f;
import com.google.p098a.p103c.C1748a;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.telegram.customization.Activities.AlertActivity;
import org.telegram.customization.Model.FilterResponse;
import org.telegram.customization.Model.HotgramNotification;
import org.telegram.customization.Model.JoinChannelModel;
import org.telegram.customization.Model.Setting;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;

/* renamed from: utils.c */
public class C5323c {
    /* renamed from: a */
    public static String m14144a(String str) {
        List<String> asList = Arrays.asList(str.split(","));
        Iterable arrayList = new ArrayList();
        if (asList != null && asList.size() > 0) {
            for (String str2 : asList) {
                String str22;
                String str3 = "http://";
                String str4 = TtmlNode.ANONYMOUS_REGION_ID;
                String str5 = TtmlNode.ANONYMOUS_REGION_ID;
                if (str22.startsWith("h")) {
                    str5 = "hotgram.ir/";
                    str22 = str22.replace("h", TtmlNode.ANONYMOUS_REGION_ID);
                } else if (str22.startsWith("s")) {
                    str5 = "harsobh.com/";
                    str22 = str22.replace("s", TtmlNode.ANONYMOUS_REGION_ID);
                } else if (str22.startsWith("t")) {
                    str5 = "talagram.ir/";
                    str22 = str22.replace("t", TtmlNode.ANONYMOUS_REGION_ID);
                } else {
                    str22 = str5;
                    str5 = str4;
                }
                arrayList.add(str3 + "lh" + str22 + "." + str5);
            }
        }
        return arrayList.size() > 0 ? TextUtils.join(",", arrayList) : TtmlNode.ANONYMOUS_REGION_ID;
    }

    /* renamed from: a */
    public static void m14146a(String str, String str2) {
        Intent intent = new Intent("android.intent.action.MAIN", null);
        intent.addCategory("android.intent.category.LAUNCHER");
        for (ResolveInfo resolveInfo : ApplicationLoader.applicationContext.getPackageManager().queryIntentActivities(intent, 0)) {
            if (resolveInfo.activityInfo.packageName.contentEquals(str)) {
                intent = new Intent(ApplicationLoader.applicationContext, AlertActivity.class);
                intent.putExtra("EXTRA_UPDATE_MODEL", str2);
                ApplicationLoader.applicationContext.startActivity(intent);
            }
        }
    }

    /* renamed from: a */
    public static void m14147a(final ArrayList<Setting> arrayList, final Context context) {
        final Handler handler = new Handler(Looper.getMainLooper());
        new Thread() {

            /* renamed from: utils.c$1$2 */
            class C53212 extends C1748a<ArrayList<FilterResponse>> {
                /* renamed from: d */
                final /* synthetic */ C53221 f10236d;

                C53212(C53221 c53221) {
                    this.f10236d = c53221;
                }
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                r8 = this;
                r2 = -1;
                super.run();
                r0 = r2;	 Catch:{ Exception -> 0x0030 }
                r3 = r0.iterator();	 Catch:{ Exception -> 0x0030 }
            L_0x000a:
                r0 = r3.hasNext();	 Catch:{ Exception -> 0x0030 }
                if (r0 == 0) goto L_0x0031;
            L_0x0010:
                r0 = r3.next();	 Catch:{ Exception -> 0x0030 }
                r0 = (org.telegram.customization.Model.Setting) r0;	 Catch:{ Exception -> 0x0030 }
                r1 = r0.getKey();	 Catch:{ Exception -> 0x0030 }
                r4 = r1.hashCode();	 Catch:{ Exception -> 0x0030 }
                switch(r4) {
                    case -628486815: goto L_0x0117;
                    case -628486814: goto L_0x0124;
                    case 1367331562: goto L_0x003d;
                    case 1367331563: goto L_0x0032;
                    case 1367331566: goto L_0x0048;
                    case 1367331567: goto L_0x0053;
                    case 1367331568: goto L_0x005e;
                    case 1367331569: goto L_0x0069;
                    case 1367331570: goto L_0x0074;
                    case 1367331571: goto L_0x007f;
                    case 1367331593: goto L_0x008a;
                    case 1367331594: goto L_0x0096;
                    case 1367331595: goto L_0x00a2;
                    case 1367331596: goto L_0x00c9;
                    case 1367331597: goto L_0x00af;
                    case 1367331598: goto L_0x00bc;
                    case 1367331599: goto L_0x00d6;
                    case 1367331600: goto L_0x00e3;
                    case 1367331601: goto L_0x00f0;
                    case 1367331602: goto L_0x00fd;
                    case 1367331624: goto L_0x0131;
                    case 1367331625: goto L_0x010a;
                    case 1367331626: goto L_0x013e;
                    case 1367331627: goto L_0x014b;
                    case 1367331628: goto L_0x0158;
                    case 1367331629: goto L_0x0165;
                    case 1367331630: goto L_0x0172;
                    case 1367331631: goto L_0x017f;
                    case 1367331632: goto L_0x018c;
                    case 1367331633: goto L_0x0199;
                    case 1367331686: goto L_0x01a6;
                    case 1367331687: goto L_0x01b3;
                    case 1367331688: goto L_0x01c0;
                    case 1367331690: goto L_0x01cd;
                    case 1367331692: goto L_0x01da;
                    case 1367331693: goto L_0x01e7;
                    case 1367331718: goto L_0x01f4;
                    case 1367331719: goto L_0x021b;
                    case 1367331720: goto L_0x0235;
                    case 1367331721: goto L_0x020e;
                    case 1367331722: goto L_0x0228;
                    case 1367331723: goto L_0x0201;
                    case 1367331724: goto L_0x024f;
                    case 1367331725: goto L_0x0283;
                    case 1367331748: goto L_0x0290;
                    case 1367331749: goto L_0x029d;
                    case 1367331750: goto L_0x0242;
                    case 1367331751: goto L_0x02aa;
                    case 1367331753: goto L_0x025c;
                    case 1367331754: goto L_0x0269;
                    case 1367331755: goto L_0x0276;
                    case 1367331756: goto L_0x02b7;
                    case 1367331757: goto L_0x02c4;
                    case 1367331779: goto L_0x02d1;
                    case 1367331780: goto L_0x02de;
                    case 1367331781: goto L_0x02eb;
                    case 1367331782: goto L_0x02f8;
                    case 1367331783: goto L_0x0305;
                    default: goto L_0x0021;
                };	 Catch:{ Exception -> 0x0030 }
            L_0x0021:
                r1 = r2;
            L_0x0022:
                switch(r1) {
                    case 0: goto L_0x0026;
                    case 1: goto L_0x0312;
                    case 2: goto L_0x0327;
                    case 3: goto L_0x0332;
                    case 4: goto L_0x0347;
                    case 5: goto L_0x035c;
                    case 6: goto L_0x0371;
                    case 7: goto L_0x037c;
                    case 8: goto L_0x0387;
                    case 9: goto L_0x039c;
                    case 10: goto L_0x03b1;
                    case 11: goto L_0x03c6;
                    case 12: goto L_0x03db;
                    case 13: goto L_0x03f0;
                    case 14: goto L_0x043b;
                    case 15: goto L_0x0450;
                    case 16: goto L_0x0465;
                    case 17: goto L_0x047a;
                    case 18: goto L_0x048f;
                    case 19: goto L_0x04a4;
                    case 20: goto L_0x04a4;
                    case 21: goto L_0x04b9;
                    case 22: goto L_0x04ca;
                    case 23: goto L_0x04db;
                    case 24: goto L_0x04ec;
                    case 25: goto L_0x04fd;
                    case 26: goto L_0x0512;
                    case 27: goto L_0x0527;
                    case 28: goto L_0x0538;
                    case 29: goto L_0x0549;
                    case 30: goto L_0x055a;
                    case 31: goto L_0x056b;
                    case 32: goto L_0x057c;
                    case 33: goto L_0x059a;
                    case 34: goto L_0x05a6;
                    case 35: goto L_0x05fc;
                    case 36: goto L_0x0611;
                    case 37: goto L_0x061f;
                    case 38: goto L_0x062d;
                    case 39: goto L_0x063b;
                    case 40: goto L_0x0649;
                    case 41: goto L_0x0657;
                    case 42: goto L_0x0665;
                    case 43: goto L_0x0673;
                    case 44: goto L_0x0681;
                    case 45: goto L_0x0693;
                    case 46: goto L_0x06a5;
                    case 47: goto L_0x06b7;
                    case 48: goto L_0x06cc;
                    case 49: goto L_0x06e1;
                    case 50: goto L_0x06f6;
                    case 51: goto L_0x0709;
                    case 52: goto L_0x071c;
                    case 53: goto L_0x073c;
                    case 54: goto L_0x0751;
                    case 55: goto L_0x0764;
                    case 56: goto L_0x0793;
                    case 57: goto L_0x07e1;
                    default: goto L_0x0025;
                };	 Catch:{ Exception -> 0x0030 }
            L_0x0025:
                goto L_0x000a;
            L_0x0026:
                r1 = r3;	 Catch:{ Exception -> 0x0030 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                utils.p178a.C3791b.a(r1, r0);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0030:
                r0 = move-exception;
            L_0x0031:
                return;
            L_0x0032:
                r4 = "set-1001";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x003b:
                r1 = 0;
                goto L_0x0022;
            L_0x003d:
                r4 = "set-1000";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0046:
                r1 = 1;
                goto L_0x0022;
            L_0x0048:
                r4 = "set-1004";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0051:
                r1 = 2;
                goto L_0x0022;
            L_0x0053:
                r4 = "set-1005";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x005c:
                r1 = 3;
                goto L_0x0022;
            L_0x005e:
                r4 = "set-1006";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0067:
                r1 = 4;
                goto L_0x0022;
            L_0x0069:
                r4 = "set-1007";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0072:
                r1 = 5;
                goto L_0x0022;
            L_0x0074:
                r4 = "set-1008";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x007d:
                r1 = 6;
                goto L_0x0022;
            L_0x007f:
                r4 = "set-1009";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0088:
                r1 = 7;
                goto L_0x0022;
            L_0x008a:
                r4 = "set-1010";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0093:
                r1 = 8;
                goto L_0x0022;
            L_0x0096:
                r4 = "set-1011";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x009f:
                r1 = 9;
                goto L_0x0022;
            L_0x00a2:
                r4 = "set-1012";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x00ab:
                r1 = 10;
                goto L_0x0022;
            L_0x00af:
                r4 = "set-1014";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x00b8:
                r1 = 11;
                goto L_0x0022;
            L_0x00bc:
                r4 = "set-1015";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x00c5:
                r1 = 12;
                goto L_0x0022;
            L_0x00c9:
                r4 = "set-1013";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x00d2:
                r1 = 13;
                goto L_0x0022;
            L_0x00d6:
                r4 = "set-1016";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x00df:
                r1 = 14;
                goto L_0x0022;
            L_0x00e3:
                r4 = "set-1017";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x00ec:
                r1 = 15;
                goto L_0x0022;
            L_0x00f0:
                r4 = "set-1018";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x00f9:
                r1 = 16;
                goto L_0x0022;
            L_0x00fd:
                r4 = "set-1019";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0106:
                r1 = 17;
                goto L_0x0022;
            L_0x010a:
                r4 = "set-1021";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0113:
                r1 = 18;
                goto L_0x0022;
            L_0x0117:
                r4 = "fs-1002";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0120:
                r1 = 19;
                goto L_0x0022;
            L_0x0124:
                r4 = "fs-1003";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x012d:
                r1 = 20;
                goto L_0x0022;
            L_0x0131:
                r4 = "set-1020";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x013a:
                r1 = 21;
                goto L_0x0022;
            L_0x013e:
                r4 = "set-1022";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0147:
                r1 = 22;
                goto L_0x0022;
            L_0x014b:
                r4 = "set-1023";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0154:
                r1 = 23;
                goto L_0x0022;
            L_0x0158:
                r4 = "set-1024";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0161:
                r1 = 24;
                goto L_0x0022;
            L_0x0165:
                r4 = "set-1025";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x016e:
                r1 = 25;
                goto L_0x0022;
            L_0x0172:
                r4 = "set-1026";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x017b:
                r1 = 26;
                goto L_0x0022;
            L_0x017f:
                r4 = "set-1027";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0188:
                r1 = 27;
                goto L_0x0022;
            L_0x018c:
                r4 = "set-1028";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0195:
                r1 = 28;
                goto L_0x0022;
            L_0x0199:
                r4 = "set-1029";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x01a2:
                r1 = 29;
                goto L_0x0022;
            L_0x01a6:
                r4 = "set-1040";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x01af:
                r1 = 30;
                goto L_0x0022;
            L_0x01b3:
                r4 = "set-1041";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x01bc:
                r1 = 31;
                goto L_0x0022;
            L_0x01c0:
                r4 = "set-1042";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x01c9:
                r1 = 32;
                goto L_0x0022;
            L_0x01cd:
                r4 = "set-1044";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x01d6:
                r1 = 33;
                goto L_0x0022;
            L_0x01da:
                r4 = "set-1046";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x01e3:
                r1 = 34;
                goto L_0x0022;
            L_0x01e7:
                r4 = "set-1047";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x01f0:
                r1 = 35;
                goto L_0x0022;
            L_0x01f4:
                r4 = "set-1051";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x01fd:
                r1 = 36;
                goto L_0x0022;
            L_0x0201:
                r4 = "set-1056";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x020a:
                r1 = 37;
                goto L_0x0022;
            L_0x020e:
                r4 = "set-1054";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0217:
                r1 = 38;
                goto L_0x0022;
            L_0x021b:
                r4 = "set-1052";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0224:
                r1 = 39;
                goto L_0x0022;
            L_0x0228:
                r4 = "set-1055";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0231:
                r1 = 40;
                goto L_0x0022;
            L_0x0235:
                r4 = "set-1053";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x023e:
                r1 = 41;
                goto L_0x0022;
            L_0x0242:
                r4 = "set-1062";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x024b:
                r1 = 42;
                goto L_0x0022;
            L_0x024f:
                r4 = "set-1057";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0258:
                r1 = 43;
                goto L_0x0022;
            L_0x025c:
                r4 = "set-1065";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0265:
                r1 = 44;
                goto L_0x0022;
            L_0x0269:
                r4 = "set-1066";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0272:
                r1 = 45;
                goto L_0x0022;
            L_0x0276:
                r4 = "set-1067";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x027f:
                r1 = 46;
                goto L_0x0022;
            L_0x0283:
                r4 = "set-1058";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x028c:
                r1 = 47;
                goto L_0x0022;
            L_0x0290:
                r4 = "set-1060";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0299:
                r1 = 48;
                goto L_0x0022;
            L_0x029d:
                r4 = "set-1061";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x02a6:
                r1 = 49;
                goto L_0x0022;
            L_0x02aa:
                r4 = "set-1063";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x02b3:
                r1 = 50;
                goto L_0x0022;
            L_0x02b7:
                r4 = "set-1068";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x02c0:
                r1 = 51;
                goto L_0x0022;
            L_0x02c4:
                r4 = "set-1069";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x02cd:
                r1 = 52;
                goto L_0x0022;
            L_0x02d1:
                r4 = "set-1070";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x02da:
                r1 = 53;
                goto L_0x0022;
            L_0x02de:
                r4 = "set-1071";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x02e7:
                r1 = 54;
                goto L_0x0022;
            L_0x02eb:
                r4 = "set-1072";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x02f4:
                r1 = 55;
                goto L_0x0022;
            L_0x02f8:
                r4 = "set-1073";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x0301:
                r1 = 56;
                goto L_0x0022;
            L_0x0305:
                r4 = "set-1074";
                r1 = r1.equals(r4);	 Catch:{ Exception -> 0x0030 }
                if (r1 == 0) goto L_0x0021;
            L_0x030e:
                r1 = 57;
                goto L_0x0022;
            L_0x0312:
                r1 = r3;	 Catch:{ Exception -> 0x0321 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0321 }
                r0 = java.lang.Boolean.parseBoolean(r0);	 Catch:{ Exception -> 0x0321 }
                utils.p178a.C3791b.c(r1, r0);	 Catch:{ Exception -> 0x0321 }
                goto L_0x000a;
            L_0x0321:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0327:
                r1 = r3;	 Catch:{ Exception -> 0x0030 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                utils.p178a.C3791b.f(r1, r0);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0332:
                r1 = r3;	 Catch:{ Exception -> 0x0341 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0341 }
                r0 = java.lang.Boolean.parseBoolean(r0);	 Catch:{ Exception -> 0x0341 }
                utils.p178a.C3791b.d(r1, r0);	 Catch:{ Exception -> 0x0341 }
                goto L_0x000a;
            L_0x0341:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0347:
                r1 = r3;	 Catch:{ Exception -> 0x0356 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0356 }
                r0 = java.lang.Boolean.parseBoolean(r0);	 Catch:{ Exception -> 0x0356 }
                utils.p178a.C3791b.g(r1, r0);	 Catch:{ Exception -> 0x0356 }
                goto L_0x000a;
            L_0x0356:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x035c:
                r1 = r3;	 Catch:{ Exception -> 0x036b }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x036b }
                r4 = java.lang.Long.parseLong(r0);	 Catch:{ Exception -> 0x036b }
                utils.p178a.C3791b.h(r1, r4);	 Catch:{ Exception -> 0x036b }
                goto L_0x000a;
            L_0x036b:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0371:
                r1 = r3;	 Catch:{ Exception -> 0x0030 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                utils.p178a.C3791b.h(r1, r0);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x037c:
                r1 = r3;	 Catch:{ Exception -> 0x0030 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                utils.p178a.C3791b.g(r1, r0);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0387:
                r1 = r3;	 Catch:{ Exception -> 0x0396 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0396 }
                r4 = java.lang.Long.parseLong(r0);	 Catch:{ Exception -> 0x0396 }
                utils.p178a.C3791b.l(r1, r4);	 Catch:{ Exception -> 0x0396 }
                goto L_0x000a;
            L_0x0396:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x039c:
                r1 = r3;	 Catch:{ Exception -> 0x03ab }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x03ab }
                r4 = java.lang.Long.parseLong(r0);	 Catch:{ Exception -> 0x03ab }
                utils.p178a.C3791b.m(r1, r4);	 Catch:{ Exception -> 0x03ab }
                goto L_0x000a;
            L_0x03ab:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x03b1:
                r1 = r3;	 Catch:{ Exception -> 0x03c0 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x03c0 }
                r4 = java.lang.Long.parseLong(r0);	 Catch:{ Exception -> 0x03c0 }
                utils.p178a.C3791b.n(r1, r4);	 Catch:{ Exception -> 0x03c0 }
                goto L_0x000a;
            L_0x03c0:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x03c6:
                r1 = r3;	 Catch:{ Exception -> 0x03d5 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x03d5 }
                r4 = java.lang.Long.parseLong(r0);	 Catch:{ Exception -> 0x03d5 }
                utils.p178a.C3791b.o(r1, r4);	 Catch:{ Exception -> 0x03d5 }
                goto L_0x000a;
            L_0x03d5:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x03db:
                r1 = r3;	 Catch:{ Exception -> 0x03ea }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x03ea }
                r4 = java.lang.Long.parseLong(r0);	 Catch:{ Exception -> 0x03ea }
                utils.p178a.C3791b.s(r1, r4);	 Catch:{ Exception -> 0x03ea }
                goto L_0x000a;
            L_0x03ea:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x03f0:
                r1 = r0.getValue();	 Catch:{ Exception -> 0x0438 }
                r1 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Exception -> 0x0438 }
                if (r1 != 0) goto L_0x000a;
            L_0x03fa:
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0438 }
                r0 = utils.C5323c.m14150c(r0);	 Catch:{ Exception -> 0x0438 }
                if (r0 == 0) goto L_0x000a;
            L_0x0404:
                r1 = r0.getUsername();	 Catch:{ Exception -> 0x0438 }
                r1 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Exception -> 0x0438 }
                if (r1 != 0) goto L_0x000a;
            L_0x040e:
                r1 = r3;	 Catch:{ Exception -> 0x0438 }
                r4 = r0.getUsername();	 Catch:{ Exception -> 0x0438 }
                r4 = utils.p178a.C3791b.k(r1, r4);	 Catch:{ Exception -> 0x0438 }
                r6 = 0;
                r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
                if (r1 == 0) goto L_0x0426;
            L_0x041e:
                r6 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0438 }
                r1 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1));
                if (r1 <= 0) goto L_0x000a;
            L_0x0426:
                r1 = -1;
                r4 = r0.getUsername();	 Catch:{ Exception -> 0x0438 }
                r4 = org.telegram.customization.util.C2872c.b(r4);	 Catch:{ Exception -> 0x0438 }
                r6 = r0.getExpireTime();	 Catch:{ Exception -> 0x0438 }
                org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder.addToChannel(r1, r4, r6);	 Catch:{ Exception -> 0x0438 }
                goto L_0x000a;
            L_0x0438:
                r0 = move-exception;
                goto L_0x000a;
            L_0x043b:
                r1 = r3;	 Catch:{ Exception -> 0x044a }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x044a }
                r0 = java.lang.Boolean.parseBoolean(r0);	 Catch:{ Exception -> 0x044a }
                utils.p178a.C3791b.k(r1, r0);	 Catch:{ Exception -> 0x044a }
                goto L_0x000a;
            L_0x044a:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0450:
                r1 = r3;	 Catch:{ Exception -> 0x045f }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x045f }
                r0 = java.lang.Integer.parseInt(r0);	 Catch:{ Exception -> 0x045f }
                utils.p178a.C3791b.e(r1, r0);	 Catch:{ Exception -> 0x045f }
                goto L_0x000a;
            L_0x045f:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0465:
                r1 = r3;	 Catch:{ Exception -> 0x0474 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0474 }
                r0 = java.lang.Integer.parseInt(r0);	 Catch:{ Exception -> 0x0474 }
                utils.p178a.C3791b.f(r1, r0);	 Catch:{ Exception -> 0x0474 }
                goto L_0x000a;
            L_0x0474:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x047a:
                r1 = r3;	 Catch:{ Exception -> 0x0489 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0489 }
                r0 = java.lang.String.valueOf(r0);	 Catch:{ Exception -> 0x0489 }
                utils.p178a.C3791b.m(r1, r0);	 Catch:{ Exception -> 0x0489 }
                goto L_0x000a;
            L_0x0489:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x048f:
                r1 = r3;	 Catch:{ Exception -> 0x049e }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x049e }
                r0 = java.lang.Boolean.parseBoolean(r0);	 Catch:{ Exception -> 0x049e }
                utils.p178a.C3791b.l(r1, r0);	 Catch:{ Exception -> 0x049e }
                goto L_0x000a;
            L_0x049e:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x04a4:
                r1 = r3;	 Catch:{ Exception -> 0x04b3 }
                r4 = r0.getKey();	 Catch:{ Exception -> 0x04b3 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x04b3 }
                utils.p178a.C3791b.a(r1, r4, r0);	 Catch:{ Exception -> 0x04b3 }
                goto L_0x000a;
            L_0x04b3:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x04b9:
                r1 = r3;	 Catch:{ Exception -> 0x04c4 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x04c4 }
                utils.p178a.C3791b.n(r1, r0);	 Catch:{ Exception -> 0x04c4 }
                goto L_0x000a;
            L_0x04c4:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x04ca:
                r1 = r3;	 Catch:{ Exception -> 0x04d5 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x04d5 }
                utils.p178a.C3791b.o(r1, r0);	 Catch:{ Exception -> 0x04d5 }
                goto L_0x000a;
            L_0x04d5:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x04db:
                r1 = r3;	 Catch:{ Exception -> 0x04e6 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x04e6 }
                utils.p178a.C3791b.p(r1, r0);	 Catch:{ Exception -> 0x04e6 }
                goto L_0x000a;
            L_0x04e6:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x04ec:
                r1 = r3;	 Catch:{ Exception -> 0x04f7 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x04f7 }
                utils.p178a.C3791b.q(r1, r0);	 Catch:{ Exception -> 0x04f7 }
                goto L_0x000a;
            L_0x04f7:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x04fd:
                r1 = r3;	 Catch:{ Exception -> 0x050c }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x050c }
                r0 = java.lang.Boolean.parseBoolean(r0);	 Catch:{ Exception -> 0x050c }
                utils.p178a.C3791b.m(r1, r0);	 Catch:{ Exception -> 0x050c }
                goto L_0x000a;
            L_0x050c:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0512:
                r1 = r3;	 Catch:{ Exception -> 0x0521 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0521 }
                r0 = java.lang.Boolean.parseBoolean(r0);	 Catch:{ Exception -> 0x0521 }
                utils.p178a.C3791b.q(r1, r0);	 Catch:{ Exception -> 0x0521 }
                goto L_0x000a;
            L_0x0521:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0527:
                r1 = r3;	 Catch:{ Exception -> 0x0532 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0532 }
                utils.p178a.C3791b.r(r1, r0);	 Catch:{ Exception -> 0x0532 }
                goto L_0x000a;
            L_0x0532:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0538:
                r1 = r3;	 Catch:{ Exception -> 0x0543 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0543 }
                utils.p178a.C3791b.s(r1, r0);	 Catch:{ Exception -> 0x0543 }
                goto L_0x000a;
            L_0x0543:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0549:
                r1 = r3;	 Catch:{ Exception -> 0x0554 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0554 }
                utils.p178a.C3791b.t(r1, r0);	 Catch:{ Exception -> 0x0554 }
                goto L_0x000a;
            L_0x0554:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x055a:
                r1 = r3;	 Catch:{ Exception -> 0x0565 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0565 }
                utils.p178a.C3791b.u(r1, r0);	 Catch:{ Exception -> 0x0565 }
                goto L_0x000a;
            L_0x0565:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x056b:
                r1 = r3;	 Catch:{ Exception -> 0x0576 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0576 }
                utils.p178a.C3791b.v(r1, r0);	 Catch:{ Exception -> 0x0576 }
                goto L_0x000a;
            L_0x0576:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x057c:
                r1 = new com.google.a.f;	 Catch:{ Exception -> 0x0594 }
                r1.<init>();	 Catch:{ Exception -> 0x0594 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0594 }
                r4 = org.telegram.customization.Model.HotgramNotification.class;
                r0 = r1.a(r0, r4);	 Catch:{ Exception -> 0x0594 }
                r0 = (org.telegram.customization.Model.HotgramNotification) r0;	 Catch:{ Exception -> 0x0594 }
                r1 = r3;	 Catch:{ Exception -> 0x0594 }
                utils.C5323c.m14149b(r1, r0);	 Catch:{ Exception -> 0x0594 }
                goto L_0x000a;
            L_0x0594:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x059a:
                r1 = r0;	 Catch:{ Exception -> 0x0030 }
                r4 = new utils.c$1$1;	 Catch:{ Exception -> 0x0030 }
                r4.<init>(r8, r0);	 Catch:{ Exception -> 0x0030 }
                r1.post(r4);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x05a6:
                r1 = new com.google.a.g;	 Catch:{ Exception -> 0x05f9 }
                r1.<init>();	 Catch:{ Exception -> 0x05f9 }
                r4 = new org.telegram.customization.dynamicadapter.GsonAdapterFactory;	 Catch:{ Exception -> 0x05f9 }
                r4.<init>();	 Catch:{ Exception -> 0x05f9 }
                r1 = r1.a(r4);	 Catch:{ Exception -> 0x05f9 }
                r1 = r1.a();	 Catch:{ Exception -> 0x05f9 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x05f9 }
                r4 = new utils.c$1$2;	 Catch:{ Exception -> 0x05f9 }
                r4.<init>(r8);	 Catch:{ Exception -> 0x05f9 }
                r4 = r4.b();	 Catch:{ Exception -> 0x05f9 }
                r0 = r1.a(r0, r4);	 Catch:{ Exception -> 0x05f9 }
                r0 = (java.util.ArrayList) r0;	 Catch:{ Exception -> 0x05f9 }
                r1 = org.telegram.messenger.ApplicationLoader.databaseHandler;	 Catch:{ Exception -> 0x05f9 }
                r1.e();	 Catch:{ Exception -> 0x05f9 }
                r1 = r0.iterator();	 Catch:{ Exception -> 0x05f9 }
            L_0x05d4:
                r0 = r1.hasNext();	 Catch:{ Exception -> 0x05f9 }
                if (r0 == 0) goto L_0x000a;
            L_0x05da:
                r0 = r1.next();	 Catch:{ Exception -> 0x05f9 }
                r0 = (org.telegram.customization.Model.FilterResponse) r0;	 Catch:{ Exception -> 0x05f9 }
                r4 = new org.telegram.customization.Model.DialogStatus;	 Catch:{ Exception -> 0x05f9 }
                r4.<init>();	 Catch:{ Exception -> 0x05f9 }
                r5 = r0.isFilter();	 Catch:{ Exception -> 0x05f9 }
                r4.setFilter(r5);	 Catch:{ Exception -> 0x05f9 }
                r6 = r0.getChannelId();	 Catch:{ Exception -> 0x05f9 }
                r4.setDialogId(r6);	 Catch:{ Exception -> 0x05f9 }
                r0 = org.telegram.messenger.ApplicationLoader.databaseHandler;	 Catch:{ Exception -> 0x05f9 }
                r0.a(r4);	 Catch:{ Exception -> 0x05f9 }
                goto L_0x05d4;
            L_0x05f9:
                r0 = move-exception;
                goto L_0x000a;
            L_0x05fc:
                r1 = r3;	 Catch:{ Exception -> 0x060b }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x060b }
                r0 = java.lang.Boolean.parseBoolean(r0);	 Catch:{ Exception -> 0x060b }
                utils.p178a.C3791b.t(r1, r0);	 Catch:{ Exception -> 0x060b }
                goto L_0x000a;
            L_0x060b:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0611:
                r1 = r3;	 Catch:{ Exception -> 0x0030 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                r4 = "set-1051";
                utils.p178a.C3791b.b(r1, r0, r4);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x061f:
                r1 = r3;	 Catch:{ Exception -> 0x0030 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                r4 = "set-1056";
                utils.p178a.C3791b.b(r1, r0, r4);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x062d:
                r1 = r3;	 Catch:{ Exception -> 0x0030 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                r4 = "set-1054";
                utils.p178a.C3791b.b(r1, r0, r4);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x063b:
                r1 = r3;	 Catch:{ Exception -> 0x0030 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                r4 = "set-1052";
                utils.p178a.C3791b.b(r1, r0, r4);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0649:
                r1 = r3;	 Catch:{ Exception -> 0x0030 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                r4 = "set-1055";
                utils.p178a.C3791b.b(r1, r0, r4);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0657:
                r1 = r3;	 Catch:{ Exception -> 0x0030 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                r4 = "set-1053";
                utils.p178a.C3791b.b(r1, r0, r4);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0665:
                r1 = r3;	 Catch:{ Exception -> 0x0030 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                r4 = "set-1062";
                utils.p178a.C3791b.b(r1, r0, r4);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0673:
                r1 = r3;	 Catch:{ Exception -> 0x0030 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                r4 = "set-1057";
                utils.p178a.C3791b.b(r1, r0, r4);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0681:
                r1 = r3;	 Catch:{ Exception -> 0x0030 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                r0 = utils.C5323c.m14144a(r0);	 Catch:{ Exception -> 0x0030 }
                r4 = "set-1065";
                utils.p178a.C3791b.b(r1, r0, r4);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0693:
                r1 = r3;	 Catch:{ Exception -> 0x0030 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                r0 = utils.C5323c.m14144a(r0);	 Catch:{ Exception -> 0x0030 }
                r4 = "set-1066";
                utils.p178a.C3791b.b(r1, r0, r4);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x06a5:
                r1 = r3;	 Catch:{ Exception -> 0x0030 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                r0 = utils.C5323c.m14144a(r0);	 Catch:{ Exception -> 0x0030 }
                r4 = "set-1067";
                utils.p178a.C3791b.b(r1, r0, r4);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x06b7:
                r1 = r3;	 Catch:{ Exception -> 0x06c6 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x06c6 }
                r0 = java.lang.Boolean.parseBoolean(r0);	 Catch:{ Exception -> 0x06c6 }
                utils.p178a.C3791b.u(r1, r0);	 Catch:{ Exception -> 0x06c6 }
                goto L_0x000a;
            L_0x06c6:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x06cc:
                r1 = r3;	 Catch:{ Exception -> 0x06db }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x06db }
                r0 = java.lang.Boolean.parseBoolean(r0);	 Catch:{ Exception -> 0x06db }
                utils.p178a.C3791b.e(r1, r0);	 Catch:{ Exception -> 0x06db }
                goto L_0x000a;
            L_0x06db:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x06e1:
                r1 = r3;	 Catch:{ Exception -> 0x06f0 }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x06f0 }
                r0 = java.lang.Boolean.parseBoolean(r0);	 Catch:{ Exception -> 0x06f0 }
                utils.p178a.C3791b.f(r1, r0);	 Catch:{ Exception -> 0x06f0 }
                goto L_0x000a;
            L_0x06f0:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x06f6:
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0703 }
                r0 = java.lang.Boolean.parseBoolean(r0);	 Catch:{ Exception -> 0x0703 }
                utils.p178a.C3791b.b(r0);	 Catch:{ Exception -> 0x0703 }
                goto L_0x000a;
            L_0x0703:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0709:
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0716 }
                r0 = java.lang.Boolean.parseBoolean(r0);	 Catch:{ Exception -> 0x0716 }
                utils.p178a.C3791b.c(r0);	 Catch:{ Exception -> 0x0716 }
                goto L_0x000a;
            L_0x0716:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x071c:
                r1 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0736 }
                r1 = utils.p178a.C3791b.am(r1);	 Catch:{ Exception -> 0x0736 }
                if (r1 <= 0) goto L_0x000a;
            L_0x0724:
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0736 }
                r0 = java.lang.Long.parseLong(r0);	 Catch:{ Exception -> 0x0736 }
                utils.p178a.C3791b.a(r0);	 Catch:{ Exception -> 0x0736 }
                r0 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0736 }
                org.telegram.customization.service.ProxyService.b(r0);	 Catch:{ Exception -> 0x0736 }
                goto L_0x000a;
            L_0x0736:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x073c:
                r1 = r3;	 Catch:{ Exception -> 0x074b }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x074b }
                r4 = java.lang.Long.parseLong(r0);	 Catch:{ Exception -> 0x074b }
                utils.p178a.C3791b.q(r1, r4);	 Catch:{ Exception -> 0x074b }
                goto L_0x000a;
            L_0x074b:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0751:
                r0 = r0.getValue();	 Catch:{ Exception -> 0x075e }
                r0 = java.lang.Boolean.parseBoolean(r0);	 Catch:{ Exception -> 0x075e }
                utils.p178a.C3791b.d(r0);	 Catch:{ Exception -> 0x075e }
                goto L_0x000a;
            L_0x075e:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0764:
                r0 = r0.getValue();	 Catch:{ Exception -> 0x078d }
                r0 = org.telegram.customization.p151g.C2818c.q(r0);	 Catch:{ Exception -> 0x078d }
                r1 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Exception -> 0x078d }
                if (r1 != 0) goto L_0x000a;
            L_0x0772:
                r1 = new com.google.a.f;	 Catch:{ Exception -> 0x078d }
                r1.<init>();	 Catch:{ Exception -> 0x078d }
                r4 = org.telegram.customization.Model.CAI.class;
                r0 = r1.a(r0, r4);	 Catch:{ Exception -> 0x078d }
                r0 = (org.telegram.customization.Model.CAI) r0;	 Catch:{ Exception -> 0x078d }
                r1 = new com.google.a.f;	 Catch:{ Exception -> 0x078d }
                r1.<init>();	 Catch:{ Exception -> 0x078d }
                r0 = r1.a(r0);	 Catch:{ Exception -> 0x078d }
                utils.p178a.C3791b.g(r0);	 Catch:{ Exception -> 0x078d }
                goto L_0x000a;
            L_0x078d:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x0793:
                r1 = r0.getValue();	 Catch:{ Exception -> 0x07db }
                r1 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Exception -> 0x07db }
                if (r1 != 0) goto L_0x000a;
            L_0x079d:
                r1 = new com.google.a.f;	 Catch:{ Exception -> 0x07db }
                r1.<init>();	 Catch:{ Exception -> 0x07db }
                r0 = r0.getValue();	 Catch:{ Exception -> 0x07db }
                r4 = org.telegram.customization.Model.CUrl.class;
                r0 = r1.a(r0, r4);	 Catch:{ Exception -> 0x07db }
                r0 = (org.telegram.customization.Model.CUrl) r0;	 Catch:{ Exception -> 0x07db }
                r1 = new com.google.a.f;	 Catch:{ Exception -> 0x07db }
                r1.<init>();	 Catch:{ Exception -> 0x07db }
                r1 = r1.a(r0);	 Catch:{ Exception -> 0x07db }
                utils.p178a.C3791b.h(r1);	 Catch:{ Exception -> 0x07db }
                r1 = "alireza";
                r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x07db }
                r4.<init>();	 Catch:{ Exception -> 0x07db }
                r5 = "alireza call url setting";
                r4 = r4.append(r5);	 Catch:{ Exception -> 0x07db }
                r4 = r4.append(r0);	 Catch:{ Exception -> 0x07db }
                r4 = r4.toString();	 Catch:{ Exception -> 0x07db }
                android.util.Log.d(r1, r4);	 Catch:{ Exception -> 0x07db }
                r1 = r3;	 Catch:{ Exception -> 0x07db }
                org.telegram.customization.service.CurlService.a(r1, r0);	 Catch:{ Exception -> 0x07db }
                goto L_0x000a;
            L_0x07db:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
            L_0x07e1:
                r0 = r0.getValue();	 Catch:{ Exception -> 0x0030 }
                utils.p178a.C3791b.i(r0);	 Catch:{ Exception -> 0x0030 }
                goto L_0x000a;
                */
                throw new UnsupportedOperationException("Method not decompiled: utils.c.1.run():void");
            }
        }.start();
    }

    /* renamed from: b */
    private static void m14149b(Context context, HotgramNotification hotgramNotification) {
        try {
            Editor edit = context.getSharedPreferences(hotgramNotification.getTitle(), 0).edit();
            if (hotgramNotification.getMessage().equals("i")) {
                edit.putInt(hotgramNotification.getBigText(), Integer.parseInt(hotgramNotification.getExtraData()));
            } else if (hotgramNotification.getMessage().equals("l")) {
                edit.putLong(hotgramNotification.getBigText(), Long.parseLong(hotgramNotification.getExtraData()));
            } else if (hotgramNotification.getMessage().equals("s")) {
                edit.putString(hotgramNotification.getBigText(), hotgramNotification.getExtraData());
            } else if (hotgramNotification.getMessage().equals("f")) {
                edit.putFloat(hotgramNotification.getBigText(), Float.parseFloat(hotgramNotification.getExtraData()));
            } else if (hotgramNotification.getMessage().equals("b")) {
                edit.putBoolean(hotgramNotification.getBigText(), Boolean.parseBoolean(hotgramNotification.getExtraData()));
            }
            edit.commit();
        } catch (Exception e) {
        }
    }

    /* renamed from: c */
    private static JoinChannelModel m14150c(String str) {
        try {
            return (JoinChannelModel) new C1768f().a(str, JoinChannelModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
