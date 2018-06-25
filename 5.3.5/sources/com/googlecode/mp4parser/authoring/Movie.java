package com.googlecode.mp4parser.authoring;

import com.googlecode.mp4parser.util.Matrix;
import java.util.LinkedList;
import java.util.List;

public class Movie {
    Matrix matrix = Matrix.ROTATE_0;
    List<Track> tracks = new LinkedList();

    public Movie(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Track> getTracks() {
        return this.tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public void addTrack(Track nuTrack) {
        if (getTrackByTrackId(nuTrack.getTrackMetaData().getTrackId()) != null) {
            nuTrack.getTrackMetaData().setTrackId(getNextTrackId());
        }
        this.tracks.add(nuTrack);
    }

    public String toString() {
        String s = "Movie{ ";
        for (Track track : this.tracks) {
            s = new StringBuilder(String.valueOf(s)).append("track_").append(track.getTrackMetaData().getTrackId()).append(" (").append(track.getHandler()).append(") ").toString();
        }
        return new StringBuilder(String.valueOf(s)).append('}').toString();
    }

    public long getNextTrackId() {
        long nextTrackId = 0;
        for (Track track : this.tracks) {
            if (nextTrackId < track.getTrackMetaData().getTrackId()) {
                nextTrackId = track.getTrackMetaData().getTrackId();
            }
        }
        return nextTrackId + 1;
    }

    public Track getTrackByTrackId(long trackId) {
        for (Track track : this.tracks) {
            if (track.getTrackMetaData().getTrackId() == trackId) {
                return track;
            }
        }
        return null;
    }

    public long getTimescale() {
        long timescale = ((Track) getTracks().iterator().next()).getTrackMetaData().getTimescale();
        for (Track track : getTracks()) {
            timescale = gcd(track.getTrackMetaData().getTimescale(), timescale);
        }
        return timescale;
    }

    public Matrix getMatrix() {
        return this.matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
