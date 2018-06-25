package org.telegram.customization.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.ir.talaeii.R;

public class ReportDetailsActivity extends Activity {
    View itemDescription;
    View itemIssueTracking;
    View itemPaymentId;
    TextView tvAmount;
    TextView tvDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfupdate);
    }
}
