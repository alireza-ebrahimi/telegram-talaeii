package com.persianswitch.sdk.payment.model.req;

import com.persianswitch.sdk.base.utils.RootUtils;
import com.persianswitch.sdk.base.webservice.OpCode;
import com.thin.downloadmanager.BuildConfig;
import org.json.JSONObject;

public class ReVerificationRequest extends RegisterRequest {
    public ReVerificationRequest() {
        super(OpCode.RE_VERIFICATION_APPLICATION);
    }

    public JSONObject toExtraData() {
        return super.toExtraData();
    }

    public String[] toLegacyExtraData() {
        String[] strArr = new String[11];
        strArr[0] = "3;";
        strArr[1] = "";
        strArr[2] = getAuthCode() + "";
        strArr[3] = BuildConfig.VERSION_NAME;
        strArr[4] = getAndroidVersion();
        strArr[5] = isTablet() ? "2" : BuildConfig.VERSION_NAME;
        strArr[6] = getModel();
        strArr[7] = getDeviceId();
        strArr[8] = getIMEI();
        strArr[9] = getWifiMAC();
        strArr[10] = RootUtils.isRooted() ? BuildConfig.VERSION_NAME : "0";
        return strArr;
    }
}
