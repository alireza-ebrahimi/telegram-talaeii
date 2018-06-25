package com.crashlytics.android.answers;

import io.fabric.sdk.android.Fabric;
import java.util.Locale;
import java.util.Map;

class AnswersEventValidator {
    boolean failFast;
    final int maxNumAttributes;
    final int maxStringLength;

    public AnswersEventValidator(int maxNumAttributes, int maxStringLength, boolean failFast) {
        this.maxNumAttributes = maxNumAttributes;
        this.maxStringLength = maxStringLength;
        this.failFast = failFast;
    }

    public String limitStringLength(String value) {
        if (value.length() <= this.maxStringLength) {
            return value;
        }
        logOrThrowException(new IllegalArgumentException(String.format(Locale.US, "String is too long, truncating to %d characters", new Object[]{Integer.valueOf(this.maxStringLength)})));
        return value.substring(0, this.maxStringLength);
    }

    public boolean isNull(Object object, String paramName) {
        if (object != null) {
            return false;
        }
        logOrThrowException(new NullPointerException(paramName + " must not be null"));
        return true;
    }

    public boolean isFullMap(Map<String, Object> attributeMap, String key) {
        if (attributeMap.size() < this.maxNumAttributes || attributeMap.containsKey(key)) {
            return false;
        }
        logOrThrowException(new IllegalArgumentException(String.format(Locale.US, "Limit of %d attributes reached, skipping attribute", new Object[]{Integer.valueOf(this.maxNumAttributes)})));
        return true;
    }

    private void logOrThrowException(RuntimeException ex) {
        if (this.failFast) {
            throw ex;
        }
        Fabric.getLogger().mo4384e(Answers.TAG, "Invalid user input detected", ex);
    }
}
