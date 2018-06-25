package org.telegram.messenger.video;

import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import com.googlecode.mp4parser.util.Matrix;
import java.io.File;
import java.util.ArrayList;

public class Mp4Movie {
    private File cacheFile;
    private int height;
    private Matrix matrix = Matrix.ROTATE_0;
    private ArrayList<Track> tracks = new ArrayList();
    private int width;

    public Matrix getMatrix() {
        return this.matrix;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setCacheFile(File file) {
        this.cacheFile = file;
    }

    public void setRotation(int angle) {
        if (angle == 0) {
            this.matrix = Matrix.ROTATE_0;
        } else if (angle == 90) {
            this.matrix = Matrix.ROTATE_90;
        } else if (angle == 180) {
            this.matrix = Matrix.ROTATE_180;
        } else if (angle == 270) {
            this.matrix = Matrix.ROTATE_270;
        }
    }

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
    }

    public ArrayList<Track> getTracks() {
        return this.tracks;
    }

    public File getCacheFile() {
        return this.cacheFile;
    }

    public void addSample(int trackIndex, long offset, BufferInfo bufferInfo) {
        if (trackIndex >= 0 && trackIndex < this.tracks.size()) {
            ((Track) this.tracks.get(trackIndex)).addSample(offset, bufferInfo);
        }
    }

    public int addTrack(MediaFormat mediaFormat, boolean isAudio) {
        this.tracks.add(new Track(this.tracks.size(), mediaFormat, isAudio));
        return this.tracks.size() - 1;
    }
}
