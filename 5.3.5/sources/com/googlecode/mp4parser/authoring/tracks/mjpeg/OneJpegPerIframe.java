package com.googlecode.mp4parser.authoring.tracks.mjpeg;

import com.coremedia.iso.Hex;
import com.coremedia.iso.boxes.CompositionTimeToSample;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.authoring.AbstractTrack;
import com.googlecode.mp4parser.authoring.Edit;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.ESDescriptor;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.ObjectDescriptorFactory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.WritableByteChannel;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

public class OneJpegPerIframe extends AbstractTrack {
    File[] jpegs;
    long[] sampleDurations;
    SampleDescriptionBox stsd;
    long[] syncSamples;
    TrackMetaData trackMetaData = new TrackMetaData();

    /* renamed from: com.googlecode.mp4parser.authoring.tracks.mjpeg.OneJpegPerIframe$1 */
    class C05921 extends AbstractList<Sample> {
        C05921() {
        }

        public int size() {
            return OneJpegPerIframe.this.jpegs.length;
        }

        public Sample get(final int index) {
            return new Sample() {
                ByteBuffer sample = null;

                public void writeTo(WritableByteChannel channel) throws IOException {
                    RandomAccessFile raf = new RandomAccessFile(OneJpegPerIframe.this.jpegs[index], "r");
                    raf.getChannel().transferTo(0, raf.length(), channel);
                    raf.close();
                }

                public long getSize() {
                    return OneJpegPerIframe.this.jpegs[index].length();
                }

                public ByteBuffer asByteBuffer() {
                    if (this.sample == null) {
                        try {
                            RandomAccessFile raf = new RandomAccessFile(OneJpegPerIframe.this.jpegs[index], "r");
                            this.sample = raf.getChannel().map(MapMode.READ_ONLY, 0, raf.length());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return this.sample;
                }
            };
        }
    }

    public OneJpegPerIframe(String name, File[] jpegs, Track alignTo) throws IOException {
        super(name);
        this.jpegs = jpegs;
        if (alignTo.getSyncSamples().length != jpegs.length) {
            throw new RuntimeException("Number of sync samples doesn't match the number of stills (" + alignTo.getSyncSamples().length + " vs. " + jpegs.length + ")");
        }
        BufferedImage a = ImageIO.read(jpegs[0]);
        this.trackMetaData.setWidth((double) a.getWidth());
        this.trackMetaData.setHeight((double) a.getHeight());
        this.trackMetaData.setTimescale(alignTo.getTrackMetaData().getTimescale());
        long[] sampleDurationsToiAlignTo = alignTo.getSampleDurations();
        long[] syncSamples = alignTo.getSyncSamples();
        int currentSyncSample = 1;
        long duration = 0;
        this.sampleDurations = new long[syncSamples.length];
        int i = 1;
        while (i < sampleDurationsToiAlignTo.length) {
            if (currentSyncSample < syncSamples.length && ((long) i) == syncSamples[currentSyncSample]) {
                this.sampleDurations[currentSyncSample - 1] = duration;
                duration = 0;
                currentSyncSample++;
            }
            duration += sampleDurationsToiAlignTo[i];
            i++;
        }
        this.sampleDurations[this.sampleDurations.length - 1] = duration;
        this.stsd = new SampleDescriptionBox();
        VisualSampleEntry visualSampleEntry = new VisualSampleEntry(VisualSampleEntry.TYPE1);
        this.stsd.addBox(visualSampleEntry);
        ESDescriptorBox esds = new ESDescriptorBox();
        esds.setData(ByteBuffer.wrap(Hex.decodeHex("038080801B000100048080800D6C11000000000A1CB4000A1CB4068080800102")));
        esds.setEsDescriptor((ESDescriptor) ObjectDescriptorFactory.createFrom(-1, ByteBuffer.wrap(Hex.decodeHex("038080801B000100048080800D6C11000000000A1CB4000A1CB4068080800102"))));
        visualSampleEntry.addBox(esds);
        this.syncSamples = new long[jpegs.length];
        for (i = 0; i < this.syncSamples.length; i++) {
            this.syncSamples[i] = (long) (i + 1);
        }
        double earliestTrackPresentationTime = 0.0d;
        boolean acceptDwell = true;
        boolean acceptEdit = true;
        for (Edit edit : alignTo.getEdits()) {
            if (edit.getMediaTime() == -1 && !acceptDwell) {
                throw new RuntimeException("Cannot accept edit list for processing (1)");
            } else if (edit.getMediaTime() >= 0 && !acceptEdit) {
                throw new RuntimeException("Cannot accept edit list for processing (2)");
            } else if (edit.getMediaTime() == -1) {
                earliestTrackPresentationTime += edit.getSegmentDuration();
            } else {
                earliestTrackPresentationTime -= ((double) edit.getMediaTime()) / ((double) edit.getTimeScale());
                acceptEdit = false;
                acceptDwell = false;
            }
        }
        if (alignTo.getCompositionTimeEntries() != null && alignTo.getCompositionTimeEntries().size() > 0) {
            long currentTime = 0;
            int[] ptss = Arrays.copyOfRange(CompositionTimeToSample.blowupCompositionTimes(alignTo.getCompositionTimeEntries()), 0, 50);
            for (int j = 0; j < ptss.length; j++) {
                ptss[j] = (int) (((long) ptss[j]) + currentTime);
                currentTime += alignTo.getSampleDurations()[j];
            }
            Arrays.sort(ptss);
            earliestTrackPresentationTime += ((double) ptss[0]) / ((double) alignTo.getTrackMetaData().getTimescale());
        }
        if (earliestTrackPresentationTime < 0.0d) {
            getEdits().add(new Edit((long) ((-earliestTrackPresentationTime) * ((double) getTrackMetaData().getTimescale())), getTrackMetaData().getTimescale(), 1.0d, ((double) getDuration()) / ((double) getTrackMetaData().getTimescale())));
        } else if (earliestTrackPresentationTime > 0.0d) {
            getEdits().add(new Edit(-1, getTrackMetaData().getTimescale(), 1.0d, earliestTrackPresentationTime));
            getEdits().add(new Edit(0, getTrackMetaData().getTimescale(), 1.0d, ((double) getDuration()) / ((double) getTrackMetaData().getTimescale())));
        }
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.stsd;
    }

    public long[] getSampleDurations() {
        return this.sampleDurations;
    }

    public TrackMetaData getTrackMetaData() {
        return this.trackMetaData;
    }

    public String getHandler() {
        return "vide";
    }

    public long[] getSyncSamples() {
        return this.syncSamples;
    }

    public List<Sample> getSamples() {
        return new C05921();
    }

    public void close() throws IOException {
    }
}
