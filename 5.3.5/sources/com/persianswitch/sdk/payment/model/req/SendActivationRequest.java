package com.persianswitch.sdk.payment.model.req;

import android.content.Context;
import android.os.Build.VERSION;
import com.persianswitch.sdk.base.security.DeviceInfo;
import com.persianswitch.sdk.base.utils.CertificateUtils;
import com.persianswitch.sdk.base.webservice.IWebServiceDescriptor;
import com.persianswitch.sdk.base.webservice.OpCode;
import com.persianswitch.sdk.base.webservice.data.WSRequest;
import com.persianswitch.sdk.payment.SDKConfig;
import com.persianswitch.sdk.payment.model.HostDataRequestField;
import com.thin.downloadmanager.BuildConfig;
import org.json.JSONObject;

public class SendActivationRequest extends AbsRequest {
    private String mIMEI;
    private boolean mIsTablet;
    private String mMobileNo;
    private String mModel;
    private String mWifiMac;

    public SendActivationRequest() {
        super(OpCode.SEND_ACTIVATION_CODE);
    }

    public IWebServiceDescriptor getWebServiceDescriptor(final Context context) {
        return new IWebServiceDescriptor() {
            public WSRequest getRequest() {
                WSRequest wsRequest = new WSRequest();
                wsRequest.setOperationCode(SendActivationRequest.this.getOpCode().getCode());
                wsRequest.setMobileNumber(SendActivationRequest.this.mMobileNo);
                wsRequest.setApplicationInfo(DeviceInfo.getApplicationInfo(context, new SDKConfig()));
                wsRequest.setPackageName(SDKConfig.getPackageName(context));
                wsRequest.setDeviceIdentifier(DeviceInfo.generateDeviceIdentifier(context, SendActivationRequest.this.mIMEI, SendActivationRequest.this.mWifiMac));
                wsRequest.setApplicationDigest(CertificateUtils.getCertificateSHA1Fingerprint(context));
                wsRequest.setHostId(SendActivationRequest.this.getHostId());
                wsRequest.setHostData(new HostDataRequestField(SendActivationRequest.this.getHostData(), SendActivationRequest.this.getHostSign(), SendActivationRequest.this.getSDKProtocolVersion(), AbsRequest.getHostVersion(context)).toJson());
                wsRequest.setExtraData(SendActivationRequest.this.toExtraData());
                wsRequest.setLegacyExtraData(SendActivationRequest.this.toLegacyExtraData());
                return wsRequest;
            }
        };
    }

    public void setMobileNo(String mobileNo) {
        this.mMobileNo = mobileNo;
    }

    public void setIMEI(String IMEI) {
        this.mIMEI = IMEI;
    }

    public void setWifiMac(String wifiMac) {
        this.mWifiMac = wifiMac;
    }

    public void setTablet(boolean tablet) {
        this.mIsTablet = tablet;
    }

    public void setModel(String model) {
        this.mModel = model;
    }

    public JSONObject toExtraData() {
        return new JSONObject();
    }

    public String[] toLegacyExtraData() {
        String[] strArr = new String[5];
        strArr[0] = BuildConfig.VERSION_NAME;
        strArr[1] = VERSION.RELEASE;
        strArr[2] = this.mIsTablet ? "2" : BuildConfig.VERSION_NAME;
        strArr[3] = this.mModel;
        strArr[4] = new SDKConfig().getDistributorCode();
        return strArr;
    }
}
