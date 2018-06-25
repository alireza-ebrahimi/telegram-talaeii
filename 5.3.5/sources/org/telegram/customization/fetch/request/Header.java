package org.telegram.customization.fetch.request;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class Header {
    private final String header;
    private final String value;

    public Header(@NonNull String header, @Nullable String value) {
        if (header == null) {
            throw new NullPointerException("header cannot be null");
        } else if (header.contains(":")) {
            throw new IllegalArgumentException("header may not contain ':'");
        } else {
            if (value == null) {
                value = "";
            }
            this.header = header;
            this.value = value;
        }
    }

    @NonNull
    public String getHeader() {
        return this.header;
    }

    @NonNull
    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.header + ":" + this.value;
    }
}
