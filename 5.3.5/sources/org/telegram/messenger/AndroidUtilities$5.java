package org.telegram.messenger;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import org.telegram.tgnet.ConnectionsManager;

class AndroidUtilities$5 implements OnClickListener {
    final /* synthetic */ String val$address;
    final /* synthetic */ String val$password;
    final /* synthetic */ String val$port;
    final /* synthetic */ String val$user;

    AndroidUtilities$5(String str, String str2, String str3, String str4) {
        this.val$address = str;
        this.val$port = str2;
        this.val$password = str3;
        this.val$user = str4;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        editor.putBoolean("proxy_enabled", true);
        editor.putString("proxy_ip", this.val$address);
        int p = Utilities.parseInt(this.val$port).intValue();
        editor.putInt("proxy_port", p);
        if (TextUtils.isEmpty(this.val$password)) {
            editor.remove("proxy_pass");
        } else {
            editor.putString("proxy_pass", this.val$password);
        }
        if (TextUtils.isEmpty(this.val$user)) {
            editor.remove("proxy_user");
        } else {
            editor.putString("proxy_user", this.val$user);
        }
        editor.commit();
        ConnectionsManager.native_setProxySettings(this.val$address, p, this.val$user, this.val$password);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.proxySettingsChanged, new Object[0]);
    }
}
