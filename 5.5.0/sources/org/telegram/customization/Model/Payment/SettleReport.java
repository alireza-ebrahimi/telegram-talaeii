package org.telegram.customization.Model.Payment;

import org.ir.talaeii.R;
import tellh.com.stickyheaderview_rv.p179a.C5304a;
import tellh.com.stickyheaderview_rv.p179a.C5306e;

public class SettleReport extends C5304a {
    String createdate;
    String description;
    long fromTelegramUserId;
    int gatewayType;
    long monoFinanceId;
    long paymentAmount;
    long paymentId;
    long settleAmount;
    String settleDate;
    long settlementId;
    int status;
    long telegramUserId;
    long toTelegramUserId;

    public String getCreatedate() {
        return this.createdate;
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

    public long getMonoFinanceId() {
        return this.monoFinanceId;
    }

    public long getPaymentAmount() {
        return this.paymentAmount;
    }

    public long getPaymentId() {
        return this.paymentId;
    }

    public long getSettleAmount() {
        return this.settleAmount;
    }

    public String getSettleDate() {
        return this.settleDate;
    }

    public long getSettlementId() {
        return this.settlementId;
    }

    public int getStatus() {
        return this.status;
    }

    public long getTelegramUserId() {
        return this.telegramUserId;
    }

    public long getToTelegramUserId() {
        return this.toTelegramUserId;
    }

    public void setCreatedate(String str) {
        this.createdate = str;
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

    public void setMonoFinanceId(long j) {
        this.monoFinanceId = j;
    }

    public void setPaymentAmount(long j) {
        this.paymentAmount = j;
    }

    public void setPaymentId(long j) {
        this.paymentId = j;
    }

    public void setSettleAmount(long j) {
        this.settleAmount = j;
    }

    public void setSettleDate(String str) {
        this.settleDate = str;
    }

    public void setSettlementId(long j) {
        this.settlementId = j;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public void setTelegramUserId(long j) {
        this.telegramUserId = j;
    }

    public void setToTelegramUserId(long j) {
        this.toTelegramUserId = j;
    }
}
