package org.telegram.messenger.exoplayer2.extractor.ts;

import android.util.SparseArray;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.util.NalUnitUtil$PpsData;
import org.telegram.messenger.exoplayer2.util.NalUnitUtil$SpsData;
import org.telegram.messenger.exoplayer2.util.ParsableNalUnitBitArray;

final class H264Reader$SampleReader {
    private static final int DEFAULT_BUFFER_SIZE = 128;
    private static final int NAL_UNIT_TYPE_AUD = 9;
    private static final int NAL_UNIT_TYPE_IDR = 5;
    private static final int NAL_UNIT_TYPE_NON_IDR = 1;
    private static final int NAL_UNIT_TYPE_PARTITION_A = 2;
    private final boolean allowNonIdrKeyframes;
    private final ParsableNalUnitBitArray bitArray = new ParsableNalUnitBitArray(this.buffer, 0, 0);
    private byte[] buffer = new byte[128];
    private int bufferLength;
    private final boolean detectAccessUnits;
    private boolean isFilling;
    private long nalUnitStartPosition;
    private long nalUnitTimeUs;
    private int nalUnitType;
    private final TrackOutput output;
    private final SparseArray<NalUnitUtil$PpsData> pps = new SparseArray();
    private SliceHeaderData previousSliceHeader = new SliceHeaderData();
    private boolean readingSample;
    private boolean sampleIsKeyframe;
    private long samplePosition;
    private long sampleTimeUs;
    private SliceHeaderData sliceHeader = new SliceHeaderData();
    private final SparseArray<NalUnitUtil$SpsData> sps = new SparseArray();

    /* renamed from: org.telegram.messenger.exoplayer2.extractor.ts.H264Reader$SampleReader$SliceHeaderData */
    private static final class SliceHeaderData {
        private static final int SLICE_TYPE_ALL_I = 7;
        private static final int SLICE_TYPE_I = 2;
        private boolean bottomFieldFlag;
        private boolean bottomFieldFlagPresent;
        private int deltaPicOrderCnt0;
        private int deltaPicOrderCnt1;
        private int deltaPicOrderCntBottom;
        private boolean fieldPicFlag;
        private int frameNum;
        private boolean hasSliceType;
        private boolean idrPicFlag;
        private int idrPicId;
        private boolean isComplete;
        private int nalRefIdc;
        private int picOrderCntLsb;
        private int picParameterSetId;
        private int sliceType;
        private NalUnitUtil$SpsData spsData;

        private SliceHeaderData() {
        }

        public void clear() {
            this.hasSliceType = false;
            this.isComplete = false;
        }

        public void setSliceType(int sliceType) {
            this.sliceType = sliceType;
            this.hasSliceType = true;
        }

        public void setAll(NalUnitUtil$SpsData spsData, int nalRefIdc, int sliceType, int frameNum, int picParameterSetId, boolean fieldPicFlag, boolean bottomFieldFlagPresent, boolean bottomFieldFlag, boolean idrPicFlag, int idrPicId, int picOrderCntLsb, int deltaPicOrderCntBottom, int deltaPicOrderCnt0, int deltaPicOrderCnt1) {
            this.spsData = spsData;
            this.nalRefIdc = nalRefIdc;
            this.sliceType = sliceType;
            this.frameNum = frameNum;
            this.picParameterSetId = picParameterSetId;
            this.fieldPicFlag = fieldPicFlag;
            this.bottomFieldFlagPresent = bottomFieldFlagPresent;
            this.bottomFieldFlag = bottomFieldFlag;
            this.idrPicFlag = idrPicFlag;
            this.idrPicId = idrPicId;
            this.picOrderCntLsb = picOrderCntLsb;
            this.deltaPicOrderCntBottom = deltaPicOrderCntBottom;
            this.deltaPicOrderCnt0 = deltaPicOrderCnt0;
            this.deltaPicOrderCnt1 = deltaPicOrderCnt1;
            this.isComplete = true;
            this.hasSliceType = true;
        }

        public boolean isISlice() {
            return this.hasSliceType && (this.sliceType == 7 || this.sliceType == 2);
        }

        private boolean isFirstVclNalUnitOfPicture(SliceHeaderData other) {
            if (this.isComplete) {
                if (!other.isComplete || this.frameNum != other.frameNum || this.picParameterSetId != other.picParameterSetId || this.fieldPicFlag != other.fieldPicFlag) {
                    return true;
                }
                if (this.bottomFieldFlagPresent && other.bottomFieldFlagPresent && this.bottomFieldFlag != other.bottomFieldFlag) {
                    return true;
                }
                if (this.nalRefIdc != other.nalRefIdc && (this.nalRefIdc == 0 || other.nalRefIdc == 0)) {
                    return true;
                }
                if (this.spsData.picOrderCountType == 0 && other.spsData.picOrderCountType == 0 && (this.picOrderCntLsb != other.picOrderCntLsb || this.deltaPicOrderCntBottom != other.deltaPicOrderCntBottom)) {
                    return true;
                }
                if ((this.spsData.picOrderCountType == 1 && other.spsData.picOrderCountType == 1 && (this.deltaPicOrderCnt0 != other.deltaPicOrderCnt0 || this.deltaPicOrderCnt1 != other.deltaPicOrderCnt1)) || this.idrPicFlag != other.idrPicFlag) {
                    return true;
                }
                if (this.idrPicFlag && other.idrPicFlag && this.idrPicId != other.idrPicId) {
                    return true;
                }
            }
            return false;
        }
    }

    public H264Reader$SampleReader(TrackOutput output, boolean allowNonIdrKeyframes, boolean detectAccessUnits) {
        this.output = output;
        this.allowNonIdrKeyframes = allowNonIdrKeyframes;
        this.detectAccessUnits = detectAccessUnits;
        reset();
    }

    public boolean needsSpsPps() {
        return this.detectAccessUnits;
    }

    public void putSps(NalUnitUtil$SpsData spsData) {
        this.sps.append(spsData.seqParameterSetId, spsData);
    }

    public void putPps(NalUnitUtil$PpsData ppsData) {
        this.pps.append(ppsData.picParameterSetId, ppsData);
    }

    public void reset() {
        this.isFilling = false;
        this.readingSample = false;
        this.sliceHeader.clear();
    }

    public void startNalUnit(long position, int type, long pesTimeUs) {
        this.nalUnitType = type;
        this.nalUnitTimeUs = pesTimeUs;
        this.nalUnitStartPosition = position;
        if (!(this.allowNonIdrKeyframes && this.nalUnitType == 1)) {
            if (!this.detectAccessUnits) {
                return;
            }
            if (!(this.nalUnitType == 5 || this.nalUnitType == 1 || this.nalUnitType == 2)) {
                return;
            }
        }
        SliceHeaderData newSliceHeader = this.previousSliceHeader;
        this.previousSliceHeader = this.sliceHeader;
        this.sliceHeader = newSliceHeader;
        this.sliceHeader.clear();
        this.bufferLength = 0;
        this.isFilling = true;
    }

    public void appendToNalUnit(byte[] data, int offset, int limit) {
        if (this.isFilling) {
            int readLength = limit - offset;
            if (this.buffer.length < this.bufferLength + readLength) {
                this.buffer = Arrays.copyOf(this.buffer, (this.bufferLength + readLength) * 2);
            }
            System.arraycopy(data, offset, this.buffer, this.bufferLength, readLength);
            this.bufferLength += readLength;
            this.bitArray.reset(this.buffer, 0, this.bufferLength);
            if (this.bitArray.canReadBits(8)) {
                this.bitArray.skipBit();
                int nalRefIdc = this.bitArray.readBits(2);
                this.bitArray.skipBits(5);
                if (this.bitArray.canReadExpGolombCodedNum()) {
                    this.bitArray.readUnsignedExpGolombCodedInt();
                    if (this.bitArray.canReadExpGolombCodedNum()) {
                        int sliceType = this.bitArray.readUnsignedExpGolombCodedInt();
                        if (!this.detectAccessUnits) {
                            this.isFilling = false;
                            this.sliceHeader.setSliceType(sliceType);
                        } else if (this.bitArray.canReadExpGolombCodedNum()) {
                            int picParameterSetId = this.bitArray.readUnsignedExpGolombCodedInt();
                            if (this.pps.indexOfKey(picParameterSetId) < 0) {
                                this.isFilling = false;
                                return;
                            }
                            NalUnitUtil$PpsData ppsData = (NalUnitUtil$PpsData) this.pps.get(picParameterSetId);
                            NalUnitUtil$SpsData spsData = (NalUnitUtil$SpsData) this.sps.get(ppsData.seqParameterSetId);
                            if (spsData.separateColorPlaneFlag) {
                                if (this.bitArray.canReadBits(2)) {
                                    this.bitArray.skipBits(2);
                                } else {
                                    return;
                                }
                            }
                            if (this.bitArray.canReadBits(spsData.frameNumLength)) {
                                boolean fieldPicFlag = false;
                                boolean bottomFieldFlagPresent = false;
                                boolean bottomFieldFlag = false;
                                int frameNum = this.bitArray.readBits(spsData.frameNumLength);
                                if (!spsData.frameMbsOnlyFlag) {
                                    if (this.bitArray.canReadBits(1)) {
                                        fieldPicFlag = this.bitArray.readBit();
                                        if (fieldPicFlag) {
                                            if (this.bitArray.canReadBits(1)) {
                                                bottomFieldFlag = this.bitArray.readBit();
                                                bottomFieldFlagPresent = true;
                                            } else {
                                                return;
                                            }
                                        }
                                    }
                                    return;
                                }
                                boolean idrPicFlag = this.nalUnitType == 5;
                                int idrPicId = 0;
                                if (idrPicFlag) {
                                    if (this.bitArray.canReadExpGolombCodedNum()) {
                                        idrPicId = this.bitArray.readUnsignedExpGolombCodedInt();
                                    } else {
                                        return;
                                    }
                                }
                                int picOrderCntLsb = 0;
                                int deltaPicOrderCntBottom = 0;
                                int deltaPicOrderCnt0 = 0;
                                int deltaPicOrderCnt1 = 0;
                                if (spsData.picOrderCountType == 0) {
                                    if (this.bitArray.canReadBits(spsData.picOrderCntLsbLength)) {
                                        picOrderCntLsb = this.bitArray.readBits(spsData.picOrderCntLsbLength);
                                        if (ppsData.bottomFieldPicOrderInFramePresentFlag && !fieldPicFlag) {
                                            if (this.bitArray.canReadExpGolombCodedNum()) {
                                                deltaPicOrderCntBottom = this.bitArray.readSignedExpGolombCodedInt();
                                            } else {
                                                return;
                                            }
                                        }
                                    }
                                    return;
                                } else if (spsData.picOrderCountType == 1 && !spsData.deltaPicOrderAlwaysZeroFlag) {
                                    if (this.bitArray.canReadExpGolombCodedNum()) {
                                        deltaPicOrderCnt0 = this.bitArray.readSignedExpGolombCodedInt();
                                        if (ppsData.bottomFieldPicOrderInFramePresentFlag && !fieldPicFlag) {
                                            if (this.bitArray.canReadExpGolombCodedNum()) {
                                                deltaPicOrderCnt1 = this.bitArray.readSignedExpGolombCodedInt();
                                            } else {
                                                return;
                                            }
                                        }
                                    }
                                    return;
                                }
                                this.sliceHeader.setAll(spsData, nalRefIdc, sliceType, frameNum, picParameterSetId, fieldPicFlag, bottomFieldFlagPresent, bottomFieldFlag, idrPicFlag, idrPicId, picOrderCntLsb, deltaPicOrderCntBottom, deltaPicOrderCnt0, deltaPicOrderCnt1);
                                this.isFilling = false;
                            }
                        }
                    }
                }
            }
        }
    }

    public void endNalUnit(long position, int offset) {
        int i = 0;
        if (this.nalUnitType == 9 || (this.detectAccessUnits && this.sliceHeader.isFirstVclNalUnitOfPicture(this.previousSliceHeader))) {
            if (this.readingSample) {
                outputSample(offset + ((int) (position - this.nalUnitStartPosition)));
            }
            this.samplePosition = this.nalUnitStartPosition;
            this.sampleTimeUs = this.nalUnitTimeUs;
            this.sampleIsKeyframe = false;
            this.readingSample = true;
        }
        boolean z = this.sampleIsKeyframe;
        if (this.nalUnitType == 5 || (this.allowNonIdrKeyframes && this.nalUnitType == 1 && this.sliceHeader.isISlice())) {
            i = 1;
        }
        this.sampleIsKeyframe = i | z;
    }

    private void outputSample(int offset) {
        this.output.sampleMetadata(this.sampleTimeUs, this.sampleIsKeyframe ? 1 : 0, (int) (this.nalUnitStartPosition - this.samplePosition), offset, null);
    }
}
