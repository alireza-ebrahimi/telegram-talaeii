package com.googlecode.mp4parser.authoring.builder;

import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import java.util.Arrays;

public class TwoSecondIntersectionFinder implements FragmentIntersectionFinder {
    private int fragmentLength = 2;
    private Movie movie;

    public TwoSecondIntersectionFinder(Movie movie, int fragmentLength) {
        this.movie = movie;
        this.fragmentLength = fragmentLength;
    }

    public long[] sampleNumbers(Track track) {
        double trackLength = 0.0d;
        for (Track thisTrack : this.movie.getTracks()) {
            double thisTracksLength = (double) (thisTrack.getDuration() / thisTrack.getTrackMetaData().getTimescale());
            if (trackLength < thisTracksLength) {
                trackLength = thisTracksLength;
            }
        }
        int fragmentCount = Math.min(((int) Math.ceil(trackLength / ((double) this.fragmentLength))) - 1, track.getSamples().size());
        if (fragmentCount < 1) {
            fragmentCount = 1;
        }
        long[] fragments = new long[fragmentCount];
        Arrays.fill(fragments, -1);
        fragments[0] = 1;
        long time = 0;
        long[] sampleDurations = track.getSampleDurations();
        int length = sampleDurations.length;
        int i = 0;
        int samples = 0;
        while (i < length) {
            long delta = sampleDurations[i];
            int currentFragment = ((int) ((time / track.getTrackMetaData().getTimescale()) / ((long) this.fragmentLength))) + 1;
            if (currentFragment >= fragments.length) {
                break;
            }
            int samples2 = samples + 1;
            fragments[currentFragment] = (long) (samples + 1);
            time += delta;
            i++;
            samples = samples2;
        }
        long last = (long) (samples + 1);
        for (int i2 = fragments.length - 1; i2 >= 0; i2--) {
            if (fragments[i2] == -1) {
                fragments[i2] = last;
            }
            last = fragments[i2];
        }
        long[] cleanedFragments = new long[0];
        for (long fragment : fragments) {
            if (cleanedFragments.length == 0 || cleanedFragments[cleanedFragments.length - 1] != fragment) {
                cleanedFragments = Arrays.copyOf(cleanedFragments, cleanedFragments.length + 1);
                cleanedFragments[cleanedFragments.length - 1] = fragment;
            }
        }
        return cleanedFragments;
    }
}
