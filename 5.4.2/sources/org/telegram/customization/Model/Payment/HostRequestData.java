package org.telegram.customization.Model.Payment;

import com.google.p098a.p099a.C1662c;

public class HostRequestData {
    Long amount;
    String date;
    String description;
    @C1662c(a = "hreq")
    String hostRequest;
    @C1662c(a = "hsign")
    String hostRequestSign;
    int orderId;
    @C1662c(a = "ver")
    String sdkProtocolVersionInfo;
    long tranId;

    public Long getAmount() {
        return this.amount;
    }

    public String getDate() {
        return this.date;
    }

    public String getDescription() {
        return this.description;
    }

    public String getHostRequest() {
        return this.hostRequest;
    }

    public String getHostRequestSign() {
        return this.hostRequestSign;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public String getSdkProtocolVersionInfo() {
        return this.sdkProtocolVersionInfo;
    }

    public long getTranId() {
        return this.tranId;
    }

    public void setAmount(Long l) {
        this.amount = l;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setHostRequest(String str) {
        this.hostRequest = str;
    }

    public void setHostRequestSign(String str) {
        this.hostRequestSign = str;
    }

    public void setOrderId(int i) {
        this.orderId = i;
    }

    public void setSdkProtocolVersionInfo(String str) {
        this.sdkProtocolVersionInfo = str;
    }

    public void setTranId(long j) {
        this.tranId = j;
    }
}
