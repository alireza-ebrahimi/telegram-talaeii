package org.telegram.customization.Model.Payment;

import org.ir.talaeii.R;
import tellh.com.stickyheaderview_rv.adapter.DataBean;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;

public class PaymentReport extends DataBean {
    long amount;
    String createDate;
    String description;
    long fromTelegramUserId;
    int gatewayType;
    long monoFinanceOrderId;
    long paymentId;
    int status;
    long toTelegramUserId;

    public long getPaymentId() {
        return this.paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public long getMonoFinanceOrderId() {
        return this.monoFinanceOrderId;
    }

    public void setMonoFinanceOrderId(long monoFinanceOrderId) {
        this.monoFinanceOrderId = monoFinanceOrderId;
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

    public long getAmount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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
