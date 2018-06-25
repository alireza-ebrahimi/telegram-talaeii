package utils;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Constants;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import org.ir.talaeii.R;
import org.telegram.customization.util.C2872c;
import org.telegram.messenger.ApplicationLoader;
import utils.view.PixelUtil;

/* renamed from: utils.d */
public class C3792d {
    /* renamed from: a */
    public static int m14073a(float f, Context context) {
        return PixelUtil.a(context, (int) f);
    }

    /* renamed from: a */
    public static int m14074a(int i, float f) {
        return Color.argb(Color.alpha(i), Math.max((int) (((float) Color.red(i)) * f), 0), Math.max((int) (((float) Color.green(i)) * f), 0), Math.max((int) (((float) Color.blue(i)) * f), 0));
    }

    /* renamed from: a */
    public static int m14075a(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /* renamed from: a */
    public static String m14076a() {
        String str = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ApplicationLoader.applicationContext.getString(R.string.ROOT_FOLDER) + File.separator;
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return str;
    }

    /* renamed from: a */
    public static String m14077a(long j) {
        String str = TtmlNode.ANONYMOUS_REGION_ID;
        Date date = new Date((long) (Double.valueOf((double) j).doubleValue() * 1000.0d));
        String b = C3792d.m14085b(date);
        String c = C3792d.m14089c(date);
        Object d = C3792d.m14091d(date);
        String d2 = C3792d.m14091d(new Date());
        str = str + b + " " + c;
        return !d2.contentEquals(d) ? str + " " + d : str;
    }

    /* renamed from: a */
    public static String m14078a(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "#55000000";
        }
        return !str.startsWith("#") ? "#" + str : str;
    }

    /* renamed from: a */
    public static String m14079a(Date date) {
        Locale locale = new Locale("en_US");
        C3792d c3792d = new C3792d();
        c3792d.getClass();
        return String.valueOf(new d$a(c3792d, date).f10244e) + "/" + String.format(locale, "%02d", new Object[]{Integer.valueOf(r2.f10243d)}) + "/" + String.format(locale, "%02d", new Object[]{Integer.valueOf(r2.f10242c)}) + " - " + date.getHours() + ":" + date.getMinutes();
    }

    /* renamed from: a */
    public static void m14080a(Context context, String str, String str2, String str3) {
        Log.d("slspushreceiver msg:", "add contact 1");
        ArrayList arrayList = new ArrayList();
        arrayList.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI).withValue("account_type", null).withValue(Constants.KEY_ACCOUNT_NAME, null).build());
        Log.d("slspushreceiver msg:", "add contact 2");
        arrayList.add(ContentProviderOperation.newInsert(Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/name").withValue("data2", str).withValue("data3", str2).build());
        Log.d("slspushreceiver msg:", "add contact 3");
        arrayList.add(ContentProviderOperation.newInsert(Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/phone_v2").withValue("data1", str3).withValue("data2", Integer.valueOf(1)).build());
        try {
            context.getContentResolver().applyBatch("com.android.contacts", arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public static void m14081a(Throwable th) {
        RuntimeException runtimeException = null;
        try {
            runtimeException = (RuntimeException) th;
        } catch (Throwable th2) {
        }
        if (runtimeException != null) {
            throw ((RuntimeException) th);
        }
    }

    /* renamed from: b */
    public static float m14082b(float f, Context context) {
        return (float) PixelUtil.b(context, (int) f);
    }

    /* renamed from: b */
    public static final int m14083b() {
        return ApplicationLoader.applicationContext.getSharedPreferences("theme", 0).getInt("themeColor", C2872c.f9484b);
    }

    /* renamed from: b */
    public static int m14084b(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /* renamed from: b */
    public static String m14085b(Date date) {
        Locale locale = new Locale("en_US");
        C3792d c3792d = new C3792d();
        c3792d.getClass();
        d$a d_a = new d$a(c3792d, date);
        return String.format(locale, "%02d", new Object[]{Integer.valueOf(d_a.f10242c)});
    }

    /* renamed from: b */
    public static void m14086b(String str) {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: b */
    public static boolean m14087b(android.content.Context r6, java.lang.String r7, java.lang.String r8, java.lang.String r9) {
        /*
        r2 = 0;
        r0 = android.provider.ContactsContract.PhoneLookup.CONTENT_FILTER_URI;
        r1 = android.net.Uri.encode(r7);
        r1 = android.net.Uri.withAppendedPath(r0, r1);
        r0 = r6.getContentResolver();
        r3 = r2;
        r4 = r2;
        r5 = r2;
        r1 = r0.query(r1, r2, r3, r4, r5);
        r0 = r1.moveToFirst();	 Catch:{ Exception -> 0x0094 }
        if (r0 == 0) goto L_0x008f;
    L_0x001c:
        r0 = "LEE";
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0094 }
        r2.<init>();	 Catch:{ Exception -> 0x0094 }
        r3 = "Contact:";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0094 }
        r3 = "display_name";
        r3 = r1.getColumnIndex(r3);	 Catch:{ Exception -> 0x0094 }
        r3 = r1.getString(r3);	 Catch:{ Exception -> 0x0094 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0094 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0094 }
        android.util.Log.d(r0, r2);	 Catch:{ Exception -> 0x0094 }
        r0 = "display_name";
        r0 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x0094 }
        r0 = r1.getString(r0);	 Catch:{ Exception -> 0x0094 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0094 }
        r2.<init>();	 Catch:{ Exception -> 0x0094 }
        r2 = r2.append(r8);	 Catch:{ Exception -> 0x0094 }
        r3 = " ";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0094 }
        r2 = r2.append(r9);	 Catch:{ Exception -> 0x0094 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0094 }
        r0 = r0.equalsIgnoreCase(r2);	 Catch:{ Exception -> 0x0094 }
        if (r0 == 0) goto L_0x0089;
    L_0x006a:
        r0 = "lookup";
        r0 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x0094 }
        r0 = r1.getString(r0);	 Catch:{ Exception -> 0x0094 }
        r2 = android.provider.ContactsContract.Contacts.CONTENT_LOOKUP_URI;	 Catch:{ Exception -> 0x0094 }
        r0 = android.net.Uri.withAppendedPath(r2, r0);	 Catch:{ Exception -> 0x0094 }
        r2 = r6.getContentResolver();	 Catch:{ Exception -> 0x0094 }
        r3 = 0;
        r4 = 0;
        r2.delete(r0, r3, r4);	 Catch:{ Exception -> 0x0094 }
        r0 = 1;
        r1.close();
    L_0x0088:
        return r0;
    L_0x0089:
        r0 = r1.moveToNext();	 Catch:{ Exception -> 0x0094 }
        if (r0 != 0) goto L_0x001c;
    L_0x008f:
        r1.close();
    L_0x0092:
        r0 = 0;
        goto L_0x0088;
    L_0x0094:
        r0 = move-exception;
        r2 = java.lang.System.out;	 Catch:{ all -> 0x00a2 }
        r0 = r0.getStackTrace();	 Catch:{ all -> 0x00a2 }
        r2.println(r0);	 Catch:{ all -> 0x00a2 }
        r1.close();
        goto L_0x0092;
    L_0x00a2:
        r0 = move-exception;
        r1.close();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: utils.d.b(android.content.Context, java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    /* renamed from: c */
    public static int m14088c() {
        return new Random().nextInt(55) + 1;
    }

    /* renamed from: c */
    public static String m14089c(Date date) {
        C3792d c3792d = new C3792d();
        c3792d.getClass();
        return new d$a(c3792d, date).f10241b;
    }

    /* renamed from: d */
    public static String m14090d() {
        return ((TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone")).getNetworkOperatorName();
    }

    /* renamed from: d */
    public static String m14091d(Date date) {
        C3792d c3792d = new C3792d();
        c3792d.getClass();
        return new d$a(c3792d, date).f10244e + TtmlNode.ANONYMOUS_REGION_ID;
    }
}
