package org.telegram.messenger.exoplayer2.source.hls.playlist;

import java.util.Collections;
import java.util.List;

public abstract class HlsPlaylist {
    public final String baseUri;
    public final List<String> tags;

    protected HlsPlaylist(String baseUri, List<String> tags) {
        this.baseUri = baseUri;
        this.tags = Collections.unmodifiableList(tags);
    }
}
