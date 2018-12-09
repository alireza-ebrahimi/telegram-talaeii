package org.telegram.customization.Model.Payment;

import java.util.ArrayList;

public class SettleHelper {
    SettleReportHeader key;
    ArrayList<SettleReport> value = new ArrayList();

    public SettleReportHeader getKey() {
        return this.key;
    }

    public ArrayList<SettleReport> getValue() {
        return this.value;
    }

    public void setKey(SettleReportHeader settleReportHeader) {
        this.key = settleReportHeader;
    }

    public void setValue(ArrayList<SettleReport> arrayList) {
        this.value = arrayList;
    }
}
