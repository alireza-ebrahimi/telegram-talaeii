package org.telegram.customization.Model.Payment;

import com.google.gson.annotations.SerializedName;

public class HostRequestData {
    Long amount;
    String date;
    String description;
    @SerializedName("hreq")
    String hostRequest;
    @SerializedName("hsign")
    String hostRequestSign;
    int orderId;
    @SerializedName("ver")
    String sdkProtocolVersionInfo;
    long tranId;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getAmount() {
        return this.amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public long getTranId() {
        return this.tranId;
    }

    public void setTranId(long tranId) {
        this.tranId = tranId;
    }

    public String getHostRequest() {
        return this.hostRequest;
    }

    public void setHostRequest(String hostRequest) {
        this.hostRequest = hostRequest;
    }

    public String getHostRequestSign() {
        return this.hostRequestSign;
    }

    public void setHostRequestSign(String hostRequestSign) {
        this.hostRequestSign = hostRequestSign;
    }

    public String getSdkProtocolVersionInfo() {
        return this.sdkProtocolVersionInfo;
    }

    public void setSdkProtocolVersionInfo(String sdkProtocolVersionInfo) {
        this.sdkProtocolVersionInfo = sdkProtocolVersionInfo;
    }
}
