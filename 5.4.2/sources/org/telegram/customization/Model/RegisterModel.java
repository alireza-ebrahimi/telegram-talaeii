package org.telegram.customization.Model;

import android.text.TextUtils;
import com.google.p098a.C1768f;
import org.telegram.customization.p151g.C2818c;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.UserConfig;

public class RegisterModel {
    private String fullName;
    boolean isFromInfo;
    private String phone;
    String pushToken;
    private long userId;
    private String username;
    private int versionCode;

    public static RegisterModel getInstance() {
        try {
            UserConfig.loadConfig();
            RegisterModel registerModel = new RegisterModel();
            if (UserConfig.getCurrentUser() == null) {
                return registerModel;
            }
            registerModel.setFullName((TextUtils.isEmpty(UserConfig.getCurrentUser().first_name) ? TtmlNode.ANONYMOUS_REGION_ID : UserConfig.getCurrentUser().first_name) + (TextUtils.isEmpty(UserConfig.getCurrentUser().last_name) ? TtmlNode.ANONYMOUS_REGION_ID : " " + UserConfig.getCurrentUser().last_name));
            registerModel.setPhone(UserConfig.getCurrentUser().phone);
            registerModel.setUserId((long) UserConfig.getCurrentUser().id);
            registerModel.setUsername(UserConfig.getCurrentUser().username);
            registerModel.setVersionCode(BuildConfig.VERSION_CODE);
            return registerModel;
        } catch (Exception e) {
            e.printStackTrace();
            return new RegisterModel();
        }
    }

    public static String getRegisterModelData() {
        Object instance = getInstance();
        instance.setFromInfo(true);
        return new String(C2818c.m13103p(new C1768f().m8395a(instance)));
    }

    public static String getRegisterModelDataNew() {
        Object instance = getInstance();
        instance.setFromInfo(true);
        return new String(C2818c.m13103p(new C1768f().m8395a(instance)));
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getPushToken() {
        return this.pushToken;
    }

    public long getUserId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public boolean isFromInfo() {
        return this.isFromInfo;
    }

    public void setFromInfo(boolean z) {
        this.isFromInfo = z;
    }

    public void setFullName(String str) {
        this.fullName = str;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public void setPushToken(String str) {
        this.pushToken = str;
    }

    public void setUserId(long j) {
        this.userId = j;
    }

    public void setUsername(String str) {
        this.username = str;
    }

    public void setVersionCode(int i) {
        this.versionCode = i;
    }
}
