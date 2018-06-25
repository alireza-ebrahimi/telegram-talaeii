package org.telegram.customization.Model.Payment;

import java.util.ArrayList;

public class SettleHelper {
    SettleReportHeader key;
    ArrayList<SettleReport> value = new ArrayList();

    public SettleReportHeader getKey() {
        return this.key;
    }

    public void setKey(SettleReportHeader key) {
        this.key = key;
    }

    public ArrayList<SettleReport> getValue() {
        return this.value;
    }

    public void setValue(ArrayList<SettleReport> value) {
        this.value = value;
    }
}
