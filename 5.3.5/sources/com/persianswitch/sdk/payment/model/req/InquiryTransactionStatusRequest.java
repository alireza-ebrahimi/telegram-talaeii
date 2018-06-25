package com.persianswitch.sdk.payment.model.req;

import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.OpCode;
import org.json.JSONObject;

public class InquiryTransactionStatusRequest extends AbsRequest {
    private String mOperationCode;
    private String mPaymentDateTime;
    private String mTranId;

    public InquiryTransactionStatusRequest() {
        super(OpCode.INQUIRY_TRANSACTION);
    }

    public void setTranId(String tranId) {
        this.mTranId = tranId;
    }

    public void setOperationCode(String operationCode) {
        this.mOperationCode = operationCode;
    }

    public void setPaymentDateTime(String paymentDateTime) {
        this.mPaymentDateTime = paymentDateTime;
    }

    public JSONObject toExtraData() {
        return new JSONObject();
    }

    public String[] toLegacyExtraData() {
        return new String[]{StringUtils.toNonNullString(this.mTranId), StringUtils.toNonNullString(this.mOperationCode), StringUtils.toNonNullString(this.mPaymentDateTime), ""};
    }
}
