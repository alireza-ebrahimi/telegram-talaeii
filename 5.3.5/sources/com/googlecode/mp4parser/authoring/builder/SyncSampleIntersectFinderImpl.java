package com.googlecode.mp4parser.authoring.builder;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.OriginalFormatBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.util.Math;
import com.googlecode.mp4parser.util.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class SyncSampleIntersectFinderImpl implements FragmentIntersectionFinder {
    private static Logger LOG = Logger.getLogger(SyncSampleIntersectFinderImpl.class.getName());
    private final int minFragmentDurationSeconds;
    private Movie movie;
    private Track referenceTrack;

    public SyncSampleIntersectFinderImpl(Movie movie, Track referenceTrack, int minFragmentDurationSeconds) {
        this.movie = movie;
        this.referenceTrack = referenceTrack;
        this.minFragmentDurationSeconds = minFragmentDurationSeconds;
    }

    static String getFormat(Track track) {
        Box se = track.getSampleDescriptionBox().getSampleEntry();
        String type = se.getType();
        if (type.equals(VisualSampleEntry.TYPE_ENCRYPTED) || type.equals(AudioSampleEntry.TYPE_ENCRYPTED) || type.equals(VisualSampleEntry.TYPE_ENCRYPTED)) {
            return ((OriginalFormatBox) Path.getPath(se, "sinf/frma")).getDataFormat();
        }
        return type;
    }

    public long[] sampleNumbers(Track track) {
        if ("vide".equals(track.getHandler())) {
            if (track.getSyncSamples() == null || track.getSyncSamples().length <= 0) {
                throw new RuntimeException("Video Tracks need sync samples. Only tracks other than video may have no sync samples.");
            }
            List<long[]> times = getSyncSamplesTimestamps(this.movie, track);
            return getCommonIndices(track.getSyncSamples(), getTimes(track, this.movie), track.getTrackMetaData().getTimescale(), (long[][]) times.toArray(new long[times.size()][]));
        } else if ("soun".equals(track.getHandler())) {
            if (this.referenceTrack == null) {
                for (Track candidate : this.movie.getTracks()) {
                    if (candidate.getSyncSamples() != null && "vide".equals(candidate.getHandler()) && candidate.getSyncSamples().length > 0) {
                        this.referenceTrack = candidate;
                    }
                }
            }
            if (this.referenceTrack != null) {
                AudioSampleEntry ase;
                long samplesPerFrame;
                double factor;
                refSyncSamples = sampleNumbers(this.referenceTrack);
                int refSampleCount = this.referenceTrack.getSamples().size();
                syncSamples = new long[refSyncSamples.length];
                long minSampleRate = 192000;
                for (Track testTrack : this.movie.getTracks()) {
                    if (getFormat(track).equals(getFormat(testTrack))) {
                        ase = (AudioSampleEntry) testTrack.getSampleDescriptionBox().getSampleEntry();
                        if (ase.getSampleRate() < 192000) {
                            minSampleRate = ase.getSampleRate();
                            stretch = ((double) ((long) testTrack.getSamples().size())) / ((double) refSampleCount);
                            samplesPerFrame = testTrack.getSampleDurations()[0];
                            for (i = 0; i < syncSamples.length; i++) {
                                syncSamples[i] = (long) Math.ceil((((double) (refSyncSamples[i] - 1)) * stretch) * ((double) samplesPerFrame));
                            }
                            ase = (AudioSampleEntry) track.getSampleDescriptionBox().getSampleEntry();
                            samplesPerFrame = track.getSampleDurations()[0];
                            factor = ((double) ase.getSampleRate()) / ((double) minSampleRate);
                            if (factor == Math.rint(factor)) {
                                throw new RuntimeException("Sample rates must be a multiple of the lowest sample rate to create a correct file!");
                            }
                            for (i = 0; i < syncSamples.length; i++) {
                                syncSamples[i] = (long) (1.0d + ((((double) syncSamples[i]) * factor) / ((double) samplesPerFrame)));
                            }
                            return syncSamples;
                        }
                    }
                }
                ase = (AudioSampleEntry) track.getSampleDescriptionBox().getSampleEntry();
                samplesPerFrame = track.getSampleDurations()[0];
                factor = ((double) ase.getSampleRate()) / ((double) minSampleRate);
                if (factor == Math.rint(factor)) {
                    for (i = 0; i < syncSamples.length; i++) {
                        syncSamples[i] = (long) (1.0d + ((((double) syncSamples[i]) * factor) / ((double) samplesPerFrame)));
                    }
                    return syncSamples;
                }
                throw new RuntimeException("Sample rates must be a multiple of the lowest sample rate to create a correct file!");
            }
            throw new RuntimeException("There was absolutely no Track with sync samples. I can't work with that!");
        } else {
            for (Track candidate2 : this.movie.getTracks()) {
                if (candidate2.getSyncSamples() != null && candidate2.getSyncSamples().length > 0) {
                    refSyncSamples = sampleNumbers(candidate2);
                    syncSamples = new long[refSyncSamples.length];
                    stretch = ((double) ((long) track.getSamples().size())) / ((double) candidate2.getSamples().size());
                    for (i = 0; i < syncSamples.length; i++) {
                        syncSamples[i] = ((long) Math.ceil(((double) (refSyncSamples[i] - 1)) * stretch)) + 1;
                    }
                    return syncSamples;
                }
            }
            throw new RuntimeException("There was absolutely no Track with sync samples. I can't work with that!");
        }
    }

    public static List<long[]> getSyncSamplesTimestamps(Movie movie, Track track) {
        List<long[]> times = new LinkedList();
        for (Track currentTrack : movie.getTracks()) {
            if (currentTrack.getHandler().equals(track.getHandler())) {
                long[] currentTrackSyncSamples = currentTrack.getSyncSamples();
                if (currentTrackSyncSamples != null && currentTrackSyncSamples.length > 0) {
                    times.add(getTimes(currentTrack, movie));
                }
            }
        }
        return times;
    }

    public long[] getCommonIndices(long[] syncSamples, long[] syncSampleTimes, long timeScale, long[]... otherTracksTimes) {
        int i;
        List<Long> nuSyncSamples = new LinkedList();
        List<Long> nuSyncSampleTimes = new LinkedList();
        for (i = 0; i < syncSampleTimes.length; i++) {
            boolean foundInEveryRef = true;
            for (long[] times : otherTracksTimes) {
                foundInEveryRef &= Arrays.binarySearch(times, syncSampleTimes[i]) >= 0 ? 1 : 0;
            }
            if (foundInEveryRef) {
                nuSyncSamples.add(Long.valueOf(syncSamples[i]));
                nuSyncSampleTimes.add(Long.valueOf(syncSampleTimes[i]));
            }
        }
        if (((double) nuSyncSamples.size()) < ((double) syncSamples.length) * 0.25d) {
            long l;
            String log = "" + String.format("%5d - Common:  [", new Object[]{Integer.valueOf(nuSyncSamples.size())});
            for (Long longValue : nuSyncSamples) {
                l = longValue.longValue();
                log = new StringBuilder(String.valueOf(log)).append(String.format("%10d,", new Object[]{Long.valueOf(l)})).toString();
            }
            LOG.warning(new StringBuilder(String.valueOf(log)).append("]").toString());
            log = "" + String.format("%5d - In    :  [", new Object[]{Integer.valueOf(syncSamples.length)});
            for (long l2 : syncSamples) {
                log = new StringBuilder(String.valueOf(log)).append(String.format("%10d,", new Object[]{Long.valueOf(l2)})).toString();
            }
            LOG.warning(new StringBuilder(String.valueOf(log)).append("]").toString());
            LOG.warning("There are less than 25% of common sync samples in the given track.");
            throw new RuntimeException("There are less than 25% of common sync samples in the given track.");
        }
        if (((double) nuSyncSamples.size()) < ((double) syncSamples.length) * 0.5d) {
            LOG.fine("There are less than 50% of common sync samples in the given track. This is implausible but I'm ok to continue.");
        } else {
            if (nuSyncSamples.size() < syncSamples.length) {
                LOG.finest("Common SyncSample positions vs. this tracks SyncSample positions: " + nuSyncSamples.size() + " vs. " + syncSamples.length);
            }
        }
        List<Long> finalSampleList = new LinkedList();
        if (this.minFragmentDurationSeconds > 0) {
            long lastSyncSampleTime = -1;
            Iterator<Long> nuSyncSamplesIterator = nuSyncSamples.iterator();
            Iterator<Long> nuSyncSampleTimesIterator = nuSyncSampleTimes.iterator();
            while (nuSyncSamplesIterator.hasNext() && nuSyncSampleTimesIterator.hasNext()) {
                long curSyncSample = ((Long) nuSyncSamplesIterator.next()).longValue();
                long curSyncSampleTime = ((Long) nuSyncSampleTimesIterator.next()).longValue();
                if (lastSyncSampleTime == -1 || (curSyncSampleTime - lastSyncSampleTime) / timeScale >= ((long) this.minFragmentDurationSeconds)) {
                    finalSampleList.add(Long.valueOf(curSyncSample));
                    lastSyncSampleTime = curSyncSampleTime;
                }
            }
        } else {
            finalSampleList = nuSyncSamples;
        }
        long[] finalSampleArray = new long[finalSampleList.size()];
        for (i = 0; i < finalSampleArray.length; i++) {
            finalSampleArray[i] = ((Long) finalSampleList.get(i)).longValue();
        }
        return finalSampleArray;
    }

    private static long[] getTimes(Track track, Movie m) {
        long[] syncSamples = track.getSyncSamples();
        long[] syncSampleTimes = new long[syncSamples.length];
        long currentDuration = 0;
        int currentSyncSampleIndex = 0;
        long scalingFactor = calculateTracktimesScalingFactor(m, track);
        for (int currentSample = 1; ((long) currentSample) <= syncSamples[syncSamples.length - 1]; currentSample++) {
            if (((long) currentSample) == syncSamples[currentSyncSampleIndex]) {
                int currentSyncSampleIndex2 = currentSyncSampleIndex + 1;
                syncSampleTimes[currentSyncSampleIndex] = currentDuration * scalingFactor;
                currentSyncSampleIndex = currentSyncSampleIndex2;
            }
            currentDuration += track.getSampleDurations()[currentSample - 1];
        }
        return syncSampleTimes;
    }

    private static long calculateTracktimesScalingFactor(Movie m, Track track) {
        long timeScale = 1;
        for (Track track1 : m.getTracks()) {
            if (track1.getHandler().equals(track.getHandler()) && track1.getTrackMetaData().getTimescale() != track.getTrackMetaData().getTimescale()) {
                timeScale = Math.lcm(timeScale, track1.getTrackMetaData().getTimescale());
            }
        }
        return timeScale;
    }
}
