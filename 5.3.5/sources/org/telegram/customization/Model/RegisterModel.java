package org.telegram.customization.Model;

import android.text.TextUtils;
import com.google.gson.Gson;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.messenger.UserConfig;

public class RegisterModel {
    private String fullName;
    boolean isFromInfo;
    private String phone;
    private long userId;
    private String username;
    private int versionCode;

    public boolean isFromInfo() {
        return this.isFromInfo;
    }

    public void setFromInfo(boolean fromInfo) {
        this.isFromInfo = fromInfo;
    }

    public static RegisterModel getInstance() {
        try {
            UserConfig.loadConfig();
            RegisterModel model = new RegisterModel();
            if (UserConfig.getCurrentUser() == null) {
                return model;
            }
            model.setFullName((TextUtils.isEmpty(UserConfig.getCurrentUser().first_name) ? "" : UserConfig.getCurrentUser().first_name) + (TextUtils.isEmpty(UserConfig.getCurrentUser().last_name) ? "" : " " + UserConfig.getCurrentUser().last_name));
            model.setPhone(UserConfig.getCurrentUser().phone);
            model.setUserId((long) UserConfig.getCurrentUser().id);
            model.setUsername(UserConfig.getCurrentUser().username);
            model.setVersionCode(135);
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            return new RegisterModel();
        }
    }

    public static String getRegisterModelData() {
        RegisterModel model = getInstance();
        model.setFromInfo(true);
        return new String(HandleRequest.getEncondedByteArr(new Gson().toJson(model)));
    }

    public static String getRegisterModelDataNew() {
        RegisterModel model = getInstance();
        model.setFromInfo(true);
        return new String(HandleRequest.getEncondedByteArr(new Gson().toJson(model)));
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}
