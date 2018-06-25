package com.onesignal;

class OSSubscriptionChangedInternalObserver {
    OSSubscriptionChangedInternalObserver() {
    }

    public void changed(OSSubscriptionState state) {
        fireChangesToPublicObserver(state);
    }

    static void fireChangesToPublicObserver(OSSubscriptionState state) {
        OSSubscriptionStateChanges stateChanges = new OSSubscriptionStateChanges();
        stateChanges.from = OneSignal.lastSubscriptionState;
        stateChanges.to = (OSSubscriptionState) state.clone();
        if (OneSignal.getSubscriptionStateChangesObserver().notifyChange(stateChanges)) {
            OneSignal.lastSubscriptionState = (OSSubscriptionState) state.clone();
            OneSignal.lastSubscriptionState.persistAsFrom();
        }
    }
}
