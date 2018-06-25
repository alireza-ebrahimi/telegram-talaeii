package org.telegram.messenger.audioinfo.mp3;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

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

        public AttachedPicture(byte b, String str, String str2, byte[] bArr) {
            this.type = b;
            this.description = str;
            this.imageType = str2;
            this.imageData = bArr;
        }
    }

    static class CommentOrUnsynchronizedLyrics {
        final String description;
        final String language;
        final String text;

        public CommentOrUnsynchronizedLyrics(String str, String str2, String str3) {
            this.language = str;
            this.description = str2;
            this.text = str3;
        }
    }

    public ID3v2Info(InputStream inputStream) {
        this(inputStream, Level.FINEST);
    }

    public ID3v2Info(InputStream inputStream, Level level) {
        this.debugLevel = level;
        if (isID3v2StartPosition(inputStream)) {
            ID3v2TagHeader iD3v2TagHeader = new ID3v2TagHeader(inputStream);
            this.brand = "ID3";
            this.version = String.format("2.%d.%d", new Object[]{Integer.valueOf(iD3v2TagHeader.getVersion()), Integer.valueOf(iD3v2TagHeader.getRevision())});
            ID3v2TagBody tagBody = iD3v2TagHeader.tagBody(inputStream);
            while (tagBody.getRemainingLength() > 10) {
                ID3v2FrameHeader iD3v2FrameHeader = new ID3v2FrameHeader(tagBody);
                if (iD3v2FrameHeader.isPadding()) {
                    break loop0;
                } else if (((long) iD3v2FrameHeader.getBodySize()) > tagBody.getRemainingLength()) {
                    if (LOGGER.isLoggable(level)) {
                        LOGGER.log(level, "ID3 frame claims to extend frames area");
                    }
                } else if (!iD3v2FrameHeader.isValid() || iD3v2FrameHeader.isEncryption()) {
                    tagBody.getData().skipFully((long) iD3v2FrameHeader.getBodySize());
                } else {
                    ID3v2FrameBody frameBody = tagBody.frameBody(iD3v2FrameHeader);
                    try {
                        parseFrame(frameBody);
                        frameBody.getData().skipFully(frameBody.getRemainingLength());
                    } catch (ID3v2Exception e) {
                        try {
                            if (LOGGER.isLoggable(level)) {
                                LOGGER.log(level, String.format("ID3 exception occured in frame %s: %s", new Object[]{iD3v2FrameHeader.getFrameId(), e.getMessage()}));
                            }
                            frameBody.getData().skipFully(frameBody.getRemainingLength());
                        } catch (ID3v2Exception e2) {
                            if (LOGGER.isLoggable(level)) {
                                LOGGER.log(level, "ID3 exception occured: " + e2.getMessage());
                            }
                        }
                    } catch (Throwable th) {
                        frameBody.getData().skipFully(frameBody.getRemainingLength());
                        throw th;
                    }
                }
            }
            tagBody.getData().skipFully(tagBody.getRemainingLength());
            if (iD3v2TagHeader.getFooterSize() > 0) {
                inputStream.skip((long) iD3v2TagHeader.getFooterSize());
            }
        }
    }

    public static boolean isID3v2StartPosition(InputStream inputStream) {
        inputStream.mark(3);
        try {
            boolean z = inputStream.read() == 73 && inputStream.read() == 68 && inputStream.read() == 51;
            inputStream.reset();
            return z;
        } catch (Throwable th) {
            inputStream.reset();
        }
    }

    AttachedPicture parseAttachedPictureFrame(ID3v2FrameBody iD3v2FrameBody) {
        String str;
        ID3v2Encoding readEncoding = iD3v2FrameBody.readEncoding();
        if (iD3v2FrameBody.getTagHeader().getVersion() == 2) {
            String toUpperCase = iD3v2FrameBody.readFixedLengthString(3, ID3v2Encoding.ISO_8859_1).toUpperCase();
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
                    str = "image/png";
                    break;
                case 1:
                    str = "image/jpeg";
                    break;
                default:
                    str = "image/unknown";
                    break;
            }
        }
        str = iD3v2FrameBody.readZeroTerminatedString(20, ID3v2Encoding.ISO_8859_1);
        return new AttachedPicture(iD3v2FrameBody.getData().readByte(), iD3v2FrameBody.readZeroTerminatedString(Callback.DEFAULT_DRAG_ANIMATION_DURATION, readEncoding), str, iD3v2FrameBody.getData().readFully((int) iD3v2FrameBody.getRemainingLength()));
    }

    CommentOrUnsynchronizedLyrics parseCommentOrUnsynchronizedLyricsFrame(ID3v2FrameBody iD3v2FrameBody) {
        ID3v2Encoding readEncoding = iD3v2FrameBody.readEncoding();
        return new CommentOrUnsynchronizedLyrics(iD3v2FrameBody.readFixedLengthString(3, ID3v2Encoding.ISO_8859_1), iD3v2FrameBody.readZeroTerminatedString(Callback.DEFAULT_DRAG_ANIMATION_DURATION, readEncoding), iD3v2FrameBody.readFixedLengthString((int) iD3v2FrameBody.getRemainingLength(), readEncoding));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void parseFrame(org.telegram.messenger.audioinfo.mp3.ID3v2FrameBody r10) {
        /*
        r9 = this;
        r8 = 800; // 0x320 float:1.121E-42 double:3.953E-321;
        r4 = 4;
        r3 = 3;
        r2 = 1;
        r0 = 0;
        r1 = LOGGER;
        r5 = r9.debugLevel;
        r1 = r1.isLoggable(r5);
        if (r1 == 0) goto L_0x0033;
    L_0x0010:
        r1 = LOGGER;
        r5 = r9.debugLevel;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "Parsing frame: ";
        r6 = r6.append(r7);
        r7 = r10.getFrameHeader();
        r7 = r7.getFrameId();
        r6 = r6.append(r7);
        r6 = r6.toString();
        r1.log(r5, r6);
    L_0x0033:
        r1 = r10.getFrameHeader();
        r5 = r1.getFrameId();
        r1 = -1;
        r6 = r5.hashCode();
        switch(r6) {
            case 66913: goto L_0x005d;
            case 79210: goto L_0x0048;
            case 82815: goto L_0x0073;
            case 82878: goto L_0x009f;
            case 82880: goto L_0x00b7;
            case 82881: goto L_0x0089;
            case 82883: goto L_0x00d0;
            case 83149: goto L_0x00f7;
            case 83253: goto L_0x0111;
            case 83254: goto L_0x012b;
            case 83269: goto L_0x0145;
            case 83341: goto L_0x015f;
            case 83377: goto L_0x0179;
            case 83378: goto L_0x0193;
            case 83552: goto L_0x01ad;
            case 84125: goto L_0x01c7;
            case 2015625: goto L_0x0052;
            case 2074380: goto L_0x0068;
            case 2567331: goto L_0x007e;
            case 2569298: goto L_0x0094;
            case 2569357: goto L_0x00ab;
            case 2569358: goto L_0x00c3;
            case 2569360: goto L_0x00dd;
            case 2570401: goto L_0x00ea;
            case 2575250: goto L_0x0186;
            case 2575251: goto L_0x01a0;
            case 2577697: goto L_0x0104;
            case 2581512: goto L_0x011e;
            case 2581513: goto L_0x0138;
            case 2581856: goto L_0x0152;
            case 2583398: goto L_0x016c;
            case 2590194: goto L_0x01ba;
            case 2614438: goto L_0x01d4;
            default: goto L_0x0043;
        };
    L_0x0043:
        r0 = r1;
    L_0x0044:
        switch(r0) {
            case 0: goto L_0x01e1;
            case 1: goto L_0x01e1;
            case 2: goto L_0x0282;
            case 3: goto L_0x0282;
            case 4: goto L_0x029f;
            case 5: goto L_0x029f;
            case 6: goto L_0x02a7;
            case 7: goto L_0x02a7;
            case 8: goto L_0x02b6;
            case 9: goto L_0x02b6;
            case 10: goto L_0x02be;
            case 11: goto L_0x02be;
            case 12: goto L_0x0311;
            case 13: goto L_0x0311;
            case 14: goto L_0x0319;
            case 15: goto L_0x035d;
            case 16: goto L_0x035d;
            case 17: goto L_0x0395;
            case 18: goto L_0x0395;
            case 19: goto L_0x039d;
            case 20: goto L_0x039d;
            case 21: goto L_0x03a5;
            case 22: goto L_0x03a5;
            case 23: goto L_0x045b;
            case 24: goto L_0x045b;
            case 25: goto L_0x0511;
            case 26: goto L_0x0511;
            case 27: goto L_0x0519;
            case 28: goto L_0x0519;
            case 29: goto L_0x0521;
            case 30: goto L_0x0521;
            case 31: goto L_0x055f;
            case 32: goto L_0x055f;
            default: goto L_0x0047;
        };
    L_0x0047:
        return;
    L_0x0048:
        r6 = "PIC";
        r5 = r5.equals(r6);
        if (r5 == 0) goto L_0x0043;
    L_0x0051:
        goto L_0x0044;
    L_0x0052:
        r0 = "APIC";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x005b:
        r0 = r2;
        goto L_0x0044;
    L_0x005d:
        r0 = "COM";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x0066:
        r0 = 2;
        goto L_0x0044;
    L_0x0068:
        r0 = "COMM";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x0071:
        r0 = r3;
        goto L_0x0044;
    L_0x0073:
        r0 = "TAL";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x007c:
        r0 = r4;
        goto L_0x0044;
    L_0x007e:
        r0 = "TALB";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x0087:
        r0 = 5;
        goto L_0x0044;
    L_0x0089:
        r0 = "TCP";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x0092:
        r0 = 6;
        goto L_0x0044;
    L_0x0094:
        r0 = "TCMP";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x009d:
        r0 = 7;
        goto L_0x0044;
    L_0x009f:
        r0 = "TCM";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x00a8:
        r0 = 8;
        goto L_0x0044;
    L_0x00ab:
        r0 = "TCOM";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x00b4:
        r0 = 9;
        goto L_0x0044;
    L_0x00b7:
        r0 = "TCO";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x00c0:
        r0 = 10;
        goto L_0x0044;
    L_0x00c3:
        r0 = "TCON";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x00cc:
        r0 = 11;
        goto L_0x0044;
    L_0x00d0:
        r0 = "TCR";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x00d9:
        r0 = 12;
        goto L_0x0044;
    L_0x00dd:
        r0 = "TCOP";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x00e6:
        r0 = 13;
        goto L_0x0044;
    L_0x00ea:
        r0 = "TDRC";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x00f3:
        r0 = 14;
        goto L_0x0044;
    L_0x00f7:
        r0 = "TLE";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x0100:
        r0 = 15;
        goto L_0x0044;
    L_0x0104:
        r0 = "TLEN";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x010d:
        r0 = 16;
        goto L_0x0044;
    L_0x0111:
        r0 = "TP1";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x011a:
        r0 = 17;
        goto L_0x0044;
    L_0x011e:
        r0 = "TPE1";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x0127:
        r0 = 18;
        goto L_0x0044;
    L_0x012b:
        r0 = "TP2";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x0134:
        r0 = 19;
        goto L_0x0044;
    L_0x0138:
        r0 = "TPE2";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x0141:
        r0 = 20;
        goto L_0x0044;
    L_0x0145:
        r0 = "TPA";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x014e:
        r0 = 21;
        goto L_0x0044;
    L_0x0152:
        r0 = "TPOS";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x015b:
        r0 = 22;
        goto L_0x0044;
    L_0x015f:
        r0 = "TRK";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x0168:
        r0 = 23;
        goto L_0x0044;
    L_0x016c:
        r0 = "TRCK";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x0175:
        r0 = 24;
        goto L_0x0044;
    L_0x0179:
        r0 = "TT1";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x0182:
        r0 = 25;
        goto L_0x0044;
    L_0x0186:
        r0 = "TIT1";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x018f:
        r0 = 26;
        goto L_0x0044;
    L_0x0193:
        r0 = "TT2";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x019c:
        r0 = 27;
        goto L_0x0044;
    L_0x01a0:
        r0 = "TIT2";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x01a9:
        r0 = 28;
        goto L_0x0044;
    L_0x01ad:
        r0 = "TYE";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x01b6:
        r0 = 29;
        goto L_0x0044;
    L_0x01ba:
        r0 = "TYER";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x01c3:
        r0 = 30;
        goto L_0x0044;
    L_0x01c7:
        r0 = "ULT";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x01d0:
        r0 = 31;
        goto L_0x0044;
    L_0x01d4:
        r0 = "USLT";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0043;
    L_0x01dd:
        r0 = 32;
        goto L_0x0044;
    L_0x01e1:
        r0 = r9.cover;
        if (r0 == 0) goto L_0x01e9;
    L_0x01e5:
        r0 = r9.coverPictureType;
        if (r0 == r3) goto L_0x0047;
    L_0x01e9:
        r1 = r9.parseAttachedPictureFrame(r10);
        r0 = r9.cover;
        if (r0 == 0) goto L_0x01f9;
    L_0x01f1:
        r0 = r1.type;
        if (r0 == r3) goto L_0x01f9;
    L_0x01f5:
        r0 = r1.type;
        if (r0 != 0) goto L_0x0047;
    L_0x01f9:
        r2 = r1.imageData;	 Catch:{ Throwable -> 0x027d }
        r3 = new android.graphics.BitmapFactory$Options;	 Catch:{ Throwable -> 0x027d }
        r3.<init>();	 Catch:{ Throwable -> 0x027d }
        r0 = 1;
        r3.inJustDecodeBounds = r0;	 Catch:{ Throwable -> 0x027d }
        r0 = 1;
        r3.inSampleSize = r0;	 Catch:{ Throwable -> 0x027d }
        r0 = 0;
        r4 = r2.length;	 Catch:{ Throwable -> 0x027d }
        android.graphics.BitmapFactory.decodeByteArray(r2, r0, r4, r3);	 Catch:{ Throwable -> 0x027d }
        r0 = r3.outWidth;	 Catch:{ Throwable -> 0x027d }
        if (r0 > r8) goto L_0x0213;
    L_0x020f:
        r0 = r3.outHeight;	 Catch:{ Throwable -> 0x027d }
        if (r0 <= r8) goto L_0x0226;
    L_0x0213:
        r0 = r3.outWidth;	 Catch:{ Throwable -> 0x027d }
        r4 = r3.outHeight;	 Catch:{ Throwable -> 0x027d }
        r0 = java.lang.Math.max(r0, r4);	 Catch:{ Throwable -> 0x027d }
    L_0x021b:
        if (r0 <= r8) goto L_0x0226;
    L_0x021d:
        r4 = r3.inSampleSize;	 Catch:{ Throwable -> 0x027d }
        r4 = r4 * 2;
        r3.inSampleSize = r4;	 Catch:{ Throwable -> 0x027d }
        r0 = r0 / 2;
        goto L_0x021b;
    L_0x0226:
        r0 = 0;
        r3.inJustDecodeBounds = r0;	 Catch:{ Throwable -> 0x027d }
        r0 = 0;
        r4 = r2.length;	 Catch:{ Throwable -> 0x027d }
        r0 = android.graphics.BitmapFactory.decodeByteArray(r2, r0, r4, r3);	 Catch:{ Throwable -> 0x027d }
        r9.cover = r0;	 Catch:{ Throwable -> 0x027d }
        r0 = r9.cover;	 Catch:{ Throwable -> 0x027d }
        if (r0 == 0) goto L_0x0272;
    L_0x0235:
        r0 = r9.cover;	 Catch:{ Throwable -> 0x027d }
        r0 = r0.getWidth();	 Catch:{ Throwable -> 0x027d }
        r2 = r9.cover;	 Catch:{ Throwable -> 0x027d }
        r2 = r2.getHeight();	 Catch:{ Throwable -> 0x027d }
        r0 = java.lang.Math.max(r0, r2);	 Catch:{ Throwable -> 0x027d }
        r0 = (float) r0;	 Catch:{ Throwable -> 0x027d }
        r2 = 1123024896; // 0x42f00000 float:120.0 double:5.548480205E-315;
        r0 = r0 / r2;
        r2 = 0;
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 <= 0) goto L_0x0278;
    L_0x024e:
        r2 = r9.cover;	 Catch:{ Throwable -> 0x027d }
        r3 = r9.cover;	 Catch:{ Throwable -> 0x027d }
        r3 = r3.getWidth();	 Catch:{ Throwable -> 0x027d }
        r3 = (float) r3;	 Catch:{ Throwable -> 0x027d }
        r3 = r3 / r0;
        r3 = (int) r3;	 Catch:{ Throwable -> 0x027d }
        r4 = r9.cover;	 Catch:{ Throwable -> 0x027d }
        r4 = r4.getHeight();	 Catch:{ Throwable -> 0x027d }
        r4 = (float) r4;	 Catch:{ Throwable -> 0x027d }
        r0 = r4 / r0;
        r0 = (int) r0;	 Catch:{ Throwable -> 0x027d }
        r4 = 1;
        r0 = android.graphics.Bitmap.createScaledBitmap(r2, r3, r0, r4);	 Catch:{ Throwable -> 0x027d }
        r9.smallCover = r0;	 Catch:{ Throwable -> 0x027d }
    L_0x026a:
        r0 = r9.smallCover;	 Catch:{ Throwable -> 0x027d }
        if (r0 != 0) goto L_0x0272;
    L_0x026e:
        r0 = r9.cover;	 Catch:{ Throwable -> 0x027d }
        r9.smallCover = r0;	 Catch:{ Throwable -> 0x027d }
    L_0x0272:
        r0 = r1.type;
        r9.coverPictureType = r0;
        goto L_0x0047;
    L_0x0278:
        r0 = r9.cover;	 Catch:{ Throwable -> 0x027d }
        r9.smallCover = r0;	 Catch:{ Throwable -> 0x027d }
        goto L_0x026a;
    L_0x027d:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0272;
    L_0x0282:
        r0 = r9.parseCommentOrUnsynchronizedLyricsFrame(r10);
        r1 = r9.comment;
        if (r1 == 0) goto L_0x0299;
    L_0x028a:
        r1 = r0.description;
        if (r1 == 0) goto L_0x0299;
    L_0x028e:
        r1 = "";
        r2 = r0.description;
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0047;
    L_0x0299:
        r0 = r0.text;
        r9.comment = r0;
        goto L_0x0047;
    L_0x029f:
        r0 = r9.parseTextFrame(r10);
        r9.album = r0;
        goto L_0x0047;
    L_0x02a7:
        r0 = "1";
        r1 = r9.parseTextFrame(r10);
        r0 = r0.equals(r1);
        r9.compilation = r0;
        goto L_0x0047;
    L_0x02b6:
        r0 = r9.parseTextFrame(r10);
        r9.composer = r0;
        goto L_0x0047;
    L_0x02be:
        r1 = r9.parseTextFrame(r10);
        r0 = r1.length();
        if (r0 <= 0) goto L_0x0047;
    L_0x02c8:
        r9.genre = r1;
        r0 = 0;
        r3 = 0;
        r3 = r1.charAt(r3);	 Catch:{ NumberFormatException -> 0x0305 }
        r4 = 40;
        if (r3 != r4) goto L_0x0308;
    L_0x02d4:
        r3 = 41;
        r3 = r1.indexOf(r3);	 Catch:{ NumberFormatException -> 0x0305 }
        if (r3 <= r2) goto L_0x02fb;
    L_0x02dc:
        r0 = 1;
        r0 = r1.substring(r0, r3);	 Catch:{ NumberFormatException -> 0x0305 }
        r0 = java.lang.Integer.parseInt(r0);	 Catch:{ NumberFormatException -> 0x0305 }
        r0 = org.telegram.messenger.audioinfo.mp3.ID3v1Genre.getGenre(r0);	 Catch:{ NumberFormatException -> 0x0305 }
        if (r0 != 0) goto L_0x02fb;
    L_0x02eb:
        r2 = r1.length();	 Catch:{ NumberFormatException -> 0x0305 }
        r4 = r3 + 1;
        if (r2 <= r4) goto L_0x02fb;
    L_0x02f3:
        r2 = r3 + 1;
        r1 = r1.substring(r2);	 Catch:{ NumberFormatException -> 0x0305 }
        r9.genre = r1;	 Catch:{ NumberFormatException -> 0x0305 }
    L_0x02fb:
        if (r0 == 0) goto L_0x0047;
    L_0x02fd:
        r0 = r0.getDescription();	 Catch:{ NumberFormatException -> 0x0305 }
        r9.genre = r0;	 Catch:{ NumberFormatException -> 0x0305 }
        goto L_0x0047;
    L_0x0305:
        r0 = move-exception;
        goto L_0x0047;
    L_0x0308:
        r0 = java.lang.Integer.parseInt(r1);	 Catch:{ NumberFormatException -> 0x0305 }
        r0 = org.telegram.messenger.audioinfo.mp3.ID3v1Genre.getGenre(r0);	 Catch:{ NumberFormatException -> 0x0305 }
        goto L_0x02fb;
    L_0x0311:
        r0 = r9.parseTextFrame(r10);
        r9.copyright = r0;
        goto L_0x0047;
    L_0x0319:
        r0 = r9.parseTextFrame(r10);
        r1 = r0.length();
        if (r1 < r4) goto L_0x0047;
    L_0x0323:
        r1 = 0;
        r2 = 4;
        r1 = r0.substring(r1, r2);	 Catch:{ NumberFormatException -> 0x0335 }
        r1 = java.lang.Short.valueOf(r1);	 Catch:{ NumberFormatException -> 0x0335 }
        r1 = r1.shortValue();	 Catch:{ NumberFormatException -> 0x0335 }
        r9.year = r1;	 Catch:{ NumberFormatException -> 0x0335 }
        goto L_0x0047;
    L_0x0335:
        r1 = move-exception;
        r1 = LOGGER;
        r2 = r9.debugLevel;
        r1 = r1.isLoggable(r2);
        if (r1 == 0) goto L_0x0047;
    L_0x0340:
        r1 = LOGGER;
        r2 = r9.debugLevel;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Could not parse year from: ";
        r3 = r3.append(r4);
        r0 = r3.append(r0);
        r0 = r0.toString();
        r1.log(r2, r0);
        goto L_0x0047;
    L_0x035d:
        r0 = r9.parseTextFrame(r10);
        r1 = java.lang.Long.valueOf(r0);	 Catch:{ NumberFormatException -> 0x036d }
        r2 = r1.longValue();	 Catch:{ NumberFormatException -> 0x036d }
        r9.duration = r2;	 Catch:{ NumberFormatException -> 0x036d }
        goto L_0x0047;
    L_0x036d:
        r1 = move-exception;
        r1 = LOGGER;
        r2 = r9.debugLevel;
        r1 = r1.isLoggable(r2);
        if (r1 == 0) goto L_0x0047;
    L_0x0378:
        r1 = LOGGER;
        r2 = r9.debugLevel;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Could not parse track duration: ";
        r3 = r3.append(r4);
        r0 = r3.append(r0);
        r0 = r0.toString();
        r1.log(r2, r0);
        goto L_0x0047;
    L_0x0395:
        r0 = r9.parseTextFrame(r10);
        r9.artist = r0;
        goto L_0x0047;
    L_0x039d:
        r0 = r9.parseTextFrame(r10);
        r9.albumArtist = r0;
        goto L_0x0047;
    L_0x03a5:
        r0 = r9.parseTextFrame(r10);
        r1 = r0.length();
        if (r1 <= 0) goto L_0x0047;
    L_0x03af:
        r1 = 47;
        r1 = r0.indexOf(r1);
        if (r1 >= 0) goto L_0x03eb;
    L_0x03b7:
        r1 = java.lang.Short.valueOf(r0);	 Catch:{ NumberFormatException -> 0x03c3 }
        r1 = r1.shortValue();	 Catch:{ NumberFormatException -> 0x03c3 }
        r9.disc = r1;	 Catch:{ NumberFormatException -> 0x03c3 }
        goto L_0x0047;
    L_0x03c3:
        r1 = move-exception;
        r1 = LOGGER;
        r2 = r9.debugLevel;
        r1 = r1.isLoggable(r2);
        if (r1 == 0) goto L_0x0047;
    L_0x03ce:
        r1 = LOGGER;
        r2 = r9.debugLevel;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Could not parse disc number: ";
        r3 = r3.append(r4);
        r0 = r3.append(r0);
        r0 = r0.toString();
        r1.log(r2, r0);
        goto L_0x0047;
    L_0x03eb:
        r2 = 0;
        r2 = r0.substring(r2, r1);	 Catch:{ NumberFormatException -> 0x0434 }
        r2 = java.lang.Short.valueOf(r2);	 Catch:{ NumberFormatException -> 0x0434 }
        r2 = r2.shortValue();	 Catch:{ NumberFormatException -> 0x0434 }
        r9.disc = r2;	 Catch:{ NumberFormatException -> 0x0434 }
    L_0x03fa:
        r1 = r1 + 1;
        r1 = r0.substring(r1);	 Catch:{ NumberFormatException -> 0x040c }
        r1 = java.lang.Short.valueOf(r1);	 Catch:{ NumberFormatException -> 0x040c }
        r1 = r1.shortValue();	 Catch:{ NumberFormatException -> 0x040c }
        r9.discs = r1;	 Catch:{ NumberFormatException -> 0x040c }
        goto L_0x0047;
    L_0x040c:
        r1 = move-exception;
        r1 = LOGGER;
        r2 = r9.debugLevel;
        r1 = r1.isLoggable(r2);
        if (r1 == 0) goto L_0x0047;
    L_0x0417:
        r1 = LOGGER;
        r2 = r9.debugLevel;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Could not parse number of discs: ";
        r3 = r3.append(r4);
        r0 = r3.append(r0);
        r0 = r0.toString();
        r1.log(r2, r0);
        goto L_0x0047;
    L_0x0434:
        r2 = move-exception;
        r2 = LOGGER;
        r3 = r9.debugLevel;
        r2 = r2.isLoggable(r3);
        if (r2 == 0) goto L_0x03fa;
    L_0x043f:
        r2 = LOGGER;
        r3 = r9.debugLevel;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Could not parse disc number: ";
        r4 = r4.append(r5);
        r4 = r4.append(r0);
        r4 = r4.toString();
        r2.log(r3, r4);
        goto L_0x03fa;
    L_0x045b:
        r0 = r9.parseTextFrame(r10);
        r1 = r0.length();
        if (r1 <= 0) goto L_0x0047;
    L_0x0465:
        r1 = 47;
        r1 = r0.indexOf(r1);
        if (r1 >= 0) goto L_0x04a1;
    L_0x046d:
        r1 = java.lang.Short.valueOf(r0);	 Catch:{ NumberFormatException -> 0x0479 }
        r1 = r1.shortValue();	 Catch:{ NumberFormatException -> 0x0479 }
        r9.track = r1;	 Catch:{ NumberFormatException -> 0x0479 }
        goto L_0x0047;
    L_0x0479:
        r1 = move-exception;
        r1 = LOGGER;
        r2 = r9.debugLevel;
        r1 = r1.isLoggable(r2);
        if (r1 == 0) goto L_0x0047;
    L_0x0484:
        r1 = LOGGER;
        r2 = r9.debugLevel;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Could not parse track number: ";
        r3 = r3.append(r4);
        r0 = r3.append(r0);
        r0 = r0.toString();
        r1.log(r2, r0);
        goto L_0x0047;
    L_0x04a1:
        r2 = 0;
        r2 = r0.substring(r2, r1);	 Catch:{ NumberFormatException -> 0x04ea }
        r2 = java.lang.Short.valueOf(r2);	 Catch:{ NumberFormatException -> 0x04ea }
        r2 = r2.shortValue();	 Catch:{ NumberFormatException -> 0x04ea }
        r9.track = r2;	 Catch:{ NumberFormatException -> 0x04ea }
    L_0x04b0:
        r1 = r1 + 1;
        r1 = r0.substring(r1);	 Catch:{ NumberFormatException -> 0x04c2 }
        r1 = java.lang.Short.valueOf(r1);	 Catch:{ NumberFormatException -> 0x04c2 }
        r1 = r1.shortValue();	 Catch:{ NumberFormatException -> 0x04c2 }
        r9.tracks = r1;	 Catch:{ NumberFormatException -> 0x04c2 }
        goto L_0x0047;
    L_0x04c2:
        r1 = move-exception;
        r1 = LOGGER;
        r2 = r9.debugLevel;
        r1 = r1.isLoggable(r2);
        if (r1 == 0) goto L_0x0047;
    L_0x04cd:
        r1 = LOGGER;
        r2 = r9.debugLevel;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Could not parse number of tracks: ";
        r3 = r3.append(r4);
        r0 = r3.append(r0);
        r0 = r0.toString();
        r1.log(r2, r0);
        goto L_0x0047;
    L_0x04ea:
        r2 = move-exception;
        r2 = LOGGER;
        r3 = r9.debugLevel;
        r2 = r2.isLoggable(r3);
        if (r2 == 0) goto L_0x04b0;
    L_0x04f5:
        r2 = LOGGER;
        r3 = r9.debugLevel;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Could not parse track number: ";
        r4 = r4.append(r5);
        r4 = r4.append(r0);
        r4 = r4.toString();
        r2.log(r3, r4);
        goto L_0x04b0;
    L_0x0511:
        r0 = r9.parseTextFrame(r10);
        r9.grouping = r0;
        goto L_0x0047;
    L_0x0519:
        r0 = r9.parseTextFrame(r10);
        r9.title = r0;
        goto L_0x0047;
    L_0x0521:
        r0 = r9.parseTextFrame(r10);
        r1 = r0.length();
        if (r1 <= 0) goto L_0x0047;
    L_0x052b:
        r1 = java.lang.Short.valueOf(r0);	 Catch:{ NumberFormatException -> 0x0537 }
        r1 = r1.shortValue();	 Catch:{ NumberFormatException -> 0x0537 }
        r9.year = r1;	 Catch:{ NumberFormatException -> 0x0537 }
        goto L_0x0047;
    L_0x0537:
        r1 = move-exception;
        r1 = LOGGER;
        r2 = r9.debugLevel;
        r1 = r1.isLoggable(r2);
        if (r1 == 0) goto L_0x0047;
    L_0x0542:
        r1 = LOGGER;
        r2 = r9.debugLevel;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Could not parse year: ";
        r3 = r3.append(r4);
        r0 = r3.append(r0);
        r0 = r0.toString();
        r1.log(r2, r0);
        goto L_0x0047;
    L_0x055f:
        r0 = r9.lyrics;
        if (r0 != 0) goto L_0x0047;
    L_0x0563:
        r0 = r9.parseCommentOrUnsynchronizedLyricsFrame(r10);
        r0 = r0.text;
        r9.lyrics = r0;
        goto L_0x0047;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.audioinfo.mp3.ID3v2Info.parseFrame(org.telegram.messenger.audioinfo.mp3.ID3v2FrameBody):void");
    }

    String parseTextFrame(ID3v2FrameBody iD3v2FrameBody) {
        return iD3v2FrameBody.readFixedLengthString((int) iD3v2FrameBody.getRemainingLength(), iD3v2FrameBody.readEncoding());
    }
}
