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

        public static HlsUrl createMediaPlaylistHlsUrl(String url) {
            return new HlsUrl(url, Format.createContainerFormat("0", MimeTypes.APPLICATION_M3U8, null, null, -1, 0, null));
        }

        public HlsUrl(String url, Format format) {
            this.url = url;
            this.format = format;
        }
    }

    public HlsMasterPlaylist(String baseUri, List<String> tags, List<HlsUrl> variants, List<HlsUrl> audios, List<HlsUrl> subtitles, Format muxedAudioFormat, List<Format> muxedCaptionFormats) {
        super(baseUri, tags);
        this.variants = Collections.unmodifiableList(variants);
        this.audios = Collections.unmodifiableList(audios);
        this.subtitles = Collections.unmodifiableList(subtitles);
        this.muxedAudioFormat = muxedAudioFormat;
        this.muxedCaptionFormats = muxedCaptionFormats != null ? Collections.unmodifiableList(muxedCaptionFormats) : null;
    }

    public HlsMasterPlaylist copy(List<String> renditionUrls) {
        return new HlsMasterPlaylist(this.baseUri, this.tags, copyRenditionsList(this.variants, renditionUrls), copyRenditionsList(this.audios, renditionUrls), copyRenditionsList(this.subtitles, renditionUrls), this.muxedAudioFormat, this.muxedCaptionFormats);
    }

    public static HlsMasterPlaylist createSingleVariantMasterPlaylist(String variantUrl) {
        List<HlsUrl> variant = Collections.singletonList(HlsUrl.createMediaPlaylistHlsUrl(variantUrl));
        List<HlsUrl> emptyList = Collections.emptyList();
        return new HlsMasterPlaylist(null, Collections.emptyList(), variant, emptyList, emptyList, null, null);
    }

    private static List<HlsUrl> copyRenditionsList(List<HlsUrl> renditions, List<String> urls) {
        List<HlsUrl> copiedRenditions = new ArrayList(urls.size());
        for (int i = 0; i < renditions.size(); i++) {
            HlsUrl rendition = (HlsUrl) renditions.get(i);
            if (urls.contains(rendition.url)) {
                copiedRenditions.add(rendition);
            }
        }
        return copiedRenditions;
    }
}
