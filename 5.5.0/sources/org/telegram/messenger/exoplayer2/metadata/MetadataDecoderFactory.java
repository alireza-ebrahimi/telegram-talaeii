package org.telegram.messenger.exoplayer2.metadata;

import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.metadata.emsg.EventMessageDecoder;
import org.telegram.messenger.exoplayer2.metadata.id3.Id3Decoder;
import org.telegram.messenger.exoplayer2.metadata.scte35.SpliceInfoDecoder;
import org.telegram.messenger.exoplayer2.util.MimeTypes;

public interface MetadataDecoderFactory {
    public static final MetadataDecoderFactory DEFAULT = new C34971();

    /* renamed from: org.telegram.messenger.exoplayer2.metadata.MetadataDecoderFactory$1 */
    static class C34971 implements MetadataDecoderFactory {
        C34971() {
        }

        public MetadataDecoder createDecoder(Format format) {
            String str = format.sampleMimeType;
            Object obj = -1;
            switch (str.hashCode()) {
                case -1248341703:
                    if (str.equals(MimeTypes.APPLICATION_ID3)) {
                        obj = null;
                        break;
                    }
                    break;
                case 1154383568:
                    if (str.equals(MimeTypes.APPLICATION_EMSG)) {
                        obj = 1;
                        break;
                    }
                    break;
                case 1652648887:
                    if (str.equals(MimeTypes.APPLICATION_SCTE35)) {
                        obj = 2;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    return new Id3Decoder();
                case 1:
                    return new EventMessageDecoder();
                case 2:
                    return new SpliceInfoDecoder();
                default:
                    throw new IllegalArgumentException("Attempted to create decoder for unsupported format");
            }
        }

        public boolean supportsFormat(Format format) {
            String str = format.sampleMimeType;
            return MimeTypes.APPLICATION_ID3.equals(str) || MimeTypes.APPLICATION_EMSG.equals(str) || MimeTypes.APPLICATION_SCTE35.equals(str);
        }
    }

    MetadataDecoder createDecoder(Format format);

    boolean supportsFormat(Format format);
}
