package org.telegram.messenger.audioinfo.m4a;

import com.google.android.gms.common.data.DataBufferSafeParcelable;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.messenger.audioinfo.AudioInfo;

public class M4AInfo extends AudioInfo {
    private static final String ASCII = "ISO8859_1";
    static final Logger LOGGER = Logger.getLogger(M4AInfo.class.getName());
    private static final String UTF_8 = "UTF-8";
    private final Level debugLevel;
    private byte rating;
    private BigDecimal speed;
    private short tempo;
    private BigDecimal volume;

    public M4AInfo(InputStream inputStream) {
        this(inputStream, Level.FINEST);
    }

    public M4AInfo(InputStream inputStream, Level level) {
        this.debugLevel = level;
        MP4Input mP4Input = new MP4Input(inputStream);
        if (LOGGER.isLoggable(level)) {
            LOGGER.log(level, mP4Input.toString());
        }
        ftyp(mP4Input.nextChild("ftyp"));
        moov(mP4Input.nextChildUpTo("moov"));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void data(org.telegram.messenger.audioinfo.m4a.MP4Atom r9) {
        /*
        r8 = this;
        r7 = 800; // 0x320 float:1.121E-42 double:3.953E-321;
        r3 = 2;
        r2 = 1;
        r4 = 4;
        r0 = 0;
        r1 = LOGGER;
        r5 = r8.debugLevel;
        r1 = r1.isLoggable(r5);
        if (r1 == 0) goto L_0x001b;
    L_0x0010:
        r1 = LOGGER;
        r5 = r8.debugLevel;
        r6 = r9.toString();
        r1.log(r5, r6);
    L_0x001b:
        r9.skip(r4);
        r9.skip(r4);
        r1 = r9.getParent();
        r5 = r1.getType();
        r1 = -1;
        r6 = r5.hashCode();
        switch(r6) {
            case 2954818: goto L_0x0040;
            case 3059752: goto L_0x0077;
            case 3060304: goto L_0x0082;
            case 3060591: goto L_0x008d;
            case 3083677: goto L_0x00b1;
            case 3177818: goto L_0x00be;
            case 3511163: goto L_0x00ff;
            case 3564088: goto L_0x010c;
            case 3568737: goto L_0x0119;
            case 5099770: goto L_0x004b;
            case 5131342: goto L_0x0036;
            case 5133313: goto L_0x0056;
            case 5133368: goto L_0x0061;
            case 5133411: goto L_0x0099;
            case 5133907: goto L_0x00a5;
            case 5136903: goto L_0x00cb;
            case 5137308: goto L_0x00d8;
            case 5142332: goto L_0x00e5;
            case 5143505: goto L_0x00f2;
            case 5152688: goto L_0x006c;
            default: goto L_0x0031;
        };
    L_0x0031:
        r0 = r1;
    L_0x0032:
        switch(r0) {
            case 0: goto L_0x0126;
            case 1: goto L_0x0131;
            case 2: goto L_0x013c;
            case 3: goto L_0x0147;
            case 4: goto L_0x0152;
            case 5: goto L_0x0152;
            case 6: goto L_0x016d;
            case 7: goto L_0x01f5;
            case 8: goto L_0x01fd;
            case 9: goto L_0x01fd;
            case 10: goto L_0x0218;
            case 11: goto L_0x023e;
            case 12: goto L_0x024f;
            case 13: goto L_0x0288;
            case 14: goto L_0x02a3;
            case 15: goto L_0x02ae;
            case 16: goto L_0x02b9;
            case 17: goto L_0x02c4;
            case 18: goto L_0x02cc;
            case 19: goto L_0x02d4;
            default: goto L_0x0035;
        };
    L_0x0035:
        return;
    L_0x0036:
        r2 = "©alb";
        r2 = r5.equals(r2);
        if (r2 == 0) goto L_0x0031;
    L_0x003f:
        goto L_0x0032;
    L_0x0040:
        r0 = "aART";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x0049:
        r0 = r2;
        goto L_0x0032;
    L_0x004b:
        r0 = "©ART";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x0054:
        r0 = r3;
        goto L_0x0032;
    L_0x0056:
        r0 = "©cmt";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x005f:
        r0 = 3;
        goto L_0x0032;
    L_0x0061:
        r0 = "©com";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x006a:
        r0 = r4;
        goto L_0x0032;
    L_0x006c:
        r0 = "©wrt";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x0075:
        r0 = 5;
        goto L_0x0032;
    L_0x0077:
        r0 = "covr";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x0080:
        r0 = 6;
        goto L_0x0032;
    L_0x0082:
        r0 = "cpil";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x008b:
        r0 = 7;
        goto L_0x0032;
    L_0x008d:
        r0 = "cprt";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x0096:
        r0 = 8;
        goto L_0x0032;
    L_0x0099:
        r0 = "©cpy";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x00a2:
        r0 = 9;
        goto L_0x0032;
    L_0x00a5:
        r0 = "©day";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x00ae:
        r0 = 10;
        goto L_0x0032;
    L_0x00b1:
        r0 = "disk";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x00ba:
        r0 = 11;
        goto L_0x0032;
    L_0x00be:
        r0 = "gnre";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x00c7:
        r0 = 12;
        goto L_0x0032;
    L_0x00cb:
        r0 = "©gen";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x00d4:
        r0 = 13;
        goto L_0x0032;
    L_0x00d8:
        r0 = "©grp";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x00e1:
        r0 = 14;
        goto L_0x0032;
    L_0x00e5:
        r0 = "©lyr";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x00ee:
        r0 = 15;
        goto L_0x0032;
    L_0x00f2:
        r0 = "©nam";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x00fb:
        r0 = 16;
        goto L_0x0032;
    L_0x00ff:
        r0 = "rtng";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x0108:
        r0 = 17;
        goto L_0x0032;
    L_0x010c:
        r0 = "tmpo";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x0115:
        r0 = 18;
        goto L_0x0032;
    L_0x0119:
        r0 = "trkn";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0031;
    L_0x0122:
        r0 = 19;
        goto L_0x0032;
    L_0x0126:
        r0 = "UTF-8";
        r0 = r9.readString(r0);
        r8.album = r0;
        goto L_0x0035;
    L_0x0131:
        r0 = "UTF-8";
        r0 = r9.readString(r0);
        r8.albumArtist = r0;
        goto L_0x0035;
    L_0x013c:
        r0 = "UTF-8";
        r0 = r9.readString(r0);
        r8.artist = r0;
        goto L_0x0035;
    L_0x0147:
        r0 = "UTF-8";
        r0 = r9.readString(r0);
        r8.comment = r0;
        goto L_0x0035;
    L_0x0152:
        r0 = r8.composer;
        if (r0 == 0) goto L_0x0162;
    L_0x0156:
        r0 = r8.composer;
        r0 = r0.trim();
        r0 = r0.length();
        if (r0 != 0) goto L_0x0035;
    L_0x0162:
        r0 = "UTF-8";
        r0 = r9.readString(r0);
        r8.composer = r0;
        goto L_0x0035;
    L_0x016d:
        r1 = r9.readBytes();	 Catch:{ Exception -> 0x01ea }
        r2 = new android.graphics.BitmapFactory$Options;	 Catch:{ Exception -> 0x01ea }
        r2.<init>();	 Catch:{ Exception -> 0x01ea }
        r0 = 1;
        r2.inJustDecodeBounds = r0;	 Catch:{ Exception -> 0x01ea }
        r0 = 1;
        r2.inSampleSize = r0;	 Catch:{ Exception -> 0x01ea }
        r0 = 0;
        r3 = r1.length;	 Catch:{ Exception -> 0x01ea }
        android.graphics.BitmapFactory.decodeByteArray(r1, r0, r3, r2);	 Catch:{ Exception -> 0x01ea }
        r0 = r2.outWidth;	 Catch:{ Exception -> 0x01ea }
        if (r0 > r7) goto L_0x0189;
    L_0x0185:
        r0 = r2.outHeight;	 Catch:{ Exception -> 0x01ea }
        if (r0 <= r7) goto L_0x019c;
    L_0x0189:
        r0 = r2.outWidth;	 Catch:{ Exception -> 0x01ea }
        r3 = r2.outHeight;	 Catch:{ Exception -> 0x01ea }
        r0 = java.lang.Math.max(r0, r3);	 Catch:{ Exception -> 0x01ea }
    L_0x0191:
        if (r0 <= r7) goto L_0x019c;
    L_0x0193:
        r3 = r2.inSampleSize;	 Catch:{ Exception -> 0x01ea }
        r3 = r3 * 2;
        r2.inSampleSize = r3;	 Catch:{ Exception -> 0x01ea }
        r0 = r0 / 2;
        goto L_0x0191;
    L_0x019c:
        r0 = 0;
        r2.inJustDecodeBounds = r0;	 Catch:{ Exception -> 0x01ea }
        r0 = 0;
        r3 = r1.length;	 Catch:{ Exception -> 0x01ea }
        r0 = android.graphics.BitmapFactory.decodeByteArray(r1, r0, r3, r2);	 Catch:{ Exception -> 0x01ea }
        r8.cover = r0;	 Catch:{ Exception -> 0x01ea }
        r0 = r8.cover;	 Catch:{ Exception -> 0x01ea }
        if (r0 == 0) goto L_0x0035;
    L_0x01ab:
        r0 = r8.cover;	 Catch:{ Exception -> 0x01ea }
        r0 = r0.getWidth();	 Catch:{ Exception -> 0x01ea }
        r1 = r8.cover;	 Catch:{ Exception -> 0x01ea }
        r1 = r1.getHeight();	 Catch:{ Exception -> 0x01ea }
        r0 = java.lang.Math.max(r0, r1);	 Catch:{ Exception -> 0x01ea }
        r0 = (float) r0;	 Catch:{ Exception -> 0x01ea }
        r1 = 1123024896; // 0x42f00000 float:120.0 double:5.548480205E-315;
        r0 = r0 / r1;
        r1 = 0;
        r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1));
        if (r1 <= 0) goto L_0x01f0;
    L_0x01c4:
        r1 = r8.cover;	 Catch:{ Exception -> 0x01ea }
        r2 = r8.cover;	 Catch:{ Exception -> 0x01ea }
        r2 = r2.getWidth();	 Catch:{ Exception -> 0x01ea }
        r2 = (float) r2;	 Catch:{ Exception -> 0x01ea }
        r2 = r2 / r0;
        r2 = (int) r2;	 Catch:{ Exception -> 0x01ea }
        r3 = r8.cover;	 Catch:{ Exception -> 0x01ea }
        r3 = r3.getHeight();	 Catch:{ Exception -> 0x01ea }
        r3 = (float) r3;	 Catch:{ Exception -> 0x01ea }
        r0 = r3 / r0;
        r0 = (int) r0;	 Catch:{ Exception -> 0x01ea }
        r3 = 1;
        r0 = android.graphics.Bitmap.createScaledBitmap(r1, r2, r0, r3);	 Catch:{ Exception -> 0x01ea }
        r8.smallCover = r0;	 Catch:{ Exception -> 0x01ea }
    L_0x01e0:
        r0 = r8.smallCover;	 Catch:{ Exception -> 0x01ea }
        if (r0 != 0) goto L_0x0035;
    L_0x01e4:
        r0 = r8.cover;	 Catch:{ Exception -> 0x01ea }
        r8.smallCover = r0;	 Catch:{ Exception -> 0x01ea }
        goto L_0x0035;
    L_0x01ea:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0035;
    L_0x01f0:
        r0 = r8.cover;	 Catch:{ Exception -> 0x01ea }
        r8.smallCover = r0;	 Catch:{ Exception -> 0x01ea }
        goto L_0x01e0;
    L_0x01f5:
        r0 = r9.readBoolean();
        r8.compilation = r0;
        goto L_0x0035;
    L_0x01fd:
        r0 = r8.copyright;
        if (r0 == 0) goto L_0x020d;
    L_0x0201:
        r0 = r8.copyright;
        r0 = r0.trim();
        r0 = r0.length();
        if (r0 != 0) goto L_0x0035;
    L_0x020d:
        r0 = "UTF-8";
        r0 = r9.readString(r0);
        r8.copyright = r0;
        goto L_0x0035;
    L_0x0218:
        r0 = "UTF-8";
        r0 = r9.readString(r0);
        r0 = r0.trim();
        r1 = r0.length();
        if (r1 < r4) goto L_0x0035;
    L_0x0229:
        r1 = 0;
        r2 = 4;
        r0 = r0.substring(r1, r2);	 Catch:{ NumberFormatException -> 0x023b }
        r0 = java.lang.Short.valueOf(r0);	 Catch:{ NumberFormatException -> 0x023b }
        r0 = r0.shortValue();	 Catch:{ NumberFormatException -> 0x023b }
        r8.year = r0;	 Catch:{ NumberFormatException -> 0x023b }
        goto L_0x0035;
    L_0x023b:
        r0 = move-exception;
        goto L_0x0035;
    L_0x023e:
        r9.skip(r3);
        r0 = r9.readShort();
        r8.disc = r0;
        r0 = r9.readShort();
        r8.discs = r0;
        goto L_0x0035;
    L_0x024f:
        r0 = r8.genre;
        if (r0 == 0) goto L_0x025f;
    L_0x0253:
        r0 = r8.genre;
        r0 = r0.trim();
        r0 = r0.length();
        if (r0 != 0) goto L_0x0035;
    L_0x025f:
        r0 = r9.getRemaining();
        r2 = 2;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 != 0) goto L_0x027d;
    L_0x0269:
        r0 = r9.readShort();
        r0 = r0 + -1;
        r0 = org.telegram.messenger.audioinfo.mp3.ID3v1Genre.getGenre(r0);
        if (r0 == 0) goto L_0x0035;
    L_0x0275:
        r0 = r0.getDescription();
        r8.genre = r0;
        goto L_0x0035;
    L_0x027d:
        r0 = "UTF-8";
        r0 = r9.readString(r0);
        r8.genre = r0;
        goto L_0x0035;
    L_0x0288:
        r0 = r8.genre;
        if (r0 == 0) goto L_0x0298;
    L_0x028c:
        r0 = r8.genre;
        r0 = r0.trim();
        r0 = r0.length();
        if (r0 != 0) goto L_0x0035;
    L_0x0298:
        r0 = "UTF-8";
        r0 = r9.readString(r0);
        r8.genre = r0;
        goto L_0x0035;
    L_0x02a3:
        r0 = "UTF-8";
        r0 = r9.readString(r0);
        r8.grouping = r0;
        goto L_0x0035;
    L_0x02ae:
        r0 = "UTF-8";
        r0 = r9.readString(r0);
        r8.lyrics = r0;
        goto L_0x0035;
    L_0x02b9:
        r0 = "UTF-8";
        r0 = r9.readString(r0);
        r8.title = r0;
        goto L_0x0035;
    L_0x02c4:
        r0 = r9.readByte();
        r8.rating = r0;
        goto L_0x0035;
    L_0x02cc:
        r0 = r9.readShort();
        r8.tempo = r0;
        goto L_0x0035;
    L_0x02d4:
        r9.skip(r3);
        r0 = r9.readShort();
        r8.track = r0;
        r0 = r9.readShort();
        r8.tracks = r0;
        goto L_0x0035;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.audioinfo.m4a.M4AInfo.data(org.telegram.messenger.audioinfo.m4a.MP4Atom):void");
    }

    void ftyp(MP4Atom mP4Atom) {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, mP4Atom.toString());
        }
        this.brand = mP4Atom.readString(4, ASCII).trim();
        if (this.brand.matches("M4V|MP4|mp42|isom")) {
            LOGGER.warning(mP4Atom.getPath() + ": brand=" + this.brand + " (experimental)");
        } else if (!this.brand.matches("M4A|M4P")) {
            LOGGER.warning(mP4Atom.getPath() + ": brand=" + this.brand + " (expected M4A or M4P)");
        }
        this.version = String.valueOf(mP4Atom.readInt());
    }

    public byte getRating() {
        return this.rating;
    }

    public BigDecimal getSpeed() {
        return this.speed;
    }

    public short getTempo() {
        return this.tempo;
    }

    public BigDecimal getVolume() {
        return this.volume;
    }

    void ilst(MP4Atom mP4Atom) {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, mP4Atom.toString());
        }
        while (mP4Atom.hasMoreChildren()) {
            MP4Atom nextChild = mP4Atom.nextChild();
            if (LOGGER.isLoggable(this.debugLevel)) {
                LOGGER.log(this.debugLevel, nextChild.toString());
            }
            if (nextChild.getRemaining() != 0) {
                data(nextChild.nextChildUpTo(DataBufferSafeParcelable.DATA_FIELD));
            } else if (LOGGER.isLoggable(this.debugLevel)) {
                LOGGER.log(this.debugLevel, nextChild.getPath() + ": contains no value");
            }
        }
    }

    void mdhd(MP4Atom mP4Atom) {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, mP4Atom.toString());
        }
        byte readByte = mP4Atom.readByte();
        mP4Atom.skip(3);
        mP4Atom.skip(readByte == (byte) 1 ? 16 : 8);
        int readInt = mP4Atom.readInt();
        long readLong = readByte == (byte) 1 ? mP4Atom.readLong() : (long) mP4Atom.readInt();
        if (this.duration == 0) {
            this.duration = (readLong * 1000) / ((long) readInt);
        } else if (LOGGER.isLoggable(this.debugLevel) && Math.abs(this.duration - ((1000 * readLong) / ((long) readInt))) > 2) {
            LOGGER.log(this.debugLevel, "mdhd: duration " + this.duration + " -> " + ((readLong * 1000) / ((long) readInt)));
        }
    }

    void mdia(MP4Atom mP4Atom) {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, mP4Atom.toString());
        }
        mdhd(mP4Atom.nextChild("mdhd"));
    }

    void meta(MP4Atom mP4Atom) {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, mP4Atom.toString());
        }
        mP4Atom.skip(4);
        while (mP4Atom.hasMoreChildren()) {
            MP4Atom nextChild = mP4Atom.nextChild();
            if ("ilst".equals(nextChild.getType())) {
                ilst(nextChild);
                return;
            }
        }
    }

    void moov(MP4Atom mP4Atom) {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, mP4Atom.toString());
        }
        while (mP4Atom.hasMoreChildren()) {
            MP4Atom nextChild = mP4Atom.nextChild();
            String type = nextChild.getType();
            Object obj = -1;
            switch (type.hashCode()) {
                case 3363941:
                    if (type.equals("mvhd")) {
                        obj = null;
                        break;
                    }
                    break;
                case 3568424:
                    if (type.equals("trak")) {
                        obj = 1;
                        break;
                    }
                    break;
                case 3585340:
                    if (type.equals("udta")) {
                        obj = 2;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    mvhd(nextChild);
                    break;
                case 1:
                    trak(nextChild);
                    break;
                case 2:
                    udta(nextChild);
                    break;
                default:
                    break;
            }
        }
    }

    void mvhd(MP4Atom mP4Atom) {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, mP4Atom.toString());
        }
        byte readByte = mP4Atom.readByte();
        mP4Atom.skip(3);
        mP4Atom.skip(readByte == (byte) 1 ? 16 : 8);
        int readInt = mP4Atom.readInt();
        long readLong = readByte == (byte) 1 ? mP4Atom.readLong() : (long) mP4Atom.readInt();
        if (this.duration == 0) {
            this.duration = (readLong * 1000) / ((long) readInt);
        } else if (LOGGER.isLoggable(this.debugLevel) && Math.abs(this.duration - ((1000 * readLong) / ((long) readInt))) > 2) {
            LOGGER.log(this.debugLevel, "mvhd: duration " + this.duration + " -> " + ((readLong * 1000) / ((long) readInt)));
        }
        this.speed = mP4Atom.readIntegerFixedPoint();
        this.volume = mP4Atom.readShortFixedPoint();
    }

    void trak(MP4Atom mP4Atom) {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, mP4Atom.toString());
        }
        mdia(mP4Atom.nextChildUpTo("mdia"));
    }

    void udta(MP4Atom mP4Atom) {
        if (LOGGER.isLoggable(this.debugLevel)) {
            LOGGER.log(this.debugLevel, mP4Atom.toString());
        }
        while (mP4Atom.hasMoreChildren()) {
            MP4Atom nextChild = mP4Atom.nextChild();
            if ("meta".equals(nextChild.getType())) {
                meta(nextChild);
                return;
            }
        }
    }
}
