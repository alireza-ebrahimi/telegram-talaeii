package org.telegram.customization.Model.Payment;

import java.util.ArrayList;
import org.ir.talaeii.R;
import tellh.com.stickyheaderview_rv.adapter.DataBean;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;

public class ReportHelper extends DataBean {
    String key;
    ArrayList<PaymentReport> value;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<PaymentReport> getValue() {
        return this.value;
    }

    public void setValue(ArrayList<PaymentReport> value) {
        this.value = value;
    }

    public int getItemLayoutId(StickyHeaderViewAdapter stickyHeaderViewAdapter) {
        return R.layout.item_payment_header;
    }

    public boolean shouldSticky() {
        return true;
    }
}
