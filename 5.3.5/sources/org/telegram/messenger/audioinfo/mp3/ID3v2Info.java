package org.telegram.messenger.audioinfo.mp3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.thin.downloadmanager.BuildConfig;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.messenger.exoplayer2.metadata.id3.ApicFrame;
import org.telegram.messenger.exoplayer2.metadata.id3.CommentFrame;

public class ID3v2Info extends AudioInfo {
    static final Logger LOGGER = Logger.getLogger(ID3v2Info.class.getName());
    private byte coverPictureType;
    private final Level debugLevel;

    static class AttachedPicture {
        static final byte TYPE_COVER_FRONT = (byte) 3;
        static final byte TYPE_OTHER = (byte) 0;
        final String description;
        final byte[] imageData;
        final String imageType;
        final byte type;

        public AttachedPicture(byte type, String description, String imageType, byte[] imageData) {
            this.type = type;
            this.description = description;
            this.imageType = imageType;
            this.imageData = imageData;
        }
    }

    static class CommentOrUnsynchronizedLyrics {
        final String description;
        final String language;
        final String text;

        public CommentOrUnsynchronizedLyrics(String language, String description, String text) {
            this.language = language;
            this.description = description;
            this.text = text;
        }
    }

    public static boolean isID3v2StartPosition(InputStream input) throws IOException {
        input.mark(3);
        try {
            boolean z = input.read() == 73 && input.read() == 68 && input.read() == 51;
            input.reset();
            return z;
        } catch (Throwable th) {
            input.reset();
        }
    }

    public ID3v2Info(InputStream input) throws IOException, ID3v2Exception {
        this(input, Level.FINEST);
    }

    public ID3v2Info(InputStream input, Level debugLevel) throws IOException, ID3v2Exception {
        this.debugLevel = debugLevel;
        if (isID3v2StartPosition(input)) {
            ID3v2TagHeader tagHeader = new ID3v2TagHeader(input);
            this.brand = "ID3";
            this.version = String.format("2.%d.%d", new Object[]{Integer.valueOf(tagHeader.getVersion()), Integer.valueOf(tagHeader.getRevision())});
            ID3v2TagBody tagBody = tagHeader.tagBody(input);
            while (tagBody.getRemainingLength() > 10) {
                ID3v2FrameHeader frameHeader = new ID3v2FrameHeader(tagBody);
                if (frameHeader.isPadding()) {
                    break loop0;
                } else if (((long) frameHeader.getBodySize()) > tagBody.getRemainingLength()) {
                    if (LOGGER.isLoggable(debugLevel)) {
                        LOGGER.log(debugLevel, "ID3 frame claims to extend frames area");
                    }
                } else if (!frameHeader.isValid() || frameHeader.isEncryption()) {
                    tagBody.getData().skipFully((long) frameHeader.getBodySize());
                } else {
                    ID3v2FrameBody frameBody = tagBody.frameBody(frameHeader);
                    try {
                        parseFrame(frameBody);
                        frameBody.getData().skipFully(frameBody.getRemainingLength());
                    } catch (ID3v2Exception e) {
                        try {
                            if (LOGGER.isLoggable(debugLevel)) {
                                LOGGER.log(debugLevel, String.format("ID3 exception occured in frame %s: %s", new Object[]{frameHeader.getFrameId(), e.getMessage()}));
                            }
                            frameBody.getData().skipFully(frameBody.getRemainingLength());
                        } catch (ID3v2Exception e2) {
                            if (LOGGER.isLoggable(debugLevel)) {
                                LOGGER.log(debugLevel, "ID3 exception occured: " + e2.getMessage());
                            }
                        }
                    } catch (Throwable th) {
                        frameBody.getData().skipFully(frameBody.getRemainingLength());
                        throw th;
                    }
                }
            }
            tagBody.getData().skipFully(tagBody.getRemainingLength());
            if (tagHeader.getFooterSize() > 0) {
                input.skip((long) tagHeader.getFooterSize());
            }
        }
    }

    void parseFrame(ID3v2FrameBody frame) throws IOException, ID3v2Exception {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, "Parsing frame: " + frame.getFrameHeader().getFrameId());
        }
        String frameId = frame.getFrameHeader().getFrameId();
        Object obj = -1;
        switch (frameId.hashCode()) {
            case 66913:
                if (frameId.equals("COM")) {
                    obj = 2;
                    break;
                }
                break;
            case 79210:
                if (frameId.equals("PIC")) {
                    obj = null;
                    break;
                }
                break;
            case 82815:
                if (frameId.equals("TAL")) {
                    obj = 4;
                    break;
                }
                break;
            case 82878:
                if (frameId.equals("TCM")) {
                    obj = 8;
                    break;
                }
                break;
            case 82880:
                if (frameId.equals("TCO")) {
                    obj = 10;
                    break;
                }
                break;
            case 82881:
                if (frameId.equals("TCP")) {
                    obj = 6;
                    break;
                }
                break;
            case 82883:
                if (frameId.equals("TCR")) {
                    obj = 12;
                    break;
                }
                break;
            case 83149:
                if (frameId.equals("TLE")) {
                    obj = 15;
                    break;
                }
                break;
            case 83253:
                if (frameId.equals("TP1")) {
                    obj = 17;
                    break;
                }
                break;
            case 83254:
                if (frameId.equals("TP2")) {
                    obj = 19;
                    break;
                }
                break;
            case 83269:
                if (frameId.equals("TPA")) {
                    obj = 21;
                    break;
                }
                break;
            case 83341:
                if (frameId.equals("TRK")) {
                    obj = 23;
                    break;
                }
                break;
            case 83377:
                if (frameId.equals("TT1")) {
                    obj = 25;
                    break;
                }
                break;
            case 83378:
                if (frameId.equals("TT2")) {
                    obj = 27;
                    break;
                }
                break;
            case 83552:
                if (frameId.equals("TYE")) {
                    obj = 29;
                    break;
                }
                break;
            case 84125:
                if (frameId.equals("ULT")) {
                    obj = 31;
                    break;
                }
                break;
            case 2015625:
                if (frameId.equals(ApicFrame.ID)) {
                    obj = 1;
                    break;
                }
                break;
            case 2074380:
                if (frameId.equals(CommentFrame.ID)) {
                    obj = 3;
                    break;
                }
                break;
            case 2567331:
                if (frameId.equals("TALB")) {
                    obj = 5;
                    break;
                }
                break;
            case 2569298:
                if (frameId.equals("TCMP")) {
                    obj = 7;
                    break;
                }
                break;
            case 2569357:
                if (frameId.equals("TCOM")) {
                    obj = 9;
                    break;
                }
                break;
            case 2569358:
                if (frameId.equals("TCON")) {
                    obj = 11;
                    break;
                }
                break;
            case 2569360:
                if (frameId.equals("TCOP")) {
                    obj = 13;
                    break;
                }
                break;
            case 2570401:
                if (frameId.equals("TDRC")) {
                    obj = 14;
                    break;
                }
                break;
            case 2575250:
                if (frameId.equals("TIT1")) {
                    obj = 26;
                    break;
                }
                break;
            case 2575251:
                if (frameId.equals("TIT2")) {
                    obj = 28;
                    break;
                }
                break;
            case 2577697:
                if (frameId.equals("TLEN")) {
                    obj = 16;
                    break;
                }
                break;
            case 2581512:
                if (frameId.equals("TPE1")) {
                    obj = 18;
                    break;
                }
                break;
            case 2581513:
                if (frameId.equals("TPE2")) {
                    obj = 20;
                    break;
                }
                break;
            case 2581856:
                if (frameId.equals("TPOS")) {
                    obj = 22;
                    break;
                }
                break;
            case 2583398:
                if (frameId.equals("TRCK")) {
                    obj = 24;
                    break;
                }
                break;
            case 2590194:
                if (frameId.equals("TYER")) {
                    obj = 30;
                    break;
                }
                break;
            case 2614438:
                if (frameId.equals("USLT")) {
                    obj = 32;
                    break;
                }
                break;
        }
        int index;
        switch (obj) {
            case null:
            case 1:
                if (this.cover == null || this.coverPictureType != (byte) 3) {
                    AttachedPicture picture = parseAttachedPictureFrame(frame);
                    if (this.cover == null || picture.type == (byte) 3 || picture.type == (byte) 0) {
                        try {
                            byte[] bytes = picture.imageData;
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
                                }
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                        this.coverPictureType = picture.type;
                        return;
                    }
                    return;
                }
                return;
            case 2:
            case 3:
                CommentOrUnsynchronizedLyrics comm = parseCommentOrUnsynchronizedLyricsFrame(frame);
                if (this.comment == null || comm.description == null || "".equals(comm.description)) {
                    this.comment = comm.text;
                    return;
                }
                return;
            case 4:
            case 5:
                this.album = parseTextFrame(frame);
                return;
            case 6:
            case 7:
                this.compilation = BuildConfig.VERSION_NAME.equals(parseTextFrame(frame));
                return;
            case 8:
            case 9:
                this.composer = parseTextFrame(frame);
                return;
            case 10:
            case 11:
                String tcon = parseTextFrame(frame);
                if (tcon.length() > 0) {
                    this.genre = tcon;
                    ID3v1Genre id3v1Genre = null;
                    try {
                        if (tcon.charAt(0) == '(') {
                            int pos = tcon.indexOf(41);
                            if (pos > 1) {
                                id3v1Genre = ID3v1Genre.getGenre(Integer.parseInt(tcon.substring(1, pos)));
                                if (id3v1Genre == null && tcon.length() > pos + 1) {
                                    this.genre = tcon.substring(pos + 1);
                                }
                            }
                        } else {
                            id3v1Genre = ID3v1Genre.getGenre(Integer.parseInt(tcon));
                        }
                        if (id3v1Genre != null) {
                            this.genre = id3v1Genre.getDescription();
                            return;
                        }
                        return;
                    } catch (NumberFormatException e2) {
                        return;
                    }
                }
                return;
            case 12:
            case 13:
                this.copyright = parseTextFrame(frame);
                return;
            case 14:
                String tdrc = parseTextFrame(frame);
                if (tdrc.length() >= 4) {
                    try {
                        this.year = Short.valueOf(tdrc.substring(0, 4)).shortValue();
                        return;
                    } catch (NumberFormatException e3) {
                        if (LOGGER.isLoggable(this.debugLevel)) {
                            LOGGER.log(this.debugLevel, "Could not parse year from: " + tdrc);
                            return;
                        }
                        return;
                    }
                }
                return;
            case 15:
            case 16:
                String tlen = parseTextFrame(frame);
                try {
                    this.duration = Long.valueOf(tlen).longValue();
                    return;
                } catch (NumberFormatException e4) {
                    if (LOGGER.isLoggable(this.debugLevel)) {
                        LOGGER.log(this.debugLevel, "Could not parse track duration: " + tlen);
                        return;
                    }
                    return;
                }
            case 17:
            case 18:
                this.artist = parseTextFrame(frame);
                return;
            case 19:
            case 20:
                this.albumArtist = parseTextFrame(frame);
                return;
            case 21:
            case 22:
                String tpos = parseTextFrame(frame);
                if (tpos.length() > 0) {
                    index = tpos.indexOf(47);
                    if (index < 0) {
                        try {
                            this.disc = Short.valueOf(tpos).shortValue();
                            return;
                        } catch (NumberFormatException e5) {
                            if (LOGGER.isLoggable(this.debugLevel)) {
                                LOGGER.log(this.debugLevel, "Could not parse disc number: " + tpos);
                                return;
                            }
                            return;
                        }
                    }
                    try {
                        this.disc = Short.valueOf(tpos.substring(0, index)).shortValue();
                    } catch (NumberFormatException e6) {
                        if (LOGGER.isLoggable(this.debugLevel)) {
                            LOGGER.log(this.debugLevel, "Could not parse disc number: " + tpos);
                        }
                    }
                    try {
                        this.discs = Short.valueOf(tpos.substring(index + 1)).shortValue();
                        return;
                    } catch (NumberFormatException e7) {
                        if (LOGGER.isLoggable(this.debugLevel)) {
                            LOGGER.log(this.debugLevel, "Could not parse number of discs: " + tpos);
                            return;
                        }
                        return;
                    }
                }
                return;
            case 23:
            case 24:
                String trck = parseTextFrame(frame);
                if (trck.length() > 0) {
                    index = trck.indexOf(47);
                    if (index < 0) {
                        try {
                            this.track = Short.valueOf(trck).shortValue();
                            return;
                        } catch (NumberFormatException e8) {
                            if (LOGGER.isLoggable(this.debugLevel)) {
                                LOGGER.log(this.debugLevel, "Could not parse track number: " + trck);
                                return;
                            }
                            return;
                        }
                    }
                    try {
                        this.track = Short.valueOf(trck.substring(0, index)).shortValue();
                    } catch (NumberFormatException e9) {
                        if (LOGGER.isLoggable(this.debugLevel)) {
                            LOGGER.log(this.debugLevel, "Could not parse track number: " + trck);
                        }
                    }
                    try {
                        this.tracks = Short.valueOf(trck.substring(index + 1)).shortValue();
                        return;
                    } catch (NumberFormatException e10) {
                        if (LOGGER.isLoggable(this.debugLevel)) {
                            LOGGER.log(this.debugLevel, "Could not parse number of tracks: " + trck);
                            return;
                        }
                        return;
                    }
                }
                return;
            case 25:
            case 26:
                this.grouping = parseTextFrame(frame);
                return;
            case 27:
            case 28:
                this.title = parseTextFrame(frame);
                return;
            case 29:
            case 30:
                String tyer = parseTextFrame(frame);
                if (tyer.length() > 0) {
                    try {
                        this.year = Short.valueOf(tyer).shortValue();
                        return;
                    } catch (NumberFormatException e11) {
                        if (LOGGER.isLoggable(this.debugLevel)) {
                            LOGGER.log(this.debugLevel, "Could not parse year: " + tyer);
                            return;
                        }
                        return;
                    }
                }
                return;
            case 31:
            case 32:
                if (this.lyrics == null) {
                    this.lyrics = parseCommentOrUnsynchronizedLyricsFrame(frame).text;
                    return;
                }
                return;
            default:
                return;
        }
    }

    String parseTextFrame(ID3v2FrameBody frame) throws IOException, ID3v2Exception {
        return frame.readFixedLengthString((int) frame.getRemainingLength(), frame.readEncoding());
    }

    CommentOrUnsynchronizedLyrics parseCommentOrUnsynchronizedLyricsFrame(ID3v2FrameBody data) throws IOException, ID3v2Exception {
        ID3v2Encoding encoding = data.readEncoding();
        return new CommentOrUnsynchronizedLyrics(data.readFixedLengthString(3, ID3v2Encoding.ISO_8859_1), data.readZeroTerminatedString(200, encoding), data.readFixedLengthString((int) data.getRemainingLength(), encoding));
    }

    AttachedPicture parseAttachedPictureFrame(ID3v2FrameBody data) throws IOException, ID3v2Exception {
        String imageType;
        ID3v2Encoding encoding = data.readEncoding();
        if (data.getTagHeader().getVersion() == 2) {
            String toUpperCase = data.readFixedLengthString(3, ID3v2Encoding.ISO_8859_1).toUpperCase();
            Object obj = -1;
            switch (toUpperCase.hashCode()) {
                case 73665:
                    if (toUpperCase.equals("JPG")) {
                        obj = 1;
                        break;
                    }
                    break;
                case 79369:
                    if (toUpperCase.equals("PNG")) {
                        obj = null;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    imageType = "image/png";
                    break;
                case 1:
                    imageType = "image/jpeg";
                    break;
                default:
                    imageType = "image/unknown";
                    break;
            }
        }
        imageType = data.readZeroTerminatedString(20, ID3v2Encoding.ISO_8859_1);
        return new AttachedPicture(data.getData().readByte(), data.readZeroTerminatedString(200, encoding), imageType, data.getData().readFully((int) data.getRemainingLength()));
    }
}
