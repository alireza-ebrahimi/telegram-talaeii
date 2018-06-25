package org.telegram.messenger.exoplayer2.source.dash.manifest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.telegram.messenger.exoplayer2.util.Util;

public final class Descriptor {
    @Nullable
    public final String id;
    @NonNull
    public final String schemeIdUri;
    @Nullable
    public final String value;

    public Descriptor(@NonNull String schemeIdUri, @Nullable String value, @Nullable String id) {
        this.schemeIdUri = schemeIdUri;
        this.value = value;
        this.id = id;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Descriptor other = (Descriptor) obj;
        if (Util.areEqual(this.schemeIdUri, other.schemeIdUri) && Util.areEqual(this.value, other.value) && Util.areEqual(this.id, other.id)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int hashCode;
        int i = 0;
        if (this.schemeIdUri != null) {
            result = this.schemeIdUri.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.value != null) {
            hashCode = this.value.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (i2 + hashCode) * 31;
        if (this.id != null) {
            i = this.id.hashCode();
        }
        return hashCode + i;
    }
}
