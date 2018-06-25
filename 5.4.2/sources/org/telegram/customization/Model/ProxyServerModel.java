package org.telegram.customization.Model;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.p098a.p099a.C1662c;
import java.util.Comparator;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.Utilities;
import utils.p178a.C3791b;

public class ProxyServerModel implements Comparable {
    public static Comparator comparator = new C26311();
    @C1662c(a = "ttl")
    long expireDateSecs;
    String ip;
    long localExpireTime;
    @C1662c(a = "pwd")
    String passWord;
    @C1662c(a = "prt")
    int port;
    @C1662c(a = "prh")
    boolean porxyHealth;
    int usedCount;
    @C1662c(a = "usr")
    String userName;

    /* renamed from: org.telegram.customization.Model.ProxyServerModel$1 */
    static class C26311 implements Comparator {
        C26311() {
        }

        public int compare(Object obj, Object obj2) {
            return ((obj instanceof ProxyServerModel) && (obj2 instanceof ProxyServerModel)) ? (int) (((ProxyServerModel) obj2).getExpireDateSecs() - ((ProxyServerModel) obj).getExpireDateSecs()) : 0;
        }
    }

    public ProxyServerModel() {
        this.porxyHealth = true;
        this.ip = TtmlNode.ANONYMOUS_REGION_ID;
        this.port = 0;
        this.userName = TtmlNode.ANONYMOUS_REGION_ID;
        this.passWord = TtmlNode.ANONYMOUS_REGION_ID;
        this.usedCount = 0;
        this.expireDateSecs = 0;
        this.localExpireTime = System.currentTimeMillis();
    }

    public ProxyServerModel(String str, String str2, String str3, String str4) {
        this.porxyHealth = true;
        this.ip = str;
        this.port = Utilities.parseInt(str2).intValue();
        this.userName = str3;
        this.passWord = str4;
        this.usedCount = 0;
        this.expireDateSecs = 0;
        this.localExpireTime = System.currentTimeMillis();
    }

    public static ProxyServerModel getFromShared() {
        ProxyServerModel proxyServerModel = new ProxyServerModel();
        proxyServerModel.setIp(C3791b.ai(ApplicationLoader.applicationContext));
        proxyServerModel.setPort(Utilities.parseInt(C3791b.aj(ApplicationLoader.applicationContext)).intValue());
        proxyServerModel.setUserName(C3791b.ak(ApplicationLoader.applicationContext));
        proxyServerModel.setPassWord(C3791b.al(ApplicationLoader.applicationContext));
        proxyServerModel.setPorxyHealth(C3791b.an(ApplicationLoader.applicationContext));
        proxyServerModel.setExpireDateSecs(C3791b.m13940b());
        return proxyServerModel;
    }

    public static ProxyServerModel getFromTelegram() {
        ProxyServerModel proxyServerModel = new ProxyServerModel();
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        try {
            proxyServerModel.setIp(sharedPreferences.getString("proxy_ip", TtmlNode.ANONYMOUS_REGION_ID));
        } catch (Exception e) {
        }
        try {
            proxyServerModel.setPort(sharedPreferences.getInt("proxy_port", 0));
        } catch (Exception e2) {
        }
        try {
            proxyServerModel.setPassWord(sharedPreferences.getString("proxy_pass", TtmlNode.ANONYMOUS_REGION_ID));
        } catch (Exception e3) {
        }
        try {
            proxyServerModel.setUserName(sharedPreferences.getString("proxy_user", TtmlNode.ANONYMOUS_REGION_ID));
        } catch (Exception e4) {
        }
        try {
            proxyServerModel.setPorxyHealth(true);
        } catch (Exception e5) {
        }
        return proxyServerModel;
    }

    public static ProxyServerModel getHardcodedProxy() {
        return new ProxyServerModel();
    }

    public int compareTo(Object obj) {
        return obj instanceof ProxyServerModel ? (int) (((ProxyServerModel) obj).getExpireDateSecs() - getExpireDateSecs()) : 0;
    }

    public boolean equalsWith(ProxyServerModel proxyServerModel) {
        try {
            return this.ip.equals(proxyServerModel.ip) && this.passWord.equals(proxyServerModel.passWord) && this.userName.equals(proxyServerModel.userName) && this.port == proxyServerModel.port;
        } catch (Exception e) {
            return false;
        }
    }

    public void fillLocalExpireTime() {
        Log.d("LEE", "expireDateSecs: " + this.expireDateSecs);
        this.localExpireTime = System.currentTimeMillis() + (this.expireDateSecs * 1000);
    }

    public long getExpireDateSecs() {
        return this.expireDateSecs;
    }

    public String getIp() {
        return this.ip;
    }

    public long getLocalExpireTime() {
        return this.localExpireTime;
    }

    public String getPassWord() {
        return this.passWord;
    }

    public int getPort() {
        return this.port;
    }

    public String getUserName() {
        return this.userName;
    }

    public boolean isHalfUsed() {
        return this.usedCount == 4;
    }

    public boolean isPorxyHealth() {
        return this.porxyHealth;
    }

    public boolean isUsed() {
        return this.usedCount >= 4;
    }

    public void setExpireDateSecs(long j) {
        this.expireDateSecs = j;
    }

    public void setIp(String str) {
        this.ip = str;
    }

    public void setPassWord(String str) {
        this.passWord = str;
    }

    public void setPort(int i) {
        this.port = i;
    }

    public void setPorxyHealth(boolean z) {
        this.porxyHealth = z;
    }

    public void setUsed(boolean z) {
        if (z) {
            this.usedCount++;
        } else {
            this.usedCount = 0;
        }
    }

    public void setUserName(String str) {
        this.userName = str;
    }
}
