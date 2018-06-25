package org.telegram.customization.Model.Payment;

import org.ir.talaeii.R;
import tellh.com.stickyheaderview_rv.p179a.C5304a;
import tellh.com.stickyheaderview_rv.p179a.C5306e;

public class PaymentReport extends C5304a {
    long amount;
    String createDate;
    String description;
    long fromTelegramUserId;
    int gatewayType;
    long monoFinanceOrderId;
    long paymentId;
    int status;
    long toTelegramUserId;

    public long getAmount() {
        return this.amount;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public String getDescription() {
        return this.description;
    }

    public long getFromTelegramUserId() {
        return this.fromTelegramUserId;
    }

    public int getGatewayType() {
        return this.gatewayType;
    }

    public int getItemLayoutId(C5306e c5306e) {
        return R.layout.item_payment;
    }

    public long getMonoFinanceOrderId() {
        return this.monoFinanceOrderId;
    }

    public long getPaymentId() {
        return this.paymentId;
    }

    public int getStatus() {
        return this.status;
    }

    public long getToTelegramUserId() {
        return this.toTelegramUserId;
    }

    public void setAmount(long j) {
        this.amount = j;
    }

    public void setCreateDate(String str) {
        this.createDate = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setFromTelegramUserId(long j) {
        this.fromTelegramUserId = j;
    }

    public void setGatewayType(int i) {
        this.gatewayType = i;
    }

    public void setMonoFinanceOrderId(long j) {
        this.monoFinanceOrderId = j;
    }

    public void setPaymentId(long j) {
        this.paymentId = j;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public void setToTelegramUserId(long j) {
        this.toTelegramUserId = j;
    }
}
