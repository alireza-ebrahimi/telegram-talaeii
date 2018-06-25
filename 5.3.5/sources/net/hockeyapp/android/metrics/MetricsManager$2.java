package net.hockeyapp.android.metrics;

import android.os.AsyncTask;
import net.hockeyapp.android.metrics.model.SessionState;
import net.hockeyapp.android.metrics.model.SessionStateData;

class MetricsManager$2 extends AsyncTask<Void, Void, Void> {
    final /* synthetic */ MetricsManager this$0;
    final /* synthetic */ SessionState val$sessionState;

    MetricsManager$2(MetricsManager this$0, SessionState sessionState) {
        this.this$0 = this$0;
        this.val$sessionState = sessionState;
    }

    protected Void doInBackground(Void... params) {
        SessionStateData sessionItem = new SessionStateData();
        sessionItem.setState(this.val$sessionState);
        MetricsManager.access$000().enqueueData(MetricsManager.createData(sessionItem));
        return null;
    }
}
