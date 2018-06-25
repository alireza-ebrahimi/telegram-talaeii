package com.stripe.android.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.annotation.VisibleForTesting;
import org.json.JSONException;
import org.json.JSONObject;

public class StripeJsonUtils {
    static final String EMPTY = "";
    static final String NULL = "null";

    @Nullable
    public static String getString(@NonNull JSONObject jsonObject, @Size(min = 1) @NonNull String fieldName) throws JSONException {
        return nullIfNullOrEmpty(jsonObject.getString(fieldName));
    }

    @Nullable
    public static String optString(@NonNull JSONObject jsonObject, @Size(min = 1) @NonNull String fieldName) {
        return nullIfNullOrEmpty(jsonObject.optString(fieldName));
    }

    @Nullable
    @VisibleForTesting
    static String nullIfNullOrEmpty(@Nullable String possibleNull) {
        return (NULL.equals(possibleNull) || "".equals(possibleNull)) ? null : possibleNull;
    }
}
