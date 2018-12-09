package org.telegram.messenger.exoplayer2.source.dash;

import android.net.Uri;
import java.io.InputStream;
import java.util.List;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.drm.DrmInitData;
import org.telegram.messenger.exoplayer2.extractor.ChunkIndex;
import org.telegram.messenger.exoplayer2.extractor.mkv.MatroskaExtractor;
import org.telegram.messenger.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkExtractorWrapper;
import org.telegram.messenger.exoplayer2.source.chunk.InitializationChunk;
import org.telegram.messenger.exoplayer2.source.dash.manifest.AdaptationSet;
import org.telegram.messenger.exoplayer2.source.dash.manifest.DashManifest;
import org.telegram.messenger.exoplayer2.source.dash.manifest.DashManifestParser;
import org.telegram.messenger.exoplayer2.source.dash.manifest.Period;
import org.telegram.messenger.exoplayer2.source.dash.manifest.RangedUri;
import org.telegram.messenger.exoplayer2.source.dash.manifest.Representation;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSourceInputStream;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.util.MimeTypes;

public final class DashUtil {
    private DashUtil() {
    }

    private static Representation getFirstRepresentation(Period period, int i) {
        int adaptationSetIndex = period.getAdaptationSetIndex(i);
        if (adaptationSetIndex == -1) {
            return null;
        }
        List list = ((AdaptationSet) period.adaptationSets.get(adaptationSetIndex)).representations;
        return list.isEmpty() ? null : (Representation) list.get(0);
    }

    public static ChunkIndex loadChunkIndex(DataSource dataSource, Representation representation) {
        ChunkExtractorWrapper loadInitializationData = loadInitializationData(dataSource, representation, true);
        return loadInitializationData == null ? null : (ChunkIndex) loadInitializationData.getSeekMap();
    }

    public static DrmInitData loadDrmInitData(DataSource dataSource, DashManifest dashManifest) {
        if (dashManifest.getPeriodCount() < 1) {
            return null;
        }
        Period period = dashManifest.getPeriod(0);
        int adaptationSetIndex = period.getAdaptationSetIndex(2);
        if (adaptationSetIndex == -1) {
            adaptationSetIndex = period.getAdaptationSetIndex(1);
            if (adaptationSetIndex == -1) {
                return null;
            }
        }
        AdaptationSet adaptationSet = (AdaptationSet) period.adaptationSets.get(adaptationSetIndex);
        if (adaptationSet.representations.isEmpty()) {
            return null;
        }
        Representation representation = (Representation) adaptationSet.representations.get(0);
        DrmInitData drmInitData = representation.format.drmInitData;
        if (drmInitData != null) {
            return drmInitData;
        }
        Format loadSampleFormat = loadSampleFormat(dataSource, representation);
        DrmInitData drmInitData2 = loadSampleFormat != null ? loadSampleFormat.drmInitData : drmInitData;
        return drmInitData2 == null ? null : drmInitData2;
    }

    public static DrmInitData loadDrmInitData(DataSource dataSource, Period period) {
        Representation firstRepresentation = getFirstRepresentation(period, 2);
        if (firstRepresentation == null) {
            firstRepresentation = getFirstRepresentation(period, 1);
            if (firstRepresentation == null) {
                return null;
            }
        }
        DrmInitData drmInitData = firstRepresentation.format.drmInitData;
        if (drmInitData != null) {
            return drmInitData;
        }
        Format loadSampleFormat = loadSampleFormat(dataSource, firstRepresentation);
        return loadSampleFormat == null ? null : loadSampleFormat.drmInitData;
    }

    private static ChunkExtractorWrapper loadInitializationData(DataSource dataSource, Representation representation, boolean z) {
        RangedUri initializationUri = representation.getInitializationUri();
        if (initializationUri == null) {
            return null;
        }
        RangedUri indexUri;
        ChunkExtractorWrapper newWrappedExtractor = newWrappedExtractor(representation.format);
        if (z) {
            indexUri = representation.getIndexUri();
            if (indexUri == null) {
                return null;
            }
            RangedUri attemptMerge = initializationUri.attemptMerge(indexUri, representation.baseUrl);
            if (attemptMerge == null) {
                loadInitializationData(dataSource, representation, newWrappedExtractor, initializationUri);
            } else {
                indexUri = attemptMerge;
            }
        } else {
            indexUri = initializationUri;
        }
        loadInitializationData(dataSource, representation, newWrappedExtractor, indexUri);
        return newWrappedExtractor;
    }

    private static void loadInitializationData(DataSource dataSource, Representation representation, ChunkExtractorWrapper chunkExtractorWrapper, RangedUri rangedUri) {
        new InitializationChunk(dataSource, new DataSpec(rangedUri.resolveUri(representation.baseUrl), rangedUri.start, rangedUri.length, representation.getCacheKey()), representation.format, 0, null, chunkExtractorWrapper).load();
    }

    public static DashManifest loadManifest(DataSource dataSource, String str) {
        InputStream dataSourceInputStream = new DataSourceInputStream(dataSource, new DataSpec(Uri.parse(str), 2));
        try {
            dataSourceInputStream.open();
            DashManifest parse = new DashManifestParser().parse(dataSource.getUri(), dataSourceInputStream);
            return parse;
        } finally {
            dataSourceInputStream.close();
        }
    }

    public static Format loadSampleFormat(DataSource dataSource, Representation representation) {
        ChunkExtractorWrapper loadInitializationData = loadInitializationData(dataSource, representation, false);
        return loadInitializationData == null ? null : loadInitializationData.getSampleFormats()[0];
    }

    private static ChunkExtractorWrapper newWrappedExtractor(Format format) {
        String str = format.containerMimeType;
        Object obj = (str.startsWith(MimeTypes.VIDEO_WEBM) || str.startsWith(MimeTypes.AUDIO_WEBM)) ? 1 : null;
        return new ChunkExtractorWrapper(obj != null ? new MatroskaExtractor() : new FragmentedMp4Extractor(), format);
    }
}
