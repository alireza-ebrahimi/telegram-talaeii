package org.telegram.messenger;

import android.location.Location;
import java.util.HashMap;
import org.telegram.messenger.SendMessagesHelper$LocationProvider.LocationProviderDelegate;

class SendMessagesHelper$1 implements LocationProviderDelegate {
    final /* synthetic */ SendMessagesHelper this$0;

    SendMessagesHelper$1(SendMessagesHelper this$0) {
        this.this$0 = this$0;
    }

    public void onLocationAcquired(Location location) {
        SendMessagesHelper.access$000(this.this$0, location);
        SendMessagesHelper.access$100(this.this$0).clear();
    }

    public void onUnableLocationAcquire() {
        HashMap<String, MessageObject> waitingForLocationCopy = new HashMap(SendMessagesHelper.access$100(this.this$0));
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.wasUnableToFindCurrentLocation, new Object[]{waitingForLocationCopy});
        SendMessagesHelper.access$100(this.this$0).clear();
    }
}
