package com.google.android.gms.wallet;

import android.content.Intent;
import android.support.annotation.NonNull;

public interface AutoResolvableResult {
    void putIntoIntent(@NonNull Intent intent);
}
