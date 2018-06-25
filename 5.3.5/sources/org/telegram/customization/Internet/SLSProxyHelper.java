package org.telegram.customization.Internet;

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
import utils.app.AppPreferences;
import utils.view.Constants;

public class SLSProxyHelper {

    /* renamed from: org.telegram.customization.Internet.SLSProxyHelper$1 */
    static class C08841 implements IResponseReceiver {
        C08841() {
        }

        public void onResult(Object object, int StatusCode) {
            switch (StatusCode) {
                case Constants.ERROR_GET_PROXY /*-24*/:
                    ArrayList<ProxyServerModel> proxyList = AppPreferences.getProxyList(ApplicationLoader.applicationContext);
                    if (proxyList.size() > 0) {
                        for (int i = 0; i < proxyList.size(); i++) {
                            ((ProxyServerModel) proxyList.get(i)).setUsed(false);
                        }
                        AppPreferences.setProxyList(ApplicationLoader.applicationContext, proxyList);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public static void setProxyDirectly(ProxyServerModel proxyServer) {
        try {
            setProxyDirectly(proxyServer.getIp(), "" + proxyServer.getPort(), proxyServer.getUserName(), proxyServer.getPassWord(), proxyServer.getExpireDateSecs());
        } catch (Exception e) {
        }
    }

    static void setProxyDirectly(String address, String port, String user, String password, long expire) {
        try {
            if (ProxyServerModel.getFromTelegram().equalsWith(new ProxyServerModel(address, port, user, password))) {
                Log.d("AminProxy", "this is same so no set");
                return;
            }
        } catch (Exception e) {
        }
        try {
            Log.d("AminProxy", "let's go set ^_^");
            Log.d("AminProxy", "ip set " + address);
            AppPreferences.setProxyAllInfo(ApplicationLoader.applicationContext, address, port, user, password, true, expire);
            Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
            boolean isProxyEnable = AppPreferences.getProxyEnable(ApplicationLoader.applicationContext) == 1;
            editor.putBoolean("proxy_enabled", isProxyEnable);
            editor.putBoolean("proxy_enabled_calls", isProxyEnable);
            editor.putString("proxy_ip", address);
            int p = Utilities.parseInt(port).intValue();
            editor.putInt("proxy_port", p);
            if (TextUtils.isEmpty(password)) {
                editor.remove("proxy_pass");
            } else {
                editor.putString("proxy_pass", password);
            }
            if (TextUtils.isEmpty(user)) {
                editor.remove("proxy_user");
            } else {
                editor.putString("proxy_user", user);
            }
            editor.commit();
            if (isProxyEnable) {
                ConnectionsManager.native_setProxySettings("", 0, "", "");
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.didUpdatedConnectionState, new Object[0]);
                ConnectionsManager.native_setProxySettings(address, p, user, password);
                if (!UserConfig.isClientActivated()) {
                    ConnectionsManager.getInstance().checkConnection();
                }
            } else {
                ConnectionsManager.native_setProxySettings("", 0, "", "");
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didUpdatedConnectionState, new Object[0]);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.proxySettingsChanged, new Object[0]);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    public static boolean isProxyFromSLS() {
        try {
            SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
            boolean useProxySettings = preferences.getBoolean("proxy_enabled", false);
            String proxyIP = preferences.getString("proxy_ip", "");
            String slsProxy = AppPreferences.getProxyAddress(ApplicationLoader.applicationContext);
            if (!useProxySettings || TextUtils.isEmpty(proxyIP) || TextUtils.isEmpty(slsProxy) || !proxyIP.equals(slsProxy) || AppPreferences.getProxyEnable(ApplicationLoader.applicationContext) <= 0) {
                return false;
            }
            return true;
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }

    public static void getProxyServer(Context context, boolean isForceCallApi) {
        ArrayList<ProxyServerModel> serverModels = AppPreferences.getProxyList(context);
        if (serverModels.size() == 0 || isForceCallApi) {
            HandleRequest.getNew(context, new C08841()).proxyGetServer(true);
        }
        Log.d("LEE", "ConnectionManger:" + isForceCallApi);
        ProxyServerModel proxyServerModel = serverModels.size() > 0 ? (ProxyServerModel) serverModels.get(0) : null;
        if (proxyServerModel != null && !isForceCallApi) {
            Log.d("LEE", "ConnectionManger:ffffff");
            setProxyDirectly(proxyServerModel);
        }
    }

    public static void getProxyServer(Context context) {
        getProxyServer(context, false);
    }
}
