package org.telegram.customization.Model.Payment;

import java.util.ArrayList;
import org.ir.talaeii.R;
import tellh.com.stickyheaderview_rv.p179a.C5304a;
import tellh.com.stickyheaderview_rv.p179a.C5306e;

public class ReportHelper extends C5304a {
    String key;
    ArrayList<PaymentReport> value;

    public int getItemLayoutId(C5306e c5306e) {
        return R.layout.item_payment_header;
    }

    public String getKey() {
        return this.key;
    }

    public ArrayList<PaymentReport> getValue() {
        return this.value;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public void setValue(ArrayList<PaymentReport> arrayList) {
        this.value = arrayList;
    }

    public boolean shouldSticky() {
        return true;
    }
}
