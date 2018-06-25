package com.onesignal;

class OSEmailSubscriptionChangedInternalObserver {
    OSEmailSubscriptionChangedInternalObserver() {
    }

    void changed(OSEmailSubscriptionState state) {
        fireChangesToPublicObserver(state);
    }

    static void fireChangesToPublicObserver(OSEmailSubscriptionState state) {
        OSEmailSubscriptionStateChanges stateChanges = new OSEmailSubscriptionStateChanges();
        stateChanges.from = OneSignal.lastEmailSubscriptionState;
        stateChanges.to = (OSEmailSubscriptionState) state.clone();
        if (OneSignal.getEmailSubscriptionStateChangesObserver().notifyChange(stateChanges)) {
            OneSignal.lastEmailSubscriptionState = (OSEmailSubscriptionState) state.clone();
            OneSignal.lastEmailSubscriptionState.persistAsFrom();
        }
    }
}
