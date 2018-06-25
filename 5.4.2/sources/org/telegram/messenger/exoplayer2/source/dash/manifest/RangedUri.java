package org.telegram.messenger.exoplayer2.source.dash.manifest;

import android.net.Uri;
import org.telegram.messenger.exoplayer2.util.UriUtil;

public final class RangedUri {
    private int hashCode;
    public final long length;
    private final String referenceUri;
    public final long start;

    public RangedUri(String str, long j, long j2) {
        if (str == null) {
            str = TtmlNode.ANONYMOUS_REGION_ID;
        }
        this.referenceUri = str;
        this.start = j;
        this.length = j2;
    }

    public RangedUri attemptMerge(RangedUri rangedUri, String str) {
        RangedUri rangedUri2 = null;
        long j = -1;
        String resolveUriString = resolveUriString(str);
        if (rangedUri != null && resolveUriString.equals(rangedUri.resolveUriString(str))) {
            long j2;
            if (this.length != -1 && this.start + this.length == rangedUri.start) {
                j2 = this.start;
                if (rangedUri.length != -1) {
                    j = this.length + rangedUri.length;
                }
                rangedUri2 = new RangedUri(resolveUriString, j2, j);
            } else if (rangedUri.length != -1 && rangedUri.start + rangedUri.length == this.start) {
                j2 = rangedUri.start;
                if (this.length != -1) {
                    j = rangedUri.length + this.length;
                }
                rangedUri2 = new RangedUri(resolveUriString, j2, j);
            }
        }
        return rangedUri2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RangedUri rangedUri = (RangedUri) obj;
        return this.start == rangedUri.start && this.length == rangedUri.length && this.referenceUri.equals(rangedUri.referenceUri);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = ((((((int) this.start) + 527) * 31) + ((int) this.length)) * 31) + this.referenceUri.hashCode();
        }
        return this.hashCode;
    }

    public Uri resolveUri(String str) {
        return UriUtil.resolveToUri(str, this.referenceUri);
    }

    public String resolveUriString(String str) {
        return UriUtil.resolve(str, this.referenceUri);
    }
}
