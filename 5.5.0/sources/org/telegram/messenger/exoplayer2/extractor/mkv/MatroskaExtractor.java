package org.telegram.messenger.exoplayer2.extractor.mkv;

import android.util.Log;
import android.util.SparseArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.drm.DrmInitData;
import org.telegram.messenger.exoplayer2.drm.DrmInitData.SchemeData;
import org.telegram.messenger.exoplayer2.extractor.ChunkIndex;
import org.telegram.messenger.exoplayer2.extractor.Extractor;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorsFactory;
import org.telegram.messenger.exoplayer2.extractor.PositionHolder;
import org.telegram.messenger.exoplayer2.extractor.SeekMap;
import org.telegram.messenger.exoplayer2.extractor.SeekMap.Unseekable;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput.CryptoData;
import org.telegram.messenger.exoplayer2.util.LongArray;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.NalUnitUtil;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.Util;
import org.telegram.messenger.exoplayer2.video.AvcConfig;
import org.telegram.messenger.exoplayer2.video.ColorInfo;
import org.telegram.messenger.exoplayer2.video.HevcConfig;

public final class MatroskaExtractor implements Extractor {
    private static final int BLOCK_STATE_DATA = 2;
    private static final int BLOCK_STATE_HEADER = 1;
    private static final int BLOCK_STATE_START = 0;
    private static final String CODEC_ID_AAC = "A_AAC";
    private static final String CODEC_ID_AC3 = "A_AC3";
    private static final String CODEC_ID_ACM = "A_MS/ACM";
    private static final String CODEC_ID_ASS = "S_TEXT/ASS";
    private static final String CODEC_ID_DTS = "A_DTS";
    private static final String CODEC_ID_DTS_EXPRESS = "A_DTS/EXPRESS";
    private static final String CODEC_ID_DTS_LOSSLESS = "A_DTS/LOSSLESS";
    private static final String CODEC_ID_DVBSUB = "S_DVBSUB";
    private static final String CODEC_ID_E_AC3 = "A_EAC3";
    private static final String CODEC_ID_FLAC = "A_FLAC";
    private static final String CODEC_ID_FOURCC = "V_MS/VFW/FOURCC";
    private static final String CODEC_ID_H264 = "V_MPEG4/ISO/AVC";
    private static final String CODEC_ID_H265 = "V_MPEGH/ISO/HEVC";
    private static final String CODEC_ID_MP2 = "A_MPEG/L2";
    private static final String CODEC_ID_MP3 = "A_MPEG/L3";
    private static final String CODEC_ID_MPEG2 = "V_MPEG2";
    private static final String CODEC_ID_MPEG4_AP = "V_MPEG4/ISO/AP";
    private static final String CODEC_ID_MPEG4_ASP = "V_MPEG4/ISO/ASP";
    private static final String CODEC_ID_MPEG4_SP = "V_MPEG4/ISO/SP";
    private static final String CODEC_ID_OPUS = "A_OPUS";
    private static final String CODEC_ID_PCM_INT_LIT = "A_PCM/INT/LIT";
    private static final String CODEC_ID_PGS = "S_HDMV/PGS";
    private static final String CODEC_ID_SUBRIP = "S_TEXT/UTF8";
    private static final String CODEC_ID_THEORA = "V_THEORA";
    private static final String CODEC_ID_TRUEHD = "A_TRUEHD";
    private static final String CODEC_ID_VOBSUB = "S_VOBSUB";
    private static final String CODEC_ID_VORBIS = "A_VORBIS";
    private static final String CODEC_ID_VP8 = "V_VP8";
    private static final String CODEC_ID_VP9 = "V_VP9";
    private static final String DOC_TYPE_MATROSKA = "matroska";
    private static final String DOC_TYPE_WEBM = "webm";
    private static final int ENCRYPTION_IV_SIZE = 8;
    public static final ExtractorsFactory FACTORY = new C34801();
    public static final int FLAG_DISABLE_SEEK_FOR_CUES = 1;
    private static final int FOURCC_COMPRESSION_VC1 = 826496599;
    private static final int ID_AUDIO = 225;
    private static final int ID_AUDIO_BIT_DEPTH = 25188;
    private static final int ID_BLOCK = 161;
    private static final int ID_BLOCK_DURATION = 155;
    private static final int ID_BLOCK_GROUP = 160;
    private static final int ID_CHANNELS = 159;
    private static final int ID_CLUSTER = 524531317;
    private static final int ID_CODEC_DELAY = 22186;
    private static final int ID_CODEC_ID = 134;
    private static final int ID_CODEC_PRIVATE = 25506;
    private static final int ID_COLOUR = 21936;
    private static final int ID_COLOUR_PRIMARIES = 21947;
    private static final int ID_COLOUR_RANGE = 21945;
    private static final int ID_COLOUR_TRANSFER = 21946;
    private static final int ID_CONTENT_COMPRESSION = 20532;
    private static final int ID_CONTENT_COMPRESSION_ALGORITHM = 16980;
    private static final int ID_CONTENT_COMPRESSION_SETTINGS = 16981;
    private static final int ID_CONTENT_ENCODING = 25152;
    private static final int ID_CONTENT_ENCODINGS = 28032;
    private static final int ID_CONTENT_ENCODING_ORDER = 20529;
    private static final int ID_CONTENT_ENCODING_SCOPE = 20530;
    private static final int ID_CONTENT_ENCRYPTION = 20533;
    private static final int ID_CONTENT_ENCRYPTION_AES_SETTINGS = 18407;
    private static final int ID_CONTENT_ENCRYPTION_AES_SETTINGS_CIPHER_MODE = 18408;
    private static final int ID_CONTENT_ENCRYPTION_ALGORITHM = 18401;
    private static final int ID_CONTENT_ENCRYPTION_KEY_ID = 18402;
    private static final int ID_CUES = 475249515;
    private static final int ID_CUE_CLUSTER_POSITION = 241;
    private static final int ID_CUE_POINT = 187;
    private static final int ID_CUE_TIME = 179;
    private static final int ID_CUE_TRACK_POSITIONS = 183;
    private static final int ID_DEFAULT_DURATION = 2352003;
    private static final int ID_DISPLAY_HEIGHT = 21690;
    private static final int ID_DISPLAY_UNIT = 21682;
    private static final int ID_DISPLAY_WIDTH = 21680;
    private static final int ID_DOC_TYPE = 17026;
    private static final int ID_DOC_TYPE_READ_VERSION = 17029;
    private static final int ID_DURATION = 17545;
    private static final int ID_EBML = 440786851;
    private static final int ID_EBML_READ_VERSION = 17143;
    private static final int ID_FLAG_DEFAULT = 136;
    private static final int ID_FLAG_FORCED = 21930;
    private static final int ID_INFO = 357149030;
    private static final int ID_LANGUAGE = 2274716;
    private static final int ID_LUMNINANCE_MAX = 21977;
    private static final int ID_LUMNINANCE_MIN = 21978;
    private static final int ID_MASTERING_METADATA = 21968;
    private static final int ID_MAX_CLL = 21948;
    private static final int ID_MAX_FALL = 21949;
    private static final int ID_PIXEL_HEIGHT = 186;
    private static final int ID_PIXEL_WIDTH = 176;
    private static final int ID_PRIMARY_B_CHROMATICITY_X = 21973;
    private static final int ID_PRIMARY_B_CHROMATICITY_Y = 21974;
    private static final int ID_PRIMARY_G_CHROMATICITY_X = 21971;
    private static final int ID_PRIMARY_G_CHROMATICITY_Y = 21972;
    private static final int ID_PRIMARY_R_CHROMATICITY_X = 21969;
    private static final int ID_PRIMARY_R_CHROMATICITY_Y = 21970;
    private static final int ID_PROJECTION = 30320;
    private static final int ID_PROJECTION_PRIVATE = 30322;
    private static final int ID_REFERENCE_BLOCK = 251;
    private static final int ID_SAMPLING_FREQUENCY = 181;
    private static final int ID_SEEK = 19899;
    private static final int ID_SEEK_HEAD = 290298740;
    private static final int ID_SEEK_ID = 21419;
    private static final int ID_SEEK_POSITION = 21420;
    private static final int ID_SEEK_PRE_ROLL = 22203;
    private static final int ID_SEGMENT = 408125543;
    private static final int ID_SEGMENT_INFO = 357149030;
    private static final int ID_SIMPLE_BLOCK = 163;
    private static final int ID_STEREO_MODE = 21432;
    private static final int ID_TIMECODE_SCALE = 2807729;
    private static final int ID_TIME_CODE = 231;
    private static final int ID_TRACKS = 374648427;
    private static final int ID_TRACK_ENTRY = 174;
    private static final int ID_TRACK_NUMBER = 215;
    private static final int ID_TRACK_TYPE = 131;
    private static final int ID_VIDEO = 224;
    private static final int ID_WHITE_POINT_CHROMATICITY_X = 21975;
    private static final int ID_WHITE_POINT_CHROMATICITY_Y = 21976;
    private static final int LACING_EBML = 3;
    private static final int LACING_FIXED_SIZE = 2;
    private static final int LACING_NONE = 0;
    private static final int LACING_XIPH = 1;
    private static final int OPUS_MAX_INPUT_SIZE = 5760;
    private static final byte[] SSA_DIALOGUE_FORMAT = Util.getUtf8Bytes("Format: Start, End, ReadOrder, Layer, Style, Name, MarginL, MarginR, MarginV, Effect, Text");
    private static final byte[] SSA_PREFIX = new byte[]{(byte) 68, (byte) 105, (byte) 97, (byte) 108, (byte) 111, (byte) 103, (byte) 117, (byte) 101, (byte) 58, (byte) 32, (byte) 48, (byte) 58, (byte) 48, (byte) 48, (byte) 58, (byte) 48, (byte) 48, (byte) 58, (byte) 48, (byte) 48, (byte) 44, (byte) 48, (byte) 58, (byte) 48, (byte) 48, (byte) 58, (byte) 48, (byte) 48, (byte) 58, (byte) 48, (byte) 48, (byte) 44};
    private static final int SSA_PREFIX_END_TIMECODE_OFFSET = 21;
    private static final byte[] SSA_TIMECODE_EMPTY = new byte[]{(byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32};
    private static final String SSA_TIMECODE_FORMAT = "%01d:%02d:%02d:%02d";
    private static long SSA_TIMECODE_LAST_VALUE_SCALING_FACTOR = 10000;
    private static final byte[] SUBRIP_PREFIX = new byte[]{(byte) 49, (byte) 10, (byte) 48, (byte) 48, (byte) 58, (byte) 48, (byte) 48, (byte) 58, (byte) 48, (byte) 48, (byte) 44, (byte) 48, (byte) 48, (byte) 48, (byte) 32, (byte) 45, (byte) 45, (byte) 62, (byte) 32, (byte) 48, (byte) 48, (byte) 58, (byte) 48, (byte) 48, (byte) 58, (byte) 48, (byte) 48, (byte) 44, (byte) 48, (byte) 48, (byte) 48, (byte) 10};
    private static final int SUBRIP_PREFIX_END_TIMECODE_OFFSET = 19;
    private static final byte[] SUBRIP_TIMECODE_EMPTY = new byte[]{(byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32};
    private static final String SUBRIP_TIMECODE_FORMAT = "%02d:%02d:%02d,%03d";
    private static long SUBRIP_TIMECODE_LAST_VALUE_SCALING_FACTOR = 1000;
    private static final String TAG = "MatroskaExtractor";
    private static final int TRACK_TYPE_AUDIO = 2;
    private static final int UNSET_ENTRY_ID = -1;
    private static final int VORBIS_MAX_INPUT_SIZE = 8192;
    private static final int WAVE_FORMAT_EXTENSIBLE = 65534;
    private static final int WAVE_FORMAT_PCM = 1;
    private static final int WAVE_FORMAT_SIZE = 18;
    private static final UUID WAVE_SUBFORMAT_PCM = new UUID(72057594037932032L, -9223371306706625679L);
    private long blockDurationUs;
    private int blockFlags;
    private int blockLacingSampleCount;
    private int blockLacingSampleIndex;
    private int[] blockLacingSampleSizes;
    private int blockState;
    private long blockTimeUs;
    private int blockTrackNumber;
    private int blockTrackNumberLength;
    private long clusterTimecodeUs;
    private LongArray cueClusterPositions;
    private LongArray cueTimesUs;
    private long cuesContentPosition;
    private Track currentTrack;
    private long durationTimecode;
    private long durationUs;
    private final ParsableByteArray encryptionInitializationVector;
    private final ParsableByteArray encryptionSubsampleData;
    private ByteBuffer encryptionSubsampleDataBuffer;
    private ExtractorOutput extractorOutput;
    private final ParsableByteArray nalLength;
    private final ParsableByteArray nalStartCode;
    private final EbmlReader reader;
    private int sampleBytesRead;
    private int sampleBytesWritten;
    private int sampleCurrentNalBytesRemaining;
    private boolean sampleEncodingHandled;
    private boolean sampleInitializationVectorRead;
    private int samplePartitionCount;
    private boolean samplePartitionCountRead;
    private boolean sampleRead;
    private boolean sampleSeenReferenceBlock;
    private byte sampleSignalByte;
    private boolean sampleSignalByteRead;
    private final ParsableByteArray sampleStrippedBytes;
    private final ParsableByteArray scratch;
    private int seekEntryId;
    private final ParsableByteArray seekEntryIdBytes;
    private long seekEntryPosition;
    private boolean seekForCues;
    private final boolean seekForCuesEnabled;
    private long seekPositionAfterBuildingCues;
    private boolean seenClusterPositionForCurrentCuePoint;
    private long segmentContentPosition;
    private long segmentContentSize;
    private boolean sentSeekMap;
    private final ParsableByteArray subtitleSample;
    private long timecodeScale;
    private final SparseArray<Track> tracks;
    private final VarintReader varintReader;
    private final ParsableByteArray vorbisNumPageSamples;

    /* renamed from: org.telegram.messenger.exoplayer2.extractor.mkv.MatroskaExtractor$1 */
    static class C34801 implements ExtractorsFactory {
        C34801() {
        }

        public Extractor[] createExtractors() {
            return new Extractor[]{new MatroskaExtractor()};
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    private final class InnerEbmlReaderOutput implements EbmlReaderOutput {
        private InnerEbmlReaderOutput() {
        }

        public void binaryElement(int i, int i2, ExtractorInput extractorInput) {
            MatroskaExtractor.this.binaryElement(i, i2, extractorInput);
        }

        public void endMasterElement(int i) {
            MatroskaExtractor.this.endMasterElement(i);
        }

        public void floatElement(int i, double d) {
            MatroskaExtractor.this.floatElement(i, d);
        }

        public int getElementType(int i) {
            return MatroskaExtractor.this.getElementType(i);
        }

        public void integerElement(int i, long j) {
            MatroskaExtractor.this.integerElement(i, j);
        }

        public boolean isLevel1Element(int i) {
            return MatroskaExtractor.this.isLevel1Element(i);
        }

        public void startMasterElement(int i, long j, long j2) {
            MatroskaExtractor.this.startMasterElement(i, j, j2);
        }

        public void stringElement(int i, String str) {
            MatroskaExtractor.this.stringElement(i, str);
        }
    }

    private static final class Track {
        private static final int DEFAULT_MAX_CLL = 1000;
        private static final int DEFAULT_MAX_FALL = 200;
        private static final int DISPLAY_UNIT_PIXELS = 0;
        private static final int MAX_CHROMATICITY = 50000;
        public int audioBitDepth;
        public int channelCount;
        public long codecDelayNs;
        public String codecId;
        public byte[] codecPrivate;
        public int colorRange;
        public int colorSpace;
        public int colorTransfer;
        public CryptoData cryptoData;
        public int defaultSampleDurationNs;
        public int displayHeight;
        public int displayUnit;
        public int displayWidth;
        public DrmInitData drmInitData;
        public boolean flagDefault;
        public boolean flagForced;
        public boolean hasColorInfo;
        public boolean hasContentEncryption;
        public int height;
        private String language;
        public int maxContentLuminance;
        public int maxFrameAverageLuminance;
        public float maxMasteringLuminance;
        public float minMasteringLuminance;
        public int nalUnitLengthFieldLength;
        public int number;
        public TrackOutput output;
        public float primaryBChromaticityX;
        public float primaryBChromaticityY;
        public float primaryGChromaticityX;
        public float primaryGChromaticityY;
        public float primaryRChromaticityX;
        public float primaryRChromaticityY;
        public byte[] projectionData;
        public int sampleRate;
        public byte[] sampleStrippedBytes;
        public long seekPreRollNs;
        public int stereoMode;
        public int type;
        public float whitePointChromaticityX;
        public float whitePointChromaticityY;
        public int width;

        private Track() {
            this.width = -1;
            this.height = -1;
            this.displayWidth = -1;
            this.displayHeight = -1;
            this.displayUnit = 0;
            this.projectionData = null;
            this.stereoMode = -1;
            this.hasColorInfo = false;
            this.colorSpace = -1;
            this.colorTransfer = -1;
            this.colorRange = -1;
            this.maxContentLuminance = 1000;
            this.maxFrameAverageLuminance = 200;
            this.primaryRChromaticityX = -1.0f;
            this.primaryRChromaticityY = -1.0f;
            this.primaryGChromaticityX = -1.0f;
            this.primaryGChromaticityY = -1.0f;
            this.primaryBChromaticityX = -1.0f;
            this.primaryBChromaticityY = -1.0f;
            this.whitePointChromaticityX = -1.0f;
            this.whitePointChromaticityY = -1.0f;
            this.maxMasteringLuminance = -1.0f;
            this.minMasteringLuminance = -1.0f;
            this.channelCount = 1;
            this.audioBitDepth = -1;
            this.sampleRate = 8000;
            this.codecDelayNs = 0;
            this.seekPreRollNs = 0;
            this.flagDefault = true;
            this.language = "eng";
        }

        private byte[] getHdrStaticInfo() {
            if (this.primaryRChromaticityX == -1.0f || this.primaryRChromaticityY == -1.0f || this.primaryGChromaticityX == -1.0f || this.primaryGChromaticityY == -1.0f || this.primaryBChromaticityX == -1.0f || this.primaryBChromaticityY == -1.0f || this.whitePointChromaticityX == -1.0f || this.whitePointChromaticityY == -1.0f || this.maxMasteringLuminance == -1.0f || this.minMasteringLuminance == -1.0f) {
                return null;
            }
            byte[] bArr = new byte[25];
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            wrap.put((byte) 0);
            wrap.putShort((short) ((int) ((this.primaryRChromaticityX * 50000.0f) + 0.5f)));
            wrap.putShort((short) ((int) ((this.primaryRChromaticityY * 50000.0f) + 0.5f)));
            wrap.putShort((short) ((int) ((this.primaryGChromaticityX * 50000.0f) + 0.5f)));
            wrap.putShort((short) ((int) ((this.primaryGChromaticityY * 50000.0f) + 0.5f)));
            wrap.putShort((short) ((int) ((this.primaryBChromaticityX * 50000.0f) + 0.5f)));
            wrap.putShort((short) ((int) ((this.primaryBChromaticityY * 50000.0f) + 0.5f)));
            wrap.putShort((short) ((int) ((this.whitePointChromaticityX * 50000.0f) + 0.5f)));
            wrap.putShort((short) ((int) ((this.whitePointChromaticityY * 50000.0f) + 0.5f)));
            wrap.putShort((short) ((int) (this.maxMasteringLuminance + 0.5f)));
            wrap.putShort((short) ((int) (this.minMasteringLuminance + 0.5f)));
            wrap.putShort((short) this.maxContentLuminance);
            wrap.putShort((short) this.maxFrameAverageLuminance);
            return bArr;
        }

        private static List<byte[]> parseFourCcVc1Private(ParsableByteArray parsableByteArray) {
            try {
                parsableByteArray.skipBytes(16);
                if (parsableByteArray.readLittleEndianUnsignedInt() != 826496599) {
                    return null;
                }
                int position = parsableByteArray.getPosition() + 20;
                byte[] bArr = parsableByteArray.data;
                while (position < bArr.length - 4) {
                    if (bArr[position] == (byte) 0 && bArr[position + 1] == (byte) 0 && bArr[position + 2] == (byte) 1 && bArr[position + 3] == (byte) 15) {
                        return Collections.singletonList(Arrays.copyOfRange(bArr, position, bArr.length));
                    }
                    position++;
                }
                throw new ParserException("Failed to find FourCC VC1 initialization data");
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ParserException("Error parsing FourCC VC1 codec private");
            }
        }

        private static boolean parseMsAcmCodecPrivate(ParsableByteArray parsableByteArray) {
            try {
                int readLittleEndianUnsignedShort = parsableByteArray.readLittleEndianUnsignedShort();
                if (readLittleEndianUnsignedShort == 1) {
                    return true;
                }
                if (readLittleEndianUnsignedShort != MatroskaExtractor.WAVE_FORMAT_EXTENSIBLE) {
                    return false;
                }
                parsableByteArray.setPosition(24);
                return parsableByteArray.readLong() == MatroskaExtractor.WAVE_SUBFORMAT_PCM.getMostSignificantBits() && parsableByteArray.readLong() == MatroskaExtractor.WAVE_SUBFORMAT_PCM.getLeastSignificantBits();
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ParserException("Error parsing MS/ACM codec private");
            }
        }

        private static List<byte[]> parseVorbisCodecPrivate(byte[] bArr) {
            int i = 0;
            try {
                if (bArr[0] != (byte) 2) {
                    throw new ParserException("Error parsing vorbis codec private");
                }
                int i2 = 0;
                int i3 = 1;
                while (bArr[i3] == (byte) -1) {
                    i3++;
                    i2 += 255;
                }
                int i4 = i3 + 1;
                i2 += bArr[i3];
                while (bArr[i4] == (byte) -1) {
                    i += 255;
                    i4++;
                }
                i3 = i4 + 1;
                i += bArr[i4];
                if (bArr[i3] != (byte) 1) {
                    throw new ParserException("Error parsing vorbis codec private");
                }
                Object obj = new byte[i2];
                System.arraycopy(bArr, i3, obj, 0, i2);
                i2 += i3;
                if (bArr[i2] != (byte) 3) {
                    throw new ParserException("Error parsing vorbis codec private");
                }
                i += i2;
                if (bArr[i] != (byte) 5) {
                    throw new ParserException("Error parsing vorbis codec private");
                }
                Object obj2 = new byte[(bArr.length - i)];
                System.arraycopy(bArr, i, obj2, 0, bArr.length - i);
                List<byte[]> arrayList = new ArrayList(2);
                arrayList.add(obj);
                arrayList.add(obj2);
                return arrayList;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ParserException("Error parsing vorbis codec private");
            }
        }

        public void initializeOutput(ExtractorOutput extractorOutput, int i) {
            Format createAudioSampleFormat;
            int i2;
            int i3 = -1;
            int i4 = -1;
            List list = null;
            String str = this.codecId;
            Object obj = -1;
            switch (str.hashCode()) {
                case -2095576542:
                    if (str.equals(MatroskaExtractor.CODEC_ID_MPEG4_AP)) {
                        obj = 5;
                        break;
                    }
                    break;
                case -2095575984:
                    if (str.equals(MatroskaExtractor.CODEC_ID_MPEG4_SP)) {
                        obj = 3;
                        break;
                    }
                    break;
                case -1985379776:
                    if (str.equals(MatroskaExtractor.CODEC_ID_ACM)) {
                        obj = 22;
                        break;
                    }
                    break;
                case -1784763192:
                    if (str.equals(MatroskaExtractor.CODEC_ID_TRUEHD)) {
                        obj = 17;
                        break;
                    }
                    break;
                case -1730367663:
                    if (str.equals(MatroskaExtractor.CODEC_ID_VORBIS)) {
                        obj = 10;
                        break;
                    }
                    break;
                case -1482641358:
                    if (str.equals(MatroskaExtractor.CODEC_ID_MP2)) {
                        obj = 13;
                        break;
                    }
                    break;
                case -1482641357:
                    if (str.equals(MatroskaExtractor.CODEC_ID_MP3)) {
                        obj = 14;
                        break;
                    }
                    break;
                case -1373388978:
                    if (str.equals(MatroskaExtractor.CODEC_ID_FOURCC)) {
                        obj = 8;
                        break;
                    }
                    break;
                case -933872740:
                    if (str.equals(MatroskaExtractor.CODEC_ID_DVBSUB)) {
                        obj = 28;
                        break;
                    }
                    break;
                case -538363189:
                    if (str.equals(MatroskaExtractor.CODEC_ID_MPEG4_ASP)) {
                        obj = 4;
                        break;
                    }
                    break;
                case -538363109:
                    if (str.equals(MatroskaExtractor.CODEC_ID_H264)) {
                        obj = 6;
                        break;
                    }
                    break;
                case -425012669:
                    if (str.equals(MatroskaExtractor.CODEC_ID_VOBSUB)) {
                        obj = 26;
                        break;
                    }
                    break;
                case -356037306:
                    if (str.equals(MatroskaExtractor.CODEC_ID_DTS_LOSSLESS)) {
                        obj = 20;
                        break;
                    }
                    break;
                case 62923557:
                    if (str.equals(MatroskaExtractor.CODEC_ID_AAC)) {
                        obj = 12;
                        break;
                    }
                    break;
                case 62923603:
                    if (str.equals(MatroskaExtractor.CODEC_ID_AC3)) {
                        obj = 15;
                        break;
                    }
                    break;
                case 62927045:
                    if (str.equals(MatroskaExtractor.CODEC_ID_DTS)) {
                        obj = 18;
                        break;
                    }
                    break;
                case 82338133:
                    if (str.equals(MatroskaExtractor.CODEC_ID_VP8)) {
                        obj = null;
                        break;
                    }
                    break;
                case 82338134:
                    if (str.equals(MatroskaExtractor.CODEC_ID_VP9)) {
                        obj = 1;
                        break;
                    }
                    break;
                case 99146302:
                    if (str.equals(MatroskaExtractor.CODEC_ID_PGS)) {
                        obj = 27;
                        break;
                    }
                    break;
                case 444813526:
                    if (str.equals(MatroskaExtractor.CODEC_ID_THEORA)) {
                        obj = 9;
                        break;
                    }
                    break;
                case 542569478:
                    if (str.equals(MatroskaExtractor.CODEC_ID_DTS_EXPRESS)) {
                        obj = 19;
                        break;
                    }
                    break;
                case 725957860:
                    if (str.equals(MatroskaExtractor.CODEC_ID_PCM_INT_LIT)) {
                        obj = 23;
                        break;
                    }
                    break;
                case 738597099:
                    if (str.equals(MatroskaExtractor.CODEC_ID_ASS)) {
                        obj = 25;
                        break;
                    }
                    break;
                case 855502857:
                    if (str.equals(MatroskaExtractor.CODEC_ID_H265)) {
                        obj = 7;
                        break;
                    }
                    break;
                case 1422270023:
                    if (str.equals(MatroskaExtractor.CODEC_ID_SUBRIP)) {
                        obj = 24;
                        break;
                    }
                    break;
                case 1809237540:
                    if (str.equals(MatroskaExtractor.CODEC_ID_MPEG2)) {
                        obj = 2;
                        break;
                    }
                    break;
                case 1950749482:
                    if (str.equals(MatroskaExtractor.CODEC_ID_E_AC3)) {
                        obj = 16;
                        break;
                    }
                    break;
                case 1950789798:
                    if (str.equals(MatroskaExtractor.CODEC_ID_FLAC)) {
                        obj = 21;
                        break;
                    }
                    break;
                case 1951062397:
                    if (str.equals(MatroskaExtractor.CODEC_ID_OPUS)) {
                        obj = 11;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    str = MimeTypes.VIDEO_VP8;
                    break;
                case 1:
                    str = MimeTypes.VIDEO_VP9;
                    break;
                case 2:
                    str = MimeTypes.VIDEO_MPEG2;
                    break;
                case 3:
                case 4:
                case 5:
                    str = MimeTypes.VIDEO_MP4V;
                    list = this.codecPrivate == null ? null : Collections.singletonList(this.codecPrivate);
                    break;
                case 6:
                    str = "video/avc";
                    AvcConfig parse = AvcConfig.parse(new ParsableByteArray(this.codecPrivate));
                    list = parse.initializationData;
                    this.nalUnitLengthFieldLength = parse.nalUnitLengthFieldLength;
                    break;
                case 7:
                    str = MimeTypes.VIDEO_H265;
                    HevcConfig parse2 = HevcConfig.parse(new ParsableByteArray(this.codecPrivate));
                    list = parse2.initializationData;
                    this.nalUnitLengthFieldLength = parse2.nalUnitLengthFieldLength;
                    break;
                case 8:
                    list = parseFourCcVc1Private(new ParsableByteArray(this.codecPrivate));
                    if (list == null) {
                        Log.w(MatroskaExtractor.TAG, "Unsupported FourCC. Setting mimeType to video/x-unknown");
                        str = MimeTypes.VIDEO_UNKNOWN;
                        break;
                    }
                    str = MimeTypes.VIDEO_VC1;
                    break;
                case 9:
                    str = MimeTypes.VIDEO_UNKNOWN;
                    break;
                case 10:
                    str = MimeTypes.AUDIO_VORBIS;
                    i3 = 8192;
                    list = parseVorbisCodecPrivate(this.codecPrivate);
                    break;
                case 11:
                    str = MimeTypes.AUDIO_OPUS;
                    i3 = MatroskaExtractor.OPUS_MAX_INPUT_SIZE;
                    list = new ArrayList(3);
                    list.add(this.codecPrivate);
                    list.add(ByteBuffer.allocate(8).order(ByteOrder.nativeOrder()).putLong(this.codecDelayNs).array());
                    list.add(ByteBuffer.allocate(8).order(ByteOrder.nativeOrder()).putLong(this.seekPreRollNs).array());
                    break;
                case 12:
                    str = MimeTypes.AUDIO_AAC;
                    list = Collections.singletonList(this.codecPrivate);
                    break;
                case 13:
                    str = MimeTypes.AUDIO_MPEG_L2;
                    i3 = 4096;
                    break;
                case 14:
                    str = MimeTypes.AUDIO_MPEG;
                    i3 = 4096;
                    break;
                case 15:
                    str = MimeTypes.AUDIO_AC3;
                    break;
                case 16:
                    str = MimeTypes.AUDIO_E_AC3;
                    break;
                case 17:
                    str = MimeTypes.AUDIO_TRUEHD;
                    break;
                case 18:
                case 19:
                    str = MimeTypes.AUDIO_DTS;
                    break;
                case 20:
                    str = MimeTypes.AUDIO_DTS_HD;
                    break;
                case 21:
                    str = MimeTypes.AUDIO_FLAC;
                    list = Collections.singletonList(this.codecPrivate);
                    break;
                case 22:
                    str = MimeTypes.AUDIO_RAW;
                    if (!parseMsAcmCodecPrivate(new ParsableByteArray(this.codecPrivate))) {
                        str = MimeTypes.AUDIO_UNKNOWN;
                        Log.w(MatroskaExtractor.TAG, "Non-PCM MS/ACM is unsupported. Setting mimeType to " + str);
                        break;
                    }
                    i4 = Util.getPcmEncoding(this.audioBitDepth);
                    if (i4 == 0) {
                        i4 = -1;
                        str = MimeTypes.AUDIO_UNKNOWN;
                        Log.w(MatroskaExtractor.TAG, "Unsupported PCM bit depth: " + this.audioBitDepth + ". Setting mimeType to " + str);
                        break;
                    }
                    break;
                case 23:
                    str = MimeTypes.AUDIO_RAW;
                    i4 = Util.getPcmEncoding(this.audioBitDepth);
                    if (i4 == 0) {
                        i4 = -1;
                        str = MimeTypes.AUDIO_UNKNOWN;
                        Log.w(MatroskaExtractor.TAG, "Unsupported PCM bit depth: " + this.audioBitDepth + ". Setting mimeType to " + str);
                        break;
                    }
                    break;
                case 24:
                    str = MimeTypes.APPLICATION_SUBRIP;
                    break;
                case 25:
                    str = MimeTypes.TEXT_SSA;
                    break;
                case 26:
                    str = MimeTypes.APPLICATION_VOBSUB;
                    list = Collections.singletonList(this.codecPrivate);
                    break;
                case 27:
                    str = MimeTypes.APPLICATION_PGS;
                    break;
                case 28:
                    str = MimeTypes.APPLICATION_DVBSUBS;
                    list = Collections.singletonList(new byte[]{this.codecPrivate[0], this.codecPrivate[1], this.codecPrivate[2], this.codecPrivate[3]});
                    break;
                default:
                    throw new ParserException("Unrecognized codec identifier.");
            }
            int i5 = (0 | (this.flagDefault ? 1 : 0)) | (this.flagForced ? 2 : 0);
            if (MimeTypes.isAudio(str)) {
                createAudioSampleFormat = Format.createAudioSampleFormat(Integer.toString(i), str, null, -1, i3, this.channelCount, this.sampleRate, i4, list, this.drmInitData, i5, this.language);
                i2 = 1;
            } else if (MimeTypes.isVideo(str)) {
                if (this.displayUnit == 0) {
                    this.displayWidth = this.displayWidth == -1 ? this.width : this.displayWidth;
                    this.displayHeight = this.displayHeight == -1 ? this.height : this.displayHeight;
                }
                float f = -1.0f;
                if (!(this.displayWidth == -1 || this.displayHeight == -1)) {
                    f = ((float) (this.height * this.displayWidth)) / ((float) (this.width * this.displayHeight));
                }
                ColorInfo colorInfo = null;
                if (this.hasColorInfo) {
                    colorInfo = new ColorInfo(this.colorSpace, this.colorRange, this.colorTransfer, getHdrStaticInfo());
                }
                createAudioSampleFormat = Format.createVideoSampleFormat(Integer.toString(i), str, null, -1, i3, this.width, this.height, -1.0f, list, -1, f, this.projectionData, this.stereoMode, colorInfo, this.drmInitData);
                i2 = 2;
            } else if (MimeTypes.APPLICATION_SUBRIP.equals(str)) {
                createAudioSampleFormat = Format.createTextSampleFormat(Integer.toString(i), str, i5, this.language, this.drmInitData);
                i2 = 3;
            } else if (MimeTypes.TEXT_SSA.equals(str)) {
                List arrayList = new ArrayList(2);
                arrayList.add(MatroskaExtractor.SSA_DIALOGUE_FORMAT);
                arrayList.add(this.codecPrivate);
                createAudioSampleFormat = Format.createTextSampleFormat(Integer.toString(i), str, null, -1, i5, this.language, -1, this.drmInitData, Long.MAX_VALUE, arrayList);
                i2 = 3;
            } else if (MimeTypes.APPLICATION_VOBSUB.equals(str) || MimeTypes.APPLICATION_PGS.equals(str) || MimeTypes.APPLICATION_DVBSUBS.equals(str)) {
                createAudioSampleFormat = Format.createImageSampleFormat(Integer.toString(i), str, null, -1, list, this.language, this.drmInitData);
                i2 = 3;
            } else {
                throw new ParserException("Unexpected MIME type.");
            }
            this.output = extractorOutput.track(this.number, i2);
            this.output.format(createAudioSampleFormat);
        }
    }

    public MatroskaExtractor() {
        this(0);
    }

    public MatroskaExtractor(int i) {
        this(new DefaultEbmlReader(), i);
    }

    MatroskaExtractor(EbmlReader ebmlReader, int i) {
        this.segmentContentPosition = -1;
        this.timecodeScale = C3446C.TIME_UNSET;
        this.durationTimecode = C3446C.TIME_UNSET;
        this.durationUs = C3446C.TIME_UNSET;
        this.cuesContentPosition = -1;
        this.seekPositionAfterBuildingCues = -1;
        this.clusterTimecodeUs = C3446C.TIME_UNSET;
        this.reader = ebmlReader;
        this.reader.init(new InnerEbmlReaderOutput());
        this.seekForCuesEnabled = (i & 1) == 0;
        this.varintReader = new VarintReader();
        this.tracks = new SparseArray();
        this.scratch = new ParsableByteArray(4);
        this.vorbisNumPageSamples = new ParsableByteArray(ByteBuffer.allocate(4).putInt(-1).array());
        this.seekEntryIdBytes = new ParsableByteArray(4);
        this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.nalLength = new ParsableByteArray(4);
        this.sampleStrippedBytes = new ParsableByteArray();
        this.subtitleSample = new ParsableByteArray();
        this.encryptionInitializationVector = new ParsableByteArray(8);
        this.encryptionSubsampleData = new ParsableByteArray();
    }

    private SeekMap buildSeekMap() {
        int i = 0;
        if (this.segmentContentPosition == -1 || this.durationUs == C3446C.TIME_UNSET || this.cueTimesUs == null || this.cueTimesUs.size() == 0 || this.cueClusterPositions == null || this.cueClusterPositions.size() != this.cueTimesUs.size()) {
            this.cueTimesUs = null;
            this.cueClusterPositions = null;
            return new Unseekable(this.durationUs);
        }
        int size = this.cueTimesUs.size();
        int[] iArr = new int[size];
        long[] jArr = new long[size];
        long[] jArr2 = new long[size];
        long[] jArr3 = new long[size];
        for (int i2 = 0; i2 < size; i2++) {
            jArr3[i2] = this.cueTimesUs.get(i2);
            jArr[i2] = this.segmentContentPosition + this.cueClusterPositions.get(i2);
        }
        while (i < size - 1) {
            iArr[i] = (int) (jArr[i + 1] - jArr[i]);
            jArr2[i] = jArr3[i + 1] - jArr3[i];
            i++;
        }
        iArr[size - 1] = (int) ((this.segmentContentPosition + this.segmentContentSize) - jArr[size - 1]);
        jArr2[size - 1] = this.durationUs - jArr3[size - 1];
        this.cueTimesUs = null;
        this.cueClusterPositions = null;
        return new ChunkIndex(iArr, jArr, jArr2, jArr3);
    }

    private void commitSampleToOutput(Track track, long j) {
        if (CODEC_ID_SUBRIP.equals(track.codecId)) {
            commitSubtitleSample(track, SUBRIP_TIMECODE_FORMAT, 19, SUBRIP_TIMECODE_LAST_VALUE_SCALING_FACTOR, SUBRIP_TIMECODE_EMPTY);
        } else if (CODEC_ID_ASS.equals(track.codecId)) {
            commitSubtitleSample(track, SSA_TIMECODE_FORMAT, 21, SSA_TIMECODE_LAST_VALUE_SCALING_FACTOR, SSA_TIMECODE_EMPTY);
        }
        track.output.sampleMetadata(j, this.blockFlags, this.sampleBytesWritten, 0, track.cryptoData);
        this.sampleRead = true;
        resetSample();
    }

    private void commitSubtitleSample(Track track, String str, int i, long j, byte[] bArr) {
        setSampleDuration(this.subtitleSample.data, this.blockDurationUs, str, i, j, bArr);
        track.output.sampleData(this.subtitleSample, this.subtitleSample.limit());
        this.sampleBytesWritten += this.subtitleSample.limit();
    }

    private static int[] ensureArrayCapacity(int[] iArr, int i) {
        return iArr == null ? new int[i] : iArr.length < i ? new int[Math.max(iArr.length * 2, i)] : iArr;
    }

    private static boolean isCodecSupported(String str) {
        return CODEC_ID_VP8.equals(str) || CODEC_ID_VP9.equals(str) || CODEC_ID_MPEG2.equals(str) || CODEC_ID_MPEG4_SP.equals(str) || CODEC_ID_MPEG4_ASP.equals(str) || CODEC_ID_MPEG4_AP.equals(str) || CODEC_ID_H264.equals(str) || CODEC_ID_H265.equals(str) || CODEC_ID_FOURCC.equals(str) || CODEC_ID_THEORA.equals(str) || CODEC_ID_OPUS.equals(str) || CODEC_ID_VORBIS.equals(str) || CODEC_ID_AAC.equals(str) || CODEC_ID_MP2.equals(str) || CODEC_ID_MP3.equals(str) || CODEC_ID_AC3.equals(str) || CODEC_ID_E_AC3.equals(str) || CODEC_ID_TRUEHD.equals(str) || CODEC_ID_DTS.equals(str) || CODEC_ID_DTS_EXPRESS.equals(str) || CODEC_ID_DTS_LOSSLESS.equals(str) || CODEC_ID_FLAC.equals(str) || CODEC_ID_ACM.equals(str) || CODEC_ID_PCM_INT_LIT.equals(str) || CODEC_ID_SUBRIP.equals(str) || CODEC_ID_ASS.equals(str) || CODEC_ID_VOBSUB.equals(str) || CODEC_ID_PGS.equals(str) || CODEC_ID_DVBSUB.equals(str);
    }

    private boolean maybeSeekForCues(PositionHolder positionHolder, long j) {
        if (this.seekForCues) {
            this.seekPositionAfterBuildingCues = j;
            positionHolder.position = this.cuesContentPosition;
            this.seekForCues = false;
            return true;
        } else if (!this.sentSeekMap || this.seekPositionAfterBuildingCues == -1) {
            return false;
        } else {
            positionHolder.position = this.seekPositionAfterBuildingCues;
            this.seekPositionAfterBuildingCues = -1;
            return true;
        }
    }

    private void readScratch(ExtractorInput extractorInput, int i) {
        if (this.scratch.limit() < i) {
            if (this.scratch.capacity() < i) {
                this.scratch.reset(Arrays.copyOf(this.scratch.data, Math.max(this.scratch.data.length * 2, i)), this.scratch.limit());
            }
            extractorInput.readFully(this.scratch.data, this.scratch.limit(), i - this.scratch.limit());
            this.scratch.setLimit(i);
        }
    }

    private int readToOutput(ExtractorInput extractorInput, TrackOutput trackOutput, int i) {
        int bytesLeft = this.sampleStrippedBytes.bytesLeft();
        if (bytesLeft > 0) {
            bytesLeft = Math.min(i, bytesLeft);
            trackOutput.sampleData(this.sampleStrippedBytes, bytesLeft);
        } else {
            bytesLeft = trackOutput.sampleData(extractorInput, i, false);
        }
        this.sampleBytesRead += bytesLeft;
        this.sampleBytesWritten += bytesLeft;
        return bytesLeft;
    }

    private void readToTarget(ExtractorInput extractorInput, byte[] bArr, int i, int i2) {
        int min = Math.min(i2, this.sampleStrippedBytes.bytesLeft());
        extractorInput.readFully(bArr, i + min, i2 - min);
        if (min > 0) {
            this.sampleStrippedBytes.readBytes(bArr, i, min);
        }
        this.sampleBytesRead += i2;
    }

    private void resetSample() {
        this.sampleBytesRead = 0;
        this.sampleBytesWritten = 0;
        this.sampleCurrentNalBytesRemaining = 0;
        this.sampleEncodingHandled = false;
        this.sampleSignalByteRead = false;
        this.samplePartitionCountRead = false;
        this.samplePartitionCount = 0;
        this.sampleSignalByte = (byte) 0;
        this.sampleInitializationVectorRead = false;
        this.sampleStrippedBytes.reset();
    }

    private long scaleTimecodeToUs(long j) {
        if (this.timecodeScale == C3446C.TIME_UNSET) {
            throw new ParserException("Can't scale timecode prior to timecodeScale being set.");
        }
        return Util.scaleLargeTimestamp(j, this.timecodeScale, 1000);
    }

    private static void setSampleDuration(byte[] bArr, long j, String str, int i, long j2, byte[] bArr2) {
        Object obj;
        if (j == C3446C.TIME_UNSET) {
            obj = bArr2;
        } else {
            long j3 = j - (((long) (((int) (j / 3600000000L)) * 3600)) * C3446C.MICROS_PER_SECOND);
            j3 -= ((long) (((int) (j3 / 60000000)) * 60)) * C3446C.MICROS_PER_SECOND;
            int i2 = (int) ((j3 - (((long) ((int) (j3 / C3446C.MICROS_PER_SECOND))) * C3446C.MICROS_PER_SECOND)) / j2);
            obj = Util.getUtf8Bytes(String.format(Locale.US, str, new Object[]{Integer.valueOf(r2), Integer.valueOf(r3), Integer.valueOf(r6), Integer.valueOf(i2)}));
        }
        System.arraycopy(obj, 0, bArr, i, bArr2.length);
    }

    private void writeSampleData(ExtractorInput extractorInput, Track track, int i) {
        if (CODEC_ID_SUBRIP.equals(track.codecId)) {
            writeSubtitleSampleData(extractorInput, SUBRIP_PREFIX, i);
        } else if (CODEC_ID_ASS.equals(track.codecId)) {
            writeSubtitleSampleData(extractorInput, SSA_PREFIX, i);
        } else {
            int i2;
            int readUnsignedIntToInt;
            TrackOutput trackOutput = track.output;
            if (!this.sampleEncodingHandled) {
                if (track.hasContentEncryption) {
                    this.blockFlags &= -1073741825;
                    if (!this.sampleSignalByteRead) {
                        extractorInput.readFully(this.scratch.data, 0, 1);
                        this.sampleBytesRead++;
                        if ((this.scratch.data[0] & 128) == 128) {
                            throw new ParserException("Extension bit is set in signal byte");
                        }
                        this.sampleSignalByte = this.scratch.data[0];
                        this.sampleSignalByteRead = true;
                    }
                    if ((this.sampleSignalByte & 1) == 1) {
                        boolean z = (this.sampleSignalByte & 2) == 2;
                        this.blockFlags |= 1073741824;
                        if (!this.sampleInitializationVectorRead) {
                            extractorInput.readFully(this.encryptionInitializationVector.data, 0, 8);
                            this.sampleBytesRead += 8;
                            this.sampleInitializationVectorRead = true;
                            this.scratch.data[0] = (byte) ((z ? 128 : 0) | 8);
                            this.scratch.setPosition(0);
                            trackOutput.sampleData(this.scratch, 1);
                            this.sampleBytesWritten++;
                            this.encryptionInitializationVector.setPosition(0);
                            trackOutput.sampleData(this.encryptionInitializationVector, 8);
                            this.sampleBytesWritten += 8;
                        }
                        if (z) {
                            if (!this.samplePartitionCountRead) {
                                extractorInput.readFully(this.scratch.data, 0, 1);
                                this.sampleBytesRead++;
                                this.scratch.setPosition(0);
                                this.samplePartitionCount = this.scratch.readUnsignedByte();
                                this.samplePartitionCountRead = true;
                            }
                            i2 = this.samplePartitionCount * 4;
                            this.scratch.reset(i2);
                            extractorInput.readFully(this.scratch.data, 0, i2);
                            this.sampleBytesRead = i2 + this.sampleBytesRead;
                            short s = (short) ((this.samplePartitionCount / 2) + 1);
                            int i3 = (s * 6) + 2;
                            if (this.encryptionSubsampleDataBuffer == null || this.encryptionSubsampleDataBuffer.capacity() < i3) {
                                this.encryptionSubsampleDataBuffer = ByteBuffer.allocate(i3);
                            }
                            this.encryptionSubsampleDataBuffer.position(0);
                            this.encryptionSubsampleDataBuffer.putShort(s);
                            i2 = 0;
                            int i4 = 0;
                            while (i2 < this.samplePartitionCount) {
                                readUnsignedIntToInt = this.scratch.readUnsignedIntToInt();
                                if (i2 % 2 == 0) {
                                    this.encryptionSubsampleDataBuffer.putShort((short) (readUnsignedIntToInt - i4));
                                } else {
                                    this.encryptionSubsampleDataBuffer.putInt(readUnsignedIntToInt - i4);
                                }
                                i2++;
                                i4 = readUnsignedIntToInt;
                            }
                            i2 = (i - this.sampleBytesRead) - i4;
                            if (this.samplePartitionCount % 2 == 1) {
                                this.encryptionSubsampleDataBuffer.putInt(i2);
                            } else {
                                this.encryptionSubsampleDataBuffer.putShort((short) i2);
                                this.encryptionSubsampleDataBuffer.putInt(0);
                            }
                            this.encryptionSubsampleData.reset(this.encryptionSubsampleDataBuffer.array(), i3);
                            trackOutput.sampleData(this.encryptionSubsampleData, i3);
                            this.sampleBytesWritten += i3;
                        }
                    }
                } else if (track.sampleStrippedBytes != null) {
                    this.sampleStrippedBytes.reset(track.sampleStrippedBytes, track.sampleStrippedBytes.length);
                }
                this.sampleEncodingHandled = true;
            }
            i2 = this.sampleStrippedBytes.limit() + i;
            if (CODEC_ID_H264.equals(track.codecId) || CODEC_ID_H265.equals(track.codecId)) {
                byte[] bArr = this.nalLength.data;
                bArr[0] = (byte) 0;
                bArr[1] = (byte) 0;
                bArr[2] = (byte) 0;
                int i5 = track.nalUnitLengthFieldLength;
                readUnsignedIntToInt = 4 - track.nalUnitLengthFieldLength;
                while (this.sampleBytesRead < i2) {
                    if (this.sampleCurrentNalBytesRemaining == 0) {
                        readToTarget(extractorInput, bArr, readUnsignedIntToInt, i5);
                        this.nalLength.setPosition(0);
                        this.sampleCurrentNalBytesRemaining = this.nalLength.readUnsignedIntToInt();
                        this.nalStartCode.setPosition(0);
                        trackOutput.sampleData(this.nalStartCode, 4);
                        this.sampleBytesWritten += 4;
                    } else {
                        this.sampleCurrentNalBytesRemaining -= readToOutput(extractorInput, trackOutput, this.sampleCurrentNalBytesRemaining);
                    }
                }
            } else {
                while (this.sampleBytesRead < i2) {
                    readToOutput(extractorInput, trackOutput, i2 - this.sampleBytesRead);
                }
            }
            if (CODEC_ID_VORBIS.equals(track.codecId)) {
                this.vorbisNumPageSamples.setPosition(0);
                trackOutput.sampleData(this.vorbisNumPageSamples, 4);
                this.sampleBytesWritten += 4;
            }
        }
    }

    private void writeSubtitleSampleData(ExtractorInput extractorInput, byte[] bArr, int i) {
        int length = bArr.length + i;
        if (this.subtitleSample.capacity() < length) {
            this.subtitleSample.data = Arrays.copyOf(bArr, length + i);
        } else {
            System.arraycopy(bArr, 0, this.subtitleSample.data, 0, bArr.length);
        }
        extractorInput.readFully(this.subtitleSample.data, bArr.length, i);
        this.subtitleSample.reset(length);
    }

    void binaryElement(int i, int i2, ExtractorInput extractorInput) {
        switch (i) {
            case ID_BLOCK /*161*/:
            case ID_SIMPLE_BLOCK /*163*/:
                if (this.blockState == 0) {
                    this.blockTrackNumber = (int) this.varintReader.readUnsignedVarint(extractorInput, false, true, 8);
                    this.blockTrackNumberLength = this.varintReader.getLastLength();
                    this.blockDurationUs = C3446C.TIME_UNSET;
                    this.blockState = 1;
                    this.scratch.reset();
                }
                Track track = (Track) this.tracks.get(this.blockTrackNumber);
                if (track == null) {
                    extractorInput.skipFully(i2 - this.blockTrackNumberLength);
                    this.blockState = 0;
                    return;
                }
                if (this.blockState == 1) {
                    readScratch(extractorInput, 3);
                    int i3 = (this.scratch.data[2] & 6) >> 1;
                    if (i3 == 0) {
                        this.blockLacingSampleCount = 1;
                        this.blockLacingSampleSizes = ensureArrayCapacity(this.blockLacingSampleSizes, 1);
                        this.blockLacingSampleSizes[0] = (i2 - this.blockTrackNumberLength) - 3;
                    } else if (i != ID_SIMPLE_BLOCK) {
                        throw new ParserException("Lacing only supported in SimpleBlocks.");
                    } else {
                        readScratch(extractorInput, 4);
                        this.blockLacingSampleCount = (this.scratch.data[3] & 255) + 1;
                        this.blockLacingSampleSizes = ensureArrayCapacity(this.blockLacingSampleSizes, this.blockLacingSampleCount);
                        if (i3 == 2) {
                            Arrays.fill(this.blockLacingSampleSizes, 0, this.blockLacingSampleCount, ((i2 - this.blockTrackNumberLength) - 4) / this.blockLacingSampleCount);
                        } else if (i3 == 1) {
                            r5 = 0;
                            r4 = 4;
                            for (i3 = 0; i3 < this.blockLacingSampleCount - 1; i3++) {
                                this.blockLacingSampleSizes[i3] = 0;
                                do {
                                    r4++;
                                    readScratch(extractorInput, r4);
                                    r6 = this.scratch.data[r4 - 1] & 255;
                                    r7 = this.blockLacingSampleSizes;
                                    r7[i3] = r7[i3] + r6;
                                } while (r6 == 255);
                                r5 += this.blockLacingSampleSizes[i3];
                            }
                            this.blockLacingSampleSizes[this.blockLacingSampleCount - 1] = ((i2 - this.blockTrackNumberLength) - r4) - r5;
                        } else if (i3 == 3) {
                            r5 = 0;
                            r4 = 4;
                            i3 = 0;
                            while (i3 < this.blockLacingSampleCount - 1) {
                                this.blockLacingSampleSizes[i3] = 0;
                                r4++;
                                readScratch(extractorInput, r4);
                                if (this.scratch.data[r4 - 1] == (byte) 0) {
                                    throw new ParserException("No valid varint length mask found");
                                }
                                long j = 0;
                                int i4 = 0;
                                while (i4 < 8) {
                                    int i5 = 1 << (7 - i4);
                                    if ((this.scratch.data[r4 - 1] & i5) != 0) {
                                        int i6 = r4 - 1;
                                        r4 += i4;
                                        readScratch(extractorInput, r4);
                                        j = (long) ((this.scratch.data[i6] & 255) & (i5 ^ -1));
                                        for (i5 = i6 + 1; i5 < r4; i5++) {
                                            j = ((long) (this.scratch.data[i5] & 255)) | (j << 8);
                                        }
                                        if (i3 > 0) {
                                            j -= (1 << ((i4 * 7) + 6)) - 1;
                                        }
                                        if (j >= -2147483648L || j > 2147483647L) {
                                            throw new ParserException("EBML lacing sample size out of range.");
                                        }
                                        r6 = (int) j;
                                        r7 = this.blockLacingSampleSizes;
                                        if (i3 != 0) {
                                            r6 += this.blockLacingSampleSizes[i3 - 1];
                                        }
                                        r7[i3] = r6;
                                        r5 += this.blockLacingSampleSizes[i3];
                                        i3++;
                                    } else {
                                        i4++;
                                    }
                                }
                                if (j >= -2147483648L) {
                                    break;
                                }
                                throw new ParserException("EBML lacing sample size out of range.");
                            }
                            this.blockLacingSampleSizes[this.blockLacingSampleCount - 1] = ((i2 - this.blockTrackNumberLength) - r4) - r5;
                        } else {
                            throw new ParserException("Unexpected lacing value: " + i3);
                        }
                    }
                    this.blockTimeUs = this.clusterTimecodeUs + scaleTimecodeToUs((long) ((this.scratch.data[0] << 8) | (this.scratch.data[1] & 255)));
                    Object obj = (this.scratch.data[2] & 8) == 8 ? 1 : null;
                    Object obj2 = (track.type == 2 || (i == ID_SIMPLE_BLOCK && (this.scratch.data[2] & 128) == 128)) ? 1 : null;
                    this.blockFlags = (obj != null ? Integer.MIN_VALUE : 0) | (obj2 != null ? 1 : 0);
                    this.blockState = 2;
                    this.blockLacingSampleIndex = 0;
                }
                if (i == ID_SIMPLE_BLOCK) {
                    while (this.blockLacingSampleIndex < this.blockLacingSampleCount) {
                        writeSampleData(extractorInput, track, this.blockLacingSampleSizes[this.blockLacingSampleIndex]);
                        commitSampleToOutput(track, this.blockTimeUs + ((long) ((this.blockLacingSampleIndex * track.defaultSampleDurationNs) / 1000)));
                        this.blockLacingSampleIndex++;
                    }
                    this.blockState = 0;
                    return;
                }
                writeSampleData(extractorInput, track, this.blockLacingSampleSizes[0]);
                return;
            case ID_CONTENT_COMPRESSION_SETTINGS /*16981*/:
                this.currentTrack.sampleStrippedBytes = new byte[i2];
                extractorInput.readFully(this.currentTrack.sampleStrippedBytes, 0, i2);
                return;
            case ID_CONTENT_ENCRYPTION_KEY_ID /*18402*/:
                byte[] bArr = new byte[i2];
                extractorInput.readFully(bArr, 0, i2);
                this.currentTrack.cryptoData = new CryptoData(1, bArr, 0, 0);
                return;
            case ID_SEEK_ID /*21419*/:
                Arrays.fill(this.seekEntryIdBytes.data, (byte) 0);
                extractorInput.readFully(this.seekEntryIdBytes.data, 4 - i2, i2);
                this.seekEntryIdBytes.setPosition(0);
                this.seekEntryId = (int) this.seekEntryIdBytes.readUnsignedInt();
                return;
            case ID_CODEC_PRIVATE /*25506*/:
                this.currentTrack.codecPrivate = new byte[i2];
                extractorInput.readFully(this.currentTrack.codecPrivate, 0, i2);
                return;
            case ID_PROJECTION_PRIVATE /*30322*/:
                this.currentTrack.projectionData = new byte[i2];
                extractorInput.readFully(this.currentTrack.projectionData, 0, i2);
                return;
            default:
                throw new ParserException("Unexpected id: " + i);
        }
    }

    void endMasterElement(int i) {
        switch (i) {
            case ID_BLOCK_GROUP /*160*/:
                if (this.blockState == 2) {
                    if (!this.sampleSeenReferenceBlock) {
                        this.blockFlags |= 1;
                    }
                    commitSampleToOutput((Track) this.tracks.get(this.blockTrackNumber), this.blockTimeUs);
                    this.blockState = 0;
                    return;
                }
                return;
            case ID_TRACK_ENTRY /*174*/:
                if (isCodecSupported(this.currentTrack.codecId)) {
                    this.currentTrack.initializeOutput(this.extractorOutput, this.currentTrack.number);
                    this.tracks.put(this.currentTrack.number, this.currentTrack);
                }
                this.currentTrack = null;
                return;
            case ID_SEEK /*19899*/:
                if (this.seekEntryId == -1 || this.seekEntryPosition == -1) {
                    throw new ParserException("Mandatory element SeekID or SeekPosition not found");
                } else if (this.seekEntryId == ID_CUES) {
                    this.cuesContentPosition = this.seekEntryPosition;
                    return;
                } else {
                    return;
                }
            case ID_CONTENT_ENCODING /*25152*/:
                if (!this.currentTrack.hasContentEncryption) {
                    return;
                }
                if (this.currentTrack.cryptoData == null) {
                    throw new ParserException("Encrypted Track found but ContentEncKeyID was not found");
                }
                this.currentTrack.drmInitData = new DrmInitData(new SchemeData(C3446C.UUID_NIL, null, MimeTypes.VIDEO_WEBM, this.currentTrack.cryptoData.encryptionKey));
                return;
            case ID_CONTENT_ENCODINGS /*28032*/:
                if (this.currentTrack.hasContentEncryption && this.currentTrack.sampleStrippedBytes != null) {
                    throw new ParserException("Combining encryption and compression is not supported");
                }
                return;
            case 357149030:
                if (this.timecodeScale == C3446C.TIME_UNSET) {
                    this.timecodeScale = C3446C.MICROS_PER_SECOND;
                }
                if (this.durationTimecode != C3446C.TIME_UNSET) {
                    this.durationUs = scaleTimecodeToUs(this.durationTimecode);
                    return;
                }
                return;
            case ID_TRACKS /*374648427*/:
                if (this.tracks.size() == 0) {
                    throw new ParserException("No valid tracks were found");
                }
                this.extractorOutput.endTracks();
                return;
            case ID_CUES /*475249515*/:
                if (!this.sentSeekMap) {
                    this.extractorOutput.seekMap(buildSeekMap());
                    this.sentSeekMap = true;
                    return;
                }
                return;
            default:
                return;
        }
    }

    void floatElement(int i, double d) {
        switch (i) {
            case ID_SAMPLING_FREQUENCY /*181*/:
                this.currentTrack.sampleRate = (int) d;
                return;
            case ID_DURATION /*17545*/:
                this.durationTimecode = (long) d;
                return;
            case ID_PRIMARY_R_CHROMATICITY_X /*21969*/:
                this.currentTrack.primaryRChromaticityX = (float) d;
                return;
            case ID_PRIMARY_R_CHROMATICITY_Y /*21970*/:
                this.currentTrack.primaryRChromaticityY = (float) d;
                return;
            case ID_PRIMARY_G_CHROMATICITY_X /*21971*/:
                this.currentTrack.primaryGChromaticityX = (float) d;
                return;
            case ID_PRIMARY_G_CHROMATICITY_Y /*21972*/:
                this.currentTrack.primaryGChromaticityY = (float) d;
                return;
            case ID_PRIMARY_B_CHROMATICITY_X /*21973*/:
                this.currentTrack.primaryBChromaticityX = (float) d;
                return;
            case ID_PRIMARY_B_CHROMATICITY_Y /*21974*/:
                this.currentTrack.primaryBChromaticityY = (float) d;
                return;
            case ID_WHITE_POINT_CHROMATICITY_X /*21975*/:
                this.currentTrack.whitePointChromaticityX = (float) d;
                return;
            case ID_WHITE_POINT_CHROMATICITY_Y /*21976*/:
                this.currentTrack.whitePointChromaticityY = (float) d;
                return;
            case ID_LUMNINANCE_MAX /*21977*/:
                this.currentTrack.maxMasteringLuminance = (float) d;
                return;
            case ID_LUMNINANCE_MIN /*21978*/:
                this.currentTrack.minMasteringLuminance = (float) d;
                return;
            default:
                return;
        }
    }

    int getElementType(int i) {
        switch (i) {
            case ID_TRACK_TYPE /*131*/:
            case ID_FLAG_DEFAULT /*136*/:
            case ID_BLOCK_DURATION /*155*/:
            case ID_CHANNELS /*159*/:
            case ID_PIXEL_WIDTH /*176*/:
            case ID_CUE_TIME /*179*/:
            case ID_PIXEL_HEIGHT /*186*/:
            case ID_TRACK_NUMBER /*215*/:
            case ID_TIME_CODE /*231*/:
            case ID_CUE_CLUSTER_POSITION /*241*/:
            case ID_REFERENCE_BLOCK /*251*/:
            case ID_CONTENT_COMPRESSION_ALGORITHM /*16980*/:
            case ID_DOC_TYPE_READ_VERSION /*17029*/:
            case ID_EBML_READ_VERSION /*17143*/:
            case ID_CONTENT_ENCRYPTION_ALGORITHM /*18401*/:
            case ID_CONTENT_ENCRYPTION_AES_SETTINGS_CIPHER_MODE /*18408*/:
            case ID_CONTENT_ENCODING_ORDER /*20529*/:
            case ID_CONTENT_ENCODING_SCOPE /*20530*/:
            case ID_SEEK_POSITION /*21420*/:
            case ID_STEREO_MODE /*21432*/:
            case ID_DISPLAY_WIDTH /*21680*/:
            case ID_DISPLAY_UNIT /*21682*/:
            case ID_DISPLAY_HEIGHT /*21690*/:
            case ID_FLAG_FORCED /*21930*/:
            case ID_COLOUR_RANGE /*21945*/:
            case ID_COLOUR_TRANSFER /*21946*/:
            case ID_COLOUR_PRIMARIES /*21947*/:
            case ID_MAX_CLL /*21948*/:
            case ID_MAX_FALL /*21949*/:
            case ID_CODEC_DELAY /*22186*/:
            case ID_SEEK_PRE_ROLL /*22203*/:
            case ID_AUDIO_BIT_DEPTH /*25188*/:
            case ID_DEFAULT_DURATION /*2352003*/:
            case ID_TIMECODE_SCALE /*2807729*/:
                return 2;
            case 134:
            case ID_DOC_TYPE /*17026*/:
            case ID_LANGUAGE /*2274716*/:
                return 3;
            case ID_BLOCK_GROUP /*160*/:
            case ID_TRACK_ENTRY /*174*/:
            case ID_CUE_TRACK_POSITIONS /*183*/:
            case ID_CUE_POINT /*187*/:
            case 224:
            case ID_AUDIO /*225*/:
            case ID_CONTENT_ENCRYPTION_AES_SETTINGS /*18407*/:
            case ID_SEEK /*19899*/:
            case ID_CONTENT_COMPRESSION /*20532*/:
            case ID_CONTENT_ENCRYPTION /*20533*/:
            case ID_COLOUR /*21936*/:
            case ID_MASTERING_METADATA /*21968*/:
            case ID_CONTENT_ENCODING /*25152*/:
            case ID_CONTENT_ENCODINGS /*28032*/:
            case ID_PROJECTION /*30320*/:
            case ID_SEEK_HEAD /*290298740*/:
            case 357149030:
            case ID_TRACKS /*374648427*/:
            case ID_SEGMENT /*408125543*/:
            case ID_EBML /*440786851*/:
            case ID_CUES /*475249515*/:
            case ID_CLUSTER /*524531317*/:
                return 1;
            case ID_BLOCK /*161*/:
            case ID_SIMPLE_BLOCK /*163*/:
            case ID_CONTENT_COMPRESSION_SETTINGS /*16981*/:
            case ID_CONTENT_ENCRYPTION_KEY_ID /*18402*/:
            case ID_SEEK_ID /*21419*/:
            case ID_CODEC_PRIVATE /*25506*/:
            case ID_PROJECTION_PRIVATE /*30322*/:
                return 4;
            case ID_SAMPLING_FREQUENCY /*181*/:
            case ID_DURATION /*17545*/:
            case ID_PRIMARY_R_CHROMATICITY_X /*21969*/:
            case ID_PRIMARY_R_CHROMATICITY_Y /*21970*/:
            case ID_PRIMARY_G_CHROMATICITY_X /*21971*/:
            case ID_PRIMARY_G_CHROMATICITY_Y /*21972*/:
            case ID_PRIMARY_B_CHROMATICITY_X /*21973*/:
            case ID_PRIMARY_B_CHROMATICITY_Y /*21974*/:
            case ID_WHITE_POINT_CHROMATICITY_X /*21975*/:
            case ID_WHITE_POINT_CHROMATICITY_Y /*21976*/:
            case ID_LUMNINANCE_MAX /*21977*/:
            case ID_LUMNINANCE_MIN /*21978*/:
                return 5;
            default:
                return 0;
        }
    }

    public void init(ExtractorOutput extractorOutput) {
        this.extractorOutput = extractorOutput;
    }

    void integerElement(int i, long j) {
        boolean z = true;
        Track track;
        switch (i) {
            case ID_TRACK_TYPE /*131*/:
                this.currentTrack.type = (int) j;
                return;
            case ID_FLAG_DEFAULT /*136*/:
                track = this.currentTrack;
                if (j != 1) {
                    z = false;
                }
                track.flagForced = z;
                return;
            case ID_BLOCK_DURATION /*155*/:
                this.blockDurationUs = scaleTimecodeToUs(j);
                return;
            case ID_CHANNELS /*159*/:
                this.currentTrack.channelCount = (int) j;
                return;
            case ID_PIXEL_WIDTH /*176*/:
                this.currentTrack.width = (int) j;
                return;
            case ID_CUE_TIME /*179*/:
                this.cueTimesUs.add(scaleTimecodeToUs(j));
                return;
            case ID_PIXEL_HEIGHT /*186*/:
                this.currentTrack.height = (int) j;
                return;
            case ID_TRACK_NUMBER /*215*/:
                this.currentTrack.number = (int) j;
                return;
            case ID_TIME_CODE /*231*/:
                this.clusterTimecodeUs = scaleTimecodeToUs(j);
                return;
            case ID_CUE_CLUSTER_POSITION /*241*/:
                if (!this.seenClusterPositionForCurrentCuePoint) {
                    this.cueClusterPositions.add(j);
                    this.seenClusterPositionForCurrentCuePoint = true;
                    return;
                }
                return;
            case ID_REFERENCE_BLOCK /*251*/:
                this.sampleSeenReferenceBlock = true;
                return;
            case ID_CONTENT_COMPRESSION_ALGORITHM /*16980*/:
                if (j != 3) {
                    throw new ParserException("ContentCompAlgo " + j + " not supported");
                }
                return;
            case ID_DOC_TYPE_READ_VERSION /*17029*/:
                if (j < 1 || j > 2) {
                    throw new ParserException("DocTypeReadVersion " + j + " not supported");
                }
                return;
            case ID_EBML_READ_VERSION /*17143*/:
                if (j != 1) {
                    throw new ParserException("EBMLReadVersion " + j + " not supported");
                }
                return;
            case ID_CONTENT_ENCRYPTION_ALGORITHM /*18401*/:
                if (j != 5) {
                    throw new ParserException("ContentEncAlgo " + j + " not supported");
                }
                return;
            case ID_CONTENT_ENCRYPTION_AES_SETTINGS_CIPHER_MODE /*18408*/:
                if (j != 1) {
                    throw new ParserException("AESSettingsCipherMode " + j + " not supported");
                }
                return;
            case ID_CONTENT_ENCODING_ORDER /*20529*/:
                if (j != 0) {
                    throw new ParserException("ContentEncodingOrder " + j + " not supported");
                }
                return;
            case ID_CONTENT_ENCODING_SCOPE /*20530*/:
                if (j != 1) {
                    throw new ParserException("ContentEncodingScope " + j + " not supported");
                }
                return;
            case ID_SEEK_POSITION /*21420*/:
                this.seekEntryPosition = this.segmentContentPosition + j;
                return;
            case ID_STEREO_MODE /*21432*/:
                switch ((int) j) {
                    case 0:
                        this.currentTrack.stereoMode = 0;
                        return;
                    case 1:
                        this.currentTrack.stereoMode = 2;
                        return;
                    case 3:
                        this.currentTrack.stereoMode = 1;
                        return;
                    case 15:
                        this.currentTrack.stereoMode = 3;
                        return;
                    default:
                        return;
                }
            case ID_DISPLAY_WIDTH /*21680*/:
                this.currentTrack.displayWidth = (int) j;
                return;
            case ID_DISPLAY_UNIT /*21682*/:
                this.currentTrack.displayUnit = (int) j;
                return;
            case ID_DISPLAY_HEIGHT /*21690*/:
                this.currentTrack.displayHeight = (int) j;
                return;
            case ID_FLAG_FORCED /*21930*/:
                track = this.currentTrack;
                if (j != 1) {
                    z = false;
                }
                track.flagDefault = z;
                return;
            case ID_COLOUR_RANGE /*21945*/:
                switch ((int) j) {
                    case 1:
                        this.currentTrack.colorRange = 2;
                        return;
                    case 2:
                        this.currentTrack.colorRange = 1;
                        return;
                    default:
                        return;
                }
            case ID_COLOUR_TRANSFER /*21946*/:
                switch ((int) j) {
                    case 1:
                    case 6:
                    case 7:
                        this.currentTrack.colorTransfer = 3;
                        return;
                    case 16:
                        this.currentTrack.colorTransfer = 6;
                        return;
                    case 18:
                        this.currentTrack.colorTransfer = 7;
                        return;
                    default:
                        return;
                }
            case ID_COLOUR_PRIMARIES /*21947*/:
                this.currentTrack.hasColorInfo = true;
                switch ((int) j) {
                    case 1:
                        this.currentTrack.colorSpace = 1;
                        return;
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        this.currentTrack.colorSpace = 2;
                        return;
                    case 9:
                        this.currentTrack.colorSpace = 6;
                        return;
                    default:
                        return;
                }
            case ID_MAX_CLL /*21948*/:
                this.currentTrack.maxContentLuminance = (int) j;
                return;
            case ID_MAX_FALL /*21949*/:
                this.currentTrack.maxFrameAverageLuminance = (int) j;
                return;
            case ID_CODEC_DELAY /*22186*/:
                this.currentTrack.codecDelayNs = j;
                return;
            case ID_SEEK_PRE_ROLL /*22203*/:
                this.currentTrack.seekPreRollNs = j;
                return;
            case ID_AUDIO_BIT_DEPTH /*25188*/:
                this.currentTrack.audioBitDepth = (int) j;
                return;
            case ID_DEFAULT_DURATION /*2352003*/:
                this.currentTrack.defaultSampleDurationNs = (int) j;
                return;
            case ID_TIMECODE_SCALE /*2807729*/:
                this.timecodeScale = j;
                return;
            default:
                return;
        }
    }

    boolean isLevel1Element(int i) {
        return i == 357149030 || i == ID_CLUSTER || i == ID_CUES || i == ID_TRACKS;
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) {
        this.sampleRead = false;
        boolean z = true;
        while (z && !this.sampleRead) {
            z = this.reader.read(extractorInput);
            if (z && maybeSeekForCues(positionHolder, extractorInput.getPosition())) {
                return 1;
            }
        }
        return !z ? -1 : 0;
    }

    public void release() {
    }

    public void seek(long j, long j2) {
        this.clusterTimecodeUs = C3446C.TIME_UNSET;
        this.blockState = 0;
        this.reader.reset();
        this.varintReader.reset();
        resetSample();
    }

    public boolean sniff(ExtractorInput extractorInput) {
        return new Sniffer().sniff(extractorInput);
    }

    void startMasterElement(int i, long j, long j2) {
        switch (i) {
            case ID_BLOCK_GROUP /*160*/:
                this.sampleSeenReferenceBlock = false;
                return;
            case ID_TRACK_ENTRY /*174*/:
                this.currentTrack = new Track();
                return;
            case ID_CUE_POINT /*187*/:
                this.seenClusterPositionForCurrentCuePoint = false;
                return;
            case ID_SEEK /*19899*/:
                this.seekEntryId = -1;
                this.seekEntryPosition = -1;
                return;
            case ID_CONTENT_ENCRYPTION /*20533*/:
                this.currentTrack.hasContentEncryption = true;
                return;
            case ID_MASTERING_METADATA /*21968*/:
                this.currentTrack.hasColorInfo = true;
                return;
            case ID_SEGMENT /*408125543*/:
                if (this.segmentContentPosition == -1 || this.segmentContentPosition == j) {
                    this.segmentContentPosition = j;
                    this.segmentContentSize = j2;
                    return;
                }
                throw new ParserException("Multiple Segment elements not supported");
            case ID_CUES /*475249515*/:
                this.cueTimesUs = new LongArray();
                this.cueClusterPositions = new LongArray();
                return;
            case ID_CLUSTER /*524531317*/:
                if (!this.sentSeekMap) {
                    if (!this.seekForCuesEnabled || this.cuesContentPosition == -1) {
                        this.extractorOutput.seekMap(new Unseekable(this.durationUs));
                        this.sentSeekMap = true;
                        return;
                    }
                    this.seekForCues = true;
                    return;
                }
                return;
            default:
                return;
        }
    }

    void stringElement(int i, String str) {
        switch (i) {
            case 134:
                this.currentTrack.codecId = str;
                return;
            case ID_DOC_TYPE /*17026*/:
                if (!DOC_TYPE_WEBM.equals(str) && !DOC_TYPE_MATROSKA.equals(str)) {
                    throw new ParserException("DocType " + str + " not supported");
                }
                return;
            case ID_LANGUAGE /*2274716*/:
                this.currentTrack.language = str;
                return;
            default:
                return;
        }
    }
}
