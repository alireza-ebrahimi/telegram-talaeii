package com.onesignal;

class OSPermissionChangedInternalObserver {
    OSPermissionChangedInternalObserver() {
    }

    void changed(OSPermissionState state) {
        handleInternalChanges(state);
        fireChangesToPublicObserver(state);
    }

    static void handleInternalChanges(OSPermissionState state) {
        if (!state.getEnabled()) {
            BadgeCountUpdater.updateCount(0, OneSignal.appContext);
        }
        OneSignalStateSynchronizer.setPermission(OneSignal.areNotificationsEnabledForSubscribedState());
    }

    static void fireChangesToPublicObserver(OSPermissionState state) {
        OSPermissionStateChanges stateChanges = new OSPermissionStateChanges();
        stateChanges.from = OneSignal.lastPermissionState;
        stateChanges.to = (OSPermissionState) state.clone();
        if (OneSignal.getPermissionStateChangesObserver().notifyChange(stateChanges)) {
            OneSignal.lastPermissionState = (OSPermissionState) state.clone();
            OneSignal.lastPermissionState.persistAsFrom();
        }
    }
}
