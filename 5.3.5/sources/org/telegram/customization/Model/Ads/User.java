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

    public void setInfo(ArrayList<Setting> info) {
        this.info = info;
    }

    public int getPoint() {
        return this.point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
}
