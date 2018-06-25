package org.telegram.customization.Model.Payment;

import org.ir.talaeii.R;
import tellh.com.stickyheaderview_rv.adapter.DataBean;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;

public class SettleReport extends DataBean {
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

    public long getSettlementId() {
        return this.settlementId;
    }

    public void setSettlementId(long settlementId) {
        this.settlementId = settlementId;
    }

    public long getTelegramUserId() {
        return this.telegramUserId;
    }

    public void setTelegramUserId(long telegramUserId) {
        this.telegramUserId = telegramUserId;
    }

    public long getMonoFinanceId() {
        return this.monoFinanceId;
    }

    public void setMonoFinanceId(long monoFinanceId) {
        this.monoFinanceId = monoFinanceId;
    }

    public long getSettleAmount() {
        return this.settleAmount;
    }

    public void setSettleAmount(long settleAmount) {
        this.settleAmount = settleAmount;
    }

    public String getSettleDate() {
        return this.settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public long getPaymentId() {
        return this.paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public long getFromTelegramUserId() {
        return this.fromTelegramUserId;
    }

    public void setFromTelegramUserId(long fromTelegramUserId) {
        this.fromTelegramUserId = fromTelegramUserId;
    }

    public long getToTelegramUserId() {
        return this.toTelegramUserId;
    }

    public void setToTelegramUserId(long toTelegramUserId) {
        this.toTelegramUserId = toTelegramUserId;
    }

    public long getPaymentAmount() {
        return this.paymentAmount;
    }

    public void setPaymentAmount(long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGatewayType() {
        return this.gatewayType;
    }

    public void setGatewayType(int gatewayType) {
        this.gatewayType = gatewayType;
    }

    public int getItemLayoutId(StickyHeaderViewAdapter stickyHeaderViewAdapter) {
        return R.layout.item_payment;
    }
}
