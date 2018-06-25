package org.telegram.customization.fetch;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.C0424l;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.customization.fetch.p164b.C2740c;
import org.telegram.customization.fetch.p166d.C2751a;
import org.telegram.customization.fetch.p166d.C2753c;

/* renamed from: org.telegram.customization.fetch.f */
final class C2756f {
    /* renamed from: a */
    static int m12779a(long j, long j2) {
        return (j2 < 1 || j < 1) ? 0 : j >= j2 ? 100 : (int) ((((double) j) / ((double) j2)) * 100.0d);
    }

    /* renamed from: a */
    static long m12780a() {
        return System.nanoTime();
    }

    /* renamed from: a */
    static String m12781a(List<C2751a> list, boolean z) {
        if (list == null) {
            return "{}";
        }
        try {
            JSONObject jSONObject = new JSONObject();
            for (C2751a c2751a : list) {
                jSONObject.put(c2751a.m12744a(), c2751a.m12745b());
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            if (z) {
                e.printStackTrace();
            }
            return "{}";
        }
    }

    /* renamed from: a */
    static List<C2751a> m12782a(String str, boolean z) {
        List<C2751a> arrayList = new ArrayList();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                arrayList.add(new C2751a(str2, jSONObject.getString(str2)));
            }
        } catch (JSONException e) {
            if (z) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    static C2753c m12783a(Cursor cursor, boolean z) {
        if (cursor == null || cursor.isClosed() || cursor.getCount() < 1) {
            return null;
        }
        long j = cursor.getLong(0);
        int i = cursor.getInt(3);
        String string = cursor.getString(1);
        String string2 = cursor.getString(2);
        int i2 = cursor.getInt(7);
        long j2 = cursor.getLong(6);
        int i3 = cursor.getInt(8);
        long j3 = cursor.getLong(5);
        return new C2753c(j, i, string, string2, C2756f.m12779a(j3, j2), j3, j2, i2, C2756f.m12782a(cursor.getString(4), z), i3);
    }

    /* renamed from: a */
    static C2753c m12784a(Cursor cursor, boolean z, boolean z2) {
        C2753c c2753c = null;
        if (cursor != null) {
            try {
                if (!cursor.isClosed() && cursor.getCount() >= 1) {
                    cursor.moveToFirst();
                    c2753c = C2756f.m12783a(cursor, z2);
                    if (cursor != null && z) {
                        cursor.close();
                    }
                    return c2753c;
                }
            } catch (Exception e) {
                if (z2) {
                    e.printStackTrace();
                }
                if (cursor != null && z) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null && z) {
                    cursor.close();
                }
            }
        }
        if (cursor != null && z) {
            cursor.close();
        }
        return c2753c;
    }

    /* renamed from: a */
    static void m12785a(C0424l c0424l, long j, int i, int i2, long j2, long j3, int i3) {
        if (c0424l != null) {
            Intent intent = new Intent("com.tonyodev.fetch.event_action_update");
            intent.putExtra("com.tonyodev.fetch.extra_id", j);
            intent.putExtra("com.tonyodev.fetch.extra_status", i);
            intent.putExtra("com.tonyodev.fetch.extra_progress", i2);
            intent.putExtra("com.tonyodev.fetch.extra_downloaded_bytes", j2);
            intent.putExtra("com.tonyodev.fetch.extra_file_size", j3);
            intent.putExtra("com.tonyodev.fetch.extra_error", i3);
            c0424l.m1904a(intent);
        }
    }

    /* renamed from: a */
    static void m12786a(C2748c c2748c) {
        if (c2748c == null) {
            throw new NullPointerException("Fetch cannot be null");
        } else if (c2748c.m12743b()) {
            throw new C2740c("Fetch instance: " + c2748c.toString() + " cannot be reused after calling its release() method.Call Fetch.getInstance() for a new instance of Fetch.", -115);
        }
    }

    /* renamed from: a */
    static boolean m12787a(long j, long j2, long j3) {
        return TimeUnit.NANOSECONDS.toMillis(j2 - j) >= j3;
    }

    /* renamed from: a */
    static boolean m12788a(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) ? false : activeNetworkInfo.getType() == 1;
    }

    /* renamed from: a */
    static boolean m12789a(String str) {
        File file = new File(str);
        return !file.exists() ? file.createNewFile() : true;
    }

    /* renamed from: b */
    static String m12790b(List<Bundle> list, boolean z) {
        if (list == null) {
            return "{}";
        }
        JSONObject jSONObject = new JSONObject();
        try {
            for (Bundle bundle : list) {
                String string = bundle.getString("com.tonyodev.fetch.extra_header_name");
                Object string2 = bundle.getString("com.tonyodev.fetch.extra_header_value");
                if (string2 == null) {
                    string2 = TtmlNode.ANONYMOUS_REGION_ID;
                }
                if (string != null) {
                    jSONObject.put(string, string2);
                }
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            if (z) {
                e.printStackTrace();
            }
            return "{}";
        }
    }

    /* renamed from: b */
    static ArrayList<Bundle> m12791b(String str, boolean z) {
        ArrayList<Bundle> arrayList = new ArrayList();
        if (str == null) {
            return arrayList;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                Bundle bundle = new Bundle();
                bundle.putString("com.tonyodev.fetch.extra_header_name", str2);
                bundle.putString("com.tonyodev.fetch.extra_header_value", jSONObject.getString(str2));
                arrayList.add(bundle);
            }
        } catch (JSONException e) {
            if (z) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    /* renamed from: b */
    static List<C2753c> m12792b(Cursor cursor, boolean z, boolean z2) {
        List<C2753c> arrayList = new ArrayList();
        if (cursor != null) {
            try {
                if (!cursor.isClosed() && cursor.getCount() >= 1) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        arrayList.add(C2756f.m12783a(cursor, z2));
                        cursor.moveToNext();
                    }
                    if (cursor != null && z) {
                        cursor.close();
                    }
                    return arrayList;
                }
            } catch (Exception e) {
                if (z2) {
                    e.printStackTrace();
                }
                if (cursor != null && z) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null && z) {
                    cursor.close();
                }
            }
        }
        if (cursor != null && z) {
            cursor.close();
        }
        return arrayList;
    }

    /* renamed from: b */
    static boolean m12793b(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /* renamed from: b */
    static boolean m12794b(String str) {
        File file = new File(str);
        return !file.exists() ? file.mkdirs() : true;
    }

    /* renamed from: c */
    static ArrayList<Bundle> m12795c(Cursor cursor, boolean z, boolean z2) {
        ArrayList<Bundle> arrayList = new ArrayList();
        if (cursor != null) {
            try {
                if (!cursor.isClosed()) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        long j = cursor.getLong(0);
                        int i = cursor.getInt(3);
                        String string = cursor.getString(1);
                        String string2 = cursor.getString(2);
                        int i2 = cursor.getInt(7);
                        long j2 = cursor.getLong(6);
                        int i3 = cursor.getInt(8);
                        long j3 = cursor.getLong(5);
                        ArrayList b = C2756f.m12791b(cursor.getString(4), z2);
                        int a = C2756f.m12779a(j3, j2);
                        Bundle bundle = new Bundle();
                        bundle.putLong("com.tonyodev.fetch.extra_id", j);
                        bundle.putInt("com.tonyodev.fetch.extra_status", i);
                        bundle.putString("com.tonyodev.fetch.extra_url", string);
                        bundle.putString("com.tonyodev.fetch.extra_file_path", string2);
                        bundle.putInt("com.tonyodev.fetch.extra_error", i2);
                        bundle.putLong("com.tonyodev.fetch.extra_downloaded_bytes", j3);
                        bundle.putLong("com.tonyodev.fetch.extra_file_size", j2);
                        bundle.putInt("com.tonyodev.fetch.extra_progress", a);
                        bundle.putInt("com.tonyodev.fetch.extra_priority", i3);
                        bundle.putParcelableArrayList("com.tonyodev.fetch.extra_headers", b);
                        arrayList.add(bundle);
                        cursor.moveToNext();
                    }
                    if (cursor != null && z) {
                        cursor.close();
                    }
                    return arrayList;
                }
            } catch (Exception e) {
                if (z2) {
                    e.printStackTrace();
                }
                if (cursor != null && z) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null && z) {
                    cursor.close();
                }
            }
        }
        if (cursor != null && z) {
            cursor.close();
        }
        return arrayList;
    }

    /* renamed from: c */
    static boolean m12796c(String str) {
        return new File(str).delete();
    }

    /* renamed from: d */
    static long m12797d(String str) {
        return new File(str).length();
    }

    /* renamed from: e */
    static boolean m12798e(String str) {
        return new File(str).exists();
    }

    /* renamed from: f */
    static File m12799f(String str) {
        return new File(str);
    }

    /* renamed from: g */
    static void m12800g(String str) {
        File f = C2756f.m12799f(str);
        boolean b = C2756f.m12794b(f.getParentFile().getAbsolutePath());
        boolean a = C2756f.m12789a(f.getAbsolutePath());
        if (!b || !a) {
            throw new IOException("File could not be created for the filePath:" + str);
        }
    }
}
