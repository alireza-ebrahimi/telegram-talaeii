package org.telegram.customization.dynamicadapter.viewholder;

import java.util.ArrayList;
import org.telegram.customization.Model.Payment.ReportHelper;

public class ResponseReportHelper {
    ArrayList<ReportHelper> response;

    public ArrayList<ReportHelper> getResponse() {
        return this.response;
    }

    public void setResponse(ArrayList<ReportHelper> response) {
        this.response = response;
    }
}
