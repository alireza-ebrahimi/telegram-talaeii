package com.googlecode.mp4parser.authoring;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.ChunkOffsetBox;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.SchemeTypeBox;
import com.coremedia.iso.boxes.TrackBox;
import com.coremedia.iso.boxes.fragment.MovieExtendsBox;
import com.coremedia.iso.boxes.fragment.MovieFragmentBox;
import com.coremedia.iso.boxes.fragment.TrackFragmentBox;
import com.coremedia.iso.boxes.fragment.TrackRunBox;
import com.googlecode.mp4parser.AbstractContainerBox;
import com.googlecode.mp4parser.authoring.tracks.CencEncryptedTrack;
import com.googlecode.mp4parser.util.Path;
import com.mp4parser.iso14496.part12.SampleAuxiliaryInformationOffsetsBox;
import com.mp4parser.iso14496.part12.SampleAuxiliaryInformationSizesBox;
import com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat;
import com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair;
import com.mp4parser.iso23001.part7.TrackEncryptionBox;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.telegram.messenger.exoplayer2.C0907C;

public class CencMp4TrackImplImpl extends Mp4TrackImpl implements CencEncryptedTrack {
    static final /* synthetic */ boolean $assertionsDisabled = (!CencMp4TrackImplImpl.class.desiredAssertionStatus());
    private UUID defaultKeyId;
    private List<CencSampleAuxiliaryDataFormat> sampleEncryptionEntries;

    private class FindSaioSaizPair {
        static final /* synthetic */ boolean $assertionsDisabled = (!CencMp4TrackImplImpl.class.desiredAssertionStatus());
        private Container container;
        private SampleAuxiliaryInformationOffsetsBox saio;
        private SampleAuxiliaryInformationSizesBox saiz;

        public FindSaioSaizPair(Container container) {
            this.container = container;
        }

        public SampleAuxiliaryInformationSizesBox getSaiz() {
            return this.saiz;
        }

        public SampleAuxiliaryInformationOffsetsBox getSaio() {
            return this.saio;
        }

        public FindSaioSaizPair invoke() {
            List<SampleAuxiliaryInformationSizesBox> saizs = this.container.getBoxes(SampleAuxiliaryInformationSizesBox.class);
            List<SampleAuxiliaryInformationOffsetsBox> saios = this.container.getBoxes(SampleAuxiliaryInformationOffsetsBox.class);
            if ($assertionsDisabled || saizs.size() == saios.size()) {
                this.saiz = null;
                this.saio = null;
                int i = 0;
                while (i < saizs.size()) {
                    if ((this.saiz == null && ((SampleAuxiliaryInformationSizesBox) saizs.get(i)).getAuxInfoType() == null) || C0907C.CENC_TYPE_cenc.equals(((SampleAuxiliaryInformationSizesBox) saizs.get(i)).getAuxInfoType())) {
                        this.saiz = (SampleAuxiliaryInformationSizesBox) saizs.get(i);
                    } else if (this.saiz != null && this.saiz.getAuxInfoType() == null && C0907C.CENC_TYPE_cenc.equals(((SampleAuxiliaryInformationSizesBox) saizs.get(i)).getAuxInfoType())) {
                        this.saiz = (SampleAuxiliaryInformationSizesBox) saizs.get(i);
                    } else {
                        throw new RuntimeException("Are there two cenc labeled saiz?");
                    }
                    if ((this.saio == null && ((SampleAuxiliaryInformationOffsetsBox) saios.get(i)).getAuxInfoType() == null) || C0907C.CENC_TYPE_cenc.equals(((SampleAuxiliaryInformationOffsetsBox) saios.get(i)).getAuxInfoType())) {
                        this.saio = (SampleAuxiliaryInformationOffsetsBox) saios.get(i);
                    } else if (this.saio != null && this.saio.getAuxInfoType() == null && C0907C.CENC_TYPE_cenc.equals(((SampleAuxiliaryInformationOffsetsBox) saios.get(i)).getAuxInfoType())) {
                        this.saio = (SampleAuxiliaryInformationOffsetsBox) saios.get(i);
                    } else {
                        throw new RuntimeException("Are there two cenc labeled saio?");
                    }
                    i++;
                }
                return this;
            }
            throw new AssertionError();
        }
    }

    public CencMp4TrackImplImpl(String name, TrackBox trackBox, IsoFile... fragments) throws IOException {
        super(name, trackBox, fragments);
        SchemeTypeBox schm = (SchemeTypeBox) Path.getPath((AbstractContainerBox) trackBox, "mdia[0]/minf[0]/stbl[0]/stsd[0]/enc.[0]/sinf[0]/schm[0]");
        if ($assertionsDisabled || (schm != null && (schm.getSchemeType().equals(C0907C.CENC_TYPE_cenc) || schm.getSchemeType().equals(C0907C.CENC_TYPE_cbc1)))) {
            this.sampleEncryptionEntries = new ArrayList();
            long trackId = trackBox.getTrackHeaderBox().getTrackId();
            TrackEncryptionBox tenc;
            FindSaioSaizPair saizSaioPair;
            SampleAuxiliaryInformationOffsetsBox saio;
            SampleAuxiliaryInformationSizesBox saiz;
            int i;
            long offset;
            int j;
            if (trackBox.getParent().getBoxes(MovieExtendsBox.class).size() > 0) {
                for (MovieFragmentBox movieFragmentBox : ((Box) trackBox.getParent()).getParent().getBoxes(MovieFragmentBox.class)) {
                    for (TrackFragmentBox traf : movieFragmentBox.getBoxes(TrackFragmentBox.class)) {
                        if (traf.getTrackFragmentHeaderBox().getTrackId() == trackId) {
                            Container base;
                            long baseOffset;
                            tenc = (TrackEncryptionBox) Path.getPath((AbstractContainerBox) trackBox, "mdia[0]/minf[0]/stbl[0]/stsd[0]/enc.[0]/sinf[0]/schi[0]/tenc[0]");
                            this.defaultKeyId = tenc.getDefault_KID();
                            if (traf.getTrackFragmentHeaderBox().hasBaseDataOffset()) {
                                base = ((Box) trackBox.getParent()).getParent();
                                baseOffset = traf.getTrackFragmentHeaderBox().getBaseDataOffset();
                            } else {
                                Object base2 = movieFragmentBox;
                                baseOffset = 0;
                            }
                            saizSaioPair = new FindSaioSaizPair(traf).invoke();
                            saio = saizSaioPair.getSaio();
                            saiz = saizSaioPair.getSaiz();
                            if ($assertionsDisabled || saio != null) {
                                long[] saioOffsets = saio.getOffsets();
                                if (!$assertionsDisabled && saioOffsets.length != traf.getBoxes(TrackRunBox.class).size()) {
                                    throw new AssertionError();
                                } else if ($assertionsDisabled || saiz != null) {
                                    List<TrackRunBox> truns = traf.getBoxes(TrackRunBox.class);
                                    int sampleNo = 0;
                                    for (i = 0; i < saioOffsets.length; i++) {
                                        int numSamples = ((TrackRunBox) truns.get(i)).getEntries().size();
                                        offset = saioOffsets[i];
                                        long length = 0;
                                        for (j = sampleNo; j < sampleNo + numSamples; j++) {
                                            length += (long) saiz.getSize(j);
                                        }
                                        ByteBuffer trunsCencSampleAuxData = base.getByteBuffer(baseOffset + offset, length);
                                        for (j = sampleNo; j < sampleNo + numSamples; j++) {
                                            int auxInfoSize = saiz.getSize(j);
                                            this.sampleEncryptionEntries.add(parseCencAuxDataFormat(tenc.getDefaultIvSize(), trunsCencSampleAuxData, (long) auxInfoSize));
                                        }
                                        sampleNo += numSamples;
                                    }
                                } else {
                                    throw new AssertionError();
                                }
                            }
                            throw new AssertionError();
                        }
                    }
                }
                return;
            }
            tenc = (TrackEncryptionBox) Path.getPath((AbstractContainerBox) trackBox, "mdia[0]/minf[0]/stbl[0]/stsd[0]/enc.[0]/sinf[0]/schi[0]/tenc[0]");
            this.defaultKeyId = tenc.getDefault_KID();
            ChunkOffsetBox chunkOffsetBox = (ChunkOffsetBox) Path.getPath((AbstractContainerBox) trackBox, "mdia[0]/minf[0]/stbl[0]/stco[0]");
            if (chunkOffsetBox == null) {
                chunkOffsetBox = (ChunkOffsetBox) Path.getPath((AbstractContainerBox) trackBox, "mdia[0]/minf[0]/stbl[0]/co64[0]");
            }
            long[] chunkSizes = trackBox.getSampleTableBox().getSampleToChunkBox().blowup(chunkOffsetBox.getChunkOffsets().length);
            saizSaioPair = new FindSaioSaizPair((Container) Path.getPath((AbstractContainerBox) trackBox, "mdia[0]/minf[0]/stbl[0]")).invoke();
            saio = saizSaioPair.saio;
            saiz = saizSaioPair.saiz;
            Container topLevel = ((MovieBox) trackBox.getParent()).getParent();
            ByteBuffer chunksCencSampleAuxData;
            if (saio.getOffsets().length == 1) {
                offset = saio.getOffsets()[0];
                int sizeInTotal = 0;
                if (saiz.getDefaultSampleInfoSize() > 0) {
                    sizeInTotal = 0 + (saiz.getSampleCount() * saiz.getDefaultSampleInfoSize());
                } else {
                    for (i = 0; i < saiz.getSampleCount(); i++) {
                        sizeInTotal += saiz.getSampleInfoSizes()[i];
                    }
                }
                chunksCencSampleAuxData = topLevel.getByteBuffer(offset, (long) sizeInTotal);
                for (i = 0; i < saiz.getSampleCount(); i++) {
                    this.sampleEncryptionEntries.add(parseCencAuxDataFormat(tenc.getDefaultIvSize(), chunksCencSampleAuxData, (long) saiz.getSize(i)));
                }
                return;
            }
            if (saio.getOffsets().length == chunkSizes.length) {
                int currentSampleNo = 0;
                for (i = 0; i < chunkSizes.length; i++) {
                    offset = saio.getOffsets()[i];
                    long size = 0;
                    if (saiz.getDefaultSampleInfoSize() > 0) {
                        size = 0 + (((long) saiz.getSampleCount()) * chunkSizes[i]);
                    } else {
                        for (j = 0; ((long) j) < chunkSizes[i]; j++) {
                            size += (long) saiz.getSize(currentSampleNo + j);
                        }
                    }
                    chunksCencSampleAuxData = topLevel.getByteBuffer(offset, size);
                    for (j = 0; ((long) j) < chunkSizes[i]; j++) {
                        this.sampleEncryptionEntries.add(parseCencAuxDataFormat(tenc.getDefaultIvSize(), chunksCencSampleAuxData, (long) saiz.getSize(currentSampleNo + j)));
                    }
                    currentSampleNo = (int) (((long) currentSampleNo) + chunkSizes[i]);
                }
                return;
            }
            throw new RuntimeException("Number of saio offsets must be either 1 or number of chunks");
        }
        throw new AssertionError("Track must be CENC (cenc or cbc1) encrypted");
    }

    private CencSampleAuxiliaryDataFormat parseCencAuxDataFormat(int ivSize, ByteBuffer chunksCencSampleAuxData, long auxInfoSize) {
        CencSampleAuxiliaryDataFormat cadf = new CencSampleAuxiliaryDataFormat();
        if (auxInfoSize > 0) {
            cadf.iv = new byte[ivSize];
            chunksCencSampleAuxData.get(cadf.iv);
            if (auxInfoSize > ((long) ivSize)) {
                cadf.pairs = new Pair[IsoTypeReader.readUInt16(chunksCencSampleAuxData)];
                for (int i = 0; i < cadf.pairs.length; i++) {
                    cadf.pairs[i] = cadf.createPair(IsoTypeReader.readUInt16(chunksCencSampleAuxData), IsoTypeReader.readUInt32(chunksCencSampleAuxData));
                }
            }
        }
        return cadf;
    }

    public UUID getDefaultKeyId() {
        return this.defaultKeyId;
    }

    public boolean hasSubSampleEncryption() {
        return false;
    }

    public List<CencSampleAuxiliaryDataFormat> getSampleEncryptionEntries() {
        return this.sampleEncryptionEntries;
    }

    public String toString() {
        return "CencMp4TrackImpl{handler='" + getHandler() + '\'' + '}';
    }

    public String getName() {
        return "enc(" + super.getName() + ")";
    }
}
