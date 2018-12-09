package org.telegram.customization.Model.Payment;

import org.ir.talaeii.R;
import tellh.com.stickyheaderview_rv.p179a.C5304a;
import tellh.com.stickyheaderview_rv.p179a.C5306e;

public class SettleReportHeader extends C5304a {
    long settleAmount;
    String settleDate;
    long settlementId;

    public int getItemLayoutId(C5306e c5306e) {
        return R.layout.item_payment_header;
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

    public void setSettleAmount(long j) {
        this.settleAmount = j;
    }

    public void setSettleDate(String str) {
        this.settleDate = str;
    }

    public void setSettlementId(long j) {
        this.settlementId = j;
    }

    public boolean shouldSticky() {
        return true;
    }
}
