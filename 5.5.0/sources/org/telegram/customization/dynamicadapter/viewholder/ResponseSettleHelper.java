package org.telegram.customization.dynamicadapter.viewholder;

import java.util.ArrayList;
import org.telegram.customization.Model.Payment.SettleHelper;

public class ResponseSettleHelper {
    ArrayList<SettleHelper> response;

    public ArrayList<SettleHelper> getResponse() {
        return this.response;
    }

    public void setResponse(ArrayList<SettleHelper> arrayList) {
        this.response = arrayList;
    }
}
