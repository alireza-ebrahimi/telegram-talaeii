package org.telegram.messenger.audioinfo.m4a;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.coremedia.iso.boxes.CopyrightBox;
import com.coremedia.iso.boxes.FileTypeBox;
import com.coremedia.iso.boxes.GenreBox;
import com.coremedia.iso.boxes.MediaBox;
import com.coremedia.iso.boxes.MediaHeaderBox;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.coremedia.iso.boxes.RatingBox;
import com.coremedia.iso.boxes.TrackBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.googlecode.mp4parser.boxes.apple.AppleNameBox;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.messenger.audioinfo.mp3.ID3v1Genre;

public class M4AInfo extends AudioInfo {
    private static final String ASCII = "ISO8859_1";
    static final Logger LOGGER = Logger.getLogger(M4AInfo.class.getName());
    private static final String UTF_8 = "UTF-8";
    private final Level debugLevel;
    private byte rating;
    private BigDecimal speed;
    private short tempo;
    private BigDecimal volume;

    public M4AInfo(InputStream input) throws IOException {
        this(input, Level.FINEST);
    }

    public M4AInfo(InputStream input, Level debugLevel) throws IOException {
        this.debugLevel = debugLevel;
        MP4Input mp4 = new MP4Input(input);
        if (LOGGER.isLoggable(debugLevel)) {
            LOGGER.log(debugLevel, mp4.toString());
        }
        ftyp(mp4.nextChild(FileTypeBox.TYPE));
        moov(mp4.nextChildUpTo(MovieBox.TYPE));
    }

    void ftyp(MP4Atom atom) throws IOException {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, atom.toString());
        }
        this.brand = atom.readString(4, ASCII).trim();
        if (this.brand.matches("M4V|MP4|mp42|isom")) {
            LOGGER.warning(atom.getPath() + ": brand=" + this.brand + " (experimental)");
        } else if (!this.brand.matches("M4A|M4P")) {
            LOGGER.warning(atom.getPath() + ": brand=" + this.brand + " (expected M4A or M4P)");
        }
        this.version = String.valueOf(atom.readInt());
    }

    void moov(MP4Atom atom) throws IOException {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, atom.toString());
        }
        while (atom.hasMoreChildren()) {
            MP4Atom child = atom.nextChild();
            String type = child.getType();
            Object obj = -1;
            switch (type.hashCode()) {
                case 3363941:
                    if (type.equals(MovieHeaderBox.TYPE)) {
                        obj = null;
                        break;
                    }
                    break;
                case 3568424:
                    if (type.equals(TrackBox.TYPE)) {
                        obj = 1;
                        break;
                    }
                    break;
                case 3585340:
                    if (type.equals(UserDataBox.TYPE)) {
                        obj = 2;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    mvhd(child);
                    break;
                case 1:
                    trak(child);
                    break;
                case 2:
                    udta(child);
                    break;
                default:
                    break;
            }
        }
    }

    void mvhd(MP4Atom atom) throws IOException {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, atom.toString());
        }
        byte version = atom.readByte();
        atom.skip(3);
        atom.skip(version == (byte) 1 ? 16 : 8);
        int scale = atom.readInt();
        long units = version == (byte) 1 ? atom.readLong() : (long) atom.readInt();
        if (this.duration == 0) {
            this.duration = (1000 * units) / ((long) scale);
        } else if (LOGGER.isLoggable(this.debugLevel) && Math.abs(this.duration - ((1000 * units) / ((long) scale))) > 2) {
            LOGGER.log(this.debugLevel, "mvhd: duration " + this.duration + " -> " + ((1000 * units) / ((long) scale)));
        }
        this.speed = atom.readIntegerFixedPoint();
        this.volume = atom.readShortFixedPoint();
    }

    void trak(MP4Atom atom) throws IOException {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, atom.toString());
        }
        mdia(atom.nextChildUpTo(MediaBox.TYPE));
    }

    void mdia(MP4Atom atom) throws IOException {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, atom.toString());
        }
        mdhd(atom.nextChild(MediaHeaderBox.TYPE));
    }

    void mdhd(MP4Atom atom) throws IOException {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, atom.toString());
        }
        byte version = atom.readByte();
        atom.skip(3);
        atom.skip(version == (byte) 1 ? 16 : 8);
        int sampleRate = atom.readInt();
        long samples = version == (byte) 1 ? atom.readLong() : (long) atom.readInt();
        if (this.duration == 0) {
            this.duration = (1000 * samples) / ((long) sampleRate);
        } else if (LOGGER.isLoggable(this.debugLevel) && Math.abs(this.duration - ((1000 * samples) / ((long) sampleRate))) > 2) {
            LOGGER.log(this.debugLevel, "mdhd: duration " + this.duration + " -> " + ((1000 * samples) / ((long) sampleRate)));
        }
    }

    void udta(MP4Atom atom) throws IOException {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, atom.toString());
        }
        while (atom.hasMoreChildren()) {
            MP4Atom child = atom.nextChild();
            if (MetaBox.TYPE.equals(child.getType())) {
                meta(child);
                return;
            }
        }
    }

    void meta(MP4Atom atom) throws IOException {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, atom.toString());
        }
        atom.skip(4);
        while (atom.hasMoreChildren()) {
            MP4Atom child = atom.nextChild();
            if (AppleItemListBox.TYPE.equals(child.getType())) {
                ilst(child);
                return;
            }
        }
    }

    void ilst(MP4Atom atom) throws IOException {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, atom.toString());
        }
        while (atom.hasMoreChildren()) {
            MP4Atom child = atom.nextChild();
            if (LOGGER.isLoggable(this.debugLevel)) {
                LOGGER.log(this.debugLevel, child.toString());
            }
            if (child.getRemaining() != 0) {
                data(child.nextChildUpTo("data"));
            } else if (LOGGER.isLoggable(this.debugLevel)) {
                LOGGER.log(this.debugLevel, child.getPath() + ": contains no value");
            }
        }
    }

    void data(MP4Atom atom) throws IOException {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, atom.toString());
        }
        atom.skip(4);
        atom.skip(4);
        String type = atom.getParent().getType();
        Object obj = -1;
        switch (type.hashCode()) {
            case 2954818:
                if (type.equals("aART")) {
                    obj = 1;
                    break;
                }
                break;
            case 3059752:
                if (type.equals("covr")) {
                    obj = 6;
                    break;
                }
                break;
            case 3060304:
                if (type.equals("cpil")) {
                    obj = 7;
                    break;
                }
                break;
            case 3060591:
                if (type.equals(CopyrightBox.TYPE)) {
                    obj = 8;
                    break;
                }
                break;
            case 3083677:
                if (type.equals("disk")) {
                    obj = 11;
                    break;
                }
                break;
            case 3177818:
                if (type.equals(GenreBox.TYPE)) {
                    obj = 12;
                    break;
                }
                break;
            case 3511163:
                if (type.equals(RatingBox.TYPE)) {
                    obj = 17;
                    break;
                }
                break;
            case 3564088:
                if (type.equals("tmpo")) {
                    obj = 18;
                    break;
                }
                break;
            case 3568737:
                if (type.equals("trkn")) {
                    obj = 19;
                    break;
                }
                break;
            case 5099770:
                if (type.equals("©ART")) {
                    obj = 2;
                    break;
                }
                break;
            case 5131342:
                if (type.equals("©alb")) {
                    obj = null;
                    break;
                }
                break;
            case 5133313:
                if (type.equals("©cmt")) {
                    obj = 3;
                    break;
                }
                break;
            case 5133368:
                if (type.equals("©com")) {
                    obj = 4;
                    break;
                }
                break;
            case 5133411:
                if (type.equals("©cpy")) {
                    obj = 9;
                    break;
                }
                break;
            case 5133907:
                if (type.equals("©day")) {
                    obj = 10;
                    break;
                }
                break;
            case 5136903:
                if (type.equals("©gen")) {
                    obj = 13;
                    break;
                }
                break;
            case 5137308:
                if (type.equals("©grp")) {
                    obj = 14;
                    break;
                }
                break;
            case 5142332:
                if (type.equals("©lyr")) {
                    obj = 15;
                    break;
                }
                break;
            case 5143505:
                if (type.equals(AppleNameBox.TYPE)) {
                    obj = 16;
                    break;
                }
                break;
            case 5152688:
                if (type.equals("©wrt")) {
                    obj = 5;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                this.album = atom.readString("UTF-8");
                return;
            case 1:
                this.albumArtist = atom.readString("UTF-8");
                return;
            case 2:
                this.artist = atom.readString("UTF-8");
                return;
            case 3:
                this.comment = atom.readString("UTF-8");
                return;
            case 4:
            case 5:
                if (this.composer == null || this.composer.trim().length() == 0) {
                    this.composer = atom.readString("UTF-8");
                    return;
                }
                return;
            case 6:
                try {
                    byte[] bytes = atom.readBytes();
                    Options opts = new Options();
                    opts.inJustDecodeBounds = true;
                    opts.inSampleSize = 1;
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
                    if (opts.outWidth > 800 || opts.outHeight > 800) {
                        for (int size = Math.max(opts.outWidth, opts.outHeight); size > 800; size /= 2) {
                            opts.inSampleSize *= 2;
                        }
                    }
                    opts.inJustDecodeBounds = false;
                    this.cover = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
                    if (this.cover != null) {
                        float scale = ((float) Math.max(this.cover.getWidth(), this.cover.getHeight())) / 120.0f;
                        if (scale > 0.0f) {
                            this.smallCover = Bitmap.createScaledBitmap(this.cover, (int) (((float) this.cover.getWidth()) / scale), (int) (((float) this.cover.getHeight()) / scale), true);
                        } else {
                            this.smallCover = this.cover;
                        }
                        if (this.smallCover == null) {
                            this.smallCover = this.cover;
                            return;
                        }
                        return;
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            case 7:
                this.compilation = atom.readBoolean();
                return;
            case 8:
            case 9:
                if (this.copyright == null || this.copyright.trim().length() == 0) {
                    this.copyright = atom.readString("UTF-8");
                    return;
                }
                return;
            case 10:
                String day = atom.readString("UTF-8").trim();
                if (day.length() >= 4) {
                    try {
                        this.year = Short.valueOf(day.substring(0, 4)).shortValue();
                        return;
                    } catch (NumberFormatException e2) {
                        return;
                    }
                }
                return;
            case 11:
                atom.skip(2);
                this.disc = atom.readShort();
                this.discs = atom.readShort();
                return;
            case 12:
                if (this.genre != null && this.genre.trim().length() != 0) {
                    return;
                }
                if (atom.getRemaining() == 2) {
                    ID3v1Genre id3v1Genre = ID3v1Genre.getGenre(atom.readShort() - 1);
                    if (id3v1Genre != null) {
                        this.genre = id3v1Genre.getDescription();
                        return;
                    }
                    return;
                }
                this.genre = atom.readString("UTF-8");
                return;
            case 13:
                if (this.genre == null || this.genre.trim().length() == 0) {
                    this.genre = atom.readString("UTF-8");
                    return;
                }
                return;
            case 14:
                this.grouping = atom.readString("UTF-8");
                return;
            case 15:
                this.lyrics = atom.readString("UTF-8");
                return;
            case 16:
                this.title = atom.readString("UTF-8");
                return;
            case 17:
                this.rating = atom.readByte();
                return;
            case 18:
                this.tempo = atom.readShort();
                return;
            case 19:
                atom.skip(2);
                this.track = atom.readShort();
                this.tracks = atom.readShort();
                return;
            default:
                return;
        }
    }

    public short getTempo() {
        return this.tempo;
    }

    public byte getRating() {
        return this.rating;
    }

    public BigDecimal getSpeed() {
        return this.speed;
    }

    public BigDecimal getVolume() {
        return this.volume;
    }
}
