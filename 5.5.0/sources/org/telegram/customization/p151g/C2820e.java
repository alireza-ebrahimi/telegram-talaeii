package org.telegram.customization.p151g;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import org.telegram.customization.Model.ProxyServerModel;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.ConnectionsManager;
import utils.p178a.C3791b;

/* renamed from: org.telegram.customization.g.e */
public class C2820e {

    /* renamed from: org.telegram.customization.g.e$1 */
    static class C28191 implements C2497d {
        C28191() {
        }

        public void onResult(Object obj, int i) {
            switch (i) {
                case -24:
                    ArrayList ao = C3791b.ao(ApplicationLoader.applicationContext);
                    if (ao.size() > 0) {
                        for (int i2 = 0; i2 < ao.size(); i2++) {
                            ((ProxyServerModel) ao.get(i2)).setUsed(false);
                        }
                        C3791b.m13947b(ApplicationLoader.applicationContext, ao);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: a */
    public static void m13150a(Context context) {
        C2820e.m13151a(context, false);
    }

    /* renamed from: a */
    public static void m13151a(Context context, boolean z) {
        ArrayList ao = C3791b.ao(context);
        Log.d("LEE", "getProxyServer:" + ao.size());
        if (ao.size() == 0 || z) {
            C2818c.m13087a(context, new C28191()).m13128b(true);
        }
        Log.d("LEE", "ConnectionManger:" + z);
        ProxyServerModel proxyServerModel = ao.size() > 0 ? (ProxyServerModel) ao.get(0) : null;
        if (proxyServerModel != null && !z) {
            Log.d("LEE", "ConnectionManger:ffffff");
            C2820e.m13153a(proxyServerModel);
        }
    }

    /* renamed from: a */
    static void m13152a(String str, String str2, String str3, String str4, long j) {
        Log.d("LEE", "setProxyDirectly 1");
        try {
            if (ProxyServerModel.getFromTelegram().equalsWith(new ProxyServerModel(str, str2, str3, str4))) {
                Log.d("AminProxy", "this is same so no set");
                return;
            }
        } catch (Exception e) {
        }
        try {
            Log.d("LEE", "setProxyDirectly 2");
            C3791b.m13933a(ApplicationLoader.applicationContext, str, str2, str3, str4, true, j);
            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
            boolean z = C3791b.am(ApplicationLoader.applicationContext) == 1;
            edit.putBoolean("proxy_enabled", z);
            edit.putBoolean("proxy_enabled_calls", z);
            edit.putString("proxy_ip", str);
            int intValue = Utilities.parseInt(str2).intValue();
            edit.putInt("proxy_port", intValue);
            if (TextUtils.isEmpty(str4)) {
                edit.remove("proxy_pass");
            } else {
                edit.putString("proxy_pass", str4);
            }
            if (TextUtils.isEmpty(str3)) {
                edit.remove("proxy_user");
            } else {
                edit.putString("proxy_user", str3);
            }
            edit.commit();
            if (z) {
                ConnectionsManager.native_setProxySettings(TtmlNode.ANONYMOUS_REGION_ID, 0, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.didUpdatedConnectionState, new Object[0]);
                ConnectionsManager.native_setProxySettings(str, intValue, str3, str4);
                if (!UserConfig.isClientActivated()) {
                    ConnectionsManager.getInstance().checkConnection();
                }
            } else {
                ConnectionsManager.native_setProxySettings(TtmlNode.ANONYMOUS_REGION_ID, 0, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID);
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didUpdatedConnectionState, new Object[0]);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.proxySettingsChanged, new Object[0]);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* renamed from: a */
    public static void m13153a(ProxyServerModel proxyServerModel) {
        try {
            C2820e.m13152a(proxyServerModel.getIp(), TtmlNode.ANONYMOUS_REGION_ID + proxyServerModel.getPort(), proxyServerModel.getUserName(), proxyServerModel.getPassWord(), proxyServerModel.getExpireDateSecs());
        } catch (Exception e) {
        }
    }

    /* renamed from: a */
    public static boolean m13154a() {
        try {
            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
            boolean z = sharedPreferences.getBoolean("proxy_enabled", false);
            Object string = sharedPreferences.getString("proxy_ip", TtmlNode.ANONYMOUS_REGION_ID);
            CharSequence ai = C3791b.ai(ApplicationLoader.applicationContext);
            return z && !TextUtils.isEmpty(string) && !TextUtils.isEmpty(ai) && string.equals(ai) && C3791b.am(ApplicationLoader.applicationContext) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
