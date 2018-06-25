package org.telegram.messenger.exoplayer2.source.dash;

import android.net.Uri;
import java.io.IOException;
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
    public static DashManifest loadManifest(DataSource dataSource, String manifestUri) throws IOException {
        InputStream inputStream = new DataSourceInputStream(dataSource, new DataSpec(Uri.parse(manifestUri), 2));
        try {
            inputStream.open();
            DashManifest parse = new DashManifestParser().parse(dataSource.getUri(), inputStream);
            return parse;
        } finally {
            inputStream.close();
        }
    }

    public static DrmInitData loadDrmInitData(DataSource dataSource, DashManifest dashManifest) throws IOException, InterruptedException {
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
        Format sampleFormat = loadSampleFormat(dataSource, representation);
        if (sampleFormat != null) {
            drmInitData = sampleFormat.drmInitData;
        }
        if (drmInitData == null) {
            return null;
        }
        return drmInitData;
    }

    public static DrmInitData loadDrmInitData(DataSource dataSource, Period period) throws IOException, InterruptedException {
        Representation representation = getFirstRepresentation(period, 2);
        if (representation == null) {
            representation = getFirstRepresentation(period, 1);
            if (representation == null) {
                return null;
            }
        }
        DrmInitData drmInitData = representation.format.drmInitData;
        if (drmInitData != null) {
            return drmInitData;
        }
        Format sampleFormat = loadSampleFormat(dataSource, representation);
        if (sampleFormat != null) {
            return sampleFormat.drmInitData;
        }
        return null;
    }

    public static Format loadSampleFormat(DataSource dataSource, Representation representation) throws IOException, InterruptedException {
        ChunkExtractorWrapper extractorWrapper = loadInitializationData(dataSource, representation, false);
        return extractorWrapper == null ? null : extractorWrapper.getSampleFormats()[0];
    }

    public static ChunkIndex loadChunkIndex(DataSource dataSource, Representation representation) throws IOException, InterruptedException {
        ChunkExtractorWrapper extractorWrapper = loadInitializationData(dataSource, representation, true);
        return extractorWrapper == null ? null : (ChunkIndex) extractorWrapper.getSeekMap();
    }

    private static ChunkExtractorWrapper loadInitializationData(DataSource dataSource, Representation representation, boolean loadIndex) throws IOException, InterruptedException {
        RangedUri initializationUri = representation.getInitializationUri();
        if (initializationUri == null) {
            return null;
        }
        RangedUri requestUri;
        ChunkExtractorWrapper extractorWrapper = newWrappedExtractor(representation.format);
        if (loadIndex) {
            RangedUri indexUri = representation.getIndexUri();
            if (indexUri == null) {
                return null;
            }
            requestUri = initializationUri.attemptMerge(indexUri, representation.baseUrl);
            if (requestUri == null) {
                loadInitializationData(dataSource, representation, extractorWrapper, initializationUri);
                requestUri = indexUri;
            }
        } else {
            requestUri = initializationUri;
        }
        loadInitializationData(dataSource, representation, extractorWrapper, requestUri);
        return extractorWrapper;
    }

    private static void loadInitializationData(DataSource dataSource, Representation representation, ChunkExtractorWrapper extractorWrapper, RangedUri requestUri) throws IOException, InterruptedException {
        new InitializationChunk(dataSource, new DataSpec(requestUri.resolveUri(representation.baseUrl), requestUri.start, requestUri.length, representation.getCacheKey()), representation.format, 0, null, extractorWrapper).load();
    }

    private static ChunkExtractorWrapper newWrappedExtractor(Format format) {
        String mimeType = format.containerMimeType;
        boolean isWebm = mimeType.startsWith(MimeTypes.VIDEO_WEBM) || mimeType.startsWith(MimeTypes.AUDIO_WEBM);
        return new ChunkExtractorWrapper(isWebm ? new MatroskaExtractor() : new FragmentedMp4Extractor(), format);
    }

    private static Representation getFirstRepresentation(Period period, int type) {
        int index = period.getAdaptationSetIndex(type);
        if (index == -1) {
            return null;
        }
        List<Representation> representations = ((AdaptationSet) period.adaptationSets.get(index)).representations;
        return representations.isEmpty() ? null : (Representation) representations.get(0);
    }

    private DashUtil() {
    }
}
