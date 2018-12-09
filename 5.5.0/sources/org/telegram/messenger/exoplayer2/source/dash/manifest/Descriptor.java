package org.telegram.messenger.exoplayer2.source.dash.manifest;

import org.telegram.messenger.exoplayer2.util.Util;

public final class Descriptor {
    public final String id;
    public final String schemeIdUri;
    public final String value;

    public Descriptor(String str, String str2, String str3) {
        this.schemeIdUri = str;
        this.value = str2;
        this.id = str3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Descriptor descriptor = (Descriptor) obj;
        return Util.areEqual(this.schemeIdUri, descriptor.schemeIdUri) && Util.areEqual(this.value, descriptor.value) && Util.areEqual(this.id, descriptor.id);
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.value != null ? this.value.hashCode() : 0) + ((this.schemeIdUri != null ? this.schemeIdUri.hashCode() : 0) * 31)) * 31;
        if (this.id != null) {
            i = this.id.hashCode();
        }
        return hashCode + i;
    }
}
