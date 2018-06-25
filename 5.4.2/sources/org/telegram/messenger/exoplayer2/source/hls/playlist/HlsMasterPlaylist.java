package org.telegram.messenger.exoplayer2.source.hls.playlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.util.MimeTypes;

public final class HlsMasterPlaylist extends HlsPlaylist {
    public final List<HlsUrl> audios;
    public final Format muxedAudioFormat;
    public final List<Format> muxedCaptionFormats;
    public final List<HlsUrl> subtitles;
    public final List<HlsUrl> variants;

    public static final class HlsUrl {
        public final Format format;
        public final String url;

        public HlsUrl(String str, Format format) {
            this.url = str;
            this.format = format;
        }

        public static HlsUrl createMediaPlaylistHlsUrl(String str) {
            return new HlsUrl(str, Format.createContainerFormat("0", MimeTypes.APPLICATION_M3U8, null, null, -1, 0, null));
        }
    }

    public HlsMasterPlaylist(String str, List<String> list, List<HlsUrl> list2, List<HlsUrl> list3, List<HlsUrl> list4, Format format, List<Format> list5) {
        super(str, list);
        this.variants = Collections.unmodifiableList(list2);
        this.audios = Collections.unmodifiableList(list3);
        this.subtitles = Collections.unmodifiableList(list4);
        this.muxedAudioFormat = format;
        this.muxedCaptionFormats = list5 != null ? Collections.unmodifiableList(list5) : null;
    }

    private static List<HlsUrl> copyRenditionsList(List<HlsUrl> list, List<String> list2) {
        List<HlsUrl> arrayList = new ArrayList(list2.size());
        for (int i = 0; i < list.size(); i++) {
            HlsUrl hlsUrl = (HlsUrl) list.get(i);
            if (list2.contains(hlsUrl.url)) {
                arrayList.add(hlsUrl);
            }
        }
        return arrayList;
    }

    public static HlsMasterPlaylist createSingleVariantMasterPlaylist(String str) {
        List singletonList = Collections.singletonList(HlsUrl.createMediaPlaylistHlsUrl(str));
        List emptyList = Collections.emptyList();
        return new HlsMasterPlaylist(null, Collections.emptyList(), singletonList, emptyList, emptyList, null, null);
    }

    public HlsMasterPlaylist copy(List<String> list) {
        return new HlsMasterPlaylist(this.baseUri, this.tags, copyRenditionsList(this.variants, list), copyRenditionsList(this.audios, list), copyRenditionsList(this.subtitles, list), this.muxedAudioFormat, this.muxedCaptionFormats);
    }
}
