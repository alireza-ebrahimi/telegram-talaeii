package com.persianswitch.sdk.payment.model.req;

import android.content.Context;
import com.persianswitch.sdk.base.utils.Json;
import com.persianswitch.sdk.base.utils.RootUtils;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.IWebServiceDescriptor;
import com.persianswitch.sdk.base.webservice.OpCode;
import com.persianswitch.sdk.base.webservice.data.WSRequest;
import com.persianswitch.sdk.payment.SDKConfig;
import com.thin.downloadmanager.BuildConfig;
import java.util.HashMap;
import org.json.JSONObject;

public class RegisterRequest extends AbsRequest {
    private String mActivationCode;
    private String mActivationId;
    private String mAndroidId;
    private String mAndroidVersion;
    private int mAuthCode;
    private String mDeviceId;
    private String mIMEI;
    private boolean mIsTablet;
    private String mMobileNo;
    private String mModel;
    private String mWifiMAC;

    RegisterRequest(OpCode opCode) {
        super(opCode);
    }

    public RegisterRequest() {
        super(OpCode.REGISTER_APPLICATION);
    }

    public IWebServiceDescriptor getWebServiceDescriptor(final Context context) {
        return new IWebServiceDescriptor() {
            public WSRequest getRequest() {
                WSRequest superRequest = super.getWebServiceDescriptor(context).getRequest();
                superRequest.setMobileNumber(RegisterRequest.this.mMobileNo);
                return superRequest;
            }
        };
    }

    public String getMobileNo() {
        return this.mMobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mMobileNo = mobileNo;
    }

    public JSONObject toExtraData() {
        return Json.toJson(new HashMap());
    }

    public String[] toLegacyExtraData() {
        String[] strArr = new String[15];
        strArr[0] = getProtocolActivationId();
        strArr[1] = StringUtils.toNonNullString(this.mActivationCode);
        strArr[2] = getAuthCode() + "";
        strArr[3] = BuildConfig.VERSION_NAME;
        strArr[4] = getAndroidVersion();
        strArr[5] = isTablet() ? "2" : BuildConfig.VERSION_NAME;
        strArr[6] = getModel();
        strArr[7] = getDeviceId();
        strArr[8] = "";
        strArr[9] = "";
        strArr[10] = new SDKConfig().getDistributorCode();
        strArr[11] = getIMEI();
        strArr[12] = getWifiMAC();
        strArr[13] = "";
        strArr[14] = RootUtils.isRooted() ? BuildConfig.VERSION_NAME : "0";
        return strArr;
    }

    private String getProtocolActivationId() {
        if (StringUtils.isEmpty(this.mActivationCode) || StringUtils.isEmpty(this.mActivationId)) {
            return "3;";
        }
        return "1;" + this.mActivationId;
    }

    public int getAuthCode() {
        return this.mAuthCode;
    }

    public void setAuthCode(int authCode) {
        this.mAuthCode = authCode;
    }

    public String getAndroidVersion() {
        return this.mAndroidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.mAndroidVersion = androidVersion;
    }

    public boolean isTablet() {
        return this.mIsTablet;
    }

    public void setTablet(boolean tablet) {
        this.mIsTablet = tablet;
    }

    public String getModel() {
        return this.mModel;
    }

    public void setModel(String model) {
        this.mModel = model;
    }

    public String getDeviceId() {
        return this.mDeviceId;
    }

    public String getIMEI() {
        return this.mIMEI;
    }

    public void setIMEI(String IMEI) {
        this.mIMEI = IMEI;
    }

    public String getWifiMAC() {
        return this.mWifiMAC;
    }

    public void setWifiMAC(String wifiMAC) {
        this.mWifiMAC = wifiMAC;
    }

    public void setDeviceId(String deviceId) {
        this.mDeviceId = deviceId;
    }

    public String getAndroidId() {
        return this.mAndroidId;
    }

    public void setAndroidId(String androidId) {
        this.mAndroidId = androidId;
    }

    public void setActivationId(String activationId) {
        this.mActivationId = activationId;
    }

    public void setActivationCode(String activationCode) {
        this.mActivationCode = activationCode;
    }
}
