package org.telegram.messenger.exoplayer2.extractor.ts;

import android.util.SparseArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.extractor.ts.TsPayloadReader.EsInfo;
import org.telegram.messenger.exoplayer2.extractor.ts.TsPayloadReader.Factory;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class DefaultTsPayloadReaderFactory implements Factory {
    private static final int DESCRIPTOR_TAG_CAPTION_SERVICE = 134;
    public static final int FLAG_ALLOW_NON_IDR_KEYFRAMES = 1;
    public static final int FLAG_DETECT_ACCESS_UNITS = 8;
    public static final int FLAG_IGNORE_AAC_STREAM = 2;
    public static final int FLAG_IGNORE_H264_STREAM = 4;
    public static final int FLAG_IGNORE_SPLICE_INFO_STREAM = 16;
    public static final int FLAG_OVERRIDE_CAPTION_DESCRIPTORS = 32;
    private final List<Format> closedCaptionFormats;
    private final int flags;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public DefaultTsPayloadReaderFactory() {
        this(0);
    }

    public DefaultTsPayloadReaderFactory(int i) {
        this(i, Collections.emptyList());
    }

    public DefaultTsPayloadReaderFactory(int i, List<Format> list) {
        List singletonList;
        this.flags = i;
        if (!isSet(32) && list.isEmpty()) {
            singletonList = Collections.singletonList(Format.createTextSampleFormat(null, MimeTypes.APPLICATION_CEA608, 0, null));
        }
        this.closedCaptionFormats = singletonList;
    }

    private SeiReader buildSeiReader(EsInfo esInfo) {
        if (isSet(32)) {
            return new SeiReader(this.closedCaptionFormats);
        }
        ParsableByteArray parsableByteArray = new ParsableByteArray(esInfo.descriptorBytes);
        List list = this.closedCaptionFormats;
        while (parsableByteArray.bytesLeft() > 0) {
            int position = parsableByteArray.getPosition() + parsableByteArray.readUnsignedByte();
            if (parsableByteArray.readUnsignedByte() == 134) {
                List arrayList = new ArrayList();
                int readUnsignedByte = parsableByteArray.readUnsignedByte() & 31;
                for (int i = 0; i < readUnsignedByte; i++) {
                    String str;
                    int i2;
                    String readString = parsableByteArray.readString(3);
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    if (((readUnsignedByte2 & 128) != 0 ? 1 : 0) != 0) {
                        str = MimeTypes.APPLICATION_CEA708;
                        i2 = readUnsignedByte2 & 63;
                    } else {
                        str = MimeTypes.APPLICATION_CEA608;
                        i2 = 1;
                    }
                    arrayList.add(Format.createTextSampleFormat(null, str, null, -1, 0, readString, i2, null));
                    parsableByteArray.skipBytes(2);
                }
                list = arrayList;
            }
            parsableByteArray.setPosition(position);
        }
        return new SeiReader(list);
    }

    private boolean isSet(int i) {
        return (this.flags & i) != 0;
    }

    public SparseArray<TsPayloadReader> createInitialPayloadReaders() {
        return new SparseArray();
    }

    public TsPayloadReader createPayloadReader(int i, EsInfo esInfo) {
        switch (i) {
            case 2:
                return new PesReader(new H262Reader());
            case 3:
            case 4:
                return new PesReader(new MpegAudioReader(esInfo.language));
            case 15:
                return !isSet(2) ? new PesReader(new AdtsReader(false, esInfo.language)) : null;
            case 21:
                return new PesReader(new Id3Reader());
            case 27:
                return !isSet(4) ? new PesReader(new H264Reader(buildSeiReader(esInfo), isSet(1), isSet(8))) : null;
            case 36:
                return new PesReader(new H265Reader(buildSeiReader(esInfo)));
            case 89:
                return new PesReader(new DvbSubtitleReader(esInfo.dvbSubtitleInfos));
            case 129:
            case TsExtractor.TS_STREAM_TYPE_E_AC3 /*135*/:
                return new PesReader(new Ac3Reader(esInfo.language));
            case TsExtractor.TS_STREAM_TYPE_HDMV_DTS /*130*/:
            case TsExtractor.TS_STREAM_TYPE_DTS /*138*/:
                return new PesReader(new DtsReader(esInfo.language));
            case 134:
                return !isSet(16) ? new SectionReader(new SpliceInfoSectionReader()) : null;
            default:
                return null;
        }
    }
}
