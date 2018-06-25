package net.hockeyapp.android.metrics;

import android.os.AsyncTask;
import java.util.Map;
import net.hockeyapp.android.metrics.model.EventData;

class MetricsManager$3 extends AsyncTask<Void, Void, Void> {
    final /* synthetic */ String val$eventName;
    final /* synthetic */ Map val$measurements;
    final /* synthetic */ Map val$properties;

    MetricsManager$3(String str, Map map, Map map2) {
        this.val$eventName = str;
        this.val$properties = map;
        this.val$measurements = map2;
    }

    protected Void doInBackground(Void... params) {
        EventData eventItem = new EventData();
        eventItem.setName(this.val$eventName);
        if (this.val$properties != null) {
            eventItem.setProperties(this.val$properties);
        }
        if (this.val$measurements != null) {
            eventItem.setMeasurements(this.val$measurements);
        }
        MetricsManager.access$000().enqueueData(MetricsManager.createData(eventItem));
        return null;
    }
}
