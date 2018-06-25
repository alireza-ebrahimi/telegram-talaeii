package net.hockeyapp.android.metrics;

import net.hockeyapp.android.PrivateEventManager.Event;
import net.hockeyapp.android.PrivateEventManager.HockeyEventListener;

class MetricsManager$1 implements HockeyEventListener {
    MetricsManager$1() {
    }

    public void onHockeyEvent(Event event) {
        if (event.getType() == 1) {
            MetricsManager.access$000().synchronize();
        }
    }
}
