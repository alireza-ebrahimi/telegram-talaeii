package com.google.android.gms.location;

import java.util.Comparator;

final class zze implements Comparator<ActivityTransition> {
    zze() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        ActivityTransition activityTransition = (ActivityTransition) obj;
        ActivityTransition activityTransition2 = (ActivityTransition) obj2;
        int activityType = activityTransition.getActivityType();
        int activityType2 = activityTransition2.getActivityType();
        if (activityType != activityType2) {
            return activityType < activityType2 ? -1 : 1;
        } else {
            activityType = activityTransition.getTransitionType();
            activityType2 = activityTransition2.getTransitionType();
            return activityType == activityType2 ? 0 : activityType >= activityType2 ? 1 : -1;
        }
    }
}
