package com.google.firebase.internal.api;

import android.support.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Hide;
import com.google.firebase.FirebaseException;

@Hide
@KeepForSdk
public class FirebaseNoSignedInUserException extends FirebaseException {
    public FirebaseNoSignedInUserException(@NonNull String str) {
        super(str);
    }
}
