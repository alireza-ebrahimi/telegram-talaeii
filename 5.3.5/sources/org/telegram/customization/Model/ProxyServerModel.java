package org.telegram.customization.Model;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.util.Comparator;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.Utilities;
import utils.app.AppPreferences;

public class ProxyServerModel implements Comparable {
    public static Comparator comparator = new ProxyServerModel$1();
    @SerializedName("ttl")
    long expireDateSecs;
    String ip;
    long localExpireTime;
    @SerializedName("pwd")
    String passWord;
    @SerializedName("prt")
    int port;
    @SerializedName("prh")
    boolean porxyHealth;
    int usedCount;
    @SerializedName("usr")
    String userName;

    public ProxyServerModel() {
        this.porxyHealth = true;
        this.ip = "";
        this.port = 0;
        this.userName = "";
        this.passWord = "";
        this.usedCount = 0;
        this.expireDateSecs = 0;
        this.localExpireTime = System.currentTimeMillis();
    }

    public ProxyServerModel(String address, String port, String user, String password) {
        this.porxyHealth = true;
        this.ip = address;
        this.port = Utilities.parseInt(port).intValue();
        this.userName = user;
        this.passWord = password;
        this.usedCount = 0;
        this.expireDateSecs = 0;
        this.localExpireTime = System.currentTimeMillis();
    }

    public boolean isUsed() {
        return this.usedCount >= 4;
    }

    public boolean isHalfUsed() {
        return this.usedCount == 4;
    }

    public void setUsed(boolean used) {
        if (used) {
            this.usedCount++;
        } else {
            this.usedCount = 0;
        }
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return this.passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public long getExpireDateSecs() {
        return this.expireDateSecs;
    }

    public void setExpireDateSecs(long expireDateSecs) {
        this.expireDateSecs = expireDateSecs;
    }

    public boolean isPorxyHealth() {
        return this.porxyHealth;
    }

    public void setPorxyHealth(boolean porxyHealth) {
        this.porxyHealth = porxyHealth;
    }

    public long getLocalExpireTime() {
        return this.localExpireTime;
    }

    public void fillLocalExpireTime() {
        this.localExpireTime = System.currentTimeMillis() + (this.expireDateSecs * 1000);
    }

    public static ProxyServerModel getFromShared() {
        ProxyServerModel ans = new ProxyServerModel();
        ans.setIp(AppPreferences.getProxyAddress(ApplicationLoader.applicationContext));
        ans.setPort(Utilities.parseInt(AppPreferences.getProxyPort(ApplicationLoader.applicationContext)).intValue());
        ans.setUserName(AppPreferences.getProxyUsername(ApplicationLoader.applicationContext));
        ans.setPassWord(AppPreferences.getProxyPassword(ApplicationLoader.applicationContext));
        ans.setPorxyHealth(AppPreferences.isProxyHealthy(ApplicationLoader.applicationContext));
        ans.setExpireDateSecs(AppPreferences.getExpireProxy());
        return ans;
    }

    public static ProxyServerModel getFromTelegram() {
        ProxyServerModel ans = new ProxyServerModel();
        SharedPreferences prefs = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        try {
            ans.setIp(prefs.getString("proxy_ip", ""));
        } catch (Exception e) {
        }
        try {
            ans.setPort(prefs.getInt("proxy_port", 0));
        } catch (Exception e2) {
        }
        try {
            ans.setPassWord(prefs.getString("proxy_pass", ""));
        } catch (Exception e3) {
        }
        try {
            ans.setUserName(prefs.getString("proxy_user", ""));
        } catch (Exception e4) {
        }
        try {
            ans.setPorxyHealth(true);
        } catch (Exception e5) {
        }
        return ans;
    }

    public static ProxyServerModel getHardcodedProxy() {
        return new ProxyServerModel();
    }

    public int compareTo(@NonNull Object o) {
        if (o instanceof ProxyServerModel) {
            return (int) (((ProxyServerModel) o).getExpireDateSecs() - getExpireDateSecs());
        }
        return 0;
    }

    public boolean equalsWith(ProxyServerModel p) {
        try {
            return this.ip.equals(p.ip) && this.passWord.equals(p.passWord) && this.userName.equals(p.userName) && this.port == p.port;
        } catch (Exception e) {
            return false;
        }
    }
}
