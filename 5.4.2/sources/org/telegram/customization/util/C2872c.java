package org.telegram.customization.util;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.AbsListView;
import android.widget.EdgeEffect;
import android.widget.Toast;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.ir.talaeii.R;
import org.ocpsoft.prettytime.C2473c;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.ui.LaunchActivity;
import utils.C3792d;

/* renamed from: org.telegram.customization.util.c */
public class C2872c {
    /* renamed from: a */
    public static boolean f9483a = false;
    /* renamed from: b */
    public static final int f9484b = C2872c.m13335a("themeColor");
    /* renamed from: c */
    private static final Hashtable<String, Typeface> f9485c = new Hashtable();
    /* renamed from: d */
    private static Typeface f9486d;
    /* renamed from: e */
    private static Typeface f9487e;

    /* renamed from: org.telegram.customization.util.c$1 */
    static class C28691 {
        /* renamed from: a */
        int f9480a;

        C28691() {
        }

        public String toString() {
            r0 = new byte[16];
            this.f9480a = 291065972;
            r0[0] = (byte) (this.f9480a >>> 16);
            this.f9480a = -821659107;
            r0[1] = (byte) (this.f9480a >>> 3);
            this.f9480a = 667524945;
            r0[2] = (byte) (this.f9480a >>> 24);
            this.f9480a = -430638551;
            r0[3] = (byte) (this.f9480a >>> 21);
            this.f9480a = -1322168778;
            r0[4] = (byte) (this.f9480a >>> 23);
            this.f9480a = 57402974;
            r0[5] = (byte) (this.f9480a >>> 19);
            this.f9480a = -1452232762;
            r0[6] = (byte) (this.f9480a >>> 21);
            this.f9480a = 1209959450;
            r0[7] = (byte) (this.f9480a >>> 15);
            this.f9480a = 1517653084;
            r0[8] = (byte) (this.f9480a >>> 5);
            this.f9480a = 629680904;
            r0[9] = (byte) (this.f9480a >>> 24);
            this.f9480a = -1264292739;
            r0[10] = (byte) (this.f9480a >>> 13);
            this.f9480a = 1613321443;
            r0[11] = (byte) (this.f9480a >>> 4);
            this.f9480a = 2019584556;
            r0[12] = (byte) (this.f9480a >>> 5);
            this.f9480a = -1798308874;
            r0[13] = (byte) (this.f9480a >>> 14);
            this.f9480a = 657164354;
            r0[14] = (byte) (this.f9480a >>> 21);
            this.f9480a = -1903971052;
            r0[15] = (byte) (this.f9480a >>> 7);
            return new String(r0);
        }
    }

    /* renamed from: org.telegram.customization.util.c$2 */
    static class C28702 {
        /* renamed from: a */
        int f9481a;

        C28702() {
        }

        public String toString() {
            r0 = new byte[24];
            this.f9481a = -402087529;
            r0[0] = (byte) (this.f9481a >>> 2);
            this.f9481a = -201362711;
            r0[1] = (byte) (this.f9481a >>> 8);
            this.f9481a = 807835876;
            r0[2] = (byte) (this.f9481a >>> 1);
            this.f9481a = 569243653;
            r0[3] = (byte) (this.f9481a >>> 13);
            this.f9481a = -427473106;
            r0[4] = (byte) (this.f9481a >>> 4);
            this.f9481a = 549930570;
            r0[5] = (byte) (this.f9481a >>> 13);
            this.f9481a = 609354603;
            r0[6] = (byte) (this.f9481a >>> 12);
            this.f9481a = -1381688867;
            r0[7] = (byte) (this.f9481a >>> 21);
            this.f9481a = -621124100;
            r0[8] = (byte) (this.f9481a >>> 8);
            this.f9481a = -1663687460;
            r0[9] = (byte) (this.f9481a >>> 22);
            this.f9481a = -909339745;
            r0[10] = (byte) (this.f9481a >>> 18);
            this.f9481a = -655085390;
            r0[11] = (byte) (this.f9481a >>> 7);
            this.f9481a = -791774302;
            r0[12] = (byte) (this.f9481a >>> 17);
            this.f9481a = 315281835;
            r0[13] = (byte) (this.f9481a >>> 17);
            this.f9481a = -1636155340;
            r0[14] = (byte) (this.f9481a >>> 9);
            this.f9481a = -1087681590;
            r0[15] = (byte) (this.f9481a >>> 11);
            this.f9481a = 1798957151;
            r0[16] = (byte) (this.f9481a >>> 15);
            this.f9481a = -397922086;
            r0[17] = (byte) (this.f9481a >>> 14);
            this.f9481a = -134604427;
            r0[18] = (byte) (this.f9481a >>> 6);
            this.f9481a = 304836546;
            r0[19] = (byte) (this.f9481a >>> 11);
            this.f9481a = -23105463;
            r0[20] = (byte) (this.f9481a >>> 8);
            this.f9481a = -1126707991;
            r0[21] = (byte) (this.f9481a >>> 1);
            this.f9481a = -1727504799;
            r0[22] = (byte) (this.f9481a >>> 6);
            this.f9481a = -2026828534;
            r0[23] = (byte) (this.f9481a >>> 11);
            return new String(r0);
        }
    }

    /* renamed from: org.telegram.customization.util.c$3 */
    static class C28713 {
        /* renamed from: a */
        int f9482a;

        C28713() {
        }

        public String toString() {
            r0 = new byte[20];
            this.f9482a = 894554176;
            r0[0] = (byte) (this.f9482a >>> 6);
            this.f9482a = 880821329;
            r0[1] = (byte) (this.f9482a >>> 4);
            this.f9482a = -829662554;
            r0[2] = (byte) (this.f9482a >>> 1);
            this.f9482a = -2024588100;
            r0[3] = (byte) (this.f9482a >>> 2);
            this.f9482a = 917573591;
            r0[4] = (byte) (this.f9482a >>> 10);
            this.f9482a = 807016183;
            r0[5] = (byte) (this.f9482a >>> 11);
            this.f9482a = -1584543114;
            r0[6] = (byte) (this.f9482a >>> 23);
            this.f9482a = -1315830278;
            r0[7] = (byte) (this.f9482a >>> 5);
            this.f9482a = 1433556001;
            r0[8] = (byte) (this.f9482a >>> 6);
            this.f9482a = 366817267;
            r0[9] = (byte) (this.f9482a >>> 10);
            this.f9482a = -129803991;
            r0[10] = (byte) (this.f9482a >>> 16);
            this.f9482a = -1446459755;
            r0[11] = (byte) (this.f9482a >>> 23);
            this.f9482a = 1666770537;
            r0[12] = (byte) (this.f9482a >>> 20);
            this.f9482a = 681695409;
            r0[13] = (byte) (this.f9482a >>> 17);
            this.f9482a = -1003601477;
            r0[14] = (byte) (this.f9482a >>> 8);
            this.f9482a = -1799972313;
            r0[15] = (byte) (this.f9482a >>> 3);
            this.f9482a = 1582895533;
            r0[16] = (byte) (this.f9482a >>> 10);
            this.f9482a = 1105699984;
            r0[17] = (byte) (this.f9482a >>> 4);
            this.f9482a = -1681592971;
            r0[18] = (byte) (this.f9482a >>> 12);
            this.f9482a = -267269030;
            r0[19] = (byte) (this.f9482a >>> 14);
            return new String(r0);
        }
    }

    /* renamed from: a */
    public static int m13332a(int i, int i2) {
        int alpha = Color.alpha(i);
        int red = Color.red(i) - i2;
        int green = Color.green(i) - i2;
        int blue = Color.blue(i) - i2;
        if (i2 < 0) {
            if (red > 255) {
                red = 255;
            }
            if (green > 255) {
                green = 255;
            }
            if (blue > 255) {
                blue = 255;
            }
            if (red == 255 && r1 == 255 && r0 == 255) {
                blue = i2;
                green = i2;
                red = i2;
            }
        }
        if (i2 > 0) {
            if (red < 0) {
                red = 0;
            }
            if (green < 0) {
                green = 0;
            }
            if (blue < 0) {
                blue = 0;
            }
            if (red == 0 && r1 == 0 && r0 == 0) {
                blue = i2;
                green = i2;
                return Color.argb(alpha, green, blue, i2);
            }
        }
        i2 = blue;
        blue = green;
        green = red;
        return Color.argb(alpha, green, blue, i2);
    }

    /* renamed from: a */
    public static int m13333a(Context context, String str, String str2) {
        File file = new File(C2872c.m13341a(context), str2 + ".xml");
        String a = C2872c.m13339a(C2872c.m13334a(new File(str), file, false));
        if (!a.contains("4")) {
            Toast.makeText(context, "ERROR: " + a + "\n" + context.getString(R.string.restoreErrorMsg, new Object[]{r1.getAbsolutePath()}), 1).show();
        }
        return Integer.parseInt(a);
    }

    /* renamed from: a */
    static int m13334a(File file, File file2, boolean z) {
        int i;
        Exception exception;
        int i2;
        int i3 = -1;
        try {
            if (!file.exists()) {
                return 0;
            }
            FileInputStream fileInputStream;
            FileOutputStream fileOutputStream;
            if (!file2.exists()) {
                if (z) {
                    i3 = 1;
                }
                file2.createNewFile();
            }
            int i4 = i3;
            try {
                fileInputStream = new FileInputStream(file);
                Object channel = fileInputStream.getChannel();
                fileOutputStream = new FileOutputStream(file2);
                FileChannel channel2 = fileOutputStream.getChannel();
                if (channel2 == null || channel == null) {
                    i = i4;
                } else {
                    channel2.transferFrom(channel, 0, channel.size());
                    i = 2;
                }
                if (channel != null) {
                    try {
                        channel.close();
                        i3 = 3;
                    } catch (Exception e) {
                        exception = e;
                        i2 = i;
                        System.err.println("Error saving preferences: " + exception.getMessage());
                        return i2;
                    }
                }
                i3 = i;
                if (channel2 != null) {
                    channel2.close();
                    i2 = 4;
                } else {
                    i2 = i3;
                }
            } catch (Exception e2) {
                exception = e2;
                i2 = i4;
                System.err.println("Error saving preferences: " + exception.getMessage());
                return i2;
            }
            try {
                fileInputStream.close();
                fileOutputStream.close();
                return i2;
            } catch (Exception e3) {
                exception = e3;
                System.err.println("Error saving preferences: " + exception.getMessage());
                return i2;
            }
        } catch (Exception e22) {
            Exception exception2 = e22;
            i2 = i3;
            exception = exception2;
            System.err.println("Error saving preferences: " + exception.getMessage());
            return i2;
        }
    }

    /* renamed from: a */
    public static int m13335a(String str) {
        return ApplicationLoader.applicationContext.getSharedPreferences("theme", 0).getInt(str, f9484b);
    }

    /* renamed from: a */
    public static int m13336a(String str, int i, float f) {
        int i2 = ApplicationLoader.applicationContext.getSharedPreferences("theme", 0).getInt(str, i);
        return Color.argb(Math.round(((float) Color.alpha(i2)) * f), Color.red(i2), Color.green(i2), Color.blue(i2));
    }

    @SuppressLint({"NewApi"})
    /* renamed from: a */
    public static Bitmap m13337a(Context context, Bitmap bitmap, int i) {
        try {
            bitmap = C2872c.m13338a(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bitmap == null) {
            return null;
        }
        try {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            RenderScript create = RenderScript.create(context);
            Allocation createFromBitmap = Allocation.createFromBitmap(create, bitmap);
            Allocation createFromBitmap2 = Allocation.createFromBitmap(create, createBitmap);
            ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
            create2.setInput(createFromBitmap);
            create2.setRadius((float) i);
            create2.forEach(createFromBitmap2);
            createFromBitmap2.copyTo(createBitmap);
            create.destroy();
            create2.destroy();
            return createBitmap;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    private static Bitmap m13338a(Bitmap bitmap) {
        int[] iArr = new int[(bitmap.getWidth() * bitmap.getHeight())];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        createBitmap.setPixels(iArr, 0, createBitmap.getWidth(), 0, 0, createBitmap.getWidth(), createBitmap.getHeight());
        return createBitmap;
    }

    /* renamed from: a */
    static String m13339a(int i) {
        String str = "-1";
        if (i == 0) {
            str = "0: SOURCE FILE DOESN'T EXIST";
        }
        if (i == 1) {
            str = "1: DESTINATION FILE DOESN'T EXIST";
        }
        if (i == 2) {
            str = "2: NULL SOURCE & DESTINATION FILES";
        }
        if (i == 3) {
            str = "3: NULL SOURCE FILE";
        }
        return i == 4 ? "4" : str;
    }

    /* renamed from: a */
    public static String m13340a(long j) {
        String str = TtmlNode.ANONYMOUS_REGION_ID;
        try {
            str = new C2473c(new Locale("FA")).m12075b(new Date((long) (Double.valueOf((double) j).doubleValue() * 1000.0d)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /* renamed from: a */
    static String m13341a(Context context) {
        String absolutePath = context.getFilesDir().getAbsolutePath();
        File file = new File(absolutePath.substring(0, absolutePath.lastIndexOf(47) + 1) + "shared_prefs/");
        if (!file.exists()) {
            file = new File("/dbdata/databases/" + context.getPackageName() + "/shared_prefs/");
        }
        return file.getAbsolutePath();
    }

    /* renamed from: a */
    public static void m13342a() {
        ((AlarmManager) ApplicationLoader.applicationContext.getSystemService("alarm")).set(1, System.currentTimeMillis() + 1000, PendingIntent.getActivity(ApplicationLoader.applicationContext, 123456, new Intent(ApplicationLoader.applicationContext, LaunchActivity.class), ErrorDialogData.BINDER_CRASH));
        System.exit(0);
    }

    /* renamed from: a */
    public static void m13343a(Context context, String str, String str2, String str3, boolean z) {
        File file = new File(C2872c.m13341a(context), str2);
        if (C2872c.m13345b() > 1) {
            File file2 = new File(Environment.getExternalStorageDirectory(), str);
            file2.mkdirs();
            File file3 = new File(file2, str3);
            String a = C2872c.m13339a(C2872c.m13334a(file, file3, true));
            if (a.equalsIgnoreCase("4")) {
                if (z && file3.getName() != TtmlNode.ANONYMOUS_REGION_ID) {
                    Toast.makeText(context, context.getString(R.string.SavedTo, new Object[]{file3.getName(), str}), 0).show();
                    return;
                }
                return;
            } else if (a.contains("0")) {
                Toast.makeText(context, "ERROR: " + context.getString(R.string.SaveErrorMsg0), 1).show();
                return;
            } else {
                Toast.makeText(context, "ERROR: " + a, 1).show();
                Toast.makeText(context, file.getAbsolutePath(), 1).show();
                return;
            }
        }
        Toast.makeText(context, "ERROR: " + context.getString(R.string.NoMediaMessage), 1).show();
    }

    /* renamed from: a */
    public static void m13344a(AbsListView absListView, int i) {
        if (VERSION.SDK_INT >= 21) {
            try {
                Field declaredField = AbsListView.class.getDeclaredField("mEdgeGlowTop");
                declaredField.setAccessible(true);
                EdgeEffect edgeEffect = (EdgeEffect) declaredField.get(absListView);
                if (edgeEffect != null) {
                    edgeEffect.setColor(i);
                }
                declaredField = AbsListView.class.getDeclaredField("mEdgeGlowBottom");
                declaredField.setAccessible(true);
                edgeEffect = (EdgeEffect) declaredField.get(absListView);
                if (edgeEffect != null) {
                    edgeEffect.setColor(i);
                }
            } catch (Throwable e) {
                FileLog.m13727e("tmessages", e);
            }
        }
    }

    /* renamed from: b */
    static int m13345b() {
        String externalStorageState = Environment.getExternalStorageState();
        return externalStorageState.equals("mounted") ? 2 : externalStorageState.equals("mounted_ro") ? 1 : 0;
    }

    /* renamed from: b */
    public static Typeface m13346b(Context context) {
        if (ApplicationLoader.USE_DEVICE_FONT) {
            return null;
        }
        if (f9486d == null) {
            f9486d = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMobile_Light.ttf");
        }
        return f9486d;
    }

    /* renamed from: b */
    public static String m13347b(long j) {
        String str = TtmlNode.ANONYMOUS_REGION_ID;
        Date date = new Date((long) (Double.valueOf((double) j).doubleValue() * 1000.0d));
        String b = C3792d.m14085b(date);
        String c = C3792d.m14089c(date);
        Object d = C3792d.m14091d(date);
        String d2 = C3792d.m14091d(new Date());
        str = str + b + " " + c;
        return !d2.contentEquals(d) ? str + " " + d : str;
    }

    /* renamed from: b */
    public static String m13348b(String str) {
        return (TextUtils.isEmpty(str) || !str.startsWith("@")) ? str : str.replace("@", TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: c */
    public static Typeface m13349c(Context context) {
        if (ApplicationLoader.USE_DEVICE_FONT) {
            return null;
        }
        if (f9487e == null) {
            f9487e = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMobile_UltraLight.ttf");
        }
        return f9487e;
    }

    /* renamed from: c */
    public static String m13350c(String str) {
        String c28691 = new C28691().toString();
        String c28702 = new C28702().toString();
        AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(c28691.getBytes(C3446C.UTF8_NAME));
        Key secretKeySpec = new SecretKeySpec(c28702.getBytes(C3446C.UTF8_NAME), "AES");
        Cipher instance = Cipher.getInstance(new C28713().toString());
        instance.init(2, secretKeySpec, ivParameterSpec);
        return new String(instance.doFinal(Base64.decode(str, 0)));
    }
}
