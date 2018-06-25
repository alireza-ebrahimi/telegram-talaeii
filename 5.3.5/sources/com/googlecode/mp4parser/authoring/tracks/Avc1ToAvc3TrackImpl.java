package com.googlecode.mp4parser.authoring.tracks;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoTypeWriterVariable;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.MemoryDataSourceImpl;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.WrappingTrack;
import com.googlecode.mp4parser.util.CastUtils;
import com.googlecode.mp4parser.util.Path;
import com.mp4parser.iso14496.part15.AvcConfigurationBox;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

public class Avc1ToAvc3TrackImpl extends WrappingTrack {
    AvcConfigurationBox avcC;
    List<Sample> samples;
    SampleDescriptionBox stsd;

    private class ReplaceSyncSamplesList extends AbstractList<Sample> {
        List<Sample> parentSamples;

        public ReplaceSyncSamplesList(List<Sample> parentSamples) {
            this.parentSamples = parentSamples;
        }

        public Sample get(int index) {
            if (Arrays.binarySearch(Avc1ToAvc3TrackImpl.this.getSyncSamples(), (long) (index + 1)) < 0) {
                return (Sample) this.parentSamples.get(index);
            }
            final int len = Avc1ToAvc3TrackImpl.this.avcC.getLengthSizeMinusOne() + 1;
            final ByteBuffer buf = ByteBuffer.allocate(len);
            final Sample orignalSample = (Sample) this.parentSamples.get(index);
            return new Sample() {
                public void writeTo(WritableByteChannel channel) throws IOException {
                    for (byte[] bytes : Avc1ToAvc3TrackImpl.this.avcC.getSequenceParameterSets()) {
                        IsoTypeWriterVariable.write((long) bytes.length, (ByteBuffer) buf.rewind(), len);
                        channel.write((ByteBuffer) buf.rewind());
                        channel.write(ByteBuffer.wrap(bytes));
                    }
                    for (byte[] bytes2 : Avc1ToAvc3TrackImpl.this.avcC.getSequenceParameterSetExts()) {
                        IsoTypeWriterVariable.write((long) bytes2.length, (ByteBuffer) buf.rewind(), len);
                        channel.write((ByteBuffer) buf.rewind());
                        channel.write(ByteBuffer.wrap(bytes2));
                    }
                    for (byte[] bytes22 : Avc1ToAvc3TrackImpl.this.avcC.getPictureParameterSets()) {
                        IsoTypeWriterVariable.write((long) bytes22.length, (ByteBuffer) buf.rewind(), len);
                        channel.write((ByteBuffer) buf.rewind());
                        channel.write(ByteBuffer.wrap(bytes22));
                    }
                    orignalSample.writeTo(channel);
                }

                public long getSize() {
                    int spsPpsSize = 0;
                    for (byte[] bytes : Avc1ToAvc3TrackImpl.this.avcC.getSequenceParameterSets()) {
                        spsPpsSize += len + bytes.length;
                    }
                    for (byte[] bytes2 : Avc1ToAvc3TrackImpl.this.avcC.getSequenceParameterSetExts()) {
                        spsPpsSize += len + bytes2.length;
                    }
                    for (byte[] bytes22 : Avc1ToAvc3TrackImpl.this.avcC.getPictureParameterSets()) {
                        spsPpsSize += len + bytes22.length;
                    }
                    return orignalSample.getSize() + ((long) spsPpsSize);
                }

                public ByteBuffer asByteBuffer() {
                    int spsPpsSize = 0;
                    for (byte[] bytes : Avc1ToAvc3TrackImpl.this.avcC.getSequenceParameterSets()) {
                        spsPpsSize += len + bytes.length;
                    }
                    for (byte[] bytes2 : Avc1ToAvc3TrackImpl.this.avcC.getSequenceParameterSetExts()) {
                        spsPpsSize += len + bytes2.length;
                    }
                    for (byte[] bytes22 : Avc1ToAvc3TrackImpl.this.avcC.getPictureParameterSets()) {
                        spsPpsSize += len + bytes22.length;
                    }
                    ByteBuffer data = ByteBuffer.allocate(CastUtils.l2i(orignalSample.getSize()) + spsPpsSize);
                    for (byte[] bytes222 : Avc1ToAvc3TrackImpl.this.avcC.getSequenceParameterSets()) {
                        IsoTypeWriterVariable.write((long) bytes222.length, data, len);
                        data.put(bytes222);
                    }
                    for (byte[] bytes2222 : Avc1ToAvc3TrackImpl.this.avcC.getSequenceParameterSetExts()) {
                        IsoTypeWriterVariable.write((long) bytes2222.length, data, len);
                        data.put(bytes2222);
                    }
                    for (byte[] bytes22222 : Avc1ToAvc3TrackImpl.this.avcC.getPictureParameterSets()) {
                        IsoTypeWriterVariable.write((long) bytes22222.length, data, len);
                        data.put(bytes22222);
                    }
                    data.put(orignalSample.asByteBuffer());
                    return (ByteBuffer) data.rewind();
                }
            };
        }

        public int size() {
            return this.parentSamples.size();
        }
    }

    public Avc1ToAvc3TrackImpl(Track parent) throws IOException {
        super(parent);
        if (VisualSampleEntry.TYPE3.equals(parent.getSampleDescriptionBox().getSampleEntry().getType())) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            parent.getSampleDescriptionBox().getBox(Channels.newChannel(baos));
            this.stsd = (SampleDescriptionBox) Path.getPath(new IsoFile(new MemoryDataSourceImpl(baos.toByteArray())), SampleDescriptionBox.TYPE);
            ((VisualSampleEntry) this.stsd.getSampleEntry()).setType(VisualSampleEntry.TYPE4);
            this.avcC = (AvcConfigurationBox) Path.getPath(this.stsd, "avc./avcC");
            this.samples = new ReplaceSyncSamplesList(parent.getSamples());
            return;
        }
        throw new RuntimeException("Only avc1 tracks can be converted to avc3 tracks");
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.stsd;
    }

    public List<Sample> getSamples() {
        return this.samples;
    }
}
