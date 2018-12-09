package org.telegram.messenger.exoplayer2.source.hls;

import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsMediaPlaylist;

public final class HlsManifest {
    public final HlsMasterPlaylist masterPlaylist;
    public final HlsMediaPlaylist mediaPlaylist;

    HlsManifest(HlsMasterPlaylist hlsMasterPlaylist, HlsMediaPlaylist hlsMediaPlaylist) {
        this.masterPlaylist = hlsMasterPlaylist;
        this.mediaPlaylist = hlsMediaPlaylist;
    }
}
