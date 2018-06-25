package com.persianswitch.sdk.payment.model.req;

import android.content.Context;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.IWebServiceDescriptor;
import com.persianswitch.sdk.base.webservice.OpCode;
import com.persianswitch.sdk.base.webservice.SyncWebService;
import com.persianswitch.sdk.base.webservice.WebService;
import com.persianswitch.sdk.base.webservice.data.WSRequest;
import com.persianswitch.sdk.base.webservice.data.WSTranRequest;
import com.persianswitch.sdk.payment.SDKConfig;
import com.persianswitch.sdk.payment.model.HostDataRequestField;
import org.json.JSONObject;

public abstract class AbsRequest implements IRequest {
    private String mAmount;
    private String mHostData;
    private int mHostId;
    private String mHostSign;
    private final OpCode mOpCode;
    private String mSDKProtocolVersion;
    private String mServerData;

    public abstract JSONObject toExtraData();

    @Deprecated
    public abstract String[] toLegacyExtraData();

    AbsRequest(OpCode opCode) {
        this.mOpCode = opCode;
    }

    public IWebServiceDescriptor getWebServiceDescriptor(final Context context) {
        final String hostVersion = getHostVersion(context);
        return this.mOpCode.isFinancial() ? new IWebServiceDescriptor() {
            public WSTranRequest getRequest() {
                WSTranRequest wsTranRequest = WSTranRequest.create(context, new SDKConfig(), AbsRequest.this.mOpCode.getCode(), 0);
                Long optAmount = StringUtils.toLong(AbsRequest.this.mAmount);
                if (optAmount != null) {
                    wsTranRequest.setAmount(optAmount.longValue());
                }
                wsTranRequest.setHostId(AbsRequest.this.mHostId);
                wsTranRequest.setHostData(new HostDataRequestField(AbsRequest.this.getHostData(), AbsRequest.this.getHostSign(), AbsRequest.this.getSDKProtocolVersion(), hostVersion).toJson());
                wsTranRequest.setExtraData(AbsRequest.this.toExtraData());
                wsTranRequest.setLegacyExtraData(AbsRequest.this.toLegacyExtraData());
                return wsTranRequest;
            }
        } : new IWebServiceDescriptor() {
            public WSRequest getRequest() {
                WSRequest wsRequest = WSRequest.create(context, new SDKConfig(), AbsRequest.this.mOpCode.getCode());
                wsRequest.setHostId(AbsRequest.this.mHostId);
                wsRequest.setHostData(new HostDataRequestField(AbsRequest.this.getHostData(), AbsRequest.this.getHostSign(), AbsRequest.this.getSDKProtocolVersion(), hostVersion).toJson());
                wsRequest.setExtraData(AbsRequest.this.toExtraData());
                wsRequest.setLegacyExtraData(AbsRequest.this.toLegacyExtraData());
                return wsRequest;
            }
        };
    }

    public static String getHostVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            return "";
        }
    }

    public final WebService getWebService(Context context) {
        return WebService.create(getWebServiceDescriptor(context).getRequest());
    }

    public final SyncWebService getSyncWebservice(Context context) {
        return SyncWebService.create(getWebServiceDescriptor(context).getRequest());
    }

    public String getSDKProtocolVersion() {
        return this.mSDKProtocolVersion;
    }

    public void setSDKProtocolVersion(String SDKProtocolVersion) {
        this.mSDKProtocolVersion = SDKProtocolVersion;
    }

    public String getHostData() {
        return this.mHostData;
    }

    public void setHostData(String hostData) {
        this.mHostData = hostData;
    }

    public String getHostSign() {
        return this.mHostSign;
    }

    public void setHostSign(String hostSign) {
        this.mHostSign = hostSign;
    }

    public OpCode getOpCode() {
        return this.mOpCode;
    }

    public String getServerData() {
        return this.mServerData;
    }

    public void setServerData(String serverData) {
        this.mServerData = serverData;
    }

    public String getAmount() {
        return this.mAmount;
    }

    public void setAmount(String amount) {
        this.mAmount = amount;
    }

    public int getHostId() {
        return this.mHostId;
    }

    public void setHostId(int hostId) {
        this.mHostId = hostId;
    }
}
