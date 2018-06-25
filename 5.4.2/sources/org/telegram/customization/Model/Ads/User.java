package org.telegram.customization.Model.Ads;

import java.util.ArrayList;
import org.telegram.customization.Model.Setting;

public class User {
    ArrayList<Setting> info;
    int point;
    ArrayList<Transaction> transactions;

    public ArrayList<Setting> getInfo() {
        return this.info;
    }

    public int getPoint() {
        return this.point;
    }

    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setInfo(ArrayList<Setting> arrayList) {
        this.info = arrayList;
    }

    public void setPoint(int i) {
        this.point = i;
    }

    public void setTransactions(ArrayList<Transaction> arrayList) {
        this.transactions = arrayList;
    }
}
