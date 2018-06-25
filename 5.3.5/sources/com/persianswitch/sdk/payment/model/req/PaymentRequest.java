package com.persianswitch.sdk.payment.model.req;

import com.persianswitch.sdk.base.utils.Json;
import com.persianswitch.sdk.base.webservice.OpCode;
import java.util.HashMap;
import org.json.JSONObject;

public final class PaymentRequest extends AbsRequest {
    private String mDistributorMobileNo;
    private String mMerchantCode;
    private String mPaymentId;
    private String mServerData;
    private String mToken;

    public PaymentRequest() {
        super(OpCode.PAY_TRANSACTION);
    }

    public void setServerData(String serverData) {
        this.mServerData = serverData;
    }

    public void setMerchantCode(String merchantCode) {
        this.mMerchantCode = merchantCode;
    }

    public void setPaymentId(String paymentId) {
        this.mPaymentId = paymentId;
    }

    public void setDistributorMobileNo(String distributorMobileNo) {
        this.mDistributorMobileNo = distributorMobileNo;
    }

    public void setToken(String token) {
        this.mToken = token;
    }

    public JSONObject toExtraData() {
        HashMap<String, Object> extraData = new HashMap();
        extraData.put("sd", this.mServerData);
        extraData.put("mc", this.mMerchantCode);
        extraData.put("id", "");
        extraData.put("pi", this.mPaymentId);
        extraData.put("dm", this.mDistributorMobileNo);
        extraData.put("tk", this.mToken);
        return Json.toJson(extraData);
    }

    public String[] toLegacyExtraData() {
        return new String[0];
    }
}
