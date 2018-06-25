package com.google.firebase;

import android.support.annotation.NonNull;

public class FirebaseNetworkException extends FirebaseException {
    public FirebaseNetworkException(@NonNull String str) {
        super(str);
    }
}
