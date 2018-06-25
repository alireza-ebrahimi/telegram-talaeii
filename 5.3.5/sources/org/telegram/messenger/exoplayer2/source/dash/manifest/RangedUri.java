package org.telegram.messenger.exoplayer2.source.dash.manifest;

import android.net.Uri;
import org.telegram.messenger.exoplayer2.util.UriUtil;

public final class RangedUri {
    private int hashCode;
    public final long length;
    private final String referenceUri;
    public final long start;

    public RangedUri(String referenceUri, long start, long length) {
        if (referenceUri == null) {
            referenceUri = "";
        }
        this.referenceUri = referenceUri;
        this.start = start;
        this.length = length;
    }

    public Uri resolveUri(String baseUri) {
        return UriUtil.resolveToUri(baseUri, this.referenceUri);
    }

    public String resolveUriString(String baseUri) {
        return UriUtil.resolve(baseUri, this.referenceUri);
    }

    public RangedUri attemptMerge(RangedUri other, String baseUri) {
        RangedUri rangedUri = null;
        long j = -1;
        String resolvedUri = resolveUriString(baseUri);
        if (other != null && resolvedUri.equals(other.resolveUriString(baseUri))) {
            long j2;
            if (this.length != -1 && this.start + this.length == other.start) {
                j2 = this.start;
                if (other.length != -1) {
                    j = this.length + other.length;
                }
                rangedUri = new RangedUri(resolvedUri, j2, j);
            } else if (other.length != -1 && other.start + other.length == this.start) {
                j2 = other.start;
                if (this.length != -1) {
                    j = other.length + this.length;
                }
                rangedUri = new RangedUri(resolvedUri, j2, j);
            }
        }
        return rangedUri;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = ((((((int) this.start) + 527) * 31) + ((int) this.length)) * 31) + this.referenceUri.hashCode();
        }
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RangedUri other = (RangedUri) obj;
        if (this.start == other.start && this.length == other.length && this.referenceUri.equals(other.referenceUri)) {
            return true;
        }
        return false;
    }
}
