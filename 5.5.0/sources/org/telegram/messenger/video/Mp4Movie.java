package org.telegram.messenger.video;

import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import com.p057c.p058a.p063b.C1320g;
import java.io.File;
import java.util.ArrayList;

public class Mp4Movie {
    private File cacheFile;
    private int height;
    private C1320g matrix = C1320g.f3985j;
    private ArrayList<Track> tracks = new ArrayList();
    private int width;

    public void addSample(int i, long j, BufferInfo bufferInfo) {
        if (i >= 0 && i < this.tracks.size()) {
            ((Track) this.tracks.get(i)).addSample(j, bufferInfo);
        }
    }

    public int addTrack(MediaFormat mediaFormat, boolean z) {
        this.tracks.add(new Track(this.tracks.size(), mediaFormat, z));
        return this.tracks.size() - 1;
    }

    public File getCacheFile() {
        return this.cacheFile;
    }

    public int getHeight() {
        return this.height;
    }

    public C1320g getMatrix() {
        return this.matrix;
    }

    public ArrayList<Track> getTracks() {
        return this.tracks;
    }

    public int getWidth() {
        return this.width;
    }

    public void setCacheFile(File file) {
        this.cacheFile = file;
    }

    public void setRotation(int i) {
        if (i == 0) {
            this.matrix = C1320g.f3985j;
        } else if (i == 90) {
            this.matrix = C1320g.f3986k;
        } else if (i == 180) {
            this.matrix = C1320g.f3987l;
        } else if (i == 270) {
            this.matrix = C1320g.f3988m;
        }
    }

    public void setSize(int i, int i2) {
        this.width = i;
        this.height = i2;
    }
}
