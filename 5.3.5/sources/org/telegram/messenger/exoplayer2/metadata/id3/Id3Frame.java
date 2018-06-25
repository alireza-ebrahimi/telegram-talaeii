package org.telegram.messenger.exoplayer2.metadata.id3;

import org.telegram.messenger.exoplayer2.metadata.Metadata$Entry;
import org.telegram.messenger.exoplayer2.util.Assertions;

public abstract class Id3Frame implements Metadata$Entry {
    public final String id;

    public Id3Frame(String id) {
        this.id = (String) Assertions.checkNotNull(id);
    }

    public int describeContents() {
        return 0;
    }
}
