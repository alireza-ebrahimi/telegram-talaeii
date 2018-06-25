package org.telegram.messenger.audioinfo;

import android.graphics.Bitmap;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import org.telegram.messenger.audioinfo.m4a.M4AInfo;
import org.telegram.messenger.audioinfo.mp3.MP3Info;

public abstract class AudioInfo {
    protected String album;
    protected String albumArtist;
    protected String artist;
    protected String brand;
    protected String comment;
    protected boolean compilation;
    protected String composer;
    protected String copyright;
    protected Bitmap cover;
    protected short disc;
    protected short discs;
    protected long duration;
    protected String genre;
    protected String grouping;
    protected String lyrics;
    protected Bitmap smallCover;
    protected String title;
    protected short track;
    protected short tracks;
    protected String version;
    protected short year;

    public static AudioInfo getAudioInfo(File file) {
        try {
            byte[] bArr = new byte[12];
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.readFully(bArr, 0, 8);
            randomAccessFile.close();
            InputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            return (bArr[4] == (byte) 102 && bArr[5] == (byte) 116 && bArr[6] == (byte) 121 && bArr[7] == (byte) 112) ? new M4AInfo(bufferedInputStream) : new MP3Info(bufferedInputStream, file.length());
        } catch (Exception e) {
            return null;
        }
    }

    public String getAlbum() {
        return this.album;
    }

    public String getAlbumArtist() {
        return this.albumArtist;
    }

    public String getArtist() {
        return this.artist;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getComment() {
        return this.comment;
    }

    public String getComposer() {
        return this.composer;
    }

    public String getCopyright() {
        return this.copyright;
    }

    public Bitmap getCover() {
        return this.cover;
    }

    public short getDisc() {
        return this.disc;
    }

    public short getDiscs() {
        return this.discs;
    }

    public long getDuration() {
        return this.duration;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getGrouping() {
        return this.grouping;
    }

    public String getLyrics() {
        return this.lyrics;
    }

    public Bitmap getSmallCover() {
        return this.smallCover;
    }

    public String getTitle() {
        return this.title;
    }

    public short getTrack() {
        return this.track;
    }

    public short getTracks() {
        return this.tracks;
    }

    public String getVersion() {
        return this.version;
    }

    public short getYear() {
        return this.year;
    }

    public boolean isCompilation() {
        return this.compilation;
    }
}
