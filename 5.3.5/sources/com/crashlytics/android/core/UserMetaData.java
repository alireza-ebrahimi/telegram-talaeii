package com.crashlytics.android.core;

public class UserMetaData {
    public static final UserMetaData EMPTY = new UserMetaData();
    public final String email;
    public final String id;
    public final String name;

    public UserMetaData() {
        this(null, null, null);
    }

    public UserMetaData(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public boolean isEmpty() {
        return this.id == null && this.name == null && this.email == null;
    }
}
