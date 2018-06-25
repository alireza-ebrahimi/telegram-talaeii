package org.telegram.customization.Model.Payment;

import org.ir.talaeii.R;
import tellh.com.stickyheaderview_rv.adapter.DataBean;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;

public class SettleReportHeader extends DataBean {
    long settleAmount;
    String settleDate;
    long settlementId;

    public long getSettlementId() {
        return this.settlementId;
    }

    public void setSettlementId(long settlementId) {
        this.settlementId = settlementId;
    }

    public String getSettleDate() {
        return this.settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public long getSettleAmount() {
        return this.settleAmount;
    }

    public void setSettleAmount(long settleAmount) {
        this.settleAmount = settleAmount;
    }

    public int getItemLayoutId(StickyHeaderViewAdapter stickyHeaderViewAdapter) {
        return R.layout.item_payment_header;
    }

    public boolean shouldSticky() {
        return true;
    }
}
