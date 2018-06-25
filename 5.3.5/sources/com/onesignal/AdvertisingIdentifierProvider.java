package com.onesignal;

import android.content.Context;

interface AdvertisingIdentifierProvider {
    String getIdentifier(Context context);
}
