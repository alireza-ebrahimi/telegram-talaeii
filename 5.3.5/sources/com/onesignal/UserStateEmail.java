package com.onesignal;

class UserStateEmail extends UserState {
    UserStateEmail(String inPersistKey, boolean load) {
        super("email" + inPersistKey, load);
    }

    UserState newInstance(String persistKey) {
        return new UserStateEmail(persistKey, false);
    }

    protected void addDependFields() {
    }

    boolean isSubscribed() {
        return true;
    }
}
